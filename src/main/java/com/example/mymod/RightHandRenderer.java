package com.example.mymod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;

public class RightHandRenderer {
    @SubscribeEvent
    public void onRenderRightHand(RenderHandEvent event) {
        // УПРАВЛЕНИЕ СТРОГО ПРАВОЙ РУКОЙ
        if (event.getHand() == InteractionHand.MAIN_HAND) {
            PoseStack poseStack = event.getPoseStack();
            float swingProgress = event.getSwingProgress();
            float rightScaleMultiplier = 1.0f - (RightHandConfig.rightScalePercent / 100.0f);
            
            // ИЗОЛЯЦИЯ КАДРА: Запоминаем чистую матрицу игры
            poseStack.pushPose();
            
            // Применяем настройки расположения и масштаба для правой руки
            poseStack.translate(RightHandConfig.rightX, RightHandConfig.rightY, RightHandConfig.rightZ);
            poseStack.scale(rightScaleMultiplier, rightScaleMultiplier, rightScaleMultiplier);
            
            // Анимации атаки правой руки
            if (swingProgress > 0.0f && RightHandConfig.swingMode > 0) {
                float f = Mth.sin(swingProgress * (float)Math.PI);
                float f1 = Mth.sin(Mth.sqrt(swingProgress) * (float)Math.PI);
                
                if (RightHandConfig.swingMode == 1) {
                    poseStack.translate(0.0f, f * 0.08f, 0.0f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(f1 * -18.0f));
                    poseStack.mulPose(Axis.YP.rotationDegrees(f1 * -12.0f));
                } 
                else if (RightHandConfig.swingMode == 2) {
                    poseStack.translate(f1 * -0.15f, 0.0f, f * 0.05f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(f1 * -45.0f));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(f1 * -10.0f));
                } 
                else if (RightHandConfig.swingMode == 3) {
                    poseStack.translate(0.0f, f1 * -0.2f, f * 0.1f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(f1 * -35.0f));
                }
            }
        } else if (event.getHand() == InteractionHand.OFF_HAND) {
            // Если событие вызвано для левой руки, но в этом стеке остался «мусор» от правой руки,
            // мы принудительно возвращаем состояние матрицы назад!
            try {
                event.getPoseStack().popPose();
            } catch (Exception ignored) {}
        }
    }
}
