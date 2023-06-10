package com.rumaruka.gribtweaks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.rumaruka.gribtweaks.common.items.BrushItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    private void applyBrushTransform(AbstractClientPlayer player, PoseStack matrices, float tickDelta) {
        // Aligning item to the center
        matrices.mulPose(Vector3f.XP.rotationDegrees(-60.0F));
        matrices.mulPose(Vector3f.ZP.rotationDegrees(-45.0F));
        matrices.translate(-0.3D, 0.3D, -1.0D);

        // Calculating periodic motion
        int max = 8 * 5;
        float progress = ((float) player.getUseItemRemainingTicks() + tickDelta) / max;
        float angleCoefficient = Mth.sin(5 * progress * Mth.PI);

        // Applying calculated angle along upward axis
        matrices.mulPose(Vector3f.ZP.rotationDegrees(40.0F * angleCoefficient));
        matrices.translate(-0.2D, 0.2D, 0.0D);

//        float f = (float)(player.getUseItemRemainingTicks() % 10);
//        float f1 = f - tickDelta + 1.0F;
//        float f2 = 1.0F - f1 / 10.0F;
//        float f3 = -90.0F;
//        float f4 = 60.0F;
//        float f5 = 150.0F;
//        float f6 = -15.0F;
//        int i = 2;
//        float f7 = -15.0F + 75.0F * Mth.cos(f2 * 2.0F * (float)Math.PI);
//        if (humanoidArm != HumanoidArm.RIGHT) {
//            matrices.translate(0.1D, 0.83D, 0.35D);
//            matrices.mulPose(Vector3f.XP.rotationDegrees(-80.0F));
//            matrices.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
//            matrices.mulPose(Vector3f.XP.rotationDegrees(f7));
//            matrices.translate(-0.3D, 0.22D, 0.35D);
//        } else {
//            matrices.translate(-0.25D, 0.22D, 0.35D);
//            matrices.mulPose(Vector3f.XP.rotationDegrees(-80.0F));
//            matrices.mulPose(Vector3f.YP.rotationDegrees(90.0F));
//            matrices.mulPose(Vector3f.ZP.rotationDegrees(0.0F));
//            matrices.mulPose(Vector3f.XP.rotationDegrees(f7));
//        }
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    private void renderArmWithItem(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (pPlayer.isUsingItem()) {
            if (pStack.getItem() instanceof BrushItem brushItem) {
                pMatrixStack.pushPose();
                applyBrushTransform(pPlayer, pMatrixStack, pPartialTicks);
                ((ItemInHandRenderer) (Object) this).renderItem(pPlayer, pStack, ItemTransforms.TransformType.FIXED, false, pMatrixStack, pBuffer, pCombinedLight);
                ci.cancel();
            }
        }
    }
}
