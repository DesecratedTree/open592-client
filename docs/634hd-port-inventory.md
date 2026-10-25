# 634HD port - file inventory

Generated from `git status --porcelain` and `git diff --cached --shortstat` after the
index and the working tree were reconciled. All tracked changes are staged, and
`git diff --exit-code` is clean: no tracked file is left unstaged. Totals: **214 files
changed** (191 additions, 23 modifications); `git diff --cached --shortstat` is the
authoritative line count. Three untracked files are JVM crash logs
(`hs_err_pid14428.log`, `hs_err_pid38548.log`, `hs_err_pid59764.log`); they are
deliberately left out and untouched.

The 158 additions and 17 modifications below are the original 634HD port inventory.
The 33 additions and 6 modifications that landed after it are listed under **Later
additions** and **Later modifications**, so 158 + 33 = 191 and 17 + 6 = 23. The latest
test result XML (97 classes, 2026-10-24 21:29) reports **844 tests, 0 failed, 0 skipped**; the
815-test figure belonged to the earlier ledger run.

**Parity status:** not achieved. This file is a file inventory, not a parity
proof; the ledger records the seven rows that have no effect.

## Additions (158, original port inventory)

`docs/` (2): 634hd-parity-ledger.md, 634hd-port-inventory.md

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/` (14): GlHdGraphicsBackend, HdCapabilities, HdCommands, HdFeature, HdFrameSequence, HdGraphicsBackend, HdPreferences, HdPreset, HdRenderDistance, HdSettings, HdStockTarget, HdSupport, HdTab, LegacyHdGraphicsBackend

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/atmosphere/` (1): HdAtmosphere

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/ground/` (8): GroundTileSpace, HdGroundTextures, HdGroundWater, HdMaterialMap, HdTerrainBlend, HdTileMaterialMap, HdWorldAtlas, HdWorldPage

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/light/` (5): HdEnvironmentLight, HdLightFrame, HdLightGate, HdLightSource, HdRoomLight

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/options/` (10): HdCanvas, HdInterfaceProbe, HdInterfaceTarget, HdOptionsAction, HdOptionsLayout, HdOptionsModel, HdOptionsOverlay, HdOptionsScreen, HdRect, ToolkitHdCanvas

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/post/` (4): HdPostGate, HdPostParams, HdPostProcess, HdPostSource

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/reflect/` (1): HdReflection

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/shader/` (12): HdDrawRequest, HdDrawRows, HdDrawSpace, HdDrawState, HdFrameState, HdLightingGate, HdLightingPipeline, HdProgram, HdProgramCompiler, HdShaderApi, HdShaderSource, JagglShaderApi

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/shadow/` (10): ShadowBinding, ShadowCaster, ShadowCasterSet, ShadowCutout, ShadowVolume, SunShadowFit, SunShadowFrame, SunShadowGate, SunShadowMap, SunShadowSubmission

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/sky/` (7): HdDayNight, HdSkyColours, HdSkyPipeline, HdSkySource, HdSkyTime, SkyGate, SkyRaySpace

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/sway/` (4): HdSway, HdSwayGate, HdVegetation, HdWindTime

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/texture/` (1): HdTextureQuality

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/water/` (7): HdCaustics, HdWaterBreaking, HdWaterGate, HdWaterPipeline, HdWaterRequest, HdWaterSource, HdWaterTime

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/` (8): HdCapabilitiesTest, HdEmbersTest, HdFeatureSupportTest, HdFrameSequenceTest, HdPreferencesTest, HdPresetTest, HdSettingsTest, RecordingHdGraphicsBackend

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/atmosphere/` (2): HdAtmosphereRealGlTest, HdAtmosphereTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/ground/` (9): GroundTileSpaceTest, HdCausticsGlTest, HdGroundRuntimeGlTest, HdGroundTexturesTest, HdMaterialMapTest, HdTerrainBlendTest, HdTileMaterialMapTest, HdWorldAtlasTest, HdWorldPageTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/light/` (5): HdEnvironmentLightGlTest, HdEnvironmentLightTest, HdLightFrameTest, HdLightGateTest, HdLightRealGlTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/options/` (6): HdOptionsLayoutTest, HdOptionsModelTest, HdOptionsScreenTest, HdOptionsShowcaseTest, ImageHdCanvas, ToolkitHdCanvasTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/post/` (3): HdPostGateTest, HdPostRealGlTest, HdPostSourceTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/reflect/` (2): HdReflectionGlTest, HdReflectionTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/shader/` (8): FakeShaderApi, HdGlslValidationTest, HdLightingGateTest, HdLightingPipelineTest, HdLightingRealGlTest, HdProgramCompilerTest, HdShaderSourceTest, HdToolkitLightingGlTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/shadow/` (6): HdSunShadowGlTest, ShadowCutoutTest, SunShadowFitTest, SunShadowFrameTest, SunShadowGateTest, SunShadowSubmissionTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/sky/` (7): HdDayNightTest, HdSkyColoursTest, HdSkyGlTest, HdSkySourceTest, HdSkyTimeTest, SkyGateTest, SkyRaySpaceTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/sway/` (4): HdSwayGateTest, HdSwayRealGlTest, HdSwayTest, HdVegetationTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/texture/` (3): HdTextureQualityGlTest, HdTextureQualityRevertGlTest, HdTextureQualityTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/water/` (7): HdCausticsTest, HdWaterBreakingTest, HdWaterGateTest, HdWaterPipelineTest, HdWaterRealGlTest, HdWaterSourceTest, HdWaterTimeTest

`src/client/src/test/java/com/jagex/game/runetek5/scene/ground/` (1): HdGroundAtlasGlTest

`src/client/src/test/java/jaggl/` (1): GlTestContext

## Modifications (17, original port inventory)

| File | Port change |
|---|---|
| `AGENTS.md` | documents the HD seams, tick ownership and the staged-changes workflow |
| `build.gradle` | wires the client source set and the LWJGL/JAWT test runtime; real-GL proofs run by default, with no property to set |
| `src/client/src/main/java/com/jagex/game/runetek5/LocEntity.java` | marks vegetation draws for the sway rows |
| `src/client/src/main/java/com/jagex/game/runetek5/Skybox.java` | routes the frame's sky slot into the HD sequence |
| `src/client/src/main/java/com/jagex/game/runetek5/cache/node/GlGroundLayer.java` | passes water depth, material id, tile texture scale and the tile-material texture to the ground seam |
| `src/client/src/main/java/com/jagex/game/runetek5/client/frame/FramePresentation.java` | per-frame presentation entry point (camera, water/wind clocks, frame sequence) |
| `src/client/src/main/java/com/jagex/game/runetek5/config/material/MaterialType.java` | exposes the stock effect slot and the two effect-parameter fields the water rows decode |
| `src/client/src/main/java/com/jagex/game/runetek5/config/quickchat/QuickChatCategoryType.java` | deob constant touched by the port's compile pass (constant pool layout) |
| `src/client/src/main/java/com/jagex/game/runetek5/deob/Class6.java` | texture hand-out hook for the mipmap/anisotropic rows |
| `src/client/src/main/java/com/jagex/game/runetek5/deob/GlVboElementsArrayBuffer.java` | supplies the interleaved vertex layout the lighting program reads |
| `src/client/src/main/java/com/jagex/game/runetek5/deob/client.java` | constructs the HD backend, settings and frame clock; tick stays game-state only |
| `src/client/src/main/java/com/jagex/game/runetek5/graphics/Toolkit.java` | declares the HD seams the deob toolkit implements (ground, water, sky, shadow, draw) |
| `src/client/src/main/java/com/jagex/game/runetek5/graphics/gl/GlToolkit.java` | the toolkit-side seam: lighting, ground atlas, water (breaking included), sky, shadow map, post pass |
| `src/client/src/main/java/com/jagex/game/runetek5/graphics/model/GlModel.java` | model draw hooks for lighting rows, reflection slot, sway and shadow casters |
| `src/client/src/main/java/com/jagex/game/runetek5/scene/ground/GlGround.java` | per-tile material map, tile-material texture and the ground lighting seam |
| `src/client/src/main/java/com/jagex/game/runetek5/toolkit/opengl/EffectManagerGL.java` | lets the reflections row take the stock specular draw |
| `src/client/src/main/java/com/jagex/game/runetek5/toolkit/opengl/OpenGLTextureCache.java` | mipmap/anisotropic hand-out and the revert path |

## Later additions (33)

These landed after the original port inventory and are included in the 191 total.

`launcher/` (23): README.md, launcher.properties.example, src-tauri/Cargo.lock,
src-tauri/Cargo.toml, src-tauri/build.rs, src-tauri/capabilities/default.json,
src-tauri/icons/128x128.png, src-tauri/icons/128x128@2x.png, src-tauri/icons/32x32.png,
src-tauri/icons/icon.icns, src-tauri/icons/icon.ico, src-tauri/icons/icon.png,
src-tauri/src/config.rs, src-tauri/src/jar.rs, src-tauri/src/launch.rs,
src-tauri/src/main.rs, src-tauri/tauri.conf.json, tools/make_icons.py, ui/app.css,
ui/app.js, ui/data.js, ui/index.html, ui/mock.js

`src/client/src/main/java/com/jagex/game/runetek5/client/config/` (2): LaunchConfig, LauncherProperties

`src/client/src/main/java/com/jagex/game/runetek5/client/frame/` (1): CanvasResizeRedraw

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/ground/` (1): HdMaterialArt

`src/client/src/main/java/com/jagex/game/runetek5/client/hd/state/` (1): HdGlState

`src/client/src/test/java/com/jagex/game/runetek5/client/config/` (2): LaunchConfigTest, LauncherPropertiesTest

`src/client/src/test/java/com/jagex/game/runetek5/client/frame/` (1): CanvasResizeRedrawTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/` (1): HdFrameContractGlTest

`src/client/src/test/java/com/jagex/game/runetek5/client/hd/ground/` (1): HdMaterialArtTest

## Later modifications (6)

| File | Change |
|---|---|
| `.gitignore` | ignores launcher runtime state, logs and Tauri build output |
| `src/client/src/main/java/com/jagex/game/runetek5/ClientLoader.java` | server host comes from `LaunchConfig` |
| `src/client/src/main/java/com/jagex/game/runetek5/client/GameShell.java` | attaches the canvas resize redraw seam and sets the letterbox background black |
| `src/client/src/main/java/com/jagex/game/runetek5/deob/Class145_Sub1.java` | RSA modulus/exponent come from `LaunchConfig` |
| `src/client/src/main/java/com/jagex/game/runetek5/deob/Class4_Sub6_Sub37.java` | RSA modulus/exponent come from `LaunchConfig` |
| `src/client/src/main/java/com/jagex/game/runetek5/deob/Static179.java` | RSA modulus/exponent come from `LaunchConfig` |

## Deliberately excluded

- `build/` - Gradle output, test result XML, `hd-parity-*.log` and
  `hd-parity-*-lines.txt`. Regenerated by every run; ignored, not review material.
- `.gradle/` and IDE state - caches, not source.

No source, test or documentation file is excluded. The three `hs_err_pid*.log`
crash logs at the repository root are deliberately excluded from staging: they are
JVM crash output from test runs, not source, and are left untouched.
