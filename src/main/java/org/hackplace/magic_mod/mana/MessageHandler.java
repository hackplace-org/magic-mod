package org.hackplace.magic_mod.mana;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.hackplace.magic_mod.MagicMod;

public class MessageHandler {
    public static final Identifier MANA_SYNC_ID = new Identifier(MagicMod.MOD_ID, "mana_sync");

    public static void receiveManaSync(MinecraftClient client, ClientPlayNetworkHandler handler,
                                       PacketByteBuf buf, PacketSender responseSender) {
        assert client.player != null;
        ((EntityDataSaver) client.player).setMana(buf.readInt());
    }

    public static void registerServerToClientPackets() {
        ClientPlayNetworking.registerGlobalReceiver(MANA_SYNC_ID, MessageHandler::receiveManaSync);
    }
}
