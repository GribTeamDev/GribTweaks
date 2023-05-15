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
    public static final int GROUND_LEVEL = 63;

    public static List<Block> getSurfaceBlocks() {
        return Lists.newArrayList(Blocks.SAND);
    }

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

    public static int getSkyColorWithTemperatureModifier(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    public static boolean canPlaceSandLayer(ServerLevel world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        BlockState stateDown = world.getBlockState(pos.below());
        Optional<ResourceKey<Biome>> biomeKey = world.registryAccess().registryOrThrow(ForgeRegistries.BIOMES.getRegistryKey()).getResourceKey(world.getBiome(pos).value());
        return (biomeKey.isPresent() && biomeKey.get() == Biomes.DESERT)
                && world.isEmptyBlock(pos.above())
                && state.getMaterial().isReplaceable()
                && Block.canSupportRigidBlock(world, pos.below())
                && !(stateDown.getBlock() instanceof SandLayersBlock)
                && !(state.getBlock() instanceof SandLayersBlock);
    }

    /**
     * Only use when world#getHeight is not working
     *
     * @param world the world
     * @param pos original pos
     * @return surface pos
     */
    public static BlockPos getSurfacePos(Level world, BlockPos pos) {
        while (pos.getY() > 1 && world.isEmptyBlock(pos.below())) {
            pos = pos.below();
        }
        while (!world.canSeeSky(pos)) {
            pos = pos.above();
        }
        return pos;
    }


}