package com.rumaruka.gribtweaks.common.event;


import com.rumaruka.gribtweaks.common.block.watercompost.LayeredWaterCompost;
import com.rumaruka.gribtweaks.init.GTBlocks;
import com.rumaruka.gribtweaks.init.GTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InteractionEvent {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        ItemStack bucket = event.getItemStack();
        Level world = event.getLevel();
        Inventory inventory = player.getInventory();
        //fill
        BlockState compost = world.getBlockState(pos);
        if (compost.getBlock() == GTBlocks.compost.get()) {
            if (bucket.getItem() == Items.WATER_BUCKET) {

                world.setBlockAndUpdate(pos, GTBlocks.compost_water.get().defaultBlockState().setValue(LayeredWaterCompost.LEVEL, 3));
                inventory.add( new ItemStack(Items.BUCKET));
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);


            }

            if (bucket.getItem() == GTItems.water_sand_bucket.get()) {

                world.setBlockAndUpdate(pos, GTBlocks.compost_water.get().defaultBlockState().setValue(LayeredWaterCompost.LEVEL, 3));
                inventory.add(new ItemStack(GTItems.sand_bucket.get()));
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);


            }
            if (bucket.getItem() == GTItems.water_wooden_bucket.get()) {

                world.setBlockAndUpdate(pos, GTBlocks.compost_water.get().defaultBlockState().setValue(LayeredWaterCompost.LEVEL, 3));
                inventory.add(new ItemStack(GTItems.wooden_bucket.get()));
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);


            }
        }
        if (compost.getBlock() == GTBlocks.compost_water.get()) {
            if (bucket.getItem() == Items.BUCKET) {

                world.setBlockAndUpdate(pos, GTBlocks.compost.get().defaultBlockState());
                inventory.add(new ItemStack(Items.WATER_BUCKET));
                bucket.shrink(1);
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);


            }
            if (bucket.getItem() == GTItems.sand_bucket.get()) {

                world.setBlockAndUpdate(pos, GTBlocks.compost.get().defaultBlockState());
                inventory.add(new ItemStack(GTItems.water_sand_bucket.get()));
                bucket.shrink(1);
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);


            }
            if (bucket.getItem() == GTItems.wooden_bucket.get()) {

                world.setBlockAndUpdate(pos, GTBlocks.compost.get().defaultBlockState());
                inventory.add(new ItemStack(GTItems.water_wooden_bucket.get()));
                bucket.shrink(1);
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);


            }
        }

    }
}
