package com.jagex.game.runetek5.config.sequence.spot;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.network.packet.ServerProt;

import com.jagex.game.runetek5.config.sequence.spot.*;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
@OriginalClass("client!nm")
public final class SpotAnimationType {

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "[S")
	private short[] recolorSrc;

	@OriginalMember(owner = "client!nm", name = "f", descriptor = "[S")
	private short[] aShortArray166;

	@OriginalMember(owner = "client!nm", name = "h", descriptor = "Lclient!qj;")
	public SpotAnimationTypeList gfxTypeList;

	@OriginalMember(owner = "client!nm", name = "i", descriptor = "[S")
	private short[] aShortArray167;

	@OriginalMember(owner = "client!nm", name = "n", descriptor = "I")
	private int modelId;

	@OriginalMember(owner = "client!nm", name = "v", descriptor = "[S")
	private short[] aShortArray168;

	@OriginalMember(owner = "client!nm", name = "z", descriptor = "I")
	public int currentId;

	@OriginalMember(owner = "client!nm", name = "g", descriptor = "B")
	public byte contourType = 0;

	@OriginalMember(owner = "client!nm", name = "d", descriptor = "I")
	private int scaleXZ = 128;

	@OriginalMember(owner = "client!nm", name = "c", descriptor = "I")
	private int scaleY = 128;

	@OriginalMember(owner = "client!nm", name = "r", descriptor = "Z")
	public boolean replay = false;

	@OriginalMember(owner = "client!nm", name = "o", descriptor = "I")
	private int rotation = 0;

	@OriginalMember(owner = "client!nm", name = "t", descriptor = "I")
	private int brightness = 0;

	@OriginalMember(owner = "client!nm", name = "w", descriptor = "I")
	public int sequenceId = -1;

	@OriginalMember(owner = "client!nm", name = "x", descriptor = "I")
	private int contrast = 0;

	@OriginalMember(owner = "client!nm", name = "y", descriptor = "I")
	private int contourModifier = -1;

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "(IILclient!du;BIILclient!ya;)Lclient!t;")
	public Model method3830(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) SeqTypeList arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) com.jagex.game.runetek5.graphics.Toolkit arg5) {
		return this.method3836(arg0, arg4, arg2, 0, null, false, arg5, arg3, (byte) 2, arg1, 0, 0, null);
	}

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "(BLclient!iv;)V")
	public void method3831(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(28) int local28 = arg0.method2490();
			if (local28 == 0) {
				return;
			}
			this.method3835(arg0, local28);
		}
	}

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "(Lclient!ta;ZIILclient!ya;IILclient!ta;IZLclient!du;II)Lclient!t;")
	public Model method3833(@OriginalArg(0) Class65 arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) com.jagex.game.runetek5.graphics.Toolkit arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) Class65 arg6, @OriginalArg(8) int arg7, @OriginalArg(10) SeqTypeList arg8, @OriginalArg(11) int arg9, @OriginalArg(12) int arg10) {
		return this.method3836(arg5, arg9, arg8, arg1, arg6, true, arg3, arg7, (byte) 2, arg10, arg2, arg4, arg0);
	}

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "(BLclient!iv;I)V")
	private void method3835(@OriginalArg(1) Packet arg0, @OriginalArg(2) int arg1) {
		if (arg1 == 1) {
			this.modelId = arg0.method2536();
		} else if (arg1 == 2) {
			this.sequenceId = arg0.method2536();
		} else if (arg1 == 4) {
			this.scaleXZ = arg0.method2536();
		} else if (arg1 == 5) {
			this.scaleY = arg0.method2536();
		} else if (arg1 == 6) {
			this.rotation = arg0.method2536();
		} else if (arg1 == 7) {
			this.brightness = arg0.method2490();
		} else if (arg1 == 8) {
			this.contrast = arg0.method2490();
		} else if (arg1 == 9) {
			this.contourType = 3;
			this.contourModifier = 8224;
		} else if (arg1 == 10) {
			this.replay = true;
		} else if (arg1 == 11) {
			this.contourType = 1;
		} else if (arg1 == 12) {
			this.contourType = 4;
		} else if (arg1 == 13) {
			this.contourType = 5;
		} else if (arg1 == 14) {
			this.contourType = 2;
			this.contourModifier = arg0.method2490() * 256;
		} else if (arg1 == 15) {
			this.contourType = 3;
			this.contourModifier = arg0.method2536();
		} else if (arg1 == 16) {
			this.contourType = 3;
			this.contourModifier = arg0.method2529();
			return;
		} else {
			@Pc(131) int local131;
			@Pc(141) int local141;
			if (arg1 == 40) {
				local131 = arg0.method2490();
				this.aShortArray166 = new short[local131];
				this.aShortArray167 = new short[local131];
				for (local141 = 0; local141 < local131; local141++) {
					this.aShortArray167[local141] = (short) arg0.method2536();
					this.aShortArray166[local141] = (short) arg0.method2536();
				}
				return;
			}
			if (arg1 == 41) {
				local131 = arg0.method2490();
				this.recolorSrc = new short[local131];
				this.aShortArray168 = new short[local131];
				for (local141 = 0; local141 < local131; local141++) {
					this.recolorSrc[local141] = (short) arg0.method2536();
					this.aShortArray168[local141] = (short) arg0.method2536();
				}
				return;
			}
		}
	}

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "(IILclient!du;ILclient!ta;ZLclient!ya;IBZIIILclient!ta;)Lclient!t;")
	private Model method3836(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) SeqTypeList arg2, @OriginalArg(3) int arg3, @OriginalArg(4) Class65 arg4, @OriginalArg(5) boolean arg5, @OriginalArg(6) com.jagex.game.runetek5.graphics.Toolkit arg6, @OriginalArg(7) int arg7, @OriginalArg(8) byte arg8, @OriginalArg(10) int arg9, @OriginalArg(11) int arg10, @OriginalArg(12) int arg11, @OriginalArg(13) Class65 arg12) {
		@Pc(7) int local7 = arg1;
		@Pc(22) SeqType local22 = this.sequenceId == -1 || arg0 == -1 ? null : arg2.method1397(this.sequenceId);
		@Pc(31) boolean local31 = arg5 & this.contourType != 0;
		if (local22 != null) {
			local7 = arg1 | local22.getFunctionMask(arg0, arg7, false);
		}
		if (local31) {
			local7 |= this.contourType == 3 ? 7 : 2;
		}
		if (this.scaleY != 128) {
			local7 |= 0x2;
		}
		if (this.scaleXZ != 128 || this.rotation != 0) {
			local7 |= 0x5;
		}
		@Pc(87) LRUCache local87 = this.gfxTypeList.aClass68_37;
		@Pc(107) Model local107;
		synchronized (this.gfxTypeList.aClass68_37) {
			local107 = (Model) this.gfxTypeList.aClass68_37.method1787((long) (this.currentId |= arg6.index << 29));
		}
		if (local107 == null || arg6.compareFunctionMasks(local107.P(), local7) != 0) {
			if (local107 != null) {
				local7 = arg6.combineFunctionMasks(local7, local107.P());
			}
			@Pc(134) int local134 = local7;
			if (this.aShortArray167 != null) {
				local134 = local7 | 0x4000;
			}
			if (this.recolorSrc != null) {
				local134 |= 0x8000;
			}
			@Pc(157) Mesh local157 = SpriteDrawQueue.method4948(this.gfxTypeList.aClass76_71, this.modelId);
			if (local157 == null) {
				return null;
			}
			if (local157.version < 13) {
				local157.method3039();
			}
			local107 = arg6.createModel(local157, local134, this.gfxTypeList.featureMask, this.brightness + 64, this.contrast + 850);
			@Pc(190) int local190;
			if (this.aShortArray167 != null) {
				for (local190 = 0; local190 < this.aShortArray167.length; local190++) {
					local107.copyAlpha(this.aShortArray167[local190], this.aShortArray166[local190]);
				}
			}
			if (this.recolorSrc != null) {
				for (local190 = 0; local190 < this.recolorSrc.length; local190++) {
					local107.I(this.recolorSrc[local190], this.aShortArray168[local190]);
				}
			}
			local107.UA(local7);
			@Pc(242) LRUCache local242 = this.gfxTypeList.aClass68_37;
			synchronized (this.gfxTypeList.aClass68_37) {
				this.gfxTypeList.aClass68_37.method1779((long) (this.currentId |= arg6.index << 29), local107);
			}
		}
		@Pc(286) Model local286 = local22 == null ? local107.copy(arg8, local7, true) : local22.method3459(arg0, arg8, local107, arg9, arg7, 0, local7);
		if (this.scaleXZ != 128 || this.scaleY != 128) {
			local286.H(this.scaleXZ, this.scaleY, this.scaleXZ);
		}
		if (this.rotation != 0) {
			if (this.rotation == 90) {
				local286.Q(4096);
			}
			if (this.rotation == 180) {
				local286.Q(8192);
			}
			if (this.rotation == 270) {
				local286.Q(12288);
			}
		}
		if (local31) {
			local286.h(this.contourType, this.contourModifier, arg4, arg12, arg10, arg3, arg11);
		}
		local286.UA(arg1);
		return local286;
	}

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "(Lclient!ya;BILclient!du;III)Lclient!t;")
	public Model method3837(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(2) int arg1, @OriginalArg(3) SeqTypeList arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		return this.method3836(arg3, arg4, arg2, 0, null, false, arg0, arg5, (byte) 5, arg1, 0, 0, null);
	}

@OriginalMember(owner = "client!nm", name = "e", descriptor = "I")
	public static int anInt4991 = 0;
@OriginalMember(owner = "client!nm", name = "p", descriptor = "I")
	public static int anInt4998 = 0;
@OriginalMember(owner = "client!nm", name = "s", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_104 = new Class83("RuneScape is loading - please wait...", "RuneScape wird geladen - bitte warten...", "Chargement de RuneScape en cours - veuillez patienter...", "RuneScape carregando. Aguarde...");
@OriginalMember(owner = "client!nm", name = "A", descriptor = "I")
	public static int anInt5006 = 1;
@OriginalMember(owner = "client!nm", name = "b", descriptor = "(B)[Lclient!jc;")
	public static ServerProt[] method3834() {
		return new ServerProt[] { Js5ResourceRequest.aClass117_4, Class204.aClass117_6, Texture3DShaderGL.aClass117_1 };
	}
}
