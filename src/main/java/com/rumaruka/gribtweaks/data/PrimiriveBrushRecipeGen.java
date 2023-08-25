package com.rumaruka.gribtweaks.data;

import com.rumaruka.gribtweaks.init.GTItems;
import com.rumaruka.gribtweaks.init.GTRecipeType;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;

public class PrimiriveBrushRecipeGen extends ProcessingRecipeGen {
    GeneratedRecipe

            SAND_COPPER = create("sand_item", GTItems.sand_copper::get, b->b.output(Items.RAW_COPPER)),
            SAND_ZINC = create("sand_item", GTItems.sand_zinc::get, b->b.output(AllItems.RAW_ZINC.get())),
            SAND_REDSTONE = create("sand_item", GTItems.sand_redstone::get, b->b.output(Items.REDSTONE)),
            SAND_LAPIS = create("sand_item", GTItems.sand_lapis::get, b->b.output(Items.LAPIS_LAZULI)),
            SAND_IRON = create("sand_item", GTItems.sand_iron::get, b->b.output(Items.RAW_IRON)),
            SAND_GOLD = create("sand_item", GTItems.sand_gold::get, b->b.output(Items.RAW_GOLD))
            ;




    public PrimiriveBrushRecipeGen(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected GTRecipeType getRecipeType() {
        return GTRecipeType.PRIMITIVE_BRUSH;
    }
}