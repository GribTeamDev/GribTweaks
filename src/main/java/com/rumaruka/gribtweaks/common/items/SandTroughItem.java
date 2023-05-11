package com.rumaruka.gribtweaks.common.items;

import com.rumaruka.gribtweaks.util.RandomUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SandTroughItem extends Item {
    public SandTroughItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getMainHandItem();
        if (RandomUtil.percentChance(0.7)) {

            player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STRING));



        }
        if (RandomUtil.percentChance(0.3)) {

            player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FEATHER));


        }
        return InteractionResultHolder.consume(stack);
    }
}
