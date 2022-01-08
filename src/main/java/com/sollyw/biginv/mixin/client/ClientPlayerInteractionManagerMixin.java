package com.sollyw.biginv.mixin.client;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    /**
     * @author SollyW
     * @reason Creative inventory is unsupported for now
     */
    @Overwrite
    public boolean hasCreativeInventory() {
        return false;
    }
}
