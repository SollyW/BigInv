package com.sollyw.biginv.mixin;

import com.sollyw.biginv.SlotConstructors;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("unused")
@Mixin(targets = "net/minecraft/screen/PlayerScreenHandler$1")
public class PlayerScreenHandler$1Mixin {
    private PlayerScreenHandler$1Mixin(PlayerScreenHandler playerScreenHandler, Inventory inventory, int i, int j, int k, EquipmentSlot equipmentSlot) {
        throw new IllegalStateException();
    }

    static {
        SlotConstructors.ARMOUR = ((inventory, index, x, y, equipmentSlot) ->
                (Slot) (Object) new PlayerScreenHandler$1Mixin(null, inventory, index, x, y, equipmentSlot));
    }
}
