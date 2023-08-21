package org.hackplace.magic_mod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.hackplace.magic_mod.MagicMod;
import org.hackplace.magic_mod.item.custom.FireWandItem;
import org.hackplace.magic_mod.item.custom.MagicWandItem;
import org.hackplace.magic_mod.item.custom.NatureWandItem;

public class ItemHandler {
    public static final Item MAGIC_WAND = register(MagicWandItem.NAME,
            new MagicWandItem(new FabricItemSettings().maxCount(1).rarity(Rarity.COMMON)));
    public static final Item NATURE_WAND = register(NatureWandItem.NAME,
            new NatureWandItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item FIRE_WAND = register(FireWandItem.NAME,
            new FireWandItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).fireproof()));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MagicMod.MOD_ID, name), item);
    }

    public static void registerItems() {
        MagicMod.LOGGER.info("Registering items for " + MagicMod.MOD_ID);
    }
}
