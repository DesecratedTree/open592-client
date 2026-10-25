//! The client jar: hashing it and comparing it with an expected digest.

use md5::{Digest, Md5};
use serde::Serialize;
use std::fs::File;
use std::io::{self, BufReader, Read};
use std::path::Path;

#[derive(Clone, Debug, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct JarInfo {
    pub path: String,
    pub exists: bool,
    pub size: u64,
    pub md5: Option<String>,
}

pub fn info(path: &Path) -> JarInfo {
    let exists = path.is_file();
    let size = if exists {
        std::fs::metadata(path).map(|meta| meta.len()).unwrap_or(0)
    } else {
        0
    };
    let md5 = if exists { md5_file(path).ok() } else { None };
    JarInfo {
        path: path.display().to_string(),
        exists,
        size,
        md5,
    }
}

/// Streams the file; a client jar is a few megabytes, but nothing here should
/// ever need the whole thing in memory.
pub fn md5_file(path: &Path) -> io::Result<String> {
    let mut reader = BufReader::new(File::open(path)?);
    let mut hasher = Md5::new();
    let mut buffer = [0u8; 64 * 1024];
    loop {
        let read = reader.read(&mut buffer)?;
        if read == 0 {
            break;
        }
        hasher.update(&buffer[..read]);
    }
    Ok(format!("{:x}", hasher.finalize()))
}

#[derive(Clone, Debug, Serialize, PartialEq)]
#[serde(rename_all = "camelCase")]
pub struct Verify {
    pub path: String,
    pub actual: Option<String>,
    pub expected: String,
    pub matched: bool,
    pub message: String,
}

/// An empty expectation is not a failure: it means the file was only hashed.
/// Digests compare case-insensitively so a pasted upper-case MD5 still works.
pub fn verify(path: &Path, expected: &str) -> Verify {
    let expected = expected.trim().to_string();
    let actual = md5_file(path).ok();
    let matched = match (&actual, expected.is_empty()) {
        (Some(actual), false) => actual.eq_ignore_ascii_case(&expected),
        _ => false,
    };
    let message = match (&actual, expected.is_empty()) {
        (None, _) => format!("cannot read {}", path.display()),
        (Some(_), true) => "no expected MD5 set — showing the jar's hash".to_string(),
        (Some(_), false) if matched => "MD5 matches".to_string(),
        (Some(_), false) => "MD5 does not match".to_string(),
    };
    Verify {
        path: path.display().to_string(),
        actual,
        expected,
        matched,
        message,
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::io::Write;

    fn temp(name: &str) -> std::path::PathBuf {
        let dir = std::env::temp_dir().join(format!(
            "open592-jar-test-{}-{}",
            name,
            std::process::id()
        ));
        let _ = std::fs::remove_dir_all(&dir);
        std::fs::create_dir_all(&dir).unwrap();
        dir.join("client-current.jar")
    }

    #[test]
    fn md5_should_match_the_known_digest_of_abc() {
        let path = temp("abc");
        let mut file = File::create(&path).unwrap();
        file.write_all(b"abc").unwrap();
        drop(file);
        assert_eq!(md5_file(&path).unwrap(), "900150983cd24fb0d6963f7d28e17f72");
    }

    #[test]
    fn info_should_report_a_missing_jar_without_failing() {
        let path = temp("missing");
        let info = info(&path);
        assert!(!info.exists);
        assert_eq!(info.size, 0);
        assert!(info.md5.is_none());
    }

    #[test]
    fn verify_should_compare_case_insensitively() {
        let path = temp("verify");
        std::fs::write(&path, b"abc").unwrap();

        let matched = verify(&path, "900150983CD24FB0D6963F7D28E17F72");
        assert!(matched.matched);
        assert_eq!(matched.message, "MD5 matches");

        let mismatched = verify(&path, "deadbeef");
        assert!(!mismatched.matched);
        assert_eq!(mismatched.message, "MD5 does not match");

        let unset = verify(&path, "   ");
        assert!(!unset.matched);
        assert!(unset.message.starts_with("no expected MD5"));
        assert_eq!(unset.actual.as_deref(), Some("900150983cd24fb0d6963f7d28e17f72"));
    }
}
