package com.atlas.atlas_combat_extras.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;

public class FletchingContainer implements Container, StackedContentsCompatible {
	private final NonNullList<ItemStack> items;
	private final int width;
	private final int height;
	private final AbstractContainerMenu menu;

	public FletchingContainer(AbstractContainerMenu abstractContainerMenu, int i, int j) {
		this.items = NonNullList.withSize(i * j, ItemStack.EMPTY);
		this.menu = abstractContainerMenu;
		this.width = i;
		this.height = j;
	}

	@Override
	public int getContainerSize() {
		return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemStack : this.items) {
			if (!itemStack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return slot >= this.getContainerSize() ? ItemStack.EMPTY : this.items.get(slot);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(this.items, slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		ItemStack itemStack = ContainerHelper.removeItem(this.items, slot, amount);
		if (!itemStack.isEmpty()) {
			this.menu.slotsChanged(this);
		}

		return itemStack;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		this.items.set(slot, stack);
		this.menu.slotsChanged(this);
	}

	@Override
	public void setChanged() {
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		this.items.clear();
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	@Override
	public void fillStackedContents(StackedContents finder) {
		for(ItemStack itemStack : this.items) {
			finder.accountSimpleStack(itemStack);
		}

	}
}
