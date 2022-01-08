package com.sollyw.biginv.mixin;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @ModifyConstant(method = {"onCreativeInventoryAction"},
            constant = @Constant(intValue = 45))
    private int change(int value) {
        return value + 72;
    }
}
