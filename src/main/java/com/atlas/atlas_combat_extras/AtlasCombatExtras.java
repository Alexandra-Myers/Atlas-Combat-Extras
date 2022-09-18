package com.atlas.atlas_combat_extras;

import net.alexandra.atlas.atlas_combat.item.KnifeItem;
import net.alexandra.atlas.atlas_combat.item.LongSwordItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
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
	public static final Item NETHERITE_KNIFE = registerItem("netherite_knife", new KnifeItem(Tiers.NETHERITE, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT).fireproof().fireproof()));
	public static final Item WOODEN_LONGSWORD = registerItem("wooden_longsword", new LongSwordItem(Tiers.WOOD, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item STONE_LONGSWORD = registerItem("stone_longsword", new LongSwordItem(Tiers.STONE, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item GOLDEN_LONGSWORD = registerItem("golden_longsword", new LongSwordItem(Tiers.GOLD, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item IRON_LONGSWORD = registerItem("iron_longsword", new LongSwordItem(Tiers.IRON, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item DIAMOND_LONGSWORD = registerItem("diamond_longsword", new LongSwordItem(Tiers.DIAMOND, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT)));
	public static final Item NETHERITE_LONGSWORD = registerItem("netherite_longsword", new LongSwordItem(Tiers.NETHERITE, new QuiltItemSettings().maxCount(1).group(CreativeModeTab.TAB_COMBAT).fireproof().fireproof()));
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
