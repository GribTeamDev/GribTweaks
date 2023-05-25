package com.rumaruka.gribtweaks.common.event;

import com.rumaruka.gribtweaks.init.GTBlocks;
import com.rumaruka.gribtweaks.init.GTItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
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
    public static boolean isCatalystRightClick;

    public static ItemStack stack;

    @SubscribeEvent
    public static void onGateStop(GateEvent.Opened event) {

        isWaveStopped = false;
        isWaveCompleted = false;

    }

    @SubscribeEvent
    public static void onGateStop(GateEvent.Failed event) {


        if (isCatalystRightClick) {
            isWaveStopped = true;
            isCatalystRightClick = false;

        }
    }

    @SubscribeEvent
    public static void onGateComplete(GateEvent.Completed event) {


        if (isCatalystRightClick) {
            isWaveCompleted = true;
            isCatalystRightClick = false;

        }

    }

    @SubscribeEvent
    public static void onInteraction(PlayerInteractEvent.RightClickBlock event) {
        stack = event.getItemStack();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        if (Minecraft.getInstance().level != null) {
            BlockState state = Minecraft.getInstance().level.getBlockState(blockPos);
            if (state.getBlock() == GTBlocks.statue_obj.get()) {
                if (stack.getItem() == GTItems.HOPE_CATALYST.get() || stack.getItem() == GTItems.FOREST_CATALYST.get()) {
                    isCatalystRightClick = true;
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

