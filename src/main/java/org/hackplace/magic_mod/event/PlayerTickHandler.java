package org.hackplace.magic_mod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.hackplace.magic_mod.mana.EntityDataSaver;

public class PlayerTickHandler implements ServerTickEvents.StartTick {
    private long previousTime = Util.getEpochTimeMs();

    @Override
    public void onStartTick(MinecraftServer server) {
        long currentTime = Util.getEpochTimeMs();

        if (currentTime - previousTime > 3000) {
            previousTime = currentTime;

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ((EntityDataSaver) player).modifyManaBy(1);
                player.sendMessage(Text.of("Mana is " + ((EntityDataSaver) player).getMana()));
            }
        }
    }
}
