package net.ibadgerlord.rhodium.blocks;

import net.ibadgerlord.rhodium.blocks.entity.MysteriousDeepslateEntity;
import net.ibadgerlord.rhodium.util.init.RhodiumBlockEntityRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MysteriousDeepslate extends BlockWithEntity implements BlockEntityProvider {

    public static final IntProperty DEEPSLATE = IntProperty.of("deepslate", 0, 4);
    public static final BooleanProperty HARDMODE = BooleanProperty.of("hardmode");

    public MysteriousDeepslate(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState())
                .with(DEEPSLATE, 0).with(HARDMODE, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        ItemStack itemStack = player.getStackInHand(hand);

        if (state.get(DEEPSLATE) == 0) {
            world.setBlockState(pos, state.with(DEEPSLATE, 1), Block.NOTIFY_ALL);
        } else if (state.get(DEEPSLATE) == 1) {
            world.setBlockState(pos, state.with(DEEPSLATE, 2), Block.NOTIFY_ALL);
        } else if (state.get(DEEPSLATE) == 2) {
            world.setBlockState(pos, state.with(DEEPSLATE, 3), Block.NOTIFY_ALL);
        } else if (state.get(DEEPSLATE) == 3) {
            world.setBlockState(pos, state.with(DEEPSLATE, 4), Block.NOTIFY_ALL);
        }

        if ((itemStack.isOf(Items.NETHER_STAR)) && !state.get(HARDMODE)) {
            world.setBlockState(pos, state.with(HARDMODE, true), Block.NOTIFY_ALL);
        }

        return ActionResult.SUCCESS;

    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(DEEPSLATE, 0).with(HARDMODE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DEEPSLATE).add(HARDMODE);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MysteriousDeepslateEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, RhodiumBlockEntityRegistry.MYSTERIOUS_DEEPSLATE, MysteriousDeepslateEntity::tick);
    }


}
