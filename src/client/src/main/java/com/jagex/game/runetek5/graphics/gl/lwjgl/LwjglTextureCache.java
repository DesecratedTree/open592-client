package com.jagex.game.runetek5.graphics.gl.lwjgl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.unpack_Sub1;

/**
 * Material texture cache for the LWJGL renderer, replacing the
 * {@code OpenGLTextureCache} role. Lookup mirrors
 * {@code method1549} exactly: LRU hit, miss builds from the material
 * provider (float 32F path when the toolkit reports float textures,
 * otherwise the RGB/ARGB int path gated by the {@code Class133.method3349}
 * upload-kind check), unavailable materials return null.
 *
 * <p>Capacity is 256 entries in access order; inserting past capacity
 * deletes the eldest GL texture, mirroring the legacy LRU eviction.
 * {@link #trim()} mirrors {@code method1545}: entries untouched since the
 * last five trims are deleted. The legacy path only demotes those entries
 * to soft references, but without a soft-ref heap here outright deletion
 * keeps GL object lifetime explicit. {@link #clear()} mirrors
 * {@code method1550}.
 */
public final class LwjglTextureCache {

    /** Cache capacity, mirroring the legacy 256-entry LRU. */
    public static final int CAPACITY = 256;

    /** Trim age limit, mirroring the {@code method1788(5)} threshold. */
    public static final int MAX_TRIM_AGE = 5;

    private static final class Entry {
        final LwjglTexture2D texture;
        int age;

        Entry(LwjglTexture2D texture) {
            this.texture = texture;
        }
    }

    private final LwjglToolkit toolkit;
    private final unpack_Sub1 provider;
    private final LwjglTextureOps ops;
    private final boolean floatTextures;
    private final LinkedHashMap<Long, Entry> entries = new LinkedHashMap<Long, Entry>(CAPACITY, 0.75F, true);

    /**
     * @param toolkit owning toolkit (size and float-texture capability).
     * @param provider material provider, may be null until wired.
     * @param ops GL seam; {@link LwjglTextureGl#get()} in game code.
     */
    public LwjglTextureCache(LwjglToolkit toolkit, unpack_Sub1 provider, LwjglTextureOps ops) {
        this(toolkit, provider, ops, toolkit != null && toolkit.method4301());
    }

    LwjglTextureCache(LwjglToolkit toolkit, unpack_Sub1 provider, LwjglTextureOps ops, boolean floatTextures) {
        if (toolkit == null || ops == null) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.provider = provider;
        this.ops = ops;
        this.floatTextures = floatTextures;
    }

    /**
     * Returns the cached texture for a material id, building and caching it
     * on first use. Returns null when the provider has no such material.
     */
    public synchronized LwjglTexture2D texture(int id) {
        Entry cached = this.entries.get(Long.valueOf(id & 0xFFFFFFFFL));
        if (cached != null) {
            cached.age = 0;
            return cached.texture;
        }
        if (this.provider == null || !this.provider.textureAvailable(id)) {
            return null;
        }
        MaterialType metrics = this.provider.getMetrics(id);
        int size = metrics.isSmall ? 64 : this.toolkit.textureSize();
        LwjglTexture2D texture;
        if (metrics.aBoolean638 && this.floatTextures) {
            float[] pixels = this.provider.method2394(0.7F, id, size, size);
            texture = new LwjglTexture2D(this.toolkit, LwjglTexture.TARGET_2D, 34842, size, size, metrics.aByte92 != 0, pixels, LwjglTexture2D.FORMAT_RGBA, this.ops);
        } else {
            int[] pixels;
            int format;
            if (metrics.aBoolean640 && LwjglTextureFormat.isDirectRgbUpload(metrics.aByte89)) {
                pixels = this.provider.getPixels(false, id, 0.7F, size, size);
                format = LwjglTexture2D.FORMAT_RGB;
            } else {
                pixels = this.provider.getPixelsArgb(id, 0.7F, size, size);
                format = LwjglTexture2D.FORMAT_RGBA;
            }
            texture = new LwjglTexture2D(this.toolkit, LwjglTexture.TARGET_2D, format, size, size, metrics.aByte92 != 0, pixels, false, this.ops);
        }
        texture.setWrap(metrics.aBoolean643, metrics.aBoolean644, this.ops);
        this.entries.put(Long.valueOf(id & 0xFFFFFFFFL), new Entry(texture));
        evictEldest();
        return texture;
    }

    /**
     * Ages every entry and deletes textures untouched for more than
     * {@link #MAX_TRIM_AGE} trims.
     */
    public synchronized void trim() {
        Iterator<Map.Entry<Long, Entry>> it = this.entries.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = it.next().getValue();
            entry.age++;
            if (entry.age > MAX_TRIM_AGE) {
                entry.texture.delete();
                it.remove();
            }
        }
    }

    /** Deletes every cached texture and empties the cache. */
    public synchronized void clear() {
        for (Entry entry : this.entries.values()) {
            entry.texture.delete();
        }
        this.entries.clear();
    }

    /** @return live entry count, for tests. */
    synchronized int size() {
        return this.entries.size();
    }

    private void evictEldest() {
        while (this.entries.size() > CAPACITY) {
            Iterator<Map.Entry<Long, Entry>> it = this.entries.entrySet().iterator();
            Entry eldest = it.next().getValue();
            eldest.texture.delete();
            it.remove();
        }
    }
}
