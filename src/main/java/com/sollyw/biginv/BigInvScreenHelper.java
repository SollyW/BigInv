package com.sollyw.biginv;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sollyw.biginv.BigInvModInfo.RightmostBehaviour;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class BigInvScreenHelper {
    public static final Identifier MOD_BACKGROUND = BigInv.id("textures/gui/inventory_mod_background.png");
    public static final Identifier BIG_HOTBAR = BigInv.id("textures/gui/big_hotbar.png");

    public static void patchScreen(MatrixStack matrices, int x, int y, int backgroundWidth, int backgroundHeight,
                                   int mouseX, int mouseY, LivingEntity entity, RightmostBehaviour rightmostBehaviour) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, MOD_BACKGROUND);

        // Bottom right block
        if (rightmostBehaviour == RightmostBehaviour.EXTEND_FROM_RIGHT) {
            DrawableHelper.drawTexture(matrices,
                    x + backgroundWidth - 320,
                    y + backgroundHeight - 83,
                    192,
                    0,
                    320,
                    119,
                    512,
                    256);
        }

        int mainX = backgroundWidth - 338;
        boolean aligned;
        if (mainX >= -100) {
            aligned = true;
            mainX = x - 100;
        } else {
            aligned = false;
            mainX += x;
        }

        // Main inventory segment
        DrawableHelper.drawTexture(matrices,
                mainX,
                y + backgroundHeight - 83,
                0,
                137,
                rightmostBehaviour == RightmostBehaviour.CAP ? 338 : 331,
                119,
                512,
                256);

        // Upper inventory (armour slots etc.)
        DrawableHelper.drawTexture(matrices,
                x - 100,
                y + backgroundHeight - 166,
                339,
                120,
                104,
                83,
                512,
                256);

        {
            int u, width;
            if (aligned) {
                u = 269;
                width = 7;
            } else {
                u = 0;
                width = 238 - backgroundWidth;
            }

            // Piece connecting the upper inventory to the main invenrory
            DrawableHelper.drawTexture(matrices,
                    mainX,
                    y + backgroundHeight - 90,
                    u,
                    129,
                    width,
                    7,
                    512,
                    256);
        }

        // Block on top of the base gui, for short guis such as the hopper
        if (backgroundHeight < 166) {
            DrawableHelper.drawTexture(matrices,
                    x,
                    y + backgroundHeight - 166,
                    191 - backgroundWidth,
                    0,
                    backgroundWidth,
                    170 - backgroundHeight,
                    512,
                    256);

        } else if (backgroundHeight > 166) {
            int xPos = x;
            int width = 4;
            if (backgroundHeight == 167) {
                xPos += 2;
                width = 3;
            } else if (backgroundHeight == 168) {
                xPos += 1;
                width = 3;
            }

            // Piece connecting the upper inventory to the base gui when the base gui is taller
            DrawableHelper.drawTexture(matrices,
                    xPos,
                    y + backgroundHeight - 166,
                    444,
                    120,
                    width,
                    4,
                    512,
                    256);
        }

        if (entity != null) {
            int x0 = x - 49;
            int y0 = y + backgroundHeight - 91;
            InventoryScreen.drawEntity(matrices,
                    x0,
                    y0,
                    30,
                    (float)(x0 - mouseX),
                    (float)(y0 - 50 - mouseY),
                    entity);
        }
    }
}
