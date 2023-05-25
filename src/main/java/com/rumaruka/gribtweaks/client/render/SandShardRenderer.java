package com.rumaruka.gribtweaks.client.render;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.rumaruka.gribtweaks.client.model.SandShardModel;
import com.rumaruka.gribtweaks.common.entity.projectile.SandShardProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SandShardRenderer extends EntityRenderer<SandShardProjectile> {
  private static final ResourceLocation texture = new ResourceLocation("gribtweaks:textures/entity/texture.png");
    private final SandShardModel model;
    public SandShardRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new SandShardModel(pContext.bakeLayer(SandShardModel.LAYER_LOCATION));
    }

    @Override
    public void render(SandShardProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        VertexConsumer vb = pBuffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(pEntity)));
        pPoseStack.pushPose();
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F + Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
        this.model.renderToBuffer(pPoseStack, vb, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.0625F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SandShardProjectile pEntity) {
        return texture;
    }
}
