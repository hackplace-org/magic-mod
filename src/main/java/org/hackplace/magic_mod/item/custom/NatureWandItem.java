package org.hackplace.magic_mod.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.hackplace.magic_mod.MagicMod;
import org.hackplace.magic_mod.mana.ManaConsumer;

import java.util.List;

public class NatureWandItem extends BoneMealItem implements ManaConsumer {
    public static int WEATHER_CLEAR_DURATION = 300;
    public static final String NAME = "nature_wand";

    public NatureWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getManaCost(Spell type) {
        return switch (type) {
            case USE_DEFAULT -> 20; // clear rain
            case USE_ON_BLOCK_PRIMARY -> 5; // grow vines
            case USE_ON_BLOCK_SECONDARY -> 4; // fertilize block
            case USE_ON_ENTITY_PRIMARY -> 10; // breeding animals
            case USE_ON_ENTITY_SECONDARY -> 5; // grow up animal
        };
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack data = user.getStackInHand(hand);

        return attemptMagic(Spell.USE_DEFAULT, user, data, () -> {
            boolean isClient = world.isClient();
            if (!isClient) {
                ServerWorld serverWorld = (ServerWorld) world;
                serverWorld.setWeather(WEATHER_CLEAR_DURATION, 0, false, false);
            }

            return TypedActionResult.success(data, isClient);
        });
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        super.useOnBlock(context);

        BlockPos blockPos = context.getBlockPos();
        Direction side = context.getSide();

        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.isFullCube(world, blockPos) && !side.equals(Direction.UP)) {
            BlockPos targetPos = blockPos.offset(side);
            BlockState vineState = Blocks.VINE.getDefaultState()
                    .withIfExists(VineBlock.getFacingProperty(side.getOpposite()), true);

            if (world.getBlockState(targetPos).equals(Blocks.AIR.getDefaultState())) {
                return attemptMagic(Spell.USE_ON_BLOCK_PRIMARY, context.getPlayer(), () -> {
                    boolean isClient = world.isClient();
                    if (!world.isClient()) {
                        world.setBlockState(targetPos, vineState);
                    }

                    return ActionResult.success(isClient);
                });
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof AnimalEntity animal) {
            boolean isClient = user.getWorld().isClient();

            int age = animal.getBreedingAge();
            if (age == 0) {
                return attemptMagic(Spell.USE_ON_ENTITY_PRIMARY, user, () -> {
                    if (!isClient) {
                        animal.lovePlayer(user);
                    }

                    return ActionResult.success(isClient);
                });
            }

            if (animal.isBaby()) {
                return attemptMagic(Spell.USE_ON_ENTITY_SECONDARY, user, () -> {
                    if (!isClient) {
                        animal.growUp(AnimalEntity.toGrowUpAge(-age), true);
                    }

                    return ActionResult.success(isClient);
                });
            }
        }

        return ActionResult.PASS;
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
