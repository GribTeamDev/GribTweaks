package com.rumaruka.gribtweaks.client.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rumaruka.gribtweaks.client.model.BadSandModel;
import com.rumaruka.gribtweaks.common.entity.BadSand;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import static com.rumaruka.gribtweaks.GribTweaks.rl;

public class BadSandRenderer extends GeoEntityRenderer<BadSand> {


    public BadSandRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BadSandModel());
        this.shadowRadius=0.2f;
    }

    @Override
    public ResourceLocation getTextureLocation(BadSand animatable) {
        return rl("textures/entity/bad_sand.png");
    }

    @Override
    public RenderType getRenderType(BadSand animatable, float partialTick, PoseStack poseStack,
                                    MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
