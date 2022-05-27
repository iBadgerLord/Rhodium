package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.blocks.AlchemyTable;
import net.ibadgerlord.rhodium.blocks.MysteriousDeepslate;
import net.ibadgerlord.rhodium.blocks.MysteriousStone;
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
            new AlchemyTable(FabricBlockSettings.copyOf(Blocks.ENCHANTING_TABLE).nonOpaque()
                    .luminance((state) -> state.get(AlchemyTable.LIT) ? 7 : 0)), RhodiumGroupRegistry.RHODIUM_GROUP);

    public static final Block MYSTERIOUS_STONE = registerBlock("mysterious_stone",
            new MysteriousStone(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);
    public static final Block MYSTERIOUS_DEEPSLATE = registerBlock("mysterious_deepslate",
            new MysteriousDeepslate(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);

    public static final Block COBALT_ORE = registerBlock("cobalt_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);
    public static final Block DEEPSLATE_COBALT_ORE = registerBlock("deepslate_cobalt_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);
    public static final Block MYTHRIL_ORE = registerBlock("mythril_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);
    public static final Block DEEPSLATE_MYTHRIL_ORE = registerBlock("deepslate_mythril_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);
    public static final Block PALLADIUM_ORE = registerBlock("palladium_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);
    public static final Block DEEPSLATE_PALLADIUM_ORE = registerBlock("deepslate_palladium_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);
    public static final Block ORICHALCUM_ORE = registerBlock("orichalcum_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);
    public static final Block DEEPSLATE_ORICHALCUM_ORE = registerBlock("deepslate_orichalcum_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)), RhodiumGroupRegistry.RHODIUM_GROUP);

    public static void registerRhodiumBlocks() { }

}
