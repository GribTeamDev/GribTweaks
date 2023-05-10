package com.rumaruka.gribtweaks.common.block;

import com.rumaruka.gribtweaks.init.GTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class CompostWaterBlock extends AbstractCauldronBlock {



    public CompostWaterBlock(BlockBehaviour.Properties p_51403_) {
        super(p_51403_, CauldronInteraction.EMPTY);
    }

    public boolean isFull(BlockState pState) {
        return false;
    }

    protected static boolean shouldHandlePrecipitation(Level pLevel, Biome.Precipitation pPrecipitation) {
        if (pPrecipitation == Biome.Precipitation.RAIN) {
            return pLevel.getRandom().nextFloat() < 0.4F;

        } else {
            return false;
        }
    }

    public void handlePrecipitation(BlockState pState, Level pLevel, BlockPos pPos, Biome.Precipitation pPrecipitation) {
        if (shouldHandlePrecipitation(pLevel, pPrecipitation)) {
            if (pPrecipitation == Biome.Precipitation.RAIN) {
                pLevel.setBlockAndUpdate(pPos, GTBlocks.compost_water.get().defaultBlockState());
                pLevel.gameEvent((Entity) null, GameEvent.BLOCK_CHANGE, pPos);
            }

        }
    }

    protected boolean canReceiveStalactiteDrip(Fluid pFluid) {
        return true;
    }

    protected void receiveStalactiteDrip(BlockState pState, Level pLevel, BlockPos pPos, Fluid pFluid) {
        if (pFluid == Fluids.WATER) {
            BlockState blockstate =  GTBlocks.compost_water.get().defaultBlockState();
            pLevel.setBlockAndUpdate(pPos, blockstate);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(blockstate));
            pLevel.levelEvent(1047, pPos, 0);
        }

    }
}