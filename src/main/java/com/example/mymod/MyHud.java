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
        // ИСПРАВЛЕНО: Используем правильный класс VanillaGuiLayers для версии 1.21.4
        if (event.getName().equals(VanillaGuiLayers.CROSSHAIR)) {
            int colorId = RightHandConfig.crosshairColorId;
            if (colorId > 0) {
                // Магия PvP-клиентов: отключаем инверсию цветов Майнкрафта, чтобы прицел красился!
                RenderSystem.blendFunc(com.mojang.blaze3d.platform.GlStateManager.SourceFactor.SRC_ALPHA, com.mojang.blaze3d.platform.GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                
                if (colorId == 1) { RenderSystem.setShaderColor(0.0f, 1.0f, 0.0f, 1.0f); }      // Зеленый
                else if (colorId == 2) { RenderSystem.setShaderColor(1.0f, 0.0f, 0.0f, 1.0f); } // Красный
                else if (colorId == 3) { RenderSystem.setShaderColor(0.0f, 0.4f, 1.0f, 1.0f); } // Синий
                else if (colorId == 4) { RenderSystem.setShaderColor(1.0f, 1.0f, 0.0f, 1.0f); } // Желтый
            }
        }
    }

    @SubscribeEvent
    public void onRenderCrosshairPost(RenderGuiLayerEvent.Post event) {
        if (event.getName().equals(VanillaGuiLayers.CROSSHAIR)) {
            // Возвращаем все стандартные настройки рендера назад
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.defaultBlendFunc();
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
