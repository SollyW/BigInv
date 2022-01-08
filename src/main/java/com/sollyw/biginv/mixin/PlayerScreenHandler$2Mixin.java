package com.sollyw.biginv.mixin;

import com.sollyw.biginv.SlotConstructors;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("unused")
@Mixin(targets = "net/minecraft/screen/PlayerScreenHandler$2")
public class PlayerScreenHandler$2Mixin {
    private PlayerScreenHandler$2Mixin(PlayerScreenHandler playerScreenHandler, Inventory inventory, int i, int j, int k) {
        throw new IllegalStateException();
    }

    static {
        SlotConstructors.OFFHAND = ((inventory, index, x, y) ->
                (Slot) (Object) new PlayerScreenHandler$2Mixin(null, inventory, index, x, y));
    }
}
