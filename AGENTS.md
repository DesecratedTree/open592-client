# AGENTS.md — open592 client

Deobfuscated RuneScape-592-style game client (Gradle, Java). Most game code
lives under `src/client/src/main/java/com/jagex/game/runetek5/` with original
deobfuscator names (`method1234`, `anInt1234`, `Class4_Sub6_Sub19`, ...).

## Build & verify

- Run tests + compile: `./gradlew test :compileClientJava`
- Tests are JUnit 5 under `src/client/src/test/java/...`, mirroring the main
  package layout. Name test classes `<Thing>Test`.
- New behavior needs tests that mirror the existing style: clamp tables,
  step/limit checks, convergence without overshoot, and gating truth tables
  (see `client/camera/CameraZoomTest`, `FogDistanceTest`).

## Code conventions

- **Never rename deob members in place.** Keep `@OriginalMember` annotations
  and `methodXXXX`/`anIntXXXX` names intact where they exist.
- **New features go in new, packaged, well-named classes** (e.g.
  `client.camera.FogDistance`, `client.camera.CameraZoomController`), then
  wire them into the deob code at the narrowest call site.
- When a renamed wrapper exists (e.g. `GlToolkit.updateFog()`), route new
  callers through it; keep the `@OriginalMember` annotation on it.
- The client tick (`deob/client.java`) owns **game state only**. Per-frame
  presentation (mouse-look, zoom/fog easing, follow camera) runs through
  `client.frame.FramePresentation.beginFrame(delta)`, which calls
  `CameraFrameUpdate.step(delta)`; easing is dt-normalised so a 20 ms step is
  bit-identical to the legacy tick step. Do not add per-tick smoothing for
  presentation values, and do not re-push fog from the tick.
- The run loop owns its **tick accounting**: execute ticks through
  `TickBatch.run(due, ...)` and drive the AWT pump and `FrameRateStats` from
  the returned *executed* count. Never bound loop work on a deob field a tick
  can reset (`GlVboArrayBuffer.method3660()` clears `Static411.anInt6741`
  mid-tick, which silently truncated catch-up ticks before).
- The frame clock has one publisher: `ClientFrameClock`. Once the loop starts,
  `Class4_Sub12_Sub1.aClass36_1` must stay the `FramePacer`, or
  `method2607()` stops advancing and every per-frame presentation value
  freezes. Off-thread lifecycle callbacks ask the loop to reset
  (`FramePacer.requestReset()`, `FrameRateStats.requestReset()`) instead of
  touching its state directly.
- State holders follow the `CameraZoom`/`FogDistance` shape: `DEFAULT`, min/max
  clamp, per-notch step, `smoothStep(int deltaMillis)`, `reset()` +
  package-private `setForTest()` hook, and a `scaleDistance`-style pure
  function so render code never duplicates the math. The shared easing curve
  (retention, tick normalisation, long-frame clamp, no-overshoot step) lives
  only in `CameraEasing`; state holders delegate to it.
- Input gating lives in the controller (`routeWheel`/`tickFog` style): pure,
  testable functions over `(notches, altHeld, ctrlHeld, shiftHeld)`.
  Document precedence (e.g. Alt+Ctrl resolves to zoom) in both code and tests.
- Default levels must be bit-identical to stock behavior (scale factor 1.0 at
  defaults); verify by running the game path, not just unit tests.

## Commits

- Message style: `feat: <what>` / `fix: <what>` / `impl: <what>`, one clear line capturing the
  intent of the whole change, plus a short body when the change spans systems.
- Do not push, open PRs, or change git config unless explicitly asked.
- THE USER WILL COMMIT CHANGES and name their commits ON THEIR OWN, KEEP CHANGES STAGED.
