#version 120

uniform sampler2D uTexture;
uniform samplerCube uEnvironment;
uniform float uReflection;
uniform float uTime;
uniform float uFlow;
uniform float uShininess;
uniform sampler2D uWaterNormal;
uniform sampler2D uWaterFoam;
uniform sampler2D uWaterFlow;
uniform float uWaterMaps;
uniform float uNormalStrength;
uniform vec3 uDeepColour;
uniform vec3 uShallowColour;
uniform float uCaustics;

// ---- 634HD's water types (hd_material.glsl, 634hd/data/materials.schema.json):r
// a material's water block is the type, and the type's own fields ride inr
// the uniforms below, so a swamp's water and a lake's are one program withr
// different numbers. The depth, foam, mirror and bend constants arer
// upstream's and the same for every type. ----
#define HD_WATER_DEPTH_RANGE 1536.0
#define HD_WATER_FOAM_DEPTH 192.0
#define HD_WATER_DEPTH_OPACITY 0.9
#define HD_WATER_REFLECTION_BEND 0.3
#define HD_WATER_MIRROR_SHARE 0.5
// specular strength, Fresnel share, least opacity - hdWaterSpec.xw and the
// surface colour's alpha in upstream's tables.
uniform vec3 uWaterSpec;
// hasFoam, animation period, plain (WATER's Fresnel amount, the quarter
// darkening).
uniform vec3 uWaterShape;
uniform vec3 uFoamColour;
// The frame's live point lights, counted the way the lighting program countsr
// them: gl_LightSource[2 .. 5], uploaded by the stock light copy.
uniform int uPointLightCount;

// 634HD's world uv (hdWaterWorldUv): a repeat every [tiles] tiles (512
// units) of the world position, negative so growing x or z walks the map
// backwards, as upstream's does.
vec2 hdWaterWorldUv634(vec2 xz, float tiles) {
	return -xz / (512.0 * tiles);
}

// The animation frame (hdWaterFrame): the phase of a slide that takes
// [period] seconds of water time; 0 for a period of none.
float hdWaterFrame634(float seconds, float period) {
	return period > 0.0 ? fract(seconds / period) : 0.0;
}

// 634HD's flow map (hdWaterLayers): the current's velocity (rg) at world
// [xz], sampled every 15 tiles through a frame 50 seconds long, both
// scaled by the type's animation period. The frame is one float added to
// both uv axes, so the current itself drifts diagonally, and the ripples
// and the foam are bent by it and travel with it. Zero without the maps,
// which leaves the shipped wave field's own motion.
vec2 hdWaterFlow634(vec2 xz, float seconds) {
	if (uWaterMaps < 0.5) {
		return vec2(0.0);
	}
	vec2 uv = hdWaterWorldUv634(xz, 15.0) + hdWaterFrame634(seconds, 50.0 * uWaterShape.y);
	return texture2D(uWaterFlow, uv).xy;
}

// 634HD's water normal map (hdWaterLayers): three scrolled layers at 3, 5
// and 7 tiles a repeat, each its own frame of a 30, 24 and 20 second
// period. They are not three copies of one slide: upstream builds the
// 3-tile layer's uv with its axes swapped and scrolls it *against* the
// clock, and the 5 and 7 tile layers with it, so neighbouring layers
// travel in different directions and their sum drifts rather than
// sliding as one sheet. Sampled in world space, so the ripples stay put
// as the camera moves.
vec2 hdWaterRipples(vec2 xz, float seconds) {
	if (uWaterMaps < 0.5) {
		return vec2(0.0);
	}
	float duration = uWaterShape.y;
	float strength = uNormalStrength;
	// The current's own bend on every layer, upstream's 0.025 of the
	// flow map's velocity.
	vec2 flow = hdWaterFlow634(xz, seconds) * 0.025;
	vec2 uv1 = hdWaterWorldUv634(xz, 3.0).yx - hdWaterFrame634(seconds, 30.0 * duration) + flow;
	vec2 uv2 = hdWaterWorldUv634(xz, 5.0) + hdWaterFrame634(seconds, 24.0 * duration) + flow;
	vec2 uv3 = hdWaterWorldUv634(xz, 7.0) + hdWaterFrame634(seconds, 20.0 * duration) + flow;
	vec2 sum = (texture2D(uWaterNormal, uv1).rg * 2.0 - 1.0)
		+ (texture2D(uWaterNormal, uv2).rg * 2.0 - 1.0)
		+ (texture2D(uWaterNormal, uv3).rg * 2.0 - 1.0);
	// Upstream's n per layer is -((r) * strength, b, (g) * strength): the
	// map's red and green are the two tilts, negated; the blue it carries
	// is the up axis this 2D port already has from the geometry.
	return -sum * strength;
}

// The foam map's texel at world [xz], drifting with the current the way
// the ripples do (upstream: fract(world / 512) + flow * 0.025), 1 when
// the maps are absent.
float hdWaterFoamTexel(vec2 xz, float seconds) {
	if (uWaterMaps < 0.5) {
		return 1.0;
	}
	vec2 flow = hdWaterFlow634(xz, seconds) * 0.025;
	return texture2D(uWaterFoam, fract(xz / 512.0) + flow).r;
}

// The shore foam mask, faded in over the stock per-vertex depth; 634HD's
// foam depth is 192 scene units.
float hdShoreFoam(vec2 xz, float depth, float seconds) {
	float mask = hdWaterFoamTexel(xz, seconds);
	return clamp(mask * (1.0 - clamp(depth / 192.0, 0.0, 1.0)) * 1.6, 0.0, 1.0);
}

// 634HD's hdCaustics: the bright web a rippling surface focuses, two
// rotated warped wave layers sharpened into thin lines, 0 to about 1.
// Transcribed number for number from hd_material.glsl, and shared by every
// surface so the Caustics row means one thing on water and seabed alike.
float hdCaustics(vec2 p, float t) {
	vec2 q = p * 0.0042;
	float total = 0.0;
	for (int i = 0; i < 2; i++) {
		float s = float(i) * 1.37 + 1.0;
		float angle = float(i) * 0.65;
		vec2 r = mat2(cos(angle), -sin(angle), sin(angle), cos(angle)) * q * s;
		vec2 w = r + vec2(sin(r.y * 1.3 + t * 0.7 * s), cos(r.x * 1.1 - t * 0.6 * s)) * 1.1;
		w += vec2(sin(w.y * 2.7 + t * 0.4), cos(w.x * 2.3 - t * 0.5)) * 0.35;
		float a = sin(w.x * 2.1 + t * 0.9) * sin(w.y * 2.4 - t * 0.8);
		total += pow(1.0 - abs(a), 7.0);
	}
	return total * 0.5;
}

// 634HD's Fresnel mapping: not a power of the cosine but a straight mix
// from 0.4 to 1.0 over the sine of the angle, so the reflection arrives
// sooner at a grazing look than a cubic does.
float hdWaterFresnel634(float facing) {
	return clamp(mix(0.4, 1.0, (1.0 - clamp(facing, 0.0, 1.0)) * 1.2), 0.0, 1.0);
}

// What the surface reflects on its own: a gradient of the area's water
// tint, normalised to its own brightest channel, at 0.05, 0.45 and 0.8 by
// the Fresnel - upstream's three stops in hdWaterReflection.
vec3 hdWaterTint634(vec3 tint, float finalFresnel) {
	vec3 t = tint / max(max(max(tint.r, tint.g), max(tint.b, 0.001)), 0.001);
	return finalFresnel < 0.5
		? mix(t * 0.05, t * 0.45, finalFresnel * 2.0)
		: mix(t * 0.45, t * 0.8, (finalFresnel - 0.5) * 2.0);
}

// The surface's own light, before anything it reflects: the frame's
// ambient, the sun's diffuse on the ripples, the view-based glint at the
// type's specular strength, and the sky's light under the fog band on
// [up], the world's up in eye space. 634HD reads its ambient and sun from
// the environment block; this frame's are gl_LightModel.ambient and the
// stock sun row, which is the same light the scene is drawn with.
vec3 hdWaterLight634(vec3 normal, vec3 view, vec3 up) {
	vec3 light = normalize(gl_LightSource[0].position.xyz);
	vec3 sun = gl_LightSource[0].diffuse.rgb;
	vec3 glint = sun * pow(clamp(dot(view, reflect(-light, normal)), 1.0e-10, 1.0),
		uShininess) * uWaterSpec.x;
	return gl_LightModel.ambient.rgb + sun * max(dot(normal, light), 0.0) + glint
		+ gl_Fog.color.rgb * max(dot(normal, up), 0.0) * 0.5;
}

// 634HD's point lights on the water: each live light's diffuse, and its
// glint mirrored about the surface at the type's gloss and strength. The
// frame's lights are gl_LightSource[2 .. 5] with the stock attenuation,
// exactly where the lighting program reads the same lights; [eye] is the
// fragment in eye space, the space this surface's normal and view live in.
vec3 hdWaterPoints634(vec3 eye, vec3 normal, vec3 view) {
	vec3 sum = vec3(0.0);
	for (int i = 2; i < 6; i++) {
		if (i - 2 >= uPointLightCount) {
			break;
		}
		vec3 toLight = gl_LightSource[i].position.xyz - eye;
		float distanceSquared = max(dot(toLight, toLight), 1.0e-8);
		float distance = sqrt(distanceSquared);
		vec3 direction = toLight / distance;
		float attenuation = 1.0 / (gl_LightSource[i].constantAttenuation
			+ gl_LightSource[i].linearAttenuation * distance
			+ gl_LightSource[i].quadraticAttenuation * distanceSquared);
		vec3 colour = gl_LightSource[i].diffuse.rgb * attenuation;
		sum += colour * max(dot(normal, direction), 0.0);
		sum += colour * pow(max(dot(view, reflect(-direction, normal)), 0.0),
			uShininess) * uWaterSpec.x;
	}
	return sum;
}

// 634HD's surface: the type's colour pulled towards the area's tint, lit
// by hdWaterLight634, mixed with what it [reflection] reflects at the
// type's Fresnel share, the plain water darkened by a quarter, the shore
// foam in the type's colour, and the depth's absorption and opacity.
// [depth] is the stock per-vertex water depth in scene units, as
// upstream's vertex depth is; [foamMask] is the shore foam map's texel,
// 1 without the maps.
vec4 hdWaterBody634(vec3 eye, vec3 normal, vec3 view, vec3 up, float depth,
		float foamMask, vec3 reflection) {
	float facing = clamp(dot(normal, view), 0.0, 1.0);
	float finalFresnel = hdWaterFresnel634(facing);
	// The sun, the sky and the frame's point lights: a torch beside a lake
	// has to glint on it, as it does upstream.
	vec3 composite = hdWaterLight634(normal, view, up)
		+ hdWaterPoints634(eye, normal, view)
		// what the surface reflects is light on it too: upstream's
		// reflection * max(specStrength, 0.2) term
		+ reflection * max(uWaterSpec.x, 0.2);
	// The type's colour, its hue pulled towards the area's tint at the
	// type's own brightness: upstream's hdWaterTint share.
	vec3 typeColour = uShallowColour;
	float typeLuma = dot(typeColour, vec3(0.299, 0.587, 0.114));
	float tintLuma = max(dot(uDeepColour, vec3(0.299, 0.587, 0.114)), 0.001);
	vec3 surfaceSrgb = mix(typeColour, uDeepColour * (typeLuma / tintLuma), 0.5);
	vec3 base = mix(surfaceSrgb * composite, reflection, uWaterSpec.y);
	// plain water - the types sharing WATER's Fresnel amount - is a
	// quarter darker, which is what keeps the shallows off a pale sheet.
	if (uWaterShape.z > 0.5) {
		base *= 0.75;
	}
	float shore = 1.0 - clamp(depth / HD_WATER_FOAM_DEPTH, 0.0, 1.0);
	vec3 foamColour = uFoamColour * foamMask * composite;
	float foamAmount = clamp(pow(max(1.0 - (1.0 - min(shore, 0.8)) / 0.7, 0.0), 3.0),
		0.0, 1.0) * clamp(foamColour.r, 0.0, 1.0) * uWaterShape.x;
	base = mix(base, foamColour, foamAmount);
	// 634HD raises the Fresnel by how flat the view is on the surface: a
	// surface seen edge-on mirrors the sky whatever its ripples do.
	finalFresnel = max(finalFresnel, 1.0 - clamp(dot(view, up), 0.0, 1.0));
	float alpha = max(uWaterSpec.z, max(foamAmount, finalFresnel));
	alpha = max(alpha, HD_WATER_DEPTH_OPACITY
		* smoothstep(0.3, 1.0, clamp(depth / HD_WATER_DEPTH_RANGE, 0.0, 1.0)));
	// the type's depth colour laid under the surface by that opacity: the
	// depth's absorption instead of a flat darkening of the same water.
	return vec4(clamp(mix(uDeepColour, base, alpha), 0.0, 1.0), alpha);
}


varying vec3 vEyePosition;
varying vec3 vWorldPosition;
varying vec3 vWorldNormal;
varying vec3 vNormalX;
varying vec3 vNormalY;
varying vec3 vNormalZ;
varying vec2 vTexCoord;
varying float vFoam;

void main() {
	vec3 surface = vWorldNormal;
	// Water geometry without normals is still a flat surface facing up.
	if (dot(surface, surface) < 1.0e-6) {
		surface = vec3(0.0, 1.0, 0.0);
	}
	// Two crossing wave trains in scene space: the surface is never flat,
	// and the waves stay put in the world as the camera moves.
	float phase = uTime;
	surface.x += sin(vWorldPosition.x * 0.31 + phase * 1.9) * uFlow
		+ sin(vWorldPosition.z * 0.17 - phase * 1.3) * uFlow * 0.6;
	surface.z += cos(vWorldPosition.z * 0.27 - phase * 1.6) * uFlow
		+ cos(vWorldPosition.x * 0.21 + phase * 1.1) * uFlow * 0.6;
	surface = normalize(surface);
	// 634HD water texture: the shipped normal map, three scrolled layers,
	// scaled by the row ripple strength. With no maps bound the surface
	// keeps the wave field above, which is the draw this port shipped.
	if (uWaterMaps > 0.5) {
		surface.xz += hdWaterRipples(vWorldPosition.xz, phase);
		surface = normalize(surface);
	}
	vec3 normal = normalize(vNormalX * surface.x
		+ vNormalY * surface.y + vNormalZ * surface.z);
	vec3 view = normalize(-vEyePosition);
	float facing = clamp(dot(normal, view), 0.0, 1.0);
	float finalFresnel = hdWaterFresnel634(facing);
	// 634HD's reflection: the area's tint as a gradient by the Fresnel, with
	// the sky the cube holds mixed over it at the mirror share - a pane of
	// glass with the trees in it is what a full mirror reads as - and bent
	// by the normal's lean, as upstream bends the mirrored scene map.
	vec3 bent = reflect(view, normal);
	bent.xz += normal.xz * HD_WATER_REFLECTION_BEND;
	vec3 reflected = textureCube(uEnvironment, bent).rgb;
	vec3 mirror = mix(gl_Fog.color.rgb, reflected, clamp(uReflection, 0.0, 1.0));
	vec3 reflection = mix(hdWaterTint634(uShallowColour, finalFresnel),
		mirror * finalFresnel,
		HD_WATER_MIRROR_SHARE * clamp(uReflection, 0.0, 1.0));
	// The shore foam mask, on the world coordinates upstream samples.
	float foamMask = hdWaterFoamTexel(vWorldPosition.xz, phase);
	// 634HD's surface: the type's colour, tint and depth absorption, which
	// is the body the stock material's white texture used to stand in for.
	vec3 lit = hdWaterBody634(vEyePosition, normal, view, normalize(vNormalY), vFoam, foamMask, reflection).rgb;
	// The Caustics row's net over the surface, the same field the seabed reads:
	// a share of the light already there where the web is bright.
	// 634HD's surface paints no caustic net: the net is the seabed's, lit
	// by the terrain pass under this water. On the surface it read as a net
	// over a flat sheet, which is what the stock water texture did.
	vec3 colour = clamp(lit, 0.0, 1.0);
	float fogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);
	// 634HD's water is a composed surface, not a translucent overlay, so
	// the vertex colour's alpha must not thin it: terrain showing through
	// the waves is the see-through water this row exists to fix.
	gl_FragColor = vec4(mix(gl_Fog.color.rgb, colour, fogFactor), 1.0);
}
