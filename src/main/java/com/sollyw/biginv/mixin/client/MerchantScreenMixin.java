package com.sollyw.biginv.mixin.client;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.Text;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends HandledScreen<MerchantScreenHandler> {
    private MerchantScreenMixin(MerchantScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "<init>",
            at = @At("TAIL"))
    private void init(MerchantScreenHandler handler, PlayerInventory inventory, Text title, CallbackInfo ci) {
        this.backgroundWidth = 238;
    }

    @ModifyConstant(method = "drawLevelInfo",
            constant = @Constant(intValue = 136),
            expect = 3)
    private int levelBarOffsetX(int value) {
        return 116;
    }

    @ModifyConstant(method = "drawLevelInfo",
            constant = @Constant(intValue = 16),
            expect = 3)
    private int levelBarOffsetY(int value) {
        return 96;
    }

    @ModifyArg(method = "drawForeground",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"),
            slice = @Slice(
                    from = @At(value = "INVOKE",
                            target = "Lnet/minecraft/text/Text;shallowCopy()Lnet/minecraft/text/MutableText;"),
                    to = @At(value = "FIELD",
                            target = "Lnet/minecraft/client/gui/screen/ingame/MerchantScreen;playerInventoryTitle:Lnet/minecraft/text/Text;",
                            opcode = Opcodes.GETFIELD)),
            index = 3)
    private float draw(float value) {
        return 86f;
    }

    @Redirect(method = "drawBackground",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/MerchantScreen;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIFFIIII)V",
                    ordinal = 1))
    public void drawTexture0(MatrixStack matrices, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        MerchantScreen.drawTexture(matrices, x - 20, y + 80, z, u, v, width, height, textureWidth, textureHeight);
    }

    @Redirect(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/MerchantScreen;isPointWithinBounds(IIIIDD)Z"))
    private boolean isPointWithinBounds(MerchantScreen instance, int x, int y, int width, int height, double pointX, double pointY) {
        return this.isPointWithinBounds(x - 20, y + 80, width, height, pointX, pointY);
    }

    @Unique
    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        if (super.isClickOutsideBounds(mouseX, mouseY, left, top, button)) return true;
        return mouseX >= left + 105 && mouseY < top + 90;
    }
}
