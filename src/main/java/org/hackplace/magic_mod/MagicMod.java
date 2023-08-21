package org.hackplace.magic_mod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.hackplace.magic_mod.event.PlayerTickHandler;
import org.hackplace.magic_mod.item.ItemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicMod implements ModInitializer {
	public static final String MOD_ID = "magic-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ItemHandler.registerItems();
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
	}
}