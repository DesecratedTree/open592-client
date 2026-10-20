package com.jagex.game.runetek5.cache.node.lru;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.cache.node.lru.*;
import com.jagex.game.runetek5.config.mec.MECTypeList;
@OriginalClass("client!gm")
public final class GlShadow extends Shadow {

	@OriginalMember(owner = "client!gm", name = "H", descriptor = "I")
	public int anInt2722;

	@OriginalMember(owner = "client!gm", name = "K", descriptor = "I")
	public int anInt2725;

	@OriginalMember(owner = "client!gm", name = "M", descriptor = "I")
	public int anInt2727;

	@OriginalMember(owner = "client!gm", name = "O", descriptor = "I")
	public int sizeX;

	@OriginalMember(owner = "client!gm", name = "N", descriptor = "[B")
	public final byte[] shade;

	@OriginalMember(owner = "client!gm", name = "<init>", descriptor = "(Lclient!fd;II)V")
	public GlShadow(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		this.shade = new byte[arg2 * arg1];
	}

	@OriginalMember(owner = "client!gm", name = "a", descriptor = "(IIIII)V")
	public void setDimension(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		this.anInt2722 = arg3 - arg0;
		this.anInt2725 = arg0;
		this.anInt2727 = arg1;
		this.sizeX = arg2 - arg1;
	}

	@OriginalMember(owner = "client!gm", name = "a", descriptor = "(III)Z")
	public boolean fitsDimension(@OriginalArg(1) int arg0, @OriginalArg(2) int sizeZ) {
		return this.shade.length >= sizeZ * arg0;
	}

	@OriginalMember(owner = "client!gm", name = "a", descriptor = "(IIIZIII)V")
	public void fillTriangle(@OriginalArg(0) int xC, @OriginalArg(1) int xA, @OriginalArg(2) int zA, @OriginalArg(4) int arg3, @OriginalArg(5) int xB, @OriginalArg(6) int zC) {
		@Pc(7) int local7 = 0;
		if (zA != xA) {
			local7 = (arg3 - xC << 16) / (xA - zA);
		}
		@Pc(23) int local23 = 0;
		if (xA != xB) {
			local23 = (zC - arg3 << 16) / (xB - xA);
		}
		@Pc(43) int local43 = 0;
		if (xB != zA) {
			local43 = (xC - zC << 16) / (zA - xB);
		}
		if (zA <= xA && xB >= zA) {
			if (xA >= xB) {
				arg3 = xC <<= 0x10;
				zC <<= 0x10;
				if (zA < 0) {
					arg3 -= local43 * zA;
					xC -= local7 * zA;
					zA = 0;
				}
				if (xB < 0) {
					zC -= xB * local23;
					xB = 0;
				}
				if (zA != xB && local7 > local43 || xB == zA && local23 > local7) {
					xA -= xB;
					xB -= zA;
					zA = this.sizeX * zA;
					while (true) {
						xB--;
						if (xB < 0) {
							while (true) {
								xA--;
								if (xA < 0) {
									return;
								}
								MECTypeList.method4125(this.shade, xC >> 16, zC >> 16, zA);
								zC += local23;
								xC += local7;
								zA += this.sizeX;
							}
						}
						MECTypeList.method4125(this.shade, xC >> 16, arg3 >> 16, zA);
						arg3 += local43;
						zA += this.sizeX;
						xC += local7;
					}
				} else {
					xA -= xB;
					xB -= zA;
					zA = this.sizeX * zA;
					while (true) {
						xB--;
						if (xB < 0) {
							while (true) {
								xA--;
								if (xA < 0) {
									return;
								}
								MECTypeList.method4125(this.shade, zC >> 16, xC >> 16, zA);
								zC += local23;
								xC += local7;
								zA += this.sizeX;
							}
						}
						MECTypeList.method4125(this.shade, arg3 >> 16, xC >> 16, zA);
						arg3 += local43;
						xC += local7;
						zA += this.sizeX;
					}
				}
			} else {
				zC = xC <<= 0x10;
				if (zA < 0) {
					xC -= local7 * zA;
					zC -= local43 * zA;
					zA = 0;
				}
				arg3 <<= 0x10;
				if (xA < 0) {
					arg3 -= xA * local23;
					xA = 0;
				}
				if ((zA == xA || local7 <= local43) && (xA != zA || local43 <= local23)) {
					xB -= xA;
					xA -= zA;
					zA = this.sizeX * zA;
					while (true) {
						xA--;
						if (xA < 0) {
							while (true) {
								xB--;
								if (xB < 0) {
									return;
								}
								MECTypeList.method4125(this.shade, zC >> 16, arg3 >> 16, zA);
								zA += this.sizeX;
								zC += local43;
								arg3 += local23;
							}
						}
						MECTypeList.method4125(this.shade, zC >> 16, xC >> 16, zA);
						xC += local7;
						zC += local43;
						zA += this.sizeX;
					}
				} else {
					xB -= xA;
					xA -= zA;
					zA *= this.sizeX;
					while (true) {
						xA--;
						if (xA < 0) {
							while (true) {
								xB--;
								if (xB < 0) {
									return;
								}
								MECTypeList.method4125(this.shade, arg3 >> 16, zC >> 16, zA);
								zC += local43;
								arg3 += local23;
								zA += this.sizeX;
							}
						}
						MECTypeList.method4125(this.shade, xC >> 16, zC >> 16, zA);
						zA += this.sizeX;
						zC += local43;
						xC += local7;
					}
				}
			}
		} else if (xA > xB) {
			if (xA > zA) {
				arg3 = zC <<= 0x10;
				xC <<= 0x10;
				if (xB < 0) {
					zC -= local43 * xB;
					arg3 -= xB * local23;
					xB = 0;
				}
				if (zA < 0) {
					xC -= local7 * zA;
					zA = 0;
				}
				if (local23 >= local43) {
					xA -= zA;
					zA -= xB;
					xB = this.sizeX * xB;
					while (true) {
						zA--;
						if (zA < 0) {
							while (true) {
								xA--;
								if (xA < 0) {
									return;
								}
								MECTypeList.method4125(this.shade, arg3 >> 16, xC >> 16, xB);
								xC += local7;
								xB += this.sizeX;
								arg3 += local23;
							}
						}
						MECTypeList.method4125(this.shade, arg3 >> 16, zC >> 16, xB);
						xB += this.sizeX;
						arg3 += local23;
						zC += local43;
					}
				} else {
					xA -= zA;
					zA -= xB;
					xB = this.sizeX * xB;
					while (true) {
						zA--;
						if (zA < 0) {
							while (true) {
								xA--;
								if (xA < 0) {
									return;
								}
								MECTypeList.method4125(this.shade, xC >> 16, arg3 >> 16, xB);
								xB += this.sizeX;
								arg3 += local23;
								xC += local7;
							}
						}
						MECTypeList.method4125(this.shade, zC >> 16, arg3 >> 16, xB);
						xB += this.sizeX;
						arg3 += local23;
						zC += local43;
					}
				}
			} else {
				xC = zC <<= 0x10;
				if (xB < 0) {
					zC -= xB * local43;
					xC -= local23 * xB;
					xB = 0;
				}
				arg3 <<= 0x10;
				if (xA < 0) {
					arg3 -= xA * local7;
					xA = 0;
				}
				if (local23 >= local43) {
					zA -= xA;
					xA -= xB;
					xB = this.sizeX * xB;
					while (true) {
						xA--;
						if (xA < 0) {
							while (true) {
								zA--;
								if (zA < 0) {
									return;
								}
								MECTypeList.method4125(this.shade, arg3 >> 16, zC >> 16, xB);
								zC += local43;
								arg3 += local7;
								xB += this.sizeX;
							}
						}
						MECTypeList.method4125(this.shade, xC >> 16, zC >> 16, xB);
						zC += local43;
						xC += local23;
						xB += this.sizeX;
					}
				} else {
					zA -= xA;
					xA -= xB;
					xB = this.sizeX * xB;
					while (true) {
						xA--;
						if (xA < 0) {
							while (true) {
								zA--;
								if (zA < 0) {
									return;
								}
								MECTypeList.method4125(this.shade, zC >> 16, arg3 >> 16, xB);
								xB += this.sizeX;
								zC += local43;
								arg3 += local7;
							}
						}
						MECTypeList.method4125(this.shade, zC >> 16, xC >> 16, xB);
						zC += local43;
						xB += this.sizeX;
						xC += local23;
					}
				}
			}
		} else if (zA <= xB) {
			zC = arg3 <<= 0x10;
			xC <<= 0x10;
			if (xA < 0) {
				zC -= xA * local7;
				arg3 -= local23 * xA;
				xA = 0;
			}
			if (zA < 0) {
				xC -= zA * local43;
				zA = 0;
			}
			if (local7 < local23) {
				xB -= zA;
				zA -= xA;
				xA *= this.sizeX;
				while (true) {
					zA--;
					if (zA < 0) {
						while (true) {
							xB--;
							if (xB < 0) {
								return;
							}
							MECTypeList.method4125(this.shade, arg3 >> 16, xC >> 16, xA);
							xC += local43;
							arg3 += local23;
							xA += this.sizeX;
						}
					}
					MECTypeList.method4125(this.shade, arg3 >> 16, zC >> 16, xA);
					xA += this.sizeX;
					arg3 += local23;
					zC += local7;
				}
			} else {
				xB -= zA;
				zA -= xA;
				xA = this.sizeX * xA;
				while (true) {
					zA--;
					if (zA < 0) {
						while (true) {
							xB--;
							if (xB < 0) {
								return;
							}
							MECTypeList.method4125(this.shade, xC >> 16, arg3 >> 16, xA);
							xA += this.sizeX;
							xC += local43;
							arg3 += local23;
						}
					}
					MECTypeList.method4125(this.shade, zC >> 16, arg3 >> 16, xA);
					xA += this.sizeX;
					zC += local7;
					arg3 += local23;
				}
			}
		} else {
			xC = arg3 <<= 0x10;
			if (xA < 0) {
				arg3 -= xA * local23;
				xC -= xA * local7;
				xA = 0;
			}
			zC <<= 0x10;
			if (xB < 0) {
				zC -= xB * local43;
				xB = 0;
			}
			if (xA != xB && local7 < local23 || xA == xB && local43 < local7) {
				zA -= xB;
				xB -= xA;
				xA = this.sizeX * xA;
				while (true) {
					xB--;
					if (xB < 0) {
						while (true) {
							zA--;
							if (zA < 0) {
								return;
							}
							MECTypeList.method4125(this.shade, zC >> 16, xC >> 16, xA);
							zC += local43;
							xA += this.sizeX;
							xC += local7;
						}
					}
					MECTypeList.method4125(this.shade, arg3 >> 16, xC >> 16, xA);
					arg3 += local23;
					xC += local7;
					xA += this.sizeX;
				}
			} else {
				zA -= xB;
				xB -= xA;
				xA *= this.sizeX;
				while (true) {
					xB--;
					if (xB < 0) {
						while (true) {
							zA--;
							if (zA < 0) {
								return;
							}
							MECTypeList.method4125(this.shade, xC >> 16, zC >> 16, xA);
							xA += this.sizeX;
							zC += local43;
							xC += local7;
						}
					}
					MECTypeList.method4125(this.shade, xC >> 16, arg3 >> 16, xA);
					xC += local7;
					xA += this.sizeX;
					arg3 += local23;
				}
			}
		}
	}

	@OriginalMember(owner = "client!gm", name = "d", descriptor = "(I)V")
	public void reset() {
		@Pc(7) int local7 = -1;
		@Pc(13) int local13 = this.shade.length - 8;
		while (local13 > local7) {
			local7++;
			this.shade[local7] = 0;
			local7++;
			this.shade[local7] = 0;
			local7++;
			this.shade[local7] = 0;
			local7++;
			this.shade[local7] = 0;
			local7++;
			this.shade[local7] = 0;
			local7++;
			this.shade[local7] = 0;
			local7++;
			this.shade[local7] = 0;
			local7++;
			this.shade[local7] = 0;
		}
		while (local7 < this.shade.length - 1) {
			local7++;
			this.shade[local7] = 0;
		}
	}

@OriginalMember(owner = "client!gm", name = "F", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_52 = new Class83("Loaded config", "Konfig geladen.", "Fichiers config chargés", "Config carregada");
@OriginalMember(owner = "client!gm", name = "Q", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_34 = new Class215(65, 3);
@OriginalMember(owner = "client!gm", name = "a", descriptor = "(BLjava/lang/String;)I")
	public static int method2302(@OriginalArg(1) String arg0) {
		@Pc(16) int local16 = arg0.length();
		@Pc(18) int local18 = 0;
		for (@Pc(20) int local20 = 0; local20 < local16; local20++) {
			local18 = arg0.charAt(local20) + (local18 << 5) - local18;
		}
		return local18;
	}
}
