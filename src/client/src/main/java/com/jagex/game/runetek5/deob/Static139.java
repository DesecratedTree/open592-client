package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.core.compress.BzipDecompressor;

import com.jagex.game.runetek5.entity.DynamicWall;
public final class Static139 {

	@OriginalMember(owner = "client!h", name = "a", descriptor = "Lclient!cu;")
	private static final BzipDecompressor INSTANCE = new BzipDecompressor();

	@OriginalMember(owner = "client!h", name = "a", descriptor = "(Lclient!cu;)V")
	private static void decompress(@OriginalArg(0) BzipDecompressor decompressor) {
		decompressor.blockSize100k = 1;
		if (DynamicWall.anIntArray501 == null) {
			DynamicWall.anIntArray501 = new int[decompressor.blockSize100k * 100000];
		}
		@Pc(56) boolean local56 = true;
		while (true) {
			while (local56) {
				@Pc(60) byte local60 = readByte(decompressor);
				if (local60 == 23) {
					return;
				}
				local60 = readByte(decompressor);
				local60 = readByte(decompressor);
				local60 = readByte(decompressor);
				local60 = readByte(decompressor);
				local60 = readByte(decompressor);
				local60 = readByte(decompressor);
				local60 = readByte(decompressor);
				local60 = readByte(decompressor);
				local60 = readByte(decompressor);
				local60 = readBit(decompressor);
				decompressor.origPtr = 0;
				local60 = readByte(decompressor);
				decompressor.origPtr = decompressor.origPtr << 8 | local60 & 0xFF;
				local60 = readByte(decompressor);
				decompressor.origPtr = decompressor.origPtr << 8 | local60 & 0xFF;
				local60 = readByte(decompressor);
				decompressor.origPtr = decompressor.origPtr << 8 | local60 & 0xFF;
				@Pc(138) int local138;
				for (local138 = 0; local138 < 16; local138++) {
					local60 = readBit(decompressor);
					if (local60 == 1) {
						decompressor.inUse16[local138] = true;
					} else {
						decompressor.inUse16[local138] = false;
					}
				}
				for (local138 = 0; local138 < 256; local138++) {
					decompressor.inUse[local138] = false;
				}
				@Pc(182) int local182;
				for (local138 = 0; local138 < 16; local138++) {
					if (decompressor.inUse16[local138]) {
						for (local182 = 0; local182 < 16; local182++) {
							local60 = readBit(decompressor);
							if (local60 == 1) {
								decompressor.inUse[local138 * 16 + local182] = true;
							}
						}
					}
				}
				buildSymbolMap(decompressor);
				@Pc(213) int local213 = decompressor.nInUse + 2;
				@Pc(217) int local217 = readBits(3, decompressor);
				@Pc(221) int local221 = readBits(15, decompressor);
				for (local138 = 0; local138 < local221; local138++) {
					local182 = 0;
					while (true) {
						local60 = readBit(decompressor);
						if (local60 == 0) {
							decompressor.selectorMtf[local138] = (byte) local182;
							break;
						}
						local182++;
					}
				}
				@Pc(246) byte[] local246 = new byte[6];
				@Pc(248) byte local248 = 0;
				while (local248 < local217) {
					local246[local248] = local248++;
				}
				for (local138 = 0; local138 < local221; local138++) {
					local248 = decompressor.selectorMtf[local138];
					@Pc(273) byte local273 = local246[local248];
					while (local248 > 0) {
						local246[local248] = local246[local248 - 1];
						local248--;
					}
					local246[0] = local273;
					decompressor.selector[local138] = local273;
				}
				@Pc(304) int local304;
				for (local304 = 0; local304 < local217; local304++) {
					@Pc(309) int local309 = readBits(5, decompressor);
					for (local138 = 0; local138 < local213; local138++) {
						while (true) {
							local60 = readBit(decompressor);
							if (local60 == 0) {
								decompressor.len[local304][local138] = (byte) local309;
								break;
							}
							local60 = readBit(decompressor);
							if (local60 == 0) {
								local309++;
							} else {
								local309--;
							}
						}
					}
				}
				for (local304 = 0; local304 < local217; local304++) {
					@Pc(347) byte local347 = 32;
					@Pc(349) byte local349 = 0;
					for (local138 = 0; local138 < local213; local138++) {
						if (decompressor.len[local304][local138] > local349) {
							local349 = decompressor.len[local304][local138];
						}
						if (decompressor.len[local304][local138] < local347) {
							local347 = decompressor.len[local304][local138];
						}
					}
					buildHuffmanTable(decompressor.limit[local304], decompressor.base[local304], decompressor.perm[local304], decompressor.len[local304], local347, local349, local213);
					decompressor.minLens[local304] = local347;
				}
				@Pc(420) int local420 = decompressor.nInUse + 1;
				@Pc(422) byte local422 = -1;
				for (local138 = 0; local138 <= 255; local138++) {
					decompressor.unzftab[local138] = 0;
				}
				@Pc(438) int local438 = 4095;
				for (@Pc(440) int local440 = 15; local440 >= 0; local440--) {
					for (@Pc(443) int local443 = 15; local443 >= 0; local443--) {
						decompressor.mtfa[local438] = (byte) (local440 * 16 + local443);
						local438--;
					}
					decompressor.mtfbase[local440] = local438 + 1;
				}
				@Pc(470) int local470 = 0;
				@Pc(473) int local473 = local422 + 1;
				@Pc(475) byte local475 = 50;
				@Pc(480) byte local480 = decompressor.selector[0];
				@Pc(485) int local485 = decompressor.minLens[local480];
				@Pc(490) int[] local490 = decompressor.limit[local480];
				@Pc(495) int[] local495 = decompressor.perm[local480];
				@Pc(500) int[] local500 = decompressor.base[local480];
				@Pc(501) int local501 = local475 - 1;
				@Pc(503) int local503 = local485;
				@Pc(507) int local507;
				@Pc(516) byte local516;
				for (local507 = readBits(local485, decompressor); local507 > local490[local503]; local507 = local507 << 1 | local516) {
					local503++;
					local516 = readBit(decompressor);
				}
				@Pc(531) int local531 = local495[local507 - local500[local503]];
				while (true) {
					while (local531 != local420) {
						if (local531 == 0 || local531 == 1) {
							@Pc(541) int local541 = -1;
							@Pc(543) int local543 = 1;
							do {
								if (local531 == 0) {
									local541 += local543;
								} else if (local531 == 1) {
									local541 += local543 * 2;
								}
								local543 *= 2;
								if (local501 == 0) {
									local473++;
									local501 = 50;
									local480 = decompressor.selector[local473];
									local485 = decompressor.minLens[local480];
									local490 = decompressor.limit[local480];
									local495 = decompressor.perm[local480];
									local500 = decompressor.base[local480];
								}
								local501--;
								local503 = local485;
								for (local507 = readBits(local485, decompressor); local507 > local490[local503]; local507 = local507 << 1 | local516) {
									local503++;
									local516 = readBit(decompressor);
								}
								local531 = local495[local507 - local500[local503]];
							} while (local531 == 0 || local531 == 1);
							local541++;
							local60 = decompressor.seqToUnseq[decompressor.mtfa[decompressor.mtfbase[0]] & 0xFF];
							decompressor.unzftab[local60 & 0xFF] += local541;
							while (local541 > 0) {
								DynamicWall.anIntArray501[local470] = local60 & 0xFF;
								local470++;
								local541--;
							}
						} else {
							@Pc(669) int local669 = local531 - 1;
							@Pc(677) int local677;
							if (local669 < 16) {
								local677 = decompressor.mtfbase[0];
								local60 = decompressor.mtfa[local677 + local669];
								while (local669 > 3) {
									@Pc(689) int local689 = local677 + local669;
									decompressor.mtfa[local689] = decompressor.mtfa[local689 - 1];
									decompressor.mtfa[local689 - 1] = decompressor.mtfa[local689 - 2];
									decompressor.mtfa[local689 - 2] = decompressor.mtfa[local689 - 3];
									decompressor.mtfa[local689 - 3] = decompressor.mtfa[local689 - 4];
									local669 -= 4;
								}
								while (local669 > 0) {
									decompressor.mtfa[local677 + local669] = decompressor.mtfa[local677 + local669 - 1];
									local669--;
								}
								decompressor.mtfa[local677] = local60;
							} else {
								@Pc(767) int local767 = local669 / 16;
								@Pc(771) int local771 = local669 % 16;
								local677 = decompressor.mtfbase[local767] + local771;
								local60 = decompressor.mtfa[local677];
								while (local677 > decompressor.mtfbase[local767]) {
									decompressor.mtfa[local677] = decompressor.mtfa[local677 - 1];
									local677--;
								}
								@Pc(806) int local806 = decompressor.mtfbase[local767]++;
								while (local767 > 0) {
									local806 = decompressor.mtfbase[local767]--;
									decompressor.mtfa[decompressor.mtfbase[local767]] = decompressor.mtfa[decompressor.mtfbase[local767 - 1] + 16 - 1];
									local767--;
								}
								local806 = decompressor.mtfbase[0]--;
								decompressor.mtfa[decompressor.mtfbase[0]] = local60;
								if (decompressor.mtfbase[0] == 0) {
									@Pc(864) int local864 = 4095;
									for (@Pc(866) int local866 = 15; local866 >= 0; local866--) {
										for (@Pc(869) int local869 = 15; local869 >= 0; local869--) {
											decompressor.mtfa[local864] = decompressor.mtfa[decompressor.mtfbase[local866] + local869];
											local864--;
										}
										decompressor.mtfbase[local866] = local864 + 1;
									}
								}
							}
							decompressor.unzftab[decompressor.seqToUnseq[local60 & 0xFF] & 0xFF]++;
							DynamicWall.anIntArray501[local470] = decompressor.seqToUnseq[local60 & 0xFF] & 0xFF;
							local470++;
							if (local501 == 0) {
								local473++;
								local501 = 50;
								local480 = decompressor.selector[local473];
								local485 = decompressor.minLens[local480];
								local490 = decompressor.limit[local480];
								local495 = decompressor.perm[local480];
								local500 = decompressor.base[local480];
							}
							local501--;
							local503 = local485;
							for (local507 = readBits(local485, decompressor); local507 > local490[local503]; local507 = local507 << 1 | local516) {
								local503++;
								local516 = readBit(decompressor);
							}
							local531 = local495[local507 - local500[local503]];
						}
					}
					decompressor.stateOutLen = 0;
					decompressor.stateOutCh = 0;
					decompressor.cftab[0] = 0;
					for (local138 = 1; local138 <= 256; local138++) {
						decompressor.cftab[local138] = decompressor.unzftab[local138 - 1];
					}
					for (local138 = 1; local138 <= 256; local138++) {
						decompressor.cftab[local138] += decompressor.cftab[local138 - 1];
					}
					for (local138 = 0; local138 < local470; local138++) {
						local60 = (byte) (DynamicWall.anIntArray501[local138] & 0xFF);
						DynamicWall.anIntArray501[decompressor.cftab[local60 & 0xFF]] |= local138 << 8;
						decompressor.cftab[local60 & 0xFF]++;
					}
					decompressor.tPos = DynamicWall.anIntArray501[decompressor.origPtr] >> 8;
					decompressor.nblockused = 0;
					decompressor.tPos = DynamicWall.anIntArray501[decompressor.tPos];
					decompressor.k0 = (byte) (decompressor.tPos & 0xFF);
					decompressor.tPos >>= 0x8;
					decompressor.nblockused++;
					decompressor.saveNblock = local470;
					decodeBlock(decompressor);
					if (decompressor.nblockused == decompressor.saveNblock + 1 && decompressor.stateOutLen == 0) {
						local56 = true;
						break;
					}
					local56 = false;
					break;
				}
			}
			return;
		}
	}

	@OriginalMember(owner = "client!h", name = "a", descriptor = "([BI[BII)I")
	public static int bunzip(@OriginalArg(0) byte[] output, @OriginalArg(1) int outputLength, @OriginalArg(2) byte[] input, @OriginalArg(3) int inLength) {
		@Pc(1) BzipDecompressor local1 = INSTANCE;
		synchronized (INSTANCE) {
			INSTANCE.input = input;
			INSTANCE.strmNextInPtr = 9;
			INSTANCE.output = output;
			INSTANCE.strmNextOutPtr = 0;
			INSTANCE.availableOut = outputLength;
			INSTANCE.bsLive = 0;
			INSTANCE.bsBuff = 0;
			INSTANCE.strmTotalInLo32 = 0;
			INSTANCE.strmTotalOutLo32 = 0;
			decompress(INSTANCE);
			@Pc(37) int local37 = outputLength - INSTANCE.availableOut;
			INSTANCE.input = null;
			INSTANCE.output = null;
			return local37;
		}
	}

	@OriginalMember(owner = "client!h", name = "a", descriptor = "([I[I[I[BIII)V")
	private static void buildHuffmanTable(@OriginalArg(0) int[] limitTable, @OriginalArg(1) int[] baseTable, @OriginalArg(2) int[] permTable, @OriginalArg(3) byte[] lengthTable, @OriginalArg(4) int minLength, @OriginalArg(5) int maxLength, @OriginalArg(6) int arg6) {
		@Pc(1) int local1 = 0;
		@Pc(3) int local3;
		for (local3 = minLength; local3 <= maxLength; local3++) {
			for (@Pc(6) int local6 = 0; local6 < arg6; local6++) {
				if (lengthTable[local6] == local3) {
					permTable[local1] = local6;
					local1++;
				}
			}
		}
		for (local3 = 0; local3 < 23; local3++) {
			baseTable[local3] = 0;
		}
		for (local3 = 0; local3 < arg6; local3++) {
			baseTable[lengthTable[local3] + 1]++;
		}
		for (local3 = 1; local3 < 23; local3++) {
			baseTable[local3] += baseTable[local3 - 1];
		}
		for (local3 = 0; local3 < 23; local3++) {
			limitTable[local3] = 0;
		}
		@Pc(85) int local85 = 0;
		for (local3 = minLength; local3 <= maxLength; local3++) {
			local85 += baseTable[local3 + 1] - baseTable[local3];
			limitTable[local3] = local85 - 1;
			local85 <<= 0x1;
		}
		for (local3 = minLength + 1; local3 <= maxLength; local3++) {
			baseTable[local3] = (limitTable[local3 - 1] + 1 << 1) - baseTable[local3];
		}
	}

	@OriginalMember(owner = "client!h", name = "b", descriptor = "(Lclient!cu;)B")
	private static byte readByte(@OriginalArg(0) BzipDecompressor entry) {
		return (byte) readBits(8, entry);
	}

	@OriginalMember(owner = "client!h", name = "c", descriptor = "(Lclient!cu;)V")
	private static void decodeBlock(@OriginalArg(0) BzipDecompressor entry) {
		@Pc(2) byte local2 = entry.stateOutCh;
		@Pc(5) int local5 = entry.stateOutLen;
		@Pc(8) int local8 = entry.nblockused;
		@Pc(11) int local11 = entry.k0;
		@Pc(13) int[] local13 = DynamicWall.anIntArray501;
		@Pc(16) int local16 = entry.tPos;
		@Pc(19) byte[] local19 = entry.output;
		@Pc(22) int local22 = entry.strmNextOutPtr;
		@Pc(25) int local25 = entry.availableOut;
		@Pc(27) int local27 = local25;
		@Pc(32) int local32 = entry.saveNblock + 1;
		label57: while (true) {
			if (local5 > 0) {
				while (true) {
					if (local25 == 0) {
						break label57;
					}
					if (local5 == 1) {
						if (local25 == 0) {
							local5 = 1;
							break label57;
						}
						local19[local22] = local2;
						local22++;
						local25--;
						break;
					}
					local19[local22] = local2;
					local5--;
					local22++;
					local25--;
				}
			}
			while (local8 != local32) {
				local2 = (byte) local11;
				local16 = local13[local16];
				@Pc(74) byte local74 = (byte) local16;
				local16 >>= 0x8;
				local8++;
				if (local74 == local11) {
					if (local8 != local32) {
						local5 = 2;
						local16 = local13[local16];
						local74 = (byte) local16;
						local16 >>= 0x8;
						local8++;
						if (local8 != local32) {
							if (local74 == local11) {
								local5 = 3;
								local16 = local13[local16];
								local74 = (byte) local16;
								local16 >>= 0x8;
								local8++;
								if (local8 != local32) {
									if (local74 == local11) {
										local16 = local13[local16];
										local74 = (byte) local16;
										local16 >>= 0x8;
										local8++;
										local5 = (local74 & 0xFF) + 4;
										local16 = local13[local16];
										local11 = (byte) local16;
										local16 >>= 0x8;
										local8++;
									} else {
										local11 = local74;
									}
								}
							} else {
								local11 = local74;
							}
						}
						continue label57;
					}
					if (local25 == 0) {
						local5 = 1;
						break label57;
					}
					local19[local22] = local2;
					local22++;
					local25--;
				} else {
					local11 = local74;
					if (local25 == 0) {
						local5 = 1;
						break label57;
					}
					local19[local22] = local2;
					local22++;
					local25--;
				}
			}
			local5 = 0;
			break;
		}
		@Pc(191) int local191 = entry.strmTotalOutLo32;
		entry.strmTotalOutLo32 += local27 - local25;
		entry.stateOutCh = local2;
		entry.stateOutLen = local5;
		entry.nblockused = local8;
		entry.k0 = local11;
		DynamicWall.anIntArray501 = local13;
		entry.tPos = local16;
		entry.output = local19;
		entry.strmNextOutPtr = local22;
		entry.availableOut = local25;
	}

	@OriginalMember(owner = "client!h", name = "d", descriptor = "(Lclient!cu;)V")
	private static void buildSymbolMap(@OriginalArg(0) BzipDecompressor entry) {
		entry.nInUse = 0;
		for (@Pc(4) int local4 = 0; local4 < 256; local4++) {
			if (entry.inUse[local4]) {
				entry.seqToUnseq[entry.nInUse] = (byte) local4;
				entry.nInUse++;
			}
		}
	}

	@OriginalMember(owner = "client!h", name = "e", descriptor = "(Lclient!cu;)B")
	private static byte readBit(@OriginalArg(0) BzipDecompressor entry) {
		return (byte) readBits(1, entry);
	}

	@OriginalMember(owner = "client!h", name = "a", descriptor = "(ILclient!cu;)I")
	private static int readBits(@OriginalArg(0) int numBits, @OriginalArg(1) BzipDecompressor entry) {
		while (entry.bsLive < numBits) {
			entry.bsBuff = entry.bsBuff << 8 | entry.input[entry.strmNextInPtr] & 0xFF;
			entry.bsLive += 8;
			entry.strmNextInPtr++;
			entry.strmTotalInLo32++;
		}
		@Pc(17) int local17 = entry.bsBuff >> entry.bsLive - numBits & (0x1 << numBits) - 1;
		entry.bsLive -= numBits;
		return local17;
	}
}
