/* Browser fallback. The real launcher injects window.__TAURI__; opening
 * index.html directly gets this in-memory backend so the layout can be
 * inspected without building the Rust shell. It never touches the disk. */

(function () {
  if (window.__TAURI__) {
    return;
  }

  window.__MOCK__ = true;

  const MODULUS =
    "150760707550173036278772945946438567090506433713559524284882578712501882671153563393908075995202185676644780813553398041300178964417276453064154818204487233225067371665309152801785075695390556272661160726188792517860341413663154923673919152081958143212280562847593468338985450560012495634444598508070964823741";

  const DEFAULTS = {
    host: "15.204.91.129",
    port: 43594,
    modulus: MODULUS,
    exponent: "65537",
    memoryMb: 512,
    preset: "ingame",
  };

  const FAKE_MD5 = "9c1f6a2e4b7d38f05a6c1e9d2b4f7a30";

  let config = {
    clientJar: "C:\\open592\\client-current.jar",
    clientUpdateUrl: "",
    clientMd5: "",
    javaPath: "C:\\Program Files\\Java\\jre1.8.0_491\\bin\\javaw.exe",
    memoryMb: 512,
    hdPreset: "ingame",
    serverHost: DEFAULTS.host,
    serverPort: DEFAULTS.port,
    rsaModulus: DEFAULTS.modulus,
    rsaExponent: DEFAULTS.exponent,
  };

  let client = {
    running: false,
    pid: null,
    exitCode: null,
    logPath: "C:\\open592\\logs\\client-1761340000.log",
  };

  function validate(cfg, jarExists, javaExists) {
    const problems = { jar: null, java: null, server: null, preset: null };
    if (!jarExists) {
      problems.jar = cfg.clientJar.trim() ? "no jar at " + cfg.clientJar : "select a client jar";
    }
    if (!javaExists) {
      problems.java = cfg.javaPath.trim()
        ? "no Java runtime at " + cfg.javaPath
        : "no Java runtime found; pick javaw.exe";
    }
    if (!cfg.serverHost.trim()) {
      problems.server = "a server address is required";
    } else if (!cfg.serverPort) {
      problems.server = "the port must be between 1 and 65535";
    } else if (!/^[0-9]+$/.test(cfg.rsaModulus.trim())) {
      problems.server = "the modulus must be decimal digits or 0x-prefixed hex";
    }
    if (!["ingame", "off", "low", "medium", "high", "ultra"].includes(cfg.hdPreset)) {
      problems.preset = "pick a 592HD mode";
    }
    return problems;
  }

  function snapshot() {
    const jarExists = Boolean(config.clientJar.trim());
    const javaExists = Boolean(config.javaPath.trim());
    return {
      config: Object.assign({}, config),
      configPath: "C:\\open592\\launcher.properties",
      jarPath: config.clientJar,
      jarExists: jarExists,
      jarInfo: {
        path: config.clientJar,
        exists: jarExists,
        size: jarExists ? 6014826 : 0,
        md5: jarExists ? FAKE_MD5 : null,
      },
      javaPath: config.javaPath,
      javaExists: javaExists,
      detectedJava: "C:\\Program Files\\Java\\jre1.8.0_491\\bin\\javaw.exe",
      validation: validate(config, jarExists, javaExists),
      client: Object.assign({}, client),
      defaults: Object.assign({}, DEFAULTS),
      presetChoices: ["ingame", "off", "low", "medium", "high", "ultra"],
      memoryChoices: [512, 1024, 2048],
      logsDir: "C:\\open592\\logs",
      version: "1.0.0",
      platform: "windows",
    };
  }

  function normalize(cfg) {
    const next = Object.assign({}, cfg);
    ["clientJar", "clientUpdateUrl", "clientMd5", "javaPath", "serverHost", "rsaModulus", "rsaExponent"].forEach(
      (key) => {
        next[key] = String(next[key] || "").trim();
      }
    );
    next.memoryMb = [512, 1024, 2048].includes(Number(next.memoryMb)) ? Number(next.memoryMb) : 512;
    next.hdPreset = ["ingame", "off", "low", "medium", "high", "ultra"].includes(next.hdPreset)
      ? next.hdPreset
      : "ingame";
    const address = next.serverHost.match(/^(.*):(\d+)$/);
    if (address) {
      next.serverHost = address[1];
      next.serverPort = Number(address[2]);
    }
    next.serverHost = next.serverHost || DEFAULTS.host;
    next.serverPort = Number(next.serverPort) > 0 ? Number(next.serverPort) : DEFAULTS.port;
    next.rsaModulus = next.rsaModulus || DEFAULTS.modulus;
    next.rsaExponent = next.rsaExponent || DEFAULTS.exponent;
    return next;
  }

  const commands = {
    async bootstrap() {
      return snapshot();
    },
    async save_config(args) {
      config = normalize(args.config || config);
      return snapshot();
    },
    async launch_client() {
      client = {
        running: true,
        pid: 4242,
        exitCode: null,
        logPath: "C:\\open592\\logs\\client-1761340000.log",
      };
      return snapshot();
    },
    async stop_client() {
      client = Object.assign({}, client, { running: false, pid: null });
      return snapshot();
    },
    async client_status() {
      return Object.assign({}, client);
    },
    async verify_jar(args) {
      const expected = String(args.expected || "").trim();
      const matched = expected !== "" && expected.toLowerCase() === FAKE_MD5;
      return {
        path: config.clientJar,
        actual: FAKE_MD5,
        expected: expected,
        matched: matched,
        message:
          expected === ""
            ? "no expected MD5 set - showing the jar's hash"
            : matched
            ? "MD5 matches"
            : "MD5 does not match",
      };
    },
    async pick_file(args) {
      return args.kind === "java"
        ? "C:\\Program Files\\Java\\jre1.8.0_491\\bin\\javaw.exe"
        : "C:\\open592\\client-current.jar";
    },
    async reveal_log() {
      return null;
    },
    async win_minimize() {
      return null;
    },
    async win_close() {
      return null;
    },
    async win_drag() {
      return null;
    },
  };

  window.__TAURI__ = {
    core: {
      invoke: async (command, args) => {
        const handler = commands[command];
        if (!handler) {
          throw new Error("unknown command: " + command);
        }
        return handler(args || {});
      },
    },
  };
})();
