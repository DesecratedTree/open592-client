package com.jagex.game.runetek5.cache;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static459;

import com.jagex.game.runetek5.HelveticaFont;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.config.staticelements.StaticElements;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static148;
import com.jagex.game.runetek5.deob.Static258;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.Static308;
import com.jagex.game.runetek5.deob.Static380;
import com.jagex.game.runetek5.deob.Static449;
import com.jagex.game.runetek5.cache.*;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.cache.compression.GzipDecompressor;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class50;
import com.jagex.game.runetek5.graphics.texture.RectangularTextureGL;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub3;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.cache.node.MonochromeImageCacheSlot;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class177;
@OriginalClass("client!lq")
public final class FileOnDisk {

	@OriginalMember(owner = "client!lq", name = "i", descriptor = "J")
	private long aLong144;

	@OriginalMember(owner = "client!lq", name = "u", descriptor = "I")
	private int anInt4428;

	@OriginalMember(owner = "client!lq", name = "f", descriptor = "J")
	private long aLong143 = -1L;

	@OriginalMember(owner = "client!lq", name = "r", descriptor = "I")
	private int anInt4426 = 0;

	@OriginalMember(owner = "client!lq", name = "o", descriptor = "J")
	private long aLong146 = -1L;

	@OriginalMember(owner = "client!lq", name = "g", descriptor = "Lclient!rp;")
	private final JFS32 aClass207_4;

	@OriginalMember(owner = "client!lq", name = "j", descriptor = "J")
	private long aLong145;

	@OriginalMember(owner = "client!lq", name = "s", descriptor = "J")
	private long aLong147;

	@OriginalMember(owner = "client!lq", name = "a", descriptor = "J")
	private long aLong142;

	@OriginalMember(owner = "client!lq", name = "n", descriptor = "[B")
	private final byte[] aByteArray54;

	@OriginalMember(owner = "client!lq", name = "v", descriptor = "[B")
	private final byte[] aByteArray55;

	static {
		new Class83("You have been temporarily muted due to breaking a rule.", "Aufgrund eines Regelverstoßes wurdest du vorübergehend stumm geschaltet.", "La messagerie instantanée a été temporairement bloquée suite à une infraction.", "Você foi temporariamente vetado por ter violado uma regra.");
	}

	@OriginalMember(owner = "client!lq", name = "<init>", descriptor = "(Lclient!rp;II)V")
	public FileOnDisk(@OriginalArg(0) JFS32 arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) throws IOException {
		this.aClass207_4 = arg0;
		this.aLong147 = this.aLong145 = arg0.method4717();
		this.aLong142 = 0L;
		this.aByteArray54 = new byte[arg2];
		this.aByteArray55 = new byte[arg1];
	}

	@OriginalMember(owner = "client!lq", name = "a", descriptor = "([BI)V")
	public void method3462(@OriginalArg(0) byte[] arg0) throws IOException {
		this.method3471(arg0.length, arg0, 0);
	}

	@OriginalMember(owner = "client!lq", name = "a", descriptor = "(Z)J")
	public long method3463() {
		return this.aLong147;
	}

	@OriginalMember(owner = "client!lq", name = "a", descriptor = "(IB[BI)V")
	public void method3464(@OriginalArg(0) int arg0, @OriginalArg(2) byte[] arg1, @OriginalArg(3) int arg2) throws IOException {
		try {
			if (this.aLong147 < (long) arg2 + this.aLong142) {
				this.aLong147 = (long) arg2 + this.aLong142;
			}
			if (this.aLong143 != -1L && (this.aLong143 > this.aLong142 || this.aLong142 > this.aLong143 + (long) this.anInt4426)) {
				this.method3472();
			}
			if (this.aLong143 != -1L && this.aLong142 + (long) arg2 > this.aLong143 + (long) this.aByteArray54.length) {
				@Pc(100) int local100 = (int) (this.aLong143 + (long) this.aByteArray54.length - this.aLong142);
				Static459.copy(arg1, arg0, this.aByteArray54, (int) (this.aLong142 - this.aLong143), local100);
				arg0 += local100;
				this.aLong142 += local100;
				arg2 -= local100;
				this.anInt4426 = this.aByteArray54.length;
				this.method3472();
			}
			if (arg2 > this.aByteArray54.length) {
				if (this.aLong144 != this.aLong142) {
					this.aClass207_4.method4718(this.aLong142);
					this.aLong144 = this.aLong142;
				}
				this.aClass207_4.method4714(arg0, arg2, arg1);
				this.aLong144 += arg2;
				if (this.aLong144 > this.aLong145) {
					this.aLong145 = this.aLong144;
				}
				@Pc(195) long local195 = -1L;
				@Pc(197) long local197 = -1L;
				if (this.aLong142 >= this.aLong146 && this.aLong146 + (long) this.anInt4428 > this.aLong142) {
					local195 = this.aLong142;
				} else if (this.aLong146 >= this.aLong142 && this.aLong146 < (long) arg2 + this.aLong142) {
					local195 = this.aLong146;
				}
				if (this.aLong146 < (long) arg2 + this.aLong142 && (long) arg2 + this.aLong142 <= (long) this.anInt4428 + this.aLong146) {
					local197 = (long) arg2 + this.aLong142;
				} else if (this.aLong146 + (long) this.anInt4428 > this.aLong142 && this.aLong142 + (long) arg2 >= this.aLong146 + (long) this.anInt4428) {
					local197 = (long) this.anInt4428 + this.aLong146;
				}
				if (local195 > -1L && local195 < local197) {
					@Pc(335) int local335 = (int) (local197 - local195);
					Static459.copy(arg1, (int) (local195 + (long) arg0 - this.aLong142), this.aByteArray55, (int) (local195 - this.aLong146), local335);
				}
				this.aLong142 += arg2;
			} else if (arg2 > 0) {
				if (this.aLong143 == -1L) {
					this.aLong143 = this.aLong142;
				}
				Static459.copy(arg1, arg0, this.aByteArray54, (int) (this.aLong142 - this.aLong143), arg2);
				this.aLong142 += arg2;
				if (this.aLong142 - this.aLong143 > (long) this.anInt4426) {
					this.anInt4426 = (int) (this.aLong142 - this.aLong143);
				}
			}
		} catch (@Pc(417) IOException local417) {
			this.aLong144 = -1L;
			throw local417;
		}
	}

	@OriginalMember(owner = "client!lq", name = "a", descriptor = "(IJ)V")
	public void method3466(@OriginalArg(1) long arg0) throws IOException {
		if (arg0 < (long) 0) {
			throw new IOException("Invalid seek to " + arg0 + " in file " + this.method3473());
		}
		this.aLong142 = arg0;
	}

	@OriginalMember(owner = "client!lq", name = "a", descriptor = "(I)V")
	private void method3467() throws IOException {
		this.anInt4428 = 0;
		if (this.aLong142 != this.aLong144) {
			this.aClass207_4.method4718(this.aLong142);
			this.aLong144 = this.aLong142;
		}
		this.aLong146 = this.aLong142;
		while (this.anInt4428 < this.aByteArray55.length) {
			@Pc(40) int local40 = this.aByteArray55.length - this.anInt4428;
			if (local40 > 200000000) {
				local40 = 200000000;
			}
			@Pc(55) int local55 = this.aClass207_4.method4715(this.anInt4428, local40, this.aByteArray55);
			if (local55 == -1) {
				break;
			}
			this.aLong144 += local55;
			this.anInt4428 += local55;
		}
	}

	@OriginalMember(owner = "client!lq", name = "b", descriptor = "(IB[BI)V")
	public void method3471(@OriginalArg(0) int arg0, @OriginalArg(2) byte[] arg1, @OriginalArg(3) int arg2) throws IOException {
		try {
			if (arg0 > arg1.length) {
				throw new ArrayIndexOutOfBoundsException(arg0 - arg1.length);
			}
			if (this.aLong143 != -1L && this.aLong143 <= this.aLong142 && (long) this.anInt4426 + this.aLong143 >= this.aLong142 + (long) arg0) {
				Static459.copy(this.aByteArray54, (int) (this.aLong142 - this.aLong143), arg1, 0, arg0);
				this.aLong142 += arg0;
				return;
			}
			@Pc(82) long local82 = this.aLong142;
			@Pc(86) int local86 = arg0;
			@Pc(124) int local124;
			if (this.aLong146 <= this.aLong142 && this.aLong142 < this.aLong146 + (long) this.anInt4428) {
				local124 = (int) (this.aLong146 + (long) this.anInt4428 - this.aLong142);
				if (arg0 < local124) {
					local124 = arg0;
				}
				Static459.copy(this.aByteArray55, (int) (this.aLong142 - this.aLong146), arg1, 0, local124);
				arg2 = local124;
				arg0 -= local124;
				this.aLong142 += local124;
			}
			if (this.aByteArray55.length < arg0) {
				this.aClass207_4.method4718(this.aLong142);
				this.aLong144 = this.aLong142;
				while (arg0 > 0) {
					local124 = this.aClass207_4.method4715(arg2, arg0, arg1);
					if (local124 == -1) {
						break;
					}
					this.aLong144 += local124;
					this.aLong142 += local124;
					arg2 += local124;
					arg0 -= local124;
				}
			} else if (arg0 > 0) {
				this.method3467();
				local124 = arg0;
				if (this.anInt4428 < arg0) {
					local124 = this.anInt4428;
				}
				Static459.copy(this.aByteArray55, 0, arg1, arg2, local124);
				this.aLong142 += local124;
				arg2 += local124;
				arg0 -= local124;
			}
			if (this.aLong143 != -1L) {
				if (this.aLong142 < this.aLong143 && arg0 > 0) {
					local124 = (int) (this.aLong143 - this.aLong142) + arg2;
					if (local124 > arg0 + arg2) {
						local124 = arg0 + arg2;
					}
					while (local124 > arg2) {
						arg1[arg2++] = 0;
						arg0--;
						this.aLong142++;
					}
				}
				@Pc(312) long local312 = -1L;
				if (this.aLong143 >= local82 && (long) local86 + local82 > this.aLong143) {
					local312 = this.aLong143;
				} else if (this.aLong143 <= local82 && this.aLong143 + (long) this.anInt4426 > local82) {
					local312 = local82;
				}
				@Pc(361) long local361 = -1L;
				if ((long) this.anInt4426 + this.aLong143 > local82 && this.aLong143 + (long) this.anInt4426 <= local82 - -((long) local86)) {
					local361 = (long) this.anInt4426 + this.aLong143;
				} else if (this.aLong143 < local82 + (long) local86 && local82 + (long) local86 <= (long) this.anInt4426 + this.aLong143) {
					local361 = (long) local86 + local82;
				}
				if (local312 > -1L && local361 > local312) {
					@Pc(440) int local440 = (int) (local361 - local312);
					Static459.copy(this.aByteArray54, (int) (local312 - this.aLong143), arg1, (int) (local312 - local82), local440);
					if (local361 > this.aLong142) {
						arg0 = (int) ((long) arg0 + this.aLong142 - local361);
						this.aLong142 = local361;
					}
				}
			}
		} catch (@Pc(481) IOException local481) {
			this.aLong144 = -1L;
			throw local481;
		}
		if (arg0 > 0) {
			throw new EOFException();
		}
	}

	@OriginalMember(owner = "client!lq", name = "a", descriptor = "(B)V")
	private void method3472() throws IOException {
		if (this.aLong143 == -1L) {
			return;
		}
		if (this.aLong143 != this.aLong144) {
			this.aClass207_4.method4718(this.aLong143);
			this.aLong144 = this.aLong143;
		}
		this.aClass207_4.method4714(0, this.anInt4426, this.aByteArray54);
		this.aLong144 += this.anInt4426;
		if (this.aLong145 < this.aLong144) {
			this.aLong145 = this.aLong144;
		}
		@Pc(63) long local63 = -1L;
		@Pc(65) long local65 = -1L;
		if (this.aLong143 >= this.aLong146 && this.aLong146 + (long) this.anInt4428 > this.aLong143) {
			local63 = this.aLong143;
		} else if (this.aLong143 <= this.aLong146 && (long) this.anInt4426 + this.aLong143 > this.aLong146) {
			local63 = this.aLong146;
		}
		if (this.aLong143 + (long) this.anInt4426 > this.aLong146 && this.aLong143 + (long) this.anInt4426 <= (long) this.anInt4428 + this.aLong146) {
			local65 = (long) this.anInt4426 + this.aLong143;
		} else if (this.aLong143 < (long) this.anInt4428 + this.aLong146 && (long) this.anInt4426 + this.aLong143 >= (long) this.anInt4428 + this.aLong146) {
			local65 = this.aLong146 + (long) this.anInt4428;
		}
		if (local63 > -1L && local65 > local63) {
			@Pc(203) int local203 = (int) (local65 - local63);
			Static459.copy(this.aByteArray54, (int) (local63 - this.aLong143), this.aByteArray55, (int) (local63 - this.aLong146), local203);
		}
		this.aLong143 = -1L;
		this.anInt4426 = 0;
	}

	@OriginalMember(owner = "client!lq", name = "c", descriptor = "(I)Ljava/io/File;")
	private File method3473() {
		return this.aClass207_4.method4716();
	}

@OriginalMember(owner = "client!lq", name = "y", descriptor = "I")
	public static int anInt4431;
@OriginalMember(owner = "client!lq", name = "d", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_194 = new Class22(8, -2);
@OriginalMember(owner = "client!lq", name = "p", descriptor = "I")
	public static int anInt4424 = 0;
@OriginalMember(owner = "client!lq", name = "a", descriptor = "(Lclient!ga;ILclient!ya;)V")
	public static void method3465(@OriginalArg(0) unpack_Sub1 arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		if (Static148.area == null) {
			return;
		}
		if (Class50.anInt3429 < 10) {
			if (!Static148.MAP_AREA_RESOURCE.method2103(Static148.area.filenamePrefix)) {
				Class50.anInt3429 = Class4_Sub23.aClass76_39.method2113(Static148.area.filenamePrefix) / 10;
				return;
			}
			Class4_Sub43.method5412();
			Class50.anInt3429 = 10;
		}
		if (Class50.anInt3429 == 10) {
			Static148.anInt5732 = Static148.area.anInt1010 >> 6 << 6;
			Static148.anInt5724 = Static148.area.anInt1018 >> 6 << 6;
			Static148.anInt5729 = (Static148.area.anInt1014 >> 6 << 6) + 64 - Static148.anInt5732;
			Static148.anInt5733 = (Static148.area.anInt1019 >> 6 << 6) + 64 - Static148.anInt5724;
			@Pc(74) int[] local74 = new int[3];
			@Pc(76) int local76 = -1;
			@Pc(78) int local78 = -1;
			if (Static148.area.method935(local74, Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 >> 7) + OpenGLTextureCache.anInt1771, Class50_Sub1.anInt3453 - -(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 >> 7))) {
				local76 = local74[1] - Static148.anInt5732;
				local78 = local74[2] - Static148.anInt5724;
			}
			if (!MonochromeImageCacheSlot.aBoolean98 && local76 >= 0 && Static148.anInt5729 > local76 && local78 >= 0 && Static148.anInt5733 > local78) {
				local76 += (int) (Math.random() * 10.0D) - 5;
				local78 += (int) (Math.random() * 10.0D) - 5;
				OpenGl2DImageTexture.anInt5471 = local76;
				TextureCombinerOp.anInt5156 = local78;
			} else if (Class120_Sub1.anInt5361 == -1 || MidiPcmStream.anInt1620 == -1) {
				Static148.area.method931(local74, Static148.area.anInt1023 & 0x3FFF, Static148.area.anInt1023 >> 14 & 0x3FFF);
				TextureCombinerOp.anInt5156 = local74[2] - Static148.anInt5724;
				OpenGl2DImageTexture.anInt5471 = local74[1] - Static148.anInt5732;
			} else {
				Static148.area.method931(local74, MidiPcmStream.anInt1620, Class120_Sub1.anInt5361);
				if (local74 != null) {
					OpenGl2DImageTexture.anInt5471 = local74[1] - Static148.anInt5732;
					TextureCombinerOp.anInt5156 = local74[2] - Static148.anInt5724;
				}
				MonochromeImageCacheSlot.aBoolean98 = false;
				MidiPcmStream.anInt1620 = -1;
				Class120_Sub1.anInt5361 = -1;
			}
			if (Static148.area.anInt1016 == 37) {
				Static148.mapZoom = 3.0F;
				Static148.requestedMapZoom = 3.0F;
			} else if (Static148.area.anInt1016 == 50) {
				Static148.mapZoom = 4.0F;
				Static148.requestedMapZoom = 4.0F;
			} else if (Static148.area.anInt1016 == 75) {
				Static148.mapZoom = 6.0F;
				Static148.requestedMapZoom = 6.0F;
			} else if (Static148.area.anInt1016 == 100) {
				Static148.mapZoom = 8.0F;
				Static148.requestedMapZoom = 8.0F;
			} else if (Static148.area.anInt1016 == 200) {
				Static148.mapZoom = 16.0F;
				Static148.requestedMapZoom = 16.0F;
			} else {
				Static148.mapZoom = 8.0F;
				Static148.requestedMapZoom = 8.0F;
			}
			Static148.tileSize = (int) Static148.mapZoom >> 1;
			Static148.tileShapePatterns = Static449.method5655(Static148.tileSize);
			JavaMatrix.method5160();
			Static148.method4502();
			EnvironmentSampler.aClass183_29 = new Class183();
			Static148.anInt5719 += (int) (Math.random() * 5.0D) - 2;
			if (Static148.anInt5719 < -8) {
				Static148.anInt5719 = -8;
			}
			Static148.anInt5721 += (int) (Math.random() * 5.0D) - 2;
			if (Static148.anInt5719 > 8) {
				Static148.anInt5719 = 8;
			}
			if (Static148.anInt5721 < -16) {
				Static148.anInt5721 = -16;
			}
			if (Static148.anInt5721 > 16) {
				Static148.anInt5721 = 16;
			}
			Static148.initOverlayColours(arg0, Static148.anInt5719 >> 2 << 10, Static148.anInt5721 >> 1);
			Static148.MECTYPE_LIST.method4131(256, 1024);
			Static148.MAP_SPRITE_TYPE_LIST.method5591(256, 256);
			Static148.LOCTYPE_LIST.method5565(4096);
			KeyRecordEntry.aClass26_1.method646(256);
			Class50.anInt3429 = 20;
		} else if (Class50.anInt3429 == 20) {
			Class177.method4087(true);
			Static148.renderMap(arg1, Static148.anInt5719, Static148.anInt5721);
			Class50.anInt3429 = 60;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
		} else if (Class50.anInt3429 == 60) {
			if (Static148.MAP_AREA_RESOURCE.method2107(Static148.area.filenamePrefix + "_staticelements")) {
				if (!Static148.MAP_AREA_RESOURCE.method2103(Static148.area.filenamePrefix + "_staticelements")) {
					return;
				}
				Static148.staticElements = Static380.method5018(Static148.MAP_AREA_RESOURCE, Static148.area.filenamePrefix + "_staticelements", MusicPatch.aBoolean506);
			} else {
				Static148.staticElements = new StaticElements(0);
			}
			Static148.method4503();
			Class50.anInt3429 = 70;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
		} else if (Class50.anInt3429 == 70) {
			Static258.aClass212_6 = new HelveticaFont(arg1, 11, true, Class164.aCanvas5);
			Class50.anInt3429 = 73;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
		} else if (Class50.anInt3429 == 73) {
			Class222.aClass212_8 = new HelveticaFont(arg1, 12, true, Class164.aCanvas5);
			Class50.anInt3429 = 76;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
		} else if (Class50.anInt3429 == 76) {
			ParticleList.aClass212_5 = new HelveticaFont(arg1, 14, true, Class164.aCanvas5);
			Class50.anInt3429 = 79;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
		} else if (Class50.anInt3429 == 79) {
			RectangularTextureGL.aClass212_3 = new HelveticaFont(arg1, 17, true, Class164.aCanvas5);
			Class50.anInt3429 = 82;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
		} else if (Class50.anInt3429 == 82) {
			EnumTypeList.aClass212_7 = new HelveticaFont(arg1, 19, true, Class164.aCanvas5);
			Class50.anInt3429 = 85;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
		} else if (Class50.anInt3429 == 85) {
			GlGroundLayer.aClass212_2 = new HelveticaFont(arg1, 22, true, Class164.aCanvas5);
			Class50.anInt3429 = 88;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
		} else if (Class50.anInt3429 == 88) {
			MonochromeImageCacheSlot.aClass212_1 = new HelveticaFont(arg1, 26, true, Class164.aCanvas5);
			Class50.anInt3429 = 91;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
		} else {
			Class4_Sub19.aClass212_4 = new HelveticaFont(arg1, 30, true, Class164.aCanvas5);
			Class50.anInt3429 = 100;
			Class177.method4087(true);
			GlVboArrayBuffer.method3660();
			System.gc();
		}
	}
@OriginalMember(owner = "client!lq", name = "a", descriptor = "(III)Z")
	public static boolean method3468(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return ((arg1 & 0x2000) != 0 | Class9_Sub3.method1353(arg0, arg1) | Js5ResourceRequest.method2904(arg0, arg1)) & Class4_Sub10.method2002(arg1, arg0);
	}
@OriginalMember(owner = "client!lq", name = "b", descriptor = "(Z)V")
	public static void method3470() {
		MapRegion.anInt7307 = 0;
		@Pc(11) int local11 = Class14.aClass4_Sub12_Sub1_3.method2489();
		@Pc(15) int local15 = Class14.aClass4_Sub12_Sub1_3.method2548();
		@Pc(19) int local19 = Class14.aClass4_Sub12_Sub1_3.method2515();
		@Pc(30) boolean local30 = Class14.aClass4_Sub12_Sub1_3.method2520() == 1;
		Class4_Sub6_Sub25.method3809(local11);
		@Pc(41) int local41 = (PointEntity.anInt4075 - Class14.aClass4_Sub12_Sub1_3.currentPosition) / 16;
		Model.anIntArrayArray50 = new int[local41][4];
		@Pc(51) int local51;
		for (@Pc(47) int local47 = 0; local47 < local41; local47++) {
			for (local51 = 0; local51 < 4; local51++) {
				Model.anIntArrayArray50[local47][local51] = Class14.aClass4_Sub12_Sub1_3.method2529();
			}
		}
		GlFramebuffer.aByteArrayArray16 = new byte[local41][];
		Class204.anIntArray418 = new int[local41];
		ProjectileAnimationNode.anIntArray155 = new int[local41];
		Class259.aByteArrayArray30 = new byte[local41][];
		Static308.anIntArray372 = new int[local41];
		Class136.anIntArray291 = new int[local41];
		Class99_Sub1.aByteArrayArray15 = new byte[local41][];
		ReflectionCheck.aByteArrayArray1 = null;
		Game.anIntArray236 = new int[local41];
		Class16_Sub4_Sub3.aByteArrayArray13 = new byte[local41][];
		PlayerVarDomain.anIntArray222 = null;
		local41 = 0;
		for (local51 = (local15 - (GLBillowTextureData.anInt5666 >> 4)) / 8; local51 <= ((GLBillowTextureData.anInt5666 >> 4) + local15) / 8; local51++) {
			for (@Pc(122) int local122 = (local19 - (Static283.anInt5187 >> 4)) / 8; local122 <= ((Static283.anInt5187 >> 4) + local19) / 8; local122++) {
				Static308.anIntArray372[local41] = local122 + (local51 << 8);
				Class136.anIntArray291[local41] = Class4_Sub10.aClass76_27.method2099("m" + local51 + "_" + local122);
				Class204.anIntArray418[local41] = Class4_Sub10.aClass76_27.method2099("l" + local51 + "_" + local122);
				ProjectileAnimationNode.anIntArray155[local41] = Class4_Sub10.aClass76_27.method2099("um" + local51 + "_" + local122);
				Game.anIntArray236[local41] = Class4_Sub10.aClass76_27.method2099("ul" + local51 + "_" + local122);
				local41++;
			}
		}
		GzipDecompressor.method4167(false, local19, local15, local30);
	}
}
