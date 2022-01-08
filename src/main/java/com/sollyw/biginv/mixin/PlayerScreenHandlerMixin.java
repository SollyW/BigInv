package com.sollyw.biginv.mixin;

import com.sollyw.biginv.BigInvModInfo;
import com.sollyw.biginv.ScreenHandlerOverrides;
import com.sollyw.biginv.ScreenHandlerExt;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

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

    @Override
    public BigInvModInfo biginv$getModInfo() {
        return ScreenHandlerOverrides.SCREEN_HANDLER_OVERRIDES.getOrDefault(PLAYER, BigInvModInfo.PLAYER);
    }
}
