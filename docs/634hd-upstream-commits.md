# 634HD upstream commit ledger

Upstream: `2011Scape/634-client` at `9f99853aacaf46c7bbc2d6818c1c16822d9d74be` (cloned read-only into `.634-client-ref/`).

The upstream client is Kotlin against a 634 deob; this client is Java against a 592 deob, so no commit can be cherry-picked. Each row is a behaviour to translate, not a patch to apply. This table is generated from `git log --all` and records the current truth: the **content/data layer** for these features is imported and tested, but the renderer behaviours below are still outstanding unless this file is updated with passing evidence.

| SHA | Date | Subject | Status |
| --- | --- | --- | --- |
| `90ef0b3b76` | 2026-11-01 | Merge pull request #138 from 2011Scape/bug/fix-hd-shadows | superseded (merge commit; the branch tip below carries it) |
| `0993a48bb1` | 2026-11-01 | 634HD shadows: a cave's hidden upper plane casts nothing | not-started |
| `13985ed2ef` | 2026-10-27 | 634HD shadows: opaque materials' texture alpha is no cut-out | ported (`ShadowCutout.testsCasterAlpha` through `GlModel.hasAlphaMaterial`; `ShadowCutoutTest`) |
| `1e19905bc6` | 2026-10-20 | Client pages: the 634HD page's building blocks as a kit, for the plugin manager to share | not-applicable to the HD port |
| `55c8feb0dd` | 2026-10-19 | 634HD: the whirlpool's lip no longer fights the water surface | not-started |
| `058d0ed96e` | 2026-10-19 | 634HD reflections: the band over the water is taken from each caster's own base | not-started |
| `582ef0f278` | 2026-10-19 | 634HD reflections: a fishing spot on a river no longer darkens the water | not-started |
| `8232b03f7a` | 2026-10-19 | 634HD reflections: fishing spots no longer darken the water, and late reflections stop strobing | not-started |
| `1606ab2573` | 2026-10-18 | 634HD water: the ripples keep their place across an environment's edge | not-started |
| `3091a2f28e` | 2026-10-18 | 634HD lights: a lamp lights its own storey, and the carried light keeps behind walls | not-started |
| `d68827a1ed` | 2026-10-18 | 634HD shadows: a moving draw's face that looks at the ground casts nothing | not-started |
| `50261e493e` | 2026-10-18 | 634HD shadows: effects, translucent faces and flat decals cast nothing | ported in part (`ShadowCasterGate.refusal` names which test refused a draw - `WATER` or `BLEND` - and `hdShadowCastersSuppressed()` / `hdShadowCastersWaterSuppressed()` / `hdShadowCastersEffectSuppressed()` count them; the per-fragment and flat-decal halves are named as out of reach in the ledger) |
| `c358edbdb8` | 2026-10-18 | Merge pull request #25 from 2011Scape/feature/634HD-Settings | superseded (merge commit; the branch tip below carries it) |
| `07aa030f59` | 2026-10-18 | 634HD page: a value at its highest shown in blue, as the Custom page does | not-started |
| `6d2cbee08e` | 2026-10-18 | 634HD page: the bevel pieces mirrored the right way | not-started |
| `c70e7515c8` | 2026-10-18 | 634HD settings as a page of the Graphics Options window | not-started |
| `c1cb40c882` | 2026-10-17 | Merge pull request #23 from 2011Scape/chore/634hd-naming | superseded (merge commit; the branch tip below carries it) |
| `692597bd43` | 2026-10-17 | 634HD: the features described as 634HD's own | not-started |
| `af4e04d5e7` | 2026-10-17 | 634HD: the 117HD environment importer leaves the repository | not-started |
| `ce64da3e88` | 2026-10-17 | 634HD fog: the fog as 117HD draws it, its environments imported | not-started |
| `96c0452864` | 2026-10-17 | Merge feature/improved-water: 634HD water as 117HD draws it (client.hd.water 2) | not-started |
| `1c70f475b4` | 2026-10-17 | 634HD water: no mirror on a water model; the ponds dark at night | not-started |
| `ab51d64312` | 2026-10-17 | 634HD water: the water models take the surface; the swamp's own type | not-started |
| `4244b8ee80` | 2026-10-17 | 634HD water: the surface as 117HD draws it (client.hd.water 2) | not-started |
| `f729ee3c93` | 2026-10-16 | 634HD: the fire cape and lava light their surroundings | not-started |
| `132a1fd772` | 2026-10-16 | 634HD fire cape: the game's colour through the tonemap; an outfit scene from behind | not-started |
| `5d1acd87a8` | 2026-10-16 | 634HD: the fire cape and lava as 117HD draws them (flow map, unlit) | not-started |
| `a565e65255` | 2026-10-16 | 634HD light grid: a one-bounce reference check (tools/light_grid_reference.py) | not-started |
| `2e6e5daaf2` | 2026-10-16 | 634HD light grid: the debug map prints the fill's profile and hue from the room's strongest lamp | not-started |
| `6d5a99fa65` | 2026-10-16 | 634HD light grid: the debug census tests every room and the outdoors | not-started |
| `a82c746672` | 2026-10-16 | 634HD light grid: a soft ceiling on the lamps' sum, a least reach of 7 tiles | not-started |
| `a7c8776cf9` | 2026-10-16 | 634HD light grid: the added light capped at the lit colour's ceiling; a leak count | not-started |
| `7e5e61b14f` | 2026-10-16 | 634HD light grid: the blur only over the lit bounds; the build timed by phase | not-started |
| `8f199d1a80` | 2026-10-16 | 634HD: client.test.hdGpu prints the 3D view's GPU time (the timer query without the panel) | not-started |
| `59d22730ec` | 2026-10-16 | 634HD light grid: the fill read a quarter tile out along the surface's normal | not-started |
| `9c4e3a89f4` | 2026-10-16 | 634HD light grid: room light flooded on the tile grid, stopped by walls | not-started |
| `6d5e498496` | 2026-10-16 | 634HD flicker: a flame's own shimmer on the smoothed game curve | not-started |
| `386c5432bd` | 2026-10-16 | 634HD flicker: five times lazier (0.6 s time constant) | not-started |
| `1fc98b03a5` | 2026-10-16 | 634HD: smooth the game's light flicker; bloom strength halved | not-started |
| `15a94b8464` | 2026-10-16 | 634HD lighting: a ceiling on colour x light, not on the light, in linear colour | not-started (the term sum clamps the final colour like the fixed pipeline; nothing caps colour x light) |
| `53812644cd` | 2026-10-16 | 634HD bloom: cap the point lights on the per-pixel and retained paths too | not-started |
| `b35c2be614` | 2026-10-16 | 634HD bloom: cap the game's point lights in linear colour; hide halos under bloom | not-started |
| `661ea4dcf3` | 2026-10-16 | 634HD material maps: no missing-PNG warning for a set that has blocks | not-started |
| `70bb874761` | 2026-10-16 | 634HD options: toggle labelled "634HD (Experimental)", button "634HD Settings" | not-started |
| `2226a619f4` | 2026-10-16 | 634HD debug panel: the refresh timer stopped with the window | not-started |
| `92122fb5be` | 2026-10-16 | 634HD: settings panel closed at logout; debug panel freed with its toolkit; client.hd.renderDistance read | not-started |
| `951331fdd2` | 2026-10-15 | 634HD: carried light from the player's lit light sources | not-started |
| `a1c6bcc7d0` | 2026-10-16 | 634HD review fixes: zone offsets at a longer draw distance, reflections lit and placed right, stream orphans | not-started |
| `7f9ea615d5` | 2026-10-15 | 634HD shadows: the attribute loops' own variable | not-started |
| `52924eaa02` | 2026-10-15 | 634HD reflections: right at dusk and night | not-started |
| `2094131eaa` | 2026-10-15 | 634HD: planar reflections in the water | not-started |
| `aba4492c4d` | 2026-10-15 | 634HD water: shown as the game shows it under the tonemap | not-started |
| `01f7a88ab6` | 2026-10-15 | 634HD water: more see-through | not-started |
| `b99b768afb` | 2026-10-15 | 634HD water: no tile facets; each ground its own depth map; longer sea fade | not-started |
| `0d72f95560` | 2026-10-15 | 634HD: remove distant detail fade; tonemapping without the plastic look | not-started |
| `376e65595a` | 2026-10-15 | 634HD: water dims at night; shadows steady with day and night | not-started |
| `cdf2ca2128` | 2026-10-16 | 634HD: blob shadows under water drawn for real; light factors in sRGB terms | not-started |
| `dd512b669f` | 2026-10-15 | 634HD: the kept region keys cleared at logout | not-started |
| `1bdb5eded4` | 2026-10-15 | 634HD reflections: players, NPCs and animated locs | not-started |
| `b38f3d29c5` | 2026-10-15 | 634HD water: the game's water as the standard toolkit draws it | not-started |
| `28e3e42464` | 2026-10-15 | 634HD water: the body of water drawn in place of the game's thin surface | not-started |
| `4619483c26` | 2026-10-15 | 634HD water: colour by depth, detail ripples, Fresnel and sun highlight | not-started |
| `64c3ae81b9` | 2026-10-15 | 634HD: the game's blob shadows kept under water with sun shadows on | not-started |
| `91515a709f` | 2026-10-15 | 634HD: the draw distance as a bigger scene, the game's own way | not-started |
| `319fe28cbe` | 2026-10-15 | 634HD: water fades into a sea horizon, not the sky | not-started |
| `1f593c6452` | 2026-10-15 | 634HD water: the tonemap undone before the fog, not after | not-started |
| `38a0ed30a0` | 2026-10-15 | 634HD: murkier water, the seabed no longer shows through the sea | not-started |
| `ace2e1ac78` | 2026-10-15 | 634HD: the fog brought in at a long draw distance | not-started |
| `9dac7ffab9` | 2026-10-15 | 634HD: the sea fades into the sky at the scene's edge | not-started |
| `c05d5f224b` | 2026-10-15 | 634HD: remove terrain shadows | not-started |
| `f9087e53e2` | 2026-10-15 | 634HD water: composed in sRGB under tonemapping; translucent models drawn in order | not-started |
| `5993b9e434` | 2026-10-14 | 634HD: retained scene built only after the toolkit's first frame | not-started |
| `107397eb73` | 2026-10-14 | 634HD: objects missing after switching to 634HD in game | not-started |
| `6133af8320` | 2026-10-14 | 634HD world textures: drop the weak ones | not-started |
| `a97f32c40c` | 2026-10-14 | 634HD world textures: calmer art and relief | not-started |
| `a5364e4413` | 2026-10-14 | 634HD world textures, part 3: the remaining world surfaces (226 in all) | not-started |
| `e48bb607fc` | 2026-10-14 | 634HD world textures, part 2: the rest of the top 81 | not-started |
| `513e219086` | 2026-10-14 | 634HD world textures, part 1: engine limits and the first 25 | not-started |
| `724c70f43c` | 2026-10-14 | 634HD content: Draynor, Falador and Al Kharid environments | not-started |
| `e36cd89fb3` | 2026-10-14 | 634HD tonemapping: natural exposure, sRGB inputs made linear, fog mixed in sRGB | not-started |
| `f766495fd0` | 2026-10-14 | 634HD: distant detail fade (client.hd.detailFade) | not-started |
| `a915ec5e43` | 2026-10-14 | 634HD particles: lights at the flame, embers sized to it | not-started |
| `0efc7482e9` | 2026-10-14 | 634HD particles: embers start at each loc's flame | not-started |
| `79629d0621` | 2026-10-14 | 634HD lighting model: point lights on the mapped relief, with highlights | not-started |
| `c78f0fa237` | 2026-10-14 | 634HD: lighting model (client.hd.lightingModel) | ported (`HdFeature.LIGHTING_MODEL`, `HdDrawRows.Row.LIGHTING_MODEL`; `HdShaderSourceTest`, `HdLightingModelGlTest`) |
| `dcecd2e014` | 2026-10-14 | 634HD water: lapping water's waves placed by world position (no grid on water models) | not-started |
| `30ae41e6c4` | 2026-10-14 | 634HD fresh textures: painterly-realistic art for the 10 kept textures | not-started |
| `2ab6d58266` | 2026-10-14 | 634HD fresh textures: bold art where fine detail vanished; 10 kept | ported (the shipped albedo draws through the row; `HdTextureReplacementGlTest`) |
| `c31daebec7` | 2026-10-14 | 634HD fresh textures: realistic art for Lumbridge and Varrock's ground and stone | ported (same art path, 186 shipped PNGs; `HdTextureReplacementGlTest`) |
| `84042b5ee2` | 2026-10-14 | 634HD fresh textures: drop 159, 322 and 387; 505 back in the tool | not-started |
| `8fd45e8fbd` | 2026-10-14 | 634HD: fresh textures for Lumbridge and Varrock (textureReplacements) | ported (`HdTextureReplacements.applied` in `OpenGLTextureCache` and `HdMaterialArt`; `HdTextureReplacementGlTest`) |
| `96eef66063` | 2026-10-14 | 634HD content: material maps for Lumbridge's and Varrock's textures | ported (39 map sets decode from BC5/BC7; `HdMaterialCoverageTest`) |
| `0adfc97d45` | 2026-10-14 | 634HD content: Lumbridge and Varrock texture overrides | not-started |
| `3448c5a331` | 2026-10-14 | 634hd-plan: status after the plan (AO fixes, shadows, content engine, water, day and night, content) | not-started |
| `aed9bdb065` | 2026-10-14 | 634HD content: Lumbridge and Varrock lights and environments, authoring tools | not-started |
| `74da958ca0` | 2026-10-14 | 634HD day and night (optional, off by default) | not-started |
| `a349371b3a` | 2026-10-14 | 634HD water and foliage: water per area, caustics, grass that bends | not-started |
| `bf74b47f71` | 2026-10-14 | 634HD content engine: custom materials, model and tile overrides, area lighting | adapted (materials, model and tile overrides in `client.hd.content`; area lighting in `HdAreaLight`; no plugin-supplied material classes) |
| `894ba2bde2` | 2026-10-14 | 634HD debug panel: the tile and locs under the mouse, for content authoring | not-started |
| `94bf76210f` | 2026-10-14 | 634HD shadows: terrain and cut-out casters, castShadows overrides | not-started |
| `66c39d5ff1` | 2026-10-14 | 634HD ambient occlusion: no darkness indoors, no pops while walking | not-started |
| `9db244fdcf` | 2026-10-14 | 634HD: shiny metal armour no longer washed out | not-started |
| `e3a84c5bf3` | 2026-10-14 | 634HD shadows: characters leave out their flat shadow models | not-started |
| `1dd51216e1` | 2026-10-13 | 634HD shadows: trees cast them, and the game's blob shadows give way | not-started |
| `a015833b82` | 2026-10-13 | 634HD settings panel: draw distance (live) and interface scale | not-started |
| `b9c769be44` | 2026-10-13 | 634HD: ambient occlusion costs a third of a millisecond; a memory soak run | not-started |
| `2cd357e1be` | 2026-10-13 | 634HD shadows: translucent characters' shadows no longer blink while walking | not-started |
| `f244af4eec` | 2026-10-13 | 634HD: no shadow flashes, no crash on toggling, much cheaper shadows and occlusion | not-started |
| `40d2ce2834` | 2026-10-13 | 634HD: the fade melts into the fog; a live on/off check for every switch | not-started |
| `69ed12b56d` | 2026-10-13 | 634HD: calmer water, ground blending that keeps the game's texture | not-started |
| `307592c73e` | 2026-10-13 | 634HD shadows: every static caster near the player, a higher sun | not-started |
| `d2739dd26a` | 2026-10-13 | 634HD: presets and an in-game settings panel | not-started |
| `b4e0330533` | 2026-10-13 | 634HD materials: height detail generated with /generate | not-started |
| `40a64cb754` | 2026-10-13 | 634HD: ambient occlusion baked into the retained vertices | not-started |
| `05350e143d` | 2026-10-13 | 634HD: material maps precompressed at build time (BC5, BC7 mode 6) | not-started |
| `5f7768fe6b` | 2026-10-13 | docs: 634HD checked on Mesa with a 4.1 core context | not-started |
| `a34db1358b` | 2026-10-13 | jaggl: a context without a swap interval where GLX has no swap control; 634HD asks for 4.1 core on macOS | not-started |
| `19f92c8c31` | 2026-10-13 | 634HD: bake ambient occlusion per region | not-started |
| `585de3e25c` | 2026-10-13 | 634HD: material maps compressed on upload (BC5 normals, BC7 packed and emissive) | adapted (`HdMapBlocks` decodes BC5 and BC7 mode 6 to RGBA - upstream's own CPU fallback for a driver that cannot sample the blocks, which jaggl cannot; all 39 shipped sets bind decoded blocks on units 6-9; `HdMapBlocksTest`, `HdMaterialCoverageTest`) |
| `a6c8dc48ce` | 2026-10-13 | 634HD: smooth interface scaling (client.hd.smoothInterface) | not-started |
| `b035d4f9e9` | 2026-10-13 | 634HD: GPU particles (client.hd.particles) | not-started |
| `77352ab5ee` | 2026-10-13 | docs: 634HD Phase 4 and 5 features, content pipeline, debug panel, plan status | not-started |
| `1615bc9b2c` | 2026-10-13 | 634HD: debug panel (client.debug builds; F10 or ::hdpanel) | not-started |
| `5238a1d10a` | 2026-10-13 | 634HD Phase 5: material maps (client.hd.materials) | adapted (`HdMaterialTextures` binds normal, ORM, emissive and height per material; the height is the packed map's alpha and `HdShaderSource.materialUv()` marches it six steps as `hdParallaxUv` does; `HdMapBlocksTest`, `HdMaterialCoverageTest`, `HdLightingModelGlTest`) |
| `ecbb91b8ec` | 2026-10-13 | 634HD: whole-number interface scale (client.hd.interfaceScale=2\|3\|4) | not-started |
| `d3454ee923` | 2026-10-13 | 634HD: soft particles (client.hd.softParticles) | not-started |
| `4c83877e76` | 2026-10-13 | 634HD: ground texture blending (client.hd.terrainBlend) | not-started |
| `14f00cc995` | 2026-10-13 | 634HD: water (client.hd.water) | not-started |
| `7aef70a6f5` | 2026-10-13 | 634HD: foliage wind sway and alpha-to-coverage cut-outs (client.hd.foliage) | not-started |
| `2914b5544f` | 2026-10-13 | 634HD: ambient occlusion from an occlusion height map (client.hd.ambientOcclusion) | adapted (baked occlusion from the material's occlusion channel, replacing the sun-map stand-in; the shipped sets have no authored height, so its parallax is inert) |
| `57aa0d051f` | 2026-10-13 | 634HD: sun shadow maps (client.hd.shadows=1024\|2048\|4096) | not-started |
| `f39cd772fa` | 2026-10-13 | 634HD: point lights from lights.json (client.hd.lights) | not-started |
| `7d06f32d67` | 2026-10-13 | 634HD: content data files, sky, fog, fade, colour grading, mipmaps, anisotropy | not-started |
| `439d93b68a` | 2026-10-13 | 634HD: per-pixel lighting (client.hd.pixelLighting) | adapted (the term sum lives in the existing per-pixel program; the area light's own sun pitch/yaw is not applied) |
| `dee39a457a` | 2026-10-13 | 634HD Phase 4: feature switches, shader library, frame uniforms, post chain | not-started |
| `b51c4d954a` | 2026-10-13 | docs: 634HD in RENDERING.md, README and the plan's Phase 3 status | not-started |
| `b384bda2a1` | 2026-10-13 | 634HD: retained translucent locs with a CPU face sort by priority | not-started |
| `19ca9b8aea` | 2026-10-13 | 634HD: stream animated models' geometry through a ring buffer | not-started |
| `6c3e4e9932` | 2026-10-13 | 634HD: coalesce immediate-mode draws; smoke compares toolkits | not-started |
| `5fa021728e` | 2026-10-13 | 634HD: retained static scene in 8x8-tile chunks | not-started |
| `fb238404fb` | 2026-10-13 | 634HD: toggle in Graphics Options; forced-failure switch; smoke debug modes | not-started |
| `6adc56e7ec` | 2026-10-13 | 634HD: toolkit 6 on a GL 3.3 core context (parity with OpenGL) | not-started |
| `13fbf406ba` | 2026-10-13 | smoke: screenshot harness (client.test.scenes); 634HD plan with the verified state | not-started |

**143 rows.** A row becomes `ported` only when a test in this repository exercises the behaviour; `adapted` is a deliberate 592 equivalent, and the difference is recorded in `docs/634hd-parity-ledger.md`.

## Verification of the ported rules (latest pass)

- `13985ed2ef` (opaque texture alpha is not a cut-out): reachable through
  `GlModel.hasAlphaMaterial` -> `ShadowCutout.testsCasterAlpha`; truth table in
  `ShadowCutoutTest`.
- `50261e493e` (effects and water cast nothing): reachable through
  `GlModel`'s caster record -> `GlToolkit.hdRecordShadowCaster` ->
  `ShadowCasterGate.refusal`, with the water and blend refusals counted apart;
  truth table in `ShadowCasterGateTest`.
- `0993a48bb1` and `d68827a1ed` remain not started.

## This port's own work, not an upstream commit

- Terrain-format ground water: `HdWaterSource.GROUND_VERTEX` over every water fragment stage (standard, custom, reflective, breaking), compiled per surface by `HdProgramCompiler.compileGroundWater`, bound by `HdWaterPipeline.beginGround` and entered from `GlGroundLayer.draw` through `GlToolkit.hdBeginWaterGround`. Proven in `HdCausticsGlTest` on a real GL context; the ledger records the measured line and the one ordering limit.
- Ground water parity and the leak it hid: the layer's material selects the surface and the row's custom value and caustics reach it, and `GlGroundLayer.draw` closes the terrain-format program in its `finally` (`released=true` in `HdCausticsGlTest`). A close nested inside the lighting branch never ran, which left the ground program bound and stripped the texture from every draw after the first water layer.
