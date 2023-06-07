package com.rumaruka.gribtweaks.common.items;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

public class BrushItem extends DiggerItem {
    public BrushItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier,  Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_SHOVEL, pProperties);
    }
}
