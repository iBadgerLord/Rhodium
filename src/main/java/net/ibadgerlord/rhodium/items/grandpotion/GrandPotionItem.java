package net.ibadgerlord.rhodium.items.grandpotion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GrandPotionItem extends Item {

    public GrandPotionItem(Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }

}
