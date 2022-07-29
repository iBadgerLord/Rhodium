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

public class AlchemyTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
    private int progress = 0;

    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: {
                    return AlchemyTableBlockEntity.this.progress;
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
                    AlchemyTableBlockEntity.this.progress = value;
                    break;

                }
            }
        }

        @Override
        public int size() {
            return 1;
        }
    };

    public AlchemyTableBlockEntity(BlockPos pos, BlockState state) {
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
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("alchemy_table.progress", progress);
    }

    public static void tick(World world, BlockPos pos, BlockState state, AlchemyTableBlockEntity entity) {
        ItemStack itemStack5 = entity.inventory.get(5);

        boolean bl = hasRecipe(entity);
        boolean bl2 = entity.progress > 0;
        boolean bl3 = itemStack5.getCount() != itemStack5.getMaxCount();
        boolean bl4 = hasRecipe(entity);

        if (bl2 && bl3 && bl4) {
            --entity.progress;
            if (bl && (entity.progress == 0)) {
                craftItem(entity);
                world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f);
            }
        } else if (bl2 && !bl4) {
            entity.progress = 0;
        } else if (bl) {
            entity.progress = 512;
        }

    }

    private static boolean hasRecipe(AlchemyTableBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());

        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<AlchemyTableRecipe> match = world.getRecipeManager().getFirstMatch(AlchemyTableRecipe.Type.INSTANCE, inventory, world);
        return match.isPresent();
    }

    private static void craftItem(AlchemyTableBlockEntity entity) {
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