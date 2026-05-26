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
                    // 0% — Полностью отменяем рендер огня, делая экран чистым
                    event.setCanceled(true);
                } else {
                    // ИСПРАВЛЕНО: Плавная прогрессия высоты. 100% - стандартный, 5% - еле заметный внизу
                    float shiftY = -0.5f + ((float) percent / 100.0f) * 0.5f;
                    // Если огонь меньше 100%, уводим матрицу вниз на основе вычислений
                    if (percent < 100) {
                        event.getPoseStack().translate(0.0D, (double)(shiftY - 0.4f), 0.0D);
                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
