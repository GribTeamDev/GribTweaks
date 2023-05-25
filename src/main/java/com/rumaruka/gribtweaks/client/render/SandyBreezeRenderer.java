package com.rumaruka.gribtweaks.client.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rumaruka.gribtweaks.client.model.BadSandModel;
import com.rumaruka.gribtweaks.client.model.SandyBreezeModel;
import com.rumaruka.gribtweaks.common.entity.BadSand;
import com.rumaruka.gribtweaks.common.entity.SandyBreeze;
import com.rumaruka.gribtweaks.common.entity.projectile.SandShardProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import static com.rumaruka.gribtweaks.GribTweaks.rl;

public class SandyBreezeRenderer extends GeoEntityRenderer<SandyBreeze> {


    public SandyBreezeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SandyBreezeModel());
        this.shadowRadius=0.2f;
    }

    @Override
    public ResourceLocation getTextureLocation(SandyBreeze animatable) {
        return rl("textures/entity/sandy_breeze.png");
    }

    @Override
    public RenderType getRenderType(SandyBreeze animatable, float partialTick, PoseStack poseStack,
                                    MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}