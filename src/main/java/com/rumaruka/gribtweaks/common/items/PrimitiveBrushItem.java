package com.rumaruka.gribtweaks.common.items;

import com.rumaruka.gribtweaks.common.recipe.PrimitiveBrushRecipe;
import com.simibubi.create.AllSoundEvents;


import com.simibubi.create.foundation.item.CustomUseEffectsItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.mixin.accessor.LivingEntityAccessor;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Iterator;
import java.util.function.Consumer;

public class PrimitiveBrushItem extends Item implements CustomUseEffectsItem {
    public PrimitiveBrushItem( ) {
        super(new Properties().defaultDurability(8));
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        InteractionResultHolder<ItemStack> FAIL = new InteractionResultHolder(InteractionResult.FAIL, itemstack);
        if (itemstack.getOrCreateTag().contains("Polishing")) {
            playerIn.startUsingItem(handIn);
            return new InteractionResultHolder(InteractionResult.PASS, itemstack);
        } else {
            InteractionHand otherHand = handIn == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            ItemStack itemInOtherHand = playerIn.getItemInHand(otherHand);
            if (PrimitiveBrushRecipe.canPolish(worldIn, itemInOtherHand)) {
                ItemStack item = itemInOtherHand.copy();
                ItemStack toPolish = item.split(1);
                playerIn.startUsingItem(handIn);
                itemstack.getOrCreateTag().put("Polishing", toPolish.serializeNBT());
                playerIn.setItemInHand(otherHand, item);
                return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
            } else {
                HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.NONE);
                if (!(raytraceresult instanceof BlockHitResult)) {
                    return FAIL;
                } else {
                    BlockHitResult ray = (BlockHitResult)raytraceresult;
                    Vec3 hitVec = ray.getLocation();
                    AABB bb = (new AABB(hitVec, hitVec)).inflate(1.0);
                    ItemEntity pickUp = null;
                    Iterator var13 = worldIn.getEntitiesOfClass(ItemEntity.class, bb).iterator();

                    while(var13.hasNext()) {
                        ItemEntity itemEntity = (ItemEntity)var13.next();
                        if (itemEntity.isAlive() && !(itemEntity.position().distanceTo(playerIn.position()) > 3.0)) {
                            ItemStack stack = itemEntity.getItem();
                            if (PrimitiveBrushRecipe.canPolish(worldIn, stack)) {
                                pickUp = itemEntity;
                                break;
                            }
                        }
                    }

                    if (pickUp == null) {
                        return FAIL;
                    } else {
                        ItemStack item = pickUp.getItem().copy();
                        ItemStack toPolish = item.split(1);
                        playerIn.startUsingItem(handIn);
                        if (!worldIn.isClientSide) {
                            itemstack.getOrCreateTag().put("Polishing", toPolish.serializeNBT());
                            if (item.isEmpty()) {
                                pickUp.discard();
                            } else {
                                pickUp.setItem(item);
                            }
                        }

                        return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
                    }
                }
            }
        }
    }

    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!(entityLiving instanceof Player player)) {
            return stack;
        } else {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("Polishing")) {
                ItemStack toPolish = ItemStack.of(tag.getCompound("Polishing"));
                ItemStack polished = PrimitiveBrushRecipe.applyPolish(worldIn, entityLiving.position(), toPolish, stack);
                if (worldIn.isClientSide) {
                    spawnParticles(entityLiving.getEyePosition(1.0F).add(entityLiving.getLookAngle().scale(0.5)), toPolish, worldIn);
                    return stack;
                }

                if (!polished.isEmpty()) {
                    if (player instanceof FakePlayer) {
                        player.drop(polished, false, false);
                    } else {
                        player.getInventory().placeItemBackInInventory(polished);
                    }
                }

                tag.remove("Polishing");
                stack.hurtAndBreak(1, entityLiving, (p) -> {
                    p.broadcastBreakEvent(p.getUsedItemHand());
                });
            }

            return stack;
        }
    }

    public static void spawnParticles(Vec3 location, ItemStack polishedStack, Level world) {
        for(int i = 0; i < 20; ++i) {
            Vec3 motion = VecHelper.offsetRandomly(Vec3.ZERO, world.random, 0.125F);
            world.addParticle(new ItemParticleOption(ParticleTypes.ITEM, polishedStack), location.x, location.y, location.z, motion.x, motion.y, motion.z);
        }

    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("Polishing")) {
                ItemStack toPolish = ItemStack.of(tag.getCompound("Polishing"));
                player.getInventory().placeItemBackInInventory(toPolish);
                tag.remove("Polishing");
            }

        }
    }

    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        BlockState newState = state.getToolModifiedState(context, ToolActions.AXE_SCRAPE, false);
        if (newState != null) {
            AllSoundEvents.SANDING_LONG.play(level, player, pos, 1.0F, 1.0F + (level.random.nextFloat() * 0.5F - 1.0F) / 5.0F);
            level.levelEvent(player, 3005, pos, 0);
        } else {
            newState = state.getToolModifiedState(context, ToolActions.AXE_WAX_OFF, false);
            if (newState != null) {
                AllSoundEvents.SANDING_LONG.play(level, player, pos, 1.0F, 1.0F + (level.random.nextFloat() * 0.5F - 1.0F) / 5.0F);
                level.levelEvent(player, 3004, pos, 0);
            }
        }

        if (newState != null) {
            level.setBlockAndUpdate(pos, newState);
            if (player != null) {
                stack.hurtAndBreak(1, player, (p) -> {
                    p.broadcastBreakEvent(p.getUsedItemHand());
                });
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == ToolActions.AXE_SCRAPE || toolAction == ToolActions.AXE_WAX_OFF;
    }

    public Boolean shouldTriggerUseEffects(ItemStack stack, LivingEntity entity) {
        return true;
    }

    public boolean triggerUseEffects(ItemStack stack, LivingEntity entity, int count, RandomSource random) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Polishing")) {
            ItemStack polishing = ItemStack.of(tag.getCompound("Polishing"));
            ((LivingEntityAccessor)entity).create$callSpawnItemParticles(polishing, 1);
        }

        if ((entity.getTicksUsingItem() - 6) % 7 == 0) {
            entity.playSound(entity.getEatingSound(stack), 0.9F + 0.2F * random.nextFloat(), random.nextFloat() * 0.2F + 0.9F);
        }

        return true;
    }

    public SoundEvent getEatingSound() {
        return AllSoundEvents.SANDING_SHORT.getMainEvent();
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    public int getEnchantmentValue() {
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new PrimitiveBrushItemRenderer()));
    }
}
