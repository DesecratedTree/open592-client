(function () {
  "use strict";

  const INVOKE = (command, args) => window.__TAURI__.core.invoke(command, args);
  const MOCK = Boolean(window.__MOCK__);
  const $ = (id) => document.getElementById(id);

  const state = {
    snapshot: null,
    tab: "news",
    settingsOpen: false,
    saveTimer: 0,
    savedTimer: 0,
    toastTimer: 0,
  };

  const SAVE_IDS = [
    "cfg-jar",
    "cfg-url",
    "cfg-expected",
    "cfg-java",
    "cfg-host",
    "cfg-port",
    "cfg-modulus",
    "cfg-exponent",
  ];

  function node(tag, className, text) {
    const element = document.createElement(tag);
    if (className) {
      element.className = className;
    }
    if (text !== undefined) {
      element.textContent = text;
    }
    return element;
  }

  function basename(path) {
    const parts = String(path || "").split(/[\\/]/);
    return parts[parts.length - 1] || String(path || "");
  }

  // -- Placeholder content ---------------------------------------------

  function renderNews() {
    const list = $("news-list");
    list.textContent = "";
    for (const entry of window.NEWS) {
      const row = node("article", "news-entry");
      row.appendChild(node("time", "news-date", entry.date));
      const body = node("div", "news-body");
      const title = node("h3", "", entry.title);
      title.appendChild(node("span", "news-tag", entry.tag));
      body.appendChild(title);
      body.appendChild(node("p", "", entry.body));
      row.appendChild(body);
      list.appendChild(row);
    }
  }

  function renderDiscord() {
    const feed = window.DISCORD;
    $("discord-online").textContent = String(feed.online);
    const list = $("discord-list");
    list.textContent = "";
    for (const message of feed.messages) {
      const row = node("div", "discord-message");
      row.appendChild(node("span", "discord-avatar " + message.avatar, message.initials));
      const body = node("div", "discord-body");
      const name = node("span", "discord-name", message.name);
      name.appendChild(node("span", "discord-time", message.time));
      body.appendChild(name);
      body.appendChild(node("p", "discord-text", message.text));
      row.appendChild(body);
      list.appendChild(row);
    }
  }

  function renderMedia() {
    const grid = $("media-grid");
    grid.textContent = "";
    for (const item of window.MEDIA) {
      const tile = node("button", "media-tile");
      tile.type = "button";
      tile.setAttribute("aria-label", item.title + " (placeholder)");
      const art = node("span", "media-art");
      const image = document.createElement("img");
      image.src = item.src;
      image.alt = "";
      image.loading = "lazy";
      art.appendChild(image);
      art.appendChild(node("span", "media-badge", "placeholder"));
      const caption = node("span", "media-caption");
      caption.appendChild(node("span", "media-title", item.title));
      caption.appendChild(node("span", "media-sub", item.caption));
      tile.appendChild(art);
      tile.appendChild(caption);
      tile.addEventListener("click", () => openLightbox(item));
      grid.appendChild(tile);
    }
  }

  // -- Snapshot rendering ----------------------------------------------

  function applySnapshot(snapshot, options) {
    const syncInputs = !options || options.syncInputs !== false;
    state.snapshot = snapshot;

    $("config-path").textContent = basename(snapshot.configPath);
    $("status-version").textContent = "open592 launcher " + snapshot.version;
    $("status-client").textContent = MOCK ? "preview mode" : "client 592 - " + snapshot.platform;
    $("launcher-version").textContent = "open592 launcher " + snapshot.version + " - " + snapshot.platform;

    $("jar-hint").textContent = snapshot.jarExists
      ? "resolved: " + snapshot.jarPath
      : "relative paths resolve from this file's folder";
    $("java-hint").textContent = snapshot.detectedJava
      ? "detected: " + snapshot.detectedJava
      : "the client needs a Java 8 runtime";

    const md5 = snapshot.jarInfo && snapshot.jarInfo.md5;
    $("cfg-md5").textContent = md5 || (snapshot.jarExists ? "hashing..." : "no jar");

    const jarLabel = $("jar-label");
    jarLabel.textContent = basename(snapshot.jarPath) || "no client jar";
    jarLabel.title = snapshot.jarPath;

    applyValidation(snapshot.validation);
    applyClient(snapshot.client);
    if (syncInputs) {
      fillInputs(snapshot);
    }
    updatePlay();
  }

  function fillInputs(snapshot) {
    const config = snapshot.config;
    setInput("cfg-jar", config.clientJar);
    setInput("cfg-url", config.clientUpdateUrl);
    setInput("cfg-expected", config.clientMd5);
    setInput("cfg-java", config.javaPath);
    setInput("cfg-host", config.serverHost);
    setInput("cfg-port", String(config.serverPort));
    setInput("cfg-modulus", config.rsaModulus);
    setInput("cfg-exponent", config.rsaExponent);
    $("cfg-preset").value = config.hdPreset;
    selectMemory(config.memoryMb);
  }

  function setInput(id, value) {
    const input = $(id);
    if (document.activeElement === input) {
      return;
    }
    input.value = value;
  }

  function applyValidation(validation) {
    $("err-jar").textContent = validation.jar || "";
    $("err-java").textContent = validation.java || "";
    $("err-server").textContent = validation.server || "";
    $("err-preset").textContent = validation.preset || "";
  }

  function problems() {
    if (!state.snapshot) {
      return ["connecting to the launcher"];
    }
    const validation = state.snapshot.validation;
    return [validation.jar, validation.java, validation.server, validation.preset].filter(Boolean);
  }

  function applyClient(client) {
    const play = $("play");
    const label = $("launch-state");
    if (client.running) {
      play.textContent = "Stop";
      play.classList.add("is-running");
      play.disabled = false;
      label.textContent = "Running - pid " + client.pid;
      label.className = "launch-state live";
      label.title = client.logPath ? "log: " + client.logPath : "";
    } else {
      play.textContent = "Play";
      play.classList.remove("is-running");
      play.disabled = problems().length > 0;
      const exited = client.exitCode !== null && client.exitCode !== undefined;
      label.className = "launch-state" + (exited ? " warn" : problems().length ? " warn" : "");
      label.textContent = exited
        ? "Exited (" + client.exitCode + ")"
        : problems().length
        ? problems()[0]
        : "Ready";
      label.title = client.logPath ? "log: " + client.logPath : "";
    }
  }

  function updatePlay() {
    if (!state.snapshot) {
      return;
    }
    applyClient(state.snapshot.client);
  }

  // -- Settings -------------------------------------------------------

  function openSettings() {
    state.settingsOpen = true;
    $("settings").classList.add("is-open");
    $("settings").setAttribute("aria-hidden", "false");
    $("scrim").hidden = false;
  }

  function closeSettings() {
    flushSave();
    state.settingsOpen = false;
    $("settings").classList.remove("is-open");
    $("settings").setAttribute("aria-hidden", "true");
    $("scrim").hidden = true;
  }

  function collectConfig() {
    const previous = state.snapshot ? state.snapshot.config : {};
    const typedPort = Number.parseInt($("cfg-port").value, 10);
    return {
      clientJar: $("cfg-jar").value.trim(),
      clientUpdateUrl: $("cfg-url").value.trim(),
      clientMd5: $("cfg-expected").value.trim(),
      javaPath: $("cfg-java").value.trim(),
      memoryMb: selectedMemory(),
      hdPreset: $("cfg-preset").value,
      serverHost: $("cfg-host").value.trim(),
      serverPort: Number.isFinite(typedPort) ? typedPort : previous.serverPort || 43594,
      rsaModulus: $("cfg-modulus").value.trim(),
      rsaExponent: $("cfg-exponent").value.trim(),
    };
  }

  function selectedMemory() {
    const active = $("memory-segments").querySelector("button.is-active");
    return active ? Number(active.dataset.memory) : 512;
  }

  function selectMemory(memory) {
    for (const button of $("memory-segments").querySelectorAll("button")) {
      button.classList.toggle("is-active", Number(button.dataset.memory) === Number(memory));
    }
  }

  function scheduleSave() {
    clearTimeout(state.saveTimer);
    state.saveTimer = setTimeout(saveNow, 420);
  }

  function flushSave() {
    if (state.saveTimer) {
      clearTimeout(state.saveTimer);
      state.saveTimer = 0;
      return saveNow();
    }
    return Promise.resolve();
  }

  async function saveNow() {
    clearTimeout(state.saveTimer);
    state.saveTimer = 0;
    if (!state.settingsOpen) {
      return;
    }
    try {
      const snapshot = await INVOKE("save_config", { config: collectConfig() });
      applySnapshot(snapshot, { syncInputs: false });
      flashSaved();
    } catch (error) {
      toast(describeError(error));
    }
  }

  function flashSaved() {
    const marker = $("save-state");
    marker.textContent = "saved";
    marker.classList.add("is-saved");
    clearTimeout(state.savedTimer);
    state.savedTimer = setTimeout(() => {
      marker.classList.remove("is-saved");
      marker.textContent = "";
    }, 1500);
  }

  function describeError(error) {
    if (typeof error === "string") {
      return error;
    }
    if (error && typeof error.message === "string") {
      return error.message;
    }
    try {
      return JSON.stringify(error);
    } catch (ignored) {
      return String(error);
    }
  }

  function toast(message) {
    const box = $("toast");
    box.textContent = message;
    box.hidden = false;
    clearTimeout(state.toastTimer);
    state.toastTimer = setTimeout(() => {
      box.hidden = true;
    }, 4600);
  }

  // -- Actions --------------------------------------------------------

  async function toggleClient() {
    if (state.snapshot && state.snapshot.client.running) {
      try {
        applySnapshot(await INVOKE("stop_client"), { syncInputs: false });
      } catch (error) {
        toast(describeError(error));
      }
      return;
    }
    if (state.settingsOpen) {
      await flushSave();
    }
    $("play").disabled = true;
    $("launch-state").textContent = "Launching...";
    $("launch-state").className = "launch-state";
    try {
      const snapshot = await INVOKE("launch_client");
      applySnapshot(snapshot, { syncInputs: false });
      if (snapshot.client.logPath) {
        toast("Client started. Log: " + snapshot.client.logPath);
      }
    } catch (error) {
      toast(describeError(error));
      if (state.snapshot) {
        applySnapshot(state.snapshot, { syncInputs: false });
      }
    }
  }

  async function browse(kind, target) {
    try {
      const picked = await INVOKE("pick_file", { kind: kind });
      if (picked) {
        $(target).value = picked;
        scheduleSave();
      }
    } catch (error) {
      toast(describeError(error));
    }
  }

  async function verify() {
    const result = $("verify-result");
    try {
      const outcome = await INVOKE("verify_jar", { expected: $("cfg-expected").value.trim() });
      $("cfg-md5").textContent = outcome.actual || "unreadable";
      result.textContent = outcome.message;
      result.className = "verify-result" + (outcome.matched ? " ok" : outcome.expected ? " bad" : "");
    } catch (error) {
      result.textContent = describeError(error);
      result.className = "verify-result bad";
    }
  }

  function resetKeys() {
    const defaults = state.snapshot.defaults;
    $("cfg-host").value = defaults.host;
    $("cfg-port").value = String(defaults.port);
    $("cfg-modulus").value = defaults.modulus;
    $("cfg-exponent").value = defaults.exponent;
    scheduleSave();
  }

  function resetAll() {
    const defaults = state.snapshot.defaults;
    // Keep the discovered jar and runtime visible: emptying them would make
    // the pane disagree with what Play is about to use.
    $("cfg-jar").value = state.snapshot.jarPath || "";
    $("cfg-url").value = "";
    $("cfg-expected").value = "";
    $("cfg-java").value = state.snapshot.javaPath || "";
    $("cfg-host").value = defaults.host;
    $("cfg-port").value = String(defaults.port);
    $("cfg-modulus").value = defaults.modulus;
    $("cfg-exponent").value = defaults.exponent;
    $("cfg-preset").value = defaults.preset;
    selectMemory(defaults.memoryMb);
    scheduleSave();
  }

  // -- Tabs and overlays ----------------------------------------------

  function showTab(tab) {
    state.tab = tab;
    for (const button of document.querySelectorAll(".tab")) {
      const active = button.dataset.tab === tab;
      button.classList.toggle("is-active", active);
      button.setAttribute("aria-selected", String(active));
    }
    for (const view of document.querySelectorAll(".view")) {
      view.classList.toggle("is-active", view.id === "view-" + tab);
    }
  }

  function openLightbox(item) {
    $("lightbox-image").src = item.src;
    $("lightbox-image").alt = item.title;
    $("lightbox-caption").textContent = item.title + " - " + item.caption + " (placeholder)";
    $("lightbox").hidden = false;
  }

  function closeLightbox() {
    $("lightbox").hidden = true;
  }

  // -- Startup --------------------------------------------------------

  function wire() {
    for (const button of document.querySelectorAll(".tab")) {
      button.addEventListener("click", () => showTab(button.dataset.tab));
    }

    $("open-settings").addEventListener("click", openSettings);
    $("close-settings").addEventListener("click", closeSettings);
    $("scrim").addEventListener("click", closeSettings);

    $("play").addEventListener("click", toggleClient);
    $("browse-jar").addEventListener("click", () => browse("jar", "cfg-jar"));
    $("browse-java").addEventListener("click", () => browse("java", "cfg-java"));
    $("verify-md5").addEventListener("click", verify);
    $("reset-keys").addEventListener("click", resetKeys);
    $("reset-all").addEventListener("click", resetAll);
    $("open-logs").addEventListener("click", () => {
      INVOKE("reveal_log").catch((error) => toast(describeError(error)));
    });

    for (const id of SAVE_IDS) {
      const input = $(id);
      input.addEventListener("input", scheduleSave);
      input.addEventListener("change", scheduleSave);
    }
    $("cfg-preset").addEventListener("change", scheduleSave);

    for (const button of $("memory-segments").querySelectorAll("button")) {
      button.addEventListener("click", () => {
        selectMemory(button.dataset.memory);
        scheduleSave();
      });
    }

    $("lightbox").addEventListener("click", (event) => {
      if (event.target === $("lightbox")) {
        closeLightbox();
      }
    });
    $("lightbox-close").addEventListener("click", closeLightbox);

    $("drag-region").addEventListener("mousedown", (event) => {
      if (event.button === 0) {
        INVOKE("win_drag").catch(() => {});
      }
    });
    $("win-min").addEventListener("click", () => INVOKE("win_minimize").catch(() => {}));
    $("win-close").addEventListener("click", () => INVOKE("win_close").catch(() => {}));

    document.addEventListener("keydown", (event) => {
      if (event.key !== "Escape") {
        return;
      }
      if (!$("lightbox").hidden) {
        closeLightbox();
      } else if (state.settingsOpen) {
        closeSettings();
      }
    });
  }

  async function poll() {
    if (!state.snapshot) {
      return;
    }
    try {
      const status = await INVOKE("client_status");
      const previous = state.snapshot.client;
      if (
        status.running !== previous.running ||
        status.pid !== previous.pid ||
        status.exitCode !== previous.exitCode
      ) {
        state.snapshot.client = status;
        applyClient(status);
      }
    } catch (ignored) {
      // The window is closing; nothing to report.
    }
  }

  async function start() {
    renderNews();
    renderDiscord();
    renderMedia();
    wire();
    showTab("news");
    try {
      applySnapshot(await INVOKE("bootstrap"));
    } catch (error) {
      $("launch-state").textContent = "Launcher backend unavailable";
      $("launch-state").className = "launch-state warn";
      toast(describeError(error));
    }
    setInterval(poll, 1400);
  }

  start();
})();
