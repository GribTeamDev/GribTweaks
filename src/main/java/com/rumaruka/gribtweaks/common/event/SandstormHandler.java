package com.rumaruka.gribtweaks.common.event;

import com.rumaruka.gribtweaks.common.block.SandLayersBlock;
import com.rumaruka.gribtweaks.config.GTConfig;
import com.rumaruka.gribtweaks.init.GTBlocks;
import com.rumaruka.gribtweaks.net.NetworkHandler;
import com.rumaruka.gribtweaks.net.packet.StormStrengthPacket;
import com.rumaruka.gribtweaks.net.packet.WeatherPacket;
import com.rumaruka.gribtweaks.util.DimensionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

public class SandstormHandler {
    public static final SandstormHandler INSTANCE = new SandstormHandler();
    public int stormTime;

    public float prevStormStrength;
    public float stormStrength;
    private long lastUpdateTime;

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel && serverLevel.dimension() == Level.OVERWORLD &&  (serverLevel.isRaining()|| serverLevel.isThundering())) {
            this.stormStrength = 1.0F;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.level instanceof ServerLevel serverLevel && serverLevel.dimension() == Level.OVERWORLD && (serverLevel.isRaining()|| serverLevel.isThundering())) {
            event.player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 2, false, false));

        }
    }

    @SubscribeEvent
    public void onPreServerTick(TickEvent.LevelTickEvent event) {
        if (event.level.dimension() == Level.OVERWORLD) {
            updateWeather(event.level);
        }
    }

    private boolean canPlaceSandAt(ServerLevel serverLevel, BlockPos pos) {
        BlockState state = serverLevel.getBlockState(pos.below());
        return (state.getBlock() != Blocks.SAND) || !state.is(BlockTags.LEAVES) || !state.is(Blocks.WATER)  && DimensionHelper.canPlaceSandLayer(serverLevel, pos);
    }

    public void updateWeather(Level level) {
        if (level instanceof ServerLevel serverLevel && !level.isClientSide) {
            ServerLevelData worldInfo = serverLevel.getServer().getWorldData().overworldData();
            int cleanWeatherTime = worldInfo.getClearWeatherTime();

            if (cleanWeatherTime > 0) {
                --cleanWeatherTime;
                this.stormTime =  (serverLevel.isRaining()|| serverLevel.isThundering()) ? 1 : 2;
            }

            if (this.stormTime <= 0) {
                if (level.isThundering()|| level.isRaining()  ) {
                    this.stormTime = serverLevel.random.nextInt(4000) + 4000;
                }
                NetworkHandler.getInstance().sendToDimension(new WeatherPacket(this.stormTime), serverLevel, Level.OVERWORLD);
            } else {
                this.stormTime--;
                if (this.stormTime <= 0) {
                    level.setRainLevel(0);
                }
            }

            worldInfo.setClearWeatherTime(cleanWeatherTime);

            this.prevStormStrength = this.stormStrength;
            if (level.isThundering()|| level.isRaining()) {
                this.stormStrength += 2.0F / (float) (20 * GTConfig.GENERAL.sandstormTransitionTime.get());
            } else {
                this.stormStrength -= 2.0F / (float) (20 * GTConfig.GENERAL.sandstormTransitionTime.get());
            }
            this.stormStrength = Mth.clamp(this.stormStrength, 0.0F, 1.0F);

            if (this.stormStrength != this.prevStormStrength || this.lastUpdateTime < System.currentTimeMillis() - 500) {
                NetworkHandler.getInstance().sendToDimension(new StormStrengthPacket(this.stormStrength), serverLevel, Level.OVERWORLD);
                this.lastUpdateTime = System.currentTimeMillis();
            }

            try {
                if (GTConfig.GENERAL.sandstormSandLayerChance.get() > 0 && serverLevel.random.nextInt(GTConfig.GENERAL.sandstormSandLayerChance.get()) == 0) {
                    if (this.stormStrength > 0.9F) {
                        ChunkMap chunkManager = serverLevel.getChunkSource().chunkMap;

                        chunkManager.getChunks().forEach(chunkHolder -> {
                            Optional<LevelChunk> optionalChunk = chunkHolder.getEntityTickingChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK).left();
                            if (optionalChunk.isPresent()) {
                                ChunkPos chunkPos = optionalChunk.get().getPos();
                                if (!chunkManager.getPlayersCloseForSpawning(chunkPos).isEmpty()) {
                                    BlockPos pos = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, serverLevel.getBlockRandomPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ(), 15));
                                    BlockPos posDown = pos.below();

                                    if (serverLevel.isAreaLoaded(posDown, 1)) {
                                        BlockState sandState = serverLevel.getBlockState(pos);
                                        BlockState belowState = serverLevel.getBlockState(posDown);
                                        if (sandState.getBlock() == GTBlocks.sand_layer.get() && belowState.getBlock() != Blocks.WATER) {
                                            int layers = sandState.getValue(SandLayersBlock.LAYERS);
                                            if (layers <= 8) {
                                                serverLevel.setBlockAndUpdate(pos, sandState.setValue(SandLayersBlock.LAYERS, ++layers));
                                            }
                                        } else if (this.canPlaceSandAt(serverLevel, pos) ) {
                                            serverLevel.setBlockAndUpdate(pos, GTBlocks.sand_layer.get().defaultBlockState());
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
