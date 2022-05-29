package net.ibadgerlord.rhodium.items.brush;

import net.ibadgerlord.rhodium.blocks.MysteriousDeepslate;
import net.ibadgerlord.rhodium.blocks.MysteriousStone;
import net.ibadgerlord.rhodium.util.init.RhodiumBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CobaltBrushItem extends Item {

    public CobaltBrushItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();

        if (block instanceof MysteriousStone) {
            int stone = blockState.get(MysteriousStone.STONE);
            if (stone == 1) {
                world.setBlockState(blockPos, RhodiumBlockRegistry.COBALT_ORE.getDefaultState(), 1);
            } else if (stone == 2) {
                world.setBlockState(blockPos, RhodiumBlockRegistry.MYTHRIL_ORE.getDefaultState(), 1);
            }
            if (playerEntity != null) {
                itemStack.damage(1, playerEntity, (player) -> {
                    player.sendToolBreakStatus(context.getHand());
                });
            }
            return ActionResult.success(world.isClient);
        } else if (block instanceof MysteriousDeepslate) {
            int deepslate = blockState.get(MysteriousDeepslate.DEEPSLATE);
            if (deepslate == 1) {
                world.setBlockState(blockPos, RhodiumBlockRegistry.DEEPSLATE_COBALT_ORE.getDefaultState(), 1);
            } else if (deepslate == 2) {
                world.setBlockState(blockPos, RhodiumBlockRegistry.DEEPSLATE_MYTHRIL_ORE.getDefaultState(), 1);
            }
            if (playerEntity != null) {
                itemStack.damage(1, playerEntity, (player) -> {
                    player.sendToolBreakStatus(context.getHand());
                });
            }
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }

}
