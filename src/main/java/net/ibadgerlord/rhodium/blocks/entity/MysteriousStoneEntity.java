package net.ibadgerlord.rhodium.blocks.entity;

import net.ibadgerlord.rhodium.blocks.MysteriousStone;
import net.ibadgerlord.rhodium.util.init.RhodiumBlockEntityRegistry;
import net.ibadgerlord.rhodium.util.init.RhodiumBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MysteriousStoneEntity extends BlockEntity {

    public MysteriousStoneEntity(BlockPos pos, BlockState state) {
        super(RhodiumBlockEntityRegistry.MYSTERIOUS_STONE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, MysteriousStoneEntity entity) {

        boolean hardmode = state.get(MysteriousStone.HARDMODE);
        int stone = state.get(MysteriousStone.STONE);

        if (hardmode) {
            if (stone == 1) {
                world.setBlockState(pos, RhodiumBlockRegistry.COBALT_ORE.getDefaultState(), 1);
            } else if (stone == 2) {
                world.setBlockState(pos, RhodiumBlockRegistry.MYTHRIL_ORE.getDefaultState(), 1);
            } else if (stone == 3) {
                world.setBlockState(pos, RhodiumBlockRegistry.PALLADIUM_ORE.getDefaultState(), 1);
            } else if (stone == 4) {
                world.setBlockState(pos, RhodiumBlockRegistry.ORICHALCUM_ORE.getDefaultState(), 1);
            }
        }

    }


}
