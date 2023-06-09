package com.rumaruka.gribtweaks.common.items;

import com.rumaruka.gribtweaks.init.GTItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class WoodenBucket extends BucketItem {


    public static final Map<Supplier<? extends Item>, Supplier<? extends Item>> REPLACEMENTS = new HashMap<>();

    public WoodenBucket(Supplier<? extends Fluid> supplier, Properties properties) {
        super(supplier, properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        BlockHitResult blockhitResult = getPlayerPOVHitResult(level, player, this.getFluid() == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> interactionResult = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, level, heldStack, blockhitResult);
        if (interactionResult != null) return interactionResult;
        if (blockhitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(heldStack);
        } else if (blockhitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(heldStack);
        } else {
            BlockPos blockPos = blockhitResult.getBlockPos();
            Direction direction = blockhitResult.getDirection();
            BlockPos relativePos = blockPos.relative(direction);
            if (level.mayInteract(player, blockPos) && player.mayUseItemAt(relativePos, direction, heldStack)) {
                if (this.getFluid() == Fluids.EMPTY) {
                    BlockState blockState = level.getBlockState(blockPos);
                    FluidState fluidState = level.getFluidState(blockPos);
                    if (blockState.getBlock() instanceof BucketPickup bucketPickup ) {



                            player.awardStat(Stats.ITEM_USED.get(this));
                            bucketPickup.getPickupSound(blockState).ifPresent((soundEvent) -> player.playSound(soundEvent, 1.0F, 1.0F));
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);
                            ItemStack resultStack = ItemUtils.createFilledResult(heldStack, player,  GTItems.water_wooden_bucket.get().getDefaultInstance());
                            if (!level.isClientSide()) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player,  GTItems.water_wooden_bucket.get().getDefaultInstance());
                                level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());

                            }
                            return InteractionResultHolder.sidedSuccess(resultStack, level.isClientSide());

                    }
                    return InteractionResultHolder.fail(heldStack);
                } else {
                    BlockState blockState = level.getBlockState(blockPos);
                    BlockPos newPos = canBlockContainFluid(level, blockPos, blockState) ? blockPos : relativePos;
                    if (this.emptyContents(player, level, newPos, blockhitResult)) {
                        this.checkExtraContent(player, level, heldStack, newPos);
                        if (player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, newPos, heldStack);
                        }
                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(heldStack, player), level.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(heldStack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(heldStack);
            }
        }
    }


    public static ItemStack swapBucketType(ItemStack filledStack) {
        Supplier<? extends Item> filledItem = filledStack::getItem;
        for (Map.Entry<Supplier<? extends Item>, Supplier<? extends Item>> entry : REPLACEMENTS.entrySet()) {
            if (filledItem.get() == entry.getKey().get()) {

                ItemStack newStack = GTItems.water_wooden_bucket.get().getDefaultInstance();
                newStack.setTag(filledStack.getTag());
                return newStack;
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * Based on {@link BucketItem#getEmptySuccessItem(ItemStack, Player)} except it returns a et instead of a vanilla bucket.
     */
    public static ItemStack getEmptySuccessItem(ItemStack bucketStack, Player player) {
        return !player.getAbilities().instabuild ? new ItemStack(GTItems.wooden_bucket.get()) : bucketStack;
    }

    /**
     * We don't initialize the Forge {@link net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper} for Skyroot Buckets.
     */
    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        if (this.getClass() == WoodenBucket.class)
            return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
        else
            return super.initCapabilities(stack, nbt);
    }

    /**
     * Copy of BucketItem#canBlockContainFluid(Level, BlockPos, BlockState).
     */
    protected boolean canBlockContainFluid(Level level, BlockPos pos, BlockState state) {
        return state.getBlock() instanceof LiquidBlockContainer liquidBlockContainer && liquidBlockContainer.canPlaceLiquid(level, pos, state, this.getFluid());
    }
}
