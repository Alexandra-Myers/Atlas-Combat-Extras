package com.atlas.atlas_combat_extras.mixin;

import com.atlas.atlas_combat_extras.AtlasCombatExtras;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Set;

@Mixin(Arrow.class)
public abstract class ArrowMixin extends AbstractArrow {
	@Shadow
	@Final
	private Set<MobEffectInstance> effects;
	@Shadow
	private Potion potion;
	@Shadow
	private boolean fixedColor;

	@Shadow
	public abstract int getColor();

	@Shadow
	public static int getCustomColor(ItemStack stack) {
		return 0;
	}

	@Shadow
	protected abstract void updateColor();

	@Shadow
	protected abstract void setFixedColor(int color);

	@Shadow
	@Final
	private static EntityDataAccessor<Integer> ID_EFFECT_COLOR;
	boolean alreadyChanged = false;
	boolean strongButHeavy;
	private ItemStack stack = new ItemStack(Items.ARROW);
	int currentTimer = 0;
	protected ArrowMixin(EntityType<? extends AbstractArrow> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "setEffectsFromItem", at = @At(value = "HEAD"), cancellable = true)
	public void extractStack(ItemStack stack, CallbackInfo ci) {
		this.stack = stack;
		strongButHeavy = stack.is(AtlasCombatExtras.STONEHEAD_ARROW) || stack.is(AtlasCombatExtras.STONEHEAD_TIPPED_ARROW) || stack.is(AtlasCombatExtras.STONEHEAD_SPECTRAL_ARROW);
		if (stack.getItem() instanceof TippedArrowItem) {
			this.potion = PotionUtils.getPotion(stack);
			Collection<MobEffectInstance> collection = PotionUtils.getCustomEffects(stack);
			if (!collection.isEmpty()) {
				for(MobEffectInstance mobEffectInstance : collection) {
					this.effects.add(new MobEffectInstance(mobEffectInstance));
				}
			}

			int i = getCustomColor(stack);
			if (i == -1) {
				this.updateColor();
			} else {
				this.setFixedColor(i);
			}
		} else if (stack.getItem() instanceof ArrowItem && !(stack.getItem() instanceof SpectralArrowItem)) {
			this.potion = Potions.EMPTY;
			this.effects.clear();
			this.entityData.set(ID_EFFECT_COLOR, -1);
		}
		ci.cancel();
	}
	@Inject(method = "tick", at = @At(value = "HEAD"))
	public void injectDamageModifier(CallbackInfo ci) {
		++currentTimer;
		if (strongButHeavy && !alreadyChanged) {
			setBaseDamage(getBaseDamage() * 2.5);
			setDeltaMovement(getDeltaMovement().scale(0.5));
			alreadyChanged = true;
		}
	}
	@Inject(method = "getPickupItem", at = @At(value = "HEAD"), cancellable = true)
	public void changePickupItem(CallbackInfoReturnable<ItemStack> cir) {
		if (this.effects.isEmpty() && this.potion == Potions.EMPTY) {
			cir.setReturnValue(new ItemStack(stack.getItem()));
		} else {
			ItemStack itemStack = new ItemStack(stack.getItem());
			PotionUtils.setPotion(itemStack, this.potion);
			PotionUtils.setCustomEffects(itemStack, this.effects);
			if (this.fixedColor) {
				itemStack.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
			}

			cir.setReturnValue(itemStack);
		}
	}
}
