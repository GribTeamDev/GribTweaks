package com.rumaruka.gribtweaks.client.model;

import com.rumaruka.gribtweaks.common.entity.BadSand;
import com.rumaruka.gribtweaks.common.entity.SandyBreeze;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.rumaruka.gribtweaks.GribTweaks.rl;

public class SandyBreezeModel extends AnimatedGeoModel<SandyBreeze> {
    @Override
    public ResourceLocation getModelResource(SandyBreeze object) {
        return rl("geo/sandy_breeze.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SandyBreeze object) {
        return  rl("textures/entity/sandy_breeze.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SandyBreeze animatable) {
        return rl("animations/sandy_breeze.animation.json");
    }
}
