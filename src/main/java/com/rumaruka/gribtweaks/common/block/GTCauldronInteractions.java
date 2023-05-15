package com.rumaruka.gribtweaks.common.block;
import com.rumaruka.gribtweaks.init.GTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
public class GTCauldronInteractions {


    public static final CauldronInteraction FILL_WATER_SAND = (state, level, pos, player, hand, stack) ->
            emptySandBucket(level, pos, player, hand, stack, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY);

    public static final CauldronInteraction FILL_WATER_WOODN = (state, level, pos, player, hand, stack) ->
            emptyWoodenBucket(level, pos, player, hand, stack, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY);


    public static final CauldronInteraction EMPTY_WATER_SAND = (state, level, pos, player, hand, stack) ->
            CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, new ItemStack(GTItems.water_sand_bucket.get()), (blockState) ->
                    blockState.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);

    public static final CauldronInteraction EMPTY_WATER_WOODEN = (state, level, pos, player, hand, stack) ->
            CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, new ItemStack(GTItems.water_wooden_bucket.get()), (blockState) ->
                    blockState.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);



    /**
     * Cleans colored Capes into White Capes.
     */


    /**
     * Empties a filled Skyroot Bucket into an empty Skyroot Bucket.
     */
    private static InteractionResult emptySandBucket(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState state, SoundEvent sound) {
        if (!level.isClientSide()) {
            Item item = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(GTItems.sand_bucket.get())));
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            level.setBlockAndUpdate(pos, state);
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private static InteractionResult emptyWoodenBucket(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState state, SoundEvent sound) {
        if (!level.isClientSide()) {
            Item item = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(GTItems.wooden_bucket.get())));
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            level.setBlockAndUpdate(pos, state);
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
