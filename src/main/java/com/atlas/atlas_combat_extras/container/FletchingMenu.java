package com.atlas.atlas_combat_extras.container;

import com.atlas.atlas_combat_extras.AtlasCombatExtras;
import com.atlas.atlas_combat_extras.extensions.IPotion;
import com.atlas.atlas_combat_extras.recipe.FletchingRecipe;
import com.atlas.atlas_combat_extras.recipe.TippedFletchingRecipe;
import com.google.common.collect.Lists;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FletchingMenu extends AbstractContainerMenu {
	private final ContainerLevelAccess access;
	private final DataSlot selectedRecipeIndex = DataSlot.standalone();
	private final Level level;
	private List<FletchingRecipe> recipes = Lists.newArrayList();
	private ItemStack flint = ItemStack.EMPTY;
	private ItemStack stick = ItemStack.EMPTY;
	private ItemStack feather = ItemStack.EMPTY;
	private ItemStack potion = ItemStack.EMPTY;
	long lastSoundTime;
	public static int removeAmount = 1;
	final Slot flintSlot;
	final Slot stickSlot;
	final Slot potionSlot;
	final Slot featherSlot;
	final Slot resultSlot;
	Runnable slotUpdateListener = () -> {
	};
	public final Container container = new SimpleContainer(4) {
		@Override
		public void setChanged() {
			super.setChanged();
			FletchingMenu.this.slotsChanged(this);
			FletchingMenu.this.slotUpdateListener.run();
		}
	};
	final ResultContainer resultContainer = new ResultContainer();

	public FletchingMenu(int i, Inventory inventory) {
		this(i, inventory, ContainerLevelAccess.NULL);
	}

	public FletchingMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
		super(AtlasCombatExtras.FLETCHING, i);
		this.access = containerLevelAccess;
		this.level = inventory.player.level;
		this.flintSlot = this.addSlot(new Slot(this.container, 1, 8, 22));
		this.stickSlot = this.addSlot(new Slot(this.container, 0, 8, 44));
		this.potionSlot = this.addSlot(new Slot(this.container, 2, 30, 22));
		this.featherSlot = this.addSlot(new Slot(this.container, 3, 30, 44));
		this.resultSlot = this.addSlot(new Slot(this.resultContainer, 4, 143, 33) {
			@Override
			public boolean mayPlace(@NotNull ItemStack stack) {
				return false;
			}

			@Override
			public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
				stack.onCraftedBy(player.level, player, stack.getCount());
				FletchingMenu.this.resultContainer.awardUsedRecipes(player);
				ItemStack itemStack = FletchingMenu.this.flintSlot.remove(1);
				ItemStack secondStack = FletchingMenu.this.stickSlot.remove(1);
				ItemStack thirdStack = FletchingMenu.this.potionSlot.getItem();
				ItemStack fourthStack = FletchingMenu.this.featherSlot.remove(1);
				if(!thirdStack.isEmpty() && thirdStack.getItem() instanceof PotionItem potionItem) {
					((IPotion)potionItem).addUses(1);
					if(((IPotion)potionItem).getUses() == 8) {
						thirdStack = FletchingMenu.this.potionSlot.remove(1);
						((IPotion)potionItem).addUses(-8);
					}
				}else if(!thirdStack.isEmpty()) {
					thirdStack = FletchingMenu.this.potionSlot.remove(1);
				}
				boolean bl = !itemStack.isEmpty() && !secondStack.isEmpty() && !fourthStack.isEmpty();
				if (bl) {
					FletchingMenu.this.setupResultSlot();
				}

				containerLevelAccess.execute((world, pos) -> {
				});
				super.onTake(player, stack);
			}
		});

		for(int j = 0; j < 3; ++j) {
			for(int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
			}
		}

		for(int j = 0; j < 9; ++j) {
			this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
		}

		this.addDataSlot(this.selectedRecipeIndex);
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return stillValid(this.access, player, Blocks.FLETCHING_TABLE);
	}

	public int getSelectedRecipeIndex() {
		return this.selectedRecipeIndex.get();
	}

	public List<FletchingRecipe> getRecipes() {
		return this.recipes;
	}

	public boolean hasInputItem() {
		boolean bl = this.stickSlot.hasItem() && this.flintSlot.hasItem() && this.featherSlot.hasItem() && !this.recipes.isEmpty();
		boolean bl1 = this.stickSlot.hasItem() && this.potionSlot.hasItem() && !this.recipes.isEmpty();
		return bl || bl1;
	}

	public void registerUpdateListener(Runnable contentsChangedListener) {
		this.slotUpdateListener = contentsChangedListener;
	}

	public int getNumRecipes() {
		return this.recipes.size();
	}

	@Override
	public boolean clickMenuButton(@NotNull Player player, int id) {
		if (this.isValidRecipeIndex(id)) {
			this.selectedRecipeIndex.set(id);
			this.setupResultSlot();
		}

		return true;
	}

	private boolean isValidRecipeIndex(int id) {
		return id >= 0 && id < this.recipes.size();
	}

	@Override
	public void slotsChanged(@NotNull Container inventory) {
		ItemStack itemStack = this.flintSlot.getItem();
		ItemStack itemStack1 = this.stickSlot.getItem();
		ItemStack itemStack2 = this.featherSlot.getItem();
		ItemStack itemStack3 = this.potionSlot.getItem();
		if (!itemStack.is(this.flint.getItem()) || !itemStack1.is(this.stick.getItem()) || !itemStack2.is(this.feather.getItem()) || !itemStack3.is(this.potion.getItem())) {
			this.flint = itemStack.copy();
			this.stick = itemStack1.copy();
			this.feather = itemStack2.copy();
			this.potion = itemStack3.copy();
			this.setupRecipeList(inventory, itemStack);
		}

	}

	private void setupRecipeList(Container input, ItemStack stack) {
		this.recipes.clear();
		this.selectedRecipeIndex.set(-1);
		this.resultSlot.set(ItemStack.EMPTY);
		if (!stack.isEmpty()) {
			this.recipes = this.level.getRecipeManager().getRecipesFor(AtlasCombatExtras.FLETCHING_TYPE, input, this.level);
		}

	}

	void setupResultSlot() {
		if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
			FletchingRecipe stonecutterRecipe = this.recipes.get(this.selectedRecipeIndex.get());
			this.resultContainer.setRecipeUsed(stonecutterRecipe);
			this.resultSlot.set(stonecutterRecipe.assemble(this.container));
		} else {
			this.resultSlot.set(ItemStack.EMPTY);
		}

		this.broadcastChanges();
	}

	@Override
	public MenuType<?> getType() {
		return AtlasCombatExtras.FLETCHING;
	}

	@Override
	public boolean canTakeItemForPickAll(@NotNull ItemStack stack, Slot slot) {
		return slot.container != this.resultContainer && super.canTakeItemForPickAll(stack, slot);
	}

	@Override
	public ItemStack quickMoveStack(@NotNull Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack itemStack2 = slot.getItem();
			Item item = itemStack2.getItem();
			itemStack = itemStack2.copy();
			if (index == 2) {
				item.onCraftedBy(itemStack2, player.level, player);
				if (!this.moveItemStackTo(itemStack2, 5, 41, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemStack2, itemStack);
			} else if (index == 0) {
				if (!this.moveItemStackTo(itemStack2, 5, 41, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index == 1) {
				if (!this.moveItemStackTo(itemStack2, 5, 41, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.level.getRecipeManager().getRecipeFor(AtlasCombatExtras.FLETCHING_TYPE, new SimpleContainer(itemStack2), this.level).isPresent()) {
				if (!this.moveItemStackTo(itemStack2, 0, 4, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 3 && index < 30) {
				if (!this.moveItemStackTo(itemStack2, 32, 41, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemStack2, 5, 32, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			}

			slot.setChanged();
			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemStack2);
			this.broadcastChanges();
		}

		return itemStack;
	}

	@Override
	public void removed(@NotNull Player player) {
		super.removed(player);
		this.resultContainer.removeItemNoUpdate(1);
		this.access.execute((world, pos) -> this.clearContainer(player, this.container));
	}
}
