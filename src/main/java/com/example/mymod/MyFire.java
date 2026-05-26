package com.example.mymod;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;

public class MyFire {
    @SubscribeEvent
    public void onRenderFire(RenderBlockScreenEffectEvent event) {
        try {
            Object type = event.getOverlayType();
            if (type != null && (type.toString().contains("FIRE") || type.toString().contains("fire"))) {
                // Переводим ползунок процентов из меню J в точные координаты смещения вниз
                float shiftY = -(RightHandConfig.fireHeightPercent / 100.0f);
                event.getPoseStack().translate(0.0D, (double)shiftY, 0.0D); 
            }
        } catch (Exception ignored) {}
    }
}
