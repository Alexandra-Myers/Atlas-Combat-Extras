package com.atlas.atlas_combat_extras.mixin;

import com.atlas.atlas_combat_extras.AtlasCombatExtras;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
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

@Mixin(SpectralArrow.class)
public abstract class SpectralArrowMixin extends AbstractArrow implements com.atlas.atlas_combat_extras.extensions.ISpectralArrow {
	boolean alreadyChanged = false;
	boolean strongButHeavy;
	private ItemStack stack = new ItemStack(Items.ARROW);
	int currentTimer = 0;
	protected SpectralArrowMixin(EntityType<? extends AbstractArrow> entityType, Level level) {
		super(entityType, level);
	}
	@Override
	public void extractStack(ItemStack stack) {
		this.stack = stack;
		strongButHeavy = stack.is(AtlasCombatExtras.STONEHEAD_SPECTRAL_ARROW);
		return;
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
		ItemStack itemStack = new ItemStack(stack.getItem());
		cir.setReturnValue(itemStack);
	}
}
