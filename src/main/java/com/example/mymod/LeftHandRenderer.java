package com.example.mymod;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;

public class LeftHandRenderer {
    @SubscribeEvent
    public void onRenderLeftHand(RenderHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        ItemStack itemStack = event.getItemStack();
        if (itemStack.isEmpty()) return;

        // ЖЕСТКАЯ ПРОВЕРКА: Проверяем, что рендерится предмет из ЛЕВОЙ руки (оффхенда)
        ItemStack offHandItem = mc.player.getOffhandItem();
        if (event.getHand() == InteractionHand.OFF_HAND || itemStack == offHandItem) {
            PoseStack poseStack = event.getPoseStack();
            float leftScaleMultiplier = 1.0f - (RightHandConfig.leftScalePercent / 100.0f);
            
            // ИЗОЛЯЦИЯ МАТРИЦЫ: Замораживаем чистый стек кадра для левой руки
            poseStack.pushPose();
            
            // Применяем настройки расположения и масштаба строго для ЛЕВОЙ руки (Клавиша I)
            poseStack.translate(RightHandConfig.leftX, RightHandConfig.leftY, RightHandConfig.leftZ);
            poseStack.scale(leftScaleMultiplier, leftScaleMultiplier, leftScaleMultiplier);
            
            // Очищаем матрицу левой руки, чтобы не сломать рендер остального интерфейса
            poseStack.popPose();
        }
    }
}
