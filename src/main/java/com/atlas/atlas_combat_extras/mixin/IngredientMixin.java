package com.atlas.atlas_combat_extras.mixin;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ingredient.class)
public class IngredientMixin {
	private static JsonObject object;
	@Inject(method = "valueFromJson", at = @At(value = "HEAD"))
	private static void injectJsonCapture(JsonObject json, CallbackInfoReturnable cir) {
		object = json;
	}
	@ModifyReturnValue(method = "valueFromJson", at = @At(value = "RETURN", ordinal = 0))
	private static Ingredient.Value modifyReturn(Ingredient.Value original) {
		return new Ingredient.ItemValue(ShapedRecipe.itemStackFromJson(object));
	}
}
