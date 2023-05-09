package com.rumaruka.gribtweaks.common.recipe;

import com.rumaruka.gribtweaks.init.GTRecipeType;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
@ParametersAreNonnullByDefault
public class PrimitiveBrushRecipe extends ProcessingRecipe<PrimitiveBrushRecipe.SandPaperInv> {

    public PrimitiveBrushRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(GTRecipeType.PRIMITIVE_BRUSH, params);
    }

    @Override
    public boolean matches(PrimitiveBrushRecipe.SandPaperInv inv, Level worldIn) {
        return ingredients.get(0)
                .test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }

    public static boolean canPolish(Level world, ItemStack stack) {
        return !getMatchingRecipes(world, stack).isEmpty();
    }

    public static ItemStack applyPolish(Level world, Vec3 position, ItemStack stack, ItemStack sandPaperStack) {
        List<Recipe<PrimitiveBrushRecipe.SandPaperInv>> matchingRecipes = getMatchingRecipes(world, stack);
        if (!matchingRecipes.isEmpty())
            return matchingRecipes.get(0)
                    .assemble(new PrimitiveBrushRecipe.SandPaperInv(stack))
                    .copy();
        return stack;
    }

    public static List<Recipe<PrimitiveBrushRecipe.SandPaperInv>> getMatchingRecipes(Level world, ItemStack stack) {
        return world.getRecipeManager()
                .getRecipesFor(GTRecipeType.PRIMITIVE_BRUSH.getType(), new PrimitiveBrushRecipe.SandPaperInv(stack), world);
    }

    public static class SandPaperInv extends RecipeWrapper {

        public SandPaperInv(ItemStack stack) {
            super(new ItemStackHandler(1));
            inv.setStackInSlot(0, stack);
        }

    }

}
