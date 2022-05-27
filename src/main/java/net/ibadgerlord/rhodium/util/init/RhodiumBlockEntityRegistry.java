package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.blocks.entity.AlchemyTableEntity;
import net.ibadgerlord.rhodium.blocks.entity.MysteriousDeepslateEntity;
import net.ibadgerlord.rhodium.blocks.entity.MysteriousStoneEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RhodiumBlockEntityRegistry {

    public static BlockEntityType<AlchemyTableEntity> ALCHEMY_TABLE;
    public static BlockEntityType<MysteriousStoneEntity> MYSTERIOUS_STONE;
    public static BlockEntityType<MysteriousDeepslateEntity> MYSTERIOUS_DEEPSLATE;

    public static void registerRhodiumBlockEntities() {
        ALCHEMY_TABLE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Rhodium.MOD_ID, "alchemy_table"),
                FabricBlockEntityTypeBuilder.create(AlchemyTableEntity::new,
                        RhodiumBlockRegistry.ALCHEMY_TABLE).build(null));
        MYSTERIOUS_STONE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Rhodium.MOD_ID, "mysterious_stone"),
                FabricBlockEntityTypeBuilder.create(MysteriousStoneEntity::new,
                        RhodiumBlockRegistry.MYSTERIOUS_STONE).build(null));
        MYSTERIOUS_DEEPSLATE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Rhodium.MOD_ID, "mysterious_deepslate"),
                FabricBlockEntityTypeBuilder.create(MysteriousDeepslateEntity::new,
                        RhodiumBlockRegistry.MYSTERIOUS_DEEPSLATE).build(null));

    }

}
