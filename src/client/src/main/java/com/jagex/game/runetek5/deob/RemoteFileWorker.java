package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.js5.Js5WorkerThread;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.js5.request.Js5WorkerRequestMessage;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9_Sub2;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.config.mec.MECTypeList;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!lv")
public final class RemoteFileWorker extends FileWorker {

	@OriginalMember(owner = "client!lv", name = "n", descriptor = "[B")
	private byte[] aByteArray56;

	@OriginalMember(owner = "client!lv", name = "t", descriptor = "Lclient!rt;")
	private Js5MasterIndex masterIndex;

	@OriginalMember(owner = "client!lv", name = "F", descriptor = "Z")
	private boolean aBoolean413;

	@OriginalMember(owner = "client!lv", name = "w", descriptor = "I")
	private int anInt4482 = 0;

	@OriginalMember(owner = "client!lv", name = "o", descriptor = "Lclient!ib;")
	private final CubeMapSpecularEffect aClass102_20 = new CubeMapSpecularEffect(16);

	@OriginalMember(owner = "client!lv", name = "J", descriptor = "I")
	private int anInt4489 = 0;

	@OriginalMember(owner = "client!lv", name = "G", descriptor = "Lclient!pk;")
	private final Class183 prefetchEntries = new Class183();

	@OriginalMember(owner = "client!lv", name = "L", descriptor = "J")
	private long cleanupCycle = 0L;

	@OriginalMember(owner = "client!lv", name = "h", descriptor = "Lclient!st;")
	private final Class222 cacheDataFile;

	@OriginalMember(owner = "client!lv", name = "j", descriptor = "I")
	private final int indexId;

	@OriginalMember(owner = "client!lv", name = "D", descriptor = "Z")
	private boolean aBoolean412;

	@OriginalMember(owner = "client!lv", name = "H", descriptor = "Lclient!pk;")
	private Class183 generalRequests;

	@OriginalMember(owner = "client!lv", name = "g", descriptor = "I")
	private final int tableCrc;

	@OriginalMember(owner = "client!lv", name = "k", descriptor = "I")
	private final int tableRevision;

	@OriginalMember(owner = "client!lv", name = "K", descriptor = "Z")
	private final boolean aBoolean414;

	@OriginalMember(owner = "client!lv", name = "i", descriptor = "Lclient!st;")
	private final Class222 masterIndexFile;

	@OriginalMember(owner = "client!lv", name = "l", descriptor = "Lclient!vn;")
	private final Js5WorkerThread standardRequester;

	@OriginalMember(owner = "client!lv", name = "B", descriptor = "Lclient!vl;")
	private final LocalRequester localRequester;

	@OriginalMember(owner = "client!lv", name = "x", descriptor = "Lclient!ec;")
	private Js5WorkerRequestMessage masterIndexRequest;

	@OriginalMember(owner = "client!lv", name = "<init>", descriptor = "(ILclient!st;Lclient!st;Lclient!vn;Lclient!vl;IIZ)V")
	public RemoteFileWorker(@OriginalArg(0) int arg0, @OriginalArg(1) Class222 arg1, @OriginalArg(2) Class222 arg2, @OriginalArg(3) Js5WorkerThread arg3, @OriginalArg(4) LocalRequester arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) boolean arg7) {
		this.cacheDataFile = arg1;
		this.indexId = arg0;
		if (this.cacheDataFile == null) {
			this.aBoolean412 = false;
		} else {
			this.aBoolean412 = true;
			this.generalRequests = new Class183();
		}
		this.tableCrc = arg5;
		this.tableRevision = arg6;
		this.aBoolean414 = arg7;
		this.masterIndexFile = arg2;
		this.standardRequester = arg3;
		this.localRequester = arg4;
		if (this.masterIndexFile != null) {
			this.masterIndexRequest = this.localRequester.method5432(this.masterIndexFile, this.indexId);
		}
	}

	@OriginalMember(owner = "client!lv", name = "b", descriptor = "(B)I")
	public int method3517() {
		return this.masterIndex == null ? 0 : this.masterIndex.size;
	}

	@OriginalMember(owner = "client!lv", name = "a", descriptor = "(III)Lclient!ec;")
	private Js5WorkerRequestMessage method3518(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(13) Js5WorkerRequestMessage local13 = (Js5WorkerRequestMessage) this.aClass102_20.method2700((long) arg1);
		if (local13 != null && arg0 == 0 && !local13.highPriority && local13.waiting) {
			local13.method5684();
			local13 = null;
		}
		if (local13 == null) {
			if (arg0 == 0) {
				if (this.cacheDataFile == null || this.aByteArray56[arg1] == -1) {
					if (this.standardRequester.isUrgentRequestQueueFull()) {
						return null;
					}
					local13 = this.standardRequester.method5465(this.indexId, (byte) 2, true, arg1);
				} else {
					local13 = this.localRequester.method5432(this.cacheDataFile, arg1);
				}
			} else if (arg0 == 1) {
				if (this.cacheDataFile == null) {
					throw new RuntimeException();
				}
				local13 = this.localRequester.method5431(this.cacheDataFile, arg1);
			} else if (arg0 == 2) {
				if (this.cacheDataFile == null) {
					throw new RuntimeException();
				}
				if (this.aByteArray56[arg1] != -1) {
					throw new RuntimeException();
				}
				if (this.standardRequester.isPrefetchRequestQueueFull()) {
					return null;
				}
				local13 = this.standardRequester.method5465(this.indexId, (byte) 2, false, arg1);
			} else {
				throw new RuntimeException();
			}
			this.aClass102_20.method2703((long) arg1, local13);
		}
		if (local13.waiting) {
			return null;
		}
		@Pc(161) byte[] local161 = local13.getData();
		@Pc(188) int local188;
		@Pc(238) Js5ResourceRequest local238;
		if (!(local13 instanceof Class4_Sub1_Sub6_Sub2)) {
			try {
				if (local161 == null || local161.length <= 2) {
					throw new RuntimeException();
				}
				SpotAnimationTypeList.aCRC32_1.reset();
				SpotAnimationTypeList.aCRC32_1.update(local161, 0, local161.length - 2);
				local188 = (int) SpotAnimationTypeList.aCRC32_1.getValue();
				if (local188 != this.masterIndex.groupSizes[arg1]) {
					throw new RuntimeException();
				}
				this.standardRequester.errors = 0;
				this.standardRequester.response = 0;
			} catch (@Pc(213) RuntimeException local213) {
				this.standardRequester.rekey();
				local13.method5684();
				if (local13.highPriority && !this.standardRequester.isUrgentRequestQueueFull()) {
					local238 = this.standardRequester.method5465(this.indexId, (byte) 2, true, arg1);
					this.aClass102_20.method2703((long) arg1, local238);
				}
				return null;
			}
			local161[local161.length - 2] = (byte) (this.masterIndex.groupChecksums[arg1] >>> 8);
			local161[local161.length - 1] = (byte) this.masterIndex.groupChecksums[arg1];
			if (this.cacheDataFile != null) {
				this.localRequester.method5435(arg1, local161, this.cacheDataFile);
				if (this.aByteArray56[arg1] != 1) {
					this.anInt4482++;
					this.aByteArray56[arg1] = 1;
				}
			}
			if (!local13.highPriority) {
				local13.method5684();
			}
			return local13;
		}
		try {
			if (local161 == null || local161.length <= 2) {
				throw new RuntimeException();
			}
			SpotAnimationTypeList.aCRC32_1.reset();
			SpotAnimationTypeList.aCRC32_1.update(local161, 0, local161.length - 2);
			local188 = (int) SpotAnimationTypeList.aCRC32_1.getValue();
			if (this.masterIndex.groupSizes[arg1] != local188) {
				throw new RuntimeException();
			}
			@Pc(371) int local371 = ((local161[local161.length - 2] & 0xFF) << 8) + (local161[local161.length - 1] & 0xFF);
			if (local371 != (this.masterIndex.groupChecksums[arg1] & 0xFFFF)) {
				throw new RuntimeException();
			}
			if (this.aByteArray56[arg1] != 1) {
				this.anInt4482++;
				this.aByteArray56[arg1] = 1;
			}
			if (!local13.highPriority) {
				local13.method5684();
			}
			return local13;
		} catch (@Pc(412) Exception local412) {
			this.aByteArray56[arg1] = -1;
			local13.method5684();
			if (local13.highPriority && !this.standardRequester.isUrgentRequestQueueFull()) {
				local238 = this.standardRequester.method5465(this.indexId, (byte) 2, true, arg1);
				this.aClass102_20.method2703((long) arg1, local238);
			}
			return null;
		}
	}

	@OriginalMember(owner = "client!lv", name = "b", descriptor = "(II)[B")
	@Override
	public byte[] fetchgroup(@OriginalArg(1) int arg0) {
		@Pc(16) Js5WorkerRequestMessage local16 = this.method3518(0, arg0);
		if (local16 == null) {
			return null;
		} else {
			@Pc(24) byte[] local24 = local16.getData();
			local16.method5684();
			return local24;
		}
	}

	@OriginalMember(owner = "client!lv", name = "d", descriptor = "(I)V")
	public void method3520() {
		if (this.generalRequests != null) {
			if (this.index() == null) {
				return;
			}
			@Pc(21) boolean local21;
			@Pc(28) Country local28;
			@Pc(34) int local34;
			@Pc(127) Country local127;
			if (this.aBoolean412) {
				local21 = true;
				for (local28 = this.generalRequests.method4140(); local28 != null; local28 = this.generalRequests.method4144()) {
					local34 = (int) local28.aLong224;
					if (this.aByteArray56[local34] == 0) {
						this.method3518(1, local34);
					}
					if (this.aByteArray56[local34] == 0) {
						local21 = false;
					} else {
						local28.method5684();
					}
				}
				while (this.anInt4489 < this.masterIndex.groupVersions.length) {
					if (this.masterIndex.groupVersions[this.anInt4489] == 0) {
						this.anInt4489++;
					} else {
						if (this.localRequester.requestCount >= 250) {
							local21 = false;
							break;
						}
						if (this.aByteArray56[this.anInt4489] == 0) {
							this.method3518(1, this.anInt4489);
						}
						if (this.aByteArray56[this.anInt4489] == 0) {
							local127 = new Country();
							local127.aLong224 = this.anInt4489;
							local21 = false;
							this.generalRequests.method4137(local127);
						}
						this.anInt4489++;
					}
				}
				if (local21) {
					this.anInt4489 = 0;
					this.aBoolean412 = false;
				}
			} else if (this.aBoolean413) {
				local21 = true;
				for (local28 = this.generalRequests.method4140(); local28 != null; local28 = this.generalRequests.method4144()) {
					local34 = (int) local28.aLong224;
					if (this.aByteArray56[local34] != 1) {
						this.method3518(2, local34);
					}
					if (this.aByteArray56[local34] == 1) {
						local28.method5684();
					} else {
						local21 = false;
					}
				}
				while (this.anInt4489 < this.masterIndex.groupVersions.length) {
					if (this.masterIndex.groupVersions[this.anInt4489] == 0) {
						this.anInt4489++;
					} else {
						if (this.standardRequester.isPrefetchRequestQueueFull()) {
							local21 = false;
							break;
						}
						if (this.aByteArray56[this.anInt4489] != 1) {
							this.method3518(2, this.anInt4489);
						}
						if (this.aByteArray56[this.anInt4489] != 1) {
							local127 = new Country();
							local127.aLong224 = this.anInt4489;
							local21 = false;
							this.generalRequests.method4137(local127);
						}
						this.anInt4489++;
					}
				}
				if (local21) {
					this.anInt4489 = 0;
					this.aBoolean413 = false;
				}
			} else {
				this.generalRequests = null;
			}
		}
		if (!this.aBoolean414 || this.cleanupCycle > Class168.method3962()) {
			return;
		}
		for (@Pc(325) Js5WorkerRequestMessage local325 = (Js5WorkerRequestMessage) this.aClass102_20.method2705(); local325 != null; local325 = (Js5WorkerRequestMessage) this.aClass102_20.method2704()) {
			if (!local325.waiting) {
				if (local325.isCompleted) {
					if (!local325.highPriority) {
						throw new RuntimeException();
					}
					local325.method5684();
				} else {
					local325.isCompleted = true;
				}
			}
		}
		this.cleanupCycle = Class168.method3962() + 1000L;
	}

	@OriginalMember(owner = "client!lv", name = "a", descriptor = "(Z)I")
	public int method3521() {
		if (this.masterIndex == null) {
			return 0;
		} else if (this.aBoolean412) {
			@Pc(25) Country local25 = this.generalRequests.method4140();
			return local25 == null ? 0 : (int) local25.aLong224;
		} else {
			return this.masterIndex.size;
		}
	}

	@OriginalMember(owner = "client!lv", name = "a", descriptor = "(IB)I")
	@Override
	public int completePercentage(@OriginalArg(0) int arg0) {
		@Pc(11) Js5WorkerRequestMessage local11 = (Js5WorkerRequestMessage) this.aClass102_20.method2700((long) arg0);
		return local11 == null ? 0 : local11.completePercentage();
	}

	@OriginalMember(owner = "client!lv", name = "c", descriptor = "(B)V")
	public void method3522() {
		if (this.generalRequests == null || this.index() == null) {
			return;
		}
		for (@Pc(23) Country local23 = this.prefetchEntries.method4140(); local23 != null; local23 = this.prefetchEntries.method4144()) {
			@Pc(29) int local29 = (int) local23.aLong224;
			if (local29 < 0 || local29 >= this.masterIndex.capacity || this.masterIndex.groupVersions[local29] == 0) {
				local23.method5684();
			} else {
				if (this.aByteArray56[local29] == 0) {
					this.method3518(1, local29);
				}
				if (this.aByteArray56[local29] == -1) {
					this.method3518(2, local29);
				}
				if (this.aByteArray56[local29] == 1) {
					local23.method5684();
				}
			}
		}
	}

	@OriginalMember(owner = "client!lv", name = "f", descriptor = "(I)I")
	public int method3525() {
		if (this.index() == null) {
			return this.masterIndexRequest == null ? 0 : this.masterIndexRequest.completePercentage();
		} else {
			return 100;
		}
	}

	@OriginalMember(owner = "client!lv", name = "a", descriptor = "(II)V")
	@Override
	public void requestGroup(@OriginalArg(0) int arg0) {
		if (this.cacheDataFile == null) {
			return;
		}
		for (@Pc(14) Country local14 = this.prefetchEntries.method4140(); local14 != null; local14 = this.prefetchEntries.method4144()) {
			if (local14.aLong224 == (long) arg0) {
				return;
			}
		}
		@Pc(43) Country local43 = new Country();
		local43.aLong224 = arg0;
		this.prefetchEntries.method4137(local43);
	}

	@OriginalMember(owner = "client!lv", name = "d", descriptor = "(B)V")
	public void method3526() {
		if (this.cacheDataFile != null) {
			this.aBoolean413 = true;
			if (this.generalRequests == null) {
				this.generalRequests = new Class183();
			}
		}
	}

	@OriginalMember(owner = "client!lv", name = "g", descriptor = "(I)I")
	public int method3527() {
		return this.anInt4482;
	}

	@OriginalMember(owner = "client!lv", name = "a", descriptor = "(B)Lclient!rt;")
	@Override
	public Js5MasterIndex index() {
		if (this.masterIndex != null) {
			return this.masterIndex;
		}
		if (this.masterIndexRequest == null) {
			if (this.standardRequester.isUrgentRequestQueueFull()) {
				return null;
			}
			this.masterIndexRequest = this.standardRequester.method5465(255, (byte) 0, true, this.indexId);
		}
		if (this.masterIndexRequest.waiting) {
			return null;
		}
		@Pc(43) byte[] local43 = this.masterIndexRequest.getData();
		if (this.masterIndexRequest instanceof Class4_Sub1_Sub6_Sub2) {
			try {
				if (local43 == null) {
					throw new RuntimeException();
				}
				this.masterIndex = new Js5MasterIndex(local43, this.tableCrc);
				if (this.masterIndex.revision != this.tableRevision) {
					throw new RuntimeException();
				}
			} catch (@Pc(133) RuntimeException local133) {
				this.masterIndex = null;
				if (this.standardRequester.isUrgentRequestQueueFull()) {
					this.masterIndexRequest = null;
				} else {
					this.masterIndexRequest = this.standardRequester.method5465(255, (byte) 0, true, this.indexId);
				}
				return null;
			}
		} else {
			try {
				if (local43 == null) {
					throw new RuntimeException();
				}
				this.masterIndex = new Js5MasterIndex(local43, this.tableCrc);
			} catch (@Pc(63) RuntimeException local63) {
				this.standardRequester.rekey();
				this.masterIndex = null;
				if (this.standardRequester.isUrgentRequestQueueFull()) {
					this.masterIndexRequest = null;
				} else {
					this.masterIndexRequest = this.standardRequester.method5465(255, (byte) 0, true, this.indexId);
				}
				return null;
			}
			if (this.masterIndexFile != null) {
				this.localRequester.method5435(this.indexId, local43, this.masterIndexFile);
			}
		}
		this.masterIndexRequest = null;
		if (this.cacheDataFile != null) {
			this.aByteArray56 = new byte[this.masterIndex.capacity];
			this.anInt4482 = 0;
		}
		return this.masterIndex;
	}

@OriginalMember(owner = "client!lv", name = "E", descriptor = "I")
	public static int anInt4487;
@OriginalMember(owner = "client!lv", name = "r", descriptor = "[I")
	public static final int[] anIntArray299 = new int[1];
@OriginalMember(owner = "client!lv", name = "c", descriptor = "(I)V")
	public static void method3519() {
		if (GlCubeTexture.anInt3253 < 0) {
			return;
		}
		@Pc(16) long local16 = Class168.method3962();
		GlCubeTexture.anInt3253 = (int) ((long) GlCubeTexture.anInt3253 + NPCType.aLong219 - local16);
		if (GlCubeTexture.anInt3253 <= 0) {
			Country.aFloat228 = StillWaterEffect.aClass155_2.aFloat151;
			Js5MasterIndex.anInt6110 = StillWaterEffect.aClass155_2.anInt4774;
			Class7.anInt116 = StillWaterEffect.aClass155_2.anInt4777;
			MemoryPool.aClass99_4 = StillWaterEffect.aClass155_2.aClass99_3;
			Class95_Sub3_Sub1.aFloat194 = StillWaterEffect.aClass155_2.aFloat146;
			Class208.aFloat187 = StillWaterEffect.aClass155_2.aFloat150;
			Static231.aFloat138 = StillWaterEffect.aClass155_2.aFloat147;
			Static398.anInt6606 = StillWaterEffect.aClass155_2.anInt4773;
			GlCubeTexture.anInt3253 = -1;
			TextureOpDiagonalWaves.aFloat131 = StillWaterEffect.aClass155_2.aFloat149;
			GlParticleRenderer.aFloat31 = StillWaterEffect.aClass155_2.aFloat148;
		} else {
			@Pc(67) int local67 = (GlCubeTexture.anInt3253 << 8) / ParticleBinaryTree.anInt6542;
			@Pc(72) int local72 = 255 - local67;
			@Pc(77) float local77 = (float) local67 / 255.0F;
			Static398.anInt6606 = (local72 * (StillWaterEffect.aClass155_2.anInt4773 & 0xFF00) + local67 * (OutputStream_Sub1.anInt2675 & 0xFF00) & 0xFF0000) + ((StillWaterEffect.aClass155_2.anInt4773 & 0xFF00FF) * local72 + (OutputStream_Sub1.anInt2675 & 0xFF00FF) * local67 & 0xFF00FF00) >>> 8;
			@Pc(114) float local114 = 1.0F - local77;
			Class208.aFloat187 = Class4_Sub1_Sub9_Sub2.aFloat103 + (StillWaterEffect.aClass155_2.aFloat150 - Class4_Sub1_Sub9_Sub2.aFloat103) * local114;
			Class7.anInt116 = local67 * PlayerEntity.anInt4343 + local72 * StillWaterEffect.aClass155_2.anInt4777 >> 8;
			Static231.aFloat138 = GameCanvas.aFloat190 + (StillWaterEffect.aClass155_2.aFloat147 - GameCanvas.aFloat190) * local114;
			Js5MasterIndex.anInt6110 = ((EmissiveTriangle.anInt715 & 0xFF00) * local67 + (StillWaterEffect.aClass155_2.anInt4774 & 0xFF00) * local72 & 0xFF0000) + ((EmissiveTriangle.anInt715 & 0xFF00FF) * local67 + ((StillWaterEffect.aClass155_2.anInt4774 & 0xFF00FF) * local72) & 0xFF00FF00) >>> 8;
			TextureOpDiagonalWaves.aFloat131 = local114 * (StillWaterEffect.aClass155_2.aFloat149 - Static105.aFloat78) + Static105.aFloat78;
			Class95_Sub3_Sub1.aFloat194 = Class50_Sub1.aFloat114 + (StillWaterEffect.aClass155_2.aFloat146 - Class50_Sub1.aFloat114) * local114;
			Country.aFloat228 = ModelNormalsGL.aFloat136 + local114 * (StillWaterEffect.aClass155_2.aFloat151 - ModelNormalsGL.aFloat136);
			GlParticleRenderer.aFloat31 = (StillWaterEffect.aClass155_2.aFloat148 - MECTypeList.aFloat169) * local114 + MECTypeList.aFloat169;
			if (GlGround.aClass99_6 != StillWaterEffect.aClass155_2.aClass99_3) {
				MemoryPool.aClass99_4 = Static57.aClass19_7.drawQuad(GlGround.aClass99_6, StillWaterEffect.aClass155_2.aClass99_3, local114, MemoryPool.aClass99_4);
			}
		}
		NPCType.aLong219 = local16;
	}
@OriginalMember(owner = "client!lv", name = "a", descriptor = "(Lclient!wn;[[BI)V")
	public static void method3523(@OriginalArg(0) MapRegion arg0, @OriginalArg(1) byte[][] arg1) {
		@Pc(12) int[] local12 = new int[] { -1, 0, 0, 0, 0 };
		@Pc(15) int local15 = arg1.length;
		@Pc(36) int local36;
		for (@Pc(17) int local17 = 0; local17 < local15; local17++) {
			@Pc(23) byte[] local23 = arg1[local17];
			if (local23 != null) {
				@Pc(30) Packet local30 = new Packet(local23);
				local36 = Static308.anIntArray372[local17] >> 8;
				@Pc(42) int local42 = Static308.anIntArray372[local17] & 0xFF;
				@Pc(49) int local49 = local36 * 64 - Class50_Sub1.anInt3453;
				@Pc(55) int local55 = local42 * 64 - OpenGLTextureCache.anInt1771;
				CubeMapSpecularEffect.method2701();
				arg0.method5634(Class50_Sub1.anInt3453, OpenGLTextureCache.anInt1771, local30, local49, PlayerVarDomain.aClass213Array1, local55);
				arg0.method5644(local12, local55, MonoStereoData.aClass19_16, local49, local30);
				if (!arg0.underwater && local36 == IDKTypeList.anInt3265 / 8 && local42 == TileMessage.anInt7373 / 8 && local12[0] != -1) {
					GlMemoryPool.aClass242_1 = StringNode.aClass217_1.method4934(local12[1], local12[0], local12[2], local12[3], CacheableNodeByteArray.aClass226_1);
					TextureOpIrregularBricks.anInt2023 = local12[4];
				}
			}
		}
		for (@Pc(136) int local136 = 0; local136 < local15; local136++) {
			@Pc(149) int local149 = (Static308.anIntArray372[local136] >> 8) * 64 - Class50_Sub1.anInt3453;
			local36 = (Static308.anIntArray372[local136] & 0xFF) * 64 - OpenGLTextureCache.anInt1771;
			@Pc(164) byte[] local164 = arg1[local136];
			if (local164 == null && TileMessage.anInt7373 < 800) {
				CubeMapSpecularEffect.method2701();
				arg0.method5630(local149, local36);
			}
		}
	}
}
