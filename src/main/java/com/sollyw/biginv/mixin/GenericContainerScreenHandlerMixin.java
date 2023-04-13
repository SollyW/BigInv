package com.sollyw.biginv.mixin;

import com.sollyw.biginv.BigInvModInfo;
import com.sollyw.biginv.ScreenHandlerOverrides;
import com.sollyw.biginv.ScreenHandlerExt;
import net.minecraft.registry.Registries;
import net.minecraft.screen.GenericContainerScreenHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GenericContainerScreenHandler.class)
public abstract class GenericContainerScreenHandlerMixin implements ScreenHandlerExt {
    @Override
    public BigInvModInfo biginv$getModInfo() {
        if (this.biginv$getType() != null) {
            return ScreenHandlerOverrides.SCREEN_HANDLER_OVERRIDES.getOrDefault(
                    Registries.SCREEN_HANDLER.getId(this.biginv$getType()),
                    BigInvModInfo.GENERIC_CONTAINER);
        }
        return BigInvModInfo.GENERIC_CONTAINER;
    }
}
