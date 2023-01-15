package com.atlas.atlas_combat_extras;

import com.atlas.atlas_combat_extras.recipe.FletchingRecipe;
import com.github.aws404.booking_it.RecipeBookAdder;
import net.minecraft.world.item.*;

import java.util.List;

public class FletchingRecipeBookAdder implements RecipeBookAdder {
    @Override
    public List<RecipeCategoryOptions> getCategories() {
        return List.of(
                RecipeBookAdder.builder("FLETCHING")
						.addSearch()
						.addGroup("ARROWS", recipe -> {
							if (recipe instanceof FletchingRecipe fletchingRecipe) {
								Item output = fletchingRecipe.getResultItem().getItem();
								return output instanceof ArrowItem && !(output instanceof SpectralArrowItem || output instanceof TippedArrowItem);
							}
							return false;
						}, "minecraft:arrow")
						.addGroup("TIPPED_ARROWS", recipe -> {
							if (recipe instanceof FletchingRecipe fletchingRecipe) {
								Item output = fletchingRecipe.getResultItem().getItem();
								return output instanceof SpectralArrowItem || output instanceof TippedArrowItem;
							}
							return false;
						}, "minecraft:spectral_arrow")
                        .build()
        );
    }
}
