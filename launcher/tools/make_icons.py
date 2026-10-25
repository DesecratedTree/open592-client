#!/usr/bin/env python3
"""Draw the launcher icon set.

The launcher ships no third-party art, so the icon is generated here: a stone
tile with a gold ring, a sun and two hills, in the client's palette. Run from
the repository root:

    python launcher/tools/make_icons.py

Writes launcher/src-tauri/icons/{icon.ico, icon.icns, icon.png, 32x32.png,
128x128.png, 128x128@2x.png}. The ICO and ICNS containers embed PNG frames,
which Windows Vista+, macOS and Tauri's bundlers all read.
"""

import math
import os
import struct
import zlib

OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src-tauri", "icons")

STONE_TOP = (48, 39, 31)
STONE_BOTTOM = (19, 15, 11)
INK = (22, 18, 13)
GOLD = (233, 196, 106)
GOLD_DEEP = (148, 111, 44)
HILL_LIGHT = (86, 108, 66)
HILL_DARK = (55, 74, 44)


def mix(a, b, t):
    return tuple(int(round(a[i] + (b[i] - a[i]) * t)) for i in range(3))


def over(dst, src, alpha):
    return tuple(int(round(dst[i] * (1 - alpha) + src[i] * alpha)) for i in range(3))


def inside_round(x, y, inset, radius):
    low = inset
    high = 1.0 - inset
    if x < low or x > high or y < low or y > high:
        return False
    r = radius - inset
    cx = min(max(x, low + r), high - r)
    cy = min(max(y, low + r), high - r)
    dx = x - cx
    dy = y - cy
    return dx * dx + dy * dy <= r * r


def sample(x, y):
    """One supersampled pixel, returning (r, g, b, a)."""
    if not inside_round(x, y, 0.0, 0.18):
        return (0, 0, 0, 0)

    colour = mix(STONE_TOP, STONE_BOTTOM, y)
    glow = max(0.0, 1.0 - math.hypot(x - 0.16, y - 0.10) / 0.8) ** 2
    colour = over(colour, (104, 80, 45), glow * 0.38)

    # Hairline gold edge just inside the tile.
    if not inside_round(x, y, 0.014, 0.18):
        colour = over(colour, GOLD_DEEP, 0.65)

    dx = x - 0.5
    dy = y - 0.51
    dist = math.hypot(dx, dy)
    if dist <= 0.36:
        if 0.308 <= dist <= 0.348:
            colour = over(colour, GOLD, 0.94)
            if y < 0.46:
                shine = max(0.0, 1.0 - (y - 0.15) * 2.4)
                colour = over(colour, (255, 238, 190), shine * 0.45)
        else:
            colour = over(colour, INK, 0.95)
            if math.hypot(x - 0.5, y - 0.425) <= 0.074:
                colour = over(colour, GOLD, 0.92)
            if y >= 0.585 + 0.02 * math.sin((x - 0.5) * 9.0):
                colour = over(colour, HILL_LIGHT, 0.95)
            if y >= 0.655 + 0.02 * math.sin((x - 0.5) * 7.0 + 1.4):
                colour = over(colour, HILL_DARK, 0.95)
            if dist > 0.26:
                edge = min(1.0, (dist - 0.26) / 0.09)
                colour = over(colour, (0, 0, 0), edge * 0.3)

    return (colour[0], colour[1], colour[2], 255)


def render_samples(size, ss=2):
    """Supersampled pixels, `size * ss` square."""
    big = size * ss
    samples = []
    for py in range(big):
        row = []
        for px in range(big):
            row.append(sample((px + 0.5) / big, (py + 0.5) / big))
        samples.append(row)
    return samples


def downscale(samples, target):
    step = len(samples) // target
    rows = samples
    out = []
    for y in range(target):
        row = []
        for x in range(target):
            accum = [0, 0, 0, 0]
            for sy in range(step):
                for sx in range(step):
                    pr, pg, pb, pa = rows[y * step + sy][x * step + sx]
                    accum[0] += pr * pa
                    accum[1] += pg * pa
                    accum[2] += pb * pa
                    accum[3] += pa
            if accum[3] == 0:
                row.append((0, 0, 0, 0))
            else:
                count = step * step
                row.append(
                    (
                        accum[0] // accum[3],
                        accum[1] // accum[3],
                        accum[2] // accum[3],
                        accum[3] // count,
                    )
                )
        out.append(row)
    return out


def png_bytes(rows):
    height = len(rows)
    width = len(rows[0])
    raw = b"".join(b"\x00" + b"".join(bytes(px) for px in row) for row in rows)

    def chunk(kind, data):
        body = struct.pack(">I", len(data)) + kind + data
        return body + struct.pack(">I", zlib.crc32(kind + data) & 0xFFFFFFFF)

    header = b"\x89PNG\r\n\x1a\n"
    ihdr = struct.pack(">IIBBBBB", width, height, 8, 6, 0, 0, 0)
    return (
        header
        + chunk(b"IHDR", ihdr)
        + chunk(b"IDAT", zlib.compress(raw, 9))
        + chunk(b"IEND", b"")
    )


def icns_bytes(entries):
    chunks = b""
    for kind, png in entries:
        chunks += kind + struct.pack(">I", len(png) + 8) + png
    return b"icns" + struct.pack(">I", len(chunks) + 8) + chunks


def ico_bytes(frames):
    header = struct.pack("<HHH", 0, 1, len(frames))
    offset = 6 + 16 * len(frames)
    entries = b""
    data = b""
    for size, png in frames:
        entries += struct.pack(
            "<BBBBHHII",
            0 if size >= 256 else size,
            0 if size >= 256 else size,
            0,
            0,
            1,
            32,
            len(png),
            offset,
        )
        offset += len(png)
        data += png
    return header + entries + data


def main():
    os.makedirs(OUT, exist_ok=True)
    # One 1024x1024 supersampled pass feeds every size, so no layer is ever
    # an upscale of a smaller one.
    samples = render_samples(512)
    base = downscale(samples, 512)
    pngs = {
        1024: png_bytes(samples),
        512: png_bytes(base),
        256: png_bytes(downscale(base, 256)),
        128: png_bytes(downscale(base, 128)),
        64: png_bytes(downscale(base, 64)),
        48: png_bytes(downscale(render_samples(48), 48)),
        32: png_bytes(downscale(base, 32)),
        16: png_bytes(downscale(base, 16)),
    }
    files = {
        "icon.png": 512,
        "128x128@2x.png": 256,
        "128x128.png": 128,
        "32x32.png": 32,
    }
    for name, size in files.items():
        with open(os.path.join(OUT, name), "wb") as handle:
            handle.write(pngs[size])

    frames = [(size, pngs[size]) for size in (16, 32, 48, 64, 128, 256)]
    with open(os.path.join(OUT, "icon.ico"), "wb") as handle:
        handle.write(ico_bytes(frames))

    # ICNS PNG types: 32, 64 (32@2x), 128, 256 (128@2x), 512, 1024 (512@2x).
    icns = [
        (b"ic11", pngs[32]),
        (b"ic12", pngs[64]),
        (b"ic07", pngs[128]),
        (b"ic13", pngs[256]),
        (b"ic08", pngs[256]),
        (b"ic14", pngs[512]),
        (b"ic09", pngs[512]),
        (b"ic10", pngs[1024]),
    ]
    with open(os.path.join(OUT, "icon.icns"), "wb") as handle:
        handle.write(icns_bytes(icns))

    print("wrote icons to", os.path.normpath(OUT))


if __name__ == "__main__":
    main()
