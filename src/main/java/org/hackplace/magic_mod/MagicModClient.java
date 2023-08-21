package org.hackplace.magic_mod;

import net.fabricmc.api.ClientModInitializer;
import org.hackplace.magic_mod.mana.MessageHandler;

public class MagicModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MessageHandler.registerServerToClientPackets();
    }
}
