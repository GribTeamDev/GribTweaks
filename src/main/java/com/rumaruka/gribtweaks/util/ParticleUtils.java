package com.rumaruka.gribtweaks.util;

import com.rumaruka.gribtweaks.client.SandstormRender;
import com.rumaruka.gribtweaks.config.GTConfig;
import com.rumaruka.gribtweaks.init.GTParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;

public class ParticleUtils {
    public static int timerval = 0;

    public static void spawnweatherparticles() {
        Minecraft instance2 = Minecraft.getInstance();
        Player player2 = instance2.player;
        Level world2 = instance2.level;
        assert player2 != null;
        assert world2 != null;
        BlockPos pos = player2.blockPosition();
        RandomSource random;
            random = instance2.level.random;
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double x = (double) i + random.nextDouble();
            double y = (double) j + random.nextDouble();
            double z = (double) k + random.nextDouble();
            for (double hj = 0; hj < Math.ceil(8 * ((GTConfig.GENERAL.particleDensity.get() * 4))); hj++) {
                if ((SandstormRender.active) && !instance2.isPaused()) {
                    for (int l = 0; l < 4; ++l) {
                        for (int i1 = 1; i1 <= GTConfig.GENERAL.sandmotemodifier.get(); ++i1) {
                            if (MiscUtil.forloopdecimalizer(GTConfig.GENERAL.sandmotemodifier.get(), i1)) {
                                    if (world2.getBiome(new BlockPos(Mth.lerp(world2.random.nextDouble(), x - 40, x + 40),
                                            Mth.lerp(world2.random.nextDouble(), 64, 256),
                                            Mth.lerp(world2.random.nextDouble(), z - 40, z + 40))).is(Biomes.DESERT)) {
                                            world2.addParticle(GTParticles.SANDMOTE.get(),
                                                    Mth.lerp(world2.random.nextDouble(), x - 40, x + 40),
                                                    Mth.lerp(world2.random.nextDouble(), 63, 90),
                                                    Mth.lerp(world2.random.nextDouble(), z - 40, z + 40), 0f, 0f, 0f);
                                    }
                                }
                            }
                        }
                    }
                    ++timerval;
            }
        }



}

