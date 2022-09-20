package com.atlas.atlas_combat_extras.mixin;

import com.atlas.atlas_combat_extras.AtlasCombatExtras;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemColors.class)
public class ItemColorsMixin {
	@Inject(method = "createDefault", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void addTippedArrows(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir, ItemColors itemColors) {
		itemColors.register((stack, tintIndex) -> tintIndex == 0 ? PotionUtils.getColor(stack) : -1, AtlasCombatExtras.STONEHEAD_TIPPED_ARROW);
	}
}
