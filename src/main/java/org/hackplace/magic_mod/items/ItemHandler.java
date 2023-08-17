package org.hackplace.magic_mod.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.hackplace.magic_mod.MagicMod;

public class ItemHandler {
    public static final Item MAGIC_WAND = registerItem(MagicWand.NAME, new MagicWand());
    public static final Item NATURE_WAND = registerItem(NatureWand.NAME, new NatureWand());
    public static final Item ICE_WAND = registerItem(IceWand.NAME, new IceWand());

    public static final ItemGroup MAGIC_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(MagicMod.MOD_ID, "magic"),
            FabricItemGroup
                    .builder()
                    .displayName(Text.translatable("itemgroup.magic"))
                    .icon(() -> new ItemStack(MAGIC_WAND))
                    .entries(((displayContext, entries) -> {
                        entries.add(MAGIC_WAND);
                        entries.add(NATURE_WAND);
                        entries.add(ICE_WAND);
                    })).build()
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MagicMod.MOD_ID, name), item);
    }

    public static void register() {
        MagicMod.LOGGER.info("Registering items for " + MagicMod.MOD_ID);
    }
}
