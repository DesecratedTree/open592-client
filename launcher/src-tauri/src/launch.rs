//! Starting, watching and stopping the client process.

use serde::Serialize;
use std::fs::{self, File};
use std::io;
use std::path::{Path, PathBuf};
use std::process::{Child, Command, Stdio};
use std::time::{Duration, Instant, SystemTime, UNIX_EPOCH};

/// Passed through from the launcher profile in `build.gradle`, so the client
/// looks the same however it is started.
pub const NO_DRAW_ARG: &str = "-Dsun.java2d.noddraw=true";

/// The client's own settings seam: the absolute launcher.properties path, read
/// by `client.config.LaunchConfig`.
pub const CONFIG_ARG: &str = "-Dopen592.config=";

/// A fully resolved command line. Pure, so every flag is asserted by a test.
#[derive(Clone, Debug, PartialEq)]
pub struct LaunchPlan {
    pub program: PathBuf,
    pub args: Vec<String>,
}

impl LaunchPlan {
    pub fn build(java: &Path, memory_mb: u32, config_file: &Path, jar: &Path) -> LaunchPlan {
        LaunchPlan {
            program: java.to_path_buf(),
            args: vec![
                format!("-Xmx{}m", memory_mb),
                NO_DRAW_ARG.to_string(),
                format!("{}{}", CONFIG_ARG, config_file.display()),
                "-jar".to_string(),
                jar.display().to_string(),
            ],
        }
    }
}

#[derive(Clone, Debug, Default, PartialEq, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct ClientStatus {
    pub running: bool,
    pub pid: Option<u32>,
    pub exit_code: Option<i32>,
    pub log_path: Option<String>,
}

/// The unit the launcher owns: the spawned process and every descendant it
/// starts. A Java launcher such as Oracle's `java8path\javaw.exe` starts the
/// real JVM as a child, so killing only the spawned PID leaves the game alive.
#[cfg(windows)]
mod tree {
    use super::*;
    use std::os::windows::io::AsRawHandle;
    use std::os::windows::process::CommandExt;
    use windows_sys::Win32::Foundation::{CloseHandle, HANDLE};
    use windows_sys::Win32::System::JobObjects::{
        AssignProcessToJobObject, CreateJobObjectW, JobObjectBasicAccountingInformation,
        JobObjectExtendedLimitInformation, QueryInformationJobObject, SetInformationJobObject,
        TerminateJobObject, JOBOBJECT_BASIC_ACCOUNTING_INFORMATION,
        JOBOBJECT_EXTENDED_LIMIT_INFORMATION, JOB_OBJECT_LIMIT_KILL_ON_JOB_CLOSE,
    };

    /// A Windows job object. Descendants inherit the job even when the
    /// launcher's direct child exits, and `KILL_ON_JOB_CLOSE` makes the game
    /// die with the launcher instead of outliving it.
    pub struct ProcessTree {
        job: usize,
    }

    impl ProcessTree {
        pub fn attach(child: &Child) -> Option<ProcessTree> {
            unsafe {
                let job = CreateJobObjectW(std::ptr::null(), std::ptr::null());
                if job.is_null() {
                    return None;
                }
                let mut limits: JOBOBJECT_EXTENDED_LIMIT_INFORMATION = std::mem::zeroed();
                limits.BasicLimitInformation.LimitFlags = JOB_OBJECT_LIMIT_KILL_ON_JOB_CLOSE;
                let configured = SetInformationJobObject(
                    job,
                    JobObjectExtendedLimitInformation,
                    &limits as *const _ as *const std::ffi::c_void,
                    std::mem::size_of::<JOBOBJECT_EXTENDED_LIMIT_INFORMATION>() as u32,
                );
                let assigned = configured != 0
                    && AssignProcessToJobObject(job, child.as_raw_handle() as HANDLE) != 0;
                if !assigned {
                    CloseHandle(job);
                    return None;
                }
                Some(ProcessTree { job: job as usize })
            }
        }

        fn handle(&self) -> HANDLE {
            self.job as HANDLE
        }

        pub fn terminate(&self) {
            unsafe {
                TerminateJobObject(self.handle(), 1);
            }
        }

        pub fn active(&self) -> usize {
            let mut info: JOBOBJECT_BASIC_ACCOUNTING_INFORMATION = unsafe { std::mem::zeroed() };
            let ok = unsafe {
                QueryInformationJobObject(
                    self.handle(),
                    JobObjectBasicAccountingInformation,
                    &mut info as *mut _ as *mut std::ffi::c_void,
                    std::mem::size_of::<JOBOBJECT_BASIC_ACCOUNTING_INFORMATION>() as u32,
                    std::ptr::null_mut(),
                )
            };
            if ok == 0 {
                0
            } else {
                info.ActiveProcesses as usize
            }
        }

        pub fn wait_until_empty(&self, timeout: Duration) -> bool {
            let deadline = Instant::now() + timeout;
            loop {
                if self.active() == 0 {
                    return true;
                }
                if Instant::now() >= deadline {
                    return false;
                }
                std::thread::sleep(Duration::from_millis(10));
            }
        }
    }

    impl Drop for ProcessTree {
        fn drop(&mut self) {
            unsafe {
                CloseHandle(self.handle());
            }
        }
    }

    /// Covers the short window between `spawn` and `AssignProcessToJobObject`,
    /// where a launcher could already have started the real JVM. The job below
    /// still owns anything that outlives its direct parent.
    pub fn kill_tree(pid: u32) {
        const CREATE_NO_WINDOW: u32 = 0x0800_0000;
        let _ = Command::new("taskkill")
            .arg("/PID")
            .arg(pid.to_string())
            .arg("/T")
            .arg("/F")
            .creation_flags(CREATE_NO_WINDOW)
            .stdout(Stdio::null())
            .stderr(Stdio::null())
            .status();
    }

    pub fn configure(_command: &mut Command) {}
}

/// Unix's ownership unit is a process group: the client and every descendant
/// keep the group id of the spawned process, so one `kill(-pgid)` ends the
/// whole game even through a shell or Java launcher wrapper.
#[cfg(unix)]
mod tree {
    use super::*;
    use std::os::unix::process::CommandExt;

    pub struct ProcessTree {
        pgid: i32,
    }

    impl ProcessTree {
        pub fn attach(child: &Child) -> Option<ProcessTree> {
            Some(ProcessTree {
                pgid: child.id() as i32,
            })
        }

        pub fn terminate(&self) {
            unsafe {
                libc::kill(-self.pgid, libc::SIGKILL);
            }
        }

        pub fn active(&self) -> usize {
            if unsafe { libc::kill(-self.pgid, 0) } == 0 {
                1
            } else {
                0
            }
        }

        pub fn wait_until_empty(&self, timeout: Duration) -> bool {
            let deadline = Instant::now() + timeout;
            loop {
                if self.active() == 0 {
                    return true;
                }
                if Instant::now() >= deadline {
                    return false;
                }
                std::thread::sleep(Duration::from_millis(10));
            }
        }
    }

    impl Drop for ProcessTree {
        fn drop(&mut self) {
            self.terminate();
        }
    }

    pub fn kill_tree(_pid: u32) {}

    pub fn configure(command: &mut Command) {
        command.process_group(0);
    }
}

#[cfg(not(any(windows, unix)))]
mod tree {
    use super::*;

    pub struct ProcessTree;

    impl ProcessTree {
        pub fn attach(_child: &Child) -> Option<ProcessTree> {
            None
        }

        pub fn terminate(&self) {}

        pub fn active(&self) -> usize {
            0
        }

        pub fn wait_until_empty(&self, _timeout: Duration) -> bool {
            true
        }
    }

    pub fn kill_tree(_pid: u32) {}

    pub fn configure(_command: &mut Command) {}
}

pub struct ClientHandle {
    child: Option<Child>,
    tree: Option<tree::ProcessTree>,
    status: ClientStatus,
}

impl ClientHandle {
    pub fn new() -> ClientHandle {
        ClientHandle {
            child: None,
            tree: None,
            status: ClientStatus::default(),
        }
    }

    /// Spawns the plan with both streams going to one timestamped log file, so
    /// a client that dies before it paints a window still leaves its reason.
    pub fn launch(&mut self, plan: &LaunchPlan, logs_dir: &Path) -> Result<ClientStatus, String> {
        if self.child.is_some() || self.tree.is_some() || self.status.running {
            return Err("the client is already running".to_string());
        }
        fs::create_dir_all(logs_dir)
            .map_err(|error| format!("cannot create {}: {error}", logs_dir.display()))?;
        let log_path = logs_dir.join(format!("client-{}.log", timestamp()));
        let log = File::create(&log_path)
            .map_err(|error| format!("cannot write {}: {error}", log_path.display()))?;
        let stderr = log
            .try_clone()
            .map_err(|error| format!("cannot open the client log: {error}"))?;
        let mut command = Command::new(&plan.program);
        command
            .args(&plan.args)
            .stdout(Stdio::from(log))
            .stderr(Stdio::from(stderr));
        tree::configure(&mut command);
        let child = command
            .spawn()
            .map_err(|error| format!("cannot start {}: {error}", plan.program.display()))?;
        self.status = ClientStatus {
            running: true,
            pid: Some(child.id()),
            exit_code: None,
            log_path: Some(log_path.display().to_string()),
        };
        self.tree = tree::ProcessTree::attach(&child);
        self.child = Some(child);
        Ok(self.status.clone())
    }

    /// Updates the tracked tree without blocking; safe to poll as often as the
    /// UI likes. A direct child that exits is not enough to report the game as
    /// stopped: a Java launcher can hand off to the real JVM and exit first.
    pub fn poll(&mut self) -> ClientStatus {
        let mut exit = None;
        if let Some(child) = self.child.as_mut() {
            if let Ok(Some(status)) = child.try_wait() {
                exit = Some(status.code().unwrap_or(-1));
            }
        }
        if let Some(code) = exit {
            self.child = None;
            if self.tree.as_ref().map_or(false, |tree| tree.active() > 0) {
                // The launcher exited but the game is still running. Keep the
                // tree so Stop can still end it, and never claim Ready here.
                self.status.running = true;
                self.status.exit_code = None;
            } else {
                self.tree = None;
                self.status.running = false;
                self.status.pid = None;
                self.status.exit_code = Some(code);
            }
        } else if self.child.is_none() {
            if let Some(tree) = self.tree.as_ref() {
                if tree.active() == 0 {
                    self.tree = None;
                    self.status.running = false;
                    self.status.pid = None;
                }
            }
        }
        self.status.clone()
    }

    /// Ends the whole client tree if this launcher started it. Used by the Stop
    /// button and when the launcher window closes. The status only clears once
    /// the tree is actually empty, so the UI cannot say Ready over a live game.
    pub fn stop(&mut self) -> ClientStatus {
        let tree = self.tree.take();
        let mut child = self.child.take();

        if let Some(child) = child.as_mut() {
            let running = matches!(child.try_wait(), Ok(None));
            if running {
                // On Windows this also catches a Java shim that started the
                // real JVM before we could attach the job object. On Unix the
                // process group is the owner and this is a no-op.
                tree::kill_tree(child.id());
            }
        }
        if let Some(tree) = tree.as_ref() {
            tree.terminate();
        }
        if let Some(mut child) = child {
            let _ = child.kill();
            let _ = child.wait();
        }

        let stopped = tree
            .as_ref()
            .map_or(true, |tree| tree.wait_until_empty(Duration::from_secs(2)));
        if stopped {
            self.tree = None;
            self.status.running = false;
            self.status.pid = None;
            self.status.exit_code = None;
        } else {
            self.tree = tree;
            self.status.running = true;
        }
        self.status.clone()
    }
}

fn timestamp() -> u64 {
    SystemTime::now()
        .duration_since(UNIX_EPOCH)
        .map(|elapsed| elapsed.as_secs())
        .unwrap_or(0)
}

/// Opens a folder in the platform's file manager: Explorer, Finder or the
/// desktop's default handler.
pub fn reveal(path: &Path) -> io::Result<()> {
    #[cfg(target_os = "windows")]
    let mut command = {
        let mut command = Command::new("explorer");
        command.arg(path);
        command
    };
    #[cfg(target_os = "macos")]
    let mut command = {
        let mut command = Command::new("open");
        command.arg(path);
        command
    };
    #[cfg(all(unix, not(target_os = "macos")))]
    let mut command = {
        let mut command = Command::new("xdg-open");
        command.arg(path);
        command
    };
    command.status().map(|_| ())
}

#[cfg(test)]
mod tests {
    use super::*;

    fn temp(name: &str) -> PathBuf {
        let dir = std::env::temp_dir().join(format!(
            "open592-launch-test-{}-{}",
            name,
            std::process::id()
        ));
        let _ = fs::remove_dir_all(&dir);
        fs::create_dir_all(&dir).unwrap();
        dir
    }

    fn exit_plan(code: i32) -> LaunchPlan {
        #[cfg(windows)]
        {
            LaunchPlan {
                program: PathBuf::from("cmd"),
                args: vec!["/C".to_string(), format!("exit {code}")],
            }
        }
        #[cfg(not(windows))]
        {
            LaunchPlan {
                program: PathBuf::from("sh"),
                args: vec!["-c".to_string(), format!("exit {code}")],
            }
        }
    }

    fn sleep_plan() -> LaunchPlan {
        #[cfg(windows)]
        {
            LaunchPlan {
                program: PathBuf::from("cmd"),
                args: vec![
                    "/C".to_string(),
                    "ping -n 30 127.0.0.1".to_string(),
                ],
            }
        }
        #[cfg(not(windows))]
        {
            LaunchPlan {
                program: PathBuf::from("sh"),
                args: vec!["-c".to_string(), "sleep 30".to_string()],
            }
        }
    }

    #[test]
    fn plan_should_carry_memory_the_seam_and_the_jar() {
        let plan = LaunchPlan::build(
            Path::new("C:/Java/bin/javaw.exe"),
            2048,
            Path::new("C:/open592/launcher.properties"),
            Path::new("C:/open592/client-current.jar"),
        );
        assert_eq!(plan.program, PathBuf::from("C:/Java/bin/javaw.exe"));
        assert_eq!(
            plan.args,
            vec![
                "-Xmx2048m".to_string(),
                "-Dsun.java2d.noddraw=true".to_string(),
                "-Dopen592.config=C:/open592/launcher.properties".to_string(),
                "-jar".to_string(),
                "C:/open592/client-current.jar".to_string(),
            ]
        );
    }

    #[test]
    fn plan_should_use_the_chosen_memory_size() {
        for memory in [512, 1024, 2048] {
            let plan = LaunchPlan::build(
                Path::new("java"),
                memory,
                Path::new("/tmp/launcher.properties"),
                Path::new("/tmp/client.jar"),
            );
            assert_eq!(plan.args[0], format!("-Xmx{memory}m"));
            assert_eq!(plan.args.len(), 5);
        }
    }

    #[test]
    fn poll_should_report_the_exit_code_once_the_client_ends() {
        let mut handle = ClientHandle::new();
        let logs = temp("exit");
        handle.launch(&exit_plan(3), &logs).unwrap();
        assert!(handle.poll().running);
        assert!(handle.poll().log_path.is_some());

        let mut status = handle.poll();
        for _ in 0..200 {
            status = handle.poll();
            if !status.running {
                break;
            }
            std::thread::sleep(std::time::Duration::from_millis(10));
        }
        assert!(!status.running);
        assert_eq!(status.exit_code, Some(3));
        assert_eq!(status.pid, None);
    }

    #[test]
    fn stop_should_end_a_running_client() {
        let mut handle = ClientHandle::new();
        let logs = temp("stop");
        handle.launch(&sleep_plan(), &logs).unwrap();
        assert!(handle.poll().running);

        let status = handle.stop();
        assert!(!status.running);
        assert!(!handle.poll().running);
    }

    #[test]
    fn launch_should_refuse_a_second_client() {
        let mut handle = ClientHandle::new();
        let logs = temp("twice");
        handle.launch(&sleep_plan(), &logs).unwrap();
        let error = handle.launch(&sleep_plan(), &logs).unwrap_err();
        assert!(error.contains("already running"));
        handle.stop();
    }

    #[cfg(windows)]
    fn tasklist_has(image: &str) -> bool {
        let filter = format!("IMAGENAME eq {image}");
        let output = Command::new("tasklist")
            .arg("/FI")
            .arg(filter)
            .arg("/FO")
            .arg("CSV")
            .arg("/NH")
            .output();
        match output {
            Ok(output) => String::from_utf8_lossy(&output.stdout)
                .to_ascii_lowercase()
                .contains(&image.to_ascii_lowercase()),
            Err(_) => false,
        }
    }

    #[cfg(windows)]
    fn wait_for_image(image: &str, present: bool, timeout: Duration) -> bool {
        let deadline = Instant::now() + timeout;
        loop {
            if tasklist_has(image) == present {
                return true;
            }
            if Instant::now() >= deadline {
                return false;
            }
            std::thread::sleep(Duration::from_millis(50));
        }
    }

    #[cfg(windows)]
    fn tasklist_has_pid(pid: u32) -> bool {
        let output = Command::new("tasklist")
            .arg("/FI")
            .arg(format!("PID eq {pid}"))
            .arg("/FO")
            .arg("CSV")
            .arg("/NH")
            .output();
        match output {
            Ok(output) => String::from_utf8_lossy(&output.stdout).contains(&format!("\"{pid}\"")),
            Err(_) => false,
        }
    }

    /// A Java launcher such as `java8path\javaw.exe` starts the real JVM as a
    /// child. Stopping must take the child down too, not just the launcher.
    #[cfg(windows)]
    #[test]
    fn stop_should_end_a_client_tree() {
        let dir = temp("tree-windows");
        let image = format!("open592-tree-child-{}.exe", std::process::id());
        let source = Path::new("C:/Windows/System32/ping.exe");
        assert!(source.is_file(), "ping.exe is required for this test");
        let child = dir.join(&image);
        fs::copy(source, &child).unwrap();
        let wrapper = dir.join("wrapper.cmd");
        fs::write(
            &wrapper,
            format!(
                "@echo off\r\n\
                 ping -n 2 127.0.0.1 >nul\r\n\
                 start \"\" /b \"{}\" -n 30 127.0.0.1\r\n\
                 exit /b 0\r\n",
                child.display()
            ),
        )
        .unwrap();

        let plan = LaunchPlan {
            program: PathBuf::from("cmd"),
            args: vec!["/C".to_string(), wrapper.display().to_string()],
        };
        let mut handle = ClientHandle::new();
        let launched = handle.launch(&plan, &dir.join("logs")).unwrap();
        let wrapper_pid = launched.pid.unwrap();
        assert!(
            wait_for_image(&image, true, Duration::from_secs(8)),
            "the wrapper never started its child"
        );
        let deadline = Instant::now() + Duration::from_secs(5);
        while tasklist_has_pid(wrapper_pid) && Instant::now() < deadline {
            std::thread::sleep(Duration::from_millis(50));
        }
        assert!(!tasklist_has_pid(wrapper_pid), "the wrapper did not exit");
        assert!(
            handle.poll().running,
            "poll reported Ready while the game child was alive"
        );

        let status = handle.stop();
        assert!(!status.running, "stop reported the tree as still running");
        assert!(
            wait_for_image(&image, false, Duration::from_secs(3)),
            "the child survived Stop"
        );

        let _ = Command::new("taskkill")
            .arg("/IM")
            .arg(&image)
            .arg("/F")
            .output();
        let _ = fs::remove_dir_all(&dir);
    }

    #[cfg(unix)]
    fn read_child_pid(path: &Path, timeout: Duration) -> Option<u32> {
        let deadline = Instant::now() + timeout;
        loop {
            if let Ok(text) = fs::read_to_string(path) {
                if let Ok(pid) = text.trim().parse::<u32>() {
                    return Some(pid);
                }
            }
            if Instant::now() >= deadline {
                return None;
            }
            std::thread::sleep(Duration::from_millis(50));
        }
    }

    #[cfg(unix)]
    fn pid_alive(pid: u32) -> bool {
        unsafe { libc::kill(pid as i32, 0) == 0 }
    }

    #[cfg(unix)]
    #[test]
    fn stop_should_end_a_client_tree() {
        let dir = temp("tree-unix");
        let pid_file = dir.join("child.pid");
        let wrapper = dir.join("wrapper.sh");
        fs::write(
            &wrapper,
            "#!/bin/sh\nsleep 1\nsleep 30 &\necho $! > \"$1\"\nexit 0\n",
        )
        .unwrap();

        let plan = LaunchPlan {
            program: PathBuf::from("sh"),
            args: vec![
                wrapper.display().to_string(),
                pid_file.display().to_string(),
            ],
        };
        let mut handle = ClientHandle::new();
        let launched = handle.launch(&plan, &dir.join("logs")).unwrap();
        let wrapper_pid = launched.pid.unwrap();
        let child = read_child_pid(&pid_file, Duration::from_secs(8));
        assert!(child.is_some(), "the wrapper never started its child");
        let child = child.unwrap();
        assert!(pid_alive(child));
        let deadline = Instant::now() + Duration::from_secs(5);
        while pid_alive(wrapper_pid) && Instant::now() < deadline {
            std::thread::sleep(Duration::from_millis(50));
        }
        assert!(!pid_alive(wrapper_pid), "the wrapper did not exit");
        assert!(
            handle.poll().running,
            "poll reported Ready while the game child was alive"
        );

        let status = handle.stop();
        assert!(!status.running, "stop reported the tree as still running");
        let deadline = Instant::now() + Duration::from_secs(3);
        while pid_alive(child) && Instant::now() < deadline {
            std::thread::sleep(Duration::from_millis(50));
        }
        assert!(!pid_alive(child), "the child survived Stop");
        let _ = fs::remove_dir_all(&dir);
    }
}
