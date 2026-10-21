package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

/**
 * 2D sprite owned by the LWJGL renderer, replacing the {@code SpriteGL}
 * role for the upload and draw steps.
 *
 * <p>Pixels upload once through the int (BGRA) path, mirroring the
 * {@code method4265} construction; the blank constructor mirrors the
 * screen-capture {@code createNativeSprite} shape with zeroed storage.
 * Draws bind the texture on unit 0, install the combine preset through
 * the toolkit (ARGB tint plus alpha-blend switch), and emit one textured
 * quad through the frame seam, mirroring the {@code glBegin(7)} passes.
 * The fourth parallelogram corner is rebuilt as top-right plus
 * bottom-left minus top-left exactly like the legacy path.
 *
 * <p>Textures upload exact-size (no power-of-two padding), so the legacy
 * {@code s}/{@code captureScreenRegion} fractions are always 1.0 here.
 * The masked quad ({@link #drawWithSurface}) needs an {@link LwjglMask},
 * whose exact-size mask texture supplies unit 1 with the same
 * {@code (7681, 8448)} + {@code (0, 768, 34168)} setup and blend mode 1
 * as the legacy pass, torn down with {@code (0, 768, 5890)}, combine
 * preset 0 and a null rebind before returning to unit 0. The channel
 * path mirrors {@code method5295}/{@code method5291}: unit 1 takes the
 * sprite with {@code (method1962(op), 7681)} + {@code (1, 768, 34167)} +
 * texgen, unit 0 takes the mask with {@code (34479, 7681)} +
 * {@code (1, 768, 34166)} plus the per-channel texenv colour.
 *
 * <p>{@link #copyAlpha} mirrors the legacy transfer setup (0.5/0.499
 * scale/bias pairs) and snapshots the framebuffer into an exact-size
 * mask texture; modern GL needs no power-of-two/rectangle fallback, so
 * the {@code Static331.method4516} branch collapses to the direct copy.
 * {@link #captureScreenRegion} mirrors {@code method4193} including its
 * dead source parameters: the copy reads from x=0 with a canvas-height
 * Y flip into the given destination rect.
 *
 * <p>Using a sprite after {@link #delete()} throws, matching the buffer
 * lifecycle.
 */
public final class LwjglSprite extends NativeSprite {

    /** 2D texture target (legacy literal 3553). */
    public static final int TARGET_TEXTURE_2D = 0x0DE1;

    /** RGBA8 internal format (legacy literal 6408). */
    public static final int FORMAT_RGBA8 = 0x1908;

    /** Alpha internal format for channel masks (legacy literal 6406). */
    public static final int FORMAT_ALPHA = 0x1906;

    private final LwjglToolkit toolkit;
    private final LwjglTexture2D texture;
    private final int width;
    private final int height;
    private int offsetX;
    private int offsetY;
    private int trimRight;
    private int trimBottom;
    private boolean stretched;
    private LwjglTexture2D channelMaskTexture;
    private int channelMaskIndex;
    private boolean deleted;

    /**
     * @param toolkit owning toolkit.
     * @param width sprite width in texels.
     * @param height sprite height in texels.
     * @param pixels ARGB rows, {@code stride} ints per row.
     * @param stride row stride in texels, at least {@code width}.
     */
    public LwjglSprite(LwjglToolkit toolkit, int width, int height, int[] pixels, int stride) {
        if (toolkit == null || width <= 0 || height <= 0 || pixels == null || stride < width
            || pixels.length < stride * (height - 1) + width) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.width = width;
        this.height = height;
        int[] packed = pixels;
        if (stride != width) {
            packed = new int[width * height];
            for (int y = 0; y < height; y++) {
                System.arraycopy(pixels, y * stride, packed, y * width, width);
            }
        }
        this.texture = new LwjglTexture2D(toolkit, TARGET_TEXTURE_2D, FORMAT_RGBA8, width, height,
            false, packed, false, toolkit.textureOps());
    }

    /**
     * @param toolkit owning toolkit.
     * @param width sprite width in texels.
     * @param height sprite height in texels.
     * @param pixels tightly packed ARGB texels.
     */
    public LwjglSprite(LwjglToolkit toolkit, int width, int height, int[] pixels) {
        this(toolkit, width, height, pixels, width);
    }

    /**
     * Blank sprite mirroring the screen-capture {@code createNativeSprite}
     * shape; capture fills it through
     * {@link #captureScreenRegion(int, int, int, int, int, int)}.
     *
     * @param toolkit owning toolkit.
     * @param width sprite width in texels.
     * @param height sprite height in texels.
     */
    public LwjglSprite(LwjglToolkit toolkit, int width, int height) {
        if (toolkit == null || width <= 0 || height <= 0) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.width = width;
        this.height = height;
        this.texture = new LwjglTexture2D(toolkit, TARGET_TEXTURE_2D, FORMAT_RGBA8, width, height,
            toolkit.textureOps());
    }

    /** @return backing texture. */
    public LwjglTexture2D texture() {
        return this.texture;
    }

    /** @return channel mask texture, null until {@link #copyAlpha}. */
    public LwjglTexture2D channelMaskTexture() {
        return this.channelMaskTexture;
    }

    /** @return channel index installed by {@link #copyAlpha}. */
    public int channelMaskIndex() {
        return this.channelMaskIndex;
    }

    /** @return true after {@link #delete()}. */
    public boolean isDeleted() {
        return this.deleted;
    }

    /** Deletes the backing texture and the channel mask. Idempotent. */
    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            this.texture.delete();
            if (this.channelMaskTexture != null) {
                this.channelMaskTexture.delete();
                this.channelMaskTexture = null;
            }
        }
    }

    /** @return drawn width after trimming, never negative. */
    public int drawnWidth() {
        return Math.max(0, this.width - this.trimRight);
    }

    /** @return drawn height after trimming, never negative. */
    public int drawnHeight() {
        return Math.max(0, this.height - this.trimBottom);
    }

    private void checkLive() {
        if (this.deleted) {
            throw new RuntimeException();
        }
    }

    private void drawQuad(int x, int y, int w, int h, int combineMode, int colorArgb, int blendMode) {
        this.checkLive();
        if (w <= 0 || h <= 0) {
            return;
        }
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.texture);
        this.toolkit.applyCombine(combineMode, colorArgb, blendMode);
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.beginQuads();
        frame.texCoord2f(0.0F, 0.0F);
        frame.vertex2i(x, y);
        frame.texCoord2f(0.0F, 1.0F);
        frame.vertex2i(x, y + h);
        frame.texCoord2f(1.0F, 1.0F);
        frame.vertex2i(x + w, y + h);
        frame.texCoord2f(1.0F, 0.0F);
        frame.vertex2i(x + w, y);
        frame.end();
    }

    /**
     * Installs the two-unit channel-mask combine, mirroring
     * {@code method5295}: unit 1 takes the sprite with
     * {@code (method1962(op), 7681)} + {@code (1, 768, 34167)} + texgen,
     * unit 0 takes the mask with {@code (34479, 7681)} +
     * {@code (1, 768, 34166)} plus the per-channel texenv colour.
     */
    private void setupChannelMask(int op) {
        this.toolkit.selectTextureUnit(1);
        this.toolkit.bindTexture(this.texture);
        this.toolkit.setCombineRgbAlpha(LwjglCombine.rgbOp(op), 7681);
        this.toolkit.setTexCombine(1, 768, 34167);
        this.toolkit.setTexCoordGen(34168, 0);
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.channelMaskTexture);
        this.toolkit.setCombineRgbAlpha(LwjglCombine.COMBINE_DOT3_RGBA, 7681);
        this.toolkit.setTexCombine(1, 768, 34166);
        float[] channel = LwjglCombine.channelEnvColor(this.channelMaskIndex);
        this.toolkit.setTexEnvColor4f(channel[0], channel[1], channel[2], channel[3]);
    }

    /**
     * Tears down the channel-mask combine, mirroring
     * {@code method5291}: unit 1 unbinds and resets, unit 0 restores its
     * operand slot.
     */
    private void teardownChannelMask() {
        this.toolkit.selectTextureUnit(1);
        this.toolkit.bindTexture(null);
        this.toolkit.setCombineRgbAlpha(8448, 8448);
        this.toolkit.setTexCombine(1, 768, 34168);
        this.toolkit.setTexCoordGen(5890, 0);
        this.toolkit.selectTextureUnit(0);
        this.toolkit.setTexCombine(1, 768, 34168);
    }

    private void maskedQuad(int x0, int y0, float u0, float v0, float u1, float v1) {
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.beginQuads();
        frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_0, 0.0F, 0.0F);
        frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, u0, v0);
        frame.vertex2i(x0, y0);
        frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_0, 0.0F, 1.0F);
        frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, u0, v1);
        frame.vertex2i(x0, y0 + this.height);
        frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_0, 1.0F, 1.0F);
        frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, u1, v1);
        frame.vertex2i(x0 + this.width, y0 + this.height);
        frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_0, 1.0F, 0.0F);
        frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, u1, v0);
        frame.vertex2i(x0 + this.width, y0);
        frame.end();
    }

    private void channelQuad(int x, int y, int w, int h, boolean ints) {
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.beginQuads();
        if (ints) {
            frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, 0.0F, 0.0F);
            frame.texCoord2f(0.0F, 0.0F);
            frame.vertex2i(x, y);
            frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, 0.0F, 1.0F);
            frame.texCoord2f(0.0F, 1.0F);
            frame.vertex2i(x, y + h);
            frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, 1.0F, 1.0F);
            frame.texCoord2f(1.0F, 1.0F);
            frame.vertex2i(x + w, y + h);
            frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, 1.0F, 0.0F);
            frame.texCoord2f(1.0F, 0.0F);
            frame.vertex2i(x + w, y);
        } else {
            frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, 0.0F, 0.0F);
            frame.texCoord2f(0.0F, 0.0F);
            frame.vertex2f((float) x, (float) y);
            frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, 0.0F, 1.0F);
            frame.texCoord2f(0.0F, 1.0F);
            frame.vertex2f((float) x, (float) (y + h));
            frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, 1.0F, 1.0F);
            frame.texCoord2f(1.0F, 1.0F);
            frame.vertex2f((float) (x + w), (float) (y + h));
            frame.multiTexCoord2f(LwjglToolkit.UNIT_TEXCOORD_1, 1.0F, 0.0F);
            frame.texCoord2f(1.0F, 0.0F);
            frame.vertex2f((float) (x + w), (float) y);
        }
        frame.end();
    }

    private void drawParallelogram(float topLeftX, float topLeftY, float topRightX, float topRightY,
        float bottomLeftX, float bottomLeftY) {
        this.checkLive();
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.texture);
        this.parallelogram(topLeftX, topLeftY, topRightX, topRightY, bottomLeftX, bottomLeftY);
    }

    private void parallelogram(float topLeftX, float topLeftY, float topRightX, float topRightY,
        float bottomLeftX, float bottomLeftY) {
        float bottomRightX = topRightX + bottomLeftX - topLeftX;
        float bottomRightY = topRightY + bottomLeftY - topLeftY;
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.beginQuads();
        frame.texCoord2f(0.0F, 0.0F);
        frame.vertex2f(topLeftX, topLeftY);
        frame.texCoord2f(1.0F, 0.0F);
        frame.vertex2f(topRightX, topRightY);
        frame.texCoord2f(0.0F, 1.0F);
        frame.vertex2f(bottomLeftX, bottomLeftY);
        frame.texCoord2f(1.0F, 1.0F);
        frame.vertex2f(bottomRightX, bottomRightY);
        frame.end();
    }

    private void tiledQuad(int x, int y, int w, int h, int colorArgb) {
        float u = (float) w / (float) this.width;
        float v = (float) h / (float) this.height;
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.color4f((float) (colorArgb >> 16 & 0xFF) / 255.0F, (float) (colorArgb >> 8 & 0xFF) / 255.0F,
            (float) (colorArgb & 0xFF) / 255.0F, (float) (colorArgb >>> 24) / 255.0F);
        frame.beginQuads();
        frame.texCoord2f(0.0F, v);
        frame.vertex2i(x, y);
        frame.texCoord2f(0.0F, 0.0F);
        frame.vertex2i(x, y + h);
        frame.texCoord2f(u, 0.0F);
        frame.vertex2i(x + w, y + h);
        frame.texCoord2f(u, v);
        frame.vertex2i(x + w, y);
        frame.end();
    }

    @Override
    public void drawScaledColored(int x, int y, int width, int height, int combineMode, int colorArgb, int blendMode) {
        this.checkLive();
        if (width <= 0 || height <= 0) {
            return;
        }
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.texture);
        this.toolkit.setBlendMode(blendMode);
        this.toolkit.setCombine(combineMode);
        if (!this.stretched) {
            this.tiledQuad(x, y, width, height, colorArgb);
            return;
        }
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.color4f((float) (colorArgb >> 16 & 0xFF) / 255.0F, (float) (colorArgb >> 8 & 0xFF) / 255.0F,
            (float) (colorArgb & 0xFF) / 255.0F, (float) (colorArgb >>> 24) / 255.0F);
        frame.pushMatrix();
        frame.translatef((float) this.offsetX, (float) this.offsetY, 0.0F);
        int stepX = this.offsetX + this.width + this.trimRight;
        int stepY = this.offsetY + this.height + this.trimBottom;
        int x1 = x + width;
        int y1 = y + height;
        int rowY = y;
        while (rowY + this.height <= y1) {
            this.gridRow(x, rowY, x1, 0.0F, 1.0F, false);
            rowY += stepY;
        }
        if (rowY < y1) {
            float visH = (float) (y1 - rowY) / (float) this.height;
            this.gridRow(x, rowY, x1, 0.0F, visH, true);
        }
        frame.popMatrix();
    }

    private void gridRow(int x, int rowY, int x1, float vTop, float vBottom, boolean partial) {
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.beginQuads();
        int colX = x;
        while (colX + this.width <= x1) {
            this.gridTile(frame, colX, rowY, 0.0F, 1.0F, vTop, vBottom);
            colX += this.offsetX + this.width + this.trimRight;
        }
        if (colX < x1) {
            float visW = (float) (x1 - colX) / (float) this.width;
            this.gridTile(frame, colX, rowY, 0.0F, visW, vTop, vBottom);
        }
        frame.end();
    }

    private void gridTile(LwjglFrameOps frame, int colX, int rowY, float uLeft, float uRight, float vTop, float vBottom) {
        int tileW = (int) ((uRight - uLeft) * (float) this.width);
        int tileH = (int) ((vBottom - vTop) * (float) this.height);
        frame.texCoord2f(uLeft, vTop);
        frame.vertex2i(colX, rowY);
        frame.texCoord2f(uLeft, vBottom);
        frame.vertex2i(colX, rowY + tileH);
        frame.texCoord2f(uRight, vBottom);
        frame.vertex2i(colX + tileW, rowY + tileH);
        frame.texCoord2f(uRight, vTop);
        frame.vertex2i(colX + tileW, rowY);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public void copyAlpha(int format, int width, int channel) {
        this.checkLive();
        if (width <= 0 || channel < 0 || channel > 3) {
            throw new RuntimeException();
        }
        LwjglTextureOps ops = this.toolkit.textureOps();
        ops.pixelTransferf(3348, 0.5F);
        ops.pixelTransferf(3349, 0.499F);
        ops.pixelTransferf(3352, 0.5F);
        ops.pixelTransferf(3353, 0.499F);
        ops.pixelTransferf(3354, 0.5F);
        ops.pixelTransferf(3355, 0.499F);
        if (this.channelMaskTexture != null) {
            this.channelMaskTexture.delete();
        }
        this.channelMaskTexture = new LwjglTexture2D(this.toolkit, TARGET_TEXTURE_2D, FORMAT_RGBA8,
            width, this.height, ops);
        this.channelMaskTexture.copySubRegion(0, 0, 0, Math.max(0, this.toolkit.canvasHeight() - this.height),
            width, this.height, ops);
        ops.pixelTransferf(3348, 1.0F);
        ops.pixelTransferf(3349, 0.0F);
        ops.pixelTransferf(3352, 1.0F);
        ops.pixelTransferf(3353, 0.0F);
        ops.pixelTransferf(3354, 1.0F);
        ops.pixelTransferf(3355, 0.0F);
        this.channelMaskIndex = channel;
    }

    @Override
    public void setOffsets(int translateX, int translateY, int trimRight, int trimBottom) {
        this.offsetX = translateX;
        this.offsetY = translateY;
        this.trimRight = trimRight;
        this.trimBottom = trimBottom;
        this.stretched = translateX != 0 || translateY != 0 || trimRight != 0 || trimBottom != 0;
    }

    @Override
    public void render(int x, int y, int combineMode, int colorArgb, int blendMode) {
        this.checkLive();
        if (this.channelMaskTexture == null) {
            this.drawQuad(x + this.offsetX, y + this.offsetY, this.drawnWidth(), this.drawnHeight(),
                combineMode, colorArgb, blendMode);
            return;
        }
        this.toolkit.setBlendMode(blendMode);
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.texture);
        this.setupChannelMask(combineMode);
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.color4f((float) (colorArgb >> 16 & 0xFF) / 255.0F, (float) (colorArgb >> 8 & 0xFF) / 255.0F,
            (float) (colorArgb & 0xFF) / 255.0F, (float) (colorArgb >>> 24) / 255.0F);
        this.channelQuad(x + this.offsetX, y + this.offsetY, this.drawnWidth(), this.drawnHeight(), true);
        this.teardownChannelMask();
    }

    @Override
    protected void a(float topLeftX, float topLeftY, float topRightX, float topRightY, float bottomLeftX,
        float bottomLeftY, int combineMode, int colorArgb, int arg8) {
        this.checkLive();
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.texture);
        this.toolkit.applyCombine(combineMode, colorArgb, arg8 == 1 ? LwjglToolkit.BLEND_ALPHA : LwjglToolkit.BLEND_NONE);
        this.parallelogram(topLeftX, topLeftY, topRightX, topRightY, bottomLeftX, bottomLeftY);
    }

    @Override
    protected void renderParallelogramImpl(float topLeftX, float topLeftY, float topRightX, float topRightY,
        float bottomLeftX, float bottomLeftY, ComponentSurface arg6, int arg7, int surfaceOffsetX) {
        this.drawParallelogram(topLeftX, topLeftY, topRightX, topRightY, bottomLeftX, bottomLeftY);
    }

    @Override
    public int scaleHeight() {
        return this.drawnHeight();
    }

    @Override
    public void captureScreenRegion(int dstX, int dstY, int width, int height, int srcX, int srcY) {
        this.checkLive();
        if (width <= 0 || height <= 0) {
            throw new RuntimeException();
        }
        this.texture.copySubRegion(dstX, this.texture.height() - dstY - height, 0,
            Math.max(0, this.toolkit.canvasHeight() - height), width, height, this.toolkit.textureOps());
    }

    @Override
    public void renderImpl(int x, int y, int width, int height, int combineMode, int colorArgb, int blendMode) {
        this.checkLive();
        if (this.channelMaskTexture == null) {
            this.drawQuad(x + this.offsetX, y + this.offsetY, width, height, combineMode, colorArgb, blendMode);
            return;
        }
        this.toolkit.setBlendMode(blendMode);
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.texture);
        this.setupChannelMask(combineMode);
        LwjglFrameOps frame = this.toolkit.frameOps();
        frame.color4f((float) (colorArgb >> 16 & 0xFF) / 255.0F, (float) (colorArgb >> 8 & 0xFF) / 255.0F,
            (float) (colorArgb & 0xFF) / 255.0F, (float) (colorArgb >>> 24) / 255.0F);
        this.channelQuad(x + this.offsetX, y + this.offsetY, width, height, true);
        this.teardownChannelMask();
    }

    @Override
    public int scaleWidth() {
        return this.drawnWidth();
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void drawWithSurface(int x, int y, ComponentSurface surface, int surfaceOffsetX, int surfaceOffsetY) {
        this.checkLive();
        if (!(surface instanceof LwjglMask)) {
            throw new RuntimeException();
        }
        LwjglMask mask = (LwjglMask) surface;
        int x0 = x + this.offsetX;
        int y0 = y + this.offsetY;
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.texture);
        this.toolkit.setCombine(1);
        this.toolkit.selectTextureUnit(1);
        this.toolkit.bindTexture(mask.texture());
        this.toolkit.setCombineRgbAlpha(7681, 8448);
        this.toolkit.setTexCombine(0, 768, 34168);
        this.toolkit.setBlendMode(LwjglToolkit.BLEND_ALPHA);
        float u0 = (float) (x0 - surfaceOffsetX) / (float) mask.width();
        float u1 = (float) (x0 + this.width - surfaceOffsetX) / (float) mask.width();
        float v0 = (float) (y0 - surfaceOffsetY) / (float) mask.height();
        float v1 = (float) (y0 + this.height - surfaceOffsetY) / (float) mask.height();
        this.toolkit.frameOps().color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.maskedQuad(x0, y0, u0, v0, u1, v1);
        this.toolkit.setTexCombine(0, 768, 5890);
        this.toolkit.setCombine(0);
        this.toolkit.bindTexture(null);
        this.toolkit.selectTextureUnit(0);
    }
}
