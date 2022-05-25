package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.items.potions.*;
import net.ibadgerlord.rhodium.items.TomeItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;


public class RhodiumItemRegistry {

    // Register Items method
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Rhodium.MOD_ID, name), item);
    }


    // Item naming and registering
    public static final Item ACORN = registerItem("acorn",
            new Item(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP)));

    public static final Item TOME = registerItem("tome",
            new TomeItem(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP).maxCount(1).rarity(Rarity.RARE)));

    public static final Item GRAND_BOTTLE = registerItem("grand_bottle",
            new Item(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP).maxCount(16)));
    public static final Item GRAND_LEAPING_BOOST = registerItem("grand_leaping_potion",
            new GrandLeaping(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item GRAND_LUCK_POTION = registerItem("grand_luck_potion",
            new GrandLuck(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item GRAND_REGENERATION_POTION = registerItem("grand_regeneration_potion",
            new GrandRegeneration(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item GRAND_STRENGTH_POTION = registerItem("grand_strength_potion",
            new GrandStrength(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item GRAND_SWIFTNESS_POTION = registerItem("grand_swiftness_potion",
            new GrandSwiftness(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP).maxCount(1).rarity(Rarity.UNCOMMON)));

    public static void registerRhodiumItems() { }

}
