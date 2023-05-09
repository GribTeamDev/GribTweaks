package com.rumaruka.gribtweaks.common.event;


import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GTEvents {


    @SubscribeEvent
    public static void onInteraction(PlayerInteractEvent event){
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        BlockState state = level.getBlockState(pos);
        ItemStack sand_knife = player.getMainHandItem();
        ItemStack sand_bucket = player.getOffhandItem();
        if (sand_knife.getItem()== Items.DIAMOND_SWORD&&sand_bucket.getItem() == Items.BUCKET){
            if (state.getBlock()== Blocks.CACTUS){
                player.setItemInHand(InteractionHand.MAIN_HAND,new ItemStack(Items.WATER_BUCKET));
                level.removeBlock(pos,true);
            }
        }
    }
}
