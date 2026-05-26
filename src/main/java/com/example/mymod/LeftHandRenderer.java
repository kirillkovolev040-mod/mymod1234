package com.example.mymod;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;

public class LeftHandRenderer {
    @SubscribeEvent
    public void onRenderLeftHand(RenderHandEvent event) {
        // УПРАВЛЕНИЕ СТРОГО ЛЕВОЙ РУКОЙ
        if (event.getHand() == InteractionHand.OFF_HAND) {
            PoseStack poseStack = event.getPoseStack();
            
            // Изолируем только наши кастомные PvP-сдвиги, не ломая ванильное покачивание игры
            poseStack.pushPose();
            
            float leftScaleMultiplier = 1.0f - (RightHandConfig.leftScalePercent / 100.0f);
            
            // Применяем чистые настройки расположения и масштаба для ЛЕВОЙ руки (Клавиша I)
            poseStack.translate(RightHandConfig.leftX, RightHandConfig.leftY, RightHandConfig.leftZ);
            poseStack.scale(leftScaleMultiplier, leftScaleMultiplier, leftScaleMultiplier);
            
            poseStack.popPose();
        }
    }
}
