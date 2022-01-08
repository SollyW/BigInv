package com.sollyw.biginv.mixin;

import com.sollyw.biginv.BigInvModInfo;
import com.sollyw.biginv.ScreenHandlerOverrides;
import com.sollyw.biginv.ScreenHandlerExt;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GenericContainerScreenHandler.class)
public abstract class GenericContainerScreenHandlerMixin implements ScreenHandlerExt {
    @Override
    public BigInvModInfo biginv$getModInfo() {
        if (this.biginv$getType() != null) {
            return ScreenHandlerOverrides.SCREEN_HANDLER_OVERRIDES.getOrDefault(
                    Registry.SCREEN_HANDLER.getId(this.biginv$getType()),
                    BigInvModInfo.GENERIC_CONTAINER);
        }
        return BigInvModInfo.GENERIC_CONTAINER;
    }
}
