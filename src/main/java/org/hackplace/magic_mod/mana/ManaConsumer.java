package org.hackplace.magic_mod.mana;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

import java.util.function.Supplier;

public interface ManaConsumer {
    enum Spell {
        USE_DEFAULT,
        USE_ON_BLOCK_PRIMARY,
        USE_ON_BLOCK_SECONDARY,
        USE_ON_ENTITY_PRIMARY,
        USE_ON_ENTITY_SECONDARY,
    }

    int getManaCost(Spell type);

    default ActionResult attemptMagic(Spell type, PlayerEntity user, Supplier<ActionResult> result) {
        int manaCost = getManaCost(type);
        EntityDataSaver player = (EntityDataSaver)user;

        if (player.getMana() >= manaCost) {
            player.raiseManaBy(-manaCost);
            return result.get();
        } else {
            return ActionResult.FAIL;
        }
    }

    default TypedActionResult<ItemStack> attemptMagic(Spell type, PlayerEntity user, ItemStack data,
                                                      Supplier<TypedActionResult<ItemStack>> result) {
        int manaCost = getManaCost(type);
        EntityDataSaver player = (EntityDataSaver)user;

        if (player.getMana() >= manaCost) {
            player.raiseManaBy(-manaCost);
            return result.get();
        } else {
            return TypedActionResult.fail(data);
        }
    }
}
