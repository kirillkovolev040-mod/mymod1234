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
                    // 0% — Полная невидимость огня
                    event.setCanceled(true);
                } else {
                    // УЛЬТРА-ФОРМУЛА: Плавно опускает огонь по капле на основе твоих процентов
                    // При 100% сдвиг равен 0.0 (дефолт), при 10% уходит глубоко вниз, оставляя полоску
                    float multiplier = 1.0f - (percent / 100.0f);
                    float shiftY = multiplier * -0.65f;
                    
                    if (percent < 100) {
                        event.getPoseStack().translate(0.0D, (double)shiftY, 0.0D);
                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
