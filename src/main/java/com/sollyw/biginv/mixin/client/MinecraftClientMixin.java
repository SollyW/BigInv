package com.sollyw.biginv.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @ModifyConstant(method = "handleInputEvents",
            constant = @Constant(intValue = 9, ordinal = 0))
    private int hotbarSize(int value) {
        return 18;
    }

    @ModifyArg(method = "doItemPick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;clickCreativeStack(Lnet/minecraft/item/ItemStack;I)V"),
            index = 1)
    public int pickSlot(int value) {
        return value + 59; // Don't ask
    }
}
