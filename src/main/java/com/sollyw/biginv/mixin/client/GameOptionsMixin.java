package com.sollyw.biginv.mixin.client;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Shadow
    @Final
    @Mutable
    public KeyBinding[] keysHotbar;

    @Inject(method = "<init>",
            at = @At(value = "FIELD",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/client/option/GameOptions;keysHotbar:[Lnet/minecraft/client/option/KeyBinding;"))
    private void init(CallbackInfo ci) {
        this.keysHotbar = Arrays.copyOf(this.keysHotbar, 18);
        this.keysHotbar[9] = new KeyBinding("key.hotbar.a", GLFW.GLFW_KEY_0, KeyBinding.INVENTORY_CATEGORY);
        this.keysHotbar[10] = new KeyBinding("key.hotbar.b", GLFW.GLFW_KEY_MINUS, KeyBinding.INVENTORY_CATEGORY);
        this.keysHotbar[11] = new KeyBinding("key.hotbar.c", GLFW.GLFW_KEY_EQUAL, KeyBinding.INVENTORY_CATEGORY);
        this.keysHotbar[12] = new KeyBinding("key.hotbar.d", InputUtil.UNKNOWN_KEY.getCode(), KeyBinding.INVENTORY_CATEGORY);
        this.keysHotbar[13] = new KeyBinding("key.hotbar.e", InputUtil.UNKNOWN_KEY.getCode(), KeyBinding.INVENTORY_CATEGORY);
        this.keysHotbar[14] = new KeyBinding("key.hotbar.f", InputUtil.UNKNOWN_KEY.getCode(), KeyBinding.INVENTORY_CATEGORY);
        this.keysHotbar[15] = new KeyBinding("key.hotbar.g", InputUtil.UNKNOWN_KEY.getCode(), KeyBinding.INVENTORY_CATEGORY);
        this.keysHotbar[16] = new KeyBinding("key.hotbar.h", InputUtil.UNKNOWN_KEY.getCode(), KeyBinding.INVENTORY_CATEGORY);
        this.keysHotbar[17] = new KeyBinding("key.hotbar.i", InputUtil.UNKNOWN_KEY.getCode(), KeyBinding.INVENTORY_CATEGORY);
    }
}
