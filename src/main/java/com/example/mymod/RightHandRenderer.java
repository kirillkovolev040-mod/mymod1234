package com.example.mymod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;

public class RightHandRenderer {
    @SubscribeEvent
    public void onRenderRightHand(RenderHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        ItemStack itemStack = event.getItemStack();
        if (itemStack.isEmpty()) return;

        // ЖЕСТКАЯ ПРОВЕРКА: Проверяем, что рендерится предмет из ПРАВОЙ руки игрока
        ItemStack mainHandItem = mc.player.getMainHandItem();
        if (event.getHand() == InteractionHand.MAIN_HAND && itemStack == mainHandItem) {
            PoseStack poseStack = event.getPoseStack();
            float swingProgress = event.getSwingProgress();
            float rightScaleMultiplier = 1.0f - (RightHandConfig.rightScalePercent / 100.0f);
            
            // ИЗОЛЯЦИЯ МАТРИЦЫ: Замораживаем чистый стек кадра для правой руки
            poseStack.pushPose();
            
            // Применяем настройки расположения и масштаба строго для ПРАВОЙ руки
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
            
            // Закрываем стек. Изменения применятся к правой руке, но полностью сотрутся перед левой!
            poseStack.popPose();
        }
    }
}
