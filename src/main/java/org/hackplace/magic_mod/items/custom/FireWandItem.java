package org.hackplace.magic_mod.items.custom;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.hackplace.magic_mod.MagicMod;

import java.util.List;

public class FireWandItem extends FlintAndSteelItem {
    public static final String NAME = "fire_wand";

    public FireWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        boolean isClient = world.isClient();
        if (!isClient) {
            float yaw = user.getYaw();
            float pitch = user.getPitch();
            float roll = user.getRoll();

            float f = -MathHelper.sin(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));
            float g = -MathHelper.sin((pitch + roll) * ((float)Math.PI / 180));
            float h = MathHelper.cos(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));

            SmallFireballEntity entity = new SmallFireballEntity(world,
                    user.getX(), user.getEyeY() - (double)0.1f, user.getZ(),
                    f * 1.5f, g * 1.5f, h * 1.5f);

            world.spawnEntity(entity);
        }

        return TypedActionResult.success(user.getStackInHand(hand), isClient);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient()) {
            entity.setOnFireFor(5);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable(String.format("item.%s.%s.tooltip", MagicMod.MOD_ID, NAME)));
    }
}