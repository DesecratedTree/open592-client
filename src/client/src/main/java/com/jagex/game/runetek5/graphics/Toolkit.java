package com.jagex.game.runetek5.graphics;

import java.awt.Canvas;
import java.awt.Rectangle;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.da;
import com.jagex.game.runetek5.deob.ia;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.scene.ground.Ground;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Static102;
import com.jagex.game.runetek5.deob.Static209;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.graphics.*;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSprite;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.core.io.DummyInputStream;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.deob.Class217;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub1;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpConstant;
@OriginalClass("client!ya")
public abstract class Toolkit {

	@OriginalMember(owner = "client!ya", name = "e", descriptor = "Lclient!ga;")
	public final unpack_Sub1 materialProvider;

	@OriginalMember(owner = "client!ya", name = "j", descriptor = "I")
	public final int index;

	static {
		new Class83("You cannot report that person for Staff Impersonation, they are Jagex Staff.", "Diese Person ist ein Jagex-Mitarbeiter!", "Cette personne est un membre du personnel de Jagex, vous ne pouvez pas la signaler pour abus d'identité.", "Você não pode denunciar essa pessoa por tentar se passar por membro da equipe Jagex, pois ela faz parte da equipe.");
		new Class83(null, "die kürzlich gesprochen oder gehandelt haben.", null, null);
	}

	@OriginalMember(owner = "client!ya", name = "<init>", descriptor = "(ILclient!ga;)V")
	public Toolkit(@OriginalArg(0) int arg0, @OriginalArg(1) unpack_Sub1 arg1) {
		this.materialProvider = arg1;
		this.index = arg0;
	}

	@OriginalMember(owner = "client!ya", name = "b", descriptor = "()V")
	public abstract void method4236();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Z)V")
	public abstract void setShrinkTextures(@OriginalArg(0) boolean shrink);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(I[Lclient!vp;)V")
	public abstract void copyLights(@OriginalArg(0) int count, @OriginalArg(1) PointLight[] lights);

	@OriginalMember(owner = "client!ya", name = "c", descriptor = "()V")
	protected abstract void stop();

	@OriginalMember(owner = "client!ya", name = "B", descriptor = "(F)V")
	public abstract void B(@OriginalArg(0) float intensity);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIIII)V")
	public final void method4240(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		this.TA(arg2, arg0, arg1, arg3, 1);
	}

	@OriginalMember(owner = "client!ya", name = "AA", descriptor = "(IIII)V")
	public abstract void AA(@OriginalArg(0) int offsetX, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3);

	@OriginalMember(owner = "client!ya", name = "d", descriptor = "()Z")
	public abstract boolean hardShadow();

	@OriginalMember(owner = "client!ya", name = "f", descriptor = "()Z")
	public abstract boolean method4243();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Lclient!u;)V")
	public abstract void setHeap(@OriginalArg(0) MemoryPool heap);

	@OriginalMember(owner = "client!ya", name = "g", descriptor = "()Z")
	public abstract boolean method4245();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(I)V")
	public abstract void tick(@OriginalArg(0) int time);

	@OriginalMember(owner = "client!ya", name = "h", descriptor = "()V")
	public abstract void method4247();

	@OriginalMember(owner = "client!ya", name = "i", descriptor = "()Z")
	public abstract boolean method4248();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Ljava/awt/Canvas;)V")
	public abstract void method4249(@OriginalArg(0) Canvas arg0);

	@OriginalMember(owner = "client!ya", name = "T", descriptor = "(IFFFFF)V")
	public abstract void scaleHeight(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5);

	@OriginalMember(owner = "client!ya", name = "fa", descriptor = "(IIII)V")
	public abstract void fa(@OriginalArg(0) int left, @OriginalArg(1) int top, @OriginalArg(2) int right, @OriginalArg(3) int bottom);

	@OriginalMember(owner = "client!ya", name = "b", descriptor = "(Ljava/awt/Canvas;)V")
	public abstract void setCanvas(@OriginalArg(0) Canvas canvas);

	@OriginalMember(owner = "client!ya", name = "ea", descriptor = "(Lclient!c;)V")
	public abstract void ea(@OriginalArg(0) Ground arg0);

	@OriginalMember(owner = "client!ya", name = "YA", descriptor = "()I")
	public abstract int YA();

	@OriginalMember(owner = "client!ya", name = "TA", descriptor = "(IIIII)V")
	public abstract void TA(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int height, @OriginalArg(3) int colour, @OriginalArg(4) int mode);

	@OriginalMember(owner = "client!ya", name = "j", descriptor = "()Z")
	public abstract boolean method4251();

	@OriginalMember(owner = "client!ya", name = "k", descriptor = "()Z")
	public abstract boolean method4252();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIIIIII)V")
	public abstract void strongLine(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int colour, @OriginalArg(5) int width);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Lclient!jq;IIII)Lclient!t;")
	public abstract Model createModel(@OriginalArg(0) Mesh mesh, @OriginalArg(1) int functionMask, @OriginalArg(2) int featureMask, @OriginalArg(3) int ambient, @OriginalArg(4) int contrast);

	@OriginalMember(owner = "client!ya", name = "b", descriptor = "(Z)V")
	public abstract void method4255(@OriginalArg(0) boolean enabled);

	@OriginalMember(owner = "client!ya", name = "c", descriptor = "(IIIII)V")
	public final void method4256(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		this.b(arg2, arg1, arg0, arg3, 1);
	}

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(FFF)V")
	public abstract void setBloomParams(@OriginalArg(0) float whitePoint, @OriginalArg(1) float bloom, @OriginalArg(2) float brightpass);

	@OriginalMember(owner = "client!ya", name = "m", descriptor = "()Z")
	public abstract boolean method4258();

	@OriginalMember(owner = "client!ya", name = "g", descriptor = "(FF)V")
	public abstract void g(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1);

	@OriginalMember(owner = "client!ya", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		this.method4267();
	}

	@OriginalMember(owner = "client!ya", name = "IA", descriptor = "()I")
	public abstract int IA();

	@OriginalMember(owner = "client!ya", name = "n", descriptor = "()Z")
	public abstract boolean supportsAntiAliasing();

	@OriginalMember(owner = "client!ya", name = "o", descriptor = "()I")
	public abstract int getMaxLights();

	@OriginalMember(owner = "client!ya", name = "p", descriptor = "()Z")
	public abstract boolean method4261();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Lclient!t;Lclient!mc;Lclient!c;Lclient!mi;I)V")
	public abstract void method4262(@OriginalArg(0) Model arg0, @OriginalArg(1) ParticleList arg1, @OriginalArg(2) Ground arg2, @OriginalArg(3) BoundingBoxPolygon arg3);

	@OriginalMember(owner = "client!ya", name = "c", descriptor = "(Ljava/awt/Canvas;)V")
	public abstract void method4263(@OriginalArg(0) Canvas arg0);

	@OriginalMember(owner = "client!ya", name = "q", descriptor = "()Z")
	public abstract boolean method4264();

	@OriginalMember(owner = "client!ya", name = "w", descriptor = "()I")
	public abstract int w();

	@OriginalMember(owner = "client!ya", name = "l", descriptor = "()V")
	public abstract void l();

	@OriginalMember(owner = "client!ya", name = "NA", descriptor = "(IIIII)V")
	protected abstract void NA(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int radius, @OriginalArg(3) int colour, @OriginalArg(4) int mode);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "([IIIII)Lclient!l;")
	public abstract NativeSprite method4265(@OriginalArg(0) int[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3);

	@OriginalMember(owner = "client!ya", name = "r", descriptor = "()V")
	public abstract void method4266();

	@OriginalMember(owner = "client!ya", name = "da", descriptor = "([I)V")
	public abstract void da(@OriginalArg(0) int[] clip);

	@OriginalMember(owner = "client!ya", name = "c", descriptor = "(Z)V")
	public final void method4267() {
		TextureOpIrregularBricks.aBooleanArray11[this.index] = false;
		this.stop();
	}

	/**
	 * Re-pushes fog state to the renderer after user fog-distance changes.
	 * The software path has no fog and inherits this no-op.
	 */
	public void refreshFog() {
	}

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIIIII)Lclient!tb;")
	public abstract EnvironmentSampler createCubeTexture(@OriginalArg(0) int materialPositiveX, @OriginalArg(1) int materialNegativeX, @OriginalArg(2) int materialPositiveY, @OriginalArg(3) int materialNegativeY, @OriginalArg(4) int materialPositiveZ, @OriginalArg(5) int materialNegativeZ);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(II)I")
	public abstract int compareFunctionMasks(@OriginalArg(0) int maskA, @OriginalArg(1) int maskB);

	@OriginalMember(owner = "client!ya", name = "d", descriptor = "(Ljava/awt/Canvas;)V")
	public abstract void releaseSurface(@OriginalArg(0) Canvas canvas);

	@OriginalMember(owner = "client!ya", name = "e", descriptor = "()V")
	public abstract void e();

	@OriginalMember(owner = "client!ya", name = "d", descriptor = "(I)V")
	public abstract void linkThreads(@OriginalArg(0) int threadId);

	@OriginalMember(owner = "client!ya", name = "s", descriptor = "()Lclient!c;")
	public abstract Ground method4274();

	/**
	 * 592HD seam: the client is about to bake the top-down map texture.
	 *
	 * <p>The bake draws the scene's ground objects into a small texture from a
	 * projection of its own, so 592HD's per-pixel lighting - which is expressed
	 * in the scene camera's space - must not run during it. A no-op on toolkits
	 * that have no such pipeline; {@code GlToolkit} is the one that does.
	 */
	public void hdMapBakeBegin() {
	}

	/** 592HD seam: the map bake is over. */
	public void hdMapBakeEnd() {
	}

	@OriginalMember(owner = "client!ya", name = "ZA", descriptor = "()I")
	public abstract int ZA();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Lclient!gt;[Lclient!bb;Z)Lclient!la;")
	public abstract AbstractFontRenderer createFontRenderer(@OriginalArg(0) com.jagex.game.runetek5.graphics.font.FontMetrics metrics, @OriginalArg(1) SpriteType[] images);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIIIIIIIII)V")
	public abstract void method4276();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Lclient!mc;)V")
	public abstract void render(@OriginalArg(0) ParticleList particleList);

	@OriginalMember(owner = "client!ya", name = "ia", descriptor = "(II)V")
	public abstract void ia(@OriginalArg(0) int zNear, @OriginalArg(1) int zFar);

	@OriginalMember(owner = "client!ya", name = "t", descriptor = "()V")
	public abstract void method4278();

	@OriginalMember(owner = "client!ya", name = "u", descriptor = "()Z")
	public abstract boolean hasTextures();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIIIZ)Lclient!l;")
	public abstract NativeSprite createNativeSprite(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) boolean hasAlpha);

	@OriginalMember(owner = "client!ya", name = "v", descriptor = "()V")
	public abstract void method4281();

	@OriginalMember(owner = "client!ya", name = "b", descriptor = "(II)I")
	public abstract int combineFunctionMasks(@OriginalArg(0) int maskA, @OriginalArg(1) int maskB);

	@OriginalMember(owner = "client!ya", name = "GA", descriptor = "(IIII)V")
	public abstract void GA(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3);

	@OriginalMember(owner = "client!ya", name = "e", descriptor = "(I)V")
	public abstract void allocateThreads(@OriginalArg(0) int threadCount);

	@OriginalMember(owner = "client!ya", name = "f", descriptor = "(I)Lclient!u;")
	public abstract MemoryPool createHeap(@OriginalArg(0) int size);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(II[I[I)Lclient!ma;")
	public abstract ComponentSurface createMask(@OriginalArg(0) int width, @OriginalArg(1) int height, @OriginalArg(2) int[] offsets, @OriginalArg(3) int[] widths);

	@OriginalMember(owner = "client!ya", name = "x", descriptor = "()Z")
	public abstract boolean method4286();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIIIIILclient!ma;II)V")
	public abstract void drawSurfaceLine(@OriginalArg(0) int startX, @OriginalArg(1) int startY, @OriginalArg(2) int endX, @OriginalArg(3) int endY, @OriginalArg(4) int colour, @OriginalArg(6) ComponentSurface arg5, @OriginalArg(7) int clippingMask, @OriginalArg(8) int maskX);

	@OriginalMember(owner = "client!ya", name = "ca", descriptor = "(ILclient!ma;II)V")
	public abstract void ca(@OriginalArg(0) int colour, @OriginalArg(1) ComponentSurface mask, @OriginalArg(2) int x, @OriginalArg(3) int y);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Lclient!tb;)V")
	public abstract void setSunCubeTexture(@OriginalArg(0) EnvironmentSampler cubeTexture);

	@OriginalMember(owner = "client!ya", name = "MA", descriptor = "(I)V")
	public abstract void setOffsets(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!ya", name = "xa", descriptor = "()F")
	public abstract float xa();

	@OriginalMember(owner = "client!ya", name = "y", descriptor = "()Lclient!c;")
	public abstract Ground method4289();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "([Ljava/awt/Rectangle;I)V")
	public abstract void method4290(@OriginalArg(0) Rectangle[] arg0, @OriginalArg(1) int arg1);

	@OriginalMember(owner = "client!ya", name = "z", descriptor = "()Z")
	public abstract boolean method4291();

	@OriginalMember(owner = "client!ya", name = "JA", descriptor = "(IIIIII)Z")
	public abstract boolean drawScaledColored(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIIBII)V")
	public final void method4293(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		this.O(arg0, arg2, arg1, arg4, arg3, 1);
	}

	@OriginalMember(owner = "client!ya", name = "UA", descriptor = "(III)V")
	public abstract void UA(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIII)V")
	public abstract void method4294(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height);

	@OriginalMember(owner = "client!ya", name = "b", descriptor = "(IIIIII)V")
	public final void method4295(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		this.outlineRect(arg0, arg4, arg3, arg2, arg1, 1);
	}

	@OriginalMember(owner = "client!ya", name = "c", descriptor = "(IIIIII)V")
	public final void method4296(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		this.line(arg2, arg0, arg4, arg3, arg1, 1);
	}

	@OriginalMember(owner = "client!ya", name = "X", descriptor = "(III[I)V")
	public abstract void X(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int[] arg3);

	@OriginalMember(owner = "client!ya", name = "DA", descriptor = "(IIIIII[BII)V")
	public abstract void DA(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int backgroundColour, @OriginalArg(5) int foregroundColour, @OriginalArg(6) byte[] mask, @OriginalArg(7) int maskWidth, @OriginalArg(8) int mode);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(II[[I[[IIII)Lclient!ta;")
	public abstract Class65 method4297(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[][] arg2, @OriginalArg(3) int[][] arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Lclient!tb;Lclient!tb;FLclient!tb;)Lclient!tb;")
	public abstract EnvironmentSampler drawQuad(@OriginalArg(0) EnvironmentSampler sourceA, @OriginalArg(1) EnvironmentSampler sourceB, @OriginalArg(2) float alpha, @OriginalArg(3) EnvironmentSampler existing);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "([Lclient!t;Lclient!mc;Lclient!c;[Lclient!mi;I)V")
	public abstract void method4299(@OriginalArg(0) Model[] arg0, @OriginalArg(1) ParticleList arg1, @OriginalArg(2) Ground arg2, @OriginalArg(3) BoundingBoxPolygon[] arg3, @OriginalArg(4) int arg4);

	@OriginalMember(owner = "client!ya", name = "B", descriptor = "()Z")
	public abstract boolean method4300();

	@OriginalMember(owner = "client!ya", name = "C", descriptor = "()Z")
	public abstract boolean method4301();

	@OriginalMember(owner = "client!ya", name = "d", descriptor = "(IIIIII)V")
	public abstract void line(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int colour, @OriginalArg(5) int mode);

	@OriginalMember(owner = "client!ya", name = "OA", descriptor = "(IIII)V")
	public abstract void OA(@OriginalArg(0) int left, @OriginalArg(1) int top, @OriginalArg(2) int right, @OriginalArg(3) int bottom);

	@OriginalMember(owner = "client!ya", name = "p", descriptor = "(I)V")
	public abstract void p(@OriginalArg(0) int colour);

	@OriginalMember(owner = "client!ya", name = "g", descriptor = "(I)V")
	public abstract void method4303(@OriginalArg(0) int threadId);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "()I")
	public abstract int a();

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(BIIII)V")
	public final void method4304(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		this.NA(arg2, arg0, arg1, arg3, 1);
	}

	@OriginalMember(owner = "client!ya", name = "O", descriptor = "(IIIIII)V")
	public abstract void O(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int colour, @OriginalArg(5) int mode);

	@OriginalMember(owner = "client!ya", name = "h", descriptor = "(I)V")
	public abstract void setTextureSize(@OriginalArg(0) int size);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "([Lclient!t;Lclient!c;[Lclient!mi;I)V")
	public abstract void method4307(@OriginalArg(0) Model[] arg0, @OriginalArg(1) Ground arg1, @OriginalArg(2) BoundingBoxPolygon[] arg2, @OriginalArg(3) int arg3);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Lclient!bb;Z)Lclient!l;")
	public abstract NativeSprite generateSprite(@OriginalArg(0) SpriteType spriteType);

	@OriginalMember(owner = "client!ya", name = "d", descriptor = "(Z)V")
	public abstract void method4309(@OriginalArg(0) boolean enabled);

	@OriginalMember(owner = "client!ya", name = "b", descriptor = "(IIIII)V")
	public abstract void b(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int colour, @OriginalArg(4) int mode);

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIIIIF)Lclient!vp;")
	public abstract PointLight createPointLight(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z, @OriginalArg(3) int radius, @OriginalArg(4) int colour, @OriginalArg(5) float intensity);

	@OriginalMember(owner = "client!ya", name = "F", descriptor = "(IIII)[I")
	public abstract int[] F(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height);

	@OriginalMember(owner = "client!ya", name = "H", descriptor = "(IIII)V")
	public abstract void H(@OriginalArg(0) int projectionCenterX, @OriginalArg(1) int projectionCenterY, @OriginalArg(2) int projectionScaleX, @OriginalArg(3) int projectionScaleY);

	@OriginalMember(owner = "client!ya", name = "W", descriptor = "()F")
	public abstract float W();

	@OriginalMember(owner = "client!ya", name = "D", descriptor = "()V")
	public abstract void cacheReset();

	@OriginalMember(owner = "client!ya", name = "e", descriptor = "(IIIIII)V")
	public abstract void outlineRect(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int colour, @OriginalArg(5) int mode);

@OriginalMember(owner = "client!ya", name = "o", descriptor = "I")
	public static int mapCenterChunkX;
@OriginalMember(owner = "client!ya", name = "q", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array11;
@OriginalMember(owner = "client!ya", name = "m", descriptor = "I")
	public static int anInt5535 = -1;
@OriginalMember(owner = "client!ya", name = "p", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_72 = new Class215(7, 7);
@OriginalMember(owner = "client!ya", name = "r", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_73 = new Class215(10, 7);	/**
	 * Whether the OpenGL renderer can be attempted on this platform. GL runs on
	 * LWJGL now, whose natives ship with the client for Windows, Linux and macOS,
	 * so nothing has to be extracted from JS5 for it. A platform without them
	 * fails in the toolkit's constructor, which falls back to the Java renderer.
	 */
	public static boolean openGlNativesAvailable() {
		return true;
	}

	@OriginalMember(owner = "client!ya", name = "a", descriptor = "(Lclient!ga;ILjava/awt/Canvas;IBLclient!et;)Lclient!ya;")
	public static synchronized Toolkit method4241(@OriginalArg(0) unpack_Sub1 arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Canvas arg2, @OriginalArg(3) int arg3, @OriginalArg(5) SignLink arg4) {
		@Pc(7) int local7 = -1;
		for (@Pc(9) int local9 = 0; local9 < 8; local9++) {
			if (!TextureOpIrregularBricks.aBooleanArray11[local9]) {
				local7 = local9;
				break;
			}
		}
		if (local7 == -1) {
			throw new IllegalStateException("NFTI");
		}
		@Pc(52) Toolkit local52;
		if (arg3 == 0) {
			local52 = StackFrame.method302(arg2, local7, arg0);
		} else if (arg3 == 1) {
			local52 = Class27_Sub4.method3941(local7, arg2, arg4, arg1, arg0);
		} else if (arg3 == 2) {
			// Standard detail. The original build used the native sw3d software
			// renderer here, which is not available in this 64-bit port; the
			// Java software toolkit provides the same low-detail fallback.
			local52 = StackFrame.method302(arg2, local7, arg0);
		} else {
			throw new IllegalArgumentException("UM");
		}
		TextureOpIrregularBricks.aBooleanArray11[local7] = true;
		return local52;
	}
@OriginalMember(owner = "client!ya", name = "b", descriptor = "(I)V")
	public static void method4270() {
		if (Class16.anInt6887 <= 0) {
			Class4_Sub19.aString29 = "";
			return;
		}
		@Pc(19) int local19 = 0;
		for (@Pc(21) int local21 = 0; local21 < Static102.aStringArray8.length; local21++) {
			if (Static102.aStringArray8[local21].startsWith("--> ")) {
				local19++;
				if (local19 == Class16.anInt6887) {
					Class4_Sub19.aString29 = Static102.aStringArray8[local21].substring(4);
					return;
				}
			}
		}
	}
@OriginalMember(owner = "client!ya", name = "A", descriptor = "()V")
	public static void method4292() {
		OpenGl2DTexture.anInt5461 = 0;
		label208: for (@Pc(3) int local3 = 0; local3 < OpenGlArbElementArrayBuffer.anInt1878; local3++) {
			@Pc(8) Class136 local8 = TextureOpSprite.aClass136Array2[local3];
			@Pc(12) int local12;
			if (Class16_Sub2_Sub1.anIntArray34 != null) {
				for (local12 = 0; local12 < Class16_Sub2_Sub1.anIntArray34.length; local12++) {
					if (Class16_Sub2_Sub1.anIntArray34[local12] != -1000000 && (local8.anInt4368 <= Class16_Sub2_Sub1.anIntArray34[local12] || local8.anInt4361 <= Class16_Sub2_Sub1.anIntArray34[local12]) && (local8.anInt4355 <= TextureOpColorGradient.anIntArray143[local12] || local8.anInt4370 <= TextureOpColorGradient.anIntArray143[local12]) && (local8.anInt4355 >= DummyInputStream.anIntArray303[local12] || local8.anInt4370 >= DummyInputStream.anIntArray303[local12]) && (local8.anInt4359 <= SpotAnimation.anIntArray106[local12] || local8.anInt4356 <= SpotAnimation.anIntArray106[local12]) && (local8.anInt4359 >= PlayerEntity.anIntArray284[local12] || local8.anInt4356 >= PlayerEntity.anIntArray284[local12])) {
						continue label208;
					}
				}
			}
			@Pc(110) int local110;
			@Pc(128) int local128;
			@Pc(164) int local164;
			@Pc(143) boolean local143;
			if (local8.anInt4360 == 1) {
				local12 = local8.anInt4365 + FileOnDisk.anInt4431 - QuickChatPhrase.anInt7073;
				if (local12 >= 0 && local12 <= FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
					local110 = local8.anInt4354 + FileOnDisk.anInt4431 - OpenGlArbElementArrayBuffer.anInt1887;
					if (local110 < 0) {
						local110 = 0;
					} else if (local110 > FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
						continue;
					}
					local128 = local8.anInt4357 + FileOnDisk.anInt4431 - OpenGlArbElementArrayBuffer.anInt1887;
					if (local128 > FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
						local128 = FileOnDisk.anInt4431 + FileOnDisk.anInt4431;
					} else if (local128 < 0) {
						continue;
					}
					local143 = false;
					while (local110 <= local128) {
						if (GlVboArrayBuffer.aBooleanArrayArray4[local12][local110++]) {
							local143 = true;
							break;
						}
					}
					if (local143) {
						local164 = Class4_Sub1_Sub11.anInt3073 - local8.anInt4355;
						if (local164 > HashTable.anInt3142) {
							local8.anInt4366 = 1;
						} else {
							if (local164 >= -HashTable.anInt3142) {
								continue;
							}
							local8.anInt4366 = 2;
							local164 = -local164;
						}
						local8.anInt4352 = (local8.anInt4359 - Static209.anInt3921 << 8) / local164;
						local8.anInt4369 = (local8.anInt4356 - Static209.anInt3921 << 8) / local164;
						local8.anInt4367 = (local8.anInt4368 - TextureOpConstant.anInt4098 << 8) / local164;
						local8.anInt4362 = (local8.anInt4361 - TextureOpConstant.anInt4098 << 8) / local164;
						EffectManagerGL.aClass136Array3[OpenGl2DTexture.anInt5461++] = local8;
					}
				}
			} else if (local8.anInt4360 == 2) {
				local12 = local8.anInt4354 + FileOnDisk.anInt4431 - OpenGlArbElementArrayBuffer.anInt1887;
				if (local12 >= 0 && local12 <= FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
					local110 = local8.anInt4365 + FileOnDisk.anInt4431 - QuickChatPhrase.anInt7073;
					if (local110 < 0) {
						local110 = 0;
					} else if (local110 > FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
						continue;
					}
					local128 = local8.anInt4363 + FileOnDisk.anInt4431 - QuickChatPhrase.anInt7073;
					if (local128 > FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
						local128 = FileOnDisk.anInt4431 + FileOnDisk.anInt4431;
					} else if (local128 < 0) {
						continue;
					}
					local143 = false;
					while (local110 <= local128) {
						if (GlVboArrayBuffer.aBooleanArrayArray4[local110++][local12]) {
							local143 = true;
							break;
						}
					}
					if (local143) {
						local164 = Static209.anInt3921 - local8.anInt4359;
						if (local164 > HashTable.anInt3142) {
							local8.anInt4366 = 3;
						} else {
							if (local164 >= -HashTable.anInt3142) {
								continue;
							}
							local8.anInt4366 = 4;
							local164 = -local164;
						}
						local8.anInt4353 = (local8.anInt4355 - Class4_Sub1_Sub11.anInt3073 << 8) / local164;
						local8.anInt4358 = (local8.anInt4370 - Class4_Sub1_Sub11.anInt3073 << 8) / local164;
						local8.anInt4367 = (local8.anInt4368 - TextureOpConstant.anInt4098 << 8) / local164;
						local8.anInt4362 = (local8.anInt4361 - TextureOpConstant.anInt4098 << 8) / local164;
						EffectManagerGL.aClass136Array3[OpenGl2DTexture.anInt5461++] = local8;
					}
				}
			} else if (local8.anInt4360 == 4) {
				local12 = local8.anInt4368 - TextureOpConstant.anInt4098;
				if (local12 > Class217.anInt6319) {
					local110 = local8.anInt4354 + FileOnDisk.anInt4431 - OpenGlArbElementArrayBuffer.anInt1887;
					if (local110 < 0) {
						local110 = 0;
					} else if (local110 > FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
						continue;
					}
					local128 = local8.anInt4357 + FileOnDisk.anInt4431 - OpenGlArbElementArrayBuffer.anInt1887;
					if (local128 > FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
						local128 = FileOnDisk.anInt4431 + FileOnDisk.anInt4431;
					} else if (local128 < 0) {
						continue;
					}
					@Pc(435) int local435 = local8.anInt4365 + FileOnDisk.anInt4431 - QuickChatPhrase.anInt7073;
					if (local435 < 0) {
						local435 = 0;
					} else if (local435 > FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
						continue;
					}
					local164 = local8.anInt4363 + FileOnDisk.anInt4431 - QuickChatPhrase.anInt7073;
					if (local164 > FileOnDisk.anInt4431 + FileOnDisk.anInt4431) {
						local164 = FileOnDisk.anInt4431 + FileOnDisk.anInt4431;
					} else if (local164 < 0) {
						continue;
					}
					@Pc(468) boolean local468 = false;
					label180: for (@Pc(470) int local470 = local435; local470 <= local164; local470++) {
						for (@Pc(473) int local473 = local110; local473 <= local128; local473++) {
							if (GlVboArrayBuffer.aBooleanArrayArray4[local470][local473]) {
								local468 = true;
								break label180;
							}
						}
					}
					if (local468) {
						local8.anInt4366 = 5;
						local8.anInt4353 = (local8.anInt4355 - Class4_Sub1_Sub11.anInt3073 << 8) / local12;
						local8.anInt4358 = (local8.anInt4370 - Class4_Sub1_Sub11.anInt3073 << 8) / local12;
						local8.anInt4352 = (local8.anInt4359 - Static209.anInt3921 << 8) / local12;
						local8.anInt4369 = (local8.anInt4356 - Static209.anInt3921 << 8) / local12;
						EffectManagerGL.aClass136Array3[OpenGl2DTexture.anInt5461++] = local8;
					}
				}
			}
		}
	}
@OriginalMember(owner = "client!ya", name = "a", descriptor = "(IIIIIIIIB)Z")
	public static boolean method4305(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7) {
		if (arg7 + arg4 > arg6 && arg2 + arg6 > arg7) {
			return arg1 + arg3 > arg5 && arg5 + arg0 > arg1;
		} else {
			return false;
		}
	}
}
