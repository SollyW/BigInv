package com.sollyw.biginv;

public enum BigInvModStage {
    /**
     * This {@link net.minecraft.screen.ScreenHandler} has not been modified.
     */
    INITIAL,

    /**
     * This {@link net.minecraft.screen.ScreenHandler} is currently being modified.
     */
    MODDING,

    /**
     * This {@link net.minecraft.screen.ScreenHandler} has been modified.
     */
    FINISHED
}
