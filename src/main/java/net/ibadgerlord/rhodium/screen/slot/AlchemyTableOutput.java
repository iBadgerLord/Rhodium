package net.ibadgerlord.rhodium.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class AlchemyTableOutput extends Slot {

    public AlchemyTableOutput(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }

}
