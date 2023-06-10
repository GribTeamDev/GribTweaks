package com.rumaruka.gribtweaks.common.event;


import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class QuestComplete {
    //id quest  3385619146852805197L
    public static boolean isLock;

    @SubscribeEvent
    public static void onServerLevelTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        TeamData data = ServerQuestFile.INSTANCE.getData(player);
        long l = data.file.id;
        if (l == 3385619146852805197L) {
            isLock = data.isCompleted(data.file);
        }
    }
}
