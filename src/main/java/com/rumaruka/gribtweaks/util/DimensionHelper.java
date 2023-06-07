package com.rumaruka.gribtweaks.util;

import com.rumaruka.gribtweaks.GribTweaks;
import com.rumaruka.gribtweaks.common.block.SandLayersBlock;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.common.Mod;
import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = GribTweaks.MODID)
public class DimensionHelper {

    public static GribSaveData getData(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(GribSaveData::load, GribSaveData::new, GribSaveData.ID);
    }

    @SubscribeEvent
    public static void onSleepFinished(SleepFinishedTimeEvent event) {
        LevelAccessor world = event.getLevel();
        if (world instanceof ServerLevel serverLevel) {
            if (serverLevel.dimension() == Level.OVERWORLD) {
                if (world.getLevelData() instanceof DerivedLevelData) {
                    ((DerivedLevelData) world.getLevelData()).wrapped.setDayTime(event.getNewTime()); //Workaround for making sleeping work in Atum
                }
            }
        }
    }



    public static boolean canPlaceSandLayer(ServerLevel world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        BlockState stateDown = world.getBlockState(pos.below());
        Optional<ResourceKey<Biome>> biomeKey = world.registryAccess().registryOrThrow(ForgeRegistries.BIOMES.getRegistryKey()).getResourceKey(world.getBiome(pos).value());
        return (biomeKey.isPresent() && biomeKey.get() != Biomes.THE_VOID)
                && world.isEmptyBlock(pos.above())
                && state.getMaterial().isReplaceable()
                && Block.canSupportRigidBlock(world, pos.below())
                && !(stateDown.getBlock() instanceof SandLayersBlock)
                && !(state.getBlock() instanceof SandLayersBlock);
    }



}