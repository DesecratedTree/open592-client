package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.GL11;

/**
 * Cube-map texture owned by the LWJGL renderer, replacing the
 * {@code GlCubeTexture} role. Each of the six faces uploads like a 2D
 * image; mipmapped uploads store the base faces and generate the pyramid
 * on the GPU (see {@link LwjglTexture2D}).
 *
 * <p>Framebuffer attach/detach mirrors the legacy methods, including the
 * legacy detach quirk of passing the 2D target instead of the cube face
 * target (harmless: the textarget is ignored when detaching).
 */
public class LwjglCubeTexture extends LwjglTexture {

    private final int size;

    private int framebufferTarget = -1;
    private int framebufferAttachment = -1;

    /**
     * Byte-face cube, mirroring the legacy byte constructor.
     */
    public LwjglCubeTexture(LwjglToolkit toolkit, int internalFormat, int size, boolean mipmapped, byte[][] faces, int format, LwjglTextureOps ops) {
        super(toolkit, TARGET_CUBE_MAP, internalFormat, size * size * 6, false, ops);
        if (size <= 0 || faces == null || faces.length != 6) {
            throw new RuntimeException();
        }
        this.size = size;
        this.bind();
        for (int face = 0; face < 6; face++) {
            if (faces[face] == null) {
                throw new RuntimeException();
            }
            ops.texImage2D(TARGET_CUBE_MAP_POSITIVE_X + face, 0, internalFormat, size, size, format, LwjglTexture2D.TYPE_UNSIGNED_BYTE, faces[face], 0);
        }
        if (mipmapped) {
            this.setMipmapped();
            ops.generateMipmap(TARGET_CUBE_MAP);
        }
        this.setSmoothing(true);
    }

    /**
     * Int-face cube (BGRA client data), mirroring the legacy int
     * constructor.
     */
    public LwjglCubeTexture(LwjglToolkit toolkit, int internalFormat, int size, boolean mipmapped, int[][] faces, LwjglTextureOps ops) {
        super(toolkit, TARGET_CUBE_MAP, internalFormat, size * size * 6, false, ops);
        if (size <= 0 || faces == null || faces.length != 6) {
            throw new RuntimeException();
        }
        this.size = size;
        this.bind();
        for (int face = 0; face < 6; face++) {
            if (faces[face] == null) {
                throw new RuntimeException();
            }
            ops.texImage2D(TARGET_CUBE_MAP_POSITIVE_X + face, 0, internalFormat, size, size, LwjglTexture2D.FORMAT_BGRA, LwjglTextureFormat.PIXEL_TYPE_UNSIGNED_BYTE, faces[face], 0);
        }
        if (mipmapped) {
            this.setMipmapped();
            ops.generateMipmap(TARGET_CUBE_MAP);
        }
        this.setSmoothing(true);
    }

    /**
     * Typeless allocation with no client data, mirroring the legacy
     * allocation constructor.
     */
    public LwjglCubeTexture(LwjglToolkit toolkit, int internalFormat, int size, LwjglTextureOps ops) {
        super(toolkit, TARGET_CUBE_MAP, internalFormat, size * size * 6, false, ops);
        if (size <= 0) {
            throw new RuntimeException();
        }
        this.size = size;
        int format = LwjglTextureFormat.formatForInternalFormat(internalFormat);
        this.bind();
        for (int face = 0; face < 6; face++) {
            ops.allocateImage2D(TARGET_CUBE_MAP_POSITIVE_X + face, 0, internalFormat, size, size, format, LwjglTexture2D.TYPE_UNSIGNED_BYTE);
        }
        this.setSmoothing(true);
    }

    /** @return cube face width (and height) in texels. */
    public int size() {
        return this.size;
    }

    /**
     * Attaches one face to a framebuffer object, mirroring
     * {@code GlCubeTexture.method2754}.
     */
    public void attachToFramebuffer(int target, int face, int attachment, LwjglTextureOps ops) {
        if (this.isDeleted()) {
            throw new RuntimeException();
        }
        ops.framebufferTexture2D(target, attachment, TARGET_CUBE_MAP_POSITIVE_X + face, this.id(), 0);
        this.framebufferAttachment = attachment;
        this.framebufferTarget = target;
    }

    /**
     * Detaches from the framebuffer, mirroring
     * {@code GlCubeTexture.detach}.
     */
    public void detachFromFramebuffer(LwjglTextureOps ops) {
        ops.framebufferTexture2D(this.framebufferTarget, this.framebufferAttachment, TARGET_2D, 0, 0);
        this.framebufferTarget = -1;
        this.framebufferAttachment = -1;
    }
}
