package com.atlas.atlas_combat_extras;

import com.atlas.atlas_combat_extras.screens.inventory.FletchingScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import static com.atlas.atlas_combat_extras.AtlasCombatExtras.FLETCHING;

public class AtlasCombatExtrasClient implements ClientModInitializer {
	static {
		MenuScreens.register(FLETCHING, FletchingScreen::new);
	}

	@Override
	public void onInitializeClient(ModContainer mod) {

	}
}
