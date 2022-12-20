package net.ibadgerlord.rhodium.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CursedPotionItem extends Item {

    public CursedPotionItem(Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }

}