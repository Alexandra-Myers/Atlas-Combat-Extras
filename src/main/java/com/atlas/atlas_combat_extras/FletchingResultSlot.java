package com.atlas.atlas_combat_extras;

import com.atlas.atlas_combat_extras.container.FletchingContainer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FletchingResultSlot extends Slot {
	private final FletchingContainer craftSlots;
	private final Player player;
	private int removeCount;

	public FletchingResultSlot(Player player, FletchingContainer craftingContainer, Container container, int i, int j, int k) {
		super(container, i, j, k);
		this.player = player;
		this.craftSlots = craftingContainer;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack remove(int amount) {
		if (this.hasItem()) {
			this.removeCount += Math.min(amount, this.getItem().getCount());
		}

		return super.remove(amount);
	}

	@Override
	protected void onQuickCraft(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.checkTakeAchievements(stack);
	}

	@Override
	protected void onSwapCraft(int amount) {
		this.removeCount += amount;
	}

	@Override
	protected void checkTakeAchievements(ItemStack stack) {
		if (this.removeCount > 0) {
			stack.onCraftedBy(this.player.level, this.player, this.removeCount);
		}

		if (this.container instanceof RecipeHolder) {
			((RecipeHolder)this.container).awardUsedRecipes(this.player);
		}

		this.removeCount = 0;
	}

	@Override
	public void onTake(Player player, ItemStack stack) {
		this.checkTakeAchievements(stack);
		NonNullList<ItemStack> nonNullList = player.level.getRecipeManager().getRemainingItemsFor(AtlasCombatExtras.FLETCHING_TYPE, this.craftSlots, player.level);

		for(int i = 0; i < nonNullList.size(); ++i) {
			ItemStack itemStack = this.craftSlots.getItem(i);
			ItemStack itemStack2 = nonNullList.get(i);
			if (!itemStack.isEmpty()) {
				this.craftSlots.removeItem(i, 1);
				itemStack = this.craftSlots.getItem(i);
			}

			if (!itemStack2.isEmpty()) {
				if (itemStack.isEmpty()) {
					this.craftSlots.setItem(i, itemStack2);
				} else if (ItemStack.isSame(itemStack, itemStack2) && ItemStack.tagMatches(itemStack, itemStack2)) {
					itemStack2.grow(itemStack.getCount());
					this.craftSlots.setItem(i, itemStack2);
				} else if (!this.player.getInventory().add(itemStack2)) {
					this.player.drop(itemStack2, false);
				}
			}
		}

	}
}
