package com.rumaruka.gribtweaks.common.tiles;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ncpbails.modestmining.block.ModBlocks;
import com.rumaruka.gribtweaks.init.GTTiles;
import com.rumaruka.gribtweaks.util.RandomUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;

import java.util.Arrays;
import java.util.List;

public class ArchaeologicalGeneratorBlockEntity extends BlockEntity {
    List<ArchaeologicalGeneratorBlockEntity.BeaconBeamSection> beamSections = Lists.newArrayList();
    private List<ArchaeologicalGeneratorBlockEntity.BeaconBeamSection> checkingBeamSections = Lists.newArrayList();
    /**
     * The number of levels of this beacon's pyramid.
     */
    int levels;
    private int lastCheckY;
    private int ticks;
    public ArchaeologicalGeneratorBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(GTTiles.arch_block.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ArchaeologicalGeneratorBlockEntity pBlockEntity) {
        pBlockEntity.ticks++;

        if (pBlockEntity.checkStructure(pLevel,pPos)){
            if (pBlockEntity.ticks>=100){
                applyWork(pLevel,pPos);
                pBlockEntity.ticks=0;
           }

            addSandParticles(pLevel,pPos,6);

        }
    }
    public static void addSandParticles(LevelAccessor p_40639_, BlockPos p_40640_, int p_40641_) {
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

            p_40639_.addParticle(ParticleTypes.ASH, (double) p_40640_.getX() + 0.5D, (double) p_40640_.getY() + 0.5D, (double) p_40640_.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
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
    public boolean checkStructure(Level level, BlockPos pos) {
            /*
            * "xxx"
            * "xsx"
            * "xxx"
            * */

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if ((x != 0 || z != 0) && level.getBlockState(pos.offset(x, 0, z)).getBlock() != Blocks.SANDSTONE) {
                    return false;
                }
                if (x != 0 && z != 0) {
                    for (int y = 0; y < 4; y++) {
                        BlockState state = level.getBlockState(pos.offset(x * 2, y, z * 2));
                        if ((y == 3 && state.getBlock() != Blocks.SANDSTONE_WALL)
                                || (y == 2 && state.getBlock() != Blocks.CHISELED_SANDSTONE)
                                || (y == 1 && state.getBlock() != Blocks.CUT_SANDSTONE)
                                || (y == 0 && state.getBlock() != Blocks.SANDSTONE)) {
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
                return false;
            }
            BlockState cwState = level.getBlockState(dirPos.relative(clockwise));
            BlockState ccwState = level.getBlockState(dirPos.relative(counterclockwise));
            if (cwState.getBlock() != Blocks.SANDSTONE_STAIRS || cwState.getValue(StairBlock.FACING) != clockwise || cwState.getValue(StairBlock.HALF) != Half.BOTTOM) {
                return false;
            }
            if (ccwState.getBlock() != Blocks.SANDSTONE_STAIRS || ccwState.getValue(StairBlock.FACING) != counterclockwise || ccwState.getValue(StairBlock.HALF) != Half.BOTTOM) {
                return false;
            }
        }
        return true;
    }
    private static void applyWork(Level pLevel, BlockPos pPos) {
        if (!pLevel.isClientSide()) {

            pLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK,Blocks.SANDSTONE.defaultBlockState()),pPos.getX(),pPos.getY(),pPos.getZ(),pLevel.random.nextInt(),pLevel.random.nextInt(),pLevel.random.nextInt());

            if (RandomUtil.percentChance(0.8)) {
                pLevel.setBlockAndUpdate(pPos.above(), ModBlocks.SUSPICIOUS_SAND.get().defaultBlockState());


            }

            if (RandomUtil.percentChance(0.2)) {
                pLevel.setBlockAndUpdate(pPos.above(), Blocks.SAND.defaultBlockState());


            }
        }



    }


    public static void playSound(Level pLevel, BlockPos pPos, SoundEvent pSound) {
        pLevel.playSound((Player) null, pPos, pSound, SoundSource.MASTER, 0.5F, 1.0F);
    }

    public List<ArchaeologicalGeneratorBlockEntity.BeaconBeamSection> getBeamSections() {
        return (List<ArchaeologicalGeneratorBlockEntity.BeaconBeamSection>) (this.levels == 0 ? ImmutableList.of() : this.beamSections);
    }


    public static class BeaconBeamSection {
        /**
         * The colors of this section of a beacon beam, in RGB float format.
         */
        final float[] color;
        private int height;

        public BeaconBeamSection(float[] pColor) {
            this.color = pColor;
            this.height = 1;
        }

        protected void increaseHeight() {
            ++this.height;
        }

        /**
         * @return The colors of this section of a beacon beam, in RGB float format.
         */
        public float[] getColor() {
            return this.color;
        }

        public int getHeight() {
            return this.height;
        }
    }
}