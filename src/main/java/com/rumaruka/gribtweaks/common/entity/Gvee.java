package com.rumaruka.gribtweaks.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class Gvee extends Monster implements PowerableMob {
    protected Gvee(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public boolean isPowered() {
        return true;
    }
}
