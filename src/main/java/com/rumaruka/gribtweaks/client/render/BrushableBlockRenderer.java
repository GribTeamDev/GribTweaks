package com.rumaruka.gribtweaks.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.rumaruka.gribtweaks.common.block.BrushableBlock;
import com.rumaruka.gribtweaks.common.tiles.BrushableBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BrushableBlockRenderer implements BlockEntityRenderer<BrushableBlockEntity> {
    private final ItemRenderer itemRenderer;

    public BrushableBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    public void render(BrushableBlockEntity brushableBlock, float f1, PoseStack poseStack, MultiBufferSource bufferSource, int p_277463_, int p_277346_) {
        if (brushableBlock.getLevel() != null) {
            int i = brushableBlock.getBlockState().getValue(BrushableBlock.DUSTED);
            if (i > 0) {
                Direction direction = Direction.UP;
                if (direction != null) {
                    ItemStack itemstack = brushableBlock.getItem();
                    if (!itemstack.isEmpty()) {
                        poseStack.pushPose();
                        poseStack.translate(0.0F, 0.5F, 0.0F);
                        float[] afloat = this.translations(direction, i);
                        poseStack.translate(afloat[0], afloat[1], afloat[2]);
                        poseStack.mulPose(Vector3f.YP.rotationDegrees(75.0F));
                        poseStack.mulPose(Vector3f.YP.rotationDegrees(11.0F));
                        poseStack.scale(0.5F, 0.5F, 0.5F);
                        int j = LevelRenderer.getLightColor(brushableBlock.getLevel(), brushableBlock.getBlockState(), brushableBlock.getBlockPos().relative(direction));
                        this.itemRenderer.renderStatic(itemstack, ItemTransforms.TransformType.FIXED, j, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 0);
                        poseStack.popPose();
                    }
                }
            }
        }
    }

    private float[] translations(Direction direction, int i) {
        float[] afloat = new float[]{0.5F, 0.0F, 0.5F};
        float f = (float)i / 10.0F * 0.75F;
        switch (direction) {
            case EAST:
                afloat[0] = 0.73F + f;
                break;
            case WEST:
                afloat[0] = 0.25F - f;
                break;
            case UP:
                afloat[1] = 0.25F + f;
                break;
            case DOWN:
                afloat[1] = -0.23F - f;
                break;
            case NORTH:
                afloat[2] = 0.25F - f;
                break;
            case SOUTH:
                afloat[2] = 0.73F + f;
        }

        return afloat;
    }
}
