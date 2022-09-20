package com.atlas.atlas_combat_extras.mixin;

import com.atlas.atlas_combat_extras.extensions.ISpectralArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpectralArrowItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpectralArrowItem.class)
public class SpectralArrowItemMixin {
	@Inject(method = "createArrow", at = @At(value = "HEAD"), cancellable = true)
	public void createArrow(Level world, ItemStack stack, LivingEntity shooter, CallbackInfoReturnable<AbstractArrow> cir) {
		SpectralArrow arrow = new SpectralArrow(world, shooter);
		((ISpectralArrow)arrow).extractStack(stack);
		cir.setReturnValue(arrow);
	}
}
