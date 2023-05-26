package com.rumaruka.gribtweaks.compat.jei.category;

import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rumaruka.gribtweaks.common.recipe.PrimitiveBrushRecipe;
import com.rumaruka.gribtweaks.init.GTItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;

import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.element.GuiGameElement;

import mezz.jei.api.constants.VanillaTypes;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class BrushCategory extends CreateRecipeCategory<PrimitiveBrushRecipe> {

    private final ItemStack renderedBrush;

    public BrushCategory(Info<PrimitiveBrushRecipe> info) {
        super(info);
        renderedBrush = new ItemStack(GTItems.primitive.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PrimitiveBrushRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, 27, 29)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));

        ProcessingOutput output = recipe.getRollableResults().get(0);
        builder
                .addSlot(RecipeIngredientRole.OUTPUT, 132, 29)
                .setBackground(getRenderedSlot(output), -1, -1)
                .addItemStack(output.getStack())
                .addTooltipCallback(addStochasticTooltip(output));
    }

    @Override
    public void draw(PrimitiveBrushRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(matrixStack, 61, 21);
        AllGuiTextures.JEI_LONG_ARROW.render(matrixStack, 52, 32);

        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        ItemStack[] matchingStacks = ingredients.get(0)
                .getItems();
        if (matchingStacks.length == 0)
            return;


        CompoundTag tag = renderedBrush.getOrCreateTag();
        tag.put("Polishing", matchingStacks[0].serializeNBT());
        tag.putBoolean("JEI", true);
        GuiGameElement.of(renderedBrush)
                .<GuiGameElement.GuiRenderBuilder>at(getBackground().getWidth() / 2 - 16, 0, 0)
                .scale(2)
                .render(matrixStack);
    }

}
