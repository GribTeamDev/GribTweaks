package com.rumaruka.gribtweaks.common.block;

import com.rumaruka.gribtweaks.common.tiles.ArchaeologicalGeneratorBlockEntity;
import com.rumaruka.gribtweaks.common.tiles.StatueOBJBlockEntity;
import com.rumaruka.gribtweaks.init.GTTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.Block;
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


    @javax.annotation.Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createArcGeneratorTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends ArchaeologicalGeneratorBlockEntity> p_151990_) {
        return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, ArchaeologicalGeneratorBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ArchaeologicalGeneratorBlockEntity(pPos,pState);
    }
}
