package com.atlas.atlas_combat_extras.recipe;

import com.atlas.atlas_combat_extras.AtlasCombatExtras;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpectralFletchingRecipe extends FletchingRecipe{
	public SpectralFletchingRecipe(ResourceLocation resourceLocation, Ingredient ingredient, Ingredient ingredient2, Ingredient ingredient3, Ingredient ingredient4, ItemStack itemStack) {
		super(resourceLocation, ingredient, ingredient2, ingredient3, ingredient4, itemStack);
	}

	@Override
	public boolean matches(Container inventory, Level world) {
		return this.stick.test(inventory.getItem(0)) && this.flint.test(inventory.getItem(1)) && this.feather.test(inventory.getItem(3)) && this.potion.test(inventory.getItem(2));
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return AtlasCombatExtras.SPECTRAL_FLETCHING_SERIALIZER;
	}
	public static class Serializer implements RecipeSerializer<FletchingRecipe> {
		public FletchingRecipe fromJson(@NotNull ResourceLocation resourceLocation, @NotNull JsonObject jsonObject) {
			Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "stick"));
			Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "flint"));
			Ingredient ingredient3 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "feather"));
			Ingredient ingredient4 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "potion"));
			ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
			return new SpectralFletchingRecipe(resourceLocation, ingredient, ingredient2, ingredient3, ingredient4, itemStack);
		}

		public FletchingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
			Ingredient ingredient = Ingredient.fromNetwork(friendlyByteBuf);
			Ingredient ingredient2 = Ingredient.fromNetwork(friendlyByteBuf);
			Ingredient ingredient3 = Ingredient.fromNetwork(friendlyByteBuf);
			Ingredient ingredient4 = Ingredient.fromNetwork(friendlyByteBuf);
			ItemStack itemStack = friendlyByteBuf.readItem();
			return new SpectralFletchingRecipe(resourceLocation, ingredient, ingredient2, ingredient3, ingredient4, itemStack);
		}

		public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, FletchingRecipe upgradeRecipe) {
			upgradeRecipe.stick.toNetwork(friendlyByteBuf);
			upgradeRecipe.flint.toNetwork(friendlyByteBuf);
			upgradeRecipe.feather.toNetwork(friendlyByteBuf);
			upgradeRecipe.potion.toNetwork(friendlyByteBuf);
			friendlyByteBuf.writeItem(upgradeRecipe.result);
		}
	}
}
