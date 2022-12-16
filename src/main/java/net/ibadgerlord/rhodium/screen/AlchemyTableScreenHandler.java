package net.ibadgerlord.rhodium.screen;

import net.ibadgerlord.rhodium.screen.slot.AlchemyTableOutput;
import net.ibadgerlord.rhodium.screen.slot.AlchemyTableSlot;
import net.ibadgerlord.rhodium.screen.slot.AlchemyTableSubject;
import net.ibadgerlord.rhodium.util.init.RhodiumScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AlchemyTableScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public AlchemyTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(1));
    }

    public AlchemyTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(RhodiumScreenHandlerRegistry.ALCHEMY_TABLE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 6);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = propertyDelegate;

        // Where our slots are placed
        this.addSlot(new AlchemyTableSlot(inventory, 0, 24, 17)); // regular slot for alchemy table
        this.addSlot(new AlchemyTableSlot(inventory, 1, 74, 17)); // regular slot for alchemy table
        this.addSlot(new AlchemyTableSlot(inventory, 2, 24, 53)); // regular slot for alchemy table
        this.addSlot(new AlchemyTableSlot(inventory, 3, 74, 53)); // regular slot for alchemy table
        this.addSlot(new AlchemyTableSubject(inventory, 4, 49, 35)); // subject slot for alchemy table
        this.addSlot(new AlchemyTableOutput(inventory, 5, 132, 35)); // output slot for alchemy table

        // Adding the player inventory
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(propertyDelegate);
    }


    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getProgress() {
        return this.propertyDelegate.get(0);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
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