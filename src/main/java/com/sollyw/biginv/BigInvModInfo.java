package com.sollyw.biginv;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;

/**
 * Parameters to customise the modification behaviour for a {@link ScreenHandler}
 * and any {@link HandledScreen}s using it.
 *
 * @param shouldAddSlots if true, the extra slots are added to the handler. Can be set to false if you want to
 *                       add them manually.
 *
 * @param shouldRemoveVanillaSlots if true, other slots bound to a {@link PlayerInventory} are unable to be
 *                                 added to the handler. This should be true if {@link BigInvModInfo#shouldAddSlots}
 *                                 is true to prevent slots from being present twice.
 *
 * @param shouldEmulateVanillaSlots if true, the vanilla slots are modified to emulate the vanilla inventory size using
 *                            the leftmost 9 hotbar slots and the first 27 slots of the inventory. The extra slots
 *                            will be inaccessible from within this handler. If this is true,
 *                            {@link BigInvModInfo#shouldAddSlots} and {@link BigInvModInfo#shouldRemoveVanillaSlots}}
 *                            must both be false.
 *
 * @param shouldAlignSlotsToTexture if true, the slots in the handler are aligned to the texture of the screen
 *                                  instead of the leftmost hotbar slot.
 *
 * @param slotOffsetX the x offset for slots added to the handler. This is usually detected automatically with
 *                    {@link IntOffset#NONE}, and should only be changed if the slots are in the wrong place.
 *
 * @param slotOffsetY the y offset for slots added to the handler. This is usually detected automatically with
 *                    {@link IntOffset#NONE}, and should only be changed if the slots are in the wrong place.
 *
 * @param armourSlotOffsetX the x offset for armour slots added to the handler. This is usually detected automatically
 *                          with {@link IntOffset#NONE}, and should only be changed if the slots are in the wrong
 *                          place.
 *
 * @param armourSlotOffsetY the y offset for armour slots added to the handler. This is usually detected automatically
 *                          with {@link IntOffset#NONE}, and should only be changed if the slots are in the wrong
 *                          place.
 *
 * @param shouldAlignInventoryTitle if true, the player inventory title is aligned to the patched texture. If false,
 *                                  the title is kept in (or shifted relative to) its original location.
 *
 * @param inventoryTitleOffsetX the x offset for the inventory title. This is usually detected automatically
 *                              with {@link IntOffset#NONE}, and should only be changed if the title is in the wrong
 *                              place.
 *
 * @param inventoryTitleOffsetY the y offset for the inventory title. This is usually detected automatically
 *                              with {@link IntOffset#NONE}, and should only be changed if the title is in the wrong
 *                              place.
 *
 * @param shouldModBackground if true, the screen background texture is modified using a vanilla art style patch
 *                            to fit the extra slots. Set to false to use a custom background.
 *
 * @param rightmostBehaviour how the screen background texture patching should treat the space to the right
 *                           of the inventory slots
 *
 * @param backgroundOffsetX the x offset for the screen background texture patch.
 *                          This is usually detected automatically with {@link IntOffset#NONE},
 *                          and should only be changed if the texture is in the wrong place.
 *
 * @param backgroundOffsetY the y offset for the screen background texture patch.
 *                          This is usually detected automatically with {@link IntOffset#NONE},
 *                          and should only be changed if the texture is in the wrong place.
 *
 * @param backgroundWidthOffset the offset for the width the screen background texture patch perceives the screen
 *                              to be. This is usually detected automatically with {@link IntOffset#NONE},
 *                              and should only be changed if the texture is in the wrong place.
 *
 * @param backgroundHeightOffset the offset for the height the screen background texture patch perceives the screen
 *                               to be. This is usually detected automatically with {@link IntOffset#NONE},
 *                               and should only be changed if the texture is in the wrong place.
 */
public record BigInvModInfo(
        boolean shouldAddSlots,
        boolean shouldRemoveVanillaSlots,
        boolean shouldEmulateVanillaSlots,
        boolean shouldAlignSlotsToTexture,
        IntOffset slotOffsetX,
        IntOffset slotOffsetY,
        IntOffset armourSlotOffsetX,
        IntOffset armourSlotOffsetY,
        boolean shouldAlignInventoryTitle,
        IntOffset inventoryTitleOffsetX,
        IntOffset inventoryTitleOffsetY,
        boolean shouldModBackground,
        RightmostBehaviour rightmostBehaviour,
        IntOffset backgroundOffsetX,
        IntOffset backgroundOffsetY,
        IntOffset backgroundWidthOffset,
        IntOffset backgroundHeightOffset
) {
    public static final BigInvModInfo DEFAULT = new BigInvModInfo(
            true,
            true,
            false,
            true,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE,
            true,
            IntOffset.NONE,
            IntOffset.NONE,
            true,
            RightmostBehaviour.EXTEND_FROM_RIGHT,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE);

    public static final BigInvModInfo GENERIC_CONTAINER = new BigInvModInfo(
            true,
            true,
            false,
            true,
            IntOffset.NONE,
            IntOffset.NEGATIVE_ONE,
            IntOffset.NONE,
            IntOffset.NEGATIVE_ONE,
            true,
            IntOffset.NONE,
            IntOffset.NEGATIVE_ONE,
            true,
            RightmostBehaviour.CAP,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NEGATIVE_ONE);

    public static final BigInvModInfo PLAYER = new BigInvModInfo(
            true,
            true,
            false,
            true,
            IntOffset.NONE,
            IntOffset.NONE,
            new IntOffset(false, 90),
            IntOffset.NONE,
            false,
            IntOffset.NONE,
            IntOffset.NONE,
            true,
            RightmostBehaviour.CAP,
            new IntOffset(false, 90),
            IntOffset.NONE,
            new IntOffset(false, -90),
            IntOffset.NONE);

    public static final BigInvModInfo MERCHANT = new BigInvModInfo(
            true,
            true,
            false,
            true,
            IntOffset.NONE,
            new IntOffset(false, 80),
            IntOffset.NONE,
            new IntOffset(false, 80),
            true,
            new IntOffset(false, 108),
            new IntOffset(false, 80),
            true,
            RightmostBehaviour.CAP,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE,
            new IntOffset(false, 80));

    public static final BigInvModInfo EMULATE = new BigInvModInfo(
            false,
            false,
            true,
            false,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE,
            false,
            IntOffset.NONE,
            IntOffset.NONE,
            false,
            RightmostBehaviour.NONE,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE,
            IntOffset.NONE);

    public enum RightmostBehaviour {
        /**
         * The screen background texture patch will cover up everything
         * to the right of the inventory slots, with an outer edge
         * on the far right
         */
        EXTEND_FROM_RIGHT,

        /**
         * The screen background texture patch will draw an outer edge
         * immediately to the right of the inventory slots
         */
        CAP,

        /**
         * No texture is added to the right of the inventory slots
         */
        @SuppressWarnings("unused")
        NONE
    }
}
