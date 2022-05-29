package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.items.brush.CobaltBrushItem;
import net.ibadgerlord.rhodium.items.brush.MythrilBrushItem;
import net.ibadgerlord.rhodium.items.brush.OrichalcumBrushItem;
import net.ibadgerlord.rhodium.items.brush.PalladiumBrushItem;
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
            new Item(new FabricItemSettings().maxCount(16).group(RhodiumGroupRegistry.RHODIUM_GROUP)));

    public static final Item GRAND_POTION = registerItem("grand_potion",
            new GrandPotionItem(new FabricItemSettings().maxCount(1).group(RhodiumGroupRegistry.RHODIUM_GROUP)));

    public static final Item WARPED_WART = registerItem("warped_wart",
            new Item(new FabricItemSettings().group(RhodiumGroupRegistry.RHODIUM_GROUP)));

    public static final Item COBALT_BRUSH = registerItem("cobalt_brush",
            new CobaltBrushItem(new FabricItemSettings().maxDamage(64).group(RhodiumGroupRegistry.RHODIUM_GROUP)));
    public static final Item MYTHRIL_BRUSH = registerItem("mythril_brush",
            new MythrilBrushItem(new FabricItemSettings().maxDamage(80).group(RhodiumGroupRegistry.RHODIUM_GROUP)));
    public static final Item PALLADIUM_BRUSH = registerItem("palladium_brush",
            new PalladiumBrushItem(new FabricItemSettings().maxDamage(128).group(RhodiumGroupRegistry.RHODIUM_GROUP)));
    public static final Item ORICHALCUM_BRUSH = registerItem("orichalcum_brush",
            new OrichalcumBrushItem(new FabricItemSettings().maxDamage(144).group(RhodiumGroupRegistry.RHODIUM_GROUP)));

    public static void registerRhodiumItems() { }

}
