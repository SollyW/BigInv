package com.sollyw.biginv.mixin;

import com.sollyw.biginv.BigInvModInfo;
import com.sollyw.biginv.ScreenHandlerOverrides;
import com.sollyw.biginv.ScreenHandlerExt;
import net.minecraft.registry.Registries;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.village.MerchantInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MerchantScreenHandler.class)
public abstract class MerchantScreenHandlerMixin implements ScreenHandlerExt {
    @ModifyArg(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/village/Merchant;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/screen/MerchantScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;"),
            index = 0)
    private Slot addSlot(Slot par1) {
        if (par1.inventory instanceof MerchantInventory) {
            SlotAccessor slotX = (SlotAccessor) par1;
            slotX.setX(par1.x - 20);
            slotX.setY(par1.y + 81);
        }
        return par1;
    }

    @Override
    public BigInvModInfo biginv$getModInfo() {
        if (this.biginv$getType() != null) {
            return ScreenHandlerOverrides.SCREEN_HANDLER_OVERRIDES.getOrDefault(
                    Registries.SCREEN_HANDLER.getId(this.biginv$getType()),
                    BigInvModInfo.MERCHANT);
        }
        return BigInvModInfo.MERCHANT;
    }
}
