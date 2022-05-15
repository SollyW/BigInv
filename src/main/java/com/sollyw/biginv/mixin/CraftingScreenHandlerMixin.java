package com.sollyw.biginv.mixin;

import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin {
    @ModifyConstant(method = "transferSlot",
            constant = @Constant(intValue = 46))
    private int invEnd(int constant) {
        return 118;
    }

    @ModifyConstant(method = "transferSlot",
            constant = @Constant(intValue = 37))
    private int hotbarStart(int constant) {
        return 100;
    }
}
