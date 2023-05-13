package com.rumaruka.gribtweaks.common.block;

import com.rumaruka.gribtweaks.common.tiles.ArchaeologicalGeneratorBlockEntity;
import com.rumaruka.gribtweaks.common.tiles.StatueOBJBlockEntity;
import com.rumaruka.gribtweaks.init.GTTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class ArchaeologicalGeneratorBlock extends BaseEntityBlock implements BeaconBeamBlock {
    public ArchaeologicalGeneratorBlock( ) {
        super(Properties.of(Material.STONE));
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {

        return createArcGeneratorTicker(p_153212_, p_153214_, GTTiles.arch_block.get());
    }
    public DyeColor getColor() {
        return DyeColor.BROWN;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @javax.annotation.Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createArcGeneratorTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends ArchaeologicalGeneratorBlockEntity> p_151990_) {
        return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, ArchaeologicalGeneratorBlockEntity::tick);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof ArchaeologicalGeneratorBlockEntity archaeologicalGeneratorBlockEntity){
            if (ArchaeologicalGeneratorBlockEntity.isActive){
                addSandParticles(pLevel,pPos,15);
            }
        }


        super.animateTick(pState, pLevel, pPos, pRandom);
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
                    p_40639_.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SAND.defaultBlockState()), d6, d7, d8, d2, d3, d4);
                }
            }

        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ArchaeologicalGeneratorBlockEntity(pPos,pState);
    }
}
