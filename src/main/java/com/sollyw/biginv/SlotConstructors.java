package com.sollyw.biginv;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

/**
 * Functions to create instances of the anonymous classes in {@link net.minecraft.screen.PlayerScreenHandler}
 * to allow other ScreenHandlers to use them rather than copypasting
 */
public class SlotConstructors {
    public static ArmourSlot ARMOUR;
    public static OffhandSlot OFFHAND;

    static {
        try {
            /*
             Load the classes to ensure the static constructor has been called,
             and the above constants are not null
             */
            Class.forName("net.minecraft.screen.PlayerScreenHandler$1");
            Class.forName("net.minecraft.screen.PlayerScreenHandler$2");
        } catch (ClassNotFoundException e) {
            BigInv.LOGGER.error("Poking PlayerScreenHandler anonymous class failed");
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface ArmourSlot {
        Slot init(Inventory inventory, int index, int x, int y, EquipmentSlot equipmentSlot);
    }

    @FunctionalInterface
    public interface OffhandSlot {
        Slot init(Inventory inventory, int index, int x, int y);
    }
}
