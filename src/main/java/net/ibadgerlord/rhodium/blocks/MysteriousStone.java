package net.ibadgerlord.rhodium.blocks;

import net.ibadgerlord.rhodium.util.init.RhodiumBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MysteriousStone extends Block {

    public static final IntProperty STONE = IntProperty.of("stone", 1, 4);

    public MysteriousStone(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(STONE, 1));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        ItemStack itemStack = player.getStackInHand(hand);

        if ((state.get(STONE) == 1) && (itemStack.isOf(Items.GLOWSTONE_DUST))) {
            world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(pos, RhodiumBlockRegistry.COBALT_ORE.getDefaultState(), 1);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;

    }

        @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(STONE, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STONE);
    }

}
