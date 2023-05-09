package com.rumaruka.gribtweaks.common.event;


import com.rumaruka.gribtweaks.common.items.SandBucketItem;
import com.rumaruka.gribtweaks.init.GTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.fml.ModList;
@Mod.EventBusSubscriber
public class GTEvents {


    @SubscribeEvent
    public static void onInteraction(PlayerInteractEvent event) {
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        BlockState state = level.getBlockState(pos);
        BlockState stateCauldron = level.getBlockState(pos);
        ItemStack sand_knife = player.getMainHandItem();
        ItemStack water_sand_bucket = player.getMainHandItem();
        ItemStack sand_bucket = player.getOffhandItem();
        if (sand_knife.getItem() == GTItems.sand_knife.get() && sand_bucket.getItem() == GTItems.sand_bucket.get()) {
            if (state.getBlock() == Blocks.CACTUS) {
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(GTItems.water_sand_bucket.get()));
                sand_knife.shrink(1);
                sand_bucket.shrink(1);
                level.addParticle(ParticleTypes.ASH, pos.getX(), pos.getY(), pos.getZ(), 1, 1, 1);
                level.playSound(null, player.getOnPos(), SoundEvents.AXE_STRIP, SoundSource.MASTER, 2, 2);
                level.removeBlock(pos, true);
            }
        }


    }

    public static void registerCauldronInteractions() {
        // empty water cauldron
        CauldronInteraction.WATER.put(GTItems.sand_bucket.get(), GTEvents::emptyWaterCauldron);

        // fill empty cauldron with water
        CauldronInteraction.EMPTY.put(GTItems.water_sand_bucket.get(), GTEvents::fillWaterCauldron);

    }

    // Fill //

    public static InteractionResult fillCauldron(Block filledBlock, SoundEvent sound, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if (!level.isClientSide) {
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, SandBucketItem.getEmptySuccessItem(stack,player)));
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            level.setBlockAndUpdate(pos, filledBlock.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1f, 1f);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static InteractionResult fillWaterCauldron(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        return fillCauldron(Blocks.WATER_CAULDRON, SoundEvents.BUCKET_EMPTY, state, level, pos, player, hand, stack);
    }



    // Empty //

    public static InteractionResult emptyCauldron(ItemLike filled, SoundEvent sound, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if (state.getValue(LayeredCauldronBlock.LEVEL) == 3) {
            if (!level.isClientSide) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, SandBucketItem.getFilledSuccessItem(stack,  player)));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    private static InteractionResult emptyWaterCauldron(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        return emptyCauldron(GTItems.water_sand_bucket::get, SoundEvents.BUCKET_FILL, state, level, pos, player, hand, stack);
    }

}
