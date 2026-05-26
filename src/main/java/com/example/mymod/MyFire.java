package com.example.mymod;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;

public class MyFire {
    @SubscribeEvent
    public void onRenderFire(RenderBlockScreenEffectEvent event) {
        try {
            Object type = event.getOverlayType();
            if (type != null && (type.toString().contains("FIRE") || type.toString().contains("fire"))) {
                int percent = RightHandConfig.fireHeightPercent;
                
                if (percent == 0) {
                    // 0% — Огонь полностью выключен, экран абсолютно чистый
                    event.setCanceled(true);
                } else {
                    // ПОДКОРРЕКТИРОВАНО: Смягчили шаг смещения.
                    // Теперь текстура не улетает под экран на 30%, а плавно доходит до самого низа к 5%
                    float multiplier = 1.0f - (percent / 100.0f);
                    float shiftY = multiplier * -0.42f; 
                    
                    if (percent < 100) {
                        event.getPoseStack().translate(0.0D, (double)shiftY, 0.0D);
                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
