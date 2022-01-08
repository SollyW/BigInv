package com.sollyw.biginv;

import com.sollyw.biginv.mixin.ScreenHandlerMixin;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

/**
 * Duck for methods implemented in {@link ScreenHandlerMixin}
 */
public interface ScreenHandlerExt {
    void biginv$setModStage(BigInvModStage value);

    BigInvModStage biginv$getModStage();

    /**
     * Override this to customise modification behaviour for a {@link ScreenHandler}
     * and any {@link HandledScreen}s using it.
     * Not overriding this will fall back to user config or defaults.
     * It's recommended to store a BigInvModInfo in a constant and return it,
     * as this gets called quite often.
     * @return the modification parameters
     *
     */
    BigInvModInfo biginv$getModInfo();

    Slot biginv$addSlot(Slot slot);

    void biginv$positionSlots(int offsetX, int offsetY, int armourOffsetX, int armourOffsetY);

    ScreenHandlerType<?> biginv$getType();
}
