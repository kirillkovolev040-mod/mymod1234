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
            
            // АНТИ-БАГ МАТРИЦЫ: Принудительно вычитаем правые настройки, если они застряли в памяти
            float rightScaleMultiplier = 1.0f - (RightHandConfig.rightScalePercent / 100.0f);
            if (rightScaleMultiplier > 0.0f) {
                poseStack.scale(1.0f / rightScaleMultiplier, 1.0f / rightScaleMultiplier, 1.0f / rightScaleMultiplier);
            }
            poseStack.translate(-RightHandConfig.rightX, -RightHandConfig.rightY, -RightHandConfig.rightZ);
            
            // ТЕПЕРЬ НАКЛАДЫВАЕМ ЧИСТЫЕ ЛЕВЫЕ НАСТРОЙКИ (Клавиша I)
            float leftScaleMultiplier = 1.0f - (RightHandConfig.leftScalePercent / 100.0f);
            poseStack.translate(RightHandConfig.leftX, RightHandConfig.leftY, RightHandConfig.leftZ);
            poseStack.scale(leftScaleMultiplier, leftScaleMultiplier, leftScaleMultiplier);
        }
    }
}
