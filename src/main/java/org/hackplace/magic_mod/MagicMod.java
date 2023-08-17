package org.hackplace.magic_mod;

import net.fabricmc.api.ModInitializer;

import org.hackplace.magic_mod.items.ItemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicMod implements ModInitializer {
	public static final String MOD_ID = "magic-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ItemHandler.registerItems();
	}
}