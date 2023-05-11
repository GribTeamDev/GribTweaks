package com.rumaruka.gribtweaks.common.items;

import com.rumaruka.gribtweaks.util.RandomUtil;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
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
        Inventory inventory = player.getInventory();
        if (!level.isClientSide()) {
            if (RandomUtil.percentChance(0.7)) {

                inventory.add( new ItemStack(Items.STRING));
                player.awardStat(Stats.ITEM_USED.get(this));


            }
            if (RandomUtil.percentChance(0.3)) {

                inventory.add( new ItemStack(Items.FEATHER));

            }

            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack,level.isClientSide());
    }
}
