package com.example.mymod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MyArmor { }

// МЕНЮ ТОЛЬКО ДЛЯ ПРАВОЙ РУКИ (Клавиша K)
class RightConfigScreen extends Screen {
    public RightConfigScreen() { super(Component.literal("Настройка ПРАВОЙ руки")); }

    // УБИРАЕМ МЫЛО: Переопределяем метод, чтобы размытие не рендерилось
    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Вместо размытия рисуем обычное чистое затемнение, чтобы видеть руки без мыла
        graphics.fill(0, 0, this.width, this.height, 0x60000000);
    }

    private void drawCustomButton(GuiGraphics g, String text, int x, int y, int w, int h, int mx, int my) {
        boolean hovered = mx >= x && mx <= x + w && my >= y && my <= y + h;
        g.fill(x, y, x + w, y + h, hovered ? 0xEE777777 : 0xEE444444);
        g.drawCenteredString(this.font, text, x + w / 2, y + (h - 8) / 2, 0xFFFFFFFF);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, "Нажмите ESC для возврата в игру", this.width / 2, this.height / 2 + 110, 0xAAAAAA);
        int cy = this.height / 2; int cx = this.width / 2 - 60;  
        graphics.drawCenteredString(this.font, "===[ НАСТРОЙКА ПРАВОЙ РУКИ (K) ]===", this.width / 2, cy - 95, 0xFF5555);
        
        drawCustomButton(graphics, "↑ Выше", cx, cy - 75, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "↓ Ниже", cx, cy - 50, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "→ Дальше", cx, cy - 25, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "← Ближе", cx, cy, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "⏪ Влево", cx, cy + 25, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "Вправо ⏩", cx, cy + 50, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "Размер (П): -" + RightHandConfig.rightScalePercent + "%", cx, cy + 75, 120, 20, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) {
            int cy = this.height / 2; int cx = this.width / 2 - 60;
            if (mx >= cx && mx <= cx + 120) {
                // ИСПРАВЛЕНО: Кнопки меняют СТРОГО только значения ПРАВОЙ руки
                if (my >= cy - 75 && my <= cy - 55) { RightHandConfig.rightY += 0.05f; return true; }
                if (my >= cy - 50 && my <= cy - 30) { RightHandConfig.rightY -= 0.05f; return true; }
                if (my >= cy - 25 && my <= cy - 5)  { RightHandConfig.rightZ -= 0.05f; return true; }
                if (my >= cy && my <= cy + 20)       { RightHandConfig.rightZ += 0.05f; return true; }
                if (my >= cy + 25 && my <= cy + 45) { RightHandConfig.rightX -= 0.05f; return true; }
                if (my >= cy + 50 && my <= cy + 70) { RightHandConfig.rightX += 0.05f; return true; }
                if (my >= cy + 75 && my <= cy + 95) { RightHandConfig.rightScalePercent = (RightHandConfig.rightScalePercent + 10) % 100; return true; }
            }
        }
        return super.mouseClicked(mx, my, button);
    }
}

// МЕНЮ ТОЛЬКО ДЛЯ ЛЕВОЙ РУКИ (Клавиша I)
class LeftConfigScreen extends Screen {
    public LeftConfigScreen() { super(Component.literal("Настройка ЛЕВОЙ руки")); }

    // УБИРАЕМ МЫЛО: Переопределяем метод, чтобы размытие не рендерилось
    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, this.width, this.height, 0x60000000);
    }

    private void drawCustomButton(GuiGraphics g, String text, int x, int y, int w, int h, int mx, int my) {
        boolean hovered = mx >= x && mx <= x + w && my >= y && my <= y + h;
        g.fill(x, y, x + w, y + h, hovered ? 0xEE777777 : 0xEE444444);
        g.drawCenteredString(this.font, text, x + w / 2, y + (h - 8) / 2, 0xFFFFFFFF);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, "Нажмите ESC для возврата в игру", this.width / 2, this.height / 2 + 110, 0xAAAAAA);
        int cy = this.height / 2; int cx = this.width / 2 - 60;  
        graphics.drawCenteredString(this.font, "===[ НАСТРОЙКА ЛЕВОЙ РУКИ (I) ]===", this.width / 2, cy - 95, 0x55FF55);
        
        drawCustomButton(graphics, "↑ Выше", cx, cy - 75, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "↓ Ниже", cx, cy - 50, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "→ Дальше", cx, cy - 25, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "← Ближе", cx, cy, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "⏪ Влево", cx, cy + 25, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "Вправо ⏩", cx, cy + 50, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "Размер (Л): -" + RightHandConfig.leftScalePercent + "%", cx, cy + 75, 120, 20, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) {
            int cy = this.height / 2; int cx = this.width / 2 - 60;
            if (mx >= cx && mx <= cx + 120) {
                // Изменяются СТРОГО только левые переменные (leftX, leftY, leftZ, leftScalePercent)
                if (my >= cy - 75 && my <= cy - 55) { RightHandConfig.leftY += 0.05f; return true; }
                if (my >= cy - 50 && my <= cy - 30) { RightHandConfig.leftY -= 0.05f; return true; }
                if (my >= cy - 25 && my <= cy - 5)  { RightHandConfig.leftZ -= 0.05f; return true; }
                if (my >= cy && my <= cy + 20)       { RightHandConfig.leftZ += 0.05f; return true; }
                if (my >= cy + 25 && my <= cy + 45) { RightHandConfig.leftX -= 0.05f; return true; }
                if (my >= cy + 50 && my <= cy + 70) { RightHandConfig.leftX += 0.05f; return true; }
                if (my >= cy + 75 && my <= cy + 95) { RightHandConfig.leftScalePercent = (RightHandConfig.leftScalePercent + 10) % 100; return true; }
            }
        }
        return super.mouseClicked(mx, my, button);
    }
}
