package com.jagex.game.runetek5.client.hd.content;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The 634HD content files, loaded from {@code /634hd/data/}.
 *
 * <p>Six files, each keyed by 634 ids and coordinates: lights, environments,
 * materials, model overrides, tile overrides and foliage. Each is parsed
 * independently, and a malformed entry is skipped with its file and index
 * recorded in {@link #problems()} - one bad entry never stops the client and
 * never hides the rest of the file.
 *
 * <p>This class is the Java counterpart of upstream's {@code HdContent}, but
 * the values are immutable once loaded: no hot reload, because this client
 * ships the files in its jar and has no data-directory option yet. The
 * lookups preserve upstream's ordering rules, which are part of the data's
 * meaning: the first light or foliage entry naming an id wins, the first tile
 * override whose predicates all match wins, and an environment is chosen by
 * its area before the file's default is considered.
 */
public final class HdContent {

	public static final String DATA_ROOT = "/634hd/data/";

	public static final String MATERIALS_ROOT = "/634hd/materials/";

	private static final String LIGHTS = "lights.json";

	private static final String ENVIRONMENTS = "environments.json";

	private static final String MATERIALS = "materials.json";

	private static final String MODEL_OVERRIDES = "model_overrides.json";

	private static final String TILE_OVERRIDES = "tile_overrides.json";

	private static final String FOLIAGE = "foliage.json";

	private static final String[] FILES = { LIGHTS, ENVIRONMENTS, MATERIALS, MODEL_OVERRIDES, TILE_OVERRIDES,
		FOLIAGE };

	private static volatile HdContent defaults;

	private final List<HdLightDefinition> lights;

	private final List<HdEnvironmentDefinition> environments;

	private final List<HdMaterialDefinition> materials;

	private final List<HdModelOverride> modelOverrides;

	private final List<HdTileOverride> tileOverrides;

	private final List<HdFoliageDefinition> foliage;

	private final List<String> problems;

	private final Map<Integer, HdLightDefinition> lightsByObject = new HashMap<Integer, HdLightDefinition>();

	private final Map<Integer, HdLightDefinition> lightsByNpc = new HashMap<Integer, HdLightDefinition>();

	private final Map<Integer, HdLightDefinition> lightsBySpotAnimation = new HashMap<Integer, HdLightDefinition>();

	private final Map<Integer, HdLightDefinition> lightsByProjectile = new HashMap<Integer, HdLightDefinition>();

	private final Map<Integer, HdMaterialDefinition> materialsById = new HashMap<Integer, HdMaterialDefinition>();

	private final Map<Integer, HdFoliageDefinition> foliageByObject = new HashMap<Integer, HdFoliageDefinition>();

	private final Map<Integer, HdModelOverride> overridesByObject = new HashMap<Integer, HdModelOverride>();

	private final Map<Integer, HdModelOverride> overridesByModel = new HashMap<Integer, HdModelOverride>();

	private final List<HdModelOverride> areaOnlyOverrides = new ArrayList<HdModelOverride>();

	private HdContent(List<HdLightDefinition> lights, List<HdEnvironmentDefinition> environments,
			List<HdMaterialDefinition> materials, List<HdModelOverride> modelOverrides,
			List<HdTileOverride> tileOverrides, List<HdFoliageDefinition> foliage, List<String> problems) {
		this.lights = Collections.unmodifiableList(lights);
		this.environments = Collections.unmodifiableList(environments);
		this.materials = Collections.unmodifiableList(materials);
		this.modelOverrides = Collections.unmodifiableList(modelOverrides);
		this.tileOverrides = Collections.unmodifiableList(tileOverrides);
		this.foliage = Collections.unmodifiableList(foliage);
		this.problems = problems;
		for (int i = 0; i < lights.size(); i++) {
			HdLightDefinition light = lights.get(i);
			index(light.objects(), light, this.lightsByObject);
			index(light.npcs(), light, this.lightsByNpc);
			index(light.spotAnimations(), light, this.lightsBySpotAnimation);
			index(light.projectiles(), light, this.lightsByProjectile);
		}
		for (int i = 0; i < materials.size(); i++) {
			HdMaterialDefinition material = materials.get(i);
			// A duplicate texture id takes the first entry, like every other
			// "first match wins" lookup in these files.
			if (!this.materialsById.containsKey(Integer.valueOf(material.texture()))) {
				this.materialsById.put(Integer.valueOf(material.texture()), material);
			}
		}
		for (int i = 0; i < foliage.size(); i++) {
			HdFoliageDefinition definition = foliage.get(i);
			int[] objects = definition.objects();
			for (int object = 0; object < objects.length; object++) {
				Integer id = Integer.valueOf(objects[object]);
				if (!this.foliageByObject.containsKey(id)) {
					this.foliageByObject.put(id, definition);
				}
			}
		}
		for (int i = 0; i < modelOverrides.size(); i++) {
			HdModelOverride override = modelOverrides.get(i);
			int[] objects = override.objects();
			if (objects != null) {
				for (int object = 0; object < objects.length; object++) {
					Integer id = Integer.valueOf(objects[object]);
					if (!this.overridesByObject.containsKey(id)) {
						this.overridesByObject.put(id, override);
					}
				}
			}
			int[] models = override.models();
			if (models != null) {
				for (int model = 0; model < models.length; model++) {
					Integer id = Integer.valueOf(models[model]);
					if (!this.overridesByModel.containsKey(id)) {
						this.overridesByModel.put(id, override);
					}
				}
			}
			if (!override.namesAnything() && override.area() != null) {
				this.areaOnlyOverrides.add(override);
			}
		}
	}

	/** The lazily loaded shipped content; never null. */
	public static HdContent get() {
		HdContent content = defaults;
		if (content == null) {
			synchronized (HdContent.class) {
				content = defaults;
				if (content == null) {
					content = loadDefault();
					defaults = content;
				}
			}
		}
		return content;
	}

	/** Reads every shipped file; a missing or malformed file is recorded, not thrown. */
	public static HdContent loadDefault() {
		Map<String, String> files = new LinkedHashMap<String, String>();
		List<String> missing = new ArrayList<String>();
		for (int i = 0; i < FILES.length; i++) {
			String text = readResource(DATA_ROOT + FILES[i]);
			if (text == null) {
				missing.add(DATA_ROOT + FILES[i] + ": missing");
			} else {
				files.put(FILES[i], text);
			}
		}
		HdContent content = parse(files);
		for (int i = 0; i < missing.size(); i++) {
			content.problems.add(missing.get(i));
		}
		return content;
	}

	/** Parses a set of file texts; the entry point every test uses. */
	public static HdContent parse(Map<String, String> files) {
		List<String> problems = new ArrayList<String>();
		List<HdLightDefinition> lights = new ArrayList<HdLightDefinition>();
		List<HdEnvironmentDefinition> environments = new ArrayList<HdEnvironmentDefinition>();
		List<HdMaterialDefinition> materials = new ArrayList<HdMaterialDefinition>();
		List<HdModelOverride> modelOverrides = new ArrayList<HdModelOverride>();
		List<HdTileOverride> tileOverrides = new ArrayList<HdTileOverride>();
		List<HdFoliageDefinition> foliage = new ArrayList<HdFoliageDefinition>();

		List<Object> entries = section(files, LIGHTS, "lights", problems);
		if (entries != null) {
			for (int i = 0; i < entries.size(); i++) {
				Map<String, Object> entry = objectEntry(entries.get(i), LIGHTS, i, problems);
				if (entry == null) {
					continue;
				}
				try {
					lights.add(parseLight(entry));
				} catch (RuntimeException ex) {
					problem(problems, LIGHTS, i, ex);
				}
			}
		}

		entries = section(files, ENVIRONMENTS, "environments", problems);
		if (entries != null) {
			for (int i = 0; i < entries.size(); i++) {
				Map<String, Object> entry = objectEntry(entries.get(i), ENVIRONMENTS, i, problems);
				if (entry == null) {
					continue;
				}
				try {
					environments.add(parseEnvironment(entry));
				} catch (RuntimeException ex) {
					problem(problems, ENVIRONMENTS, i, ex);
				}
			}
		}

		entries = section(files, MATERIALS, "materials", problems);
		if (entries != null) {
			for (int i = 0; i < entries.size(); i++) {
				Map<String, Object> entry = objectEntry(entries.get(i), MATERIALS, i, problems);
				if (entry == null) {
					continue;
				}
				try {
					materials.add(parseMaterial(entry));
				} catch (RuntimeException ex) {
					problem(problems, MATERIALS, i, ex);
				}
			}
		}

		entries = section(files, MODEL_OVERRIDES, "overrides", problems);
		if (entries != null) {
			for (int i = 0; i < entries.size(); i++) {
				Map<String, Object> entry = objectEntry(entries.get(i), MODEL_OVERRIDES, i, problems);
				if (entry == null) {
					continue;
				}
				try {
					modelOverrides.add(parseModelOverride(entry));
				} catch (RuntimeException ex) {
					problem(problems, MODEL_OVERRIDES, i, ex);
				}
			}
		}

		entries = section(files, TILE_OVERRIDES, "tiles", problems);
		if (entries != null) {
			for (int i = 0; i < entries.size(); i++) {
				Map<String, Object> entry = objectEntry(entries.get(i), TILE_OVERRIDES, i, problems);
				if (entry == null) {
					continue;
				}
				try {
					tileOverrides.add(parseTileOverride(entry));
				} catch (RuntimeException ex) {
					problem(problems, TILE_OVERRIDES, i, ex);
				}
			}
		}

		entries = section(files, FOLIAGE, "foliage", problems);
		if (entries != null) {
			for (int i = 0; i < entries.size(); i++) {
				Map<String, Object> entry = objectEntry(entries.get(i), FOLIAGE, i, problems);
				if (entry == null) {
					continue;
				}
				try {
					foliage.add(parseFoliage(entry));
				} catch (RuntimeException ex) {
					problem(problems, FOLIAGE, i, ex);
				}
			}
		}

		return new HdContent(lights, environments, materials, modelOverrides, tileOverrides, foliage, problems);
	}

	private static HdLightDefinition parseLight(Map<String, Object> entry) {
		int rgb = requiredColour(entry, "colour");
		Double radius = HdJson.number(entry.get("radius"));
		if (radius == null) {
			throw new IllegalArgumentException("radius is required");
		}
		float[] offset = decimalArray(entry.get("offset"), "offset", 3, 3);
		String flickerName = HdJson.string(entry.get("flicker"));
		int flicker = HdLightDefinition.FLICKER_NONE;
		if ("flicker".equals(flickerName)) {
			flicker = HdLightDefinition.FLICKER;
		} else if ("pulse".equals(flickerName)) {
			flicker = HdLightDefinition.PULSE;
		} else if (flickerName != null && flickerName.length() > 0 && !"none".equals(flickerName)) {
			throw new IllegalArgumentException("unknown flicker '" + flickerName + "'");
		}
		return new HdLightDefinition(HdJson.string(entry.get("name")), ids(entry.get("objects"), "objects", 0,
			Integer.MAX_VALUE), ids(entry.get("npcs"), "npcs", 0, Integer.MAX_VALUE),
			ids(entry.get("spotAnimations"), "spotAnimations", 0, Integer.MAX_VALUE),
			ids(entry.get("projectiles"), "projectiles", 0, Integer.MAX_VALUE), rgb,
			clamp(radius.floatValue(), 0.1F, 64.0F), clamp(HdJson.decimal(entry.get("intensity"), 1.0F), 0.0F, 16.0F),
			offset[0], offset[1], offset[2], flicker);
	}

	private static HdEnvironmentDefinition parseEnvironment(Map<String, Object> entry) {
		HdEnvironmentDefinition environment = new HdEnvironmentDefinition();
		environment.name = HdJson.string(entry.get("name"));
		if (environment.name == null || environment.name.length() == 0) {
			throw new IllegalArgumentException("name is required");
		}
		environment.source = HdJson.string(entry.get("source"));
		Map<String, Object> area = optionalObject(entry.get("area"), "area");
		if (area != null) {
			// Absent lists stay null: an empty array is a real (and very
			// different) constraint - no level is ever in an empty level list,
			// which would make the entry match nothing at all.
			environment.regions = nullableIds(area.get("regions"), "area.regions", 65535);
			environment.levels = nullableIds(area.get("levels"), "area.levels", 3);
			List<Object> bounds = HdJson.array(area.get("bounds"));
			if (area.get("bounds") != null && bounds == null) {
				throw new IllegalArgumentException("area.bounds must be an array");
			}
			if (bounds != null) {
				environment.bounds = new int[bounds.size()][];
				for (int i = 0; i < bounds.size(); i++) {
					int[] bound = ints(bounds.get(i), "area.bounds[" + i + "]", 4, 4);
					environment.bounds[i] = bound;
				}
			}
		}
		Map<String, Object> sun = optionalObject(entry.get("sun"), "sun");
		if (sun != null) {
			environment.sunPitch = optionalDecimal(sun.get("pitch"), "sun.pitch", 0.0F, 90.0F);
			environment.sunYaw = optionalDecimal(sun.get("yaw"), "sun.yaw", -360.0F, 360.0F);
			environment.sunColour = optionalColour(sun, "colour");
			environment.sunIntensity = clamp(HdJson.decimal(sun.get("intensity"), 1.0F), 0.0F, 8.0F);
		}
		Map<String, Object> ambient = optionalObject(entry.get("ambient"), "ambient");
		if (ambient != null) {
			environment.ambientColour = optionalColour(ambient, "colour");
			environment.ambientIntensity = clamp(HdJson.decimal(ambient.get("intensity"), 1.0F), 0.0F, 8.0F);
		}
		Map<String, Object> fog = optionalObject(entry.get("fog"), "fog");
		if (fog != null) {
			environment.fogColour = optionalColour(fog, "colour");
			environment.fogDepth = optionalNumber(fog.get("depth"), "fog.depth", 0.0F, 1000.0F);
			environment.fogEnd = optionalNumber(fog.get("end"), "fog.end", 0.0F, Float.MAX_VALUE);
		}
		Map<String, Object> sky = optionalObject(entry.get("sky"), "sky");
		if (sky != null) {
			environment.skyTop = optionalColour(sky, "top");
			environment.skyHorizon = optionalColour(sky, "horizon");
		}
		Map<String, Object> groundFog = optionalObject(entry.get("groundFog"), "groundFog");
		if (groundFog != null) {
			environment.groundFogStart = HdJson.decimal(groundFog.get("start"), 0.0F);
			environment.groundFogEnd = HdJson.decimal(groundFog.get("end"), 0.0F);
			environment.groundFogOpacity = clamp(HdJson.decimal(groundFog.get("opacity"), 0.0F), 0.0F, 1.0F);
		}
		environment.lut = HdJson.string(entry.get("lut"));
		String waterTint = HdJson.string(entry.get("waterTint"));
		if (waterTint != null) {
			environment.waterTint = Integer.valueOf(parseColour(waterTint, "waterTint"));
		}
		Map<String, Object> water = optionalObject(entry.get("water"), "water");
		if (water != null) {
			if (water.get("tint") != null) {
				environment.waterTint = Integer.valueOf(requiredColour(water, "tint"));
			}
			environment.waterStrength = optionalNumber(water.get("strength"), "water.strength", 0.0F, 1.0F);
			environment.waterWaves = clamp(HdJson.decimal(water.get("waves"), 1.0F), 0.0F, 4.0F);
			environment.waterSpeed = clamp(HdJson.decimal(water.get("speed"), 1.0F), 0.0F, 4.0F);
			environment.waterSize = clamp(HdJson.decimal(water.get("size"), 1.0F), 0.25F, 4.0F);
			environment.waterGlint = clamp(HdJson.decimal(water.get("glint"), 1.0F), 0.0F, 4.0F);
		}
		return environment;
	}

	private static HdMaterialDefinition parseMaterial(Map<String, Object> entry) {
		Double texture = HdJson.number(entry.get("texture"));
		if (texture == null || texture.doubleValue() != Math.floor(texture.doubleValue())
			|| texture.doubleValue() < 0.0D) {
			throw new IllegalArgumentException("texture must be a non-negative integer");
		}
		float[] scroll = decimalArray(entry.get("uvScroll"), "uvScroll", 2, 2);
		float[] flowDuration = decimalArray(entry.get("flowDuration"), "flowDuration", 2, 2);
		if (flowDuration[0] <= 0.0F || flowDuration[1] <= 0.0F) {
			flowDuration = new float[] { 1.0F, 1.0F };
		}
		float[] light = decimalArray(entry.get("light"), "light", 3, 3);
		boolean unlit = Boolean.TRUE.equals(HdJson.bool(entry.get("unlit")));
		HdMaterialDefinition.Water water = parseWater(optionalObject(entry.get("water"), "water"));
		return new HdMaterialDefinition(texture.intValue(), HdJson.string(entry.get("name")),
			HdJson.string(entry.get("albedo")), HdJson.string(entry.get("normal")),
			HdJson.string(entry.get("orm")), HdJson.string(entry.get("height")),
			HdJson.string(entry.get("emissive")), HdJson.string(entry.get("flowMap")), scroll[0], scroll[1],
			clamp(HdJson.decimal(entry.get("specular"), 0.5F), 0.0F, 4.0F),
			clamp(HdJson.decimal(entry.get("emissiveStrength"), 1.0F), 0.0F, 16.0F),
			clamp(HdJson.decimal(entry.get("parallax"), 0.04F), 0.0F, 0.2F),
			clamp(HdJson.decimal(entry.get("flowStrength"), 0.0F), 0.0F, 1.0F), flowDuration[0],
			flowDuration[1], unlit, clamp(light[0], 0.0F, 1.0F), clamp(light[1], 0.0F, 1.0F),
			clamp(light[2], 0.0F, 1.0F), water);
	}

	private static HdMaterialDefinition.Water parseWater(Map<String, Object> entry) {
		if (entry == null) {
			return null;
		}
		float duration = HdJson.decimal(entry.get("duration"), 1.0F);
		return new HdMaterialDefinition.Water(colour(entry, "surface", 0x69809C), colour(entry, "foam", 0xB0A492),
			colour(entry, "depth", 0x00758E), clamp(HdJson.decimal(entry.get("opacity"), 0.5F), 0.0F, 1.0F),
			clamp(HdJson.decimal(entry.get("specular"), 0.5F), 0.0F, 4.0F),
			clamp(HdJson.decimal(entry.get("gloss"), 500.0F), 1.0F, 2000.0F),
			clamp(HdJson.decimal(entry.get("normalStrength"), 0.09F), 0.0F, 1.0F),
			clamp(HdJson.decimal(entry.get("fresnel"), 0.85F), 0.0F, 1.0F),
			clamp(duration, 0.001F, 8.0F), !Boolean.FALSE.equals(HdJson.bool(entry.get("hasFoam"))),
			Boolean.TRUE.equals(HdJson.bool(entry.get("flat"))));
	}

	private static HdModelOverride parseModelOverride(Map<String, Object> entry) {
		String uv = HdJson.string(entry.get("uv"));
		boolean worldUv = true;
		if (uv != null) {
			if ("world".equals(uv)) {
				worldUv = true;
			} else if ("model".equals(uv)) {
				worldUv = false;
			} else {
				throw new IllegalArgumentException("uv must be 'world' or 'model'");
			}
		}
		int material = HdJson.integer(entry.get("material"), -1);
		if (material < -1) {
			throw new IllegalArgumentException("material must be non-negative");
		}
		return new HdModelOverride(HdJson.string(entry.get("name")),
			nullableIds(entry.get("objects"), "objects", Integer.MAX_VALUE),
			nullableIds(entry.get("models"), "models", Integer.MAX_VALUE),
			nullableIds(entry.get("faceColours"), "faceColours", 65535),
			nullableIds(entry.get("area"), "area", Integer.MAX_VALUE, 4, 4), material,
			Boolean.TRUE.equals(HdJson.bool(entry.get("wind"))),
			!Boolean.FALSE.equals(HdJson.bool(entry.get("castShadows"))), worldUv,
			Boolean.TRUE.equals(HdJson.bool(entry.get("replace"))),
			clamp(HdJson.decimal(entry.get("uvScale"), 1.0F), 0.05F, 16.0F));
	}

	private static HdTileOverride parseTileOverride(Map<String, Object> entry) {
		int material = HdJson.integer(entry.get("material"), -1);
		if (material < 0) {
			throw new IllegalArgumentException("material is required");
		}
		HdTileOverride override = new HdTileOverride(HdJson.string(entry.get("name")),
			nullableIds(entry.get("underlays"), "underlays", 255),
			nullableIds(entry.get("overlays"), "overlays", 255),
			nullableIds(entry.get("area"), "area", Integer.MAX_VALUE, 4, 4), material,
			clamp(HdJson.decimal(entry.get("scale"), 1.0F), 0.05F, 16.0F),
			Boolean.TRUE.equals(HdJson.bool(entry.get("replace"))));
		if (!override.hasPredicate()) {
			throw new IllegalArgumentException("a tile override needs an area, underlays or overlays");
		}
		return override;
	}

	private static HdFoliageDefinition parseFoliage(Map<String, Object> entry) {
		int[] objects = ids(entry.get("objects"), "objects", 0, Integer.MAX_VALUE);
		if (objects.length == 0) {
			throw new IllegalArgumentException("objects is required");
		}
		return new HdFoliageDefinition(HdJson.string(entry.get("name")), objects,
			clamp(HdJson.decimal(entry.get("strength"), 1.0F), 0.0F, 4.0F),
			clamp(HdJson.decimal(entry.get("frequency"), 1.0F), 0.0F, 8.0F));
	}

	// -- lookups ---------------------------------------------------------

	public List<HdLightDefinition> lights() {
		return this.lights;
	}

	public List<HdEnvironmentDefinition> environments() {
		return this.environments;
	}

	public List<HdMaterialDefinition> materials() {
		return this.materials;
	}

	public List<HdModelOverride> modelOverrides() {
		return this.modelOverrides;
	}

	public List<HdTileOverride> tileOverrides() {
		return this.tileOverrides;
	}

	public List<HdFoliageDefinition> foliage() {
		return this.foliage;
	}

	/** Entries skipped while loading, each naming its file and index. */
	public List<String> problems() {
		return Collections.unmodifiableList(this.problems);
	}

	/** The first light naming this object, or null. */
	public HdLightDefinition lightForObject(int object) {
		return this.lightsByObject.get(Integer.valueOf(object));
	}

	public HdLightDefinition lightForNpc(int npc) {
		return this.lightsByNpc.get(Integer.valueOf(npc));
	}

	public HdLightDefinition lightForSpotAnimation(int animation) {
		return this.lightsBySpotAnimation.get(Integer.valueOf(animation));
	}

	public HdLightDefinition lightForProjectile(int projectile) {
		return this.lightsByProjectile.get(Integer.valueOf(projectile));
	}

	public HdMaterialDefinition material(int texture) {
		return this.materialsById.get(Integer.valueOf(texture));
	}

	public HdFoliageDefinition foliageForObject(int object) {
		return this.foliageByObject.get(Integer.valueOf(object));
	}

	/**
	 * The environment for a tile: the first entry whose area holds it, else
	 * the file's default, else null.
	 *
	 * <p>Area entries are considered before defaults regardless of file order.
	 * That is what lets a generated block sit above the hand-written default
	 * without swallowing it, and it matches upstream's "first environment
	 * whose area holds the player applies; one without an area is the default".
	 */
	public HdEnvironmentDefinition environmentFor(int tileX, int tileZ, int level) {
		for (int i = 0; i < this.environments.size(); i++) {
			HdEnvironmentDefinition environment = this.environments.get(i);
			if (!environment.isDefault() && environment.contains(tileX, tileZ, level)) {
				return environment;
			}
		}
		for (int i = 0; i < this.environments.size(); i++) {
			HdEnvironmentDefinition environment = this.environments.get(i);
			if (environment.isDefault() && environment.contains(tileX, tileZ, level)) {
				return environment;
			}
		}
		return null;
	}

	/** Upstream's object-and-area lookup: a named object, else an area-only entry. */
	public HdModelOverride overrideForObject(int object, int tileX, int tileZ) {
		HdModelOverride override = this.overridesByObject.get(Integer.valueOf(object));
		if (override != null && override.covers(tileX, tileZ)) {
			return override;
		}
		for (int i = 0; i < this.areaOnlyOverrides.size(); i++) {
			HdModelOverride candidate = this.areaOnlyOverrides.get(i);
			if (candidate.covers(tileX, tileZ)) {
				return candidate;
			}
		}
		return null;
	}

	/** The override for a model, honouring its area when it has one. */
	public HdModelOverride overrideForModel(int model, int tileX, int tileZ) {
		HdModelOverride override = this.overridesByModel.get(Integer.valueOf(model));
		return override != null && override.covers(tileX, tileZ) ? override : null;
	}

	/** The first tile override matching the tile and its floor ids, or null. */
	public HdTileOverride tileOverrideFor(int tileX, int tileZ, int underlayId, int overlayId) {
		for (int i = 0; i < this.tileOverrides.size(); i++) {
			HdTileOverride override = this.tileOverrides.get(i);
			if (override.matches(tileX, tileZ, underlayId, overlayId)) {
				return override;
			}
		}
		return null;
	}

	/** The bytes of {@code name} in material {@code texture}'s folder, or null. */
	public byte[] materialFile(int texture, String name) {
		return materialFile(Integer.toString(texture), name);
	}

	/** The bytes of a file under {@code 634hd/materials/<folder>/}, or null. */
	public byte[] materialFile(String folder, String name) {
		if (!safeName(folder) || !safeName(name)) {
			return null;
		}
		return readBytes(MATERIALS_ROOT + folder + "/" + name);
	}

	/** The bytes of a colour-grading strip under {@code 634hd/luts/}, or null. */
	public byte[] lutFile(String name) {
		if (!safeName(name)) {
			return null;
		}
		return readBytes("/634hd/luts/" + name);
	}

	// -- parsing helpers -------------------------------------------------

	private static List<Object> section(Map<String, String> files, String file, String key, List<String> problems) {
		String text = files == null ? null : files.get(file);
		if (text == null) {
			return null;
		}
		try {
			Map<String, Object> root = HdJson.object(HdJson.parse(text));
			if (root == null) {
				problems.add(file + ": root is not an object");
				return null;
			}
			List<Object> entries = HdJson.array(root.get(key));
			if (entries == null) {
				problems.add(file + ": '" + key + "' must be an array");
				return null;
			}
			return entries;
		} catch (RuntimeException ex) {
			problems.add(file + ": " + ex.getMessage());
			return null;
		}
	}

	private static Map<String, Object> objectEntry(Object value, String file, int index, List<String> problems) {
		Map<String, Object> entry = HdJson.object(value);
		if (entry == null) {
			problems.add(file + "[" + index + "]: not an object");
		}
		return entry;
	}

	private static void problem(List<String> problems, String file, int index, RuntimeException ex) {
		String message = ex.getMessage();
		problems.add(file + "[" + index + "]: " + (message == null ? ex.getClass().getSimpleName() : message));
	}

	private static int requiredColour(Map<String, Object> entry, String key) {
		String text = HdJson.string(entry.get(key));
		if (text == null) {
			throw new IllegalArgumentException(key + " is required");
		}
		return parseColour(text, key);
	}

	private static int colour(Map<String, Object> entry, String key, int fallback) {
		Object value = entry.get(key);
		return value == null ? fallback : requiredColour(entry, key);
	}

	private static Integer optionalColour(Map<String, Object> entry, String key) {
		Object value = entry.get(key);
		if (value == null) {
			return null;
		}
		return Integer.valueOf(requiredColour(entry, key));
	}

	private static int parseColour(String text, String key) {
		int rgb = HdJson.colour(text, -1);
		if (rgb < 0) {
			throw new IllegalArgumentException(key + " must be #rrggbb");
		}
		return rgb;
	}

	private static Map<String, Object> optionalObject(Object value, String key) {
		if (value == null) {
			return null;
		}
		Map<String, Object> object = HdJson.object(value);
		if (object == null) {
			throw new IllegalArgumentException(key + " must be an object");
		}
		return object;
	}

	private static Float optionalDecimal(Object value, String key, float min, float max) {
		if (value == null) {
			return null;
		}
		return Float.valueOf(clamp(number(value, key), min, max));
	}

	private static float optionalNumber(Object value, String key, float min, float max) {
		return value == null ? Float.NaN : clamp(number(value, key), min, max);
	}

	private static float number(Object value, String key) {
		Double number = HdJson.number(value);
		if (number == null) {
			throw new IllegalArgumentException(key + " must be a number");
		}
		return number.floatValue();
	}

	private static int[] ids(Object value, String key, int minId, int maxId) {
		int[] ids = nullableIds(value, key, maxId);
		if (ids == null) {
			return new int[0];
		}
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] < minId || ids[i] > maxId) {
				throw new IllegalArgumentException(key + " contains " + ids[i] + ", outside " + minId + ".." + maxId);
			}
		}
		return ids;
	}

	private static int[] nullableIds(Object value, String key, int maxId) {
		return nullableIds(value, key, maxId, 0, Integer.MAX_VALUE);
	}

	private static int[] nullableIds(Object value, String key, int maxId, int minCount, int maxCount) {
		if (value == null) {
			return null;
		}
		int[] ids = ints(value, key, minCount, maxCount);
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] < 0 || ids[i] > maxId) {
				throw new IllegalArgumentException(key + " contains " + ids[i] + ", outside 0.." + maxId);
			}
		}
		return ids;
	}

	private static int[] ints(Object value, String key, int minCount, int maxCount) {
		List<Object> list = HdJson.array(value);
		if (list == null) {
			throw new IllegalArgumentException(key + " must be an array");
		}
		if (list.size() < minCount || list.size() > maxCount) {
			throw new IllegalArgumentException(key + " must hold " + minCount + ".." + maxCount + " values");
		}
		int[] values = new int[list.size()];
		for (int i = 0; i < values.length; i++) {
			Double number = HdJson.number(list.get(i));
			if (number == null || number.doubleValue() != Math.floor(number.doubleValue())) {
				throw new IllegalArgumentException(key + " must hold whole numbers");
			}
			double raw = number.doubleValue();
			if (raw < Integer.MIN_VALUE || raw > Integer.MAX_VALUE) {
				throw new IllegalArgumentException(key + " holds a number out of range");
			}
			values[i] = (int) raw;
		}
		return values;
	}

	private static float[] decimalArray(Object value, String key, int minCount, int maxCount) {
		if (value == null) {
			return new float[maxCount];
		}
		List<Object> list = HdJson.array(value);
		if (list == null || list.size() < minCount || list.size() > maxCount) {
			throw new IllegalArgumentException(key + " must hold " + minCount + ".." + maxCount + " numbers");
		}
		float[] values = new float[maxCount];
		for (int i = 0; i < list.size(); i++) {
			values[i] = number(list.get(i), key + "[" + i + "]");
		}
		return values;
	}

	private static float clamp(float value, float min, float max) {
		if (Float.isNaN(value)) {
			return min;
		}
		return value < min ? min : (value > max ? max : value);
	}

	private static boolean safeName(String name) {
		return name != null && name.length() > 0 && name.indexOf('/') < 0 && name.indexOf('\\') < 0
			&& !name.contains("..");
	}

	private static void index(int[] ids, HdLightDefinition light, Map<Integer, HdLightDefinition> target) {
		for (int i = 0; i < ids.length; i++) {
			Integer id = Integer.valueOf(ids[i]);
			if (!target.containsKey(id)) {
				target.put(id, light);
			}
		}
	}

	private static String readResource(String path) {
		byte[] bytes = readBytes(path);
		if (bytes == null) {
			return null;
		}
		try {
			return new String(bytes, "UTF-8");
		} catch (IOException ex) {
			return null;
		}
	}

	private static byte[] readBytes(String path) {
		InputStream stream = HdContent.class.getResourceAsStream(path);
		if (stream == null) {
			return null;
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int read;
			while ((read = stream.read(buffer)) >= 0) {
				out.write(buffer, 0, read);
			}
			return out.toByteArray();
		} catch (IOException ex) {
			return null;
		} finally {
			try {
				stream.close();
			} catch (IOException ignored) {
				// Nothing useful to do with a failed close of a classpath stream.
			}
		}
	}
}
