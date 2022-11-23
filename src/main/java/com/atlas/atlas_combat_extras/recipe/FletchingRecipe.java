package com.atlas.atlas_combat_extras.recipe;

import com.atlas.atlas_combat_extras.AtlasCombatExtras;
import com.atlas.atlas_combat_extras.container.FletchingMenu;
import com.google.gson.JsonObject;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class FletchingRecipe implements Recipe<Container> {
	public final Ingredient stick;
	public final Ingredient flint;
	public final Ingredient feather;
	public final Ingredient potion;
	final ItemStack result;
	private final ResourceLocation id;

	public FletchingRecipe(ResourceLocation resourceLocation, Ingredient ingredient, Ingredient ingredient2, Ingredient ingredient3, ItemStack itemStack) {
		this.id = resourceLocation;
		this.stick = ingredient;
		this.flint = ingredient2;
		this.feather = ingredient3;
		this.potion =  Ingredient.EMPTY;
		this.result = itemStack;
	}
	public FletchingRecipe(ResourceLocation resourceLocation, Ingredient ingredient, Ingredient ingredient2, Ingredient ingredient3, Ingredient ingredient4, ItemStack itemStack) {
		this.id = resourceLocation;
		this.stick = ingredient;
		this.flint = ingredient2;
		this.feather = ingredient3;
		this.potion = ingredient4;
		this.result = itemStack;
	}

	@Override
	public boolean matches(Container inventory, Level world) {
		return this.stick.test(inventory.getItem(0)) && this.flint.test(inventory.getItem(1)) && this.feather.test(inventory.getItem(3)) && this.potion.test(inventory.getItem(2));
	}

	@Override
	public ItemStack assemble(Container inventory) {
		ItemStack itemStack = this.result.copy();
		CompoundTag compoundTag = inventory.getItem(0).getTag();
		if (compoundTag != null) {
			itemStack.setTag(compoundTag.copy());
		}

		return itemStack;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(Blocks.FLETCHING_TABLE);
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return AtlasCombatExtras.FLETCHING_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return AtlasCombatExtras.FLETCHING_TYPE;
	}

	@Override
	public boolean isIncomplete() {
		return Stream.of(this.stick).anyMatch(ingredient -> ingredient.getItems().length == 0);
	}

	public static class Serializer implements RecipeSerializer<FletchingRecipe> {
		public FletchingRecipe fromJson(@NotNull ResourceLocation resourceLocation, @NotNull JsonObject jsonObject) {
			Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "stick"));
			Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "flint"));
			Ingredient ingredient3 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "feather"));
			ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
			return new FletchingRecipe(resourceLocation, ingredient, ingredient2, ingredient3, itemStack);
		}

		public FletchingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
			Ingredient ingredient = Ingredient.fromNetwork(friendlyByteBuf);
			Ingredient ingredient2 = Ingredient.fromNetwork(friendlyByteBuf);
			Ingredient ingredient3 = Ingredient.fromNetwork(friendlyByteBuf);
			ItemStack itemStack = friendlyByteBuf.readItem();
			return new FletchingRecipe(resourceLocation, ingredient, ingredient2, ingredient3, itemStack);
		}

		public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, FletchingRecipe upgradeRecipe) {
			upgradeRecipe.stick.toNetwork(friendlyByteBuf);
			upgradeRecipe.flint.toNetwork(friendlyByteBuf);
			upgradeRecipe.feather.toNetwork(friendlyByteBuf);
			friendlyByteBuf.writeItem(upgradeRecipe.result);
		}
	}
}
