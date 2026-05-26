package com.example.mymod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ParticleConfigScreen extends Screen {
    public ParticleConfigScreen() { super(Component.literal("Сетка PvP Частиц")); }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, this.width, this.height, 0x60000000); // Без мыла
    }

    private void drawGridButton(GuiGraphics g, String text, int x, int y, int w, int h, int mx, int my, boolean active) {
        boolean hovered = mx >= x && mx <= x + w && my >= y && my <= y + h;
        int color = active ? 0xEE22AA22 : (hovered ? 0xEE777777 : 0xEE333333); 
        RenderSystem.enableBlend();
        g.fill(x, y, x + w, y + h, color);
        g.drawCenteredString(this.font, text, x + w / 2, y + (h - 8) / 2, active ? 0xFFFFFFFF : 0xDDDDDD);
    }

    private String getShortName(int id) {
        String[] shorts = {"Эндер", "Порт-В", "Порт-Вы", "Дракон", "Сакура", "Руны", "Спрут", "Свечение", "Крит-С", "Крит", "Чары", "ХП-Инд", "Злость", "Удача", "Салют", "Снег", "Ад-Огонь", "Мини-О", "Лава", "Душ-Пл", "Дым-С", "Дым-Б", "Душа", "Костер", "Ведьма", "Пооф", "Пузыри", "Дождь", "Мицелий", "Споры", "Зелье", "Бел-Дым", "Син-Пл", "Искры-Д", "Скалк-Д", "Скалк-З", "Капли-В", "Глоу-Сп", "Дно-Пуз", "Пепел", "Салют-2", "Облако", "Взрыв", "Пооф-2", "Споры-В", "Вода-Т", "Лава-Т", "Нота", "Базальт", "Пуз-Взр", "Край-П", "Багров", "Искажен", "Ад-Кап", "Вода-К", "Глоу-2"};
        return (id >= 0 && id < 56) ? shorts[id] : "P-" + (id + 1);
    }

    private String getFullName(int id) {
        String[] fulls = {"Искры Эндер-Стержня", "Эффект входа в Портал", "Эффект выхода из Портала", "Драконье Дыхание", "Розовые Листья Сакуры", "Руны Стола Зачарования", "Чернила Обычного Спрута", "Светящиеся Частицы", "Сердечки при Крит-ударе", "Классический Крит-удар", "Магические PvP Чары", "Индикатор урона (Сердца)", "Злость Деревенского Жителя", "Искры Удачи Жителя", "PvP Искры Фейерверка", "Зимние Снежинки", "Адское Пламя", "Микро-Огонек", "Яркие Bрызги Лавы", "Бирюзовое Пламя Душ", "Обычный Серый Дым", "Плотный Огненный Дым", "Летящая Душа Моб", "Уютный Дым Костра", "Фиолетовая Магия Ведьмы", "Дымный Взрыв (Пооф)", "Водные PvP Пузыри", "Капли Сильного Дождя", "Споры Грибного Мицелия", "Аурические Споры Цвeтка", "Эффект Бутылочного Зелья", "Чистый Белый Дым", "Яркое Синее Пламя", "Светящиеся Искры Душ", "Частицы Скалк-Души", "Скалк Зарядный Взрыв", "Падающие Капли Воды", "Чернила Светящегося Спрута", "Глубоководные Пузыри", "Серый Вулканический Пепел", "Дополнительный Фейерверк", "PvP Облако", "Эффект Mini-Взрыва", "Второй Дымный Пооф", "Лесные Споры Воздуха", "Текущие Частицы Воды", "Брызги Текущей Лавы", "PvP Музыкальная Нота", "Микро-Пепел Базальта", "Взрыв Водного Пузыря", "Портал Края (Второй тип)", "Споры Багрового Гриба", "Споры Искаженного Гриба", "Капающая Адская Лава", "Капающая Вода", "Врое Глубокое Свечение"};
        return (id >= 0 && id < 56) ? fulls[id] : "Кастомный PvP Эффект";
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        int cy = this.height / 2;
        graphics.drawCenteredString(this.font, "==== СЕТКА PvP ЧАСТИЦ (56 ЭФФЕКТОВ) ====", this.width / 2, cy - 120, 0xFFFF55);
        int startX = this.width / 2 - 256; int startY = cy - 105;
        int hoveredParticleId = -1; int buttonId = 0;
        
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 8; row++) {
                int btnX = startX + (col * 73); int btnY = startY + (row * 20);
                boolean isActive = (RightHandConfig.activeParticleId == buttonId);
                drawGridButton(graphics, getShortName(buttonId), btnX, btnY, 70, 16, mouseX, mouseY, isActive);
                if (mouseX >= btnX && mouseX <= btnX + 70 && mouseY >= btnY && mouseY <= btnY + 16) { hoveredParticleId = buttonId; }
                buttonId++;
            }
        }
        String hintText = (hoveredParticleId != -1) ? "Выбрано: " + getFullName(hoveredParticleId) : "Наведите на кнопку, чтобы увидеть полное название";
        graphics.drawCenteredString(this.font, hintText, this.width / 2, cy + 62, (hoveredParticleId != -1) ? 0x55FFFF : 0x777777);
        
        // Панель выбора цвета прицела
        graphics.drawCenteredString(this.font, "==== ЦВЕТ ПРИЦЕЛА ====", this.width / 2, cy + 76, 0x55FF55);
        int colorStartX = this.width / 2 - 180; int colorY = cy + 88;
        String[] colors = {"Обычный", "Зеленый", "Красный", "Синий", "Желтый"};
        for (int i = 0; i < 5; i++) {
            boolean isColorActive = (RightHandConfig.crosshairColorId == i);
            drawGridButton(graphics, colors[i], colorStartX + (i * 73), colorY, 70, 16, mouseX, mouseY, isColorActive);
        }

        // Панель настройки уровня огня
        graphics.drawCenteredString(this.font, "==== УРОВЕНЬ ОГНЯ НА ЭКРАНЕ ====", this.width / 2, cy + 110, 0xFF5555);
        int fireStartX = this.width / 2 - 110; int fireY = cy + 122;
        drawGridButton(graphics, "Огонь -5%", fireStartX, fireY, 65, 16, mouseX, mouseY, false);
        drawGridButton(graphics, "Высота: " + RightHandConfig.fireHeightPercent + "%", fireStartX + 69, fireY, 80, 16, mouseX, mouseY, true);
        drawGridButton(graphics, "Огонь +5%", fireStartX + 153, fireY, 65, 16, mouseX, mouseY, false);

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) {
            int cy = this.height / 2; int startX = this.width / 2 - 256; int startY = cy - 105; int buttonId = 0;
            for (int col = 0; col < 7; col++) {
                for (int row = 0; row < 8; row++) {
                    int btnX = startX + (col * 73); int btnY = startY + (row * 20);
                    if (mx >= btnX && mx <= btnX + 70 && my >= btnY && my <= btnY + 16) { RightHandConfig.activeParticleId = buttonId; return true; }
                    buttonId++;
                }
            }
            int colorStartX = this.width / 2 - 180; int colorY = cy + 88;
            for (int i = 0; i < 5; i++) {
                int btnX = colorStartX + (i * 73);
                if (mx >= btnX && mx <= btnX + 70 && my >= colorY && my <= colorY + 16) { RightHandConfig.crosshairColorId = i; return true; }
            }

            int fireStartX = this.width / 2 - 110; int fireY = cy + 122;
            if (my >= fireY && my <= fireY + 16) {
                if (mx >= fireStartX && mx <= fireStartX + 65) {
                    RightHandConfig.fireHeightPercent = Math.max(0, RightHandConfig.fireHeightPercent - 5);
                    return true;
                }
                if (mx >= fireStartX + 153 && mx <= fireStartX + 218) {
                    RightHandConfig.fireHeightPercent = Math.min(100, RightHandConfig.fireHeightPercent + 5);
                    return true;
                }
            }
        }
        return super.mouseClicked(mx, my, button);
    }
}
