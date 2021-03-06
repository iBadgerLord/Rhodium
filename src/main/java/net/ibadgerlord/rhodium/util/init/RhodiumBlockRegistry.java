package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.blocks.AlchemyTableBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RhodiumBlockRegistry {

    // Register Blocks method
    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Rhodium.MOD_ID, name), block);
    }

    // Register Block Items method
    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(Rhodium.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    // Block naming and registering
    public static final Block ALCHEMY_TABLE = registerBlock("alchemy_table",
            new AlchemyTableBlock(FabricBlockSettings.of(Material.STONE, MapColor.PURPLE)
                    .requiresTool().strength(5.0F, 1200.0F)
                    .luminance((state) -> state.get(AlchemyTableBlock.LIT) ? 7 : 0)),
            RhodiumGroupRegistry.RHODIUM_GROUP);

    public static void registerRhodiumBlocks() { }

}