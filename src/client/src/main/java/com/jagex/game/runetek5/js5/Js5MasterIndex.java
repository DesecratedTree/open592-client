package com.jagex.game.runetek5.js5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class22;

import com.jagex.game.runetek5.js5.*;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class135;
import com.jagex.game.runetek5.deob.TileMessage;
@OriginalClass("client!rt")
public final class Js5MasterIndex {

	@OriginalMember(owner = "client!rt", name = "a", descriptor = "[I")
	public int[] groupVersions;

	@OriginalMember(owner = "client!rt", name = "c", descriptor = "I")
	public int size;

	@OriginalMember(owner = "client!rt", name = "d", descriptor = "[I")
	public int[] groupChecksums;

	@OriginalMember(owner = "client!rt", name = "e", descriptor = "[I")
	public int[] groupSizes;

	@OriginalMember(owner = "client!rt", name = "h", descriptor = "[[I")
	public int[][] validFileIds;

	@OriginalMember(owner = "client!rt", name = "i", descriptor = "[[I")
	public int[][] fileNameHashes;

	@OriginalMember(owner = "client!rt", name = "j", descriptor = "[I")
	public int[] groupIds;

	@OriginalMember(owner = "client!rt", name = "l", descriptor = "Lclient!ud;")
	public NamedFileReference archiveNames;

	@OriginalMember(owner = "client!rt", name = "m", descriptor = "I")
	public int capacity;

	@OriginalMember(owner = "client!rt", name = "n", descriptor = "I")
	public int revision;

	@OriginalMember(owner = "client!rt", name = "p", descriptor = "[Lclient!ud;")
	public NamedFileReference[] namedFiles;

	@OriginalMember(owner = "client!rt", name = "q", descriptor = "[I")
	public int[] nameHashes;

	@OriginalMember(owner = "client!rt", name = "w", descriptor = "[I")
	public int[] groupCapacities;

	@OriginalMember(owner = "client!rt", name = "k", descriptor = "I")
	public final int headerCrc;

	@OriginalMember(owner = "client!rt", name = "<init>", descriptor = "([BI)V")
	public Js5MasterIndex(@OriginalArg(0) byte[] arg0, @OriginalArg(1) int expectedCrc) {
		this.headerCrc = GameShell.method879(arg0.length, arg0);
		if (this.headerCrc != expectedCrc) {
			throw new RuntimeException();
		}
		this.decode(arg0);
	}

	@OriginalMember(owner = "client!rt", name = "a", descriptor = "([BI)V")
	private void decode(@OriginalArg(0) byte[] arg0) {
		@Pc(12) Packet local12 = new Packet(TileMessage.method5680(arg0));
		@Pc(16) int local16 = local12.method2490();
		if (local16 != 5 && local16 != 6) {
			throw new RuntimeException();
		}
		if (local16 >= 6) {
			this.revision = local12.method2529();
		} else {
			this.revision = 0;
		}
		@Pc(45) int local45 = local12.method2490();
		this.size = local12.method2536();
		@Pc(52) int local52 = 0;
		this.groupIds = new int[this.size];
		@Pc(59) int local59 = -1;
		for (@Pc(61) int local61 = 0; local61 < this.size; local61++) {
			this.groupIds[local61] = local52 += local12.method2536();
			if (local59 < this.groupIds[local61]) {
				local59 = this.groupIds[local61];
			}
		}
		this.capacity = local59 + 1;
		this.groupChecksums = new int[this.capacity];
		this.groupCapacities = new int[this.capacity];
		this.groupSizes = new int[this.capacity];
		this.groupVersions = new int[this.capacity];
		this.validFileIds = new int[this.capacity][];
		@Pc(133) int local133;
		@Pc(151) int local151;
		if (local45 != 0) {
			this.nameHashes = new int[this.capacity];
			for (local133 = 0; local133 < this.capacity; local133++) {
				this.nameHashes[local133] = -1;
			}
			for (local151 = 0; local151 < this.size; local151++) {
				this.nameHashes[this.groupIds[local151]] = local12.method2529();
			}
			this.archiveNames = new NamedFileReference(this.nameHashes);
		}
		for (local133 = 0; local133 < this.size; local133++) {
			this.groupSizes[this.groupIds[local133]] = local12.method2529();
		}
		for (local151 = 0; local151 < this.size; local151++) {
			this.groupChecksums[this.groupIds[local151]] = local12.method2529();
		}
		for (@Pc(223) int local223 = 0; local223 < this.size; local223++) {
			this.groupVersions[this.groupIds[local223]] = local12.method2536();
		}
		@Pc(249) int local249;
		@Pc(256) int local256;
		@Pc(258) int local258;
		@Pc(266) int local266;
		@Pc(283) int local283;
		for (@Pc(242) int local242 = 0; local242 < this.size; local242++) {
			local249 = this.groupIds[local242];
			local52 = 0;
			local256 = this.groupVersions[local249];
			local258 = -1;
			this.validFileIds[local249] = new int[local256];
			for (local266 = 0; local266 < local256; local266++) {
				local283 = this.validFileIds[local249][local266] = local52 += local12.method2536();
				if (local258 < local283) {
					local258 = local283;
				}
			}
			this.groupCapacities[local249] = local258 + 1;
			if (local256 == local258 + 1) {
				this.validFileIds[local249] = null;
			}
		}
		if (local45 == 0) {
			return;
		}
		this.namedFiles = new NamedFileReference[local59 + 1];
		this.fileNameHashes = new int[local59 + 1][];
		for (local249 = 0; local249 < this.size; local249++) {
			local256 = this.groupIds[local249];
			local258 = this.groupVersions[local256];
			this.fileNameHashes[local256] = new int[this.groupCapacities[local256]];
			for (local266 = 0; local266 < this.groupCapacities[local256]; local266++) {
				this.fileNameHashes[local256][local266] = -1;
			}
			for (local283 = 0; local283 < local258; local283++) {
				@Pc(391) int local391;
				if (this.validFileIds[local256] == null) {
					local391 = local283;
				} else {
					local391 = this.validFileIds[local256][local283];
				}
				this.fileNameHashes[local256][local391] = local12.method2529();
			}
			this.namedFiles[local256] = new NamedFileReference(this.fileNameHashes[local256]);
		}
	}

@OriginalMember(owner = "client!rt", name = "f", descriptor = "I")
	public static int anInt6110;
@OriginalMember(owner = "client!rt", name = "t", descriptor = "Lclient!qj;")
	public static SpotAnimationTypeList aClass194_2;
@OriginalMember(owner = "client!rt", name = "b", descriptor = "I")
	public static int anInt6108 = 0;
@OriginalMember(owner = "client!rt", name = "g", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_281 = new Class22(74, -1);
@OriginalMember(owner = "client!rt", name = "u", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_82 = new Class215(71, 2);
@OriginalMember(owner = "client!rt", name = "v", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_282 = new Class22(63, 4);
@OriginalMember(owner = "client!rt", name = "a", descriptor = "(ZI)V")
	public static void method4768(@OriginalArg(1) int arg0) {
		if (Class135.anIntArray288 == null || arg0 > Class135.anIntArray288.length) {
			Class135.anIntArray288 = new int[arg0];
		}
	}
}
