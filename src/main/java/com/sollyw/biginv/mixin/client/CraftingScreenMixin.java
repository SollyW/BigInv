package com.sollyw.biginv.mixin.client;

import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin {
    @Shadow
    private boolean narrow;

    @Inject(method = "init",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/ingame/CraftingScreen;narrow:Z",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER))
    private void init(CallbackInfo ci) {
        this.narrow = true;
    }

    @Redirect(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/CraftingScreen;drawBackground(Lnet/minecraft/client/util/math/MatrixStack;FII)V"),
            allow = 1)
    private void drawBackground(CraftingScreen instance, MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // nom
    }
}
