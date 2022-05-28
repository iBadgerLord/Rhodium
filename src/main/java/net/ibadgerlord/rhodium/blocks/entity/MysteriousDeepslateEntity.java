package net.ibadgerlord.rhodium.blocks.entity;

import net.ibadgerlord.rhodium.blocks.MysteriousDeepslate;
import net.ibadgerlord.rhodium.util.init.RhodiumBlockEntityRegistry;
import net.ibadgerlord.rhodium.util.init.RhodiumBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MysteriousDeepslateEntity extends BlockEntity {

    public MysteriousDeepslateEntity(BlockPos pos, BlockState state) {
        super(RhodiumBlockEntityRegistry.MYSTERIOUS_DEEPSLATE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, MysteriousDeepslateEntity entity) {


        boolean hardmode = state.get(MysteriousDeepslate.HARDMODE);
        int stone = state.get(MysteriousDeepslate.DEEPSLATE);

        if (hardmode) {
            if (stone == 1) {
                world.setBlockState(pos, RhodiumBlockRegistry.DEEPSLATE_COBALT_ORE.getDefaultState(), 1);
            } else if (stone == 2) {
                world.setBlockState(pos, RhodiumBlockRegistry.DEEPSLATE_MYTHRIL_ORE.getDefaultState(), 1);
            } else if (stone == 3) {
                world.setBlockState(pos, RhodiumBlockRegistry.DEEPSLATE_PALLADIUM_ORE.getDefaultState(), 1);
            } else if (stone == 4) {
                world.setBlockState(pos, RhodiumBlockRegistry.DEEPSLATE_ORICHALCUM_ORE.getDefaultState(), 1);
            }
        }

    }


}
