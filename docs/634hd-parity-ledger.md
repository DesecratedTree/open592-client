# 634HD parity ledger — 592 client

Every row of the 592HD pane (`HdFeature`) as recorded below, with what actually
ships and where the evidence is. This is a ledger of *claims*, not a roadmap: a
row is only listed as proven where a test in this repository asserts it, and the
quoted numbers are from the recorded run; the **Currentness** note records what
changed after it.

## Parity verdict

**Parity is not achieved.** The 33-of-40 `READY` count is a **wiring count**: a
value reaches a branch or a uniform, not evidence that the feature matches
634HD. Of those 33, 6 drive a stock 592 flag and 1 (`LIGHTING_DETAIL`) is a
port-only control; the other 26 are renderer rows, most of them subsets of
their 634HD counterparts. Seven rows have no effect at all:

| Row | Status |
|---|---|
| Lighting model | PENDING_RENDERER |
| Soft particles | PENDING_RENDERER |
| Texture replacements | UNAVAILABLE |
| Water | UNAVAILABLE |
| Embers and sparks | UNAVAILABLE |
| Interface scale | UNAVAILABLE |
| Smooth interface | UNAVAILABLE |

Treat this document as a wiring inventory and a list of blockers, not as a
parity proof. The next pass has to implement those seven and replace the
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
latest checked-in result XML (97 classes, 2026-10-24 21:29) reports **844 tests, 0 failed,
0 skipped**. The pixel numbers quoted below were not regenerated for this
record; they remain evidence from the quoted run. The status columns and the
parity verdict are a wiring classification, not a fresh pixel-parity proof.

**Status counts (40 rows):** 33 READY — of which 26 are 592HD renderer rows,
6 drive a stock 592 flag, and 1 (`LIGHTING_DETAIL`) is a port-only control with
no upstream row — 2 PENDING, 5 UNAVAILABLE. This is a wiring count, not a parity
score; see the verdict above and the seven no-effect rows listed there.

`READY` here means exactly what `HdSupport.READY` means: the value changes the
game now, and only when the row's dependencies are on. It does not mean
"byte-identical to 634HD". The rows that drive a stock flag change 592's own
effect; this renderer reimplements nothing for them, and no pixel test of theirs
exists. Several renderer rows are also conditional on Per-pixel lighting or on
the atlas rows; the seven that have no effect at all are listed in the verdict.

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
| Lighting model | **PENDING** | Nothing. The value is stored so a preset survives; no shader reads it. | Blocker below: missing material content. Proof file: `MaterialType`. |
| Point lights | READY | The scene's lights evaluated per pixel, four slots, zeroed when off. | `HdLightingRealGlTest` "point lights add only when the row is on, through the uniform loop": `[hd-gl] point light on = rgb(51,115,51)`, `[hd-gl] point lights row off = rgb(51,51,51)`. |
| Carried light | READY | The strongest map light on the carrier's tile, re-anchored at the player. | `HdLightRealGlTest` "a carried light brightens near the carrier and falls to zero at its radius": `[hd-gl] carried, near = rgb(148,148,148) (expected 148), middle rgb(82,82,82) (expected 82), far rgb(51,51,51) (expected 51)`. |
| Room light | READY | An eight-by-eight chunk's own ambient, gained and tinted by the lights on its tiles, bounded to the chunk. | `HdLightRealGlTest` "a room light cannot leak into the region beside it": `[hd-gl] room: left rgb(127,51,51), right rgb(51,127,51)`. |
| Contact occlusion | READY | Ambient darkening read from the sun map with a wide-biased kernel. Stands in for 634HD's *baked* AO; the name says what it draws. | `HdAtmosphereRealGlTest` "ambient occlusion darkens contact and leaves a free fragment alone": `[hd-gl] row off, in contact = rgb(51,51,51)`, `[hd-gl] row on, in contact = rgb(23,23,23)`, `[hd-gl] row on, nothing occludes = rgb(51,51,51)`. |
| Distance fade | READY | The projection's outer band fades to the fog colour, reaching it at the far plane. | `HdAtmosphereRealGlTest` "distance fade leaves nearby geometry untouched and fades the far plane": `[hd-gl] near fragment, fade row on = rgb(255,255,255)`, `[hd-gl] one unit before the far plane = rgb(255,8,8)`. |
| Day and night | READY | A world clock scaling the stock light, fog colour and shadow survival; recolours the dome when Sky is on. It does not move the sun or its shadows. | `HdSkyGlTest` "day and night darkens the sky, and only when its row is on": `[hd-sky] fog: noon fogged #203040 horizon #203040, midnight fogged #070B0E horizon #070B0E`; `HdDayNightTest` "off is the identity, exactly - not nearly". |
| Environment light | READY | The ambient tinted by the sky above a fragment at the environment's level. Ambient only; there is no ground colour in this client's environment table. | `HdEnvironmentLightGlTest` "with the row on a surface facing the sky takes the environment's colour at its level": `[hd-gl] environment row on: red sky rgb(51,0,0), green sky rgb(0,51,0)`; `[hd-gl] environment row off: red sky rgb(51,51,51), green sky rgb(51,51,51)`. |

## World

| Row | Status | What ships | Evidence in this repo |
|---|---|---|---|
| Textures | READY, stock | 592's texture flag, pushed through the backend. | `HdSettingsTest` "a preset writes only the rows this client can apply today"; `HdFeatureSupportTest` "a READY row is either stock-backed or renderer-owned, never neither". No pixel line. |
| Stock ground blending | READY, stock | 592's per-tile material pin (`Class50.aBoolean289`), i.e. one material per layer. | `HdGroundTexturesTest` "the blend row's strength travels with the draw"; `HdSettingsTest` backend-write assertion. Not 634HD's blended ground — that is Terrain blend. |
| Terrain blend | READY | A tile's own two materials mixed through a scene-anchored noise mask, triplanar on steep ground. Needs World textures and Material maps; Off is strength 0. | `HdGroundAtlasGlTest` "a two-material tile blends its own pair through the tile map entry": `[hd-atlas] blend: differing=1352, mate colour 0 -> 624, means rgb(255,0,0) n=1352 / rgb(255,75,0) n=1352, repeat differs=0`. |
| World textures | READY | One 512×512 page of 64×64 cells, each layer selecting its cell through the material map, repeating at the layer's stock texture scale (one repeat per `textureScale` scene units — not per tile times it, which magnified the art 128× into a flat texel). A material the page cannot serve keeps its stock texture: the shader reports the miss and the draw multiplies the bound texture, instead of multiplying neutral white and leaving the vertex colour. Stock non-small materials upload at 128×128, so this is a downsampled repack, not byte-identical art; a 128-material working set also evicts (66 + 1 in the stress case). | `HdGroundAtlasGlTest` "each material's tile draws its own atlas cell, on the frame after the page is built": `[hd-atlas] cells: build frames bind 0/0 and differ from stock 0/0 px; atlas frames bind 1/1 and differ from stock 0/0 px; ...`; and "the atlas repeats at the stock texture scale": `[hd-atlas] repeat: 52 colours, red spread 195; build binds 0, atlas binds 1` (the old repeat gave 3 colours, spread 2). |
| Material maps | READY | One texel per material id, `(cell + 1, brightness, blend, 255)`, built from the client's `MaterialType` records. The shader reads the red cell id; green (brightness) and blue (blend) are packed and uploaded but not yet sampled. | `HdMaterialMapTest` "a texel packs the cell and both parameters into its channels"; `HdTileMaterialMapTest` "a two-material tile records both ids and a full weight". |
| Texture replacements | **UNAVAILABLE** | Nothing. | Blocker below: no replacement content. Proof file: `MaterialType`. |
| Water | **UNAVAILABLE** | Nothing; its one value (`Always`) is inert. | Blocker below: no mechanism. Proof file: `HdFeature` (row doc) and `HdStockTarget` (no water target). |
| 592HD water | READY | Four surface programs: calm, reflective (environment cube), breaking (the material's own effect parameters), and the row's Custom calm style. Off is the stock water draw, no GL. | `HdWaterRealGlTest`, `HdWaterGateTest`, `HdWaterPipelineTest`, `HdWaterBreakingTest`. Numbers below. |
| Reflections | READY | The environment cube on the stock specular slot only, reproducing `GL_MODULATE`; every other material keeps the stock pass. | `HdReflectionGlTest` "the reflective surface follows the environment cube": `[hd-reflect] red cube rgb(102,0,0), green cube rgb(0,102,0), changed 2704 px, bound true/true, uploads=1`; `[hd-reflect] plain: cube swap changes 0 px, row toggle changes 0 px`. |
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

**Lighting model (PENDING) — missing content.** The row needs 634HD's material
maps: a normal map, occlusion/roughness, emissive and height, and a per-material
specular strength and exponent. This client has none. `MaterialType` is the only
material record the cache carries and holds texture-generation parameters only
(no specular strength, exponent or roughness anywhere in it); the fixed
"specular" that does exist is the environment cube, a texture effect, not a
response to the sun. A highlight built today would have to invent both numbers.
Unblocked by Material maps **and** the authored content behind them, not by a
renderer-only change. Proof: `src/client/src/main/java/com/jagex/game/runetek5/config/material/MaterialType.java`,
row doc in `HdFeature`.

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

**Texture replacements (UNAVAILABLE) — missing content.** 634HD's row draws a
cached world texture from a newer authored image. This client ships no
replacement set and has nowhere to load one from: `MaterialType` carries
generation parameters, and the cache has no replacement index or authored image
to point at. Proof: `MaterialType.java`, row doc in `HdFeature`.

**Water (UNAVAILABLE) — missing mechanism, not content.** This row mirrors
upstream's list. 592 has no switch that suppresses water and no second stock
mode: it always draws its own water. The row declares a single value, `Always`,
which has nothing to switch to, so it is marked n/a rather than pending — the
working upgrade is 592HD water. Proof:
row doc in `HdFeature`, `HdStockTarget` (no water target exists).

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
6. **Embers, texture replacements, interface scale, lighting model, soft
   particles**: unchanged conclusions from this ledger — each still needs
   content (a producer kind, a replacement set, material maps) or a capability (a
   scene depth target, a scalable interface layout) that a code-only session
   cannot manufacture.
