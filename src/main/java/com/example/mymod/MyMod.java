package com.example.mymod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod("mymod")
public class MyMod {
    private static boolean wasClicking = false;
    private static boolean wasKeyKDown = false;
    private static boolean wasKeyIDown = false;
    private static boolean wasKeyJDown = false;
    private static final java.util.Random RANDOM = new java.util.Random();
    
    private static final ParticleOptions[] PARTICLE_REGISTRY = {
        ParticleTypes.END_ROD, ParticleTypes.PORTAL, ParticleTypes.REVERSE_PORTAL, ParticleTypes.DRAGON_BREATH, ParticleTypes.CHERRY_LEAVES, ParticleTypes.ENCHANT, ParticleTypes.SQUID_INK, ParticleTypes.GLOW,
        ParticleTypes.HEART, ParticleTypes.CRIT, ParticleTypes.ENCHANTED_HIT, ParticleTypes.DAMAGE_INDICATOR, ParticleTypes.ANGRY_VILLAGER, ParticleTypes.HAPPY_VILLAGER, ParticleTypes.FIREWORK, ParticleTypes.SNOWFLAKE,
        ParticleTypes.FLAME, ParticleTypes.SMALL_FLAME, ParticleTypes.LAVA, ParticleTypes.SOUL_FIRE_FLAME, ParticleTypes.SMOKE, ParticleTypes.LARGE_SMOKE, ParticleTypes.SOUL, ParticleTypes.CAMPFIRE_COSY_SMOKE,
        ParticleTypes.WITCH, ParticleTypes.POOF, ParticleTypes.BUBBLE, ParticleTypes.RAIN, ParticleTypes.MYCELIUM, ParticleTypes.EFFECT, ParticleTypes.INSTANT_EFFECT, ParticleTypes.WHITE_SMOKE,
        ParticleTypes.SOUL_FIRE_FLAME, ParticleTypes.SOUL, ParticleTypes.SCULK_SOUL, ParticleTypes.SCULK_CHARGE_POP, ParticleTypes.DRIPPING_WATER, ParticleTypes.GLOW_SQUID_INK, underwaterParticlesFix(), ParticleTypes.WHITE_ASH,
        ParticleTypes.FIREWORK, ParticleTypes.CLOUD, ParticleTypes.EXPLOSION, ParticleTypes.POOF, ParticleTypes.SPORE_BLOSSOM_AIR, ParticleTypes.FALLING_WATER, ParticleTypes.FALLING_LAVA, ParticleTypes.NOTE,
        ParticleTypes.ASH, ParticleTypes.BUBBLE_POP, ParticleTypes.PORTAL, ParticleTypes.CRIMSON_SPORE, ParticleTypes.WARPED_SPORE, ParticleTypes.FALLING_LAVA, ParticleTypes.FALLING_WATER, ParticleTypes.GLOW
    };

    private static ParticleOptions underwaterParticlesFix() {
        try { return ParticleTypes.UNDERWATER; } catch(Throwable t) { return ParticleTypes.BUBBLE; }
    }

    public MyMod(IEventBus modEventBus) {
        modEventBus.addListener(MyKeyBindings::registerKeys);
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new MyFire());
        NeoForge.EVENT_BUS.register(new MyHud()); 
    }

    @SubscribeEvent
    public void onClientTickPost(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        boolean isDown = mc.options.keyAttack.isDown();
        if (isDown && !wasClicking) {
            HitResult hitResult = mc.hitResult;
            if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                Entity target = ((EntityHitResult) hitResult).getEntity();
                
                if (mc.player.hasLineOfSight(target)) {
                    double x = target.getX(); double y = target.getY(); double z = target.getZ();
                    float height = target.getBbHeight();
                    int id = RightHandConfig.activeParticleId;
                    ParticleOptions selectedParticle = (id >= 0 && id < 56) ? PARTICLE_REGISTRY[id] : ParticleTypes.END_ROD;
                    
                    for (int i = 0; i < 12; i++) {
                        double offsetX = (RANDOM.nextDouble() - 0.5) * 0.4;
                        double offsetZ = (RANDOM.nextDouble() - 0.5) * 0.4;
                        double offsetY = RANDOM.nextDouble() * height;
                        double speedX = (RANDOM.nextDouble() - 0.5) * 0.15;
                        double speedY = RANDOM.nextDouble() * 0.10;
                        double speedZ = (RANDOM.nextDouble() - 0.5) * 0.15;
                        mc.level.addParticle(selectedParticle, x + offsetX, y + offsetY, z + offsetZ, speedX, speedY, speedZ);
                    }
                }
            }
        }
        wasClicking = isDown;
        
        boolean isKDown = MyKeyBindings.OPEN_RIGHT_CONFIG.isDown();
        if (isKDown && !wasKeyKDown && mc.screen == null) { mc.setScreen(new RightConfigScreen()); }
        wasKeyKDown = isKDown;

        boolean isIDown = MyKeyBindings.OPEN_LEFT_CONFIG.isDown();
        if (isIDown && !wasKeyIDown && mc.screen == null) { mc.setScreen(new LeftConfigScreen()); }
        wasKeyIDown = isIDown;

        boolean isJDown = MyKeyBindings.OPEN_PARTICLE_CONFIG.isDown();
        if (isJDown && !wasKeyJDown && mc.screen == null) { mc.setScreen(new ParticleConfigScreen()); }
        wasKeyJDown = isJDown;
    }

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;
        if (event.getItemStack().isEmpty()) return;

        PoseStack poseStack = event.getPoseStack();
        float swingProgress = event.getSwingProgress();

        if (event.getHand() == InteractionHand.MAIN_HAND) {
            float rightScaleMultiplier = 1.0f - (RightHandConfig.rightScalePercent / 100.0f);
            
            poseStack.translate(RightHandConfig.rightX, RightHandConfig.rightY, RightHandConfig.rightZ);
            poseStack.scale(rightScaleMultiplier, rightScaleMultiplier, rightScaleMultiplier);
            
            // ИСПРАВЛЕНЫ И ДОБАВЛЕНЫ ЛУЧШИЕ PvP АНИМАЦИИ
            if (swingProgress > 0.0f && RightHandConfig.swingMode > 0) {
                float f = Mth.sin(swingProgress * (float)Math.PI);
                float f1 = Mth.sin(Mth.sqrt(swingProgress) * (float)Math.PI);
                
                if (RightHandConfig.swingMode == 1) {
                    // 1. Плавный замах (Старый Block Animation из 1.7.10)
                    poseStack.translate(0.0f, f * 0.08f, 0.0f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(f1 * -18.0f));
                    poseStack.mulPose(Axis.YP.rotationDegrees(f1 * -12.0f));
                } 
                else if (RightHandConfig.swingMode == 2) {
                    // 2. PvP Круговой взмах (Lunar Client Style)
                    poseStack.translate(f1 * 0.12f, f * -0.06f, 0.0f);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(f1 * -40.0f));
                    poseStack.mulPose(Axis.YP.rotationDegrees(f1 * -20.0f));
                } 
                else if (RightHandConfig.swingMode == 3) {
                    // 3. Горизонтальный Слайд (Old-School Блокхит)
                    poseStack.translate(f1 * -0.15f, 0.0f, f * 0.05f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(f1 * -45.0f));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(f1 * -10.0f));
                } 
                else if (RightHandConfig.swingMode == 4) {
                    // 4. Хлыст (Хлыстообразный резкий PvP удар сверху-вниз)
                    poseStack.translate(0.0f, f1 * -0.2f, f * 0.1f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(f1 * -35.0f));
                } 
                else if (RightHandConfig.swingMode == 5) {
                    // 5. Полное 3D Вращение меча по спирали
                    poseStack.translate(0.0f, 0.0f, f * -0.1f);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(swingProgress * 360.0f));
                }
            }
        } 
        else if (event.getHand() == InteractionHand.OFF_HAND) {
            float leftScaleMultiplier = 1.0f - (RightHandConfig.leftScalePercent / 100.0f);
            
            // Левая рука полностью изолирована
            poseStack.translate(RightHandConfig.leftX, RightHandConfig.leftY, RightHandConfig.leftZ);
            poseStack.scale(leftScaleMultiplier, leftScaleMultiplier, leftScaleMultiplier);
        }
    }
}
