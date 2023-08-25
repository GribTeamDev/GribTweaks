package com.rumaruka.gribtweaks.common.tiles;

import com.rumaruka.gribtweaks.common.block.StatueOBJ;
import com.rumaruka.gribtweaks.common.event.GateWayEvents;
import com.rumaruka.gribtweaks.init.GTTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StatueOBJBlockEntity extends BlockEntity {

    public StatueOBJBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(GTTiles.statue_obj.get(), p_155229_, p_155230_);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, StatueOBJBlockEntity entity) {

        if ((GateWayEvents.isWaveStopped || GateWayEvents.isWaveCompleted) && state.getValue(StatueOBJ.ACTIVE) == Boolean.TRUE) {
            level.setBlockAndUpdate(pos, state.setValue(StatueOBJ.ACTIVE, false));

            GateWayEvents.isHopeCatalystRightClick = false;
            GateWayEvents.isForestCatalystRightClick = false;

        }


    }


}


