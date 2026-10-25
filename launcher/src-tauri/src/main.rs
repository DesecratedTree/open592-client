#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

mod config;
mod jar;
mod launch;

use config::{LauncherConfig, Paths, Validation};
use launch::{ClientHandle, ClientStatus, LaunchPlan};
use std::path::PathBuf;
use std::sync::Mutex;
use tauri::Manager;

struct AppState {
    paths: Mutex<Paths>,
    config: Mutex<LauncherConfig>,
    extras: Mutex<Vec<String>>,
    process: Mutex<ClientHandle>,
}

/// Everything the UI needs in one object, so opening, saving and launching all
/// answer with the same shape.
#[derive(serde::Serialize)]
#[serde(rename_all = "camelCase")]
struct Snapshot {
    config: LauncherConfig,
    config_path: String,
    jar_path: String,
    jar_exists: bool,
    jar_info: jar::JarInfo,
    java_path: String,
    java_exists: bool,
    detected_java: Option<String>,
    validation: Validation,
    client: ClientStatus,
    defaults: Defaults,
    preset_choices: Vec<String>,
    memory_choices: Vec<u32>,
    logs_dir: String,
    version: String,
    platform: String,
}

#[derive(serde::Serialize)]
#[serde(rename_all = "camelCase")]
struct Defaults {
    host: String,
    port: u16,
    modulus: String,
    exponent: String,
    memory_mb: u32,
    preset: String,
}

fn exe_dir() -> PathBuf {
    std::env::current_exe()
        .ok()
        .and_then(|path| path.parent().map(|parent| parent.to_path_buf()))
        .unwrap_or_else(|| PathBuf::from("."))
}

fn config_argument() -> Option<PathBuf> {
    let mut args = std::env::args_os().skip(1);
    while let Some(arg) = args.next() {
        let text = arg.to_string_lossy().to_string();
        if let Some(value) = text.strip_prefix("--config=") {
            return Some(PathBuf::from(value));
        }
        if text == "--config" {
            return args.next().map(PathBuf::from);
        }
    }
    None
}

fn resolved_java(config: &LauncherConfig, detected: &Option<PathBuf>) -> PathBuf {
    let configured = config.java_path.trim();
    if !configured.is_empty() {
        PathBuf::from(configured)
    } else {
        detected.clone().unwrap_or_default()
    }
}

fn snapshot(state: &AppState) -> Snapshot {
    let config = state.config.lock().unwrap().clone();
    let paths = state.paths.lock().unwrap().clone();
    let detected = config::detect_java();
    let jar = config::resolve_jar(&config.client_jar, &paths.dir, &exe_dir());
    let java = resolved_java(&config, &detected);
    let validation = config::validate(&config, jar.is_file(), java.is_file());
    let client = state.process.lock().unwrap().poll();
    Snapshot {
        config,
        config_path: paths.config_file.display().to_string(),
        jar_path: jar.display().to_string(),
        jar_exists: jar.is_file(),
        jar_info: jar::info(&jar),
        java_path: java.display().to_string(),
        java_exists: java.is_file(),
        detected_java: detected.map(|path| path.display().to_string()),
        validation,
        client,
        defaults: Defaults {
            host: config::DEFAULT_HOST.to_string(),
            port: config::DEFAULT_PORT,
            modulus: config::DEFAULT_MODULUS.to_string(),
            exponent: config::DEFAULT_EXPONENT.to_string(),
            memory_mb: config::DEFAULT_MEMORY_MB,
            preset: config::DEFAULT_PRESET.to_string(),
        },
        preset_choices: config::PRESET_CHOICES
            .iter()
            .map(|choice| choice.to_string())
            .collect(),
        memory_choices: config::MEMORY_CHOICES.to_vec(),
        logs_dir: paths.logs_dir.display().to_string(),
        version: env!("CARGO_PKG_VERSION").to_string(),
        platform: std::env::consts::OS.to_string(),
    }
}

impl AppState {
    fn new(paths: Paths) -> AppState {
        let (mut config, extras) = config::load(&paths);
        config.normalize();

        let detected_java = config::detect_java();
        let mut changed = false;
        if config.client_jar.is_empty() {
            let jar = config::resolve_jar("", &paths.dir, &exe_dir());
            if jar.is_file() {
                config.client_jar = jar.display().to_string();
                changed = true;
            }
        }
        if config.java_path.is_empty() {
            if let Some(java) = detected_java {
                config.java_path = java.display().to_string();
            }
        }
        if !paths.config_file.is_file() || changed {
            let _ = config::save(&paths, &config, &extras);
        }
        AppState {
            paths: Mutex::new(paths),
            config: Mutex::new(config),
            extras: Mutex::new(extras),
            process: Mutex::new(ClientHandle::new()),
        }
    }
}

#[tauri::command]
fn bootstrap(state: tauri::State<'_, AppState>) -> Snapshot {
    snapshot(state.inner())
}

#[tauri::command]
fn save_config(config: LauncherConfig, state: tauri::State<'_, AppState>) -> Result<Snapshot, String> {
    let mut config = config;
    config.normalize();

    if config.client_jar.is_empty() {
        let paths = state.paths.lock().unwrap().clone();
        let jar = config::resolve_jar("", &paths.dir, &exe_dir());
        if jar.is_file() {
            config.client_jar = jar.display().to_string();
        }
    }
    if config.java_path.is_empty() {
        if let Some(java) = config::detect_java() {
            config.java_path = java.display().to_string();
        }
    }

    {
        let paths = state.paths.lock().unwrap();
        let extras = state.extras.lock().unwrap();
        config::save(&paths, &config, &extras).map_err(|error| {
            format!("cannot save {}: {error}", paths.config_file.display())
        })?;
    }
    *state.config.lock().unwrap() = config;
    Ok(snapshot(state.inner()))
}

#[tauri::command]
fn launch_client(state: tauri::State<'_, AppState>) -> Result<Snapshot, String> {
    let config = state.config.lock().unwrap().clone();
    let paths = state.paths.lock().unwrap().clone();
    let jar = config::resolve_jar(&config.client_jar, &paths.dir, &exe_dir());
    let java = resolved_java(&config, &config::detect_java());
    let validation = config::validate(&config, jar.is_file(), java.is_file());
    if validation.blocking() {
        return Err(validation.messages().join("\n"));
    }
    let plan = LaunchPlan::build(&java, config.memory_mb, &paths.config_file, &jar);
    state.process.lock().unwrap().launch(&plan, &paths.logs_dir)?;
    Ok(snapshot(state.inner()))
}

#[tauri::command]
fn stop_client(state: tauri::State<'_, AppState>) -> Snapshot {
    state.process.lock().unwrap().stop();
    snapshot(state.inner())
}

#[tauri::command]
fn client_status(state: tauri::State<'_, AppState>) -> ClientStatus {
    state.process.lock().unwrap().poll()
}

#[tauri::command]
fn verify_jar(expected: String, state: tauri::State<'_, AppState>) -> jar::Verify {
    let config = state.config.lock().unwrap().clone();
    let paths = state.paths.lock().unwrap().clone();
    let jar = config::resolve_jar(&config.client_jar, &paths.dir, &exe_dir());
    jar::verify(&jar, &expected)
}

#[tauri::command]
fn pick_file(kind: String) -> Option<String> {
    let dialog = rfd::FileDialog::new();
    let picked = match kind.as_str() {
        "jar" => dialog.add_filter("Client jar", &["jar"]).pick_file(),
        "java" => {
            // Windows executables have an extension; macOS and Linux Java
            // binaries do not, and a "*" filter matches no file on macOS.
            // Those hosts browse without an extension filter.
            let extensions = config::java_filter_extensions(config::host());
            if extensions.is_empty() {
                dialog.pick_file()
            } else {
                dialog.add_filter("Java runtime", extensions).pick_file()
            }
        }
        _ => dialog.pick_file(),
    };
    picked.map(|path| path.display().to_string())
}

#[tauri::command]
fn reveal_log(state: tauri::State<'_, AppState>) -> Result<(), String> {
    let paths = state.paths.lock().unwrap().clone();
    std::fs::create_dir_all(&paths.logs_dir)
        .map_err(|error| format!("cannot create {}: {error}", paths.logs_dir.display()))?;
    launch::reveal(&paths.logs_dir).map_err(|error| error.to_string())
}

#[tauri::command]
fn win_minimize(window: tauri::Window) -> Result<(), String> {
    window.minimize().map_err(|error| error.to_string())
}

#[tauri::command]
fn win_close(window: tauri::Window) -> Result<(), String> {
    window.close().map_err(|error| error.to_string())
}

#[tauri::command]
fn win_drag(window: tauri::Window) -> Result<(), String> {
    window.start_dragging().map_err(|error| error.to_string())
}

fn main() {
    let paths = Paths::discover(
        config_argument(),
        std::env::var_os("OPEN592_LAUNCHER_CONFIG").map(PathBuf::from),
        exe_dir(),
    );
    let state = AppState::new(paths);

    tauri::Builder::default()
        .manage(state)
        .invoke_handler(tauri::generate_handler![
            bootstrap,
            save_config,
            launch_client,
            stop_client,
            client_status,
            verify_jar,
            pick_file,
            reveal_log,
            win_minimize,
            win_close,
            win_drag
        ])
        .on_window_event(|window, event| {
            // A launcher that exits must not leave a game behind.
            if matches!(
                event,
                tauri::WindowEvent::CloseRequested { .. } | tauri::WindowEvent::Destroyed
            ) {
                if let Some(state) = window.try_state::<AppState>() {
                    state.process.lock().unwrap().stop();
                }
            }
        })
        .run(tauri::generate_context!())
        .expect("the open592 launcher failed to start");
}
