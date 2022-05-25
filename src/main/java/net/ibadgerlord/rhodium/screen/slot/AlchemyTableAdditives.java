package net.ibadgerlord.rhodium.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

public class AlchemyTableAdditives extends Slot {

    public AlchemyTableAdditives(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }

}
