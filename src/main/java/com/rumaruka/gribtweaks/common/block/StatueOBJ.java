package com.rumaruka.gribtweaks.common.block;

import com.rumaruka.gribtweaks.init.GTTiles;
import com.rumaruka.gribtweaks.common.tiles.StatueOBJBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class StatueOBJ extends BaseEntityBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    ;


    public StatueOBJ() {
        super(Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {

        return createStatueTicker(p_153212_, p_153214_, GTTiles.statue_obj.get());
    }



    @javax.annotation.Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createStatueTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends StatueOBJBlockEntity> p_151990_) {
        return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, StatueOBJBlockEntity::serverTick);
    }

    public RenderShape getRenderShape(BlockState p_50950_) {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48725_) {
        p_48725_.add(ACTIVE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new StatueOBJBlockEntity(p_153215_, p_153216_);
    }
}