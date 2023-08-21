package org.hackplace.magic_mod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.hackplace.magic_mod.MagicMod;
import org.hackplace.magic_mod.mana.EntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityDataSaverMixin implements EntityDataSaver {
    @Unique
    private NbtCompound persistentData;

    @Override
    public NbtCompound magic_mod$getPersistentData() {
        if (persistentData == null) {
            persistentData = new NbtCompound();
        }

        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (persistentData != null) {
            nbt.put(String.format("%s.magic_data", MagicMod.MOD_ID), persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readNbt(NbtCompound nbt, CallbackInfo ci) {
        String key = String.format("%s.magic_data", MagicMod.MOD_ID);
        if (nbt.contains(key, NbtElement.COMPOUND_TYPE)) {
            persistentData = nbt.getCompound(key);
        }
    }
}
