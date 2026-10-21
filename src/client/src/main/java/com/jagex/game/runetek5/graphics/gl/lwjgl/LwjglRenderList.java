package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Display-list range owned by the LWJGL renderer, replacing the
 * {@code GlRenderList} role. Reserving, executing and compiling mirror the
 * legacy behaviour exactly: the constructor reserves {@code range} names,
 * calls add the reserved base to the caller's index, and compilation opens
 * with mode 4864 ({@code GL_COMPILE}, legacy literal).
 */
public final class LwjglRenderList {

    /** Compile-only list mode (legacy literal 4864). */
    public static final int MODE_COMPILE = 0x1300;

    private final LwjglListOps ops;
    private final int base;

    /**
     * @param ops list seam; {@link LwjglListGl#get()} in game code.
     * @param range list-name count to reserve.
     */
    public LwjglRenderList(LwjglListOps ops, int range) {
        if (ops == null) {
            throw new RuntimeException();
        }
        this.ops = ops;
        this.base = ops.genLists(range);
    }

    /** @return first reserved list name. */
    public int base() {
        return this.base;
    }

    /** Executes the list at {@code index} above the reserved base. */
    public void callList(char index) {
        this.ops.callList(index + this.base);
    }

    /** Opens the list at {@code index} above the base for compilation. */
    public void beginList(int index) {
        this.ops.newList(this.base + index, MODE_COMPILE);
    }

    /** Closes the open list. */
    public void endList() {
        this.ops.endList();
    }
}
