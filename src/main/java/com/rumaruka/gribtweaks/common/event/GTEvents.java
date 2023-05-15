package com.rumaruka.gribtweaks.common.event;


import com.rumaruka.gribtweaks.common.block.GTCauldronInteractions;
import com.rumaruka.gribtweaks.common.block.watercompost.LayeredWaterCompost;
import com.rumaruka.gribtweaks.init.GTBlocks;
import com.rumaruka.gribtweaks.init.GTItems;
import com.rumaruka.gribtweaks.util.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.world.item.Item.getPlayerPOVHitResult;

@Mod.EventBusSubscriber
public class GTEvents {

    public static int ticks = 0;

    public static boolean isActive = false;


    @SubscribeEvent
    public static void onInteraction(PlayerInteractEvent event) {
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        BlockState state = level.getBlockState(pos);
        ItemStack sand_knife = player.getMainHandItem();

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

    @SubscribeEvent
    public static void onPlayerInteractSandBucket(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        ItemStack heldItem = player.getItemInHand(event.getHand());

        if (heldItem.getItem() == GTItems.wooden_bucket.get() && world.getBlockState(pos).getBlock() == Blocks.SAND) {
            world.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState());
            player.setItemInHand(event.getHand(), new ItemStack(GTItems.sand_bucket.get()));
            world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        }


    }

    @SubscribeEvent
    public static void onPlayerInteractDeadBush(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        Inventory inventory = player.getInventory();

        if (world.getBlockState(pos).getBlock() == Blocks.DEAD_BUSH) {
            world.setBlockAndUpdate(pos, GTBlocks.breake_bush.get().defaultBlockState());
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.STICK, 3));
            world.addFreshEntity(itemEntity);
        }


    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.ServerTickEvent event) {
        if (isActive) {
            ticks++;

        }


    }

    @SubscribeEvent
    public static void onLevelRainOrThunder(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        int water = 1;
        for (Player player : level.players()) {
            BlockHitResult rayTraceResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
            if (rayTraceResult.getType() == BlockHitResult.Type.BLOCK) {
                BlockPos pos = rayTraceResult.getBlockPos();
                BlockState state = level.getBlockState(pos);

                if (level instanceof ServerLevel serverLevel && (serverLevel.isThundering() || serverLevel.isRaining())) {

                    if (RandomUtil.percentChance(0.45f)) {
                        if (state.getBlock() == GTBlocks.compost.get()) {


                            level.setBlockAndUpdate(pos, GTBlocks.compost_water.get().defaultBlockState().setValue(LayeredWaterCompost.LEVEL, 3));


                        }


                    }
                }

            }
        }
    }


    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {

        Level world = event.level;

        if (event.phase == TickEvent.Phase.END) {

            for (Player player : world.players()) {

                BlockHitResult rayTraceResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
                if (rayTraceResult.getType() == BlockHitResult.Type.BLOCK) {
                    BlockPos pos = rayTraceResult.getBlockPos();


                    if (world.getBlockState(pos).getBlock() == GTBlocks.breake_bush.get() && world.getBlockState(pos.below()).getBlock() == Blocks.MUD) {
                        isActive = true;

                        if (ticks >= 30 * 20) {
                            world.setBlockAndUpdate(pos, Blocks.OAK_SAPLING.defaultBlockState());
                            world.setBlockAndUpdate(pos.below(), Blocks.DIRT.defaultBlockState());
                            addMudParticles(world, pos.below(), 5);
                            ticks = 0;
                            isActive = false;
                        }


                    }


                }
            }
        }
    }


    public static void addMudParticles(LevelAccessor p_40639_, BlockPos p_40640_, int p_40641_) {
        if (p_40641_ == 0) {
            p_40641_ = 15;
        }

        BlockState blockstate = p_40639_.getBlockState(p_40640_);
        if (!blockstate.isAir()) {
            double d0 = 0.5D;
            double d1;
            if (blockstate.is(Blocks.WATER)) {
                p_40641_ *= 3;
                d1 = 1.0D;
                d0 = 3.0D;
            } else if (blockstate.isSolidRender(p_40639_, p_40640_)) {
                p_40640_ = p_40640_.above();
                p_40641_ *= 3;
                d0 = 3.0D;
                d1 = 1.0D;
            } else {
                d1 = blockstate.getShape(p_40639_, p_40640_).max(Direction.Axis.Y);
            }

            p_40639_.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MUD.defaultBlockState()), (double) p_40640_.getX() + 0.5D, (double) p_40640_.getY() + 0.5D, (double) p_40640_.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            RandomSource randomsource = p_40639_.getRandom();

            for (int i = 0; i < p_40641_; ++i) {
                double d2 = randomsource.nextGaussian() * 0.02D;
                double d3 = randomsource.nextGaussian() * 0.02D;
                double d4 = randomsource.nextGaussian() * 0.02D;
                double d5 = 0.5D - d0;
                double d6 = (double) p_40640_.getX() + d5 + randomsource.nextDouble() * d0 * 2.0D;
                double d7 = (double) p_40640_.getY() + randomsource.nextDouble() * d1;
                double d8 = (double) p_40640_.getZ() + d5 + randomsource.nextDouble() * d0 * 2.0D;
                if (!p_40639_.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                    p_40639_.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MUD.defaultBlockState()), d6, d7, d8, d2, d3, d4);
                }
            }

        }
    }


    //Cauldron

    public static void registerCauldronInteractions() {
        // Cauldron
        CauldronInteraction.WATER.put(GTItems.sand_bucket.get(), GTCauldronInteractions.EMPTY_WATER_SAND);
        CauldronInteraction.WATER.put(GTItems.wooden_bucket.get(), GTCauldronInteractions.EMPTY_WATER_WOODEN);

        CauldronInteraction.EMPTY.put(GTItems.water_sand_bucket.get(), GTCauldronInteractions.FILL_WATER_SAND);
        CauldronInteraction.EMPTY.put(GTItems.water_wooden_bucket.get(), GTCauldronInteractions.FILL_WATER_WOODN);


    }


}
