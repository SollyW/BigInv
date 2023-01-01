package com.sollyw.biginv.mixin;

import com.sollyw.biginv.BigInvModInfo;
import com.sollyw.biginv.ScreenHandlerOverrides;
import com.sollyw.biginv.ScreenHandlerExt;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin implements ScreenHandlerExt {
    @Unique
    private static final Identifier PLAYER = new Identifier("minecraft", "player");

    /**
     * @author SollyW
     * @reason Account for larger hotbar
     */
    @Overwrite
    public static boolean isInHotbar(int slot) {
        return slot >= 108 && slot <= 126;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 9,
                            ordinal = 1),
                    @Constant(intValue = 9,
                            ordinal = 3),
                    @Constant(intValue = 9,
                            ordinal = 4),
                    @Constant(intValue = 9,
                            ordinal = 5),
                    @Constant(intValue = 9,
                            ordinal = 6)})
    private int mainInvStart(int value) {
        return 5;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 36,
                            ordinal = 0),
                    @Constant(intValue = 36,
                            ordinal = 1),
                    @Constant(intValue = 36,
                            ordinal = 2),
                    @Constant(intValue = 36,
                            ordinal = 3)})
    private int mainInvEndHotbarStart(int value) {
        return 95;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 45,
                            ordinal = 1),
                    @Constant(intValue = 45,
                            ordinal = 2),
                    @Constant(intValue = 5,
                            ordinal = 1),
                    @Constant(intValue = 45,
                            ordinal = 5),
                    @Constant(intValue = 45,
                            ordinal = 6),
                    @Constant(intValue = 45,
                            ordinal = 7)})
    private int hotbarEndArmourStart(int value) {
        return 113;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 8,
                            ordinal = 0),
                    @Constant(intValue = 8,
                            ordinal = 1)})
    private int armourLast(int value) {
        return 116;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 9,
                            ordinal = 2),
                    @Constant(intValue = 45,
                            ordinal = 3),
                    @Constant(intValue = 45,
                            ordinal = 4)})
    private int armourEndOffhandStart(int value) {
        return 117;
    }

    @ModifyConstant(method = "quickMove",
            constant = @Constant(intValue = 46,
                            ordinal = 0))
    private int offhandEnd(int value) {
        return 118;
    }

    @Override
    public BigInvModInfo biginv$getModInfo() {
        return ScreenHandlerOverrides.SCREEN_HANDLER_OVERRIDES.getOrDefault(PLAYER, BigInvModInfo.PLAYER);
    }
}
