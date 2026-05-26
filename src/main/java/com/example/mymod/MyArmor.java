package com.example.mymod;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MyArmor { }

class RightConfigScreen extends Screen {
    public RightConfigScreen() { super(Component.literal("Настройка ПРАВОЙ руки")); }

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
        graphics.drawCenteredString(this.font, "Нажмите ESC для возврата в игру", this.width / 2, this.height / 2 + 115, 0xAAAAAA);
        int cy = this.height / 2; int cx = this.width / 2 - 60;  
        graphics.drawCenteredString(this.font, "===[ НАСТРОЙКА ПРАВОЙ РУКИ ]===", this.width / 2, cy - 100, 0xFF5555);
        
        drawCustomButton(graphics, "↑ Выше", cx, cy - 80, 120, 18, mouseX, mouseY);
        drawCustomButton(graphics, "↓ Ниже", cx, cy - 58, 120, 18, mouseX, mouseY);
        drawCustomButton(graphics, "→ Дальше", cx, cy - 36, 120, 18, mouseX, mouseY);
        drawCustomButton(graphics, "← Ближе", cx, cy - 14, 120, 18, mouseX, mouseY);
        drawCustomButton(graphics, "⏪ Влево", cx, cy + 8, 120, 18, mouseX, mouseY);
        drawCustomButton(graphics, "Вправо ⏩", cx, cy + 30, 120, 18, mouseX, mouseY);
        drawCustomButton(graphics, "Размер: -" + RightHandConfig.rightScalePercent + "%", cx, cy + 52, 120, 18, mouseX, mouseY);
        
        String animName = "Дефолт";
        if (RightHandConfig.swingMode == 1) animName = "Плавный 1.7";
        if (RightHandConfig.swingMode == 2) animName = "Слайд";
        if (RightHandConfig.swingMode == 3) animName = "Хлыст";
        drawCustomButton(graphics, "Удар: " + animName, cx, cy + 74, 120, 18, mouseX, mouseY);
        
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) {
            int cy = this.height / 2; int cx = this.width / 2 - 60;
            if (mx >= cx && mx <= cx + 120) {
                if (my >= cy - 80 && my <= cy - 62) { RightHandConfig.rightY += 0.05f; return true; }
                if (my >= cy - 58 && my <= cy - 40) { RightHandConfig.rightY -= 0.05f; return true; }
                if (my >= cy - 36 && my <= cy - 18) { RightHandConfig.rightZ -= 0.05f; return true; }
                if (my >= cy - 14 && my <= cy + 4)  { RightHandConfig.rightZ += 0.05f; return true; }
                if (my >= cy + 8  && my <= cy + 26) { RightHandConfig.rightX -= 0.05f; return true; }
                if (my >= cy + 30 && my <= cy + 48) { RightHandConfig.rightX += 0.05f; return true; }
                if (my >= cy + 52 && my <= cy + 70) { RightHandConfig.rightScalePercent = (RightHandConfig.rightScalePercent + 10) % 100; return true; }
                if (my >= cy + 74 && my <= cy + 92) { RightHandConfig.swingMode = (RightHandConfig.swingMode + 1) % 4; return true; } // Максимум 3 режима + дефолт
            }
        }
        return super.mouseClicked(mx, my, button);
    }
}

class LeftConfigScreen extends Screen {
    public LeftConfigScreen() { super(Component.literal("Настройка ЛЕВОЙ руки")); }

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
        graphics.drawCenteredString(this.font, "===[ НАСТРОЙКА ЛЕВОЙ РУКИ ]===", this.width / 2, cy - 95, 0x55FF55);
        
        drawCustomButton(graphics, "↑ Выше", cx, cy - 75, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "↓ Ниже", cx, cy - 50, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "→ Дальше", cx, cy - 25, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "← Ближе", cx, cy, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "⏪ Влево", cx, cy + 25, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "Вправо ⏩", cx, cy + 50, 120, 20, mouseX, mouseY);
        drawCustomButton(graphics, "Размер: -" + RightHandConfig.leftScalePercent + "%", cx, cy + 75, 120, 20, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) {
            int cy = this.height / 2; int cx = this.width / 2 - 60;
            if (mx >= cx && mx <= cx + 120) {
                // Изменяются строго ЛЕВЫЕ переменные
                if (my >= cy - 75 && my <= cy - 55) { RightHandConfig.leftY += 0.05f; return true; }
                if (my >= cy - 45 && my <= cy - 25) { RightHandConfig.leftY -= 0.05f; return true; }
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
