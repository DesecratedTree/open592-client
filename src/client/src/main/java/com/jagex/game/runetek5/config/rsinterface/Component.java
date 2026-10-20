package com.jagex.game.runetek5.config.rsinterface;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.deob.Class217;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Static232;
import com.jagex.game.runetek5.deob.Static384;
import com.jagex.game.runetek5.deob.Static410;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.vardomain.VarDomain;

import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Static145;
import com.jagex.game.runetek5.deob.Static271;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.config.rsinterface.*;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class101;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.client.input.mouse.SimpleMouseLog;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub29;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
@OriginalClass("client!uu")
public final class Component {

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "[I")
	public int[] anIntArray515;

	@OriginalMember(owner = "client!uu", name = "d", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray6;

	@OriginalMember(owner = "client!uu", name = "g", descriptor = "Z")
	public boolean aBoolean609;

	@OriginalMember(owner = "client!uu", name = "h", descriptor = "[I")
	public int[] cycles;

	@OriginalMember(owner = "client!uu", name = "j", descriptor = "I")
	public int lightPositionX;

	@OriginalMember(owner = "client!uu", name = "l", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray7;

	@OriginalMember(owner = "client!uu", name = "n", descriptor = "[I")
	public int[] anIntArray517;

	@OriginalMember(owner = "client!uu", name = "q", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray8;

	@OriginalMember(owner = "client!uu", name = "r", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray9;

	@OriginalMember(owner = "client!uu", name = "t", descriptor = "[B")
	public byte[] keyTriggeringAction;

	@OriginalMember(owner = "client!uu", name = "u", descriptor = "I")
	public int anInt6781;

	@OriginalMember(owner = "client!uu", name = "v", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray10;

	@OriginalMember(owner = "client!uu", name = "x", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray11;

	@OriginalMember(owner = "client!uu", name = "z", descriptor = "[I")
	public int[] anIntArray518;

	@OriginalMember(owner = "client!uu", name = "G", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray12;

	@OriginalMember(owner = "client!uu", name = "I", descriptor = "Ljava/lang/String;")
	public String aString64;

	@OriginalMember(owner = "client!uu", name = "J", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray13;

	@OriginalMember(owner = "client!uu", name = "K", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray14;

	@OriginalMember(owner = "client!uu", name = "P", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray15;

	@OriginalMember(owner = "client!uu", name = "U", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray16;

	@OriginalMember(owner = "client!uu", name = "V", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray17;

	@OriginalMember(owner = "client!uu", name = "W", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray18;

	@OriginalMember(owner = "client!uu", name = "Z", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray19;

	@OriginalMember(owner = "client!uu", name = "ab", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray20;

	@OriginalMember(owner = "client!uu", name = "eb", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray21;

	@OriginalMember(owner = "client!uu", name = "fb", descriptor = "I")
	public int lightPositionZ;

	@OriginalMember(owner = "client!uu", name = "gb", descriptor = "Ljava/lang/String;")
	public String name;

	@OriginalMember(owner = "client!uu", name = "ib", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray22;

	@OriginalMember(owner = "client!uu", name = "nb", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray23;

	@OriginalMember(owner = "client!uu", name = "wb", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray24;

	@OriginalMember(owner = "client!uu", name = "zb", descriptor = "I")
	public int lightPositionY;

	@OriginalMember(owner = "client!uu", name = "Ab", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray25;

	@OriginalMember(owner = "client!uu", name = "Db", descriptor = "I")
	public int sunRotationX;

	@OriginalMember(owner = "client!uu", name = "Mb", descriptor = "I")
	public int serverTriggers;

	@OriginalMember(owner = "client!uu", name = "Sb", descriptor = "[Ljava/lang/String;")
	public String[] options;

	@OriginalMember(owner = "client!uu", name = "Tb", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray26;

	@OriginalMember(owner = "client!uu", name = "Xb", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray27;

	@OriginalMember(owner = "client!uu", name = "ac", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray28;

	@OriginalMember(owner = "client!uu", name = "hc", descriptor = "[I")
	public int[] anIntArray519;

	@OriginalMember(owner = "client!uu", name = "jc", descriptor = "[I")
	public int[] anIntArray520;

	@OriginalMember(owner = "client!uu", name = "kc", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray29;

	@OriginalMember(owner = "client!uu", name = "pc", descriptor = "[I")
	public int[] opCursors;

	@OriginalMember(owner = "client!uu", name = "sc", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray30;

	@OriginalMember(owner = "client!uu", name = "vc", descriptor = "I")
	public int anInt6840;

	@OriginalMember(owner = "client!uu", name = "wc", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray31;

	@OriginalMember(owner = "client!uu", name = "yc", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray32;

	@OriginalMember(owner = "client!uu", name = "Cc", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray33;

	@OriginalMember(owner = "client!uu", name = "Lc", descriptor = "[B")
	public byte[] keyTriggerParams;

	@OriginalMember(owner = "client!uu", name = "Oc", descriptor = "Z")
	public boolean aBoolean623;

	@OriginalMember(owner = "client!uu", name = "Sc", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray34;

	@OriginalMember(owner = "client!uu", name = "Tc", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray35;

	@OriginalMember(owner = "client!uu", name = "Wc", descriptor = "[I")
	public int[] anIntArray522;

	@OriginalMember(owner = "client!uu", name = "ad", descriptor = "[Lclient!uu;")
	public Component[] slotChildren;

	@OriginalMember(owner = "client!uu", name = "ed", descriptor = "[I")
	public int[] anIntArray523;

	@OriginalMember(owner = "client!uu", name = "fd", descriptor = "[Ljava/lang/Object;")
	public Object[] anObjectArray36;

	@OriginalMember(owner = "client!uu", name = "id", descriptor = "I")
	public int sunRotationY;

	@OriginalMember(owner = "client!uu", name = "jd", descriptor = "Lclient!ib;")
	private CubeMapSpecularEffect aClass102_39;

	@OriginalMember(owner = "client!uu", name = "ld", descriptor = "[I")
	public int[] anIntArray524;

	@OriginalMember(owner = "client!uu", name = "A", descriptor = "I")
	public int anInt6783 = 1;

	@OriginalMember(owner = "client!uu", name = "p", descriptor = "I")
	public int anInt6779 = -1;

	@OriginalMember(owner = "client!uu", name = "e", descriptor = "I")
	public int anInt6773 = -1;

	@OriginalMember(owner = "client!uu", name = "S", descriptor = "I")
	public int anInt6794 = 1;

	@OriginalMember(owner = "client!uu", name = "O", descriptor = "I")
	public int anInt6791 = -1;

	@OriginalMember(owner = "client!uu", name = "f", descriptor = "I")
	public int anInt6774 = 1;

	@OriginalMember(owner = "client!uu", name = "w", descriptor = "I")
	public int anInt6782 = 3000;

	@OriginalMember(owner = "client!uu", name = "s", descriptor = "I")
	public int anInt6780 = -1;

	@OriginalMember(owner = "client!uu", name = "lb", descriptor = "I")
	public int lb = -1;

	@OriginalMember(owner = "client!uu", name = "mb", descriptor = "I")
	public int anInt6802 = 0;

	@OriginalMember(owner = "client!uu", name = "L", descriptor = "I")
	public int anInt6788 = -1;

	@OriginalMember(owner = "client!uu", name = "k", descriptor = "I")
	public int anInt6777 = GlRectangleTexture.anInt5106;

	@OriginalMember(owner = "client!uu", name = "bb", descriptor = "I")
	public int anInt6798 = -1;

	@OriginalMember(owner = "client!uu", name = "Lb", descriptor = "Lclient!ns;")
	public GlRectangleTexture aClass4_Sub33_2 = Class53.aClass4_Sub33_1;

	@OriginalMember(owner = "client!uu", name = "o", descriptor = "I")
	public int anInt6778 = 0;

	@OriginalMember(owner = "client!uu", name = "vb", descriptor = "I")
	public int anInt6808 = 0;

	@OriginalMember(owner = "client!uu", name = "Cb", descriptor = "Z")
	public boolean aBoolean614 = false;

	@OriginalMember(owner = "client!uu", name = "m", descriptor = "Z")
	public boolean aBoolean610 = false;

	@OriginalMember(owner = "client!uu", name = "Pb", descriptor = "I")
	public int anInt6822 = 0;

	@OriginalMember(owner = "client!uu", name = "Zb", descriptor = "I")
	public int anInt6829 = 0;

	@OriginalMember(owner = "client!uu", name = "db", descriptor = "Z")
	public boolean aBoolean613 = false;

	@OriginalMember(owner = "client!uu", name = "c", descriptor = "I")
	public int anInt6772 = 0;

	@OriginalMember(owner = "client!uu", name = "Gb", descriptor = "I")
	public int anInt6815 = 0;

	@OriginalMember(owner = "client!uu", name = "Q", descriptor = "I")
	public int anInt6792 = 0;

	@OriginalMember(owner = "client!uu", name = "rb", descriptor = "I")
	public int anInt6804 = 0;

	@OriginalMember(owner = "client!uu", name = "Rb", descriptor = "I")
	public int anInt6824 = -1;

	@OriginalMember(owner = "client!uu", name = "cc", descriptor = "Z")
	public boolean aBoolean615 = false;

	@OriginalMember(owner = "client!uu", name = "Ib", descriptor = "I")
	public int anInt6816 = 0;

	@OriginalMember(owner = "client!uu", name = "B", descriptor = "Z")
	public boolean aBoolean612 = false;

	@OriginalMember(owner = "client!uu", name = "ec", descriptor = "I")
	public int anInt6832 = 0;

	@OriginalMember(owner = "client!uu", name = "N", descriptor = "I")
	public int anInt6790 = -1;

	@OriginalMember(owner = "client!uu", name = "M", descriptor = "I")
	public int anInt6789 = -1;

	@OriginalMember(owner = "client!uu", name = "Nb", descriptor = "I")
	public int anInt6820 = 0;

	@OriginalMember(owner = "client!uu", name = "sb", descriptor = "I")
	public int anInt6805 = 0;

	@OriginalMember(owner = "client!uu", name = "F", descriptor = "I")
	public int anInt6786 = 0;

	@OriginalMember(owner = "client!uu", name = "Hb", descriptor = "Lclient!uu;")
	public Component depthChildOverride = null;

	@OriginalMember(owner = "client!uu", name = "X", descriptor = "I")
	public int anInt6796 = 1;

	@OriginalMember(owner = "client!uu", name = "yb", descriptor = "I")
	public int anInt6809 = -1;

	@OriginalMember(owner = "client!uu", name = "Fb", descriptor = "I")
	public int anInt6814 = 0;

	@OriginalMember(owner = "client!uu", name = "jb", descriptor = "Ljava/lang/String;")
	public String aString66 = "";

	@OriginalMember(owner = "client!uu", name = "Kb", descriptor = "I")
	public int anInt6818 = 0;

	@OriginalMember(owner = "client!uu", name = "Vb", descriptor = "I")
	public int anInt6826 = 0;

	@OriginalMember(owner = "client!uu", name = "Yb", descriptor = "I")
	public int anInt6828 = 2;

	@OriginalMember(owner = "client!uu", name = "fc", descriptor = "I")
	public int anInt6833 = 0;

	@OriginalMember(owner = "client!uu", name = "rc", descriptor = "Z")
	public boolean aBoolean617 = false;

	@OriginalMember(owner = "client!uu", name = "xc", descriptor = "Z")
	public boolean aBoolean619 = false;

	@OriginalMember(owner = "client!uu", name = "tb", descriptor = "I")
	public int anInt6806 = 0;

	@OriginalMember(owner = "client!uu", name = "dc", descriptor = "I")
	public int anInt6831 = 0;

	@OriginalMember(owner = "client!uu", name = "y", descriptor = "Z")
	public boolean aBoolean611 = false;

	@OriginalMember(owner = "client!uu", name = "Bc", descriptor = "I")
	public int anInt6843 = 0;

	@OriginalMember(owner = "client!uu", name = "T", descriptor = "I")
	public int anInt6795 = 0;

	@OriginalMember(owner = "client!uu", name = "C", descriptor = "B")
	public byte aByte78 = 0;

	@OriginalMember(owner = "client!uu", name = "ob", descriptor = "B")
	public byte aByte80 = 0;

	@OriginalMember(owner = "client!uu", name = "Mc", descriptor = "I")
	public int anInt6849 = 0;

	@OriginalMember(owner = "client!uu", name = "Qc", descriptor = "B")
	public byte aByte81 = 0;

	@OriginalMember(owner = "client!uu", name = "Rc", descriptor = "I")
	public int anInt6852 = -1;

	@OriginalMember(owner = "client!uu", name = "Kc", descriptor = "Z")
	public boolean aBoolean622 = false;

	@OriginalMember(owner = "client!uu", name = "R", descriptor = "I")
	public int anInt6793 = 0;

	@OriginalMember(owner = "client!uu", name = "Jc", descriptor = "I")
	public int anInt6848 = -1;

	@OriginalMember(owner = "client!uu", name = "Hc", descriptor = "I")
	public int anInt6846 = 0;

	@OriginalMember(owner = "client!uu", name = "cb", descriptor = "B")
	public byte aByte79 = 0;

	@OriginalMember(owner = "client!uu", name = "pb", descriptor = "I")
	public int anInt6803 = 0;

	@OriginalMember(owner = "client!uu", name = "Vc", descriptor = "Ljava/lang/String;")
	public String aString68 = "";

	@OriginalMember(owner = "client!uu", name = "Uc", descriptor = "I")
	public int anInt6853 = 0;

	@OriginalMember(owner = "client!uu", name = "mc", descriptor = "I")
	public int anInt6836 = 0;

	@OriginalMember(owner = "client!uu", name = "uc", descriptor = "Z")
	public boolean aBoolean618 = false;

	@OriginalMember(owner = "client!uu", name = "Fc", descriptor = "Z")
	public boolean aBoolean620 = false;

	@OriginalMember(owner = "client!uu", name = "Ob", descriptor = "I")
	public int anInt6821 = 0;

	@OriginalMember(owner = "client!uu", name = "xb", descriptor = "Ljava/lang/String;")
	public String aString67 = "";

	@OriginalMember(owner = "client!uu", name = "dd", descriptor = "I")
	public int spriteScale = 100;

	@OriginalMember(owner = "client!uu", name = "qc", descriptor = "I")
	public int anInt6839 = 0;

	@OriginalMember(owner = "client!uu", name = "gc", descriptor = "I")
	public int anInt6834 = 0;

	@OriginalMember(owner = "client!uu", name = "Ic", descriptor = "I")
	public int anInt6847 = 0;

	@OriginalMember(owner = "client!uu", name = "oc", descriptor = "I")
	public int anInt6838 = 0;

	@OriginalMember(owner = "client!uu", name = "Pc", descriptor = "I")
	public int anInt6851 = 0;

	@OriginalMember(owner = "client!uu", name = "Gc", descriptor = "Z")
	public boolean aBoolean621 = false;

	@OriginalMember(owner = "client!uu", name = "ic", descriptor = "Z")
	public boolean hidden = false;

	@OriginalMember(owner = "client!uu", name = "Ub", descriptor = "I")
	public int anInt6825 = 0;

	@OriginalMember(owner = "client!uu", name = "gd", descriptor = "I")
	public int anInt6858 = 0;

	@OriginalMember(owner = "client!uu", name = "Dc", descriptor = "I")
	public int anInt6844 = 0;

	@OriginalMember(owner = "client!uu", name = "Nc", descriptor = "I")
	public int anInt6850 = -1;

	@OriginalMember(owner = "client!uu", name = "bd", descriptor = "Z")
	public boolean aBoolean624 = false;

	@OriginalMember(owner = "client!uu", name = "hd", descriptor = "I")
	public int anInt6859 = 0;

	@OriginalMember(owner = "client!uu", name = "Jb", descriptor = "I")
	public int anInt6817 = 0;

	@OriginalMember(owner = "client!uu", name = "md", descriptor = "I")
	public int anInt6862 = 0;

	@OriginalMember(owner = "client!uu", name = "cd", descriptor = "I")
	public int anInt6856 = 1;

	@OriginalMember(owner = "client!uu", name = "Wb", descriptor = "I")
	public int anInt6827 = 0;

	@OriginalMember(owner = "client!uu", name = "Ec", descriptor = "I")
	public int anInt6845 = -1;

	@OriginalMember(owner = "client!uu", name = "pd", descriptor = "I")
	public int anInt6865 = -1;

	@OriginalMember(owner = "client!uu", name = "Ac", descriptor = "I")
	public int anInt6842 = 0;

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(Ljava/lang/String;II)V")
	public void method5297(@OriginalArg(0) String arg0, @OriginalArg(1) int arg1) {
		if (this.aClass102_39 == null) {
			this.aClass102_39 = new CubeMapSpecularEffect(16);
			this.aClass102_39.method2703((long) arg1, new Class4_Sub7(arg0));
			return;
		}
		@Pc(41) Class4_Sub7 local41 = (Class4_Sub7) this.aClass102_39.method2700((long) arg1);
		if (local41 == null) {
			this.aClass102_39.method2703((long) arg1, new Class4_Sub7(arg0));
		} else {
			local41.aString12 = arg0;
		}
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(III)V")
	public void method5298(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		if (this.opCursors == null || this.opCursors.length <= arg1) {
			@Pc(26) int[] local26 = new int[arg1 + 1];
			if (this.opCursors != null) {
				for (@Pc(31) int local31 = 0; local31 < this.opCursors.length; local31++) {
					local26[local31] = this.opCursors[local31];
				}
				for (@Pc(50) int local50 = this.opCursors.length; local50 < arg1; local50++) {
					local26[local50] = -1;
				}
			}
			this.opCursors = local26;
		}
		this.opCursors[arg1] = arg0;
	}

	@OriginalMember(owner = "client!uu", name = "b", descriptor = "(III)I")
	public int method5299(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		if (this.aClass102_39 == null) {
			return arg0;
		} else {
			@Pc(16) Class4_Sub37 local16 = (Class4_Sub37) this.aClass102_39.method2700((long) arg1);
			return local16 == null ? arg0 : local16.anInt5426;
		}
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(Lclient!ya;B)Lclient!l;")
	public NativeSprite method5300(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		GlMemoryPool.aBoolean15 = false;
		@Pc(59) long local59 = ((long) this.anInt6778 << 40) + ((this.aBoolean623 ? 1L : 0L) << 39) + (long) this.lb + ((this.aBoolean614 ? 1L : 0L) << 35) + ((long) this.anInt6831 << 36) + ((this.aBoolean609 ? 1L : 0L) << 38);
		@Pc(65) NativeSprite local65 = (NativeSprite) Class48_Sub1_Sub1.aClass68_26.method1787(local59);
		if (local65 != null) {
			return local65;
		}
		@Pc(75) SpriteType local75 = SpriteType.listSingle(ParamTypeList.aClass76_89, this.lb, 0);
		if (local75 == null) {
			GlMemoryPool.aBoolean15 = true;
			return null;
		}
		if (this.aBoolean609) {
			local75.flipVertical();
		}
		if (this.aBoolean623) {
			local75.flipHorizontal();
		}
		if (this.anInt6831 > 0) {
			local75.drawSmallBorder(this.anInt6831);
		} else if (this.anInt6778 != 0) {
			local75.drawSmallBorder(1);
		}
		if (this.anInt6831 >= 1) {
			local75.drawThickBorder(1);
		}
		if (this.anInt6831 >= 2) {
			local75.drawThickBorder(16777215);
		}
		if (this.anInt6778 != 0) {
			local75.outline(this.anInt6778 | 0xFF000000);
		}
		local65 = arg0.generateSprite(local75);
		Class48_Sub1_Sub1.aClass68_26.method1779(local59, local65);
		return local65;
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(ILclient!iv;)[Ljava/lang/Object;")
	private Object[] method5302(@OriginalArg(1) Packet arg0) {
		@Pc(9) int local9 = arg0.method2490();
		if (local9 == 0) {
			return null;
		}
		@Pc(19) Object[] local19 = new Object[local9];
		for (@Pc(26) int local26 = 0; local26 < local9; local26++) {
			@Pc(32) int local32 = arg0.method2490();
			if (local32 == 0) {
				local19[local26] = Integer.valueOf(arg0.method2529());
			} else if (local32 == 1) {
				local19[local26] = arg0.method2537();
			}
		}
		this.aBoolean612 = true;
		return local19;
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(BI)V")
	public void method5303(@OriginalArg(1) int arg0) {
		if (this.aClass102_39 != null) {
			@Pc(9) Country local9 = this.aClass102_39.method2700((long) arg0);
			if (local9 != null) {
				local9.method5684();
			}
		}
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(Lclient!iv;I)V")
	public void method5304(@OriginalArg(0) Packet arg0) {
		@Pc(9) int local9 = arg0.method2490();
		if (local9 == 255) {
			local9 = -1;
		}
		this.anInt6840 = arg0.method2490();
		if ((this.anInt6840 & 0x80) != 0) {
			this.anInt6840 &= 0x7F;
			this.name = arg0.method2537();
		}
		this.anInt6842 = arg0.method2536();
		this.anInt6804 = arg0.method2512();
		this.anInt6808 = arg0.method2512();
		this.anInt6816 = arg0.method2536();
		this.anInt6802 = arg0.method2536();
		this.aByte80 = arg0.method2542();
		this.aByte78 = arg0.method2542();
		this.aByte79 = arg0.method2542();
		this.aByte81 = arg0.method2542();
		this.anInt6850 = arg0.method2536();
		if (this.anInt6850 == 65535) {
			this.anInt6850 = -1;
		} else {
			this.anInt6850 = (this.anInt6809 & 0xFFFF0000) + this.anInt6850;
		}
		@Pc(113) int local113 = arg0.method2490();
		if (local9 >= 0) {
			this.aBoolean622 = (local113 & 0x2) != 0;
		}
		this.hidden = (local113 & 0x1) != 0;
		if (this.anInt6840 == 0) {
			this.anInt6821 = arg0.method2536();
			this.anInt6834 = arg0.method2536();
			if (local9 < 0) {
				this.aBoolean622 = arg0.method2490() == 1;
			}
		}
		if (this.anInt6840 == 5) {
			this.lb = arg0.method2529();
			this.anInt6818 = arg0.method2536();
			@Pc(181) int local181 = arg0.method2490();
			this.aBoolean621 = (local181 & 0x1) != 0;
			this.aBoolean614 = (local181 & 0x2) != 0;
			this.anInt6851 = arg0.method2490();
			this.anInt6831 = arg0.method2490();
			this.anInt6778 = arg0.method2529();
			this.aBoolean609 = arg0.method2490() == 1;
			this.aBoolean623 = arg0.method2490() == 1;
			this.anInt6772 = arg0.method2529();
		}
		if (this.anInt6840 == 6) {
			this.anInt6796 = 1;
			this.anInt6781 = arg0.method2536();
			if (this.anInt6781 == 65535) {
				this.anInt6781 = -1;
			}
			this.anInt6846 = arg0.method2512();
			this.anInt6793 = arg0.method2512();
			this.anInt6844 = arg0.method2536();
			this.anInt6858 = arg0.method2536();
			this.anInt6843 = arg0.method2536();
			this.spriteScale = arg0.method2536();
			this.anInt6788 = arg0.method2536();
			if (this.anInt6788 == 65535) {
				this.anInt6788 = -1;
			}
			this.aBoolean620 = arg0.method2490() == 1;
			this.anInt6862 = arg0.method2512();
			this.anInt6782 = arg0.method2512();
			this.aBoolean619 = arg0.method2490() == 1;
			if (this.aByte80 != 0) {
				this.anInt6822 = arg0.method2536();
			}
			if (this.aByte78 != 0) {
				this.anInt6792 = arg0.method2536();
			}
		}
		if (this.anInt6840 == 4) {
			this.anInt6791 = arg0.method2536();
			if (this.anInt6791 == 65535) {
				this.anInt6791 = -1;
			}
			this.aString67 = arg0.method2537();
			this.anInt6853 = arg0.method2490();
			this.anInt6838 = arg0.method2490();
			this.anInt6803 = arg0.method2490();
			this.aBoolean615 = arg0.method2490() == 1;
			this.anInt6772 = arg0.method2529();
			this.anInt6851 = arg0.method2490();
			if (local9 >= 0) {
				this.anInt6817 = arg0.method2490();
			}
		}
		if (this.anInt6840 == 3) {
			this.anInt6772 = arg0.method2529();
			this.aBoolean618 = arg0.method2490() == 1;
			this.anInt6851 = arg0.method2490();
		}
		if (this.anInt6840 == 9) {
			this.anInt6794 = arg0.method2490();
			this.anInt6772 = arg0.method2529();
			this.aBoolean610 = arg0.method2490() == 1;
		}
		@Pc(490) int local490 = arg0.method2493();
		@Pc(494) int local494 = arg0.method2490();
		@Pc(516) int local516;
		if (local494 != 0) {
			this.anIntArray523 = new int[11];
			this.keyTriggeringAction = new byte[11];
			this.keyTriggerParams = new byte[11];
			while (local494 != 0) {
				local516 = (local494 >> 4) - 1;
				local494 = local494 << 8 | arg0.method2490();
				local494 &= 0xFFF;
				if (local494 == 4095) {
					local494 = -1;
				}
				@Pc(539) byte local539 = arg0.method2542();
				if (local539 != 0) {
					this.aBoolean624 = true;
				}
				@Pc(548) byte local548 = arg0.method2542();
				this.anIntArray523[local516] = local494;
				this.keyTriggeringAction[local516] = local539;
				this.keyTriggerParams[local516] = local548;
				local494 = arg0.method2490();
			}
		}
		this.aString66 = arg0.method2537();
		local516 = arg0.method2490();
		@Pc(582) int local582 = local516 & 0xF;
		@Pc(593) int local593;
		if (local582 > 0) {
			this.options = new String[local582];
			for (local593 = 0; local593 < local582; local593++) {
				this.options[local593] = arg0.method2537();
			}
		}
		@Pc(614) int local614 = local516 >> 4;
		@Pc(628) int local628;
		if (local614 > 0) {
			local593 = arg0.method2490();
			this.opCursors = new int[local593 + 1];
			for (local628 = 0; local628 < this.opCursors.length; local628++) {
				this.opCursors[local628] = -1;
			}
			this.opCursors[local593] = arg0.method2536();
		}
		if (local614 > 1) {
			local593 = arg0.method2490();
			this.opCursors[local593] = arg0.method2536();
		}
		this.aString64 = arg0.method2537();
		if (this.aString64.equals("")) {
			this.aString64 = null;
		}
		this.anInt6795 = arg0.method2490();
		this.anInt6786 = arg0.method2490();
		this.anInt6777 = arg0.method2490();
		this.aString68 = arg0.method2537();
		local593 = -1;
		if (Class95_Sub3_Sub1.method4995(local490) != 0) {
			local593 = arg0.method2536();
			this.anInt6790 = arg0.method2536();
			if (local593 == 65535) {
				local593 = -1;
			}
			if (this.anInt6790 == 65535) {
				this.anInt6790 = -1;
			}
			this.anInt6773 = arg0.method2536();
			if (this.anInt6773 == 65535) {
				this.anInt6773 = -1;
			}
		}
		if (local9 >= 0) {
			this.anInt6789 = arg0.method2536();
			if (this.anInt6789 == 65535) {
				this.anInt6789 = -1;
			}
		}
		this.aClass4_Sub33_2 = new GlRectangleTexture(local490, local593);
		if (local9 >= 0) {
			local628 = arg0.method2490();
			@Pc(786) int local786;
			@Pc(790) int local790;
			for (@Pc(780) int local780 = 0; local780 < local628; local780++) {
				local786 = arg0.method2493();
				local790 = arg0.method2529();
				this.aClass102_39.method2703((long) local786, new Class4_Sub37(local790));
			}
			local786 = arg0.method2490();
			for (local790 = 0; local790 < local786; local790++) {
				@Pc(816) int local816 = arg0.method2493();
				@Pc(820) String local820 = arg0.method2497();
				this.aClass102_39.method2703((long) local816, new Class4_Sub7(local820));
			}
		}
		this.anObjectArray28 = this.method5302(arg0);
		this.anObjectArray32 = this.method5302(arg0);
		this.anObjectArray9 = this.method5302(arg0);
		this.anObjectArray25 = this.method5302(arg0);
		this.anObjectArray21 = this.method5302(arg0);
		this.anObjectArray18 = this.method5302(arg0);
		this.anObjectArray19 = this.method5302(arg0);
		this.anObjectArray36 = this.method5302(arg0);
		this.anObjectArray8 = this.method5302(arg0);
		this.anObjectArray15 = this.method5302(arg0);
		if (local9 >= 0) {
			this.anObjectArray17 = this.method5302(arg0);
		}
		this.anObjectArray27 = this.method5302(arg0);
		this.anObjectArray12 = this.method5302(arg0);
		this.anObjectArray23 = this.method5302(arg0);
		this.anObjectArray33 = this.method5302(arg0);
		this.anObjectArray22 = this.method5302(arg0);
		this.anObjectArray26 = this.method5302(arg0);
		this.anObjectArray16 = this.method5302(arg0);
		this.anObjectArray24 = this.method5302(arg0);
		this.anObjectArray30 = this.method5302(arg0);
		this.anObjectArray34 = this.method5302(arg0);
		this.anIntArray515 = this.method5306(arg0);
		this.anIntArray520 = this.method5306(arg0);
		this.anIntArray522 = this.method5306(arg0);
		this.anIntArray518 = this.method5306(arg0);
		this.anIntArray517 = this.method5306(arg0);
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(Ljava/lang/String;IB)V")
	public void method5305(@OriginalArg(0) String arg0, @OriginalArg(1) int arg1) {
		if (this.options == null || arg1 >= this.options.length) {
			@Pc(22) String[] local22 = new String[arg1 + 1];
			if (this.options != null) {
				for (@Pc(27) int local27 = 0; local27 < this.options.length; local27++) {
					local22[local27] = this.options[local27];
				}
			}
			this.options = local22;
		}
		this.options[arg1] = arg0;
	}

	@OriginalMember(owner = "client!uu", name = "b", descriptor = "(Lclient!iv;I)[I")
	private int[] method5306(@OriginalArg(0) Packet arg0) {
		@Pc(15) int local15 = arg0.method2490();
		if (local15 == 0) {
			return null;
		}
		@Pc(25) int[] local25 = new int[local15];
		for (@Pc(27) int local27 = 0; local27 < local15; local27++) {
			local25[local27] = arg0.method2529();
		}
		return local25;
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(Lclient!ro;Lclient!ya;Lclient!uk;Lclient!cj;Lclient!du;IBLclient!lp;ILclient!in;ILclient!vs;ILclient!v;)Lclient!t;")
	public Model method5307(@OriginalArg(0) ObjTypeList arg0, @OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(2) NPCTypeList arg2, @OriginalArg(3) PlayerAppearance arg3, @OriginalArg(4) SeqTypeList arg4, @OriginalArg(5) int arg5, @OriginalArg(7) SeqType arg6, @OriginalArg(8) int arg7, @OriginalArg(9) IDKTypeList arg8, @OriginalArg(10) int arg9, @OriginalArg(11) VarDomain arg10, @OriginalArg(12) int arg11, @OriginalArg(13) Class249 arg12) {
		GlMemoryPool.aBoolean15 = false;
		if (this.anInt6796 == 0) {
			return null;
		} else if (this.anInt6796 == 1 && this.anInt6781 == -1) {
			return null;
		} else {
			@Pc(64) Model local64;
			if (this.anInt6796 == 1) {
				if (arg6 != null) {
					arg5 = arg6.getFunctionMask(arg7, arg9, true) | 0x800;
				}
				local64 = (Model) Static232.aClass68_25.method1787((long) ((this.anInt6796 << 16) + (arg1.index << 29) + this.anInt6781));
				if (local64 == null || arg1.compareFunctionMasks(local64.P(), arg5) != 0) {
					if (local64 != null) {
						arg5 = arg1.combineFunctionMasks(arg5, local64.P());
					}
					@Pc(90) Mesh local90 = SpriteDrawQueue.method4948(Static410.aClass76_95, this.anInt6781);
					if (local90 == null) {
						GlMemoryPool.aBoolean15 = true;
						return null;
					}
					if (local90.version < 13) {
						local90.method3039();
					}
					local64 = arg1.createModel(local90, arg5, Class48_Sub1_Sub1.anInt4879, 64, 768);
					Static232.aClass68_25.method1779((long) ((this.anInt6796 << 16) + this.anInt6781), local64);
				}
				if (arg6 != null) {
					local64 = arg6.method3456(arg11, arg5, arg9, arg7, local64);
				}
				local64.UA(2048);
				return local64;
			}
			@Pc(167) Model local167;
			if (this.anInt6796 == 2) {
				local167 = arg2.method5229(this.anInt6781).method5584(arg9, arg11, arg6, arg7, arg10, arg1, arg4);
				if (local167 == null) {
					GlMemoryPool.aBoolean15 = true;
					return null;
				} else {
					return local167;
				}
			} else if (this.anInt6796 == 3) {
				if (arg3 == null) {
					return null;
				}
				local167 = arg3.method830(arg7, arg9, arg2, arg0, arg10, arg11, arg6, arg8, arg1, arg4);
				if (local167 == null) {
					GlMemoryPool.aBoolean15 = true;
					return null;
				} else {
					return local167;
				}
			} else if (this.anInt6796 == 4) {
				@Pc(220) ItemType local220 = arg0.method4703(this.anInt6781);
				local64 = local220.method4773(arg11, 10, arg6, 2048, arg9, arg3, arg7, arg1);
				if (local64 == null) {
					GlMemoryPool.aBoolean15 = true;
					return null;
				} else {
					return local64;
				}
			} else if (this.anInt6796 == 6) {
				local167 = arg2.method5229(this.anInt6781).method5575(0, arg6, arg10, arg9, 2048, arg11, 0, arg1, arg7, 0, null, null, arg4, arg12);
				if (local167 == null) {
					GlMemoryPool.aBoolean15 = true;
					return null;
				} else {
					return local167;
				}
			} else if (this.anInt6796 != 7) {
				return null;
			} else if (arg3 == null) {
				return null;
			} else {
				@Pc(294) int local294 = this.anInt6781 >>> 16;
				@Pc(299) int local299 = this.anInt6781 & 0xFFFF;
				@Pc(302) int local302 = this.anInt6848;
				@Pc(319) Model local319 = arg3.method837(arg6, arg4, local294, local302, arg8, local299, arg9, arg1, arg11, arg7);
				if (local319 == null) {
					GlMemoryPool.aBoolean15 = true;
					return null;
				} else {
					return local319;
				}
			}
		}
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(Lclient!ya;I)Lclient!la;")
	public AbstractFontRenderer method5310(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		GlMemoryPool.aBoolean15 = false;
		if (this.anInt6791 == -1) {
			return null;
		}
		@Pc(19) AbstractFontRenderer local19 = (AbstractFontRenderer) Static384.aClass68_47.method1787((long) this.anInt6791);
		if (local19 != null) {
			return local19;
		}
		@Pc(28) SpriteType[] local28 = SpriteType.method323(ParamTypeList.aClass76_89, this.anInt6791);
		if (local28 == null) {
			GlMemoryPool.aBoolean15 = true;
			return null;
		}
		@Pc(40) com.jagex.game.runetek5.graphics.font.FontMetrics local40 = Class4_Sub1_Sub17.method4213(this.anInt6791, Class38.aClass76_15);
		if (local40 == null) {
			GlMemoryPool.aBoolean15 = true;
			return null;
		} else {
			local19 = arg0.createFontRenderer(local40, local28);
			Static384.aClass68_47.method1779((long) this.anInt6791, local19);
			return local19;
		}
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(ILclient!tf;Lclient!sn;)Lclient!un;")
	public Skybox method5311(@OriginalArg(1) ParamTypeList arg0, @OriginalArg(2) Class217 arg1) {
		if (this.anInt6780 == -1) {
			return null;
		}
		@Pc(50) long local50 = ((long) this.lightPositionZ & 0xFFFFL) << 16 | 0xFFFFL << 48 & (long) this.lightPositionX << 48 | ((long) this.lightPositionY & 0xFFFFL) << 32 | (long) this.anInt6780 & 0xFFFFL;
		@Pc(56) Skybox local56 = (Skybox) Class101.aClass68_16.method1787(local50);
		if (local56 == null) {
			local56 = arg1.method4934(this.lightPositionX, this.anInt6780, this.lightPositionY, this.lightPositionZ, arg0);
			Class101.aClass68_16.method1779(local50, local56);
		}
		return local56;
	}

	@OriginalMember(owner = "client!uu", name = "a", descriptor = "(ILclient!ya;)Lclient!ma;")
	public ComponentSurface method5312(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(13) ComponentSurface local13 = (ComponentSurface) Class38.aClass68_4.method1787((long) this.anInt6809);
		if (local13 != null) {
			return local13;
		}
		@Pc(23) SpriteType local23 = SpriteType.listSingle(ParamTypeList.aClass76_89, this.lb, 0);
		if (local23 == null) {
			return null;
		}
		@Pc(36) int local36 = local23.borderX + local23.width + local23.minX;
		@Pc(46) int local46 = local23.height + local23.minY + local23.borderY;
		this.anIntArray524 = new int[local46];
		this.anIntArray519 = new int[local46];
		for (@Pc(56) int local56 = 0; local56 < local23.height; local56++) {
			@Pc(60) int local60 = 0;
			@Pc(62) int local62 = local36;
			for (@Pc(64) int local64 = 0; local64 < local23.width; local64++) {
				if (local23.pixels[local64 + local23.width * local56] != 0) {
					local60 = local64;
					break;
				}
			}
			for (@Pc(88) int local88 = local60; local88 < local23.width; local88++) {
				if (local23.pixels[local23.width * local56 + local88] == 0) {
					local62 = local88;
					break;
				}
			}
			this.anIntArray524[local56 + local23.minY] = local60 + local23.minX;
			this.anIntArray519[local23.minY + local56] = local62 - local60;
		}
		local13 = arg0.createMask(local36, local46, this.anIntArray524, this.anIntArray519);
		Class38.aClass68_4.method1779((long) this.anInt6809, local13);
		return local13;
	}

	@OriginalMember(owner = "client!uu", name = "b", descriptor = "(Ljava/lang/String;II)Ljava/lang/String;")
	public String method5314(@OriginalArg(0) String arg0, @OriginalArg(1) int arg1) {
		if (this.aClass102_39 == null) {
			return arg0;
		} else {
			@Pc(22) Class4_Sub7 local22 = (Class4_Sub7) this.aClass102_39.method2700((long) arg1);
			return local22 == null ? arg0 : local22.aString12;
		}
	}

	@OriginalMember(owner = "client!uu", name = "c", descriptor = "(III)V")
	public void method5315(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		if (this.aClass102_39 == null) {
			this.aClass102_39 = new CubeMapSpecularEffect(16);
			this.aClass102_39.method2703((long) arg0, new Class4_Sub37(arg1));
			return;
		}
		@Pc(31) Class4_Sub37 local31 = (Class4_Sub37) this.aClass102_39.method2700((long) arg0);
		if (local31 == null) {
			this.aClass102_39.method2703((long) arg0, new Class4_Sub37(arg1));
		} else {
			local31.anInt5426 = arg1;
		}
	}

@OriginalMember(owner = "client!uu", name = "tc", descriptor = "Lclient!qm;")
	public static OggOtherStream aClass4_Sub39_2;
@OriginalMember(owner = "client!uu", name = "qb", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_47 = new Class183();
@OriginalMember(owner = "client!uu", name = "Qb", descriptor = "I")
	public static int anInt6823 = 0;
@OriginalMember(owner = "client!uu", name = "Yc", descriptor = "Lclient!sl;")
	private static final Class215 aClass215_91 = new Class215(70, 8);
@OriginalMember(owner = "client!uu", name = "kd", descriptor = "I")
	public static int anInt6861 = -1;
@OriginalMember(owner = "client!uu", name = "a", descriptor = "(I)V")
	public static void method5301() {
		for (@Pc(7) int local7 = 0; local7 < Class16.anInt6886; local7++) {
			@Pc(13) Class269 local13 = OpenGLTextureCache.aClass269Array1[local7];
			if (local13.aByte104 == 2) {
				if (local13.aClass4_Sub15_Sub3_4 == null) {
					local13.anInt7336 = Integer.MIN_VALUE;
				} else {
					GameCanvas.aClass4_Sub15_Sub2_2.method2955(local13.aClass4_Sub15_Sub3_4);
				}
			}
		}
	}
@OriginalMember(owner = "client!uu", name = "a", descriptor = "(BLclient!cw;)I")
	public static int method5308(@OriginalArg(1) Class4_Sub12_Sub1 arg0) {
		@Pc(10) int local10 = arg0.method1143(2);
		@Pc(27) int local27;
		if (local10 == 0) {
			local27 = 0;
		} else if (local10 == 1) {
			local27 = arg0.method1143(5);
		} else if (local10 == 2) {
			local27 = arg0.method1143(8);
		} else {
			local27 = arg0.method1143(11);
		}
		return local27;
	}
@OriginalMember(owner = "client!uu", name = "a", descriptor = "(ZILjava/lang/String;II)V")
	public static void method5309(@OriginalArg(1) int arg0, @OriginalArg(2) String arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		@Pc(8) Component local8 = EnvironmentSampler.method3230(arg2, arg3);
		if (local8 == null) {
			return;
		}
		if (local8.anObjectArray15 != null) {
			@Pc(18) HookRequest local18 = new HookRequest();
			local18.source = local8;
			local18.params = local8.anObjectArray15;
			local18.aString50 = arg1;
			local18.anInt5250 = arg0;
			Static271.executeHookInner(local18);
		}
		@Pc(35) boolean local35 = true;
		if (local8.anInt6842 != 0) {
			local35 = Class4_Sub6_Sub29.method4579(local8);
		}
		if (!local35 || !client.method900(local8).method3905(arg0 - 1)) {
			return;
		}
		if (arg0 == 1) {
			PingWorker.method5476(Class189.aClass215_70);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
		if (arg0 == 2) {
			PingWorker.method5476(Class229.aClass215_85);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
		if (arg0 == 3) {
			PingWorker.method5476(Static145.aClass215_37);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
		if (arg0 == 4) {
			PingWorker.method5476(TextureOpSquareWaveform.aClass215_95);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
		if (arg0 == 5) {
			PingWorker.method5476(SimpleMouseLog.aClass215_8);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
		if (arg0 == 6) {
			PingWorker.method5476(OpenGl2DTexture.aClass215_71);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
		if (arg0 == 7) {
			PingWorker.method5476(SoundBankCache.aClass215_64);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
		if (arg0 == 8) {
			PingWorker.method5476(Class136.aClass215_46);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
		if (arg0 == 9) {
			PingWorker.method5476(GlShadowMap.aClass215_89);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
		if (arg0 == 10) {
			PingWorker.method5476(aClass215_91);
			Resampler.method5547(arg2, arg3, local8.anInt6779);
		}
	}
}
