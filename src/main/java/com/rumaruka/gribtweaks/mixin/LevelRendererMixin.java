package com.rumaruka.gribtweaks.mixin;


import com.mojang.blaze3d.systems.RenderSystem;
import com.rumaruka.gribtweaks.GribTweaks;
import com.rumaruka.gribtweaks.util.ParticleUtils;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Inject(at = @At("HEAD"), method = "renderSnowAndRain")
    private void impactfulweather$renderSnowAndRain(LightTexture p_109704_, float p_109705_, double p_109706_, double p_109707_, double p_109708_, CallbackInfo ci) {
        ParticleUtils.spawnweatherparticles();
    }
}
