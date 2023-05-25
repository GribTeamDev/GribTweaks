package com.rumaruka.gribtweaks.client.model;

import com.rumaruka.gribtweaks.common.entity.BadSand;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.rumaruka.gribtweaks.GribTweaks.rl;

public class BadSandModel extends AnimatedGeoModel<BadSand> {
    @Override
    public ResourceLocation getModelResource(BadSand object) {
        return rl("geo/bad_sand.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BadSand object) {
        return  rl("textures/entity/bad_sand.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BadSand animatable) {
        return rl("animations/bad_sand.animation.json");
    }
}
