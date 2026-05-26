package com.example.mymod;

public class RightHandConfig {
    // Настройки ПРАВОЙ руки (Клавиша K)
    public static float rightX = 0.12f; 
    public static float rightY = 0.10f;
    public static float rightZ = -0.45f;
    public static int rightScalePercent = 30; // Изначально уменьшена на 30%

    // Настройки ЛЕВОЙ руки (Клавиша I)
    public static float leftX = -0.315f; 
    public static float leftY = 0.10f;
    public static float leftZ = -0.45f;
    public static int leftScalePercent = 40; // Изначально уменьшена на 40%
    
    // Анимации удара (0 - Дефолт, 1 - Плавный 1.7, 2 - Слайд, 3 - Хлыст)
    public static int swingMode = 1; 
    
    // Эффекты и Цвета
    public static int activeParticleId = 0;
    public static int crosshairColorId = 0; 

    // Настройки меню J (Огонь и Оптимизация частиц)
    public static int fireHeightPercent = 40; 
    public static int maxHitParticles = 12; // ДОБАВЛЕНО: Регулируемое количество частиц (по дефолту 12)
}
