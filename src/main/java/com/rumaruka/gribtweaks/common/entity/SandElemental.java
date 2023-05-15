package com.rumaruka.gribtweaks.common.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class SandElemental extends Monster {
    protected SandElemental(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


}
