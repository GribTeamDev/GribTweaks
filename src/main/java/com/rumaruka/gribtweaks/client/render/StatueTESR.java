package com.rumaruka.gribtweaks.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.rumaruka.gribtweaks.common.event.GateWayEvents;
import com.rumaruka.gribtweaks.init.GTItems;
import com.rumaruka.gribtweaks.common.tiles.StatueOBJBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;


public class StatueTESR implements BlockEntityRenderer<StatueOBJBlockEntity> {
    public StatueTESR(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(StatueOBJBlockEntity entity,  float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {


        if (GateWayEvents.isCatalystRightClick) {


                ItemStack itemstack = new ItemStack(GTItems.TEST.get());
                if (!itemstack.isEmpty()) {
                    matrixStack.pushPose();
                    float yDiff = Mth.sin((System.currentTimeMillis() % 86400000) / 1000F) * 0.1F + 0.1F;
                    matrixStack.translate(0.5D, 1.5D + yDiff, -0.45D);
                    float f3 = ((System.currentTimeMillis() % 86400000) / 2000F) * (180F / (float) Math.PI);
                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(f3));
                    matrixStack.scale(1.5F, 1.5F, 1.5F);
                    Minecraft.getInstance().getItemRenderer().renderStatic(GateWayEvents.stack, ItemTransforms.TransformType.GROUND, packedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer, 0);
                    matrixStack.popPose();
                }




        }


    }



}







