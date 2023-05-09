package com.rumaruka.gribtweaks.data;

import com.rumaruka.gribtweaks.init.GTItems;
import com.rumaruka.gribtweaks.init.GTRecipeType;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;

public class PrimiriveBrushRecipeGen extends ProcessingRecipeGen {
    GeneratedRecipe

            TEST = create("test",() -> Items.GOLD_BLOCK, b -> b.output(AllItems.POLISHED_ROSE_QUARTZ.get())),
            SAND_COPPER = create("sand_copper", GTItems.sand_copper::get, b->b.output(Items.RAW_COPPER))
            ;

    public PrimiriveBrushRecipeGen(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected GTRecipeType getRecipeType() {
        return GTRecipeType.PRIMITIVE_BRUSH;
    }
}