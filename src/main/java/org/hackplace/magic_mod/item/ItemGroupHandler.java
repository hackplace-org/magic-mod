package org.hackplace.magic_mod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.hackplace.magic_mod.MagicMod;

import static org.hackplace.magic_mod.item.ItemHandler.*;

public class ItemGroupHandler {
    public static final ItemGroup MAGIC_GROUP = FabricItemGroup
            .builder()
            .displayName(Text.translatable("itemgroup.magic"))
            .icon(() -> new ItemStack(MAGIC_WAND))
            .entries(((displayContext, entries) -> {
                entries.add(MAGIC_WAND);
                entries.add(NATURE_WAND);
                entries.add(FIRE_WAND);
            })).build();

    public static void register(String name, ItemGroup itemGroup) {
        Registry.register(Registries.ITEM_GROUP, new Identifier(MagicMod.MOD_ID, name), itemGroup);
    }

    public static void registerItemGroups() {
        MagicMod.LOGGER.info("Registering item groups for " + MagicMod.MOD_ID);

        register("magic", MAGIC_GROUP);
    }
}
