package com.rumaruka.gribtweaks.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;

public class GribSaveData extends SavedData {
    public static final String ID = "gribtweaks_level_data";

    public boolean isStorming;


    public static GribSaveData load(@Nonnull CompoundTag nbt) {
        GribSaveData data = new GribSaveData();
        data.isStorming = nbt.getBoolean("IsStorming");


        return data;
    }

    @Override
    @Nonnull
    public CompoundTag save(@Nonnull CompoundTag nbt) {

        nbt.putBoolean("IsStorming", this.isStorming);

        return nbt;
    }


    public boolean isStorming() {
        return this.isStorming;
    }


    public void setStorming(boolean isStorming) {
        this.isStorming = isStorming;
        this.setDirty();
    }


}