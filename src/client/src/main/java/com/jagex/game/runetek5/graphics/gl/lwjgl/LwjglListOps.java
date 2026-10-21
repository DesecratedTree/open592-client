package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Seam for the display-list calls backing the LWJGL render list.
 * The production implementation issues real {@code GL11} calls (and so
 * requires a current context); tests inject a recording fake, which keeps
 * list lifecycle logic headless-safe.
 */
public interface LwjglListOps {

    /** Reserves a contiguous range of list names. */
    int genLists(int range);

    /** Executes a compiled list. */
    void callList(int id);

    /** Opens a list for compilation. */
    void newList(int id, int mode);

    /** Closes the open list. */
    void endList();
}
