package org.hackplace.magic_mod.mana;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

public interface EntityDataSaver {
    String MANA_KEY = "mana";

    NbtCompound magic_mod$getPersistentData();

    default int getMana() {
        return this.magic_mod$getPersistentData().getInt(MANA_KEY);
    }

    default int setMana(int amount) {
        int mana =  MathHelper.clamp(amount, 0, 20);
        this.magic_mod$getPersistentData().putInt(MANA_KEY, mana);

        return mana;
    }

    default void raiseManaBy(int amount) {
        int current = this.getMana();

        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(this.setMana(current + amount));

        if (this.getMana() != current) {
            ServerPlayNetworking.send((ServerPlayerEntity) this, MessageHandler.MANA_SYNC_ID, buffer);
        }
    }
}
