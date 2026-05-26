package com.example.mymod;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;

public class LeftHandRenderer {
    @SubscribeEvent
    public void onRenderLeftHand(RenderHandEvent event) {
        // УПРАВЛЕНИЕ СТРОГО ЛЕВОЙ РУКОЙ (Игнорирует любые правые команды K)
        if (event.getHand() == InteractionHand.OFF_HAND) {
            PoseStack poseStack = event.getPoseStack();
            
            poseStack.pushPose(); // Замораживаем изолированную матрицу для левой руки
            
            float leftScaleMultiplier = 1.0f - (RightHandConfig.leftScalePercent / 100.0f);
            
            // Применяем настройки расположения и масштаба строго для ЛЕВОЙ руки (Клавиша I)
            poseStack.translate(RightHandConfig.leftX, RightHandConfig.leftY, RightHandConfig.leftZ);
            poseStack.scale(leftScaleMultiplier, leftScaleMultiplier, leftScaleMultiplier);
            
            poseStack.popPose(); // Очищаем стек кадра
        }
    }
}
