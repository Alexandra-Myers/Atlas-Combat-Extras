package com.atlas.atlas_combat_extras.mixin;

import com.atlas.atlas_combat_extras.extensions.IPotion;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PotionItem.class)
public class PotionMixin extends Item implements IPotion {
	private int uses = 0;

	public PotionMixin(Properties properties) {
		super(properties);
	}

	@Override
	public void addUses(int value) {
		uses += value;
	}
	@Override
	public int getUses() {
		return uses;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		CompoundTag tag = stack.getOrCreateTagElement("uses");
		tag.putInt("craftingUses", uses);
		uses = tag.getInt("craftingUses");
	}
}
