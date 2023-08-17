package org.hackplace.magic_mod.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.hackplace.magic_mod.MagicMod;
import org.hackplace.magic_mod.items.custom.FireWandItem;
import org.hackplace.magic_mod.items.custom.IceWandItem;
import org.hackplace.magic_mod.items.custom.MagicWandItem;
import org.hackplace.magic_mod.items.custom.NatureWandItem;

public class ItemHandler {
    public static final Item MAGIC_WAND = register(MagicWandItem.NAME,
            new MagicWandItem(new FabricItemSettings().maxCount(1)));
    public static final Item NATURE_WAND = register(NatureWandItem.NAME,
            new NatureWandItem(new FabricItemSettings().maxCount(1)));
    public static final Item ICE_WAND = register(IceWandItem.NAME,
            new IceWandItem(new FabricItemSettings().maxCount(1)));
    public static final Item FIRE_WAND = register(FireWandItem.NAME,
            new FireWandItem(new FabricItemSettings().maxCount(1)));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MagicMod.MOD_ID, name), item);
    }

    public static void registerItems() {
        MagicMod.LOGGER.info("Registering items for " + MagicMod.MOD_ID);
    }
}
