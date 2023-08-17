package org.hackplace.magic_mod.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.hackplace.magic_mod.MagicMod;

import java.util.List;

public class NatureWand extends Item {
    public static final String NAME = "nature_wand";

    public NatureWand() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable(String.format("item.%s.%s.tooltip", MagicMod.MOD_ID, NAME)));
    }
}
