package com.rumaruka.gribtweaks.common.event;

import com.rumaruka.gribtweaks.init.GTBlocks;
import com.rumaruka.gribtweaks.init.GTItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import shadows.gateways.event.GateEvent;

@Mod.EventBusSubscriber
public class GateWayEvents {
    public static boolean isWaveStopped;
    public static boolean isWaveCompleted;
    public static boolean isHopeCatalystRightClick;
    public static boolean isForestCatalystRightClick;


    public static Minecraft mc;
    @SubscribeEvent
    public static void onGateStop(GateEvent.Opened event) {

        isWaveStopped = false;
        isWaveCompleted = false;

    }

    @SubscribeEvent
    public static void onGateStop(GateEvent.Failed event) {

        Player player = mc.player;
        assert player != null;
        Inventory inventory = player.getInventory();

        if (isHopeCatalystRightClick) {
            isWaveStopped = true;
            isHopeCatalystRightClick = false;
            inventory.add(new ItemStack(GTItems.DAMAGE_HOPE_CATALYST.get()));
        }
        if (isForestCatalystRightClick) {
            isWaveStopped = true;
            isForestCatalystRightClick = false;
            inventory.add(new ItemStack(GTItems.DAMAGE_FOREST_CATALYST.get()));
        }
    }

    @SubscribeEvent
    public static void onGateComplete(GateEvent.Completed event) {


        if (isHopeCatalystRightClick) {
            isWaveCompleted = true;
            isHopeCatalystRightClick = false;

        }
        if (isForestCatalystRightClick) {
            isWaveCompleted = true;
            isForestCatalystRightClick = false;

        }
    }

    @SubscribeEvent
    public static void onInteraction(PlayerInteractEvent.RightClickBlock event) {

        BlockPos blockPos = event.getHitVec().getBlockPos();
        if (Minecraft.getInstance().level != null) {
            BlockState state = Minecraft.getInstance().level.getBlockState(blockPos);
            if (state.getBlock() == GTBlocks.statue_obj.get()) {
                if (event.getItemStack().getItem() == GTItems.HOPE_CATALYST.get()) {
                    isHopeCatalystRightClick = true;
                }
                if (event.getItemStack().getItem() == GTItems.FOREST_CATALYST.get()) {
                    isForestCatalystRightClick = true;
                }
            }

        }


    }

    @SubscribeEvent
    public static void onDrop(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player ) {
            if (isWaveStopped) {
                isWaveStopped = false;
            }

        }
    }


}

