package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.blocks.entity.AlchemyTableEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RhodiumBlockEntityRegistry {

    public static BlockEntityType<AlchemyTableEntity> ALCHEMY_TABLE;

    public static void registerRhodiumBlockEntities() {
        ALCHEMY_TABLE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Rhodium.MOD_ID, "alchemy_table"),
                FabricBlockEntityTypeBuilder.create(AlchemyTableEntity::new,
                        RhodiumBlockRegistry.ALCHEMY_TABLE).build(null));
    }

}
