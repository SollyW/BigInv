package com.sollyw.biginv.mixin.client;

import com.sollyw.biginv.BigInvScreenHelper;
import com.sollyw.biginv.BigInvModInfo;
import com.sollyw.biginv.BigInvModStage;
import com.sollyw.biginv.ScreenHandlerExt;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen {
    @Shadow
    @Final
    protected T handler;

    @Shadow
    protected int x;

    @Shadow
    protected int y;

    @Shadow
    protected int backgroundWidth;

    @Shadow
    protected int backgroundHeight;

    @Shadow
    protected abstract boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button);

    @Shadow
    protected int playerInventoryTitleX;

    @Shadow
    protected int playerInventoryTitleY;

    private HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        ScreenHandlerExt handlerX = (ScreenHandlerExt) this.handler;
        BigInvModInfo info = handlerX.biginv$getModInfo();

        if (info.shouldAlignSlotsToTexture()) {
            handlerX.biginv$positionSlots(
                    info.slotOffsetX().applyTo(Math.min(backgroundWidth - 176, 62)),
                    info.slotOffsetY().applyTo(this.backgroundHeight - 166),
                    info.armourSlotOffsetX().applyTo(62),
                    info.armourSlotOffsetY().applyTo(this.backgroundHeight - 166));
        }

        if (info.shouldAlignInventoryTitle()) {
            this.playerInventoryTitleX = info.inventoryTitleOffsetX().offset();
            this.playerInventoryTitleY = info.inventoryTitleOffsetY().applyTo(this.backgroundHeight - 94);
        } else {
            this.playerInventoryTitleX = info.inventoryTitleOffsetX().applyTo(this.playerInventoryTitleX);
            this.playerInventoryTitleY = info.inventoryTitleOffsetY().applyTo(this.playerInventoryTitleY);
        }
    }

    @Inject(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawBackground(Lnet/minecraft/client/util/math/MatrixStack;FII)V",
                    shift = At.Shift.AFTER))
    private void renderBigInvOverlay(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        BigInvModInfo info = ((ScreenHandlerExt) this.handler).biginv$getModInfo();
        if (info.shouldModBackground()) {
            BigInvScreenHelper.patchScreen(
                    matrices,
                    info.backgroundOffsetX().applyTo(this.x),
                    info.backgroundOffsetY().applyTo(this.y),
                    info.backgroundWidthOffset().applyTo(this.backgroundWidth),
                    info.backgroundHeightOffset().applyTo(this.backgroundHeight),
                    mouseX,
                    mouseY,
                    this.client == null ? null : this.client.player,
                    info.rightmostBehaviour());
        }
    }

    @Redirect(method = {"mouseReleased", "mouseClicked"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;isClickOutsideBounds(DDIII)Z"))
    private boolean isClickOutsideBounds(HandledScreen<T> instance, double mouseX, double mouseY, int left, int top, int button) {
        if (!this.isClickOutsideBounds(mouseX, mouseY, left, top, button)) return false;
        if (((ScreenHandlerExt) this.handler).biginv$getModStage() == BigInvModStage.INITIAL) return true;
        BigInvModInfo info = ((ScreenHandlerExt) this.handler).biginv$getModInfo();

        int left0 = info.backgroundOffsetX().applyTo(left);
        int top0 = info.backgroundOffsetY().applyTo(top);
        int backgroundHeight0 = info.backgroundHeightOffset().applyTo(this.backgroundHeight);
        int backgroundWidth0 = info.backgroundWidthOffset().applyTo(this.backgroundWidth);

        int mainX = backgroundWidth0 - 338;
        if (mainX >= -100) {
            mainX = left0 - 100;
        } else {
            mainX += left0;
        }

        return mouseX < mainX
                || mouseY < top0 + backgroundHeight0 - 166
                || mouseX >= left0 + backgroundWidth0
                || mouseY >= top0 + backgroundHeight0 + 36
                || (mouseX < left0 - 100 && mouseY < top0 + backgroundHeight0 - 90);
    }

    @ModifyArg(method = {"handleHotbarKeyPressed", "onMouseClick(I)V"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V"),
            index = 2)
    private int offhandSlotIndex(Slot slot, int slotId, int button, SlotActionType actionType) {
        return button == 40 ? 112 : button;
    }

    @ModifyConstant(method = {"handleHotbarKeyPressed", "onMouseClick(I)V"},
            constant = @Constant(intValue = 9, ordinal = 0))
    private int hotbarSize(int value) {
        return 18;
    }
}
