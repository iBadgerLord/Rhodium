package net.ibadgerlord.rhodium.blocks.entity;

import net.ibadgerlord.rhodium.recipe.AlchemyTableRecipe;
import net.ibadgerlord.rhodium.screen.AlchemyTableScreenHandler;
import net.ibadgerlord.rhodium.util.init.RhodiumBlockEntityRegistry;
import net.ibadgerlord.rhodium.util.other.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlchemyTableEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(7, ItemStack.EMPTY);
    private int progress = 0;
    private int fuel = 0;

    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: {
                    return AlchemyTableEntity.this.progress;
                }
                case 1: {
                    return AlchemyTableEntity.this.fuel;
                }
                default: {
                    return 0;
                }
            }

        }

        @Override
        public void set(int index, int value) {
            switch(index) {
                case 0: {
                    AlchemyTableEntity.this.progress = value;
                    break;

                }
                case 1: {
                    AlchemyTableEntity.this.fuel = value;
                    break;
                }
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    public AlchemyTableEntity(BlockPos pos, BlockState state) {
        super(RhodiumBlockEntityRegistry.ALCHEMY_TABLE, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("container.alchemy_table");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new AlchemyTableScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("alchemy_table.progress");
        fuel = nbt.getInt("alchemy_table.fuel");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("alchemy_table.progress", progress);
        nbt.putInt("alchemy_table.fuel", fuel);
    }

    public static void tick(World world, BlockPos pos, BlockState state, AlchemyTableEntity entity) {
        ItemStack itemStack6 = entity.inventory.get(6);
        ItemStack itemStack5 = entity.inventory.get(5);

        if (entity.fuel <= 0 && itemStack6.isOf(Items.AMETHYST_SHARD)) {
            entity.fuel = 20;
            itemStack6.decrement(1);
        }

        boolean bl = hasRecipe(entity);
        boolean bl2 = entity.fuel > 0;
        boolean bl3 = entity.progress > 0;
        boolean bl4 = (itemStack5.getCount() == 0);
        boolean bl5 = hasRecipe(entity);

        if (bl3 && bl5) {
            --entity.progress;
            if (bl && (entity.progress == 0)) {
                craftItem(entity);
                world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f);
            }
        } else if (bl3 && !bl5) {
            entity.progress = 0;
        } else if (bl && bl2) {
            --entity.fuel;
            entity.progress = 256;
        }

    }

    private static boolean hasRecipe(AlchemyTableEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());

        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<AlchemyTableRecipe> match = world.getRecipeManager().getFirstMatch(AlchemyTableRecipe.Type.INSTANCE, inventory, world);
        return match.isPresent();
    }

    private static void craftItem(AlchemyTableEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<AlchemyTableRecipe> match = world.getRecipeManager()
                .getFirstMatch(AlchemyTableRecipe.Type.INSTANCE, inventory, world);

        if (match.isPresent()) {
            entity.removeStack(0,1);
            entity.removeStack(1,1);
            entity.removeStack(2,1);
            entity.removeStack(3,1);
            entity.removeStack(4,1);

            entity.setStack(5, new ItemStack(match.get().getOutput().getItem(),
                    entity.getStack(5).getCount() + 1));

        }
    }

}