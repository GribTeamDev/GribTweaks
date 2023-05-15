package com.rumaruka.gribtweaks.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Material;

public class BadGrassOnEntityTickUpdateProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (world.getBlockState(new BlockPos(Math.floor(entity.getX()), Math.floor(entity.getY()), Math.floor(entity.getZ()))).getMaterial() == Material.PLANT) {
            world.destroyBlock(new BlockPos(Math.floor(entity.getX()), Math.floor(entity.getY()), Math.floor(entity.getZ())), false);
        }
        if (world.getBlockState(new BlockPos(Math.floor(entity.getX()), Math.floor(entity.getY()) + 1.0, Math.floor(entity.getZ()))).getMaterial() == Material.PLANT) {
            world.destroyBlock(new BlockPos(Math.floor(entity.getX()), Math.floor(entity.getY()) + 1.0, Math.floor(entity.getZ())), false);
        }
    }
}
