package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;

/**
 * Font renderer owned by the LWJGL renderer, replacing the {@code GlFont}
 * role for the glyph atlas and display-list steps.
 *
 * <p>Construction mirrors the legacy packing: the cell is the largest
 * glyph dimension, the 256 glyphs tile a cell-by-16 atlas, and each glyph
 * compiles one list drawing its quad at its (minX, minY) offset with
 * atlas-fraction UVs flipped against the top edge, exactly like the legacy
 * {@code captureScreenRegion}-relative coordinates on an exactly-sized
 * upload. The atlas uploads as one byte-per-texel 6406 image, mirroring
 * {@code Class28.method671}. Draws bind the atlas, install the ARGB
 * colour, translate to the pen and call the glyph list, mirroring the
 * {@code OA} path; the mask texgen of the full path stays with the future
 * combine step.
 */
public final class LwjglFont extends AbstractFontRenderer {

    /** Alpha internal/client format for the glyph atlas (legacy 6406). */
    public static final int FORMAT_ALPHA = 0x1906;

    private final LwjglToolkit toolkit;
    private final LwjglTexture2D atlas;
    private final LwjglRenderList glyphs;
    private final int cell;
    private final int atlasSize;

    /**
     * @param toolkit owning toolkit.
     * @param metrics font metrics, held for the layout step.
     * @param images 256 glyph images in code order.
     */
    public LwjglFont(LwjglToolkit toolkit, FontMetrics metrics, SpriteType[] images) {
        super(toolkit, metrics);
        if (toolkit == null || images == null || images.length < 256) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        int max = 0;
        for (int i = 0; i < 256; i++) {
            if (images[i] == null) {
                throw new RuntimeException();
            }
            if (images[i].height > max) {
                max = images[i].height;
            }
            if (images[i].width > max) {
                max = images[i].width;
            }
        }
        this.cell = max;
        this.atlasSize = max * 16;
        byte[] bits = new byte[this.atlasSize * this.atlasSize];
        for (int i = 0; i < 256; i++) {
            SpriteType glyph = images[i];
            byte[] rows = glyph.pixels;
            if (rows == null) {
                throw new RuntimeException();
            }
            int col0 = i % 16 * max;
            int row0 = i / 16 * max;
            int at = row0 * this.atlasSize + col0;
            int src = 0;
            for (int y = 0; y < glyph.height; y++) {
                for (int x = 0; x < glyph.width; x++) {
                    bits[at++] = (byte) (rows[src++] == 0 ? 0 : -1);
                }
                at += this.atlasSize - glyph.width;
            }
        }
        this.atlas = new LwjglTexture2D(toolkit, LwjglSprite.TARGET_TEXTURE_2D, FORMAT_ALPHA,
            this.atlasSize, this.atlasSize, false, bits, FORMAT_ALPHA, false, toolkit.textureOps());
        this.glyphs = new LwjglRenderList(toolkit.listOps(), 256);
        LwjglFrameOps frame = toolkit.frameOps();
        for (int i = 0; i < 256; i++) {
            SpriteType glyph = images[i];
            float left = (float) (i % 16 * max) / (float) this.atlasSize;
            float top = (float) (i / 16 * max) / (float) this.atlasSize;
            float u1 = left + (float) glyph.width / (float) this.atlasSize;
            float vTop = 1.0F - top;
            float vBottom = 1.0F - (top + (float) glyph.height / (float) this.atlasSize);
            int ox = glyph.minX;
            int oy = glyph.minY;
            int w = glyph.width;
            int h = glyph.height;
            this.glyphs.beginList(i);
            frame.beginQuads();
            frame.texCoord2f(left, vTop);
            frame.vertex2i(ox, oy);
            frame.texCoord2f(left, vBottom);
            frame.vertex2i(ox, oy + h);
            frame.texCoord2f(u1, vBottom);
            frame.vertex2i(ox + w, oy + h);
            frame.texCoord2f(u1, vTop);
            frame.vertex2i(ox + w, oy);
            frame.end();
            this.glyphs.endList();
        }
    }

    /** @return glyph atlas texture. */
    public LwjglTexture2D atlas() {
        return this.atlas;
    }

    /** @return compiled glyph lists, index = code point. */
    public LwjglRenderList glyphs() {
        return this.glyphs;
    }

    /** @return atlas cell edge in texels. */
    public int cell() {
        return this.cell;
    }

    /** @return atlas edge in texels. */
    public int atlasSize() {
        return this.atlasSize;
    }

    private void drawGlyph(char c, int x, int y, int colour) {
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.atlas);
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.color4f((float) (colour >> 16 & 0xFF) / 255.0F, (float) (colour >> 8 & 0xFF) / 255.0F,
            (float) (colour & 0xFF) / 255.0F, (float) (colour >>> 24) / 255.0F);
        frame.translatef((float) x, (float) y, 0.0F);
        this.glyphs.callList(c);
        frame.loadIdentity();
    }

    @Override
    protected void renderSymbol(char c, int x, int y, int colour, boolean shadow, ComponentSurface mask,
        int offsetX, int offsetY) {
        this.drawGlyph(c, x, y, colour);
    }

    @Override
    protected void OA(char c, int x, int y, int colour, boolean shadow) {
        this.drawGlyph(c, x, y, colour);
    }
}
