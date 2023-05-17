package com.rumaruka.gribtweaks.common.tiles;

import com.google.common.collect.Lists;
import com.ncpbails.modestmining.block.ModBlocks;
import com.rumaruka.gribtweaks.init.GTTiles;
import com.rumaruka.gribtweaks.util.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;

import java.util.List;

public class ArchaeologicalGeneratorBlockEntity extends BlockEntity {

    /**
     * The number of levels of this beacon's pyramid.
     */
    int levels;

    public static boolean isActive;
    public static boolean isWork;
    private int ticks;

    public ArchaeologicalGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(GTTiles.arch_block.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ArchaeologicalGeneratorBlockEntity pBlockEntity) {
        pBlockEntity.ticks++;

        if (pBlockEntity.checkStructure(pLevel, pPos)) {

            if (pBlockEntity.ticks >= 100) {

                isActive = true;
                applyWork(pLevel, pPos);
                playSound(pLevel, pPos, SoundEvents.BEACON_AMBIENT);
                pBlockEntity.ticks = 0;
            }


        } else {
            isActive = false;
        }
        if (isBlockAbove(pPos, pLevel)) {

            isWork=false;
            return;
        }

    }

    public boolean checkStructure(Level level, BlockPos pos) {

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if ((x != 0 || z != 0) && level.getBlockState(pos.offset(x, 0, z)).getBlock() != Blocks.SANDSTONE) {
                    isWork=false;
                    return false;
                }
                if (x != 0 && z != 0) {
                    for (int y = 0; y < 4; y++) {
                        BlockState state = level.getBlockState(pos.offset(x * 2, y, z * 2));
                        if ((y == 3 && state.getBlock() != Blocks.SANDSTONE_WALL)
                                || (y == 2 && state.getBlock() != Blocks.CHISELED_SANDSTONE)
                                || (y == 1 && state.getBlock() != Blocks.CUT_SANDSTONE)
                                || (y == 0 && state.getBlock() != Blocks.SANDSTONE)) {
                            isWork=false;
                            return false;
                        }
                    }
                }
            }
        }

        for (Direction dir : Direction.values()) {
            if (dir.getAxis() == Direction.Axis.Y) continue;

            BlockPos dirPos = pos.relative(dir, 2);
            Direction clockwise = dir.getClockWise(Direction.Axis.Y);
            Direction counterclockwise = dir.getCounterClockWise(Direction.Axis.Y);
            if (level.getBlockState(dirPos).getBlock() != Blocks.SANDSTONE_SLAB) {
                isWork=false;
                return false;
            }
            BlockState cwState = level.getBlockState(dirPos.relative(clockwise));
            BlockState ccwState = level.getBlockState(dirPos.relative(counterclockwise));
            if (cwState.getBlock() != Blocks.SANDSTONE_STAIRS || cwState.getValue(StairBlock.FACING) != clockwise || cwState.getValue(StairBlock.HALF) != Half.BOTTOM) {
                isWork=false;
                return false;
            }
            if (ccwState.getBlock() != Blocks.SANDSTONE_STAIRS || ccwState.getValue(StairBlock.FACING) != counterclockwise || ccwState.getValue(StairBlock.HALF) != Half.BOTTOM) {
                isWork=false;
                return false;
            }
        }
        isWork=true;
        return true;
    }

    private static void applyWork(Level pLevel, BlockPos pPos) {
        if (!pLevel.isClientSide()) {

            pLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SANDSTONE.defaultBlockState()), pPos.getX(), pPos.getY(), pPos.getZ(), pLevel.random.nextInt(), pLevel.random.nextInt(), pLevel.random.nextInt());

            if (RandomUtil.percentChance(0.8)) {
                pLevel.setBlockAndUpdate(pPos.above(), ModBlocks.SUSPICIOUS_SAND.get().defaultBlockState());


            }

            if (RandomUtil.percentChance(0.2)) {
                pLevel.setBlockAndUpdate(pPos.above(), Blocks.SAND.defaultBlockState());



            }

        }


    }


    public static void playSound(Level pLevel, BlockPos pPos, SoundEvent pSound) {
        pLevel.playSound((Player) null, pPos, pSound, SoundSource.MASTER, 1.0F, 1.0F);
    }


    public static boolean isBlockAbove(BlockPos pos, Level world) {
        return world.getBlockState(pos.above()).getBlock() != Blocks.AIR;
    }


}