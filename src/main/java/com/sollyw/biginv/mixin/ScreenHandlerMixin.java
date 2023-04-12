package com.sollyw.biginv.mixin;

import com.sollyw.biginv.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.sollyw.biginv.BigInvModStage.*;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin implements ScreenHandlerExt {
    @Shadow
    protected abstract Slot addSlot(Slot slot);

    @Shadow
    @Final
    @Nullable
    private ScreenHandlerType<?> type;

    @Unique
    private BigInvModStage stage = INITIAL;

    @Unique
    @Nullable
    private Slot[] bigInvSlots;

    // This is where vanilla slots are removed, and the slots for biginv are added
    @Inject(method = "addSlot",
            at = @At("HEAD"),
            cancellable = true)
    private void addSlot(Slot slot, CallbackInfoReturnable<Slot> cir) {
        if (this.stage != MODDING && slot.inventory instanceof PlayerInventory playerInventory) {
            boolean cancel = true;

            if (this.stage == INITIAL) {
                BigInvModInfo info = this.biginv$getModInfo();
                cancel = info.shouldRemoveVanillaSlots();
                if (info.shouldAddSlots() && slot.getIndex() == 0) {
                    this.bigInvSlots = BigInvScreenHandlerHelper.createSlots(
                            (ScreenHandler) (Object) this,
                            playerInventory);

                    // Slot index zero, the leftmost hotbar slot, is used to align the new slots
                    BigInvScreenHandlerHelper.positionSlots(this.bigInvSlots,
                            info.slotOffsetX().applyTo(slot.x - 8),
                            info.slotOffsetY().applyTo(slot.y - 142),
                            info.armourSlotOffsetX().applyTo(slot.x - 8),
                            info.armourSlotOffsetY().applyTo(slot.y - 142));
                }

                if (info.shouldEmulateVanillaSlots()) {
                    ((SlotAccessor) slot).setIndex(offsetSlotId(slot.getIndex()));
                }
            }

            if (cancel) cir.setReturnValue(slot);
        }
    }

    @Override
    public void biginv$setModStage(BigInvModStage value) {
        this.stage = value;
    }

    @Override
    public BigInvModStage biginv$getModStage() {
        return stage;
    }

    @Override
    public BigInvModInfo biginv$getModInfo() {
        if (this.type != null) {
            return ScreenHandlerOverrides.SCREEN_HANDLER_OVERRIDES.getOrDefault(
                    Registries.SCREEN_HANDLER.getId(this.type),
                    BigInvModInfo.DEFAULT);
        }

        return BigInvModInfo.DEFAULT;
    }

    @Override
    public Slot biginv$addSlot(Slot slot) {
        return this.addSlot(slot);
    }

    @Override
    public void biginv$positionSlots(int offsetX, int offsetY, int armourOffsetX, int armourOffsetY) {

        if (this.bigInvSlots != null) {
            BigInvScreenHandlerHelper.positionSlots(this.bigInvSlots, offsetX, offsetY, armourOffsetX, armourOffsetY);
        }
    }

    @Override
    public ScreenHandlerType<?> biginv$getType() {
        return this.type;
    }

    private static int offsetSlotId(int slot) {
        if (slot < 9) return slot;
        else if (slot < 36) return slot + 9;
        else return slot + 72;
    }
}
