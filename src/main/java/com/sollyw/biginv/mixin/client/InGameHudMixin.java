package com.sollyw.biginv.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sollyw.biginv.BigInvScreenHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    protected abstract void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed);

    @Shadow
    private int scaledWidth;

    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    @Shadow
    @Final
    private static Identifier WIDGETS_TEXTURE;

    @Redirect(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
                    ordinal = 0))
    private void renderHotbarItem(InGameHud instance, int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed) {
        int n2 = (x + 88 - this.scaledWidth / 2) / 20;
        this.renderHotbarItem(x - 90,
                y,
                tickDelta,
                player,
                this.getCameraPlayer().getInventory().main.get(n2),
                seed);

        this.renderHotbarItem(x + 90,
                y,
                tickDelta,
                player,
                this.getCameraPlayer().getInventory().main.get(n2 + 9),
                seed + 10);
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
                    ordinal = 1),
            index = 0)
    private int moveOffhandLeft(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed) {
        return x - 90;
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
                    ordinal = 2),
            index = 0)
    private int moveOffhandRight(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed) {
        return x + 90;
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V",
                    ordinal = 2),
            index = 1)
    private int moveOffhandItemLeft(MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
        return x - 90;
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V",
                    ordinal = 3),
            index = 1)
    private int moveOffhandItemRight(MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
        return x + 90;
    }

    @Redirect(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V",
                    ordinal = 0))
    private void drawTexture(InGameHud instance, MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
        RenderSystem.setShaderTexture(0, BigInvScreenHelper.BIG_HOTBAR);
        DrawableHelper.drawTexture(matrices, x - 90, y, instance.getZOffset(), 0, 0, width << 1, height, 512, 32);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V",
                    ordinal = 1), index = 1)
    private int moveSelectionOutline(MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
        return x - 90;
    }
}
