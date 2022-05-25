package net.ibadgerlord.rhodium.screen;

import net.ibadgerlord.rhodium.screen.slot.AlchemyTableAdditives;
import net.ibadgerlord.rhodium.screen.slot.AlchemyTableFuel;
import net.ibadgerlord.rhodium.screen.slot.AlchemyTableOutput;
import net.ibadgerlord.rhodium.util.init.RhodiumScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AlchemyTableScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public AlchemyTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(7), new ArrayPropertyDelegate(2));
    }

    public AlchemyTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(RhodiumScreenHandlerRegistry.ALCHEMY_TABLE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 7);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = propertyDelegate;

        // Where our slots are placed
        this.addSlot(new AlchemyTableAdditives(inventory, 0, 54, 17));
        this.addSlot(new AlchemyTableAdditives(inventory, 1, 106, 17));
        this.addSlot(new AlchemyTableAdditives(inventory, 2, 54, 53));
        this.addSlot(new AlchemyTableAdditives(inventory, 3, 106, 53));
        this.addSlot(new AlchemyTableAdditives(inventory, 4, 80, 35));
        this.addSlot(new AlchemyTableOutput(inventory, 5, 138, 35));
        this.addSlot(new AlchemyTableFuel(inventory, 6, 22, 31));
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(propertyDelegate);
    }


    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getFuel() {
        return this.propertyDelegate.get(1);
    }

    public int getProgress() {
        return this.propertyDelegate.get(0);
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

}