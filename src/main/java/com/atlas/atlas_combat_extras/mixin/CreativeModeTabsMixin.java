package com.atlas.atlas_combat_extras.mixin;

import com.atlas.atlas_combat_extras.AtlasCombatExtras;
import com.atlas.atlas_combat_extras.extensions.ICreativeModeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(CreativeModeTabs.class)
public abstract class CreativeModeTabsMixin implements ICreativeModeTabs {
	private static ArrayList<Item> list = new ArrayList<>();

	@Shadow
	private static void generateFireworksAllDurations(CreativeModeTab.Output collector, CreativeModeTab.TabVisibility visibility) {

	}

	@Shadow
	@Mutable
	@Final
	public static CreativeModeTab COMBAT;

	@Final
	@Shadow
	@Mutable
	private static List<CreativeModeTab> TABS;

	@Shadow
	private static List<CreativeModeTab> checkTabs(CreativeModeTab... creativeModeTabs) {
		return null;
	}

	@Shadow
	@Final
	public static CreativeModeTab BUILDING_BLOCKS;

	@Shadow
	@Final
	public static CreativeModeTab COLORED_BLOCKS;

	@Shadow
	@Final
	public static CreativeModeTab NATURAL_BLOCKS;

	@Shadow
	@Final
	public static CreativeModeTab FUNCTIONAL_BLOCKS;

	@Shadow
	@Final
	public static CreativeModeTab REDSTONE_BLOCKS;

	@Shadow
	@Final
	public static CreativeModeTab HOTBAR;

	@Shadow
	@Final
	public static CreativeModeTab SEARCH;

	@Shadow
	@Final
	public static CreativeModeTab TOOLS_AND_UTILITIES;

	@Shadow
	@Final
	public static CreativeModeTab FOOD_AND_DRINKS;

	@Shadow
	@Final
	public static CreativeModeTab INGREDIENTS;

	@Shadow
	@Final
	public static CreativeModeTab SPAWN_EGGS;

	@Shadow
	@Final
	public static CreativeModeTab OP_BLOCKS;

	@Shadow
	@Final
	public static CreativeModeTab INVENTORY;

	static {
		COMBAT = CreativeModeTab.builder(CreativeModeTab.Row.BOTTOM, 1)
				.title(Component.translatable("itemGroup.combat"))
				.icon(() -> new ItemStack(Items.NETHERITE_SWORD))
				.displayItems((featureFlagSet, collector, bl) -> {
					list.add(Items.TIPPED_ARROW);
					list.add(AtlasCombatExtras.STONEHEAD_TIPPED_ARROW);
					collector.accept(Items.WOODEN_SWORD);
					collector.accept(Items.STONE_SWORD);
					collector.accept(Items.IRON_SWORD);
					collector.accept(Items.GOLDEN_SWORD);
					collector.accept(Items.DIAMOND_SWORD);
					collector.accept(Items.NETHERITE_SWORD);
					collector.accept(Items.WOODEN_AXE);
					collector.accept(Items.STONE_AXE);
					collector.accept(Items.IRON_AXE);
					collector.accept(Items.GOLDEN_AXE);
					collector.accept(Items.DIAMOND_AXE);
					collector.accept(Items.NETHERITE_AXE);
					collector.accept(AtlasCombatExtras.WOODEN_KNIFE);
					collector.accept(AtlasCombatExtras.STONE_KNIFE);
					collector.accept(AtlasCombatExtras.IRON_KNIFE);
					collector.accept(AtlasCombatExtras.GOLDEN_KNIFE);
					collector.accept(AtlasCombatExtras.DIAMOND_KNIFE);
					collector.accept(AtlasCombatExtras.NETHERITE_KNIFE);
					collector.accept(AtlasCombatExtras.WOODEN_LONGSWORD);
					collector.accept(AtlasCombatExtras.STONE_LONGSWORD);
					collector.accept(AtlasCombatExtras.IRON_LONGSWORD);
					collector.accept(AtlasCombatExtras.GOLDEN_LONGSWORD);
					collector.accept(AtlasCombatExtras.DIAMOND_LONGSWORD);
					collector.accept(AtlasCombatExtras.NETHERITE_LONGSWORD);
					collector.accept(Items.TRIDENT);
					collector.accept(Items.SHIELD);
					collector.accept(Items.LEATHER_HELMET);
					collector.accept(Items.LEATHER_CHESTPLATE);
					collector.accept(Items.LEATHER_LEGGINGS);
					collector.accept(Items.LEATHER_BOOTS);
					collector.accept(Items.CHAINMAIL_HELMET);
					collector.accept(Items.CHAINMAIL_CHESTPLATE);
					collector.accept(Items.CHAINMAIL_LEGGINGS);
					collector.accept(Items.CHAINMAIL_BOOTS);
					collector.accept(Items.IRON_HELMET);
					collector.accept(Items.IRON_CHESTPLATE);
					collector.accept(Items.IRON_LEGGINGS);
					collector.accept(Items.IRON_BOOTS);
					collector.accept(Items.GOLDEN_HELMET);
					collector.accept(Items.GOLDEN_CHESTPLATE);
					collector.accept(Items.GOLDEN_LEGGINGS);
					collector.accept(Items.GOLDEN_BOOTS);
					collector.accept(Items.DIAMOND_HELMET);
					collector.accept(Items.DIAMOND_CHESTPLATE);
					collector.accept(Items.DIAMOND_LEGGINGS);
					collector.accept(Items.DIAMOND_BOOTS);
					collector.accept(Items.NETHERITE_HELMET);
					collector.accept(Items.NETHERITE_CHESTPLATE);
					collector.accept(Items.NETHERITE_LEGGINGS);
					collector.accept(Items.NETHERITE_BOOTS);
					collector.accept(Items.TURTLE_HELMET);
					collector.accept(Items.LEATHER_HORSE_ARMOR);
					collector.accept(Items.IRON_HORSE_ARMOR);
					collector.accept(Items.GOLDEN_HORSE_ARMOR);
					collector.accept(Items.DIAMOND_HORSE_ARMOR);
					collector.accept(Items.TOTEM_OF_UNDYING);
					collector.accept(Items.TNT);
					collector.accept(Items.END_CRYSTAL);
					collector.accept(Items.SNOWBALL);
					collector.accept(Items.EGG);
					collector.accept(Items.BOW);
					collector.accept(Items.CROSSBOW);
					generateFireworksAllDurations(collector, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
					collector.accept(Items.ARROW);
					collector.accept(AtlasCombatExtras.STONEHEAD_ARROW);
					collector.accept(Items.SPECTRAL_ARROW);
					collector.accept(AtlasCombatExtras.STONEHEAD_SPECTRAL_ARROW);
					generatePotionEffectTypes(collector, list, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
				})
				.build();
		TABS = checkTabs(BUILDING_BLOCKS, COLORED_BLOCKS, NATURAL_BLOCKS, FUNCTIONAL_BLOCKS, REDSTONE_BLOCKS, HOTBAR, SEARCH, TOOLS_AND_UTILITIES, COMBAT, FOOD_AND_DRINKS, INGREDIENTS, SPAWN_EGGS, OP_BLOCKS, INVENTORY);
	}

	private static void generatePotionEffectTypes(CreativeModeTab.Output collector, List<Item> potions, CreativeModeTab.TabVisibility visibility) {
		for (Potion potion2 : BuiltInRegistries.POTION) {
			for (Item potion : potions) {
				if (potion2 != Potions.EMPTY) {
					collector.accept(PotionUtils.setPotion(new ItemStack(potion), potion2), visibility);
				}
			}
		}
	}
	@Override
	public void appendTippedArrow(TippedArrowItem item) {
		list.add(item);
	}
}
