package com.sollyw.biginv;

import com.sollyw.biginv.mixin.SlotAccessor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BigInvScreenHandlerHelper {
    public static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET};

    public static Slot[] createSlots(ScreenHandler handler, PlayerInventory playerInventory) {
        ScreenHandlerExt handlerX = (ScreenHandlerExt) handler;
        Slot[] slots = new Slot[113];

        handlerX.biginv$setModStage(BigInvModStage.MODDING);

        for(int i = 0; i < 90; ++i) {
            int index = i + 18;
            slots[index] = handlerX.biginv$addSlot(new Slot(
                    playerInventory,
                    index,
                    -9999,
                    -9999));
        }

        for(int i = 0; i < 18; ++i) {
            slots[i] = handlerX.biginv$addSlot(new Slot(
                    playerInventory,
                    i,
                    -9999,
                    -9999));
        }

        slots[112] = handlerX.biginv$addSlot(SlotConstructors.OFFHAND.init(
                playerInventory,
                112,
                -9999,
                -9999));

        for(int i = 0; i < 4; ++i) {
            int index = 111 - i;
            slots[index] = handlerX.biginv$addSlot(SlotConstructors.ARMOUR.init(
                    playerInventory,
                    index,
                    -9999,
                    -9999,
                    EQUIPMENT_SLOT_ORDER[i]
            ));
        }

        handlerX.biginv$setModStage(BigInvModStage.FINISHED);
        return slots;
    }

    public static void positionSlots(Slot[] slots, int offsetX, int offsetY, int armourOffsetX, int armourOffsetY) {
        for(int row = 0; row < 5; ++row) {
            for(int col = 0; col < 18; ++col) {
                move(slots,
                        col + row * 18 + 18,
                        col * 18 - 154 + offsetX,
                        84 + row * 18 + offsetY);
            }
        }

        for(int col = 0; col < 18; ++col) {
            move(slots,
                    col,
                    col * 18 - 154 + offsetX,
                    offsetY + 178);
        }

        move(slots,
                112,
                armourOffsetX - 85,
                armourOffsetY + 62);

        for(int row = 0; row < 4; ++row) {
            move(slots,
                    111 - row,
                    armourOffsetX - 154,
                    armourOffsetY + 8 + row * 18);
        }
    }

    private static void move(Slot[] slots, int index, int x, int y) {
        SlotAccessor slotX = (SlotAccessor) slots[index];
        slotX.setX(x);
        slotX.setY(y);
    }
}
