package com.rumaruka.gribtweaks.net;
import com.rumaruka.gribtweaks.GribTweaks;
import com.rumaruka.gribtweaks.net.packet.StormStrengthPacket;
import com.rumaruka.gribtweaks.net.packet.WeatherPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
public class NetworkHandler {
    private static NetworkHandler instance = null;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel network = NetworkRegistry.ChannelBuilder.named(new ResourceLocation("grib_network"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();
    private static int id = 0;
    public NetworkHandler() {

    }

    public static int nextID() {
        return id++;
    }
    public static void initialize() {
        if (instance != null) {
            return;
        }

        instance = new NetworkHandler();
        network.registerMessage(nextID(), WeatherPacket.class, WeatherPacket::encode, WeatherPacket::decode, WeatherPacket.Handler::handle);
        network.registerMessage(nextID(), StormStrengthPacket.class, StormStrengthPacket::encode, StormStrengthPacket::decode, StormStrengthPacket.Handler::handle);
    }
    public static NetworkHandler getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Attempt to call network getInstance before network is setup");
        }

        return instance;
    }
    public static void sendTo(ServerPlayer playerMP, Object toSend) {
        network.sendTo(toSend, playerMP.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object msg) {
        network.sendToServer(msg);
    }

    public  void sendToDimension(Object packet, ServerLevel serverLevel, ResourceKey<Level> dimension) {
        PlayerList playerList = serverLevel.getServer().getPlayerList();
        for (int i = 0; i < playerList.getPlayerCount(); ++i) {
            ServerPlayer serverPlayer = playerList.getPlayers().get(i);
            if (serverPlayer.level.dimension() == dimension) {
                sendTo(serverPlayer, packet);
            }
        }
    }

    /*
     * Used to update TESRs
     */
    public static void sendToTracking(ServerLevel level, BlockPos blockPos, Packet<?> packet, boolean boundaryOnly) {
        level.getChunkSource().chunkMap.getPlayers(new ChunkPos(blockPos), boundaryOnly).forEach(p -> p.connection.send(packet));
    }
}
