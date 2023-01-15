package com.atlas.atlas_combat_extras.recipe;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FletchingRecipeBookComponent extends RecipeBookComponent {

	@Override
	protected void initFilterButtonTextures() {
		this.filterButton.initTextureValues(152, 182, 28, 18, RECIPE_BOOK_LOCATION);
	}

	@Override
	public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
		ItemStack itemStack = recipe.getResultItem();
		this.ghostRecipe.setRecipe(recipe);
		this.ghostRecipe.addIngredient(Ingredient.of(itemStack), ((Slot)slots.get(0)).x, ((Slot)slots.get(0)).y);
		this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), recipe, recipe.getIngredients().iterator(), 0);
	}
}
