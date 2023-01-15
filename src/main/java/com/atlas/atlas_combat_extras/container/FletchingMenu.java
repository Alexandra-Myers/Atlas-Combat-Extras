package com.atlas.atlas_combat_extras.container;

import com.atlas.atlas_combat_extras.AtlasCombatExtras;
import com.atlas.atlas_combat_extras.FletchingResultSlot;
import com.atlas.atlas_combat_extras.recipe.FletchingRecipe;
import com.chocohead.mm.api.ClassTinkerers;
import com.github.aws404.booking_it.BookingIt;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public class FletchingMenu extends RecipeBookMenu<FletchingContainer> {
	private final ContainerLevelAccess access;
	final Slot resultSlot;
	public final Player player;
	public final FletchingContainer container = new FletchingContainer(this, 2, 2);
	final ResultContainer resultContainer = new ResultContainer();

	public FletchingMenu(int i, Inventory inventory) {
		this(i, inventory, ContainerLevelAccess.NULL);
	}

	public FletchingMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
		super(AtlasCombatExtras.FLETCHING, i);
		this.access = containerLevelAccess;
		player = inventory.player;
		this.addSlot(new Slot(this.container, 1, 38, 22));
		this.addSlot(new Slot(this.container, 0, 38, 44));
		this.addSlot(new Slot(this.container, 2, 60, 22));
		this.addSlot(new Slot(this.container, 3, 60, 44));
		this.resultSlot = this.addSlot(new FletchingResultSlot(inventory.player, container, resultContainer, 4, 116, 33));

		for(int j = 0; j < 3; ++j) {
			for(int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
			}
		}

		for(int j = 0; j < 9; ++j) {
			this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
		}

		DataSlot selectedRecipeIndex = DataSlot.standalone();
		this.addDataSlot(selectedRecipeIndex);
	}

	protected static void slotChangedCraftingGrid(
			AbstractContainerMenu handler, Level world, Player player, FletchingContainer craftingInventory, ResultContainer resultInventory
	) {
		if (!world.isClientSide) {
			ServerPlayer serverPlayer = (ServerPlayer)player;
			ItemStack itemStack = ItemStack.EMPTY;
			Optional<FletchingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(AtlasCombatExtras.FLETCHING_TYPE, craftingInventory, world);
			if (optional.isPresent()) {
				FletchingRecipe craftingRecipe = optional.get();
				if (resultInventory.setRecipeUsed(world, serverPlayer, craftingRecipe)) {
					itemStack = craftingRecipe.assemble(craftingInventory);
				}
			}

			resultInventory.setItem(0, itemStack);
			handler.setRemoteSlot(0, itemStack);
			serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(handler.containerId, handler.incrementStateId(), 0, itemStack));
		}
	}

	@Override
	public void slotsChanged(Container inventory) {
		this.access.execute((world, pos) -> slotChangedCraftingGrid(this, world, this.player, this.container, this.resultContainer));
	}

	@Override
	public void fillCraftSlotsStackedContents(StackedContents finder) {
		this.container.fillStackedContents(finder);
	}

	@Override
	public void clearCraftingContent() {
		this.container.clearContent();
		this.resultContainer.clearContent();
	}

	@Override
	public boolean recipeMatches(Recipe<? super FletchingContainer> recipe) {
		return recipe.matches(this.container, this.player.level);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.access.execute((world, pos) -> this.clearContainer(player, this.container));
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(this.access, player, Blocks.FLETCHING_TABLE);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemStack2 = slot.getItem();
			itemStack = itemStack2.copy();
			if (index == 4) {
				this.access.execute((world, pos) -> itemStack2.getItem().onCraftedBy(itemStack2, world, player));
				if (!this.moveItemStackTo(itemStack2, 10, 46, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemStack2, itemStack);
			} else if (index >= 10 && index < 46) {
				if (!this.moveItemStackTo(itemStack2, 0, 3, false)) {
					if (index < 37) {
						if (!this.moveItemStackTo(itemStack2, 37, 46, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.moveItemStackTo(itemStack2, 10, 37, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(itemStack2, 10, 46, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemStack2);
			if (index == 4) {
				player.drop(itemStack2, false);
			}
		}

		return itemStack;
	}

	@Override
	public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
		return slot.container != this.resultSlot && super.canTakeItemForPickAll(stack, slot);
	}

	@Override
	public int getResultSlotIndex() {
		return 4;
	}

	@Override
	public int getGridWidth() {
		return this.container.getWidth();
	}

	@Override
	public int getGridHeight() {
		return this.container.getHeight();
	}

	@Override
	public int getSize() {
		return 5;
	}

	@Override
	public RecipeBookType getRecipeBookType() {
		return BookingIt.getCategory("FLETCHING");
	}

	@Override
	public boolean shouldMoveToInventory(int index) {
		return index != this.getResultSlotIndex();
	}
}
