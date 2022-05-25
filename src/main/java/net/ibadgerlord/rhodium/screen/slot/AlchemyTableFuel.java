package net.ibadgerlord.rhodium.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class AlchemyTableFuel extends Slot {

    public AlchemyTableFuel(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return AlchemyTableFuel.matches(stack);
    }

    @Override
    public int getMaxItemCount() {
        return 64;
    }

    public static boolean matches(ItemStack stack) {
        return stack.isOf(Items.AMETHYST_SHARD);
    }

}