package com.rumaruka.gribtweaks.common.event;

import com.rumaruka.gribtweaks.init.GTBlocks;
import com.rumaruka.gribtweaks.init.GTItems;
import com.rumaruka.gribtweaks.util.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SandBrushIterationEvent {


    @SubscribeEvent
    public static void onSandBrushingEvent(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        LevelAccessor world = event.getLevel();
        Player player = event.getPlayer();
        BlockState state = world.getBlockState(pos);
        ItemStack brush = player.getMainHandItem();
        Inventory inventory = player.getInventory();
        if (state.getBlock() == Blocks.SAND && (brush.getItem() == GTItems.primitive_brush.get() || brush.getItem() == GTItems.copper_brush.get())) {
            brush.hurtAndBreak(1, player, (e) ->
                    e.broadcastBreakEvent(EquipmentSlot.MAINHAND)
            );

            if (RandomUtil.percentChance(0.4)) {
                inventory.add(GTItems.sand_copper.get().getDefaultInstance());
            }
            if (RandomUtil.percentChance(0.2)) {
                inventory.add(GTItems.sand_stone_pieces.get().getDefaultInstance());
            }
            if (RandomUtil.percentChance(0.4)) {
                inventory.add(GTItems.stone_pieces.get().getDefaultInstance());
            }
        }

        if (state.getBlock() == GTBlocks.suspection_sand.get() && brush.getItem() == GTItems.copper_brush.get()) {
            brush.hurtAndBreak(1, player, (e) ->
                    e.broadcastBreakEvent(EquipmentSlot.MAINHAND)
            );
            if (RandomUtil.percentChance(0.15)) {
                inventory.add(GTItems.sand_iron.get().getDefaultInstance());
            }
            if (RandomUtil.percentChance(0.25)) {
                inventory.add(GTItems.andezit_pieces.get().getDefaultInstance());
            }
            if (RandomUtil.percentChance(0.15)) {
                inventory.add(GTItems.sand_zinc.get().getDefaultInstance());
            }
            if (RandomUtil.percentChance(0.25)) {
                inventory.add(GTItems.sand_copper.get().getDefaultInstance());
            }
            if (RandomUtil.percentChance(0.1)) {
                inventory.add(GTItems.sand_stone_pieces.get().getDefaultInstance());
            }
            if (RandomUtil.percentChance(0.1)) {
                inventory.add(GTItems.stone_pieces.get().getDefaultInstance());
            }
        }
    }
}
