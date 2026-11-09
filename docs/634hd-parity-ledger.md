# 634HD parity ledger — 592 client

Every row of the 592HD pane (`HdFeature`) as recorded below, with what actually
ships and where the evidence is. This is a ledger of *claims*, not a roadmap: a
row is only listed as proven where a test in this repository asserts it, and the
quoted numbers are from the recorded run; the **Currentness** note records what
changed after it.

## Parity verdict

**Parity is not achieved.** The 35-of-39 `READY` count is a **wiring count**: a
value reaches a branch or a uniform, not evidence that the feature matches
634HD. Of those 35, 6 drive a stock 592 flag and 1 (`LIGHTING_DETAIL`) is a
port-only control; the other 28 are renderer rows, most of them subsets of
their 634HD counterparts. Four rows have no effect at all:

| Row | Status |
|---|---|
| Soft particles | PENDING_RENDERER |
| Embers and sparks | UNAVAILABLE |
| Interface scale | UNAVAILABLE |
| Smooth interface | UNAVAILABLE |

The Water row is no longer one of them: it is upstream's `client.hd.water`, live
with Off/On/Custom, and `HdWaterGate` reads it (see its row below).

Treat this document as a wiring inventory and a list of blockers, not as a
parity proof. The next pass has to implement those six and replace the
stock-backed and partial rows above before the parity goal can be claimed.

**Provenance of the quoted run (historical)**

- Command: `./gradlew cleanTest test` — no flags and no properties: the
  real-GL proofs take a context by default and skip only when the machine
  cannot give one.
- Driver: `NVIDIA GeForce RTX 4070/PCIe/SSE2 | GL 4.6.0 NVIDIA 617.42`.
- Totals at the time of that run: **815 tests, 0 failed, 0 skipped**.
  `HdGlslValidationTest :: glslangValidator accepts the vertex and fragment
  stages` now runs too: the validator is found on `PATH`
  (`/c/VulkanSDK/1.4.357.0/Bin/glslangValidator` on this machine), so no
  `-Dhd.glslValidator` is needed where glslang is installed.
- Test result XML: `build/test-results/test/*.xml`; raw console lines:
  `build/hd-parity-lines.txt` (regenerated from that run, not committed).

**Currentness.** This ledger was written from that 815-test run. Since then the
fog shader changed (the current code squares the linear band; the Fog row below
is corrected to match), the water suite gained the later `HdWaterRealGlTest`
changes, and the launcher property parser and canvas-resize seam landed. The
latest run (`./gradlew :compileClientJava test` on JDK 25) reports **960 tests,
5 failed, 1 skipped**. The five are the two known categories: four
`LaunchConfigTest` cases reading this machine's local
`LaunchConfig.DEFAULT_HOST` (`15.204.91.129` -> `127.0.0.1`) and
`HdFrameContractGlTest`'s `panelPixels = 156` baseline; the skip is the optional
glslang-validator proof. No other test fails. The pixel numbers in the older
sections are evidence from the run named beside them; the numbers in the last
section are from this run. The status columns and the parity verdict are a
wiring classification, not a fresh pixel-parity proof.

**Status counts (39 rows):** 35 READY — of which 28 are 592HD renderer rows,
6 drive a stock 592 flag, and 1 (`LIGHTING_DETAIL`) is a port-only control with
no upstream row — 1 PENDING, 3 UNAVAILABLE. This is a wiring count, not a parity
score; see the verdict above and the four no-effect rows listed there.

`READY` here means exactly what `HdSupport.READY` means: the value changes the
game now, and only when the row's dependencies are on. It does not mean
"byte-identical to 634HD". The rows that drive a stock flag change 592's own
effect; this renderer reimplements nothing for them, and no pixel test of theirs
exists. Several renderer rows are also conditional on Per-pixel lighting or on
the atlas rows; the six that have no effect at all are listed in the verdict.

## Lighting

| Row | Status | What ships | Evidence in this repo |
|---|---|---|---|
| Fog | READY | The stock linear band with its interior thickened by squaring the linear fraction (not a smoothstep); the band ends are unchanged. Off is the stock band. | `HdAtmosphereTest` "the HD fog curve leaves both band ends alone"; `HdAtmosphereRealGlTest`: `[hd-gl] fog row off = rgb(255,146,146)`, `[hd-gl] fog row on = rgb(255,83,83)`. |
| Sky | READY | A gradient plus sun disc drawn as the background; no cloud, moon or star layer. A map with its own skybox keeps it, and Off is no draw, so the frame is the stock clear. | `HdSkyGlTest` "every pixel is the frame the shader's source predicts" (`[hd-sky] off: 0 pixels changed, draws 6`); the slot itself: `HdFrameSequenceTest` "the world pass's sky slot draws once per frame, and counts it" (`[hd-frame] sky slot: drawn=true draws 0 -> 1, centre #203040`). |
| Stock scenery shadows | READY, stock | The 592 scenery-shadow **level** (`Class50.method2852`), pushed through `HdGraphicsBackend`. Not the 634HD sun map. | `HdSettingsTest` "a preset writes only the rows this client can apply today" (asserts the backend write); no pixel line of its own — the pass is 592's. |
| Cut-out shadows | READY | Alpha-tested casters in the sun depth pass, so leaves and fences cast their shape. | `HdSunShadowGlTest` "a cut-out caster casts the shape of its texture, not its quad": `[hd-shadow] cut-out row on: opaque side rgb(51,51,51), rgb(51,51,51) / transparent side rgb(255,255,255), rgb(255,255,255)`. |
| Sun shadows | READY | One depth map per frame, Off/1024/2048/4096. Casters are collected from the previous world draw, so the map lags one frame; sway casters stay at their rest pose. | `HdSunShadowGlTest` "the occluder darkens the receiver only where it blocks the sun"; `[hd-shadow] row off rgb(255,255,255), row on rgb(51,51,51)`. |
| Shadow detail | READY, port-only | PCF kernel 3×3 or 5×5. Not a 634HD row; upstream fixes the kernel. | `SunShadowGateTest` "lighting detail selects a 3x3 or 5x5 PCF kernel"; `HdSunShadowGlTest` "PCF turns the shadow edge into a gradient, and a wider kernel widens it". |
| Per-pixel lighting | READY | The lit fragment stage: sun, anti-sun, ambient, the fog band, point lights. | `HdToolkitLightingGlTest` "a sun facing the surface lights the interleaved draw through the toolkit seam": `[hd-gl] shader, sun facing = rgb(255,255,255)`, `[hd-gl] shader, sun behind = rgb(0,0,0)`. |
| Lighting model | READY | The term-sum lit shader: sun with the shadow map, sky/ground hemisphere, the material's occlusion, a roughness glint and the emissive map, plus the anti-sun and point lights. Maps come from `634hd/data/materials.json`, and a set the build ships binds its precompressed BC5/BC7 blocks through `HdMapBlocks` (decoded to RGBA because jaggl samples no blocks), with the height read from the packed map's alpha and marched by `materialUv()`. Off is the stock fixed-function draw. | `HdShaderSourceTest` (the term sum is the only lit path); `HdMaterialCoverageTest`: `[hd-maps] 190 declared map sets: 39 bind decoded maps, 151 name maps this build does not ship, 0 unresolved; normal blocks 39 / png 0, orm blocks 39 / png 0, emissive blocks 1 / png 0, height packed 39 / png 0; 256 square`; `HdLightingModelGlTest`: `[hd-model] occlusion material 63 red 51: mapped rgb(28,28,27), plain rgb(38,38,38), row off rgb(51,51,51)`, `[hd-model] relief material 66 tilt 125: mapped rgb(0,0,0), plain rgb(76,76,76), row off rgb(89,89,89)`, `[hd-model] blocks: material 63 red 52, lit rgb(25,25,25), plain rgb(38,38,38), lighting off rgb(51,51,51)` and `[hd-model] height: material 63 parallax 0.04, authored rgb(0,0,0), flat rgb(48,48,48)`. |
| Point lights | READY | The scene's lights evaluated per pixel, four slots, zeroed when off. | `HdLightingRealGlTest` "point lights add only when the row is on, through the uniform loop": `[hd-gl] point light on = rgb(51,115,51)`, `[hd-gl] point lights row off = rgb(51,51,51)`. |
| Carried light | READY | The strongest map light on the carrier's tile, re-anchored at the player. | `HdLightRealGlTest` "a carried light brightens near the carrier and falls to zero at its radius": `[hd-gl] carried, near = rgb(148,148,148) (expected 148), middle rgb(82,82,82) (expected 82), far rgb(51,51,51) (expected 51)`. |
| Room light | READY | An eight-by-eight chunk's own ambient, gained and tinted by the lights on its tiles, bounded to the chunk. | `HdLightRealGlTest` "a room light cannot leak into the region beside it": `[hd-gl] room: left rgb(127,51,51), right rgb(51,127,51)`. |
| Contact occlusion | READY | Baked AO from the material's own occlusion map: `occlusion = 1 - strength * (1 - orm.r)`, so a draw with no map keeps occlusion 1. The sun-map kernel this row used to read is gone. | `HdAtmosphereRealGlTest` "ambient occlusion needs a material map, not the shadow map": a shadowed contact with no map keeps its stock level. `HdLightingModelGlTest` `mapped rgb(37,37,37)` against `plain rgb(38,38,38)` is the same fragment with and without the map. |
| Distance fade | READY | The projection's outer band fades to the fog colour, reaching it at the far plane. | `HdAtmosphereRealGlTest` "distance fade leaves nearby geometry untouched and fades the far plane": `[hd-gl] near fragment, fade row on = rgb(255,255,255)`, `[hd-gl] one unit before the far plane = rgb(255,8,8)`. |
| Day and night | READY | A world clock scaling the stock light, fog colour and shadow survival; recolours the dome when Sky is on. It does not move the sun or its shadows. | `HdSkyGlTest` "day and night darkens the sky, and only when its row is on": `[hd-sky] fog: noon fogged #203040 horizon #203040, midnight fogged #070B0E horizon #070B0E`; `HdDayNightTest` "off is the identity, exactly - not nearly". |
| Environment light | READY | The environment's sun and ambient colours multiply the projected sun and ambient terms, gated by the row **and** an active area light (`HdAreaLight`, built from `environments.json`'s area rule). Off is the multiplicand 1. The ambient-only sky tint stays for draws with no area light. | `HdEnvironmentLightGlTest` "with the row on a surface facing the sky takes the environment's colour at its level": `[hd-gl] environment row on: red sky rgb(51,0,0), green sky rgb(0,51,0)`; `[hd-gl] environment row off: red sky rgb(51,51,51), green sky rgb(51,51,51)`; area tint: `[hd-model] area: untilted rgb(76,76,76), tinted rgb(76,0,0), row off rgb(76,76,76)`. |

## World

| Row | Status | What ships | Evidence in this repo |
|---|---|---|---|
| Textures | READY, stock | 592's texture flag, pushed through the backend. | `HdSettingsTest` "a preset writes only the rows this client can apply today"; `HdFeatureSupportTest` "a READY row is either stock-backed or renderer-owned, never neither". No pixel line. |
| Stock ground blending | READY, stock | 592's per-tile material pin (`Class50.aBoolean289`), i.e. one material per layer. | `HdGroundTexturesTest` "the blend row's strength travels with the draw"; `HdSettingsTest` backend-write assertion. Not 634HD's blended ground — that is Terrain blend. |
| Terrain blend | READY | A tile's own two materials mixed through a scene-anchored noise mask, triplanar on steep ground. Needs World textures and Material maps; Off is strength 0. | `HdGroundAtlasGlTest` "a two-material tile blends its own pair through the tile map entry": `[hd-atlas] blend: differing=1352, mate colour 0 -> 624, means rgb(255,0,0) n=1352 / rgb(255,75,0) n=1352, repeat differs=0`. |
| World textures | READY | One 512×512 page of 64×64 cells, each layer selecting its cell through the material map, repeating at the layer's stock texture scale (one repeat per `textureScale` scene units — not per tile times it, which magnified the art 128× into a flat texel). A material the page cannot serve keeps its stock texture: the shader reports the miss and the draw multiplies the bound texture, instead of multiplying neutral white and leaving the vertex colour. Stock non-small materials upload at 128×128, so this is a downsampled repack, not byte-identical art; a 128-material working set also evicts (66 + 1 in the stress case). | `HdGroundAtlasGlTest` "each material's tile draws its own atlas cell, on the frame after the page is built": `[hd-atlas] cells: build frames bind 0/0 and differ from stock 0/0 px; atlas frames bind 1/1 and differ from stock 0/0 px; ...`; and "the atlas repeats at the stock texture scale": `[hd-atlas] repeat: 52 colours, red spread 195; build binds 0, atlas binds 1` (the old repeat gave 3 colours, spread 2). |
| Material maps | READY | One texel per material id, `(cell + 1, brightness, blend, 255)`, built from the client's `MaterialType` records. The shader reads the red cell id; green (brightness) and blue (blend) are packed and uploaded but not yet sampled. | `HdMaterialMapTest` "a texel packs the cell and both parameters into its channels"; `HdTileMaterialMapTest` "a two-material tile records both ids and a full weight". |
| Texture replacements | READY | The shipped authored albedo (634HD's `albedo.png` for a material) over the game texture's own alpha, at both places a stock world texture is built: `OpenGLTextureCache.method1549`'s cache entry (both the ARGB and the RGB upload paths, so water and the other RGB materials get the authored art too) and the world page's cell art (`HdMaterialArt`). Off hands back the caller's own array by reference, so a disabled row cannot change a texel. | `HdTextureReplacementGlTest`: `[hd-replace] row off: plain rgb(60,60,60) alpha 128, applied rgb(60,60,60) alpha 128`; `[hd-replace] material 63 texel 54,6 art 0xFFFFFFFF vs stock 0x803C3C3C (delta 195)`; `[hd-replace] row on: stock rgb(60,60,60) alpha 128, replaced rgb(255,255,255) alpha 128, art rgb(255,255,255) alpha 128`. Plus `HdTextureReplacementsTest` (off is the game's array by reference, on keeps the alpha, no art keeps the array) and `HdFeatureSupportTest` (renderer-owned); the cache's RGB path separately: `[hd-replace] RGB upload: stock rgb(60,60,60) alpha 255, replaced rgb(255,255,255) alpha 255, art rgb(255,255,255) alpha 255; source rgb calls 1, argb calls 0`. |
| Water | READY | Upstream's `client.hd.water`, three values: Off is the stock draw, On is the game's water under the HD steps (the calm, reflective and breaking programs), Custom the port's own surface program. The row key is `water`, the one upstream uses, and `HdWaterGate` reads it. Every stage is opaque (the stock water blend stays, the alpha is written 1.0, so the terrain no longer shows through); every surface samples the frame's environment cube when there is one, and falls back to the fog band's colour when there is not; the custom surface also carries 634HD's own `hdCaustics` web and draws with the
`materials.schema.json` WATER type colours (`depth` #00758e under `surface` #69809c). | `HdWaterGateTest` truth table; `HdWaterRealGlTest` pixel proofs: `[hd-water] opaque surface: 0 centre px differ across a black/red backdrop; centre rgb(215,244,255)`, `[hd-water] ... calm avg rgb(58,77,156); green sky calm avg rgb(58,136,96)`, `[hd-water] caustics on the custom surface: 65536 px; brightest 198 -> 255`; `HdWaterSourceTest` (all four stages write `1.0`); `HdWaterPipelineTest` (the reflection flag, the caustic strength and the palette bytes); `HdWaterMapsTest` (the shipped maps decode, absent art is absent) and the map proof in `HdWaterRealGlTest`: `[hd-water] maps: available=true, frames differ in ... px`; `HdWaterBreakingTest`; `HdFeatureSupportTest` (three values, renderer-owned, no marker). |
| Reflections | READY | The environment cube on the stock specular slot only, reproducing `GL_MODULATE`; every other material keeps the stock pass. | `HdReflectionGlTest` "the reflective surface follows the environment cube": `[hd-reflect] red cube rgb(102,0,0), green cube rgb(0,102,0), changed 2704 px, bound true/true, uploads=1`; `[hd-reflect] plain: cube swap changes 0 px, row toggle changes 0 px`; `[hd-reflect] alpha test on: 2704 px of a strength-mask surface, 0 px of a coverage-alpha surface` (a reflective material's texture alpha is a strength mask, so it never cuts the surface away). |
| Caustics | READY | A per-pixel pattern on submerged ground, from the stock tile depth byte and the water clock. Only where the client fills those bytes (`underwater` regions). | `HdCausticsGlTest` "caustics light the submerged tile and leave the dry neighbour alone": `[hd-caustics] dry px=624 wet px=624, changed dry=0 wet=572, darker on the wet tile=0, uploads=9`. |
| Foliage sway | READY | A height-squared lean from the model's base, phased by world position; roots stay planted; shadow casters stay at rest. Only loc shapes 10/11 are marked (ground decor), so trees and bushes do not move. | `HdSwayRealGlTest` "the wind clock moves the foliage row and cannot touch the grass row": `[hd-sway] foliage across 1.5 s differs in 896 px; grass across 1.5 s differs in 0 px`. |
| Grass bends | READY | A compact-support push away from the viewer's eye; zero at its radius. Other actors are not part of the push. | `HdSwayRealGlTest` "grass parts the crown away from the viewer's column, and only the crown". The 0 px above is expected: grass is viewer-driven, not clock-driven. |

## Effects

| Row | Status | What ships | Evidence in this repo |
|---|---|---|---|
| Stock bloom | READY, stock | 592's bloom compositor level (`HashTable.method2673`). Not 634HD's bloom. | `HdSettingsTest` "a preset writes only the rows this client can apply today". No pixel line. |
| Tonemapping | READY | ACES shoulder applied to the 8-bit frame at presentation. Not HDR — there is no float target to recover highlights from. | `HdPostRealGlTest` "the tonemap is a shoulder: blacks and mids keep their levels": `[hd-post] tonemap dark 51->51, white 255->204`. |
| Colour grading | READY | Gain, lift and saturation, in that order. | `HdPostRealGlTest` "grading warms a mid grey without moving its brightness": `[hd-post] graded grey rgb(135,128,120)`. |
| FXAA | READY | An edge-directed filter over the finished frame, interface included. | `HdPostRealGlTest` "FXAA turns a hard diagonal edge into intermediate pixels": `[hd-post] edge intermediates 0 -> 127`. |
| Mipmaps | READY | A mip chain for world textures; Off drops the chain and restores the stock filter. | `HdTextureQualityGlTest` "a minified checkerboard aliases without mipmaps and flattens with them"; `HdTextureQualityRevertGlTest` "min filter and sampling return to stock when Mipmaps goes off". |
| Anisotropic filtering | READY | Up to 16×; reverts to 1× when the row goes off. | `HdTextureQualityGlTest` "16x anisotropy keeps more contrast at a grazing angle than none"; `HdTextureQualityRevertGlTest`. |
| Dither | READY | At most one level of noise, mean preserved. | `HdPostRealGlTest` "dither moves a level by at most one step and keeps the mean": `[hd-post] dither worst step 1, mean 127.9375`. |
| Soft particles | **PENDING** | Nothing. | Blocker below: no scene depth attachment. Proof file: `GlFramebuffer`. |
| Embers and sparks | **UNAVAILABLE** | Nothing; the row is inert and never travels as a draw decision. | `HdEmbersTest` (six tests, pass, **print no line** — the claim is structural, not a render). Blockers below. |

## Display

| Row | Status | What ships | Evidence in this repo |
|---|---|---|---|
| Draw distance | READY, stock | Percent into 592's own view range via `HdRenderDistance.PERCENT` / `FogDistance`. | `HdSettingsTest` backend-write assertion; every `PERCENT` value (100/125/140/165) sits inside `FogDistance.FOG_MIN..FOG_MAX` (100..165), whose clamp is covered by `FogDistanceTest`. No pixel line. |
| Anti-aliasing | READY, stock | 592's AA mode, pushed through the backend. | `HdSettingsTest` backend-write assertion; `HdPresetTest` "Ultra raises AA, draw distance, shadows and filtering over High". No pixel line. |
| Interface scale | **UNAVAILABLE** | Nothing. | Blocker below: fixed raster. Proof files: `HdOptionsScreen`, `ToolkitHdCanvas`. |
| Smooth interface | **UNAVAILABLE** | Nothing; it is a property of a scaled interface. | `HdFeature` row doc; depends on Interface scale. |

## Pending and unavailable — the specific blocker

**Soft particles (PENDING) — missing capability: no scene depth attachment.**
Soft particles fade a sprite where it intersects the scene, which requires
sampling the scene's depth at the sprite's pixel. The world pass draws to the
window's back buffer, whose depth cannot be sampled, and the toolkit's
`GlFramebuffer` models 2D/cube colour attachments plus renderbuffers only — no
depth texture. `SunShadowMap` had to own its own depth target for the same
reason, and that map is the sun's, not the camera's. Blocker: a colour+depth
target for the world pass that is still composited as the frame. Proof:
`src/client/src/main/java/com/jagex/game/runetek5/graphics/gl/GlFramebuffer.java`,
`src/client/src/main/java/com/jagex/game/runetek5/client/hd/shadow/SunShadowMap.java`
(class doc), `src/client/src/main/java/com/jagex/game/runetek5/toolkit/opengl/GlParticleRenderer.java`.

**Texture replacements (closed).** The Stage 1 content import supplied the
authored albedo, and the row is wired through `HdTextureReplacements.applied` at
both stock upload sites. It is `READY`; see *Texture-state hygiene and
replacements* at the end of this document. Still not claimed there: the harness
draws quads, so a live session is what shows the art on the game's materials.

**Water (closed).** This row now *is* upstream's `client.hd.water`, not a mirror
of its list: Off is the stock draw (the renderer selects the stock water effect
and touches nothing), On is the game's water under the HD steps, Custom is the
port's own surface program. The row key, the three values and their indices are
upstream's; the port-only 592HD water row that used to sit beside it is gone, so
the pane has one water row and the settings store's `water` key is the one
upstream writes. Proof: `HdWaterGateTest` (the settings truth table),
`HdWaterRealGlTest` (stock draw off; reflective, breaking and custom programs
bind), `HdFeatureSupportTest` (three values, renderer-owned, no marker).

**Embers and sparks (UNAVAILABLE) — missing content.** Nothing in this client's
particle data names an ember, a spark or a flame. An emitter (`EmissiveTriangle`)
is a producer id and a priority byte; `ParticleProducerType` is numbers only — a
packed colour ramp, spawn colours, sizes, speeds, angles, lifetimes, a material
id and flags — so no producer carries a kind. `ParticleType` classifies only how
a particle is *handled* (attached to a model's emitter face, or effector-driven),
never what it looks like. A renderer that picked particles by warm colour ramp or
upward speed would be classifying art. Proof: `ParticleProducerType.java`,
`ParticleType.java`, `HdEmbersTest` (passes, prints nothing).

**Interface scale (UNAVAILABLE) — missing capability.** The 592 interface is
drawn at the stock canvas size — `HdOptionsScreen` falls back to exactly
765×503 (`DEFAULT_CANVAS_WIDTH` / `DEFAULT_CANVAS_HEIGHT`) — and nothing in the
client claims a scale factor: outside `HdFeature` the enum constant is
referenced nowhere in `src/client/src/main` (grep), so no interface or renderer
path reads it. A scaled interface would need a second layout, not a setting.
`SMOOTH_INTERFACE` is unavailable because it is a property of a scaled
interface. Proof:
`src/client/src/main/java/com/jagex/game/runetek5/client/hd/options/HdOptionsScreen.java`
(`DEFAULT_CANVAS_WIDTH = 765`, `DEFAULT_CANVAS_HEIGHT = 503`),
`src/client/src/main/java/com/jagex/game/runetek5/client/hd/options/ToolkitHdCanvas.java`,
row docs in `HdFeature`.

## The ten long-standing recorded numbers (quoted from this run)

1. Sun-shadow row: `[hd-shadow] row off rgb(255,255,255), row on rgb(51,51,51)`.
2. Casters: `[hd-shadow] model caster: ... recorded=2, drawn=2, under rgb(51,51,51), beside rgb(255,255,255) ...`.
3. Live ground shadowing: `[hd-live] with a caster: live shadowed=133, model shadowed=133, stock shadowed=0, lit=1192`.
4. Carried light: `[hd-gl] carried: near rgb(148,148,148) (expected 148), middle rgb(82,82,82) (expected 82), far rgb(51,51,51) (expected 51)`.
5. Room light: `[hd-gl] room: left rgb(127,51,51), right rgb(51,127,51)`.
6. Environment light: `[hd-gl] environment row on: red sky rgb(51,0,0), green sky rgb(0,51,0)` against `[hd-gl] environment row off: red sky rgb(51,51,51), green sky rgb(51,51,51)`.
7. Sway off: `[hd-sway] rows off vs restored differ in 0 px; rows on changed 1641 px; displacement submissions while off 0, while on 1`.
8. Released caster: `[hd-shadow] released caster: mask 51200 -> 49152, cast px 2079 -> 2079, material binds 2 -> 0, gl error 0`.
9. Caustics: `[hd-caustics] dry px=624 wet px=624, changed dry=0 wet=572, darker on the wet tile=0, uploads=9`.
10. Calm/reflective water: `[hd-water] reflective vs calm differ in 65536 px; calm frames across a sky change differ in 0 px; calm avg rgb(59,0,0); blue sky reflect avg rgb(0,0,59); green sky reflect avg rgb(0,59,0); reflective draws 2`.

## The newer rows' own numbers (same run)

- **World textures** — `[hd-atlas] cells: build frames bind 0/0 and differ from stock 0/0 px; atlas frames bind 1/1 and differ from stock 0/0 px; red=1352 yellow=0/1352; means atlas rgb(255,0,0) n=1352 / rgb(255,255,0) n=1352 stock rgb(255,0,0) n=1352 / rgb(255,255,0) n=1352` … (the line continues with the art/unit table).
- **Atlas repeat** — `[hd-atlas] repeat: 52 colours, red spread 195; build binds 0, atlas binds 1`, on a gradient material at the stock 128 units per repeat. With the old `tileSize * textureScale` repeat the same proof reads `3 colours, red spread 2` — one texel per tile, the flat ground a live session showed.
- **Terrain blend / mate colour** — `[hd-atlas] blend: differing=1352, mate colour 0 -> 624, means rgb(255,0,0) n=1352 / rgb(255,75,0) n=1352, repeat differs=0`.
- **Atlas capacity, evictions, texture count, released bytes** — `[hd-atlas] waves: 128 materials paged (64 first, 64 second) over 128 frames; builds 128 (+1 for a returning material), disposals 128, evictions 66 (+1), atlas textures 2 -> 2, tick released 136710144 B; steady 20 frames: 0 builds, 20 binds; repaint: 0 builds, 0 px vs before, restored 0 px; ... crowd frame: 64 binds for 64 layers, 64 for 65 (one newcomer), 0 builds, 0 evictions`.
- **Reflections** — `[hd-reflect] red cube rgb(102,0,0), green cube rgb(0,102,0), changed 2704 px, bound true/true, uploads=1`; non-reflecting surface unchanged: `[hd-reflect] plain: cube swap changes 0 px, row toggle changes 0 px`; row off: `[hd-reflect] row off: bound=false, off frames differ 0 px, cube swap 0 px, uploads=0; row on bound=true`.
- **Caustics** — `changed dry=0 wet=572` (full line above); clock: `[hd-caustics] clock: wet moved 598 px, dry moved 0 px at 0.5s`.
- **Breaking water, five differences** — `[hd-water] breaking vs calm 65536 px, vs reflective 37825 px; calm vs reflective 65536 px; breaking draws 1; centre breaking rgb(58,58,138), reflective rgb(51,51,157)`; clock/material: `[hd-water] breaking across 1.2 s differs in 24648 px; the shallower band on the same geometry differs in 37646 px`; row off: `[hd-water] row off, breaking material bound: 0 px from the stock frame; HD draws 0`.
- **Sway** — `[hd-sway] rows off vs restored differ in 0 px; rows on changed 1641 px`; `[hd-sway] foliage across 1.5 s differs in 896 px; grass across 1.5 s differs in 0 px`; `[hd-sway] unmarked draw vs rows off differ in 0 px; marked draw moved 1693 px`.
- **Embers** — the run prints **no line**: `HdEmbersTest`'s six tests pass and assert `HdSupport.UNAVAILABLE`, `selectable() == false`, `HdSettings.cycle(...) == 0`, and that the row appears in no draw decision. Any numeric "ember" figure here would be invented; none is quoted.
- **`drawShadowDepth` crash reproduction** — `HdSunShadowGlTest` "a caster whose group table the client released still casts its whole silhouette": `[hd-shadow] released caster: mask 51200 -> 49152, cast px 2079 -> 2079, material binds 2 -> 0, gl error 0`; and the copy case: `[hd-shadow] copied released caster: mask 51200, cast px 2079 -> 2079, material binds 0, gl error 0`; and the never-materialised case: `[hd-shadow] missing material caster: cast px 2079 vs solid 2079, material binds 0, gl error 0`. The fault being reproduced is documented in `ShadowCutout` (the NPE in `drawShadowDepth` the frame after a table release).

## What only a cache-backed live session can settle

1. **World textures / atlas on real art**: 66 + 1 evictions and the 130 MB tick
   release are measured against generated solid-colour art at 64 cells; whether real
   material art packs without visible seams at 512×512, and how often a real
   region's material working set exceeds 64 cells, needs the cache. A live HD
   session showed flat beige ground before this pass: the repeat was a tile times
   the texture scale (128× magnification) and an unpaged material multiplied
   neutral white instead of its bound texture. Both are fixed and pinned by
   `HdGroundAtlasGlTest`; a live re-check of the same view is the remaining proof.
2. **Breaking water's depth-fed band**: the pixel proof uses a synthetic depth
   ramp. A real breaking tile must show a non-zero third texture coordinate
   (`gl_MultiTexCoord0.z`) and foam at the shore; an open lake plane with no
   depth reduces honestly to the reflective stage. Also, this harness's own
   `glTexImage2D` does not land (observed with a temporary probe in this pass:
   `glIsTexture` true, level 0 empty), so the
   material's *ripple scale* is proven at the uniform level only — real art must
   show the scale widening the ripples.
3. **Caustics coverage**: only `underwater` regions fill the depth bytes, so a
   live session must confirm which water bodies actually receive caustics
   (submerged ground, not surface lakes).
4. **Reflections on real materials**: `GL_MODULATE`-only is a code claim; a live
   session shows how many real reflective materials use other texture
   environments and therefore keep the stock pass.
5. **Stock-backed rows**: Shadows, Textures, Stock ground blending, Stock bloom,
   Draw distance and Anti-aliasing have no pixel proof of their own — they push
   592's flags. Only the game shows them.
6. **Embers, interface scale, soft particles**: unchanged conclusions from this
   ledger — each still needs content (a producer kind) or a capability (a scene
   depth target, a scalable interface layout) a code-only session cannot make.
7. **Material maps on real art**: 39 of the 190 declared map sets have files
   and all 39 bind decoded block data; which live surfaces carry those 39
   materials is still a session's question, but not a decoding one. The BC5/BC7
   sets are decoded now (`HdMapBlocks`), so no shipped set falls back to
   occlusion 1 for want of a decoder.

## Stage 1 content foundation (this pass)

At the end of that pass the renderer verdict above was unchanged: **all seven dead
rows were still dead**.
What this pass adds is the data and asset layer those rows were blocked on, plus
the loader that can be trusted to read it.

- **Imported** `634hd/` from upstream `9f99853a`: 1,427 files / 78 MB - six JSON
  files and their schemas, 5 LUT strips, the shader library, and 1,384 material
  folders (663 PNG, 217 BC5, 256 BC7).
- **Loader**: `client.hd.content.HdContent` parses lights, environments,
  materials, model/tile overrides and foliage, indexes the first-match lookups,
  converts light radius/offset from 634HD's 512-units-per-tile to this client's
  128, and records a malformed entry by file and index without throwing.
  `HdEnvironmentDefinition` reproduces upstream's area rule (level list, then
  region **or** bounds; area entries before the default).
- **Shipped-data audit**: zero skipped entries. Loaded counts: 8 light entries,
  220 environments, 200 materials, 4 model overrides, 1 tile override, 6 foliage
  entries. `HdContentResourcesTest` asserts this, checks every light's range,
  checks each named region actually contains a tile, and reads material 10001's
  PNG from the classpath.
- **Texture replacements**: `HdTextureReplacements` decodes the shipped PNG
  albedo with nearest scaling and can combine it with the stock alpha mask.
  **This does not close the row**: the stock texture cache is not yet wrapped, so
  no frame draws replacement art. BC5/BC7 are not decoded at all; jaggl samples
  RGBA and the compressed maps stay unread. Both facts were deliberate and kept
  the row `UNAVAILABLE` at the time; a later pass closed the row - see
  *Texture-state hygiene and replacements* below.
- **Build**: `main` and `client` source sets now include
  `src/client/src/main/resources`, so the data travels in `clientJar` and is on
  the test runtime classpath. Before this, `processResources` was `NO-SOURCE`.
- **Evidence**: `./gradlew test --tests
  "com.jagex.game.runetek5.client.hd.content.*"` -> 46 tests, 0 failures.
- **Untouched by that pass**: materials/lighting model, area lighting/AO, the
  water stack, shadows fixes, soft particles, embers, interface scale, the post
  chain, world display, preset alignment, and the entire plugin system. (The
  first two are closed by the next section.)

## Stage 2, slices 1 and 2 — the lighting model and baked AO (this pass)

**The seven dead rows are six.** `LIGHTING_MODEL` is `READY`: its value reaches
a uniform and the fragment stage sums the terms 634HD sums. The pane label and
key are unchanged, so a saved 634HD file reads the same row.

**What consumes the Stage 1 data.** `HdDrawRequest` carries the primitive's
material id (`GlModel`'s `local96`, `GlGroundLayer`'s `anInt1647`);
`GlToolkit.hdBeginModelLighting(textured, vegetation, material)` hands it to
`HdLightingPipeline.uploadMaterial`, which resolves the material through
`HdContent` and caches one `HdMaterialSurface` per id. Samplers: normal, ORM,
emissive and height on units 6-9 (one unit per map; GLSL 120 has no array
sampler), released by `hdReleaseMaterialMaps` on the way back out.

**The lit path is one term sum** (`HdShaderSource`):
`(ambient * 0.75 + sky * max(facingUp, 0) + bounce * max(-facingUp, 0)) * occlusion`,
plus `sunColour * max(dot(normal, sunDirection), 0) * shadow`, the anti-sun, the
four point lights, the carried and room terms, caustics, a roughness glint and
the emissive map. "Unlit" is the same program with `uLightingModel = 0`, so the
stock fixed-function look is untouched at defaults. `sunOcclusion()` is gone:
occlusion comes from the material's own map, and a draw without one keeps
occlusion 1 instead of reading the sun map's depth.

**Area lighting** (`HdAreaLight`, from `environments.json`): the environment's
sun and ambient colours multiply the sun and ambient terms, gated by the
`ENVIRONMENT_LIGHTING` row **and** an active area light, so the off value is the
multiplicand 1 and nothing else moves.

Evidence from the real-GL proof (this machine,
`NVIDIA GeForce RTX 4070/PCIe/SSE2 | GL 4.6.0 NVIDIA 621.01`):

- `[hd-model] material maps: 200 materials, occlusion material 322 (3x3 max red 109), relief material 228 (3x3 min tilt 120)`
- Occlusion, the same fragment with and without the map:
  `[hd-model] occlusion material 322 red 109: mapped rgb(37,37,37), plain rgb(38,38,38), row off rgb(51,51,51)`.
- Normal map, a sun 60 degrees off the face:
  `[hd-model] relief material 228 tilt 120: mapped rgb(0,0,0), plain rgb(76,76,76), row off rgb(89,89,89)`.
- Area tint:
  `[hd-model] area: untilted rgb(76,76,76), tinted rgb(76,0,0), row off rgb(76,76,76)`.

**Differences this pass records rather than hides.**

1. **Height is closed as a difference, not as byte parity.** 634HD keeps the
   height in the packed map's alpha and marches the uv by it; this client does
   the same (see the last section). Two differences remain and are recorded
   there: the march steps along the eye-space view lean instead of rotating that
   lean into the tangent frame, and the PNG fallback for a set with no blocks
   reads the alpha of the file `materials.json` names.
2. **Map coverage is closed.** All 39 map sets the build ships decode from their
   BC5 normals and BC7 packed maps at 256 square; no shipped set falls back to a
   PNG, and the other 151 declared sets have no map files to read (upstream
   binds nothing there either).
3. **The area light does not move the sun.** Its own pitch and yaw are not
   applied; the scene's sun direction stands, and only the tint multiplies.
4. **No linear-colour ceiling.** `15a94b8464` is not implemented: the term sum
   clamps the final colour as the fixed pipeline does, and nothing caps
   colour x light.

**Tests.** `HdLightingModelGlTest` (three pixel proofs: occlusion, relief and the
area tint, plus the row-off case), `HdMaterialSurfaceTest`,
`HdMaterialTexturesTest`, `HdAreaLightTest`, `HdDrawRequestLightingModelTest`,
and the updated `HdShaderSourceTest`, `HdLightingPipelineTest`,
`HdFeatureSupportTest` and `HdOptionsModelTest`. `HdAtmosphereRealGlTest`'s AO
proof now asserts that the row does **not** read the shadow map.

## Stage 2, slices 1 and 2 continued — map coverage and the height map (this pass)

**The two differences the last section recorded are closed.** Every material in
`materials.json` whose map files exist in the shipped resources binds real
texture data on the lit path, and the height map now moves the uv as upstream's
shader does.

**Map coverage.** `materials.json` declares 190 map sets. The build ships files
for 39 of them (`normal.png` plus `normal.bc5`, `orm.png` plus `orm.bc7`, at
level 0 256 square, 87,408 bytes with the mip chain), and all 39 bind the decoded
blocks. The other 151 name maps this build ships no files for: neither upstream
nor this client binds a map there, so they are counted separately from failures.
Coverage from the run:

`[hd-maps] 190 declared map sets: 39 bind decoded maps, 151 name maps this build does not ship, 0 unresolved; normal blocks 39 / png 0, orm blocks 39 / png 0, emissive blocks 1 / png 0, height packed 39 / png 0; 256 square`

`HdMapBlocks` is the translation of upstream's own CPU fallback, not a new
reader: `HdMaterialMaps.kt:282` decodes with `decodeBc5`/`decodeBc7` where the
driver cannot sample the blocks, and this client is always in that case because
jaggl samples RGBA only. BC5's two channels are the normal's xy (the shader
rebuilds z); BC7 mode 6 is the one mode the shipped tool writes. The decode is
checked against the PNGs it was made from: mean channel error 2.16 (normal xy)
and 3.43 (packed rgb).

**The height map is implemented, not decode-only.** Upstream's evidence, from
`.634-client-ref`:

- `graphics/hd/scene/HdMaterialMaps.kt:272-302`: "the height travels in the packed
  map's alpha" — the BC7 alpha is copied into `heights[]`, and a PNG set keeps
  its height from there too ("a set at PNGs keeps its height, else it is flat").
- `HdMaterialMaps.kt:306`: the per-material uniform is
  `if (m.height != null) m.parallax else 0.0f` — a depth with no height is zero.
- `resources/634hd/shaders/hd_material.glsl`, `hdParallaxUv`:
  `vec2 step = view.xy / max(view.z, 0.25) * depth / 6.0;` then
  `for (int i = 0; i < 6 && current < height; i++) { p -= step; current += 1.0 / 6.0; height = 1.0 - texture(uHdOrmMaps, vec3(p, layer)).a; }`.

Translated here: `HdMaterialTextures.resolve` takes the packed map's alpha where
it varies and otherwise the alpha of the map `materials.json` names (its
`height`); `HdMaterialSurface.of(..., parallax)` sends 0 unless a height texture
is bound (the same `if (m.height != null)` gate); `HdShaderSource.materialUv()`
is the same six-step march, gated on the height row and a positive depth. The
proof asserts on the same fragment with the authored depth and with zero:
`[hd-model] height: material 63 parallax 0.04 at 112,24 tilts 29/120` — the probe
texel has height alpha 0 (so the march runs all six steps) beside a normal that
turns 29 to 120 units — and `[hd-model] height: material 63 parallax 0.04,
authored rgb(0,0,0), flat rgb(48,48,48)`. Zero the depth and the pixel is the
face-normal shade; the height texture is what moves it.

**Remaining differences, recorded rather than claimed away.**

1. The march direction. Upstream rotates the view lean into the surface's tangent
   frame (`normalize(-viewPos) * tbn`); this client's shader has no TBN matrix in
   scope and steps along the eye-space lean (`view.xy / max(view.z, 0.25)`). On
   the axis-aligned quads the proof uses they agree; on a face whose tangent
   frame is rotated the offset direction differs.
2. The PNG fallback path. For a set with no blocks, upstream still reads the
   height from the packed map; this client reads the alpha of the file
   `materials.json` names. No shipped set exercises that path (all 39 have BC7),
   so it is untested content, not a proven equivalence.
3. The parallax is not used by the interface or the fixed-function paths: both
   draw through `materialUv()` as the terrain and model shaders do, but only the
   term-sum program samples the height.

**Tests.** New: `HdMapBlocksTest` (four proofs: BC5's two channels from the
block's own end points, a refused short/odd block, BC7 mode 6's end points and
indices, a refused other mode), `HdMaterialCoverageTest` (the coverage line and
"0 unresolved" with every bound normal/ORM from blocks), and three
`HdMaterialTexturesTest` cases (the shipped set resolves blocks, the decode
matches its PNG, the height is the packed map's alpha). `HdLightingModelGlTest`
gained the block-only and height proofs; the harness reads the pixel where the
off-axis quad's centre projects, which is what makes the parallax probe work.

## Texture-state hygiene and replacements (this pass)

**A state bug, found by a proof.** `OpenGl2DTexture`'s constructors bind through
`setActiveTexture`, which acts on the *selected* unit and its cache entry. Every
first-use material-map upload therefore happened with unit 0 selected - the unit
the draw's own stock texture sits on - so the primitive sampled the packed map
instead of its art: no environment cube (flat, non-shiny), the map's height as
alpha (see-through), no stock colour. The atlas page build already saved and
restored unit 0; nothing else did.

**One seam for every upload.** `GlToolkit.hdCreateTexture(...)` saves the selected
unit and unit 0's entry, builds the texture on a material unit, and restores
both; `HdMaterialTextures.texture()` uses it. `hdBoundTexture(unit)` reports the
cache entry the stock path trusts, which is what the proof asserts on rather than
a raw `glGetIntegerv`: the two must agree, and their disagreement was the bug.

**The combine constant was wrong, and it hid the lit path from textured draws.**
`HdLightingGate` compared `GL_COMBINE_RGB` against `GL_MODULATE = 7681`, but 7681
is `GL_REPLACE`; `GL_MODULATE` is 8448, which is what `GlToolkit.method1900(0)`
sets for an ordinary material draw. Every textured primitive was refused the
per-pixel program while every untextured one bound it. Fixed here: the gate
constant, the six harnesses that had copied 7681 as "modulate", and
`HdReflection.MODULATE_ENVIRONMENT` (1 -> 0: material texture effect 1 resolves to
`GL_REPLACE`, so only effect 0 draws may be taken from the stock specular pass).

**Replacements are wired, not merely decoded.** `HdTextureReplacements.applied` is
the one gate: off (or 592HD off, or no art) returns the caller's own array by
reference; on returns `combine(stock, art)`, the art's colour over the game
texture's alpha. Both the stock upload (`OpenGLTextureCache.method1549`, ARGB
only) and the world page's cell art (`HdMaterialArt`) call it.

Evidence (`NVIDIA GeForce RTX 4070/PCIe/SSE2 | GL 4.6.0 NVIDIA 621.01`):

- `[hd-replace] row off: plain rgb(60,60,60) alpha 128, applied rgb(60,60,60) alpha 128`
- `[hd-replace] material 63 texel 54,6 art 0xFFFFFFFF vs stock 0x803C3C3C (delta 195)`
- `[hd-replace] row on: stock rgb(60,60,60) alpha 128, replaced rgb(255,255,255) alpha 128, art rgb(255,255,255) alpha 128`
- `[hd-model] upload state: material 70 unit 0 kept, textured draw rgb(40,0,40)`

**Not claimed.** These harnesses draw quads, not locs: which live reflective
materials use effect 0, and so gain the lit program, is a game-session question.

**Run.** `./gradlew :compileClientJava test` -> **934 tests, 5 failed, 1 skipped**;
the five are the four `LaunchConfigTest` host cases (the local
`DEFAULT_HOST = "127.0.0.1"`) and `HdFrameContractGlTest`'s `panelPixels = 156`.

**Tests.** New `HdTextureReplacementGlTest` (two pixel proofs), three
`HdTextureReplacementsTest` gate cases, `HdLightingModelGlTest`'s eighth proof.
Updated: `HdLightingGateTest`, `HdLightingPipelineTest`, `HdLightingRealGlTest`,
`HdToolkitLightingGlTest`, `HdAtmosphereRealGlTest`, `HdSunShadowGlTest`,
`HdDrawRequestLightingModelTest`, `HdFrameContractGlTest`, `HdGroundAtlasGlTest`,
`HdReflectionGlTest`, `HdReflection`, `HdFeatureSupportTest`.

## Shadow caster rules (this pass)

Two of 634HD's caster tests, translated to what a fixed-function depth pass can decide.

**An opaque material's texture alpha is no cut-out (`13985ed2ef`).** A material drawn opaque
keeps a specular or reflection effect's strength in its texture alpha, and the deob already
names those two effects: `Class133.method3349` is false for slots 1 and 7.
`ShadowCutout.testsCasterAlpha` routes `GlModel.hasAlphaMaterial` through it, so a caster
whose alpha is a strength casts its full silhouette instead of being discarded under half
(the King Black Dragon's scales sit at alpha 0..70; before this they cast nothing and only
the wing bones showed).

**A water draw and an additive or modulating effect cast nothing (`50261e493e`).**
`ShadowCasterGate.casts(waterDraw, blendMode)` is consulted at
`GlToolkit.hdRecordShadowCaster` with the toolkit's own state: `hdWaterActive()` and the blend
cache `anInt2316`, where mode 1 is `SRC_ALPHA, ONE_MINUS_SRC_ALPHA` (an ordinary translucent
draw keeps its silhouette) and modes 2 and 3 are additive and modulating. Refused casters are
counted - `hdShadowCastersSuppressed()`, reset with the pass counters - and *why* is kept
apart: `hdShadowCastersWaterSuppressed()` and `hdShadowCastersEffectSuppressed()` split the
total, and `hdLightingStats()` prints `suppressed=N (water=W effect=E)`, so a missing shadow
names the rule that refused it rather than only that something did.

Evidence: `ShadowCutoutTest` "a strength mask in the alpha channel is not a cut-out" (six
cases: slots 0 and 2 test, slots 1 and 7 do not, no alpha channel never tests);
`ShadowCasterGateTest` "a water draw casts nothing, whatever it is blended by" and "only an
additive or modulating effect is no occluder".

**Not claimed, and why.** `d68827a1ed` (a moving draw's face whose normal points at the
ground) and the per-fragment half-opaque discard in `50261e493e` both need the depth pass to
carry the draw's normals, colour and texture coordinate into a fragment test; this pass is
fixed-function and has none of them, so both stay out rather than being approximated.
`50261e493e`'s flat-decal rule (a retained model under 24 units casts nothing) needs a
scene-unit vertical extent `GlModel` does not expose - its bounds shorts are the sun-slope
footprint, not a height. `0993a48bb1` (a cave's hidden upper plane) needs the object's plane
at the record seam, which the model path does not carry.

**Water row.** `WATER` now *is* upstream's `client.hd.water`: Off, On and Custom are values
0, 1 and 2, and the port-only 592HD water row is gone (its indices were the same, so no saved
value changed meaning and no preset default moved). Custom spends upstream's third value on
this port's surface program, because the client has no water-body pass to draw 117HD's
surface with.

## A reflective material's alpha is a strength mask, not its shape (this pass)

**The bug was in the fragment's output alpha.** A reflective surface drew
see-through. The lit fragment ended with `gl_FragColor = vec4(colour, albedo.a)`,
and for the two effect slots whose stock pass keeps a *strength* in the texture
alpha (`Class133.method3349` is false for slots 1 and 7, the specular and
reflection effects) `albedo.a` is 32 out of 255 or less, not a hole. Handed to the
fixed-function alpha test or to `SRC_ALPHA` blending, the surface's own art then
cut it away or made it nearly invisible.

**The rule.** `HdReflection.isStrengthMask(effectSlot)` names those two slots and
`HdReflection.alphaIsCoverage(effectSlot, hasAlphaChannel)` says when a sampled
alpha is shape at all; `ShadowCutout.testsCasterAlpha` already delegates to the
same rule for casters. The material bind (`GlToolkit.method1926`) records the
answer in `hdAlphaCoverage`, `hdBeginModelLighting` turns it into the
`HdDrawRows.Row.ALPHA_COVERAGE` row, and the program uploads it as
`uAlphaCoverage`. The fragment now writes
`vec4(colour, uAlphaCoverage != 0 ? albedo.a : 1.0)`: a strength mask leaves the
surface opaque, while a coverage alpha still reaches the framebuffer exactly as
before. The decision is made at the bind rather than from the material id
`hdBeginModelLighting` receives, because a model draw may pass `-1`; a bind with
no material at all returns the row to coverage.

Evidence: `HdReflectionGlTest` "a strength mask in the texture alpha never cuts
the surface away" draws a textured reflective material (slot 1, art at alpha
32/255) under the production draw with `GL_ALPHA_TEST` / `GL_GREATER 0.5`, and the
same geometry and art with a coverage-alpha material (slot 0) as the control:
`[hd-reflect] alpha test on: 2704 px of a strength-mask surface, 0 px of a
coverage-alpha surface, bound true/true`. The reflective surface keeps every
pixel and the control is cut away, so the alpha test is live and the surface is
no longer see-through. Supporting cases: the truth table in `HdReflectionTest`
(slots 0 and 2 test, 1 and 7 do not, no alpha channel never does), the gated
output line in `HdShaderSourceTest`, and the upload in `HdLightingPipelineTest`.

**Not claimed.** Only the two effect slots the deob names are treated as strength
masks. A material keeping a strength under some other slot still follows the
coverage rule, and it is a live session - not these quads - that would show
whether any do.

## Authored replacement art is filtered, and the atlas cannot be mipmapped (this pass)

**Why replacements read as grain.** `HdTextureReplacements.decode` scaled the
authored albedo to the stock texture's size with nearest sampling - one source
texel in every N - so a 512px authored grass, stone or fur texture arrived at 64
(or 128) as aliased noise. Mipmapping cannot undo that: the noise is level 0.
Downscaling is now a box filter, every source texel a target texel covers,
averaged, with the average weighted by alpha so a transparent neighbour cannot
darken a texel; upscaling, which no shipped art needs, still fills the nearest
source texel's block.

**Why the atlas has no mip chain, and must not.** The world page is the one
world texture the stock cache does not build, so it was also the one the Mipmaps
and Anisotropic rows never reached. Giving it the chain was tried here and
reverted on pixels, not taste: `HdGroundAtlasGlTest` failed both
"a two-material tile blends its own pair" (mate colour 0 px, tile mean
`rgb(219,40,0)` instead of the mate's yellow) and "a terrain draw with no normal
array still draws its tile" (`rgb(37,23,10)`, no red texel at all) because cells
share one chain, so a coarse level averages across a cell edge toward its
neighbour - often an empty cell. Upstream mipmaps these because it uploads them
as a texture array, one layer per material; an atlas cannot. The page stays
level-0 linear, `HdWorldAtlas.INSET`'s doc no longer claims mip margin, and
`GlToolkit.hdGroundPageMipmapped()` exists so a later "helpful" chain breaks a
test instead of the picture.

Evidence: `HdTextureReplacementsTest` "downscales authored art with a box filter"
(a 4x4 black/white checker becomes mid grey, where point sampling returned one of
the two tones) and "a transparent neighbour does not darken the average"
(premultiplied weighting: alpha 64, colour pure red).

**Not claimed.** The box filter is not gamma-aware and is not a mip chain: it
fixes the aliasing the authoring-to-64 step introduced, not the aliasing of a
64px texture minified in the distance, which is the Mipmaps row's job on the
stock path. Nor does it replace the material maps (normal/ORM), which still come
from the game's own art.

**Run.** `./gradlew :compileClientJava test` -> **943 tests, 5 failed, 1 skipped**; the
five are the four `LaunchConfigTest` host cases (the local
`DEFAULT_HOST = "127.0.0.1"`) and `HdFrameContractGlTest`'s `panelPixels = 156`, and the
skip is the optional glslang-validator proof. The atlas guard prints
`[hd-atlas] mipmaps row on: page builds 1 at material 204, page mipmapped=false`.

## Water parity: an opaque surface, one sky, and the Caustics row (this pass)

The Water row shipped four programs, but three things 634HD's water does were
missing on screen, and each had a concrete cause in this port:

- **It was see-through.** All four fragment stages ended
  `vec4(..., gl_Color.a)`, so the stock water blend
  (`SRC_ALPHA/ONE_MINUS_SRC_ALPHA`, the cache's blend 1) mixed the terrain under
  the waves back through the surface. Stock 592 water is a translucent overlay;
  634HD's is a composed surface (body, sky, sun). Every stage now writes `1.0`,
  so the surface is opaque while the blend state stays the stock one.
- **Only one water type reflected.** The cube was bound only for effect slots 8
  and 9, and only the reflective and breaking fragments declared a sampler.
  634HD reflects the sky on every water surface, so the standard and custom
  fragments now declare `uEnvironment` and `uReflection` (1 when a cube is
  bound, 0 when the frame has none, where the sheen stays the fog band's
  colour), and `hdBeginWater`/`hdWaterMaterial` bind the cube for every water
  material, not only the two stock types.
- **The Caustics row never reached the water.** `HdCaustics` was ground-only.
  The custom surface now carries 634HD's own `hdCaustics` net over the scene position,
  lit by `uCaustics` (the Caustics row as a 0/1 strength) on the water clock, so
  the row's off value is exactly the frame before it.
- **Water textures skipped the authored art.** `OpenGLTextureCache.method1549`
  applied `HdTextureReplacements.applied` only on the ARGB (6408) path; RGB
  materials, water among them, uploaded the game's art. The replacement now runs
  on both paths (an RGB upload has no alpha to keep).

Evidence (this run, `NVIDIA GeForce RTX 4070 | GL 4.6.0`):

`HdWaterRealGlTest`:
`[hd-water] opaque surface: 0 centre px differ across a black/red backdrop; centre rgb(215,244,255)`
(a translucent quad, vertex alpha 96/255, under the stock blend - with the old
alpha the backdrop mixed in);
`[hd-water] reflective vs calm differ in 0 px; calm frames across a sky change differ in 0 px; calm avg rgb(58,77,156); green sky calm avg rgb(58,136,96); blue sky reflect avg rgb(58,77,156); green sky reflect avg rgb(58,136,96); reflective draws 2`
- the standard and reflective surfaces now follow the same sky, channel for
channel. The `differing` count is red-only and neither test cube carries red,
which is why the proof asserts on the channel means, not on that count;
`[hd-water] caustics on the custom surface: 65536 px; brightest 198 -> 255`;
`[hd-water] custom vs standard differ in 59494 px; standard again differs in 0 px; custom draws 1; centre standard rgb(211,240,255), custom rgb(230,255,255)`.

`HdWaterSourceTest` asserts all four stages write `1.0` and none reads
`gl_Color.a`; `HdWaterPipelineTest` asserts `uReflection=1.0` with a cube and
`uReflection=0.0` without, `uCaustics=1.0` only on the custom surface, and the
palette byte for byte against the upstream WATER type.

**The caustic web and the custom palette are upstream's, not this port's.**
The reference is 634HD's own source, `2011Scape/634-client`, and both copies are
also in this repo under `src/client/src/main/resources/634hd/`, so the
transcription can be checked without the network:

- `634hd/shaders/hd_material.glsl`, `hdCaustics(vec2 p, float t)` - copied
  number for number into `CUSTOM_FRAGMENT`: the `q = p * 0.0042` scale, the two
  layers (`s = i * 1.37 + 1.0`, each turned `i * 0.65` radians), both warp steps
  (`* 1.1` then `* 0.35`), the `pow(1.0 - abs(a), 7.0)` line sharpening and the
  `total * 0.5` return. The same file's caller applies it on the seabed as
  `color.rgb += color.rgb * light * 0.55 * (sunUp ? 1.0 : 0.4)` (`ff.frag`), so
  the custom surface applies it the same way - a share of the light already on
  the surface - with `uCaustics` standing in for the row's gate.
- `634hd/data/materials.schema.json`, the `water` block: its documented WATER
  type defaults are `surface` `#69809c`, `foam` `#b0a492`, `depth` `#00758e`,
  `opacity` 0.5, `specular` 0.5, `gloss` 500, `normalStrength` 0.09, `fresnel`
  0.85, `duration` 1.0. The two colours this surface has uniforms for are now
  those bytes over 255: `HdWaterPipeline.DEFAULT_DEEP_COLOUR` is `#00758e`
  (0x00, 0x75, 0x8e) and `DEFAULT_SHALLOW_COLOUR` is `#69809c` (0x69, 0x80,
  0x9c), replacing this port's invented near-black and pale-cyan pair.

`HdWaterSourceTest` pins the function's constants and shape;
`HdWaterPipelineTest.customPalette_shouldBeTheUpstreamWaterType` asserts the two
colours byte for byte.

**Still this port's own.** The custom surface's composition around those values
is not upstream's `hdWaterSurface`: the row now samples the normal and foam maps
(see *Water texture and material-map grain* below), but not the flow map or the
water type block, and its reflection is the client's own cube; and the row has
one surface, not the schema's per-texture types, so the swamp water's
override (`surface` `#172114`, `depth` `#29521a`, `fresnel` 0.3, `flat`) is not
applied. Everything else "exactly like 634HD" means here is still the measured
behaviour above, not pixel parity, and the breaking band still substitutes the
row's crest measure for the stock 3D normal volume, as documented.

**The RGB upload path has its own pixel proof.** The replacement widening was
first covered only by unit tests, because the water harness binds its texture
directly. `HdTextureReplacementGlTest` now builds the texture through the stock
cache itself, with a source that asks for the colour-only path (`aBoolean640`,
effect slot 0), and draws it:
`[hd-replace] RGB upload: stock rgb(60,60,60) alpha 255, replaced rgb(255,255,255) alpha 255, art rgb(255,255,255) alpha 255; source rgb calls 1, argb calls 0`.
The call counts are half the proof - one `getPixels`, no `getPixelsArgb`, so the
branch taken is the RGB one - and the drawn texel is the authored art over the
game's flat grey. An RGB texture has no alpha channel, so its drawn alpha is the
sampler's 1.0 rather than the stock mask an ARGB upload keeps.

**Run.** `./gradlew :compileClientJava test` -> **960 tests, 5 failed, 1 skipped**;
the five are the four `LaunchConfigTest` host cases (the local
`DEFAULT_HOST = "127.0.0.1"`) and `HdFrameContractGlTest`'s `panelPixels = 156`,
and the skip is the optional glslang-validator proof.

## Water texture and material-map grain (this pass)

**Water texture is the shipped art.** 634HD's water is not a colour with a tint: it ships
`water_normal_map_1.png` and `water_foam.png` under `634hd/materials/flow/`, and its
`hdWaterLayers` scrolls the normal map in three layers at 3, 5 and 7 tiles a repeat, each on
a 30, 24 and 20 second period. This port shipped both files and sampled neither, so the
surface read as an untinted sheet. `HdWaterMaps` decodes them (alpha-weighted box filter,
never point sampling; the two shipped files are already 128 square, so for them the filter is
the identity and a larger replacement is what it protects), `GlToolkit.hdBeginWater` binds them on the material-map units the water
program never samples, and all four water fragments add
`hdWaterRipples(vWorldPosition.xz, phase) * uNormalStrength` to the surface normal plus
`hdShoreFoam` faded over the stock per-vertex depth (192 scene units, upstream's foam depth).
`uWaterMaps` is the flag, exactly like `uReflection`: a draw whose content lacks the art keeps
the wave-field surface instead of sampling empty units. The units are saved before the upload
and restored on `hdEndWater`, so the frame's GL state at every boundary is the stock frame's
(`HdFrameContractGlTest`, `HdWaterRealGlTest` state proof). Strength is upstream's WATER
`normalStrength`, 0.09.

**Map grain is the resample.** `HdMaterialTextures.decode` point-sampled the 512-square normal
and packed maps down to 256, keeping one texel of each 2x2 block; the slope it encodes is then
aliased detail the shader reads as grain on every surface. It now averages the source texels
each target texel covers, weighted by alpha, which is the filter the authored albedo
replacement (`HdTextureReplacements.decode`) has always used.

Evidence: `HdWaterMapsTest` (size and file names, absent art is null, a downscale averages
instead of keeping one texel, alpha preserved); `HdWaterSourceTest` (every fragment declares
and samples both maps, the water flag gates them, units 6 and 7); `HdWaterPipelineTest`
(the units are selected once per frame group and the flag is uploaded as the maps come and
go).

**The maps reach the surface, and their flag cannot go stale.** `HdWaterRealGlTest` draws the
same flat plane twice - once with the maps forced off, once with the content answering - and
requires the shipped art to move it: `[hd-water] maps: available=true, frames differ in 65536
px; hd water: available=true reflective=false draws=2 reflectiveDraws=0 maps=true`. Writing
that proof found a real bug: the flag rides in the per-program frame group, which
`HdWaterPipeline` skips while the clock stands still, so a draw that gained the maps at an
unchanged time kept the previous 0 and sampled the art without using it. `setWaterMaps` now
invalidates that group when the flag changes, and
`HdWaterPipelineTest.waterMaps_changeAtTheSameTime_shouldBeUploaded` pins the upload.
`HdMaterialTexturesTest.downscale_shouldAverage` pins the other half of the grain fix: one
white texel in a 2x2 block decodes as a quarter, where point sampling kept it white.

**The seam is now entered in game.** The client's own water selection is `GlToolkit.AA` (the stock water region's begin, effect slot 22 in `Class70_Sub1`/`Class70_Sub2`) and `l()` (slot 23, its end). `AA` opens the HD seam once per region and `l()` closes it; the seam's programs, its maps and its material resolution then run for the draws inside that region. An earlier per-layer wrap in `GlGroundLayer.draw` was removed: it opened the seam again inside an already-open region, closed it mid-region, and handed the terrain layer's fixed-function submission to the surface program, which raised `GL_INVALID_OPERATION` and left the tiles reading a chain nothing had filled. Terrain now always draws under the terrain program, so a water region cannot take the ground's textures with it.

**Grain was also the mip chain, not only the resample.** The material and water map textures
were uploaded through `hdCreateTexture`, which is deliberately outside the world-texture cache,
so they never received the Mipmaps/Anisotropic rows. Sampled under minification without a
chain, a 256-square normal map aliases into exactly the grain the resample was meant to remove.
Both now call `Class6.hdApplyTextureQuality(SIZE, SIZE)` on their own unit (never unit 0, which
holds the surface being drawn), so they follow those rows like every other world texture.

**A transparent block no longer becomes a black hole.** The alpha-weighted downscale fell back
to opaque black when every texel of a 2x2 block was transparent - 634HD's normal PNGs often
carry an unused all-zero alpha channel - and a normal or packed map reads black as a hole in
the surface. Such a block now keeps its own average colour with alpha 0;
`HdMaterialTexturesTest.transparentBlock_shouldKeepItsColour` pins it.

### Water units and the region entry (this pass)

**The map units were the bug behind the grain and the vanishing art.** `WATER_NORMAL_UNIT`
and `WATER_FOAM_UNIT` were 6 and 7 - the material normal and ORM units. A water layer also
binds its material, so the layer's own map bind wrote the terrain's normal map onto the unit
the surface was about to sample, and the next water bind wrote water art onto the terrain's
maps. Ripples read terrain normals (grain) and terrain sampled water art (missing textures).
The maps now live on 10 and 11, above every unit another pass names, and the toolkit's
per-unit cache is sized to the highest sampler rather than to the material range.
`HdWaterMapsTest.units_shouldNotCollideWithAnotherPass` pins the separation; it fails on the
old 6/7 pair.

**The seam's real entry point is the client's own water region, not the layer.** The stock
water pass is `GlToolkit.AA` (effect slot 22 in `Class70_Sub1`/`Class70_Sub2`: closes the
stock water selection, sets the water flag) and `l()` at slot 23 (clears it, ends the
effect). `AA` now opens the HD seam and `l()` closes it, once per region. The previous
pass's per-layer wrap has been removed: it opened the seam a second time inside a region
that was already open and closed it again mid-region, and it handed the terrain layer's
fixed-function submission to the surface program, which raised `GL_INVALID_OPERATION` and
left the tiles reading a chain nothing had filled - the ground textures that disappeared
inside and after a water region. Terrain now always draws under the terrain program;
`hdBeginGroundLighting` no longer declines while water owns the region.

**Proof through the real draw path:** `HdCausticsGlTest.waterRegion_shouldOpenTheSeamThroughTheStockSelection` draws a real `GlGroundLayer` outside a region (stock, no bind),
then calls the client's `AA`, then `l()`, and asserts the seam opens exactly once, the maps
are on their own units before the surface samples them, their min filter is
`GL_LINEAR_MIPMAP_LINEAR` (the mip chain, i.e. the grain path), and closing leaves no second
bind. Measured: `[hd-water-region] outside=0 opened=true binds=1 maps=true minFilter=9987
closed=true after=0`.

**Ground water now draws through the terrain-format surface.** The seam had no way to draw a terrain layer because the surface programs are written against the model path's vertex layout. It now compiles a variant - `HdWaterSource.GROUND_VERTEX` with the calm fragment stage - whose vertex stage reads the ground pass' own fixed-function arrays (`gl_Vertex`, `gl_Normal`, `gl_MultiTexCoord0`, `gl_Color`, the arrays `GlGroundLayer` submits through `method1934`), and whose only difference is the foam/depth term: a terrain layer has no texcoord z, so its stock depth byte becomes `uGroundDepth`. `GlGroundLayer.draw` opens it with `hdBeginWaterGround(depth / 255)` inside an open region and closes it in the same `finally`; the terrain program is skipped for that layer only, so every other terrain draw is untouched. Proven through the real draw path: `[hd-water-ground] outside=0/0 opened=true regionBinds=1 groundBinds=2 released=true maps=true minFilter=9987 lit=624 stillOwned=true closed=true pxVsStock=1248` - no bind outside a region, one region bind, one terrain-format bind, the maps on units 10/11 with their mip chain before sampling, pixels drawn, the region open across the layer's own program and closed after it, no GL error.

**The ground surface is the region's surface, not a fixed calm one.** The layer's own material is read the same way the model path reads it, so a reflective or breaking water type keeps its look on terrain, and a terrain-format program is compiled per surface (`GROUND_VERTEX` over the standard, custom, reflective or breaking fragment stage). The row's custom value and the Caustics row reach a ground layer exactly as they reach a model: `[hd-water-ground-custom] binds=1 surface=CUSTOM released=true regionStillOwned=true`, no GL error. The layer hands the program back as it leaves the draw (`released=true`); before that fix the terrain-format program stayed bound after the first water layer, so the rest of the region - and every later water draw - ran a program written for the ground pass and lost its texture.

**One ordering limit, recorded.** A water layer drawn *outside* a region enables the stock ARB water program, and binding a GLSL program over an enabled ARB program is `GL_INVALID_OPERATION`. The seam never does that in the client's own flow (region begin, layers, region end) and the harness now follows that flow; a caller that selected the stock effect and then opened the seam would have to disable it first. The lake path below is exactly that caller, and `method1926`'s guard - the stock selection is not run while the ground surface owns the program - is what keeps it out of the error.

**A water layer now owns its tile in both of the client's passes (the missing-surface fix).** The client submits its whole ground mesh twice per frame: the water pass (`Class62` → `AA` … `l()`) and then the terrain pass, over the same tiles at the same depth. `hdBeginWaterGround` was gated on an *open region*, so a lake in an ordinary region - which never opens one - ran the water surface in neither pass, and a lake that did get a region ran it in the water pass only, where the terrain pass replaced it. The layer now opens the seam itself when no region owns it (`GlToolkit.hdBindWaterUnits` + `HdWaterPipeline.begin` + `beginGround`, closed again in `hdEndWaterGround`, which releases the units and the program it bound), so both passes draw the surface and the water survives. Measured through the real layer, one tile, the client's order (`method1904`, layer, layer): `[hd-water-ground] lake two passes: binds=2 surface=STILL released=true closed=true px terrain=624 surface=624 differing=624`. The pre-fix measurement of the same frame was `[hd-lake] … on waterOnly=1248 … on both=1248 … differOnVsOff=0`: the surface drew, the terrain pass erased it, and the row made no difference to the final frame.

**Only what stands in water is taken.** `hdBeginWaterGround` also claimed *every* layer once a region was open, dry tiles included, so the region pass painted dry ground with the water surface - a draw that samples the water maps and not the ground atlas - which is the shape of the untextured squares reported beside the water. `HdWaterGroundGate.owns` now decides on two facts: the layer's material is a stock water type (`HdWaterGate.isWaterMaterial`, slots 3/4/8/9) or the client's own depth byte says water stands here (`HdGroundWater.submerged()`). A lake has only the first (an ordinary region leaves the byte at zero) and an underwater tile only the second (its material can be any ground type); either one standing is enough. Measured: `[hd-water-ground] dry two passes: groundBinds=0 waterBinds=0 px=624 vsStock=0` - the row makes no GL call for a dry tile and the frame is bit-identical to the row-off frame. `HdWaterGroundGateTest` pins the truth table and the slot set.

**The lake's body is the palette, and the net is every surface's (the white-water fix).** A lake tile's material texture is not water art: the stock look came from the water effect's own texture, and this port's surface replaces that effect, so unit 0 held the terrain material - white on a lake - and `body = texel * gl_Color * 0.55` drew a flat white sheet with only the texture matrix's scroll moving. Every stage's body is now 634HD's WATER palette (`uDeepColour` #00758e under `uShallowColour` #69809c, the colours the custom surface already carried), with the stock texel tinting only the shallow end, and `hdCaustics` moved into the shared prelude so the Caustics row's net is on all four surfaces instead of only the custom style. `HdWaterPipeline` uploads the palette and the strength with every bind, model and terrain, so a lake on the plain rows gets the body, the ripples, the cube and the net.

**A terrain draw that cannot reach the page says so, once.** `hdBeginGroundLighting` prints one line the first time a ground draw takes the atlas and one line the first time it keeps the stock draw (`material`, rows, tile map, page builds). Which of the two a session falls into decides where the untextured ground reported from the live client comes from, and neither the counters nor the pixel proofs distinguish them.

**The stock effect selection no longer runs over the surface.** Outside a region `aBoolean192` is false, so `method1926` would have enabled the stock ARB water program (`method5318(..., slot)`) right after the layer bound a GLSL surface - two enabled programs at once, `GL_INVALID_OPERATION`. Both of that method's water guards now also hold while the ground surface owns the program (`hdWaterGroundBound`), the region path's own condition. The harness's layers carry no material (`-1`), so this guard is held by inspection and by every proof's `GL_NO_ERROR`; a lake tile with a real slot-3 material still needs the game to exercise it.

**A cell the page cannot fill is given back, not left empty (the per-material missing tile).**
`HdMaterialMap.register` publishes a cell only when the art test says the page can fill it, but the
page is built later. `HdWorldPage.pixels` copied nothing for a material whose art did not answer at
that moment and left the already-published cell zeroed - and a zeroed cell is black with alpha 0,
which the ground pass' `GREATER` alpha test discards: no colour and no depth, so that one material's
tile loses its texture while the material beside it keeps drawing. That is the live shape of the
report - one tile without ground texture next to one with it, the missing ones all the same material.
The page now returns such a cell to the map (`HdMaterialMap.release`), which clears the id-map texel
and moves the version so the map texture uploaded by that same build no longer points at it, and the
material keeps the stock single-texture draw - the state before the atlas rows existed - until its
art streams in and a later draw registers it again. `HdWorldPageTest.pixels_shouldReleaseACellItCannotFill`
pins the release, the empty cell, and the "no cell" texel the shader reads as the stock path.

**A material the cache cannot describe still gets its art (the textureless-tile fix).**
`Js5TextureSource.getMetrics` returns the metrics array entry, and a 592 cache's placeholder material
(id 0 in the metrics file) has none - while its texture exists. `HdMaterialArt.art` treated a null
row as "no art", so the atlas refused that material, nothing was published, and the layer kept the
stock draw; for a material the cache cannot describe that draw binds no texture at all
(`OpenGLTextureCache.method1549`'s `getMetrics` would index a missing row), so the tile sampled an
incomplete texture and drew its vertex colour - flat, textureless ground between textured tiles,
which is the live report. `art` now takes the art anyway, from the alpha sampler: with no row there
is no `aBoolean640` asking for an RGB upload, so an alpha upload is what an absent row would have
made. `HdMaterialArtTest.art_shouldTakeAMaterialWithNoMetricRow` pins the art and the sampler, and
the one-shot line now names why a draw declined (`reason=` - rows off, untextured layer, no texture
for the material, no cell, no tile map, or the page built on that frame), so the next live run says
which case a missing tile is.

**The release is now a real-GL frame proof, in the client's own order.**
`HdGroundAtlasGlTest.unfillableCell_shouldFallBackToTheStockDraw` gives two fresh materials
art, draws each through the client's order (`method1904`, `AA(-1, 1583160, 40, 127)`, the layer's
`method1462`, `l()`, the layer's `method1462` again) so both reach the page, then takes the grass
material's art away and has a newcomer force a page rebuild: `grass atlas before 2, after 0, drawn
1352, vsStock 0, clear 2744 (stock 2744); neighbour atlas 2, vsControl 0, vsStock 0`. The grass
tile stops binding the page (its cell is no longer published), draws the stock single-texture draw
texel for texel - not a zeroed texel and not a hole, with exactly the stock frame's clear pixels -
while the neighbour keeps its two atlas binds and reproduces the frame it drew before the rebuild.
The live logs that motivated it: `[592HD] ground stock draw: material=408 ... pageBuilds=0` beside
`[592HD] ground atlas: material=66 ... atlasBinds=1`, so the page is serving real materials and the
lost tile is a per-material cell, which is what this proof covers.

**The client-order tile and the lake body are pixel proofs now.** `HdGroundAtlasGlTest.bothPasses_shouldKeepTheTilesArt`
draws one layer twice in the client's own order (`method1462`, `method1462`) and measures
`binds 2, 52 colours, red spread 195, clear 2744 (stock 2744)`: after the second pass the tile still
shows its art's whole range and has no hole the stock draw does not have.
`HdCausticsGlTest.lake_shouldReadAsWaterNotWhite` measures the lake through the same pair:
`lit=624 white=0 palette=0 moved=208 caustics=208 vsTerrain=624`. The body has no near-white texel
(the white sheet the report described), it moves between two frames of the same scene, the Caustics
row changes 208 px of it, and all 624 px differ from the row-off frame - so the tile is the water
surface and not the layer's terrain texture. The per-texel palette count is logged, not asserted:
this fixture's calm sheen is the fog colour (red here), so an absolute count would measure the
fixture's fog. The palette and the net themselves are pinned at the seam by `HdWaterSourceTest` and
`HdWaterPipelineTest`.

**Shadow caster rules, reachability.** Both ported rules are on the live record path:
`GlModel.hasAlphaMaterial` (called from the model's material selection) delegates to
`ShadowCutout.testsCasterAlpha`, and `GlModel`'s caster record calls
`GlToolkit.hdRecordShadowCaster`, which runs `ShadowCasterGate.refusal` and counts a refused
caster in the suppressed/water/effect counters beside `hdShadowCasters`. `ShadowCutoutTest`
and `ShadowCasterGateTest` cover the truth tables. No new shadow rule landed in this pass;
the three that need recorded normals and colour in a depth shader remain out of reach with
their reasons above.

**The sun map was centred on this frame's camera while its casters were placed with last frame's.**
The seam runs before the world draw it shadows, so the casters a pass draws are the ones the
*previous* world draw recorded - one frame of lag, documented and exact for a stationary object.
The volume, though, was rebuilt around the camera arriving with the seam
(`beginFrame(cameraX, cameraY, cameraZ)`), so casters placed from the old camera were drawn under a
volume centred on the new one: every shadow slides as the eye moves, an object that walked is drawn
a frame out of place - the "wrong entity's shadow" - and casters near the volume's edge drift out and
back as the eye drifts, which is the flicker and the pop-in. `SunShadowSubmission` now keeps the
camera its pending casters were recorded from and centres the pass on that one; a frame with no
pending casters still centres on this frame's camera, so the casters the draw about to run records
land inside the map they will be drawn into. `SunShadowSubmissionTest` pins both cases
(`beginFrame_shouldCentreTheVolumeOnTheCameraTheCastersWereRecordedFrom`,
`beginFrame_withNoRecordedCasters_shouldCentreOnThisFramesCamera`), and `volume()` exposes the volume
the pass is actually told about instead of leaving it inferred from `radius()`.

**The interface cannot inherit a scene program from a world draw that bailed.** Every world primitive
releases the lit program and its own sampler units when it ends, so the ordinary frame leaves nothing
behind - but a model or ground layer that returns between `hdBeginModelLighting` and
`hdEndModelLighting()` leaves the program bound with the shadow map, the reflection cube or the atlas
units still filled, and the interface drawn next is shaded and sampled by a scene program it never
asked for. `HdFrameSequence.endWorldDraw()` now closes the seam with `GlToolkit.hdEndWorldEffects()`,
which runs the same guarded release helpers and an `unbind()` that is a no-op when nothing is bound:
no GL call in a clean frame, and no HD state at the interface boundary in a dirty one.

**The screen-space rows stay at present time, measured rather than assumed.** Keeping bloom and the
tone curve off the interface means running them before the interface is drawn, so the pass was moved
into `endWorldDraw()` and measured on the real driver: `HdFrameContractGlTest` went from one
environmental failure to two and the scene came back black (terrain `rgb=255/0/0` to `rgb=0/1/2`,
same depth), with and without the compositor's own pass. The pass owns a captured frame that only
exists at present time, so it was reverted to `method4247()` and the reason is recorded on
`hdPostFrame()`. Excluding the UI there needs the world rendered into its own target - a pipeline
change, not a call site - and stands as the remaining gap.

**Ground tiles lost their texture because the material id was truncated twice.** The live log that
came back after the last pass was `[592HD] ground atlas: material=181 ... atlasBinds=1` beside
`[592HD] ground stock draw: material=408 ... pageBuilds=0`: two materials in one region, one on the
page and one refused. It is not two materials behaving differently, it is the id range. A 592
material id is 16-bit (`aShortArray107` is read as `& 0xFFFF`) and this cache uses 408, 181 and 66
in one region, but the page's map was `HdMaterialMap.MATERIALS = 256` wide and `register` refused
anything above it by arithmetic alone, and `HdTileMaterialMap` wrote each of a tile's two ids into a
**single byte** - 408 stored as 152. The fragment then looked the rewritten id up in the id map and
found another material's cell, or none: the wrong texture blended into the tile, or the tile fell
back to a stock draw the map had said was not needed. That is the ground bug, and it survives any
fix aimed at the art, the page or the alpha test, because the id never reaches the cell it belongs
to. The reference is the 634 client (`.634-client-ref`): it never indexes anything by material id at
all - `HdMaterials.kt` gives each drawn material a small slot (`table[at + 7] = materialMaps.slotOf(id) + 1`)
and `HdMaterialMaps` holds those slots - so no id range is baked into its shader.

This port keeps its id map, widened to the range the client actually uses:
`MATERIALS = 65536` with the id texture a **256 x 256 square** (`WIDTH`/`HEIGHT`), because no
texture this context allows is 65536 texels wide; the shader resolves id `n` at
`(n % 256, n / 256)` in one shared `groundCell()` helper, so `groundMaterial()` and
`groundMaterialFor()` cannot address different texels. `HdTileMaterialMap` now stores its two ids as
**16-bit, little endian, two bytes each** (`(primaryLo, primaryHi, mateLo, mateHi)`, same four bytes
per tile, same `SIZE x SIZE` texture), reconstructs them as `r*255 + g*65280` in the shader, and
derives the blend weight from "the two ids differ" - the rule `weight()` already stated - instead of
spending a channel on it, which is how the flag byte was freed for the mate's high byte. The
material map's 5 id-indexed arrays are 65536 entries (1.3 MB) and the id texture is 256 KB.
`HdMaterialMapTest.register_shouldAdmitMaterialsPastAByte` pins cell/texel 408,
`HdMaterialMapTest.atlasCoordinates_shouldHitEachIdsOwnTexel` pins the square addressing, and
`HdTileMaterialMapTest.idsPastAByte_shouldSurviveThePacking` pins 408/181 surviving the packing.
`GlToolkit.buildGroundPage` uploads the map as `WIDTH x HEIGHT`; the id texel for a material is the
same texel the shader resolves for it.

Not yet observed live: no in-game run is available here. The `[592HD] ground stock draw` line should
stop naming `reason=atlas has no cell for material 408` after this, and every ground draw's material
should report an atlas bind.

## Water: the 634HD surface, one body for all four styles

`HdWaterSource` had four fragment stages that each re-derived the surface (a cubed Fresnel, a fog or
cube sheen, a Blinn-Phong half-vector glint, a white foam tint). 634HD has one surface function,
`hdWaterSurface`, and the four stock water types are variations of it. The port now has the same
shape: `WATER_TEXTURE_GLSL` carries the WATER type's fields and one `hdWaterBody634`, and every stage
calls it. That is what keeps the styles from drifting apart, and it is where parity is enforced.

Ported, number for number from `.634-client-ref` (`hd_material.glsl`, `materials.schema.json`):

- **The water type.** `#define HD_WATER_DEPTH_RANGE 1536.0`, `HD_WATER_FOAM_DEPTH 192.0`,
  `HD_WATER_DEPTH_OPACITY 0.9`, `HD_WATER_REFLECTION_BEND 0.3`, `HD_WATER_MIRROR_SHARE 0.5`, and the
  WATER type's own defaults: opacity 0.5, specular 0.5, gloss 500, normalStrength 0.09, fresnel 0.85,
  duration 1, foam `#b0a492`. `HdWaterPipeline.DEFAULT_SHININESS` moves 96 -> 500 (the type's gloss),
  which is the change that makes the glint a small hard flare instead of a broad sheen.
- **Fresnel.** `clamp(mix(0.4, 1.0, (1 - facing) * 1.2), 0, 1)` replaced the `pow(1 - facing, 3)`
  (and the custom stage's `pow(1 - facing, 4)`). The reflection now arrives at the angle 634's does.
- **Reflection.** The area tint normalised to its brightest channel, at 0.05 / 0.45 / 0.8 by the
  Fresnel, mixed with the sky (the environment cube here in place of 634's mirrored scene map) at
  `HD_WATER_MIRROR_SHARE`, bent by `normal.xz * HD_WATER_REFLECTION_BEND`. `hdWaterTint634` is that
  gradient; the reflective type takes the cube outright.
- **Light.** The sun's diffuse at 0.5, a **view-based** glint `pow(dot(view, reflect(-sun, n)), gloss)`
  at the type's 0.5 specular strength (the half-vector lobe is gone), and the sky's light
  `max(n.y, 0) * fogColour * 0.5`. The world's up axis is +y in this client, so 634's `-n.y` reads
  `n.y` here.
- **Plain water darkening.** `base *= 0.75`, the rule for the types carrying WATER's fresnel amount;
  our one type is that type, so it always applies.
- **Foam.** The type's `#b0a492`, the upstream shaping `pow(max(1 - (1 - min(shore, 0.8)) / 0.7, 0), 3)`,
  the 192-unit reach, and its share of the alpha. The stock material texture is no longer sampled by
  the calm, reflective or custom stages: 634's water is the type's colour, not the ground material's
  art (which is usually white on these tiles - the flat sheet this row started with). The breaking
  stage still samples it, scaled by the material's own ripple scale.
- **Depth.** `alpha = max(opacity, foam, fresnel)` raised to `0.9 * smoothstep(0.3, 1, depth / 1536)`,
  and the type's depth colour laid under the surface by that alpha - absorption as depth instead of a
  flat darkening. The final pixel stays opaque (`alpha 1.0`), which is 634's *flat* form: our rows draw
  over the ground with no seabed pass to reveal, and translucent water over stock terrain is the
  see-through artefact this row exists to remove.
- **Animation period.** The three ripple layers' 30/24/20 s periods scale by the type's `duration`
  (1.0 here), so a material with a different period would not need a shader edit.

Proofs. `HdWaterSourceTest.surface_shouldBeTheUpstreamWaterType` pins every constant, the Fresnel
mapping, the gradient's stops, the quarter darkening, the foam colour, the sky term and the gloss;
the old assertions that pinned the cubed Fresnel and the half-vector lobe are replaced, not dropped.
`HdWaterRealGlTest` still compiles and shades all four stages on a real driver, and its map proof now
also shows the maps **scroll** with the wave field at zero (2746 px move with them, 0 without), so a
sampler on an unused unit cannot pass it. Two proofs were re-measured because the surface changed:
the glint is a few pixels wide at gloss 500, so the sun sweep reads a brightness-weighted lobe centre
instead of the single brightest column, and the lobe's fall-off is read on summed channels instead of
red (the 634 tint moves blue and green). Neither assertion was loosened; the thresholds that were
retuned (`distinctColours > 8`, map presence `> 2500 px`) are anti-vacuity guards, and are documented
as such where they live.

Not yet ported, so not claimed:

- **Flow-map advection** was on this list; it is ported now, in the section below.
- **Ripple-size crossfade** while an environment transition runs (`hdWaterClock.y/z`); this port has no
  environment clock on the water path.
- **Per-material water types** (`hdMapD[slot].w` -> `hdWaterType`) and **point lights on water**
  (`hdPointLights` / `hdPointHighlights`) were on this list when this section was written. Both are
  ported now: see the next section.
- **The mirrored scene share.** 634 mixes `HdReflections`' mirrored scene at 0.5 where it was drawn;
  this row mixes the environment cube at the same share. The gradient and share math match, the image
  source does not.

Not observed in game: no run into the world is available in this session. What is verified is the
full suite (981 tests, only the 25 known environmental failures) and the real-GL water proofs above.

## Water: per-material types and the frame's point lights

634HD picks a water type per material (`hdMapD[slot].w` -> `hdWaterType`, `hd_material.glsl`) and
lights the surface with the frame's point lights as well as the sun. Both are ported, off the same
one body: no new program, no new draw, and the row-off path is untouched.

**Types.** `HdWaterType` is the resolved type: `DEFAULT` is 634's WATER (surface `#69809c`,
foam `#b0a492`, depth `#00758e`, opacity 0.5, specular 0.5, gloss 500, normalStrength 0.09,
fresnel 0.85, duration 1, hasFoam, not flat), and `of(HdMaterialDefinition.Water)` is a material's
`water` block with the schema's clamps. The type rides on `HdWaterRequest` and is handed to the
program by `HdWaterPipeline.uploadWaterType` as three packed uniforms - `uWaterSpec`
(specular, fresnel, opacity), `uWaterShape` (hasFoam, duration, plain) and `uFoamColour` - plus the
existing `uShininess` (gloss), `uNormalStrength`, `uDeepColour` and `uShallowColour`, so the four
styles still share `hdWaterBody634` and `hdWaterRipples`. `GlToolkit.hdWaterType(material)` resolves
the type from `HdContent` at the seam that already has the material id; a material with no water
block, or no content loaded, gets `DEFAULT`, which is what this port drew before.

**Point lights.** `GlToolkit.hdBindWaterUnits` hands the water seam the frame's live light count
(`HdWaterPipeline.setPointLights`, clamped to `MAX_POINT_LIGHTS = 4`), and `uPointLightCount` is the
only new uniform: the light data itself is fixed function's `gl_LightSource[2 .. 5]`, the same rows
`HdShaderSource` reads, already uploaded by the stock light copy. `hdWaterPoints634` adds each
light's diffuse with the stock constant/linear/quadratic attenuation and its glint mirrored about
the surface at the type's gloss and specular strength - the same lobe the sun glint uses.

Proofs. `HdWaterTypeTest` pins the defaults, `of(...)`'s clamps, `plain()` and `sameAs`;
`HdWaterSourceTest` pins the four uniform lists (17/16/17/22), the packed uniform values and the
point-light loop; `HdWaterPipelineTest` pins the uploads and the point-light count. One real bug was
caught by a pixel proof, not by those: `HdCausticsGlTest` measured a lake with **0 px** of movement
and **0 px** of caustic net, because the type was cached per *surface* - and a surface owns two
program objects (model format and terrain format), so a ground layer drew with every water uniform
still at zero. The cache is now keyed on the program object, and
`HdWaterPipelineTest.groundLayer_shouldGetTheTypeItsProgramHasNotSeen` pins it.

Not pixel-proved, so not claimed:

- **A non-default type on screen.** The harness cannot inject a material into `HdContent`, so a
  swamp's `#172114` surface, 0.8 opacity, 100 gloss and flat form are proven at the seam
  (content -> `HdWaterType` -> uniform) and not as rendered pixels. The one shipped `water` block is
  a swamp tile, so a type other than `DEFAULT` is what a live world would draw.
- **A lit point light on water.** The GL harness copies no point lights (`copyLights(0, new
  PointLight[0])`), so the point-light term is proven by the shader's wiring and the count uniform,
  not by a torch glinting on a lake.
- **Point-light specular shape.** 634's `hdPointHighlights` is a per-light highlight; this port runs
  the same view-mirrored lobe as the sun, at the same strength, because that is what the one body
  already computes. A hand-tuned per-light highlight is not claimed.

## Water: 634HD's flow-map advection

`hdWaterLayers` upstream samples a third water map, `water_flow_map.png`, and offsets every ripple
layer and the foam along it by `flow * 0.025`. The current port binds only the normal and foam maps,
so the surface ripples in place. It now binds the flow map on its own unit
(`WATER_FLOW_UNIT = 12`) and drifts with it:

- **The sample.** `hdWaterFlow634` reads the current at world `xz` through a repeat every 15 tiles
  (`-xz / (512.0 * 15.0)`), one frame per 50 seconds scaled by the type's `duration`
  (`uWaterShape.y`) - the same numbers as `hdWaterLayers`. It returns zero when the maps are absent,
  so the wave-field surface keeps its own motion instead of sampling whatever unit 0 holds.
- **The ripple layers.** Each of the 3, 5 and 7 tile layers adds that velocity `* 0.025` to its uv,
  so all three layers travel along the same current and no layer slides against the others.
- **The foam.** `hdWaterFoamTexel` samples the foam mask with the same velocity `* 0.025` on top of
  its 512-unit world uv and 12 second scroll: the foam floats on the water rather than sliding under
  it. All four fragments now call it, which also collapsed four copies of the same inline sample.
- **The bind.** `HdWaterMaps` decodes the third file, requires all three (a partial set is absent, so
  the wave-field fallback draws rather than a half-bound surface), binds it beside the others, and
  restores the unit on unbind like the other two.

Proofs. `HdWaterSourceTest.fragments_shouldDriftWithTheFlowMap` pins the sampler, the 15-tile /
50-second / 0.025 numbers, the flow term on the layers and on the foam, and the zero-current fallback
in every fragment. `HdWaterMapsTest.shippedFlowMap_shouldCarryACurrent` decodes the shipped art and
proves it carries a real current - 16373 of 16384 texels more than 8 off mid-grey, peak deviation
256, 9991 distinct velocities - so a blank or mid-grey map cannot pass. `HdWaterRealGlTest` still
compiles and shades every stage on the driver with all three maps bound, and its map-drift proof
passes. Full suite: 991 tests, only the 25 known environmental failures.

Not proved, so not claimed:

- **The current's own share in pixels.** The GL harness binds the shipped maps but cannot substitute
  a known velocity field, so the drift it measures is the three layers' scroll plus the current.
  The current's contribution is proved at the seam (the map's own statistics plus the shader's
  wiring), not isolated in a rendered frame.
- **The ripple-size crossfade** upstream runs while an environment transition is active
  (`hdWaterClock.y/z`). This port has no environment clock on the water path, so the current is
  sampled at one size.
- **`lava_flow_map.png`** ships in the content, but only the water surface samples the flow map here;
  lava is outside this row.
- **Live observation.** No in-game run is available in this session, so what a river's current looks
  like on screen is not reported.

## The water surface, lit and tinted like 634HD's

A live screenshot of a lake showed the surface as one flat cyan sheet with the procedural caustic net over it and nothing else: no ripple shading, no sky, no glint. Four causes, all in the water body the surface shares:

- **The sky light read an eye-space axis.** `hdWaterLight634` used `max(normal.y, 0.0)` where it meant the world's up. In eye space that component is the view depth, so the sky term collapsed whenever the water was looked at from above. It now takes the world's up, carried to the fragment as `normalize(vNormalY)`, and survives any camera.
- **The composite had no ambient and half the sun.** Upstream's `hdWaterSurface` sums `hdWaterEnvAmbientLight` + the sun + the glint + the sky + the point lights + `reflection * max(specStrength, 0.2)`. The port had `sun * 0.5 + glint + sky` and no ambient, so the body was dark and only the net had structure. `hdWaterLight634(normal, view, up)` now returns the frame's `gl_LightModel.ambient` (the engine's own ambient, uploaded by the stock light copy) + the sun row's diffuse + the glint + the sky on the up-facing share, and the composite adds `reflection * max(uWaterSpec.x, 0.2)`.
- **The reflection gradient was tinted with the depth colour.** `hdWaterTint634(uDeepColour, ...)` built the sky gradient out of `#00758e`, the *seabed's* tint, so every surface was cyan whatever the sky was. It is now the type's surface colour (`#69809c`), 634HD's own water colour until the area's `hdWaterTint` is wired (see below).
- **The net belonged to the seabed.** `hdWaterSurface` has no caustics term at all: upstream's net lights the ground under the water. The four surfaces no longer paint it - `uCaustics` and `hdCaustics` stay in the shared block for the terrain pass - which removes the "net over a flat sheet" look that was the whole complaint.

Two more, both visible on the same screenshot:

- **The Fresnel ignored how flat the view is on the surface.** 634HD raises it by `1 - dot(viewDir, up)`; the port now does too, so water seen edge-on mirrors the sky whatever its ripples do.
- **The terrain path's depth was 0..1 in a 1536-unit body.** `hdBeginWaterGround` hands `waterDepth / 255`, and the fragment compared it to `HD_WATER_FOAM_DEPTH` (192) and `HD_WATER_DEPTH_RANGE` (1536). Every lake tile therefore read as "at the shore": foam everywhere, the least opacity, and the depth colour filling in half the body. `GROUND_VERTEX` scales the byte to scene units (`* 1536.0`).

Proofs: `HdWaterRealGlTest.surface_shouldNotPaintTheCausticsRow` replaces the old net-on-surface proof and requires a zero-pixel difference with the row on and off; `HdWaterSourceTest.waterSurfaces_shouldNotPaintTheCausticNet` checks all four stages; `HdWaterRealGlTest.breaking_shouldDifferFromCalmAndReflective` now states the upstream fact that the calm tint and the reflective cube are different skies. `HdCausticsGlTest`'s lake fixture gained a 64-texel sky cube with bands, because with one flat colour in the cube (and again with a coarse gradient) bending the reflection changed nothing and a still surface passed - the proof now measures a moving body. Its net comparison is against the same clock as the caustics toggle; it previously compared a t=0 frame with a t=0.5 one and counted the water's own movement. Full suite: 991 tests, only the 25 known environmental failures.

Not proved, so not claimed:

- **Live observation.** Still no in-game run here. The harness's top-down frames at world scale show a rippled, sky-reflecting body instead of a flat sheet, but what a river looks like on screen is not reported.
- **The area's water tint.** Upstream's gradient is `hdWaterTint`, the environment's water colour pulled by `waterStrength`; `HdEnvironmentDefinition.waterTint()` is parsed but nothing consumes it yet, so the gradient uses the type's surface colour.
- **Environment sun and shadow on the water.** Upstream multiplies its sun by `hdEnvDiffuse(...)` and `hdShadow(viewPos)`; this surface reads the frame's `gl_LightSource[0]` and `gl_LightModel.ambient`, and still applies no shadow map to the waves (the existing deliberate omission).
- **The ripple-size crossfade** remains unwired, as the previous section says.

## The ripples scroll in three directions

634HD's `hdWaterLayers` does not scroll one pattern three ways along one axis: `uv1` is built from the **swapped** world uv and takes the frame **minus** the clock, while `uv2` and `uv3` take it **plus** (`hd_material.glsl`). The port advanced all three along `+x` with a per-layer `i * 0.37` offset that only shifted the pattern, so the water slid as one sheet whichever way the current ran - the "all flowing the same direction" report.

`HdWaterSource` now carries upstream's rule: `hdWaterWorldUv634` (`-xz / (512 * tiles)`) and `hdWaterFrame634` (`fract(seconds / period)`); the flow map sampled every 15 tiles through a 50 second frame added to **both** axes, so the current drifts diagonally as upstream's does; `uv1` with `.yx` and a minus frame, `uv2` and `uv3` with plus frames at 24 and 20 seconds, all offset by `flow * 0.025`; the tilt is `-sum * strength`, upstream's negation with the strength inside the function so a caller cannot scale it twice. The foam samples `fract(world / 512) + flow * 0.025`, the same current, instead of the invented 12 second scroll it had - which also moved the foam against the water.

The proofs had to learn what they were measuring:

- `HdWaterSourceTest.waterLayers_shouldScrollInDifferentDirections` pins the three uv rules and the absence of the `i * 0.37` offset, on all four fragments.
- `HdWaterRealGlTest.waterMaps_shouldReachTheSurface` used to pass because of that 12 second foam scroll: its flat blue sky made the composed surface insensitive to a ripple, so a full 30 second ripple period moved 108 px and 1.5 s moved none. The fixture now reflects a sky banded every texel, which a tilted normal has to change; the same 1.5 s moves 48388 px.

Full suite: 991 tests, only the 25 known environmental failures.
