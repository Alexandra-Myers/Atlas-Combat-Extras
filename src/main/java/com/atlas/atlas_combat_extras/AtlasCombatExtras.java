package com.atlas.atlas_combat_extras;

import com.atlas.atlas_combat_extras.container.FletchingMenu;
import com.atlas.atlas_combat_extras.recipe.FletchingRecipe;
import com.atlas.atlas_combat_extras.recipe.SpectralFletchingRecipe;
import com.atlas.atlas_combat_extras.recipe.TippedFletchingRecipe;
import com.atlas.atlas_combat_extras.screens.inventory.FletchingScreen;
import net.alexandra.atlas.atlas_combat.item.KnifeItem;
import net.alexandra.atlas.atlas_combat.item.LongSwordItem;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtlasCombatExtras implements ModInitializer {
	public static final Item WOODEN_KNIFE = registerItem("wooden_knife", new KnifeItem(Tiers.WOOD, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item STONE_KNIFE = registerItem("stone_knife", new KnifeItem(Tiers.STONE, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item GOLDEN_KNIFE = registerItem("golden_knife", new KnifeItem(Tiers.GOLD, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item IRON_KNIFE = registerItem("iron_knife", new KnifeItem(Tiers.IRON, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item DIAMOND_KNIFE = registerItem("diamond_knife", new KnifeItem(Tiers.DIAMOND, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item NETHERITE_KNIFE = registerItem("netherite_knife", new KnifeItem(Tiers.NETHERITE, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT).fireproof()));
	public static final Item WOODEN_LONGSWORD = registerItem("wooden_longsword", new LongSwordItem(Tiers.WOOD, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item STONE_LONGSWORD = registerItem("stone_longsword", new LongSwordItem(Tiers.STONE, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item GOLDEN_LONGSWORD = registerItem("golden_longsword", new LongSwordItem(Tiers.GOLD, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item IRON_LONGSWORD = registerItem("iron_longsword", new LongSwordItem(Tiers.IRON, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item DIAMOND_LONGSWORD = registerItem("diamond_longsword", new LongSwordItem(Tiers.DIAMOND, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item NETHERITE_LONGSWORD = registerItem("netherite_longsword", new LongSwordItem(Tiers.NETHERITE, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT).fireproof()));
	public static final Item STONEHEAD_ARROW = registerItem("stonehead_arrow", new ArrowItem(new QuiltItemSettings().group(CreativeModeTab.TAB_COMBAT)));
	public static final Item STONEHEAD_SPECTRAL_ARROW = registerItem("stonehead_spectral_arrow", new SpectralArrowItem(new QuiltItemSettings().group(CreativeModeTab.TAB_COMBAT)));
	public static final Item STONEHEAD_TIPPED_ARROW = registerItem("stonehead_tipped_arrow", new TippedArrowItem(new QuiltItemSettings().group(CreativeModeTab.TAB_COMBAT)));
	public static final MenuType<FletchingMenu> FLETCHING = register(new ResourceLocation("fletching"), FletchingMenu::new);
	private static <T extends AbstractContainerMenu> MenuType<T> register(ResourceLocation id, MenuType.MenuSupplier<T> factory) {
		return Registry.register(Registry.MENU, id, new MenuType<>(factory));
	}
	public static final RecipeType<FletchingRecipe> FLETCHING_TYPE = register("fletching");
	static <T extends Recipe<?>> RecipeType<T> register(String id) {
		return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(id), new RecipeType<T>() {
			public String toString() {
				return id;
			}
		});
	}
	public static final RecipeSerializer<FletchingRecipe> FLETCHING_SERIALIZER = register("fletching", new FletchingRecipe.Serializer());
	public static final RecipeSerializer<FletchingRecipe> TIPPED_FLETCHING_SERIALIZER = register("tipped_fletching", new TippedFletchingRecipe.Serializer());
	public static final RecipeSerializer<FletchingRecipe> SPECTRAL_FLETCHING_SERIALIZER = register("spectral_fletching", new SpectralFletchingRecipe.Serializer());
	static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
		return Registry.register(Registry.RECIPE_SERIALIZER, id, serializer);
	}
	public static final ResourceLocation INTERACT_WITH_FLETCHING_TABLE = Stats.makeCustomStat("interact_with_fletching_table", StatFormatter.DEFAULT);
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Atlas Combat Extras");
	public static Item registerItem(String name, Item item) {
		return Registry.register(Registry.ITEM, new ResourceLocation("atlas_combat",name),item);
	}

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
	}
}
