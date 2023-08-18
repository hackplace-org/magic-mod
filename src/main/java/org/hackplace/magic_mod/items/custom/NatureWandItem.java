package org.hackplace.magic_mod.items.custom;

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
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.hackplace.magic_mod.MagicMod;

import java.util.List;

public class NatureWandItem extends BoneMealItem {
    public static final String NAME = "nature_wand";

    public NatureWandItem(Settings settings) {
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
                boolean isClient = world.isClient();
                if (!world.isClient()) {
                    world.setBlockState(targetPos, vineState);
                }

                return ActionResult.success(isClient);
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof AnimalEntity animal) {
            boolean isClient = user.getWorld().isClient();

            int age = animal.getBreedingAge();
            if (age == 0 && !isClient) {
                animal.lovePlayer(user);
                return ActionResult.SUCCESS;
            }

            if (animal.isBaby()) {
                animal.growUp(AnimalEntity.toGrowUpAge(-age), true);
                return ActionResult.success(isClient);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable(String.format("item.%s.%s.tooltip", MagicMod.MOD_ID, NAME)));
    }
}
