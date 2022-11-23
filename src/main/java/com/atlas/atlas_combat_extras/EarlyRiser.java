package com.atlas.atlas_combat_extras;

import com.chocohead.mm.api.ClassTinkerers;
import net.alexandra.atlas.atlas_combat.networking.NewServerboundInteractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.quiltmc.loader.api.MappingResolver;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.function.Function;

public class EarlyRiser implements Runnable {
	@Override
	public void run() {
		MappingResolver remapper = QuiltLoader.getMappingResolver();
	}
}
