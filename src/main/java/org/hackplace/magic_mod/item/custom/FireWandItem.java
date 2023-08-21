package org.hackplace.magic_mod.item.custom;

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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.hackplace.magic_mod.MagicMod;
import org.hackplace.magic_mod.item.ItemHandler;
import org.hackplace.magic_mod.mana.ManaConsumer;

import java.util.List;

public class FireWandItem extends FlintAndSteelItem implements ManaConsumer {
    public static int RAYCAST_DISTANCE = 100;
    public static double FIREBALL_SPEED = 1.5;

    public static final String NAME = "fire_wand";

    public FireWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getManaCost(ManaConsumer.Spell type) {
        return switch (type) {
            case USE_DEFAULT -> 10; // shoot fireball
            case USE_ON_BLOCK_PRIMARY, USE_ON_BLOCK_SECONDARY -> 4; // light on fire
            case USE_ON_ENTITY_PRIMARY, USE_ON_ENTITY_SECONDARY -> 5; // light animal on fire
        };
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack data = user.getStackInHand(hand);

        return attemptMagic(Spell.USE_DEFAULT, user, data, () -> {
            boolean isClient = world.isClient();
            if (!isClient) {
                Vec3d position = new Vec3d(user.getX(), user.getEyeY() - (double)0.1f, user.getZ())
                        .add(user.getHandPosOffset(ItemHandler.FIRE_WAND));
                Vec3d target = user.raycast(RAYCAST_DISTANCE, 1.0F, false).getPos();

                Vec3d velocity = target.subtract(position).normalize().multiply(FIREBALL_SPEED);

                SmallFireballEntity entity = new SmallFireballEntity(world,
                        position.getX(), position.getY(), position.getZ(),
                        velocity.getX(), velocity.getY(), velocity.getZ());

                world.spawnEntity(entity);
            }

            return TypedActionResult.success(data, isClient);
        });
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        return attemptMagic(Spell.USE_ON_ENTITY_PRIMARY, user, () -> {
            if (!user.getWorld().isClient()) {
                entity.setOnFireFor(5);
            }

            return ActionResult.SUCCESS;
        });
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
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable(String.format("item.%s.%s.tooltip", MagicMod.MOD_ID, NAME)));
    }
}