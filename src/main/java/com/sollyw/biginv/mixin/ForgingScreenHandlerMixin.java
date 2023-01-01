package com.sollyw.biginv.mixin;

import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ForgingScreenHandler.class)
public abstract class ForgingScreenHandlerMixin {
    @ModifyConstant(method = "quickMove",
            constant = @Constant(intValue = 39))
    private int value(int constant) {
        return 111;
    }
}
