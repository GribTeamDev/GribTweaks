package com.rumaruka.gribtweaks.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemUtils {






    public static void spawn(Level world, double x, double y, double z, ItemStack stack) {
        double d = (double) EntityType.ITEM.getWidth();
        double e = 1.0 - d;
        double f = d / 2.0;
        double g = Math.floor(x) + world.random.nextDouble() * e + f;
        double h = Math.floor(y) + world.random.nextDouble() * e;
        double i = Math.floor(z) + world.random.nextDouble() * e + f;

        while(!stack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, g, h, i, stack.split(world.random.nextInt(21) + 10));
            float j = 0.05F;
            itemEntity.setPos(world.random.triangle(0.0, 0.11485000171139836), world.random.triangle(0.2, 0.11485000171139836), world.random.triangle(0.0, 0.11485000171139836));
            world.addFreshEntity(itemEntity);
        }

    }
}
