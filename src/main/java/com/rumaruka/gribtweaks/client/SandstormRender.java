package com.rumaruka.gribtweaks.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.rumaruka.gribtweaks.GribTweaks;

import com.rumaruka.gribtweaks.util.DimensionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber
public class SandstormRender {
    public static boolean active;
    static final float[] colors = new float[3];
    static float color = 0F;
    static float fog = 1F;

    private static final ResourceLocation SAND_BLUR_TEXTURE = new ResourceLocation(GribTweaks.MODID, "textures/hud/sandstormwip.png");
    @SubscribeEvent
    public static void onViewportRender(ViewportEvent.RenderFog event) {
        if (active || fog < 8F) {
            float f = 45F;
            f = f >= event.getFarPlaneDistance() ? event.getFarPlaneDistance() : Mth.clampedLerp(f, event.getFarPlaneDistance(), fog);
            float shift = (float) ((active ? (fog > 0.25F ? 0.1F : 0.005F) : (fog > 0.25F ? 0.01F : 0.001F)) * event.getPartialTick());
            if (active)
                fog -= shift;
            else
                fog += shift;
            fog = Mth.clamp(fog, 0F, 1F);
            RenderSystem.setShaderTexture(0,SAND_BLUR_TEXTURE);

            if (event.getMode() == FogRenderer.FogMode.FOG_SKY) {
                RenderSystem.setShaderFogStart(0.0F);
                RenderSystem.setShaderFogEnd(f);



            } else {
                RenderSystem.setShaderFogStart(f * 0.75F);
                RenderSystem.setShaderFogEnd(f);
                RenderSystem.setShaderTexture(0,SAND_BLUR_TEXTURE);

            }
        }
    }

    @SubscribeEvent
    public static void onViewportComputeFogColor(ViewportEvent.ComputeFogColor event) {
        if (active || color > 0F) {
//            final float[] realColors = {event.getRed(), event.getGreen(), event.getBlue()};
//            for (int i = 0; i < 3; i++) {
//                final float real = realColors[i];
//                final float c = 1;
//                colors[i] = real == c ? c : Mth.clampedLerp(real, c, color);
//            }
//            float shift = (float) (5F * event.getPartialTick());
//            if (active)
//                color += shift;
//            else
//                color -= shift;
//
//            color = Mth.clamp(color, 0F, 5F);
//            Color color1 = Color.pink;
            event.setRed(0);
            event.setGreen(0);
            event.setBlue(0);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.level instanceof ServerLevel serverLevel) {

            if (DimensionHelper.getData(serverLevel).isStorming()) {
                active = true;
                RandomSource random = event.player.getRandom();
                for (int i = 0; i < 15; i++) {
                    Vec3 vec = event.player.position().add(0, random.nextDouble() * 3D, 0).
                            add(new Vec3(random.nextDouble() * 6D, 0D, 0D).yRot((float) Math.toRadians(random.nextInt(360))));
                    event.player.level.addParticle(ParticleTypes.ASH, vec.x, vec.y, vec.z, 2, 2, 2);
                }

            }else{
                active=false;
            }


        }
    }



}


