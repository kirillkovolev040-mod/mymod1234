package com.example.mymod;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class MyHud {

    @SubscribeEvent
    public void onRenderCrosshairPre(RenderGuiLayerEvent.Pre event) {
        // Если игра пытается отрисовать стандартный прицел
        if (event.getName().equals(VanillaGuiLayers.CROSSHAIR)) {
            int colorId = RightHandConfig.crosshairColorId;
            if (colorId > 0) {
                // ОТМЕНЯЕМ стандартный прицел Майнкрафта, чтобы он не мешал
                event.setCanceled(true);
                
                Minecraft mc = Minecraft.getInstance();
                GuiGraphics graphics = mc.guiGraphics(); // Получаем графический контекст кадра
                if (graphics == null || mc.options.hideGui) return;

                int screenWidth = mc.getWindow().getGuiScaledWidth();
                int screenHeight = mc.getWindow().getGuiScaledHeight();
                int centerX = screenWidth / 2;
                int centerY = screenHeight / 2;

                // Настраиваем выбранный цвет для нашего кастомного прицела
                int colorHex = 0xFFFFFFFF; // Дефолт (Белый)
                if (colorId == 1) colorHex = 0xFF00FF00;      // Зеленый
                else if (colorId == 2) colorHex = 0xFFFF0000; // Красный
                else if (colorId == 3) colorHex = 0xFF0066FF; // Синий
                else if (colorId == 4) colorHex = 0xFFFFD700; // Желтый

                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                // Рисуем аккуратный кастомный PvP-прицел (крестик 4x4) выбранного цвета
                graphics.fill(centerX - 4, centerY, centerX - 1, centerY + 1, colorHex);
                graphics.fill(centerX + 2, centerY, centerX + 5, centerY + 1, colorHex);
                graphics.fill(centerX, centerY - 4, centerX + 1, centerY - 1, colorHex);
                graphics.fill(centerX, centerY + 2, centerX + 1, centerY + 5, colorHex);
                
                // Ставим точку в самом центре
                graphics.fill(centerX, centerY, centerX + 1, centerY + 1, colorHex);
                
                RenderSystem.disableBlend();
            }
        }
    }

    @SubscribeEvent
    public void onRenderGuiPost(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null || mc.player == null || mc.options.hideGui) return;

        GuiGraphics graphics = event.getGuiGraphics();
        int screenWidth = graphics.guiWidth();
        int screenHeight = graphics.guiHeight();

        int leftArmor = screenWidth / 2 + 75; 
        int topArmor = screenHeight - 54; 
        EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        int currentX = leftArmor;

        for (EquipmentSlot slot : slots) {
            ItemStack armorStack = mc.player.getItemBySlot(slot);
            if (!armorStack.isEmpty()) {
                PoseStack poseStack = graphics.pose();
                poseStack.pushPose();
                poseStack.translate(currentX, topArmor, 0.0f);
                poseStack.scale(0.82f, 0.82f, 0.82f);
                graphics.renderItem(armorStack, 0, 0);
                graphics.renderItemDecorations(mc.font, armorStack, 0, 0);
                poseStack.popPose();
                currentX -= 15; 
            }
        }
    }
}
