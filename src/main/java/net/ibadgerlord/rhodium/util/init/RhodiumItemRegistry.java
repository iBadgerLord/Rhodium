package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.items.grandpotion.GrandPotionItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class RhodiumItemRegistry {

    // Register Items method
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Rhodium.MOD_ID, name), item);
    }


    // Item naming and registering
    public static final Item GRAND_BOTTLE = registerItem("grand_bottle",
            new Item(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP).maxCount(16)));

    public static final Item GRAND_POTION = registerItem("grand_potion",
            new GrandPotionItem(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP).maxCount(1)));

    public static void registerRhodiumItems() { }

}
