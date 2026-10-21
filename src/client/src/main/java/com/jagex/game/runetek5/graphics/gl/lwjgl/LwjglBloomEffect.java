package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.GL11;

/**
 * Bloom post effect owned by the LWJGL renderer, replacing the
 * {@code RectangularTextureGL} role: bright-pass extraction into a 256x256
 * float buffer (with a mipmap downsample chain for larger scenes),
 * separable 17-tap blur ping-ponged between two buffers, and a final
 * scene-plus-bloom composite left active for the compositor's quad.
 *
 * <p>The four GLSL sources are verbatim copies of the legacy ones; only
 * the entry-point names change (program creation goes through
 * {@link LwjglShader}/{@link LwjglProgram}). Uniform values the legacy
 * code read from game globals at present time live on this effect
 * instead: {@link #setBloomParams} mirrors the tuning the stock
 * bright/composite passes use.
 *
 * <p>Like the legacy class, every pass assumes the compositor holds its
 * resolve target bound underneath: internal passes push this effect's own
 * framebuffers and pop them before the composite program is activated.
 */
public class LwjglBloomEffect implements LwjglPostEffect {

    private static final String BRIGHT_RECT_SOURCE = "#extension GL_ARB_texture_rectangle : enable\nuniform vec3 params;\nuniform sampler2DRect sceneTex;\nconst vec3 lumCoef = vec3(0.2126, 0.7152, 0.0722);\nvoid main() {\n    vec4 col = texture2DRect(sceneTex, gl_TexCoord[0].xy);\n    gl_FragColor = col*step(params.x, dot(lumCoef, col.rgb));\n}\n";

    private static final String BRIGHT_2D_SOURCE = "uniform vec3 params;\nuniform sampler2D sceneTex;\nconst vec3 lumCoef = vec3(0.2126, 0.7152, 0.0722);\nvoid main() {\n    vec4 col = texture2D(sceneTex, gl_TexCoord[0].xy);\n    gl_FragColor = col*step(params.x, dot(lumCoef, col.rgb));\n}\n";

    private static final String COMPOSITE_SOURCE = "#extension GL_ARB_texture_rectangle : enable\nuniform vec3 params;\nuniform vec3 dimScale;\nuniform sampler2D bloomTex;\nuniform sampler2DRect sceneTex;\nconst vec3 lumCoef = vec3(0.2126, 0.7152, 0.0722);\nvoid main() {\n\t vec4 bloomCol = texture2D(bloomTex, gl_TexCoord[1].xy);\n\t vec4 sceneCol = texture2DRect(sceneTex, gl_TexCoord[0].xy);\n\t float preLum = 0.99*dot(lumCoef, sceneCol.rgb)+0.01;\n    float postLum = preLum*(1.0+(preLum/params.y))/(preLum+1.0);\n\t gl_FragColor = sceneCol*(postLum/preLum)+bloomCol*params.x;\n}\n";

    private static final String BLUR_SOURCE = "uniform vec3 step;\nuniform sampler2D baseTex;\nvoid main() {\n\tvec4 fragCol = texture2D(baseTex, gl_TexCoord[0].xy)*0.091396265;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-1.0*step.xy))*0.088584304;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 1.0*step.xy))*0.088584304;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-2.0*step.xy))*0.08065692;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 2.0*step.xy))*0.08065692;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-3.0*step.xy))*0.068989515;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 3.0*step.xy))*0.068989515;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-4.0*step.xy))*0.055434637;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 4.0*step.xy))*0.055434637;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-5.0*step.xy))*0.04184426;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 5.0*step.xy))*0.04184426;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-6.0*step.xy))*0.029672023;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 6.0*step.xy))*0.029672023;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-7.0*step.xy))*0.019765828;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 7.0*step.xy))*0.019765828;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-8.0*step.xy))*0.012369139;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 8.0*step.xy))*0.012369139;\n\tgl_FragColor = fragCol;\n}\n";

    /** Bright buffer edge length, mirroring the legacy 256. */
    public static final int BRIGHT_SIZE = 256;

    private static final int BRIGHT_FORMAT = 34842;

    private final LwjglToolkit toolkit;
    private final LwjglTextureOps textureOps;
    private final LwjglFrameOps frameOps;

    private LwjglProgram brightRect;
    private LwjglProgram bright2D;
    private LwjglProgram composite;
    private LwjglProgram blur;
    private LwjglTexture2D brightA;
    private LwjglTexture2D brightB;
    private LwjglTexture2D[] mipmaps;
    private LwjglFramebuffer brightFbo;
    private LwjglFramebuffer downsampleFbo;

    private int width;
    private int height;
    private int sizedWidth;
    private int sizedHeight;
    private float brightThreshold;
    private float bloomStrength;
    private float whitePoint;
    private boolean active;

    public LwjglBloomEffect(LwjglToolkit toolkit, LwjglTextureOps textureOps, LwjglFrameOps frameOps) {
        if (toolkit == null || textureOps == null || frameOps == null) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.textureOps = textureOps;
        this.frameOps = frameOps;
    }

    /**
     * Tunes the bright threshold and composite strength, mirroring the
     * game-global values the legacy passes read at present time.
     */
    public void setBloomParams(float brightThreshold, float bloomStrength, float whitePoint) {
        this.brightThreshold = brightThreshold;
        this.bloomStrength = bloomStrength;
        this.whitePoint = whitePoint;
    }

    @Override
    public boolean isAvailable() {
        return this.brightFbo != null;
    }

    @Override
    public boolean setup() {
        if (!this.frameOps.supportsFramebufferObjects()) {
            return false;
        }
        this.brightFbo = new LwjglFramebuffer(this.toolkit, this.frameOps);
        this.brightB = new LwjglTexture2D(this.toolkit, LwjglTexture.TARGET_2D, BRIGHT_FORMAT, BRIGHT_SIZE, BRIGHT_SIZE, this.textureOps);
        this.brightB.setWrap(false, false, this.textureOps);
        this.brightA = new LwjglTexture2D(this.toolkit, LwjglTexture.TARGET_2D, BRIGHT_FORMAT, BRIGHT_SIZE, BRIGHT_SIZE, this.textureOps);
        this.brightA.setWrap(false, false, this.textureOps);
        this.toolkit.pushFramebuffer(this.brightFbo);
        this.brightFbo.attachTexture(this.brightB, 0, this.textureOps);
        this.brightFbo.attachTexture(this.brightA, 1, this.textureOps);
        this.brightFbo.selectDrawBuffer(0);
        boolean complete = this.brightFbo.checkComplete();
        this.toolkit.popFramebuffer(this.brightFbo);
        if (!complete) {
            return false;
        }
        this.brightRect = link(BRIGHT_RECT_SOURCE);
        this.bright2D = link(BRIGHT_2D_SOURCE);
        this.composite = link(COMPOSITE_SOURCE);
        this.blur = link(BLUR_SOURCE);
        return this.brightRect != null && this.bright2D != null && this.composite != null && this.blur != null;
    }

    @Override
    public int formatNeed() {
        return 1;
    }

    @Override
    public void present(int pass, LwjglTexture2D scene, LwjglTexture2D depth) {
        this.frameOps.pushAttrib(GL11.GL_ENABLE_BIT);
        this.frameOps.matrixMode(GL11.GL_PROJECTION);
        this.frameOps.pushMatrix();
        this.frameOps.loadIdentity();
        this.frameOps.ortho(0.0D, 1.0D, 0.0D, 1.0D, -1.0D, 1.0D);
        if (this.mipmaps == null) {
            this.toolkit.bindTexture(scene);
            this.toolkit.pushFramebuffer(this.brightFbo);
            this.brightFbo.selectDrawBuffer(0);
            this.toolkit.setViewport(0, 0, BRIGHT_SIZE, BRIGHT_SIZE);
            this.brightRect.use();
            this.brightRect.uniform1i("sceneTex", 0);
            this.brightRect.uniform3f("params", this.brightThreshold, 0.0F, 0.0F);
            this.fullQuad(0.0F, 0.0F, this.width, this.height);
        } else {
            this.toolkit.pushFramebuffer(this.downsampleFbo);
            int levelWidth = floorPowerOfTwo(this.width);
            int levelHeight = floorPowerOfTwo(this.height);
            int level = 0;
            while (levelWidth > BRIGHT_SIZE || levelHeight > BRIGHT_SIZE) {
                this.toolkit.setViewport(0, 0, levelWidth, levelHeight);
                this.downsampleFbo.attachTexture(this.mipmaps[level], 0, this.textureOps);
                if (level == 0) {
                    this.toolkit.bindTexture(scene);
                    this.fullQuad(0.0F, 0.0F, this.width, this.height);
                } else {
                    this.toolkit.bindTexture(this.mipmaps[level - 1]);
                    this.fullQuad(0.0F, 0.0F, 1.0F, 1.0F);
                }
                if (levelWidth > BRIGHT_SIZE) {
                    levelWidth >>= 0x1;
                }
                if (levelHeight > BRIGHT_SIZE) {
                    levelHeight >>= 0x1;
                }
                level++;
            }
            this.toolkit.popFramebuffer(this.downsampleFbo);
            this.toolkit.bindTexture(this.mipmaps[level - 1]);
            this.toolkit.pushFramebuffer(this.brightFbo);
            this.brightFbo.selectDrawBuffer(0);
            this.toolkit.setViewport(0, 0, BRIGHT_SIZE, BRIGHT_SIZE);
            this.bright2D.use();
            this.bright2D.uniform1i("sceneTex", 0);
            this.bright2D.uniform3f("params", this.brightThreshold, 0.0F, 0.0F);
            this.fullQuad(0.0F, 0.0F, 1.0F, 1.0F);
        }
        this.brightFbo.selectDrawBuffer(1);
        this.toolkit.bindTexture(this.brightB);
        this.blur.use();
        this.blur.uniform1i("baseTex", 0);
        this.blur.uniform3f("step", 0.00390625F, 0.0F, 0.0F);
        this.fullQuad(0.0F, 0.0F, 1.0F, 1.0F);
        this.brightFbo.selectDrawBuffer(0);
        this.toolkit.bindTexture(this.brightA);
        this.blur.uniform3f("step", 0.0F, 0.00390625F, 0.0F);
        this.fullQuad(0.0F, 0.0F, 1.0F, 1.0F);
        this.frameOps.popAttrib();
        this.frameOps.popMatrix();
        this.frameOps.matrixMode(GL11.GL_MODELVIEW);
        this.toolkit.popFramebuffer(this.brightFbo);
        this.composite.use();
        this.composite.uniform1i("sceneTex", 0);
        this.composite.uniform1i("bloomTex", 1);
        this.composite.uniform3f("params", this.bloomStrength, this.whitePoint, 0.0F);
        this.toolkit.selectTextureUnit(1);
        this.toolkit.bindTexture(this.brightB);
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(scene);
    }

    @Override
    public void finish(int pass) {
        LwjglProgram.unbind(this.textureOps);
        this.toolkit.selectTextureUnit(1);
        this.toolkit.bindTexture(null);
        this.toolkit.selectTextureUnit(0);
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        int levelWidth = floorPowerOfTwo(width);
        int levelHeight = floorPowerOfTwo(height);
        if (levelWidth == this.sizedWidth && levelHeight == this.sizedHeight) {
            return;
        }
        if (this.mipmaps != null) {
            for (int i = 0; i < this.mipmaps.length; i++) {
                this.mipmaps[i].delete();
            }
            this.mipmaps = null;
        }
        if (levelWidth <= BRIGHT_SIZE && levelHeight <= BRIGHT_SIZE) {
            if (this.downsampleFbo != null) {
                this.downsampleFbo.delete();
                this.downsampleFbo = null;
            }
        } else {
            int countWidth = levelWidth;
            int countHeight = levelHeight;
            int count = 0;
            if (this.downsampleFbo == null) {
                this.downsampleFbo = new LwjglFramebuffer(this.toolkit, this.frameOps);
            }
            while (countWidth > BRIGHT_SIZE || countHeight > BRIGHT_SIZE) {
                if (countWidth > BRIGHT_SIZE) {
                    countWidth >>= 0x1;
                }
                if (countHeight > BRIGHT_SIZE) {
                    countHeight >>= 0x1;
                }
                count++;
            }
            int buildWidth = levelWidth;
            int buildHeight = levelHeight;
            this.mipmaps = new LwjglTexture2D[count];
            int built = 0;
            while (buildWidth > BRIGHT_SIZE || buildHeight > BRIGHT_SIZE) {
                this.mipmaps[built++] = new LwjglTexture2D(this.toolkit, LwjglTexture.TARGET_2D, BRIGHT_FORMAT, buildWidth, buildHeight, this.textureOps);
                if (buildHeight > BRIGHT_SIZE) {
                    buildHeight >>= 0x1;
                }
                if (buildWidth > BRIGHT_SIZE) {
                    buildWidth >>= 0x1;
                }
            }
        }
        this.sizedWidth = levelWidth;
        this.sizedHeight = levelHeight;
    }

    @Override
    public void release() {
        if (this.brightRect != null) {
            this.brightRect.delete();
            this.brightRect = null;
        }
        if (this.bright2D != null) {
            this.bright2D.delete();
            this.bright2D = null;
        }
        if (this.composite != null) {
            this.composite.delete();
            this.composite = null;
        }
        if (this.blur != null) {
            this.blur.delete();
            this.blur = null;
        }
        if (this.brightB != null) {
            this.brightB.delete();
            this.brightB = null;
        }
        if (this.brightA != null) {
            this.brightA.delete();
            this.brightA = null;
        }
        if (this.mipmaps != null) {
            for (int i = 0; i < this.mipmaps.length; i++) {
                this.mipmaps[i].delete();
            }
            this.mipmaps = null;
        }
        if (this.brightFbo != null) {
            this.brightFbo.delete();
            this.brightFbo = null;
        }
        if (this.downsampleFbo != null) {
            this.downsampleFbo.delete();
            this.downsampleFbo = null;
        }
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * Largest power of two at or below the value, mirroring
     * {@code CubeMapSpecularEffect.method2696}.
     */
    static int floorPowerOfTwo(int value) {
        int half = value >>> 1;
        int spread = half | half >>> 1;
        int spread2 = spread | spread >>> 2;
        int spread3 = spread2 | spread2 >>> 4;
        int spread4 = spread3 | spread3 >>> 8;
        int spread5 = spread4 | spread4 >>> 16;
        return ~spread5 & value;
    }

    private LwjglProgram link(String source) {
        LwjglShader shader = LwjglShader.compile(this.textureOps, LwjglShader.TYPE_FRAGMENT_SHADER, source);
        if (shader == null) {
            return null;
        }
        return LwjglProgram.link(this.textureOps, new LwjglShader[] { shader });
    }

    private void fullQuad(float s0, float t0, float s1, float t1) {
        this.frameOps.beginQuads();
        this.frameOps.texCoord2f(0.0F, 0.0F);
        this.frameOps.vertex2i(0, 0);
        this.frameOps.texCoord2f(s1, 0.0F);
        this.frameOps.vertex2i(1, 0);
        this.frameOps.texCoord2f(s1, t1);
        this.frameOps.vertex2i(1, 1);
        this.frameOps.texCoord2f(0.0F, t1);
        this.frameOps.vertex2i(0, 1);
        this.frameOps.end();
    }
}
