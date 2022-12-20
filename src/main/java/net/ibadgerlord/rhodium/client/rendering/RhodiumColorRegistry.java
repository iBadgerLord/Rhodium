package net.ibadgerlord.rhodium.client.rendering;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.ibadgerlord.rhodium.util.init.RhodiumItemRegistry;
import net.minecraft.item.ItemStack;

public class RhodiumColorRegistry {

    public static void registerRhodiumColors() {
        ColorProviderRegistry.ITEM.register(RhodiumColorRegistry::grandWaterBottleColor, RhodiumItemRegistry.GRAND_WATER_BOTTLE);
        ColorProviderRegistry.ITEM.register(RhodiumColorRegistry::cursedWaterBottleColor, RhodiumItemRegistry.CURSED_WATER_BOTTLE);
    }

    private static int grandWaterBottleColor(ItemStack stack, int tintIndex) {
        return tintIndex == 1 ? 0x385dc6 : -1;
    }
    private static int cursedWaterBottleColor(ItemStack stack, int tintIndex) {
        return tintIndex == 1 ? 0x385dc6 : -1;
    }

}
