package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.items.CursedPotionItem;
import net.ibadgerlord.rhodium.items.GrandPotionItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.minecraft.entity.effect.StatusEffects.*;


public class RhodiumItemRegistry {

    // Register Items method
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Rhodium.MOD_ID, name), item);
    }

    // Item naming and registering
    public static final Item GRAND_BOTTLE = registerItem("grand_bottle",
            new Item(new FabricItemSettings().maxCount(16).group(RhodiumGroupRegistry.RHODIUM_GROUP)));
    public static final Item GRAND_WATER_BOTTLE = registerItem("grand_water_bottle",
            new Item(new FabricItemSettings().maxCount(1).group(RhodiumGroupRegistry.RHODIUM_GROUP)));
    public static final Item GRAND_POTION = registerItem("grand_potion",
            new GrandPotionItem(INSTANT_HEALTH, 1, 1, 1, new FabricItemSettings().maxCount(1).group(RhodiumGroupRegistry.RHODIUM_GROUP)));

    public static final Item CURSED_BOTTLE = registerItem("cursed_bottle",
            new Item(new FabricItemSettings().maxCount(16).group(RhodiumGroupRegistry.RHODIUM_GROUP)));
    public static final Item CURSED_WATER_BOTTLE = registerItem("cursed_water_bottle",
            new Item(new FabricItemSettings().maxCount(1).group(RhodiumGroupRegistry.RHODIUM_GROUP)));
    public static final Item CURSED_POTION = registerItem("cursed_potion",
            new CursedPotionItem( INSTANT_DAMAGE, 1, 1, 1, new FabricItemSettings().maxCount(1).group(RhodiumGroupRegistry.RHODIUM_GROUP)));

    public static void registerRhodiumItems() { }

}
