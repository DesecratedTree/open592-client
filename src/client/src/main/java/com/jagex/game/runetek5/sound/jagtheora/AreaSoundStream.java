package com.jagex.game.runetek5.sound.jagtheora;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.impl.VariableRateSoundPacket;

import com.jagex.game.runetek5.cache.node.impl.VariableRateSoundPacket;
import com.jagex.game.runetek5.sound.jagtheora.*;
import com.jagex.game.runetek5.sound.jagtheora.AreaSoundStream;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.GpuFaceNormal;
@OriginalClass("client!qo")
public final class AreaSoundStream extends PcmStream {

	@OriginalMember(owner = "client!qo", name = "p", descriptor = "I")
	public int rightGainStep;

	@OriginalMember(owner = "client!qo", name = "s", descriptor = "I")
	public int gain;

	@OriginalMember(owner = "client!qo", name = "t", descriptor = "I")
	private int loops;

	@OriginalMember(owner = "client!qo", name = "v", descriptor = "I")
	public int leftGainStep;

	@OriginalMember(owner = "client!qo", name = "x", descriptor = "I")
	private int fadeSamplesRemaining;

	@OriginalMember(owner = "client!qo", name = "A", descriptor = "I")
	public int gainStep;

	@OriginalMember(owner = "client!qo", name = "C", descriptor = "I")
	public int leftGain;

	@OriginalMember(owner = "client!qo", name = "D", descriptor = "I")
	public int rightGain;

	@OriginalMember(owner = "client!qo", name = "q", descriptor = "I")
	private final int start;

	@OriginalMember(owner = "client!qo", name = "y", descriptor = "I")
	private final int minBitRate;

	@OriginalMember(owner = "client!qo", name = "u", descriptor = "Z")
	private final boolean enableLoop;

	@OriginalMember(owner = "client!qo", name = "B", descriptor = "I")
	private int rate;

	@OriginalMember(owner = "client!qo", name = "z", descriptor = "I")
	private int targetVolume;

	@OriginalMember(owner = "client!qo", name = "w", descriptor = "I")
	private int panPosition;

	@OriginalMember(owner = "client!qo", name = "r", descriptor = "I")
	public int position;

	@OriginalMember(owner = "client!qo", name = "<init>", descriptor = "(Lclient!js;II)V")
	public AreaSoundStream(@OriginalArg(0) VariableRateSoundPacket variableRateSoundPacket, @OriginalArg(1) int rate, @OriginalArg(2) int volume) {
		super.aClass4_Sub4_5 = variableRateSoundPacket;
		this.start = variableRateSoundPacket.nominalBitRate;
		this.minBitRate = variableRateSoundPacket.minBitRate;
		this.enableLoop = variableRateSoundPacket.pingPong;
		this.rate = rate;
		this.targetVolume = volume;
		this.panPosition = 8192;
		this.position = 0;
		this.recalculateChannelVolumes();
	}

	@OriginalMember(owner = "client!qo", name = "<init>", descriptor = "(Lclient!js;III)V")
	public AreaSoundStream(@OriginalArg(0) VariableRateSoundPacket arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		super.aClass4_Sub4_5 = arg0;
		this.start = arg0.nominalBitRate;
		this.minBitRate = arg0.minBitRate;
		this.enableLoop = arg0.pingPong;
		this.rate = arg1;
		this.targetVolume = arg2;
		this.panPosition = arg3;
		this.position = 0;
		this.recalculateChannelVolumes();
	}

	@OriginalMember(owner = "client!qo", name = "a", descriptor = "([IIIII)I")
	private int fillBackward(@OriginalArg(0) int[] mix, @OriginalArg(1) int arg1, @OriginalArg(2) int limit, @OriginalArg(3) int arg3, @OriginalArg(4) int edgeSample) {
		do {
			if (this.fadeSamplesRemaining <= 0) {
				if (this.rate == -256 && (this.position & 0xFF) == 0) {
					if (GpuFaceNormal.aBoolean649) {
						return AreaSoundStream.mixBackwardStereo(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.leftGain, this.rightGain, arg3, limit, this);
					}
					return AreaSoundStream.mixBackwardMono(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.gain, arg3, limit, this);
				}
				if (GpuFaceNormal.aBoolean649) {
					return AreaSoundStream.mixBackwardStereoResampled(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.leftGain, this.rightGain, arg3, limit, this, this.rate, edgeSample);
				}
				return AreaSoundStream.mixBackwardMonoResampled(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.gain, arg3, limit, this, this.rate, edgeSample);
			}
			@Pc(5) int local5 = arg1 + this.fadeSamplesRemaining;
			if (local5 > arg3) {
				local5 = arg3;
			}
			this.fadeSamplesRemaining += arg1;
			if (this.rate == -256 && (this.position & 0xFF) == 0) {
				if (GpuFaceNormal.aBoolean649) {
					arg1 = AreaSoundStream.mixBackwardStereoRamped(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.leftGain, this.rightGain, this.leftGainStep, this.rightGainStep, local5, limit, this);
				} else {
					arg1 = AreaSoundStream.mixBackwardMonoRamped(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.gain, this.gainStep, local5, limit, this);
				}
			} else if (GpuFaceNormal.aBoolean649) {
				arg1 = AreaSoundStream.mixBackwardStereoResampledRamped(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.leftGain, this.rightGain, this.leftGainStep, this.rightGainStep, local5, limit, this, this.rate, edgeSample);
			} else {
				arg1 = AreaSoundStream.mixBackwardMonoResampledRamped(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.gain, this.gainStep, local5, limit, this, this.rate, edgeSample);
			}
			this.fadeSamplesRemaining -= arg1;
			if (this.fadeSamplesRemaining != 0) {
				return arg1;
			}
		} while (!this.updateFadeState());
		return arg3;
	}

	@OriginalMember(owner = "client!qo", name = "a", descriptor = "(I)V")
	@Override
	public synchronized void skip(@OriginalArg(0) int length) {
		if (this.fadeSamplesRemaining > 0) {
			if (length >= this.fadeSamplesRemaining) {
				if (this.targetVolume == Integer.MIN_VALUE) {
					this.targetVolume = 0;
					this.gain = this.leftGain = this.rightGain = 0;
					this.method5684();
					length = this.fadeSamplesRemaining;
				}
				this.fadeSamplesRemaining = 0;
				this.recalculateChannelVolumes();
			} else {
				this.gain += this.gainStep * length;
				this.leftGain += this.leftGainStep * length;
				this.rightGain += this.rightGainStep * length;
				this.fadeSamplesRemaining -= length;
			}
		}
		@Pc(71) VariableRateSoundPacket local71 = (VariableRateSoundPacket) super.aClass4_Sub4_5;
		@Pc(76) int local76 = this.start << 8;
		@Pc(81) int local81 = this.minBitRate << 8;
		@Pc(87) int local87 = local71.data.length << 8;
		@Pc(91) int local91 = local81 - local76;
		if (local91 <= 0) {
			this.loops = 0;
		}
		if (this.position < 0) {
			if (this.rate <= 0) {
				this.cancelFade();
				this.method5684();
				return;
			}
			this.position = 0;
		}
		if (this.position >= local87) {
			if (this.rate >= 0) {
				this.cancelFade();
				this.method5684();
				return;
			}
			this.position = local87 - 1;
		}
		this.position += this.rate * length;
		if (this.loops >= 0) {
			if (this.loops > 0) {
				if (this.enableLoop) {
					label125: {
						if (this.rate < 0) {
							if (this.position >= local76) {
								return;
							}
							this.position = local76 + local76 - this.position - 1;
							this.rate = -this.rate;
							if (--this.loops == 0) {
								break label125;
							}
						}
						do {
							if (this.position < local81) {
								return;
							}
							this.position = local81 + local81 - this.position - 1;
							this.rate = -this.rate;
							if (--this.loops == 0) {
								break;
							}
							if (this.position >= local76) {
								return;
							}
							this.position = local76 + local76 - this.position - 1;
							this.rate = -this.rate;
						} while (--this.loops != 0);
					}
				} else {
					@Pc(361) int local361;
					if (this.rate < 0) {
						if (this.position >= local76) {
							return;
						}
						local361 = (local81 - this.position - 1) / local91;
						if (local361 < this.loops) {
							this.position += local91 * local361;
							this.loops -= local361;
							return;
						}
						this.position += local91 * this.loops;
						this.loops = 0;
					} else if (this.position >= local81) {
						local361 = (this.position - local76) / local91;
						if (local361 < this.loops) {
							this.position -= local91 * local361;
							this.loops -= local361;
							return;
						}
						this.position -= local91 * this.loops;
						this.loops = 0;
					} else {
						return;
					}
				}
			}
			if (this.rate < 0) {
				if (this.position < 0) {
					this.position = -1;
					this.cancelFade();
					this.method5684();
					return;
				}
			} else if (this.position >= local87) {
				this.position = local87;
				this.cancelFade();
				this.method5684();
			}
		} else if (this.enableLoop) {
			if (this.rate < 0) {
				if (this.position >= local76) {
					return;
				}
				this.position = local76 + local76 - this.position - 1;
				this.rate = -this.rate;
			}
			while (this.position >= local81) {
				this.position = local81 + local81 - this.position - 1;
				this.rate = -this.rate;
				if (this.position >= local76) {
					return;
				}
				this.position = local76 + local76 - this.position - 1;
				this.rate = -this.rate;
			}
		} else if (this.rate < 0) {
			if (this.position < local76) {
				this.position = local81 - (local81 - 1 - this.position) % local91 - 1;
			}
		} else if (this.position >= local81) {
			this.position = local76 + (this.position - local76) % local91;
		}
	}

	@OriginalMember(owner = "client!qo", name = "a", descriptor = "(Z)V")
	public synchronized void playBackward() {
		this.rate = (this.rate ^ this.rate >> 31) + (this.rate >>> 31);
		this.rate = -this.rate;
	}

	@OriginalMember(owner = "client!qo", name = "c", descriptor = "()I")
	@Override
	public int getActiveVoiceCount() {
		return this.targetVolume == 0 && this.fadeSamplesRemaining == 0 ? 0 : 1;
	}

	@OriginalMember(owner = "client!qo", name = "e", descriptor = "()Z")
	private boolean updateFadeState() {
		@Pc(2) int local2 = this.targetVolume;
		@Pc(10) int local10;
		@Pc(8) int local8;
		if (local2 == Integer.MIN_VALUE) {
			local8 = 0;
			local10 = 0;
			local2 = 0;
		} else {
			local10 = AreaSoundStream.calculateSoundAttenuation(local2, this.panPosition);
			local8 = AreaSoundStream.calculateSoundPan(local2, this.panPosition);
		}
		if (this.gain != local2 || this.leftGain != local10 || this.rightGain != local8) {
			if (this.gain < local2) {
				this.gainStep = 1;
				this.fadeSamplesRemaining = local2 - this.gain;
			} else if (this.gain > local2) {
				this.gainStep = -1;
				this.fadeSamplesRemaining = this.gain - local2;
			} else {
				this.gainStep = 0;
			}
			if (this.leftGain < local10) {
				this.leftGainStep = 1;
				if (this.fadeSamplesRemaining == 0 || this.fadeSamplesRemaining > local10 - this.leftGain) {
					this.fadeSamplesRemaining = local10 - this.leftGain;
				}
			} else if (this.leftGain > local10) {
				this.leftGainStep = -1;
				if (this.fadeSamplesRemaining == 0 || this.fadeSamplesRemaining > this.leftGain - local10) {
					this.fadeSamplesRemaining = this.leftGain - local10;
				}
			} else {
				this.leftGainStep = 0;
			}
			if (this.rightGain < local8) {
				this.rightGainStep = 1;
				if (this.fadeSamplesRemaining == 0 || this.fadeSamplesRemaining > local8 - this.rightGain) {
					this.fadeSamplesRemaining = local8 - this.rightGain;
				}
			} else if (this.rightGain > local8) {
				this.rightGainStep = -1;
				if (this.fadeSamplesRemaining == 0 || this.fadeSamplesRemaining > this.rightGain - local8) {
					this.fadeSamplesRemaining = this.rightGain - local8;
				}
			} else {
				this.rightGainStep = 0;
			}
			return false;
		} else if (this.targetVolume == Integer.MIN_VALUE) {
			this.targetVolume = 0;
			this.gain = this.leftGain = this.rightGain = 0;
			this.method5684();
			return true;
		} else {
			this.recalculateChannelVolumes();
			return false;
		}
	}

	@OriginalMember(owner = "client!qo", name = "c", descriptor = "(I)V")
	public synchronized void setNumLoops(@OriginalArg(0) int numLoops) {
		this.loops = numLoops;
	}

	@OriginalMember(owner = "client!qo", name = "a", descriptor = "([III)V")
	@Override
	public synchronized void fill(@OriginalArg(0) int[] mix, @OriginalArg(1) int offset, @OriginalArg(2) int length) {
		if (this.targetVolume == 0 && this.fadeSamplesRemaining == 0) {
			this.skip(length);
			return;
		}
		@Pc(13) VariableRateSoundPacket local13 = (VariableRateSoundPacket) super.aClass4_Sub4_5;
		@Pc(18) int local18 = this.start << 8;
		@Pc(23) int local23 = this.minBitRate << 8;
		@Pc(29) int local29 = local13.data.length << 8;
		@Pc(33) int local33 = local23 - local18;
		if (local33 <= 0) {
			this.loops = 0;
		}
		@Pc(40) int local40 = offset;
		@Pc(44) int local44 = length + offset;
		if (this.position < 0) {
			if (this.rate <= 0) {
				this.cancelFade();
				this.method5684();
				return;
			}
			this.position = 0;
		}
		if (this.position >= local29) {
			if (this.rate >= 0) {
				this.cancelFade();
				this.method5684();
				return;
			}
			this.position = local29 - 1;
		}
		if (this.loops >= 0) {
			if (this.loops > 0) {
				if (this.enableLoop) {
					label130: {
						if (this.rate < 0) {
							local40 = this.fillBackward(mix, offset, local18, local44, local13.data[this.start]);
							if (this.position >= local18) {
								return;
							}
							this.position = local18 + local18 - this.position - 1;
							this.rate = -this.rate;
							if (--this.loops == 0) {
								break label130;
							}
						}
						do {
							local40 = this.fillForward(mix, local40, local23, local44, local13.data[this.minBitRate - 1]);
							if (this.position < local23) {
								return;
							}
							this.position = local23 + local23 - this.position - 1;
							this.rate = -this.rate;
							if (--this.loops == 0) {
								break;
							}
							local40 = this.fillBackward(mix, local40, local18, local44, local13.data[this.start]);
							if (this.position >= local18) {
								return;
							}
							this.position = local18 + local18 - this.position - 1;
							this.rate = -this.rate;
						} while (--this.loops != 0);
					}
				} else {
					@Pc(416) int local416;
					if (this.rate < 0) {
						while (true) {
							local40 = this.fillBackward(mix, local40, local18, local44, local13.data[this.minBitRate - 1]);
							if (this.position >= local18) {
								return;
							}
							local416 = (local23 - this.position - 1) / local33;
							if (local416 >= this.loops) {
								this.position += local33 * this.loops;
								this.loops = 0;
								break;
							}
							this.position += local33 * local416;
							this.loops -= local416;
						}
					} else {
						while (true) {
							local40 = this.fillForward(mix, local40, local23, local44, local13.data[this.start]);
							if (this.position < local23) {
								return;
							}
							local416 = (this.position - local18) / local33;
							if (local416 >= this.loops) {
								this.position -= local33 * this.loops;
								this.loops = 0;
								break;
							}
							this.position -= local33 * local416;
							this.loops -= local416;
						}
					}
				}
			}
			if (this.rate < 0) {
				this.fillBackward(mix, local40, 0, local44, 0);
				if (this.position < 0) {
					this.position = -1;
					this.cancelFade();
					this.method5684();
					return;
				}
			} else {
				this.fillForward(mix, local40, local29, local44, 0);
				if (this.position >= local29) {
					this.position = local29;
					this.cancelFade();
					this.method5684();
				}
			}
		} else if (this.enableLoop) {
			if (this.rate < 0) {
				local40 = this.fillBackward(mix, offset, local18, local44, local13.data[this.start]);
				if (this.position >= local18) {
					return;
				}
				this.position = local18 + local18 - this.position - 1;
				this.rate = -this.rate;
			}
			while (true) {
				local40 = this.fillForward(mix, local40, local23, local44, local13.data[this.minBitRate - 1]);
				if (this.position < local23) {
					return;
				}
				this.position = local23 + local23 - this.position - 1;
				this.rate = -this.rate;
				local40 = this.fillBackward(mix, local40, local18, local44, local13.data[this.start]);
				if (this.position >= local18) {
					return;
				}
				this.position = local18 + local18 - this.position - 1;
				this.rate = -this.rate;
			}
		} else if (this.rate < 0) {
			while (true) {
				local40 = this.fillBackward(mix, local40, local18, local44, local13.data[this.minBitRate - 1]);
				if (this.position >= local18) {
					return;
				}
				this.position = local23 - (local23 - 1 - this.position) % local33 - 1;
			}
		} else {
			while (true) {
				local40 = this.fillForward(mix, local40, local23, local44, local13.data[this.start]);
				if (this.position < local23) {
					return;
				}
				this.position = local18 + (this.position - local18) % local33;
			}
		}
	}

	@OriginalMember(owner = "client!qo", name = "f", descriptor = "()Z")
	public boolean isFinished() {
		return this.position < 0 || this.position >= ((VariableRateSoundPacket) super.aClass4_Sub4_5).data.length << 8;
	}

	@OriginalMember(owner = "client!qo", name = "d", descriptor = "()Lclient!ti;")
	@Override
	public PcmStream method4860() {
		return null;
	}

	@OriginalMember(owner = "client!qo", name = "a", descriptor = "()I")
	@Override
	public int getPriority() {
		@Pc(6) int local6 = this.gain * 3 >> 6;
		local6 = (local6 ^ local6 >> 31) + (local6 >>> 31);
		if (this.loops == 0) {
			local6 -= local6 * this.position / (((VariableRateSoundPacket) super.aClass4_Sub4_5).data.length << 8);
		} else if (this.loops >= 0) {
			local6 -= local6 * this.start / ((VariableRateSoundPacket) super.aClass4_Sub4_5).data.length;
		}
		return local6 > 255 ? 255 : local6;
	}

	@OriginalMember(owner = "client!qo", name = "g", descriptor = "()V")
	private void cancelFade() {
		if (this.fadeSamplesRemaining == 0) {
			return;
		}
		if (this.targetVolume == Integer.MIN_VALUE) {
			this.targetVolume = 0;
		}
		this.fadeSamplesRemaining = 0;
		this.recalculateChannelVolumes();
	}

	@OriginalMember(owner = "client!qo", name = "d", descriptor = "(I)V")
	public synchronized void setPlaybackRate(@OriginalArg(0) int playbackRate) {
		if (this.rate < 0) {
			this.rate = -playbackRate;
		} else {
			this.rate = playbackRate;
		}
	}

	@OriginalMember(owner = "client!qo", name = "h", descriptor = "()I")
	public synchronized int getTargetVolume() {
		return this.targetVolume == Integer.MIN_VALUE ? 0 : this.targetVolume;
	}

	@OriginalMember(owner = "client!qo", name = "i", descriptor = "()V")
	private void recalculateChannelVolumes() {
		this.gain = this.targetVolume;
		this.leftGain = AreaSoundStream.calculateSoundAttenuation(this.targetVolume, this.panPosition);
		this.rightGain = AreaSoundStream.calculateSoundPan(this.targetVolume, this.panPosition);
	}

	@OriginalMember(owner = "client!qo", name = "e", descriptor = "(I)V")
	public synchronized void setPlaybackPosition(@OriginalArg(0) int position) {
		@Pc(7) int local7 = ((VariableRateSoundPacket) super.aClass4_Sub4_5).data.length << 8;
		if (position < -1) {
			position = -1;
		}
		if (position > local7) {
			position = local7;
		}
		this.position = position;
	}

	@OriginalMember(owner = "client!qo", name = "j", descriptor = "()I")
	public synchronized int getPanPosition() {
		return this.panPosition < 0 ? -1 : this.panPosition;
	}

	@OriginalMember(owner = "client!qo", name = "b", descriptor = "(II)V")
	private synchronized void update(@OriginalArg(0) int targetVolume, @OriginalArg(1) int panPosition) {
		this.targetVolume = targetVolume;
		this.panPosition = panPosition;
		this.fadeSamplesRemaining = 0;
		this.recalculateChannelVolumes();
	}

	@OriginalMember(owner = "client!qo", name = "f", descriptor = "(I)V")
	public synchronized void setVolume(@OriginalArg(0) int volume) {
		this.update(volume << 6, this.getPanPosition());
	}

	@OriginalMember(owner = "client!qo", name = "b", descriptor = "([IIIII)I")
	private int fillForward(@OriginalArg(0) int[] mix, @OriginalArg(1) int arg1, @OriginalArg(2) int limit, @OriginalArg(3) int arg3, @OriginalArg(4) int edgeSample) {
		do {
			if (this.fadeSamplesRemaining <= 0) {
				if (this.rate == 256 && (this.position & 0xFF) == 0) {
					if (GpuFaceNormal.aBoolean649) {
						return AreaSoundStream.mixForwardStereo(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.leftGain, this.rightGain, arg3, limit, this);
					}
					return AreaSoundStream.mixForwardMono(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.gain, arg3, limit, this);
				}
				if (GpuFaceNormal.aBoolean649) {
					return AreaSoundStream.mixForwardStereoResampled(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.leftGain, this.rightGain, arg3, limit, this, this.rate, edgeSample);
				}
				return AreaSoundStream.mixForwardMonoResampled(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.gain, arg3, limit, this, this.rate, edgeSample);
			}
			@Pc(5) int local5 = arg1 + this.fadeSamplesRemaining;
			if (local5 > arg3) {
				local5 = arg3;
			}
			this.fadeSamplesRemaining += arg1;
			if (this.rate == 256 && (this.position & 0xFF) == 0) {
				if (GpuFaceNormal.aBoolean649) {
					arg1 = AreaSoundStream.mixForwardStereoRamped(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.leftGain, this.rightGain, this.leftGainStep, this.rightGainStep, local5, limit, this);
				} else {
					arg1 = AreaSoundStream.mixForwardMonoRamped(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.gain, this.gainStep, local5, limit, this);
				}
			} else if (GpuFaceNormal.aBoolean649) {
				arg1 = AreaSoundStream.mixForwardStereoResampledRamped(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.leftGain, this.rightGain, this.leftGainStep, this.rightGainStep, local5, limit, this, this.rate, edgeSample);
			} else {
				arg1 = AreaSoundStream.mixForwardMonoResampledRamped(((VariableRateSoundPacket) super.aClass4_Sub4_5).data, mix, this.position, arg1, this.gain, this.gainStep, local5, limit, this, this.rate, edgeSample);
			}
			this.fadeSamplesRemaining -= arg1;
			if (this.fadeSamplesRemaining != 0) {
				return arg1;
			}
		} while (!this.updateFadeState());
		return arg3;
	}

	@OriginalMember(owner = "client!qo", name = "a", descriptor = "(III)V")
	public synchronized void fadeToVolumeAndPan(@OriginalArg(0) int fadeDuration, @OriginalArg(1) int volume, @OriginalArg(2) int pan) {
		if (fadeDuration == 0) {
			this.update(volume, pan);
			return;
		}
		@Pc(10) int local10 = AreaSoundStream.calculateSoundAttenuation(volume, pan);
		@Pc(14) int local14 = AreaSoundStream.calculateSoundPan(volume, pan);
		if (this.leftGain == local10 && this.rightGain == local14) {
			this.fadeSamplesRemaining = 0;
			return;
		}
		@Pc(31) int local31 = volume - this.gain;
		if (this.gain - volume > local31) {
			local31 = this.gain - volume;
		}
		if (local10 - this.leftGain > local31) {
			local31 = local10 - this.leftGain;
		}
		if (this.leftGain - local10 > local31) {
			local31 = this.leftGain - local10;
		}
		if (local14 - this.rightGain > local31) {
			local31 = local14 - this.rightGain;
		}
		if (this.rightGain - local14 > local31) {
			local31 = this.rightGain - local14;
		}
		if (fadeDuration > local31) {
			fadeDuration = local31;
		}
		this.fadeSamplesRemaining = fadeDuration;
		this.targetVolume = volume;
		this.panPosition = pan;
		this.gainStep = (volume - this.gain) / fadeDuration;
		this.leftGainStep = (local10 - this.leftGain) / fadeDuration;
		this.rightGainStep = (local14 - this.rightGain) / fadeDuration;
	}

	@OriginalMember(owner = "client!qo", name = "c", descriptor = "(II)V")
	public synchronized void fadeToVolume(@OriginalArg(0) int samples, @OriginalArg(1) int volume) {
		this.fadeToVolumeAndPan(samples, volume, this.getPanPosition());
	}

	@OriginalMember(owner = "client!qo", name = "k", descriptor = "()I")
	public synchronized int getAbsolutePlaybackRate() {
		return this.rate < 0 ? -this.rate : this.rate;
	}

	@OriginalMember(owner = "client!qo", name = "b", descriptor = "()Lclient!ti;")
	@Override
	public PcmStream method4857() {
		return null;
	}

	@OriginalMember(owner = "client!qo", name = "l", descriptor = "()Z")
	public boolean isFading() {
		return this.fadeSamplesRemaining != 0;
	}

	@OriginalMember(owner = "client!qo", name = "g", descriptor = "(I)V")
	private synchronized void mute() {
		this.update(0, this.getPanPosition());
	}

	@OriginalMember(owner = "client!qo", name = "h", descriptor = "(I)V")
	public synchronized void fadeOutAndStop(@OriginalArg(0) int fadeDuration) {
		if (fadeDuration == 0) {
			this.mute();
			this.method5684();
		} else if (this.leftGain == 0 && this.rightGain == 0) {
			this.fadeSamplesRemaining = 0;
			this.targetVolume = 0;
			this.gain = 0;
			this.method5684();
		} else {
			@Pc(31) int local31 = -this.gain;
			if (this.gain > local31) {
				local31 = this.gain;
			}
			if (-this.leftGain > local31) {
				local31 = -this.leftGain;
			}
			if (this.leftGain > local31) {
				local31 = this.leftGain;
			}
			if (-this.rightGain > local31) {
				local31 = -this.rightGain;
			}
			if (this.rightGain > local31) {
				local31 = this.rightGain;
			}
			if (fadeDuration > local31) {
				fadeDuration = local31;
			}
			this.fadeSamplesRemaining = fadeDuration;
			this.targetVolume = Integer.MIN_VALUE;
			this.gainStep = -this.gain / fadeDuration;
			this.leftGainStep = -this.leftGain / fadeDuration;
			this.rightGainStep = -this.rightGain / fadeDuration;
		}
	}

@OriginalMember(owner = "client!qo", name = "a", descriptor = "(I[B[IIIIIIIILclient!qo;)I")
	public static int mixBackwardStereo(@OriginalArg(1) byte[] arg0, @OriginalArg(2) int[] arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(8) int arg6, @OriginalArg(9) int arg7, @OriginalArg(10) AreaSoundStream arg8) {
		arg2 >>= 0x8;
		@Pc(7) int local7 = arg7 >> 8;
		@Pc(11) int local11 = arg4 << 2;
		@Pc(15) int local15 = arg5 << 2;
		@Pc(24) int local24;
		if ((local24 = arg3 + arg2 + 1 - local7) > arg6) {
			local24 = arg6;
		}
		arg3 <<= 0x1;
		local24 <<= 0x1;
		local24 -= 6;
		@Pc(43) byte local43;
		@Pc(46) int local46;
		while (arg3 < local24) {
			@Pc(41) int local41 = arg2 - 1;
			local43 = arg0[arg2];
			local46 = arg3 + 1;
			arg1[arg3] += local43 * local11;
			@Pc(56) int local56 = local46 + 1;
			arg1[local46] += local43 * local15;
			@Pc(66) int local66 = local41 - 1;
			@Pc(68) byte local68 = arg0[local41];
			@Pc(71) int local71 = local56 + 1;
			arg1[local56] += local68 * local11;
			@Pc(81) int local81 = local71 + 1;
			arg1[local71] += local68 * local15;
			@Pc(91) int local91 = local66 - 1;
			@Pc(93) byte local93 = arg0[local66];
			@Pc(96) int local96 = local81 + 1;
			arg1[local81] += local93 * local11;
			@Pc(106) int local106 = local96 + 1;
			arg1[local96] += local93 * local15;
			arg2 = local91 - 1;
			@Pc(118) byte local118 = arg0[local91];
			@Pc(121) int local121 = local106 + 1;
			arg1[local106] += local118 * local11;
			arg3 = local121 + 1;
			arg1[local121] += local118 * local15;
		}
		local24 += 6;
		while (arg3 < local24) {
			local43 = arg0[arg2--];
			local46 = arg3 + 1;
			arg1[arg3] += local43 * local11;
			arg3 = local46 + 1;
			arg1[local46] += local43 * local15;
		}
		arg8.position = arg2 << 8;
		return arg3 >> 1;
	}
@OriginalMember(owner = "client!qo", name = "a", descriptor = "(II[B[IIIIIIIILclient!qo;II)I")
	public static int mixForwardStereoResampled(@OriginalArg(2) byte[] arg0, @OriginalArg(3) int[] arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3, @OriginalArg(6) int arg4, @OriginalArg(7) int arg5, @OriginalArg(9) int arg6, @OriginalArg(10) int arg7, @OriginalArg(11) AreaSoundStream arg8, @OriginalArg(12) int arg9, @OriginalArg(13) int arg10) {
		@Pc(14) int local14;
		if (arg9 == 0 || (local14 = arg3 + (arg7 + arg9 - arg2 - 257) / arg9) > arg6) {
			local14 = arg6;
		}
		arg3 <<= 0x1;
		local14 <<= 0x1;
		@Pc(35) byte local35;
		@Pc(54) int local54;
		@Pc(51) int local51;
		@Pc(53) int local53;
		while (arg3 < local14) {
			@Pc(31) int local31 = arg2 >> 8;
			local35 = arg0[local31];
			local51 = (local35 << 8) + (arg0[local31 + 1] - local35) * (arg2 & 0xFF);
			local53 = arg3;
			local54 = arg3 + 1;
			arg1[local53] += local51 * arg4 >> 6;
			@Pc(65) int local65 = local54;
			arg3 = local54 + 1;
			arg1[local65] += local51 * arg5 >> 6;
			arg2 += arg9;
		}
		if (arg9 == 0 || (local14 = (arg3 >> 1) + (arg7 + arg9 - arg2 - 1) / arg9) > arg6) {
			local14 = arg6;
		}
		local14 <<= 0x1;
		while (arg3 < local14) {
			local35 = arg0[arg2 >> 8];
			local51 = (local35 << 8) + (arg10 - local35) * (arg2 & 0xFF);
			local53 = arg3;
			local54 = arg3 + 1;
			arg1[local53] += local51 * arg4 >> 6;
			local53 = local54;
			arg3 = local54 + 1;
			arg1[local53] += local51 * arg5 >> 6;
			arg2 += arg9;
		}
		arg8.position = arg2;
		return arg3 >> 1;
	}
@OriginalMember(owner = "client!qo", name = "a", descriptor = "([B[IIIIIIIILclient!qo;)I")
	public static int mixBackwardMonoRamped(@OriginalArg(0) byte[] samples, @OriginalArg(1) int[] mix, @OriginalArg(2) int position, @OriginalArg(3) int arg3, @OriginalArg(4) int gain, @OriginalArg(5) int gainStep, @OriginalArg(7) int arg6, @OriginalArg(8) int end, @OriginalArg(9) AreaSoundStream limit) {
		position >>= 0x8;
		@Pc(7) int local7 = end >> 8;
		gain <<= 0x2;
		@Pc(15) int local15 = gainStep << 2;
		@Pc(24) int local24;
		if ((local24 = arg3 + position + 1 - local7) > arg6) {
			local24 = arg6;
		}
		limit.leftGain += limit.leftGainStep * (local24 - arg3);
		limit.rightGain += limit.rightGainStep * (local24 - arg3);
		local24 -= 3;
		@Pc(54) int local54;
		while (arg3 < local24) {
			local54 = arg3++;
			@Pc(57) int local57 = mix[local54];
			@Pc(60) int local60 = position - 1;
			mix[local54] = local57 + samples[position] * gain;
			@Pc(69) int local69 = gain + local15;
			@Pc(71) int local71 = arg3++;
			@Pc(74) int local74 = mix[local71];
			@Pc(77) int local77 = local60 - 1;
			mix[local71] = local74 + samples[local60] * local69;
			@Pc(86) int local86 = local69 + local15;
			@Pc(88) int local88 = arg3++;
			@Pc(91) int local91 = mix[local88];
			@Pc(94) int local94 = local77 - 1;
			mix[local88] = local91 + samples[local77] * local86;
			@Pc(103) int local103 = local86 + local15;
			@Pc(105) int local105 = arg3++;
			@Pc(108) int local108 = mix[local105];
			position = local94 - 1;
			mix[local105] = local108 + samples[local94] * local103;
			gain = local103 + local15;
		}
		local24 += 3;
		while (arg3 < local24) {
			local54 = arg3++;
			mix[local54] += samples[position--] * gain;
			gain += local15;
		}
		limit.gain = gain >> 2;
		limit.position = position << 8;
		return arg3;
	}
@OriginalMember(owner = "client!qo", name = "a", descriptor = "([B[IIIIIIILclient!qo;)I")
	public static int mixBackwardMono(@OriginalArg(0) byte[] samples, @OriginalArg(1) int[] mix, @OriginalArg(2) int position, @OriginalArg(3) int arg3, @OriginalArg(4) int gain, @OriginalArg(6) int arg5, @OriginalArg(7) int end, @OriginalArg(8) AreaSoundStream limit) {
		position >>= 0x8;
		@Pc(7) int local7 = end >> 8;
		@Pc(11) int local11 = gain << 2;
		@Pc(20) int local20;
		if ((local20 = arg3 + position + 1 - local7) > arg5) {
			local20 = arg5;
		}
		local20 -= 3;
		@Pc(28) int local28;
		while (arg3 < local20) {
			local28 = arg3++;
			@Pc(31) int local31 = mix[local28];
			@Pc(34) int local34 = position - 1;
			mix[local28] = local31 + samples[position] * local11;
			@Pc(41) int local41 = arg3++;
			@Pc(44) int local44 = mix[local41];
			@Pc(47) int local47 = local34 - 1;
			mix[local41] = local44 + samples[local34] * local11;
			@Pc(54) int local54 = arg3++;
			@Pc(57) int local57 = mix[local54];
			@Pc(60) int local60 = local47 - 1;
			mix[local54] = local57 + samples[local47] * local11;
			@Pc(67) int local67 = arg3++;
			@Pc(70) int local70 = mix[local67];
			position = local60 - 1;
			mix[local67] = local70 + samples[local60] * local11;
		}
		local20 += 3;
		while (arg3 < local20) {
			local28 = arg3++;
			mix[local28] += samples[position--] * local11;
		}
		limit.position = position << 8;
		return arg3;
	}
@OriginalMember(owner = "client!qo", name = "a", descriptor = "(II)I")
	public static int calculateSoundAttenuation(@OriginalArg(0) int baseVolume, @OriginalArg(1) int distance) {
		return distance < 0 ? baseVolume : (int) ((double) baseVolume * Math.sqrt((double) (16384 - distance) * 1.220703125E-4D) + 0.5D);
	}
@OriginalMember(owner = "client!qo", name = "b", descriptor = "(II[B[IIIIIIIILclient!qo;II)I")
	public static int mixBackwardMonoResampledRamped(@OriginalArg(2) byte[] arg0, @OriginalArg(3) int[] arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3, @OriginalArg(6) int arg4, @OriginalArg(7) int arg5, @OriginalArg(9) int arg6, @OriginalArg(10) int arg7, @OriginalArg(11) AreaSoundStream arg8, @OriginalArg(12) int arg9, @OriginalArg(13) int arg10) {
		arg8.leftGain -= arg8.leftGainStep * arg3;
		arg8.rightGain -= arg8.rightGainStep * arg3;
		@Pc(32) int local32;
		if (arg9 == 0 || (local32 = arg3 + (arg7 + arg9 + 256 - arg2) / arg9) > arg6) {
			local32 = arg6;
		}
		@Pc(49) int local49;
		while (arg3 < local32) {
			@Pc(41) int local41 = arg2 >> 8;
			@Pc(47) byte local47 = arg0[local41 - 1];
			local49 = arg3++;
			arg1[local49] += ((local47 << 8) + (arg0[local41] - local47) * (arg2 & 0xFF)) * arg4 >> 6;
			arg4 += arg5;
			arg2 += arg9;
		}
		if (arg9 == 0 || (local32 = arg3 + (arg7 + arg9 - arg2) / arg9) > arg6) {
			local32 = arg6;
		}
		while (arg3 < local32) {
			local49 = arg3++;
			arg1[local49] += ((arg10 << 8) + (arg0[arg2 >> 8] - arg10) * (arg2 & 0xFF)) * arg4 >> 6;
			arg4 += arg5;
			arg2 += arg9;
		}
		arg8.leftGain += arg8.leftGainStep * arg3;
		arg8.rightGain += arg8.rightGainStep * arg3;
		arg8.gain = arg4;
		arg8.position = arg2;
		return arg3;
	}
@OriginalMember(owner = "client!qo", name = "b", descriptor = "(I[B[IIIIIIIILclient!qo;)I")
	public static int mixForwardStereo(@OriginalArg(1) byte[] arg0, @OriginalArg(2) int[] arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(8) int arg6, @OriginalArg(9) int arg7, @OriginalArg(10) AreaSoundStream arg8) {
		arg2 >>= 0x8;
		@Pc(7) int local7 = arg7 >> 8;
		@Pc(11) int local11 = arg4 << 2;
		@Pc(15) int local15 = arg5 << 2;
		@Pc(22) int local22;
		if ((local22 = arg3 + local7 - arg2) > arg6) {
			local22 = arg6;
		}
		arg3 <<= 0x1;
		local22 <<= 0x1;
		local22 -= 6;
		@Pc(41) byte local41;
		@Pc(44) int local44;
		while (arg3 < local22) {
			@Pc(39) int local39 = arg2 + 1;
			local41 = arg0[arg2];
			local44 = arg3 + 1;
			arg1[arg3] += local41 * local11;
			@Pc(54) int local54 = local44 + 1;
			arg1[local44] += local41 * local15;
			@Pc(64) int local64 = local39 + 1;
			@Pc(66) byte local66 = arg0[local39];
			@Pc(69) int local69 = local54 + 1;
			arg1[local54] += local66 * local11;
			@Pc(79) int local79 = local69 + 1;
			arg1[local69] += local66 * local15;
			@Pc(89) int local89 = local64 + 1;
			@Pc(91) byte local91 = arg0[local64];
			@Pc(94) int local94 = local79 + 1;
			arg1[local79] += local91 * local11;
			@Pc(104) int local104 = local94 + 1;
			arg1[local94] += local91 * local15;
			arg2 = local89 + 1;
			@Pc(116) byte local116 = arg0[local89];
			@Pc(119) int local119 = local104 + 1;
			arg1[local104] += local116 * local11;
			arg3 = local119 + 1;
			arg1[local119] += local116 * local15;
		}
		local22 += 6;
		while (arg3 < local22) {
			local41 = arg0[arg2++];
			local44 = arg3 + 1;
			arg1[arg3] += local41 * local11;
			arg3 = local44 + 1;
			arg1[local44] += local41 * local15;
		}
		arg8.position = arg2 << 8;
		return arg3 >> 1;
	}
@OriginalMember(owner = "client!qo", name = "a", descriptor = "(I[B[IIIIIIIIIILclient!qo;)I")
	public static int mixForwardStereoRamped(@OriginalArg(1) byte[] arg0, @OriginalArg(2) int[] arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(10) int arg8, @OriginalArg(11) int arg9, @OriginalArg(12) AreaSoundStream arg10) {
		arg2 >>= 0x8;
		@Pc(7) int local7 = arg9 >> 8;
		arg4 <<= 0x2;
		arg5 <<= 0x2;
		@Pc(19) int local19 = arg6 << 2;
		@Pc(23) int local23 = arg7 << 2;
		@Pc(30) int local30;
		if ((local30 = arg3 + local7 - arg2) > arg8) {
			local30 = arg8;
		}
		arg10.gain += arg10.gainStep * (local30 - arg3);
		arg3 <<= 0x1;
		local30 <<= 0x1;
		local30 -= 6;
		@Pc(60) byte local60;
		@Pc(63) int local63;
		while (arg3 < local30) {
			@Pc(58) int local58 = arg2 + 1;
			local60 = arg0[arg2];
			local63 = arg3 + 1;
			arg1[arg3] += local60 * arg4;
			@Pc(74) int local74 = arg4 + local19;
			@Pc(77) int local77 = local63 + 1;
			arg1[local63] += local60 * arg5;
			@Pc(88) int local88 = arg5 + local23;
			@Pc(91) int local91 = local58 + 1;
			@Pc(93) byte local93 = arg0[local58];
			@Pc(96) int local96 = local77 + 1;
			arg1[local77] += local93 * local74;
			@Pc(107) int local107 = local74 + local19;
			@Pc(110) int local110 = local96 + 1;
			arg1[local96] += local93 * local88;
			@Pc(121) int local121 = local88 + local23;
			@Pc(124) int local124 = local91 + 1;
			@Pc(126) byte local126 = arg0[local91];
			@Pc(129) int local129 = local110 + 1;
			arg1[local110] += local126 * local107;
			@Pc(140) int local140 = local107 + local19;
			@Pc(143) int local143 = local129 + 1;
			arg1[local129] += local126 * local121;
			@Pc(154) int local154 = local121 + local23;
			arg2 = local124 + 1;
			@Pc(159) byte local159 = arg0[local124];
			@Pc(162) int local162 = local143 + 1;
			arg1[local143] += local159 * local140;
			arg4 = local140 + local19;
			arg3 = local162 + 1;
			arg1[local162] += local159 * local154;
			arg5 = local154 + local23;
		}
		local30 += 6;
		while (arg3 < local30) {
			local60 = arg0[arg2++];
			local63 = arg3 + 1;
			arg1[arg3] += local60 * arg4;
			arg4 += local19;
			arg3 = local63 + 1;
			arg1[local63] += local60 * arg5;
			arg5 += local23;
		}
		arg10.leftGain = arg4 >> 2;
		arg10.rightGain = arg5 >> 2;
		arg10.position = arg2 << 8;
		return arg3 >> 1;
	}
@OriginalMember(owner = "client!qo", name = "a", descriptor = "(II[B[IIIIIIILclient!qo;II)I")
	public static int mixBackwardMonoResampled(@OriginalArg(2) byte[] arg0, @OriginalArg(3) int[] arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3, @OriginalArg(6) int arg4, @OriginalArg(8) int arg5, @OriginalArg(9) int arg6, @OriginalArg(10) AreaSoundStream arg7, @OriginalArg(11) int arg8, @OriginalArg(12) int arg9) {
		@Pc(14) int local14;
		if (arg8 == 0 || (local14 = arg3 + (arg6 + arg8 + 256 - arg2) / arg8) > arg5) {
			local14 = arg5;
		}
		@Pc(31) int local31;
		while (arg3 < local14) {
			@Pc(23) int local23 = arg2 >> 8;
			@Pc(29) byte local29 = arg0[local23 - 1];
			local31 = arg3++;
			arg1[local31] += ((local29 << 8) + (arg0[local23] - local29) * (arg2 & 0xFF)) * arg4 >> 6;
			arg2 += arg8;
		}
		if (arg8 == 0 || (local14 = arg3 + (arg6 + arg8 - arg2) / arg8) > arg5) {
			local14 = arg5;
		}
		while (arg3 < local14) {
			local31 = arg3++;
			arg1[local31] += ((arg9 << 8) + (arg0[arg2 >> 8] - arg9) * (arg2 & 0xFF)) * arg4 >> 6;
			arg2 += arg8;
		}
		arg7.position = arg2;
		return arg3;
	}
@OriginalMember(owner = "client!qo", name = "c", descriptor = "(II[B[IIIIIIIILclient!qo;II)I")
	public static int mixBackwardStereoResampled(@OriginalArg(2) byte[] arg0, @OriginalArg(3) int[] arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3, @OriginalArg(6) int arg4, @OriginalArg(7) int arg5, @OriginalArg(9) int arg6, @OriginalArg(10) int arg7, @OriginalArg(11) AreaSoundStream arg8, @OriginalArg(12) int arg9, @OriginalArg(13) int arg10) {
		@Pc(14) int local14;
		if (arg9 == 0 || (local14 = arg3 + (arg7 + arg9 + 256 - arg2) / arg9) > arg6) {
			local14 = arg6;
		}
		arg3 <<= 0x1;
		local14 <<= 0x1;
		@Pc(54) int local54;
		@Pc(51) int local51;
		@Pc(53) int local53;
		while (arg3 < local14) {
			@Pc(31) int local31 = arg2 >> 8;
			@Pc(37) byte local37 = arg0[local31 - 1];
			local51 = (local37 << 8) + (arg0[local31] - local37) * (arg2 & 0xFF);
			local53 = arg3;
			local54 = arg3 + 1;
			arg1[local53] += local51 * arg4 >> 6;
			@Pc(65) int local65 = local54;
			arg3 = local54 + 1;
			arg1[local65] += local51 * arg5 >> 6;
			arg2 += arg9;
		}
		if (arg9 == 0 || (local14 = (arg3 >> 1) + (arg7 + arg9 - arg2) / arg9) > arg6) {
			local14 = arg6;
		}
		local14 <<= 0x1;
		while (arg3 < local14) {
			local51 = (arg10 << 8) + (arg0[arg2 >> 8] - arg10) * (arg2 & 0xFF);
			local53 = arg3;
			local54 = arg3 + 1;
			arg1[local53] += local51 * arg4 >> 6;
			local53 = local54;
			arg3 = local54 + 1;
			arg1[local53] += local51 * arg5 >> 6;
			arg2 += arg9;
		}
		arg8.position = arg2;
		return arg3 >> 1;
	}
@OriginalMember(owner = "client!qo", name = "a", descriptor = "(II[B[IIIIIIIIIILclient!qo;II)I")
	public static int mixBackwardStereoResampledRamped(@OriginalArg(2) byte[] arg0, @OriginalArg(3) int[] arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3, @OriginalArg(6) int arg4, @OriginalArg(7) int arg5, @OriginalArg(8) int arg6, @OriginalArg(9) int arg7, @OriginalArg(11) int arg8, @OriginalArg(12) int arg9, @OriginalArg(13) AreaSoundStream arg10, @OriginalArg(14) int arg11, @OriginalArg(15) int arg12) {
		arg10.gain -= arg10.gainStep * arg3;
		@Pc(23) int local23;
		if (arg11 == 0 || (local23 = arg3 + (arg9 + arg11 + 256 - arg2) / arg11) > arg8) {
			local23 = arg8;
		}
		arg3 <<= 0x1;
		local23 <<= 0x1;
		@Pc(63) int local63;
		@Pc(60) int local60;
		@Pc(62) int local62;
		while (arg3 < local23) {
			@Pc(40) int local40 = arg2 >> 8;
			@Pc(46) byte local46 = arg0[local40 - 1];
			local60 = (local46 << 8) + (arg0[local40] - local46) * (arg2 & 0xFF);
			local62 = arg3;
			local63 = arg3 + 1;
			arg1[local62] += local60 * arg4 >> 6;
			arg4 += arg6;
			@Pc(78) int local78 = local63;
			arg3 = local63 + 1;
			arg1[local78] += local60 * arg5 >> 6;
			arg5 += arg7;
			arg2 += arg11;
		}
		if (arg11 == 0 || (local23 = (arg3 >> 1) + (arg9 + arg11 - arg2) / arg11) > arg8) {
			local23 = arg8;
		}
		local23 <<= 0x1;
		while (arg3 < local23) {
			local60 = (arg12 << 8) + (arg0[arg2 >> 8] - arg12) * (arg2 & 0xFF);
			local62 = arg3;
			local63 = arg3 + 1;
			arg1[local62] += local60 * arg4 >> 6;
			arg4 += arg6;
			local62 = local63;
			arg3 = local63 + 1;
			arg1[local62] += local60 * arg5 >> 6;
			arg5 += arg7;
			arg2 += arg11;
		}
		local63 = arg3 >> 1;
		arg10.gain += arg10.gainStep * local63;
		arg10.leftGain = arg4;
		arg10.rightGain = arg5;
		arg10.position = arg2;
		return local63;
	}
@OriginalMember(owner = "client!qo", name = "b", descriptor = "(II[B[IIIIIIIIIILclient!qo;II)I")
	public static int mixForwardStereoResampledRamped(@OriginalArg(2) byte[] arg0, @OriginalArg(3) int[] arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3, @OriginalArg(6) int arg4, @OriginalArg(7) int arg5, @OriginalArg(8) int arg6, @OriginalArg(9) int arg7, @OriginalArg(11) int arg8, @OriginalArg(12) int arg9, @OriginalArg(13) AreaSoundStream arg10, @OriginalArg(14) int arg11, @OriginalArg(15) int arg12) {
		arg10.gain -= arg10.gainStep * arg3;
		@Pc(23) int local23;
		if (arg11 == 0 || (local23 = arg3 + (arg9 + arg11 - arg2 - 257) / arg11) > arg8) {
			local23 = arg8;
		}
		arg3 <<= 0x1;
		local23 <<= 0x1;
		@Pc(44) byte local44;
		@Pc(63) int local63;
		@Pc(60) int local60;
		@Pc(62) int local62;
		while (arg3 < local23) {
			@Pc(40) int local40 = arg2 >> 8;
			local44 = arg0[local40];
			local60 = (local44 << 8) + (arg0[local40 + 1] - local44) * (arg2 & 0xFF);
			local62 = arg3;
			local63 = arg3 + 1;
			arg1[local62] += local60 * arg4 >> 6;
			arg4 += arg6;
			@Pc(78) int local78 = local63;
			arg3 = local63 + 1;
			arg1[local78] += local60 * arg5 >> 6;
			arg5 += arg7;
			arg2 += arg11;
		}
		if (arg11 == 0 || (local23 = (arg3 >> 1) + (arg9 + arg11 - arg2 - 1) / arg11) > arg8) {
			local23 = arg8;
		}
		local23 <<= 0x1;
		while (arg3 < local23) {
			local44 = arg0[arg2 >> 8];
			local60 = (local44 << 8) + (arg12 - local44) * (arg2 & 0xFF);
			local62 = arg3;
			local63 = arg3 + 1;
			arg1[local62] += local60 * arg4 >> 6;
			arg4 += arg6;
			local62 = local63;
			arg3 = local63 + 1;
			arg1[local62] += local60 * arg5 >> 6;
			arg5 += arg7;
			arg2 += arg11;
		}
		local63 = arg3 >> 1;
		arg10.gain += arg10.gainStep * local63;
		arg10.leftGain = arg4;
		arg10.rightGain = arg5;
		arg10.position = arg2;
		return local63;
	}
@OriginalMember(owner = "client!qo", name = "b", descriptor = "([B[IIIIIIILclient!qo;)I")
	public static int mixForwardMono(@OriginalArg(0) byte[] samples, @OriginalArg(1) int[] mix, @OriginalArg(2) int position, @OriginalArg(3) int arg3, @OriginalArg(4) int gain, @OriginalArg(6) int arg5, @OriginalArg(7) int end, @OriginalArg(8) AreaSoundStream limit) {
		position >>= 0x8;
		@Pc(7) int local7 = end >> 8;
		@Pc(11) int local11 = gain << 2;
		@Pc(18) int local18;
		if ((local18 = arg3 + local7 - position) > arg5) {
			local18 = arg5;
		}
		local18 -= 3;
		@Pc(26) int local26;
		while (arg3 < local18) {
			local26 = arg3++;
			@Pc(29) int local29 = mix[local26];
			@Pc(32) int local32 = position + 1;
			mix[local26] = local29 + samples[position] * local11;
			@Pc(39) int local39 = arg3++;
			@Pc(42) int local42 = mix[local39];
			@Pc(45) int local45 = local32 + 1;
			mix[local39] = local42 + samples[local32] * local11;
			@Pc(52) int local52 = arg3++;
			@Pc(55) int local55 = mix[local52];
			@Pc(58) int local58 = local45 + 1;
			mix[local52] = local55 + samples[local45] * local11;
			@Pc(65) int local65 = arg3++;
			@Pc(68) int local68 = mix[local65];
			position = local58 + 1;
			mix[local65] = local68 + samples[local58] * local11;
		}
		local18 += 3;
		while (arg3 < local18) {
			local26 = arg3++;
			mix[local26] += samples[position++] * local11;
		}
		limit.position = position << 8;
		return arg3;
	}
@OriginalMember(owner = "client!qo", name = "b", descriptor = "(II[B[IIIIIIILclient!qo;II)I")
	public static int mixForwardMonoResampled(@OriginalArg(2) byte[] arg0, @OriginalArg(3) int[] arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3, @OriginalArg(6) int arg4, @OriginalArg(8) int arg5, @OriginalArg(9) int arg6, @OriginalArg(10) AreaSoundStream arg7, @OriginalArg(11) int arg8, @OriginalArg(12) int arg9) {
		@Pc(14) int local14;
		if (arg8 == 0 || (local14 = arg3 + (arg6 + arg8 - arg2 - 257) / arg8) > arg5) {
			local14 = arg5;
		}
		@Pc(27) byte local27;
		@Pc(29) int local29;
		while (arg3 < local14) {
			@Pc(23) int local23 = arg2 >> 8;
			local27 = arg0[local23];
			local29 = arg3++;
			arg1[local29] += ((local27 << 8) + (arg0[local23 + 1] - local27) * (arg2 & 0xFF)) * arg4 >> 6;
			arg2 += arg8;
		}
		if (arg8 == 0 || (local14 = arg3 + (arg6 + arg8 - arg2 - 1) / arg8) > arg5) {
			local14 = arg5;
		}
		while (arg3 < local14) {
			local27 = arg0[arg2 >> 8];
			local29 = arg3++;
			arg1[local29] += ((local27 << 8) + (arg9 - local27) * (arg2 & 0xFF)) * arg4 >> 6;
			arg2 += arg8;
		}
		arg7.position = arg2;
		return arg3;
	}
@OriginalMember(owner = "client!qo", name = "d", descriptor = "(II[B[IIIIIIIILclient!qo;II)I")
	public static int mixForwardMonoResampledRamped(@OriginalArg(2) byte[] arg0, @OriginalArg(3) int[] arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3, @OriginalArg(6) int arg4, @OriginalArg(7) int arg5, @OriginalArg(9) int arg6, @OriginalArg(10) int arg7, @OriginalArg(11) AreaSoundStream arg8, @OriginalArg(12) int arg9, @OriginalArg(13) int arg10) {
		arg8.leftGain -= arg8.leftGainStep * arg3;
		arg8.rightGain -= arg8.rightGainStep * arg3;
		@Pc(32) int local32;
		if (arg9 == 0 || (local32 = arg3 + (arg7 + arg9 - arg2 - 257) / arg9) > arg6) {
			local32 = arg6;
		}
		@Pc(45) byte local45;
		@Pc(47) int local47;
		while (arg3 < local32) {
			@Pc(41) int local41 = arg2 >> 8;
			local45 = arg0[local41];
			local47 = arg3++;
			arg1[local47] += ((local45 << 8) + (arg0[local41 + 1] - local45) * (arg2 & 0xFF)) * arg4 >> 6;
			arg4 += arg5;
			arg2 += arg9;
		}
		if (arg9 == 0 || (local32 = arg3 + (arg7 + arg9 - arg2 - 1) / arg9) > arg6) {
			local32 = arg6;
		}
		while (arg3 < local32) {
			local45 = arg0[arg2 >> 8];
			local47 = arg3++;
			arg1[local47] += ((local45 << 8) + (arg10 - local45) * (arg2 & 0xFF)) * arg4 >> 6;
			arg4 += arg5;
			arg2 += arg9;
		}
		arg8.leftGain += arg8.leftGainStep * arg3;
		arg8.rightGain += arg8.rightGainStep * arg3;
		arg8.gain = arg4;
		arg8.position = arg2;
		return arg3;
	}
@OriginalMember(owner = "client!qo", name = "b", descriptor = "([B[IIIIIIIILclient!qo;)I")
	public static int mixForwardMonoRamped(@OriginalArg(0) byte[] samples, @OriginalArg(1) int[] mix, @OriginalArg(2) int position, @OriginalArg(3) int arg3, @OriginalArg(4) int gain, @OriginalArg(5) int gainStep, @OriginalArg(7) int arg6, @OriginalArg(8) int end, @OriginalArg(9) AreaSoundStream limit) {
		position >>= 0x8;
		@Pc(7) int local7 = end >> 8;
		gain <<= 0x2;
		@Pc(15) int local15 = gainStep << 2;
		@Pc(22) int local22;
		if ((local22 = arg3 + local7 - position) > arg6) {
			local22 = arg6;
		}
		limit.leftGain += limit.leftGainStep * (local22 - arg3);
		limit.rightGain += limit.rightGainStep * (local22 - arg3);
		local22 -= 3;
		@Pc(52) int local52;
		while (arg3 < local22) {
			local52 = arg3++;
			@Pc(55) int local55 = mix[local52];
			@Pc(58) int local58 = position + 1;
			mix[local52] = local55 + samples[position] * gain;
			@Pc(67) int local67 = gain + local15;
			@Pc(69) int local69 = arg3++;
			@Pc(72) int local72 = mix[local69];
			@Pc(75) int local75 = local58 + 1;
			mix[local69] = local72 + samples[local58] * local67;
			@Pc(84) int local84 = local67 + local15;
			@Pc(86) int local86 = arg3++;
			@Pc(89) int local89 = mix[local86];
			@Pc(92) int local92 = local75 + 1;
			mix[local86] = local89 + samples[local75] * local84;
			@Pc(101) int local101 = local84 + local15;
			@Pc(103) int local103 = arg3++;
			@Pc(106) int local106 = mix[local103];
			position = local92 + 1;
			mix[local103] = local106 + samples[local92] * local101;
			gain = local101 + local15;
		}
		local22 += 3;
		while (arg3 < local22) {
			local52 = arg3++;
			mix[local52] += samples[position++] * gain;
			gain += local15;
		}
		limit.gain = gain >> 2;
		limit.position = position << 8;
		return arg3;
	}
@OriginalMember(owner = "client!qo", name = "d", descriptor = "(II)I")
	public static int calculateSoundPan(@OriginalArg(0) int basePan, @OriginalArg(1) int distance) {
		return distance < 0 ? -basePan : (int) ((double) basePan * Math.sqrt((double) distance * 1.220703125E-4D) + 0.5D);
	}
@OriginalMember(owner = "client!qo", name = "a", descriptor = "(Lclient!js;III)Lclient!qo;")
	public static AreaSoundStream create(@OriginalArg(0) VariableRateSoundPacket packet, @OriginalArg(1) int rate, @OriginalArg(2) int volume, @OriginalArg(3) int range) {
		return packet.data == null || packet.data.length == 0 ? null : new AreaSoundStream(packet, rate, volume, range);
	}
@OriginalMember(owner = "client!qo", name = "a", descriptor = "(Lclient!js;II)Lclient!qo;")
	public static AreaSoundStream method4489(@OriginalArg(0) VariableRateSoundPacket arg0, @OriginalArg(2) int arg1) {
		return arg0.data == null || arg0.data.length == 0 ? null : new AreaSoundStream(arg0, (int) ((long) arg0.sampleRate * 256L * (long) 100 / (long) (MECType.anInt4928 * 100)), arg1 << 6);
	}
@OriginalMember(owner = "client!qo", name = "b", descriptor = "(I[B[IIIIIIIIIILclient!qo;)I")
	public static int mixBackwardStereoRamped(@OriginalArg(1) byte[] arg0, @OriginalArg(2) int[] arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(10) int arg8, @OriginalArg(11) int arg9, @OriginalArg(12) AreaSoundStream arg10) {
		arg2 >>= 0x8;
		@Pc(7) int local7 = arg9 >> 8;
		arg4 <<= 0x2;
		arg5 <<= 0x2;
		@Pc(19) int local19 = arg6 << 2;
		@Pc(23) int local23 = arg7 << 2;
		@Pc(32) int local32;
		if ((local32 = arg3 + arg2 + 1 - local7) > arg8) {
			local32 = arg8;
		}
		arg10.gain += arg10.gainStep * (local32 - arg3);
		arg3 <<= 0x1;
		local32 <<= 0x1;
		local32 -= 6;
		@Pc(62) byte local62;
		@Pc(65) int local65;
		while (arg3 < local32) {
			@Pc(60) int local60 = arg2 - 1;
			local62 = arg0[arg2];
			local65 = arg3 + 1;
			arg1[arg3] += local62 * arg4;
			@Pc(76) int local76 = arg4 + local19;
			@Pc(79) int local79 = local65 + 1;
			arg1[local65] += local62 * arg5;
			@Pc(90) int local90 = arg5 + local23;
			@Pc(93) int local93 = local60 - 1;
			@Pc(95) byte local95 = arg0[local60];
			@Pc(98) int local98 = local79 + 1;
			arg1[local79] += local95 * local76;
			@Pc(109) int local109 = local76 + local19;
			@Pc(112) int local112 = local98 + 1;
			arg1[local98] += local95 * local90;
			@Pc(123) int local123 = local90 + local23;
			@Pc(126) int local126 = local93 - 1;
			@Pc(128) byte local128 = arg0[local93];
			@Pc(131) int local131 = local112 + 1;
			arg1[local112] += local128 * local109;
			@Pc(142) int local142 = local109 + local19;
			@Pc(145) int local145 = local131 + 1;
			arg1[local131] += local128 * local123;
			@Pc(156) int local156 = local123 + local23;
			arg2 = local126 - 1;
			@Pc(161) byte local161 = arg0[local126];
			@Pc(164) int local164 = local145 + 1;
			arg1[local145] += local161 * local142;
			arg4 = local142 + local19;
			arg3 = local164 + 1;
			arg1[local164] += local161 * local156;
			arg5 = local156 + local23;
		}
		local32 += 6;
		while (arg3 < local32) {
			local62 = arg0[arg2--];
			local65 = arg3 + 1;
			arg1[arg3] += local62 * arg4;
			arg4 += local19;
			arg3 = local65 + 1;
			arg1[local65] += local62 * arg5;
			arg5 += local23;
		}
		arg10.leftGain = arg4 >> 2;
		arg10.rightGain = arg5 >> 2;
		arg10.position = arg2 << 8;
		return arg3 >> 1;
	}
}
