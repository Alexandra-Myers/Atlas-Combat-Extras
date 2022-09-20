package com.atlas.atlas_combat_extras.mixin;

import com.atlas.atlas_combat_extras.AtlasCombatExtras;
import com.atlas.atlas_combat_extras.container.FletchingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.FletchingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FletchingTableBlock.class)
public class FletchingTableMixin extends CraftingTableBlock {
	private static final Component CONTAINER_TITLE = Component.translatable("container.fletching");

	public FletchingTableMixin(Properties properties) {
		super(properties);
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
		return new SimpleMenuProvider((syncId, inventory, player) -> new FletchingMenu(syncId, inventory, ContainerLevelAccess.create(world, pos)), CONTAINER_TITLE);
	}

	@Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
	public void use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
		if (world.isClientSide) {
			cir.setReturnValue(InteractionResult.SUCCESS);
			cir.cancel();
		} else {
			player.openMenu(state.getMenuProvider(world, pos));
			player.awardStat(AtlasCombatExtras.INTERACT_WITH_FLETCHING_TABLE);
			cir.setReturnValue(InteractionResult.CONSUME);
			cir.cancel();
		}
	}
}
