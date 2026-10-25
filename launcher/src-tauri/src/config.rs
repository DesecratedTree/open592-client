//! Launcher settings.
//!
//! One hand-editable properties file is the single source of truth for the
//! GUI, the launcher's launch path and (through `-Dopen592.config`) the client's
//! own startup seam. It uses a literal grammar, not `java.util.Properties`
//! escapes: values are written and read verbatim so a Windows path stays
//! readable and a literal `\u` cannot break the file. The client's
//! `LauncherProperties` reader implements the same grammar; see
//! `parse_properties` for the exact rules. Everything here is pure except
//! `load`/`save`, so the parsing, clamping and path rules are all
//! unit-testable without a GUI.

use serde::{Deserialize, Serialize};
use std::collections::BTreeMap;
use std::fs;
use std::io;
use std::path::{Path, PathBuf};

/// The stock open592 server host, identical to `ClientLoader.serverIp`.
pub const DEFAULT_HOST: &str = "15.204.91.129";

/// The stock game port, identical to `client.maininit`'s GL branch.
pub const DEFAULT_PORT: u16 = 43594;

/// The stock login modulus, identical to `Class186.aBigInteger2`.
pub const DEFAULT_MODULUS: &str = "150760707550173036278772945946438567090506433713559524284882578712501882671153563393908075995202185676644780813553398041300178964417276453064154818204487233225067371665309152801785075695390556272661160726188792517860341413663154923673919152081958143212280562847593468338985450560012495634444598508070964823741";

/// The stock login exponent, identical to `TextureOpColorGradient.aBigInteger1`.
pub const DEFAULT_EXPONENT: &str = "65537";

pub const MEMORY_CHOICES: [u32; 3] = [512, 1024, 2048];

/// `ingame` means "leave `~/.open592/hd.properties` authoritative".
pub const PRESET_CHOICES: [&str; 6] = ["ingame", "off", "low", "medium", "high", "ultra"];

pub const DEFAULT_MEMORY_MB: u32 = 512;

pub const DEFAULT_PRESET: &str = "ingame";

pub const CONFIG_FILE_NAME: &str = "launcher.properties";

pub const JAR_FILE_NAME: &str = "client-current.jar";

const HEADER: &str = "# open592 launcher - hand-editable, rewritten by the launcher GUI";
const PRESET_COMMENT: &str =
    "# 592HD preset: ingame (use the in-game choice), off, low, medium, high, ultra";

const KEY_LAUNCHER_VERSION: &str = "launcher.version";
const KEY_JAR: &str = "client.jar";
const KEY_UPDATE_URL: &str = "client.updateUrl";
const KEY_EXPECTED_MD5: &str = "client.md5";
const KEY_JAVA: &str = "java.path";
const KEY_MEMORY: &str = "memory.mb";
const KEY_PRESET: &str = "hd.preset";
const KEY_HOST: &str = "server.host";
const KEY_PORT: &str = "server.port";
const KEY_MODULUS: &str = "rsa.modulus";
const KEY_EXPONENT: &str = "rsa.exponent";

const CONFIG_VERSION: u32 = 1;

/// The settings the GUI edits. Camel-cased on the wire so the front-end reads
/// exactly the names it renders.
#[derive(Clone, Debug, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct LauncherConfig {
    pub client_jar: String,
    pub client_update_url: String,
    pub client_md5: String,
    pub java_path: String,
    pub memory_mb: u32,
    pub hd_preset: String,
    pub server_host: String,
    pub server_port: u16,
    pub rsa_modulus: String,
    pub rsa_exponent: String,
}

impl Default for LauncherConfig {
    fn default() -> Self {
        LauncherConfig {
            client_jar: String::new(),
            client_update_url: String::new(),
            client_md5: String::new(),
            java_path: String::new(),
            memory_mb: DEFAULT_MEMORY_MB,
            hd_preset: DEFAULT_PRESET.to_string(),
            server_host: DEFAULT_HOST.to_string(),
            server_port: DEFAULT_PORT,
            rsa_modulus: DEFAULT_MODULUS.to_string(),
            rsa_exponent: DEFAULT_EXPONENT.to_string(),
        }
    }
}

impl LauncherConfig {
    /// Trims every string and pulls hand-edited values back inside the ranges
    /// the GUI and the client understand. Never panics; bad input becomes the
    /// stock value for that field.
    pub fn normalize(&mut self) {
        self.client_jar = self.client_jar.trim().to_string();
        self.client_update_url = self.client_update_url.trim().to_string();
        self.client_md5 = self.client_md5.trim().to_string();
        self.java_path = self.java_path.trim().to_string();
        self.rsa_modulus = self.rsa_modulus.trim().to_string();
        self.rsa_exponent = self.rsa_exponent.trim().to_string();

        if !MEMORY_CHOICES.contains(&self.memory_mb) {
            self.memory_mb = DEFAULT_MEMORY_MB;
        }

        let preset = self.hd_preset.trim().to_ascii_lowercase();
        self.hd_preset = if PRESET_CHOICES.contains(&preset.as_str()) {
            preset
        } else {
            DEFAULT_PRESET.to_string()
        };

        let (host, port) = split_address(&self.server_host);
        self.server_host = if host.is_empty() {
            DEFAULT_HOST.to_string()
        } else {
            host
        };
        if let Some(port) = port {
            self.server_port = port;
        }
        if self.server_port == 0 {
            self.server_port = DEFAULT_PORT;
        }

        if self.rsa_modulus.is_empty() {
            self.rsa_modulus = DEFAULT_MODULUS.to_string();
        }
        if self.rsa_exponent.is_empty() {
            self.rsa_exponent = DEFAULT_EXPONENT.to_string();
        }
    }
}

/// Splits `host`, `host:port` or `[v6]:port`, returning the host and the port
/// only when one was written. A bare IPv6 address stays whole.
pub fn split_address(input: &str) -> (String, Option<u16>) {
    let trimmed = input.trim();
    if let Some(rest) = trimmed.strip_prefix('[') {
        if let Some((host, tail)) = rest.split_once(']') {
            let port = tail
                .strip_prefix(':')
                .and_then(|value| value.parse::<u16>().ok());
            return (host.trim().to_string(), port);
        }
    }
    if trimmed.matches(':').count() == 1 {
        if let Some((host, port)) = trimmed.rsplit_once(':') {
            if let Ok(port) = port.parse::<u16>() {
                return (host.trim().to_string(), Some(port));
            }
        }
    }
    (trimmed.to_string(), None)
}

/// Accepts a decimal modulus or `0x`-prefixed hex, matching what the client's
/// `LaunchConfig` can parse into a `BigInteger`.
pub fn validate_modulus(input: &str) -> Result<(), String> {
    let trimmed = input.trim();
    if trimmed.is_empty() {
        return Err("a modulus is required".to_string());
    }
    let hex = trimmed.starts_with("0x") || trimmed.starts_with("0X");
    let digits = if hex { &trimmed[2..] } else { trimmed };
    if hex {
        if digits.len() < 16 {
            return Err("a hex modulus needs at least 16 digits".to_string());
        }
        if !digits.chars().all(|value| value.is_ascii_hexdigit()) {
            return Err("a hex modulus may only contain 0-9 and a-f".to_string());
        }
    } else {
        if digits.len() < 16 {
            return Err("a modulus needs at least 16 digits".to_string());
        }
        if !digits.chars().all(|value| value.is_ascii_digit()) {
            return Err("the modulus must be decimal digits or 0x-prefixed hex".to_string());
        }
    }
    Ok(())
}

fn validate_exponent(input: &str) -> Result<(), String> {
    input
        .trim()
        .parse::<u32>()
        .map(|_| ())
        .map_err(|_| "the exponent must be a positive number".to_string())
}

/// Per-field messages, all `None` when the launcher can start the client.
#[derive(Clone, Debug, Default, PartialEq, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct Validation {
    pub jar: Option<String>,
    pub java: Option<String>,
    pub server: Option<String>,
    pub preset: Option<String>,
}

impl Validation {
    pub fn blocking(&self) -> bool {
        self.jar.is_some() || self.java.is_some() || self.server.is_some() || self.preset.is_some()
    }

    pub fn messages(&self) -> Vec<String> {
        [&self.jar, &self.java, &self.server, &self.preset]
            .into_iter()
            .flatten()
            .cloned()
            .collect()
    }
}

/// The one validation used by the GUI, the launch button and the tests.
pub fn validate(config: &LauncherConfig, jar_exists: bool, java_exists: bool) -> Validation {
    let mut validation = Validation::default();
    if !jar_exists {
        validation.jar = Some(if config.client_jar.is_empty() {
            "select a client jar".to_string()
        } else {
            format!("no jar at {}", config.client_jar)
        });
    }
    if !java_exists {
        validation.java = Some(if config.java_path.is_empty() {
            format!("no Java runtime found; pick {}", java_picker_hint(host()))
        } else {
            format!("no Java runtime at {}", config.java_path)
        });
    }
    if config.server_host.trim().is_empty() {
        validation.server = Some("a server address is required".to_string());
    } else if config.server_port == 0 {
        validation.server = Some("the port must be between 1 and 65535".to_string());
    } else if let Err(message) = validate_modulus(&config.rsa_modulus) {
        validation.server = Some(message);
    } else if let Err(message) = validate_exponent(&config.rsa_exponent) {
        validation.server = Some(message);
    }
    if !PRESET_CHOICES.contains(&config.hd_preset.as_str()) {
        validation.preset = Some("pick a 592HD mode".to_string());
    }
    validation
}

/// Which conventions the paths below follow.
#[derive(Clone, Copy, Debug, PartialEq)]
pub enum Host {
    Windows,
    MacOs,
    Linux,
}

pub fn host() -> Host {
    if cfg!(target_os = "windows") {
        Host::Windows
    } else if cfg!(target_os = "macos") {
        Host::MacOs
    } else {
        Host::Linux
    }
}

pub fn home_dir() -> PathBuf {
    std::env::var_os("HOME")
        .or_else(|| std::env::var_os("USERPROFILE"))
        .map(PathBuf::from)
        .unwrap_or_default()
}

/// Windows keeps the properties beside the executable, so a folder can be
/// carried between machines. macOS and Linux install into read-only bundles or
/// prefixes, so they use the platform's per-user configuration directory.
///
/// Pure so all three conventions are testable from one build machine.
pub fn config_dir_for(
    host: Host,
    exe_dir: &Path,
    home: &Path,
    xdg_config_home: Option<&Path>,
) -> PathBuf {
    match host {
        Host::Windows => exe_dir.to_path_buf(),
        Host::MacOs => home
            .join("Library")
            .join("Application Support")
            .join("open592"),
        Host::Linux => xdg_config_home
            .filter(|path| !path.as_os_str().is_empty())
            .map(Path::to_path_buf)
            .unwrap_or_else(|| home.join(".config"))
            .join("open592"),
    }
}

/// Where the launcher keeps its file and logs.
#[derive(Clone, Debug)]
pub struct Paths {
    pub config_file: PathBuf,
    pub dir: PathBuf,
    pub logs_dir: PathBuf,
}

impl Paths {
    /// `--config` beats `OPEN592_LAUNCHER_CONFIG` beats the platform default.
    /// A directory argument gets the default file name appended.
    pub fn discover(cli: Option<PathBuf>, env: Option<PathBuf>, exe_dir: PathBuf) -> Paths {
        let platform = config_dir_for(
            host(),
            &exe_dir,
            &home_dir(),
            std::env::var_os("XDG_CONFIG_HOME").map(PathBuf::from).as_deref(),
        );
        Self::discover_in(cli, env, &platform)
    }

    fn discover_in(cli: Option<PathBuf>, env: Option<PathBuf>, platform_dir: &Path) -> Paths {
        let mut file = cli
            .or(env)
            .unwrap_or_else(|| platform_dir.join(CONFIG_FILE_NAME));
        if file.is_dir() {
            file = file.join(CONFIG_FILE_NAME);
        }
        let dir = file
            .parent()
            .map(Path::to_path_buf)
            .unwrap_or_else(|| platform_dir.to_path_buf());
        Paths {
            config_file: file,
            logs_dir: dir.join("logs"),
            dir,
        }
    }
}

fn is_known_key(key: &str) -> bool {
    [
        KEY_LAUNCHER_VERSION,
        KEY_JAR,
        KEY_UPDATE_URL,
        KEY_EXPECTED_MD5,
        KEY_JAVA,
        KEY_MEMORY,
        KEY_PRESET,
        KEY_HOST,
        KEY_PORT,
        KEY_MODULUS,
        KEY_EXPONENT,
    ]
    .iter()
    .any(|known| *known == key)
}

/// The literal grammar shared with `client.config.LauncherProperties`:
///
/// - UTF-8; bytes that are not valid UTF-8 make the file unreadable;
/// - lines end at `\n` and one trailing `\r` is ignored;
/// - surrounding Unicode whitespace is trimmed from the line, key and value;
/// - a line whose first character is `#` or `!` is a comment;
/// - the key ends at the first `=`, or at the first `:` when the line has no
///   `=`; the rest of the line is the literal value, so `\`, `\u`, `:`, `=`
///   and `#` are ordinary characters inside a value;
/// - there are no escapes and no line continuations, so a value cannot span
///   lines; a later duplicate key replaces an earlier one.
fn parse_properties(text: &str) -> (BTreeMap<String, String>, Vec<String>) {
    let mut values = BTreeMap::new();
    let mut extras = Vec::new();
    for raw in text.lines() {
        let line = raw.trim();
        if line.is_empty() {
            continue;
        }
        if line.starts_with('#') || line.starts_with('!') {
            if !line.starts_with("# open592 launcher") && !line.starts_with("# 592HD preset") {
                extras.push(line.to_string());
            }
            continue;
        }
        match line.split_once('=').or_else(|| line.split_once(':')) {
            Some((key, value)) => {
                let key = key.trim();
                if is_known_key(key) {
                    values.insert(key.to_string(), value.trim().to_string());
                } else {
                    // An unknown key is the user's own line; keep it.
                    extras.push(line.to_string());
                }
            }
            None => extras.push(line.to_string()),
        }
    }
    (values, extras)
}

/// Renders the file: generated header, the known keys in a stable order, then
/// every unknown line the user had, so a hand-added key survives a GUI save.
/// Values are written literally (no escaping), matching `parse_properties`.
pub fn render(config: &LauncherConfig, extras: &[String]) -> String {
    let mut out = String::new();
    out.push_str(HEADER);
    out.push('\n');
    out.push_str(PRESET_COMMENT);
    out.push('\n');
    let mut put = |key: &str, value: &str| {
        out.push_str(key);
        out.push('=');
        out.push_str(value);
        out.push('\n');
    };
    put(KEY_LAUNCHER_VERSION, &CONFIG_VERSION.to_string());
    put(KEY_JAR, &config.client_jar);
    put(KEY_UPDATE_URL, &config.client_update_url);
    put(KEY_EXPECTED_MD5, &config.client_md5);
    put(KEY_JAVA, &config.java_path);
    put(KEY_MEMORY, &config.memory_mb.to_string());
    put(KEY_PRESET, &config.hd_preset);
    put(KEY_HOST, &config.server_host);
    put(KEY_PORT, &config.server_port.to_string());
    put(KEY_MODULUS, &config.rsa_modulus);
    put(KEY_EXPONENT, &config.rsa_exponent);
    if !extras.is_empty() {
        out.push('\n');
        for extra in extras {
            out.push_str(extra);
            out.push('\n');
        }
    }
    out
}

pub fn parse(text: &str) -> (LauncherConfig, Vec<String>) {
    let (values, extras) = parse_properties(text);
    let mut config = LauncherConfig::default();
    if let Some(value) = values.get(KEY_JAR) {
        config.client_jar = value.clone();
    }
    if let Some(value) = values.get(KEY_UPDATE_URL) {
        config.client_update_url = value.clone();
    }
    if let Some(value) = values.get(KEY_EXPECTED_MD5) {
        config.client_md5 = value.clone();
    }
    if let Some(value) = values.get(KEY_JAVA) {
        config.java_path = value.clone();
    }
    if let Some(value) = values.get(KEY_MEMORY) {
        config.memory_mb = value
            .parse::<u32>()
            .unwrap_or(DEFAULT_MEMORY_MB);
    }
    if let Some(value) = values.get(KEY_PRESET) {
        config.hd_preset = value.clone();
    }
    if let Some(value) = values.get(KEY_HOST) {
        config.server_host = value.clone();
    }
    if let Some(value) = values.get(KEY_PORT) {
        config.server_port = value.parse::<u16>().unwrap_or(DEFAULT_PORT);
    }
    if let Some(value) = values.get(KEY_MODULUS) {
        config.rsa_modulus = value.clone();
    }
    if let Some(value) = values.get(KEY_EXPONENT) {
        config.rsa_exponent = value.clone();
    }
    config.normalize();
    (config, extras)
}

pub fn load(paths: &Paths) -> (LauncherConfig, Vec<String>) {
    let text = fs::read_to_string(&paths.config_file).unwrap_or_default();
    parse(&text)
}

pub fn save(paths: &Paths, config: &LauncherConfig, extras: &[String]) -> io::Result<()> {
    if let Some(parent) = paths.config_file.parent() {
        fs::create_dir_all(parent)?;
    }
    fs::write(&paths.config_file, render(config, extras))
}

/// Absolute configured paths are used as written; relative ones are tried
/// against the config folder and then the executable's folder.
pub fn resolve_jar(configured: &str, config_dir: &Path, exe_dir: &Path) -> PathBuf {
    let trimmed = configured.trim();
    if trimmed.is_empty() {
        return discover_jar(config_dir, exe_dir)
            .unwrap_or_else(|| exe_dir.join(JAR_FILE_NAME));
    }
    let path = Path::new(trimmed);
    if path.is_absolute() {
        return path.to_path_buf();
    }
    for base in [config_dir, exe_dir] {
        let candidate = base.join(path);
        if candidate.is_file() {
            return candidate;
        }
    }
    config_dir.join(path)
}

/// Looks for the stock jar in the launcher's folder and up to four parents,
/// which is how a build under `target/release` still finds the repository jar.
pub fn discover_jar(config_dir: &Path, exe_dir: &Path) -> Option<PathBuf> {
    let mut dirs = vec![exe_dir.to_path_buf(), config_dir.to_path_buf()];
    let mut ancestor = exe_dir.to_path_buf();
    for _ in 0..4 {
        if !ancestor.pop() {
            break;
        }
        dirs.push(ancestor.clone());
    }
    if let Ok(cwd) = std::env::current_dir() {
        dirs.push(cwd);
    }
    find_in(&dirs)
}

fn find_in(dirs: &[PathBuf]) -> Option<PathBuf> {
    let mut seen: Vec<&PathBuf> = Vec::new();
    for dir in dirs {
        if seen.contains(&dir) {
            continue;
        }
        seen.push(dir);
        let candidate = dir.join(JAR_FILE_NAME);
        if candidate.is_file() {
            return Some(candidate);
        }
    }
    None
}

/// JAVA_HOME first (both JDK and JRE layouts), then every PATH entry, then
/// macOS's own registry. Windows starts `javaw` so no console flashes; the
/// Unix hosts have no such split and start `java`.
pub fn detect_java() -> Option<PathBuf> {
    detect_java_from(
        std::env::var("JAVA_HOME").ok().as_deref(),
        std::env::var_os("PATH").as_deref(),
    )
    .or_else(macos_java_home)
}

#[cfg(target_os = "macos")]
fn macos_java_home() -> Option<PathBuf> {
    let output = std::process::Command::new("/usr/libexec/java_home")
        .output()
        .ok()?;
    if !output.status.success() {
        return None;
    }
    let home = String::from_utf8(output.stdout).ok()?;
    let candidate = Path::new(home.trim()).join("bin").join(java_binary_name());
    candidate.is_file().then_some(candidate)
}

#[cfg(not(target_os = "macos"))]
fn macos_java_home() -> Option<PathBuf> {
    None
}

fn java_binary_name() -> &'static str {
    java_binary_name_for(host())
}

/// The Java executable to look for: Windows starts `javaw.exe` so no console
/// flashes, while macOS and Linux ship an extensionless `java`.
pub fn java_binary_name_for(host: Host) -> &'static str {
    match host {
        Host::Windows => "javaw.exe",
        Host::MacOs | Host::Linux => "java",
    }
}

/// The Browse filter for a Java runtime. Windows executables end in `.exe`.
/// macOS and Linux executables have no extension, so they get no filter at all:
/// rfd's macOS backend passes a filter straight to `setAllowedFileTypes`, where
/// `*` is a literal extension and matches no real file.
pub fn java_filter_extensions(host: Host) -> &'static [&'static str] {
    match host {
        Host::Windows => &["exe"],
        Host::MacOs | Host::Linux => &[],
    }
}

/// The missing-Java validation text names the file the platform's Browse
/// dialog is actually looking for.
fn java_picker_hint(host: Host) -> &'static str {
    match host {
        Host::Windows => "javaw.exe",
        Host::MacOs | Host::Linux => "the java executable",
    }
}

fn detect_java_from(java_home: Option<&str>, path: Option<&std::ffi::OsStr>) -> Option<PathBuf> {
    let binary = java_binary_name();
    if let Some(home) = java_home {
        for candidate in [
            Path::new(home).join("bin").join(binary),
            Path::new(home).join("jre").join("bin").join(binary),
        ] {
            if candidate.is_file() {
                return Some(candidate);
            }
        }
    }
    if let Some(path) = path {
        for entry in std::env::split_paths(path) {
            let candidate = entry.join(binary);
            if candidate.is_file() {
                return Some(candidate);
            }
        }
    }
    None
}

#[cfg(test)]
mod tests {
    use super::*;

    fn temp(name: &str) -> PathBuf {
        let dir = std::env::temp_dir().join(format!(
            "open592-launcher-test-{}-{}",
            name,
            std::process::id()
        ));
        let _ = fs::remove_dir_all(&dir);
        fs::create_dir_all(&dir).unwrap();
        dir
    }

    #[test]
    fn defaults_match_the_stock_client() {
        let config = LauncherConfig::default();
        assert_eq!(config.server_host, "15.204.91.129");
        assert_eq!(config.server_port, 43594);
        assert_eq!(config.hd_preset, "ingame");
        assert_eq!(config.memory_mb, 512);
        assert_eq!(config.rsa_exponent, "65537");
        assert!(config.rsa_modulus.starts_with("1507607075501730362787729459"));
        assert_eq!(config.rsa_modulus.len(), 309);
        assert_eq!(config.client_jar, "");
    }

    #[test]
    fn parse_should_read_every_known_key() {
        let text = "\
launcher.version=1
client.jar=C:\\rs\\client-current.jar
client.updateUrl=https://example.com/client.jar
client.md5=ABC123
java.path=C:\\Java\\bin\\javaw.exe
memory.mb=2048
hd.preset=high
server.host=play.example.com
server.port=5555
rsa.modulus=0xDEADBEEFDEADBEEFDEADBEEF
rsa.exponent=3
";
        let (config, extras) = parse(text);
        assert_eq!(config.client_jar, "C:\\rs\\client-current.jar");
        assert_eq!(config.client_update_url, "https://example.com/client.jar");
        assert_eq!(config.client_md5, "ABC123");
        assert_eq!(config.memory_mb, 2048);
        assert_eq!(config.hd_preset, "high");
        assert_eq!(config.server_host, "play.example.com");
        assert_eq!(config.server_port, 5555);
        assert_eq!(config.rsa_exponent, "3");
        assert!(extras.is_empty());
    }

    #[test]
    fn parse_should_keep_values_literal() {
        let text = concat!(
            "  server.host :  play.example.com  \n",
            "server.port\t=\t5555\n",
            "client.md5= padded value \n",
            "client.md5=a=b:c#d\n",
            "client.jar=",
            r"C:\utils\ünïcode\client-current.jar",
            "\n",
            "hd.preset=  hi gh  \n",
            "server.host=last.example.com\n",
        );
        let (values, _) = parse_properties(text);

        assert_eq!(values["server.host"], "last.example.com");
        assert_eq!(values["server.port"], "5555");
        assert_eq!(values["client.md5"], "a=b:c#d");
        assert_eq!(
            values["client.jar"],
            r"C:\utils\ünïcode\client-current.jar"
        );
        assert_eq!(values["hd.preset"], "hi gh");
    }

    #[test]
    fn parse_should_handle_crlf_and_comments() {
        let text =
            "# note with = and \\u1234\r\n! other\r\nserver.host=play.example.com\r\n";
        let (values, extras) = parse_properties(text);

        assert_eq!(values["server.host"], "play.example.com");
        assert_eq!(
            extras,
            vec!["# note with = and \\u1234".to_string(), "! other".to_string()]
        );
    }

    #[test]
    fn render_should_round_trip_literal_values() {
        let dir = temp("literal");
        let paths = Paths::discover(None, None, dir.clone());
        let mut config = LauncherConfig::default();
        config.client_jar = r"C:\utils\ünïcode\client-current.jar".to_string();
        config.java_path =
            r"C:\Program Files\Java\jre1.8.0_491\bin\javaw.exe".to_string();
        config.client_update_url = "https://example.com/client.jar?q=a=b#frag".to_string();
        config.server_host = "play.example.com".to_string();
        config.server_port = 5555;
        config.hd_preset = "ultra".to_string();
        config.rsa_modulus = "0xDEADBEEFDEADBEEFDEADBEEF".to_string();
        config.rsa_exponent = "3".to_string();
        save(&paths, &config, &[]).unwrap();

        let text = fs::read_to_string(&paths.config_file).unwrap();
        assert!(text.contains(r"client.jar=C:\utils\ünïcode\client-current.jar"));
        assert!(
            text.contains(r"java.path=C:\Program Files\Java\jre1.8.0_491\bin\javaw.exe")
        );
        assert!(text.contains("client.updateUrl=https://example.com/client.jar?q=a=b#frag"));

        let (loaded, extras) = load(&paths);
        assert!(extras.is_empty());
        assert_eq!(loaded, config);
    }

    #[test]
    fn load_should_ignore_a_file_that_is_not_utf8() {
        let dir = temp("utf8");
        let paths = Paths::discover(None, None, dir.clone());
        fs::write(
            &paths.config_file,
            [
                b's', b'e', b'r', b'v', b'e', b'r', b'.', b'h', b'o', b's', b't', b'=', 0xC3, 0x28,
            ],
        )
        .unwrap();

        let (config, extras) = load(&paths);
        assert_eq!(config, LauncherConfig::default());
        assert!(extras.is_empty());
    }

	#[test]
	fn parse_should_clamp_hand_edits_to_the_vocabulary() {
        let text = "memory.mb=9999\nhd.preset=ULTRA\nserver.port=0\nrsa.modulus=\nrsa.exponent=\n";
        let (config, _) = parse(text);
        assert_eq!(config.memory_mb, 512);
        assert_eq!(config.hd_preset, "ultra");
        assert_eq!(config.server_port, 43594);
        assert_eq!(config.rsa_modulus, DEFAULT_MODULUS);
        assert_eq!(config.rsa_exponent, DEFAULT_EXPONENT);
    }

    #[test]
    fn render_should_round_trip_and_keep_unknown_lines() {
        let dir = temp("round-trip");
        let paths = Paths::discover(None, None, dir.clone());
        let (mut config, _) = parse("world.id=2\n# my note\n");
        assert_eq!(config.server_host, DEFAULT_HOST);
        config.hd_preset = "high".to_string();
        config.memory_mb = 1024;
        config.client_jar = dir.join("client-current.jar").display().to_string();
        let extras = vec!["world.id=2".to_string(), "# my note".to_string()];
        save(&paths, &config, &extras).unwrap();

        let text = fs::read_to_string(&paths.config_file).unwrap();
        assert!(text.starts_with("# open592 launcher"));
        assert!(text.contains("world.id=2"));
        assert!(text.contains("# my note"));
        assert_eq!(text.matches("memory.mb=").count(), 1);

        let (loaded, extras) = load(&paths);
        assert_eq!(loaded, config);
        assert_eq!(extras, vec!["world.id=2".to_string(), "# my note".to_string()]);
    }

    #[test]
    fn split_address_should_handle_ports_and_ipv6() {
        assert_eq!(
            split_address("play.example.com:43594"),
            ("play.example.com".to_string(), Some(43594))
        );
        assert_eq!(
            split_address(" 15.204.91.129 "),
            ("15.204.91.129".to_string(), None)
        );
        assert_eq!(split_address("[::1]:4444"), ("::1".to_string(), Some(4444)));
        assert_eq!(split_address("::1"), ("::1".to_string(), None));
        assert_eq!(
            split_address("host:notaport"),
            ("host:notaport".to_string(), None)
        );
    }

    #[test]
    fn normalize_should_split_a_pasted_address() {
        let mut config = LauncherConfig::default();
        config.server_host = "localhost:5000".to_string();
        config.normalize();
        assert_eq!(config.server_host, "localhost");
        assert_eq!(config.server_port, 5000);
    }

    #[test]
    fn validate_modulus_should_only_accept_numbers() {
        assert!(validate_modulus(DEFAULT_MODULUS).is_ok());
        assert!(validate_modulus("0x0123456789abcdef").is_ok());
        assert!(validate_modulus("0xnothex0123456789").is_err());
        assert!(validate_modulus("123").is_err());
        assert!(validate_modulus("12 34").is_err());
        assert!(validate_modulus("").is_err());
    }

    #[test]
    fn validation_should_name_the_missing_piece() {
        let mut config = LauncherConfig::default();
        let missing = validate(&config, false, false);
        assert!(missing.blocking());
        assert_eq!(missing.jar.as_deref(), Some("select a client jar"));
        let expected_java = format!("no Java runtime found; pick {}", java_picker_hint(host()));
        assert_eq!(missing.java.as_deref(), Some(expected_java.as_str()));

        config.client_jar = "C:/rs/client-current.jar".to_string();
        config.java_path = "C:/Java/bin/javaw.exe".to_string();
        let ready = validate(&config, true, true);
        assert_eq!(ready, Validation::default());

        config.rsa_modulus = "abc".to_string();
        let bad_key = validate(&config, true, true);
        assert!(bad_key.server.unwrap().contains("modulus"));
    }

    #[test]
    fn java_labels_should_follow_each_host() {
        assert_eq!(java_binary_name_for(Host::Windows), "javaw.exe");
        assert_eq!(java_binary_name_for(Host::MacOs), "java");
        assert_eq!(java_binary_name_for(Host::Linux), "java");

        assert_eq!(
            java_filter_extensions(Host::Windows),
            ["exe"].as_slice()
        );
        assert!(java_filter_extensions(Host::MacOs).is_empty());
        assert!(java_filter_extensions(Host::Linux).is_empty());

        assert_eq!(java_picker_hint(Host::Windows), "javaw.exe");
        assert_eq!(java_picker_hint(Host::MacOs), "the java executable");
        assert_eq!(java_picker_hint(Host::Linux), "the java executable");
    }

    #[test]
    fn resolve_jar_should_try_config_folder_then_executable_folder() {
        let config_dir = temp("resolve-config");
        let exe_dir = temp("resolve-exe");
        fs::write(exe_dir.join(JAR_FILE_NAME), b"jar").unwrap();
        assert_eq!(
            resolve_jar(JAR_FILE_NAME, &config_dir, &exe_dir),
            exe_dir.join(JAR_FILE_NAME)
        );
        fs::write(config_dir.join("custom.jar"), b"jar").unwrap();
        assert_eq!(
            resolve_jar("custom.jar", &config_dir, &exe_dir),
            config_dir.join("custom.jar")
        );
        assert_eq!(
            resolve_jar("", &config_dir, &exe_dir),
            exe_dir.join(JAR_FILE_NAME)
        );
    }

    #[test]
    fn discover_jar_should_search_ancestors() {
        let root = temp("ancestors");
        let nested = root.join("launcher").join("src-tauri").join("target");
        fs::create_dir_all(&nested).unwrap();
        fs::write(root.join(JAR_FILE_NAME), b"jar").unwrap();
        assert_eq!(
            discover_jar(&root, &nested),
            Some(root.join(JAR_FILE_NAME))
        );
        let empty = temp("ancestors-empty");
        assert_eq!(discover_jar(&empty, &empty), None);
    }

    #[test]
    fn detection_should_prefer_java_home_and_fall_back_to_path() {
        let home = temp("java-home");
        fs::create_dir_all(home.join("bin")).unwrap();
        let binary = home.join("bin").join(java_binary_name());
        fs::write(&binary, b"").unwrap();

        let path_dir = temp("java-path");
        fs::create_dir_all(&path_dir).unwrap();
        let path_binary = path_dir.join(java_binary_name());
        fs::write(&path_binary, b"").unwrap();

        let joined = std::env::join_paths([path_dir.as_path()]).unwrap();
        assert_eq!(
            detect_java_from(Some(home.to_str().unwrap()), Some(&joined)),
            Some(binary)
        );
        assert_eq!(
            detect_java_from(None, Some(&joined)),
            Some(path_binary.clone())
        );
        assert_eq!(
            detect_java_from(Some("/definitely/not/here"), Some(&joined)),
            Some(path_binary)
        );
    }

    #[test]
    fn paths_should_prefer_cli_then_env() {
        let platform = PathBuf::from("C:/rs");
        let cli = Paths::discover_in(
            Some(PathBuf::from("C:/tmp/custom.properties")),
            Some(PathBuf::from("C:/tmp/env.properties")),
            &platform,
        );
        assert_eq!(cli.config_file, PathBuf::from("C:/tmp/custom.properties"));
        assert_eq!(cli.logs_dir, PathBuf::from("C:/tmp/logs"));

        let env = Paths::discover_in(
            None,
            Some(PathBuf::from("C:/tmp/env.properties")),
            &platform,
        );
        assert_eq!(env.config_file, PathBuf::from("C:/tmp/env.properties"));

        let beside = Paths::discover_in(None, None, &platform);
        assert_eq!(beside.config_file, PathBuf::from("C:/rs/launcher.properties"));
    }

    #[test]
    fn config_dirs_should_follow_each_hosts_convention() {
        let exe = PathBuf::from("/opt/open592");
        let home = PathBuf::from("/home/player");
        assert_eq!(
            config_dir_for(Host::Windows, &exe, &home, None),
            PathBuf::from("/opt/open592")
        );
        assert_eq!(
            config_dir_for(Host::MacOs, &exe, &home, None),
            PathBuf::from("/home/player/Library/Application Support/open592")
        );
        assert_eq!(
            config_dir_for(Host::Linux, &exe, &home, None),
            PathBuf::from("/home/player/.config/open592")
        );
        assert_eq!(
            config_dir_for(
                Host::Linux,
                &exe,
                &home,
                Some(Path::new("/srv/xdg/open592"))
            ),
            PathBuf::from("/srv/xdg/open592/open592")
        );
    }
}
