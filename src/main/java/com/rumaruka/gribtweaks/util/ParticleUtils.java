package com.rumaruka.gribtweaks.util;

import com.rumaruka.gribtweaks.client.SandstormRender;
import com.rumaruka.gribtweaks.config.GTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class ParticleUtils {
    public static List<BlockPos> fungusposlist = new ArrayList<>();
    public static List<BlockPos> fungusblockposlist = new ArrayList<>();
    public static List<BlockPos> soulsandposlist = new ArrayList<>();
    public static int netherweathertimer = 0;
    public static boolean weathertoggle = true;

    public static int timerval = 0;

    public static void spawnweatherparticles() {
        Minecraft instance2 = Minecraft.getInstance();
        Player player2 = instance2.player;
        Level world2 = instance2.level;
        assert player2 != null;
        assert world2 != null;
        BlockPos pos = player2.blockPosition();
        RandomSource random = null;

            random = instance2.level.random;


            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double x = (double) i + random.nextDouble();
            double y = (double) j + random.nextDouble();
            double z = (double) k + random.nextDouble();

            for (double hj = 0; hj < Math.ceil(GTConfig.GENERAL.particleamount.get() * ((GTConfig.GENERAL.particleDensity.get() * 4))); hj++) {
                if ((SandstormRender.active) && !instance2.isPaused()) {
                    for (int l = 0; l < 10; ++l) {
                        for (int i1 = 1; i1 <= GTConfig.GENERAL.sandmotemodifier.get(); ++i1) {
                            if (MiscUtil.forloopdecimalizer(GTConfig.GENERAL.sandmotemodifier.get(), i1)) {


                                    if (world2.getBiome(new BlockPos(Mth.lerp(world2.random.nextDouble(), x - 128, x + 128), Mth.lerp(world2.random.nextDouble(), 128, 456), Mth.lerp(world2.random.nextDouble(), z - 128, z + 128))).is(Biomes.DESERT)) {
                                        for (int m = 0; m < 360; m++) {
                                            float radian = (float) (m* Math.PI / 180);
                                            double xp = x + Math.cos(radian);
                                            double zp = z + Math.sin(radian) ;
                                            world2.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), Mth.lerp(world2.random.nextDouble(), xp - m, xp +m), Mth.lerp(world2.random.nextDouble(), y-m, y+m), Mth.lerp(world2.random.nextDouble(), zp - m, zp + m), 1, 1, 1);

                                        }



                                    }
                                }

                            }
                        }
                    }
                    ++timerval;


            }


            fungusblockposlist.clear();
            fungusposlist.clear();
            soulsandposlist.clear();
        }



}

