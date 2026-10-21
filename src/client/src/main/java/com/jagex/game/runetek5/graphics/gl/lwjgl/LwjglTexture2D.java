package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * 2D texture owned by the LWJGL renderer, replacing the
 * {@code OpenGl2DTexture} role. Upload paths mirror the legacy constructors:
 * byte, int (BGRA) and float (RGBA) full uploads with optional row flip,
 * typeless allocation, and framebuffer copies; the legacy CPU-side
 * compressed-mipmap uploaders have no LWJGL equivalent, so mipmapped uploads
 * store the base level and generate the pyramid on the GPU instead.
 *
 * <p>Wrap modes mirror {@code OpenGl2DTexture.method4196} (repeat vs
 * clamp-to-edge, 2D targets only) and framebuffer attach/detach mirrors
 * {@code method4195}/{@code detach}, using this texture's own target.
 */
public class LwjglTexture2D extends LwjglTexture {

    /** Byte pixel type. */
    public static final int TYPE_UNSIGNED_BYTE = GL11.GL_UNSIGNED_BYTE;

    /** Float pixel type. */
    public static final int TYPE_FLOAT = GL11.GL_FLOAT;

    /** BGRA client format for packed int uploads. */
    public static final int FORMAT_BGRA = GL12.GL_BGRA;

    /** RGBA client format. */
    public static final int FORMAT_RGBA = GL11.GL_RGBA;

    /** RGB client format. */
    public static final int FORMAT_RGB = GL11.GL_RGB;

    /** Luminance client format for byte sub-uploads. */
    public static final int FORMAT_LUMINANCE = 0x1906;

    private final int width;
    private final int height;

    private int framebufferTarget = -1;
    private int framebufferAttachment = -1;

    /**
     * Byte upload, mirroring the legacy byte constructor (row flip when
     * {@code flipY}, unpack alignment 1 during upload).
     */
    public LwjglTexture2D(LwjglToolkit toolkit, int target, int internalFormat, int width, int height, boolean mipmapped, byte[] pixels, int format, boolean flipY, LwjglTextureOps ops) {
        super(toolkit, target, internalFormat, width * height, false, ops);
        if (width <= 0 || height <= 0 || pixels == null) {
            throw new RuntimeException();
        }
        this.width = width;
        this.height = height;
        this.bind();
        ops.pixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        ops.texImage2D(target, 0, internalFormat, width, height, format, TYPE_UNSIGNED_BYTE, flipY ? flipRows(pixels, width, height, bytesPerRow(format, width)) : pixels, 0);
        ops.pixelStorei(GL11.GL_UNPACK_ALIGNMENT, 4);
        if (mipmapped) {
            this.setMipmapped();
            ops.generateMipmap(target);
        }
        this.setSmoothing(true);
    }

    /**
     * Int upload (BGRA client data), mirroring the legacy int constructor.
     */
    public LwjglTexture2D(LwjglToolkit toolkit, int target, int internalFormat, int width, int height, boolean mipmapped, int[] pixels, boolean flipY, LwjglTextureOps ops) {
        super(toolkit, target, internalFormat, width * height, false, ops);
        if (width <= 0 || height <= 0 || pixels == null) {
            throw new RuntimeException();
        }
        this.width = width;
        this.height = height;
        this.bind();
        int[] rows = flipY ? flipRows(pixels, width, height) : pixels;
        ops.texImage2D(target, 0, internalFormat, width, height, FORMAT_BGRA, LwjglTextureFormat.PIXEL_TYPE_UNSIGNED_BYTE, rows, 0);
        if (mipmapped) {
            this.setMipmapped();
            ops.generateMipmap(target);
        }
        this.setSmoothing(true);
    }

    /**
     * Float upload (RGBA client data), mirroring the legacy float
     * constructor.
     */
    public LwjglTexture2D(LwjglToolkit toolkit, int target, int internalFormat, int width, int height, boolean mipmapped, float[] pixels, int format, LwjglTextureOps ops) {
        super(toolkit, target, internalFormat, width * height, false, ops);
        if (width <= 0 || height <= 0 || pixels == null) {
            throw new RuntimeException();
        }
        this.width = width;
        this.height = height;
        this.bind();
        ops.texImage2D(target, 0, internalFormat, width, height, format, TYPE_FLOAT, pixels, 0);
        if (mipmapped) {
            this.setMipmapped();
            ops.generateMipmap(target);
        }
        this.setSmoothing(true);
    }

    /**
     * Typeless allocation with no client data, mirroring the legacy
     * allocation constructor.
     */
    public LwjglTexture2D(LwjglToolkit toolkit, int target, int internalFormat, int width, int height, LwjglTextureOps ops) {
        super(toolkit, target, internalFormat, width * height, false, ops);
        if (width <= 0 || height <= 0) {
            throw new RuntimeException();
        }
        this.width = width;
        this.height = height;
        this.bind();
        ops.allocateImage2D(target, 0, internalFormat, width, height, LwjglTextureFormat.formatForInternalFormat(internalFormat), TYPE_UNSIGNED_BYTE);
        this.setSmoothing(true);
    }

    /**
     * Framebuffer copy, mirroring the legacy copy constructor. The source
     * origin is already GL-style (bottom-left); callers flip the Y using
     * the canvas height, which the legacy constructor read from the
     * toolkit internally.
     */
    public LwjglTexture2D(LwjglToolkit toolkit, int target, int internalFormat, int copyX, int copyY, int width, int height, LwjglTextureOps ops) {
        super(toolkit, target, internalFormat, width * height, false, ops);
        if (width <= 0 || height <= 0) {
            throw new RuntimeException();
        }
        this.width = width;
        this.height = height;
        this.bind();
        ops.copyImage2D(target, 0, internalFormat, copyX, copyY, width, height);
        this.setSmoothing(true);
    }

    /** @return texture width in texels. */
    public int width() {
        return this.width;
    }

    /** @return texture height in texels. */
    public int height() {
        return this.height;
    }

    /**
     * Byte sub-upload, mirroring {@code OpenGl2DTexture.method4191}
     * (the dead row-length branches of the deobfuscated code are omitted).
     */
    public void uploadSubRegion(int width, int height, byte[] pixels, int format, boolean flipY, LwjglTextureOps ops) {
        if (this.isDeleted() || pixels == null) {
            throw new RuntimeException();
        }
        this.bind();
        ops.pixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        ops.texSubImage2D(this.target(), 0, 0, 0, width, height, format, TYPE_UNSIGNED_BYTE, flipY ? flipRows(pixels, width, height, bytesPerRow(format, width)) : pixels, 0);
        ops.pixelStorei(GL11.GL_UNPACK_ALIGNMENT, 4);
    }

    /**
     * Int sub-upload (BGRA client data), mirroring
     * {@code OpenGl2DTexture.method4197}.
     */
    public void uploadSubRegion(int width, int height, int[] pixels, LwjglTextureOps ops) {
        if (this.isDeleted() || pixels == null) {
            throw new RuntimeException();
        }
        int[] rows = new int[width * height];
        for (int row = 0; row < height; row++) {
            int src = (height - row - 1) * width;
            int dst = row * width;
            for (int col = 0; col < width; col++) {
                rows[dst++] = pixels[src++];
            }
        }
        this.bind();
        ops.texSubImage2D(this.target(), 0, 0, 0, width, height, FORMAT_BGRA, LwjglTextureFormat.PIXEL_TYPE_UNSIGNED_BYTE, rows, 0);
    }

    /**
     * Framebuffer region copy into this texture, mirroring
     * {@code OpenGl2DTexture.method4193}. The source origin is already
     * GL-style (bottom-left); callers flip the Y using the canvas height.
     */
    public void copySubRegion(int dstX, int dstY, int srcX, int srcY, int width, int height, LwjglTextureOps ops) {
        if (this.isDeleted()) {
            throw new RuntimeException();
        }
        this.bind();
        ops.copySubImage2D(this.target(), 0, dstX, dstY, srcX, srcY, width, height);
        ops.flush();
    }

    /**
     * Sets the S/T wrap modes, mirroring
     * {@code OpenGl2DTexture.method4196}: repeat when true,
     * clamp-to-edge when false, 2D targets only.
     */
    public void setWrap(boolean repeatS, boolean repeatT, LwjglTextureOps ops) {
        if (this.isDeleted()) {
            throw new RuntimeException();
        }
        if (this.target() == TARGET_2D) {
            this.bind();
            ops.texParameteri(this.target(), GL11.GL_TEXTURE_WRAP_S, repeatS ? GL11.GL_REPEAT : GL12.GL_CLAMP_TO_EDGE);
            ops.texParameteri(this.target(), GL11.GL_TEXTURE_WRAP_T, repeatT ? GL11.GL_REPEAT : GL12.GL_CLAMP_TO_EDGE);
        }
    }

    /**
     * Attaches this image to a framebuffer object, mirroring
     * {@code OpenGl2DTexture.method4195}.
     */
    public void attachToFramebuffer(int target, int attachment, LwjglTextureOps ops) {
        if (this.isDeleted()) {
            throw new RuntimeException();
        }
        ops.framebufferTexture2D(target, attachment, this.target(), this.id(), 0);
        this.framebufferAttachment = attachment;
        this.framebufferTarget = target;
    }

    /**
     * Detaches from the framebuffer, mirroring
     * {@code OpenGl2DTexture.detach}.
     */
    public void detachFromFramebuffer(LwjglTextureOps ops) {
        ops.framebufferTexture2D(this.framebufferTarget, this.framebufferAttachment, this.target(), 0, 0);
        this.framebufferTarget = -1;
        this.framebufferAttachment = -1;
    }

    private static int bytesPerRow(int format, int width) {
        return LwjglTextureFormat.bytesPerPixel(format) * width;
    }

    private static byte[] flipRows(byte[] pixels, int width, int height, int rowBytes) {
        byte[] flipped = new byte[pixels.length];
        for (int row = 0; row < height; row++) {
            System.arraycopy(pixels, (height - row - 1) * rowBytes, flipped, row * rowBytes, rowBytes);
        }
        return flipped;
    }

    private static int[] flipRows(int[] pixels, int width, int height) {
        int[] flipped = new int[pixels.length];
        for (int row = 0; row < height; row++) {
            System.arraycopy(pixels, (height - row - 1) * width, flipped, row * width, width);
        }
        return flipped;
    }
}
