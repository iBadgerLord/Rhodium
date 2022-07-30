package net.ibadgerlord.rhodium.blocks;

import net.ibadgerlord.rhodium.blocks.entity.AlchemyTableBlockEntity;
import net.ibadgerlord.rhodium.util.init.RhodiumBlockEntityRegistry;
import net.ibadgerlord.rhodium.util.init.RhodiumParticleRegistry;
import net.ibadgerlord.rhodium.util.other.FlameType;
import net.ibadgerlord.rhodium.util.other.RhodiumProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class AlchemyTableBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty BOOKS = IntProperty.of("books", 0, 3);
    public static final IntProperty CANDLES = IntProperty.of("candles", 0, 3);
    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    public static final EnumProperty<FlameType> FLAME_TYPE = RhodiumProperties.FLAME_TYPE;

    protected static final VoxelShape ALCHEMY_TABLE_BASE = Block.createCuboidShape(0, 0, 0, 16, 12, 16);
    protected static final VoxelShape BOOKS_PRIMARY_N = Block.createCuboidShape(3, 12, 2, 10, 14.003, 12);
    protected static final VoxelShape BOOKS_SECONDARY_N = Block.createCuboidShape(3, 12, 2, 10, 16.006, 12);
    protected static final VoxelShape BOOKS_TERTIARY_N = Block.createCuboidShape(3, 12, 2, 10, 18, 12);
    protected static final VoxelShape BOOKS_PRIMARY_E = Block.createCuboidShape(4, 12, 3, 14, 14.003, 10);
    protected static final VoxelShape BOOKS_SECONDARY_E = Block.createCuboidShape(4, 12, 3, 14, 16.006, 10);
    protected static final VoxelShape BOOKS_TERTIARY_E = Block.createCuboidShape(4, 12, 3, 14, 18, 10);
    protected static final VoxelShape BOOKS_PRIMARY_S = Block.createCuboidShape(6, 12, 4, 13, 14.003, 14);
    protected static final VoxelShape BOOKS_SECONDARY_S = Block.createCuboidShape(6, 12, 4, 13, 16.006, 14);
    protected static final VoxelShape BOOKS_TERTIARY_S = Block.createCuboidShape(6, 12, 4, 13, 18, 14);
    protected static final VoxelShape BOOKS_PRIMARY_W = Block.createCuboidShape(2, 12, 6, 12, 14.003, 13);
    protected static final VoxelShape BOOKS_SECONDARY_W = Block.createCuboidShape(2, 12, 6, 12, 16.006, 13);
    protected static final VoxelShape BOOKS_TERTIARY_W = Block.createCuboidShape(2, 12, 6, 12, 18, 13);
    protected static final VoxelShape CANDLE_PRIMARY_N = Block.createCuboidShape(12, 12, 12, 14, 18, 14);
    protected static final VoxelShape CANDLE_SECONDARY_N = Block.createCuboidShape(9, 12, 11, 15, 18, 14);
    protected static final VoxelShape CANDLE_TERTIARY_N = Block.createCuboidShape(10, 12, 10, 15, 18, 15);
    protected static final VoxelShape CANDLE_PRIMARY_E = Block.createCuboidShape(2, 12, 12, 4, 18, 14);
    protected static final VoxelShape CANDLE_SECONDARY_E = Block.createCuboidShape(2, 12, 9, 5, 18, 15);
    protected static final VoxelShape CANDLE_TERTIARY_E = Block.createCuboidShape(1, 12, 10, 6, 18, 15);
    protected static final VoxelShape CANDLE_PRIMARY_S = Block.createCuboidShape(2, 12, 2, 4, 18, 4);
    protected static final VoxelShape CANDLE_SECONDARY_S = Block.createCuboidShape(1, 12, 2, 7, 18, 5);
    protected static final VoxelShape CANDLE_TERTIARY_S = Block.createCuboidShape(1, 12, 1, 6, 18, 6);
    protected static final VoxelShape CANDLE_PRIMARY_W = Block.createCuboidShape(12, 12, 2, 14, 18, 4);
    protected static final VoxelShape CANDLE_SECONDARY_W = Block.createCuboidShape(11, 12, 1, 14, 18, 7);
    protected static final VoxelShape CANDLE_TERTIARY_W = Block.createCuboidShape(10, 12, 1, 15, 18, 6);

    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N = ALCHEMY_TABLE_BASE;
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_PRIMARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_SECONDARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_TERTIARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_E = ALCHEMY_TABLE_BASE;
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_PRIMARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_SECONDARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_TERTIARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_S = ALCHEMY_TABLE_BASE;
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_PRIMARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_SECONDARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_TERTIARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_W = ALCHEMY_TABLE_BASE;
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_PRIMARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_SECONDARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, CANDLE_TERTIARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_PRIMARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_N, CANDLE_PRIMARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_SECONDARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_N, CANDLE_SECONDARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_TERTIARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_N, CANDLE_TERTIARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_PRIMARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_E, CANDLE_PRIMARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_SECONDARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_E, CANDLE_SECONDARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_TERTIARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_E, CANDLE_TERTIARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_PRIMARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_S, CANDLE_PRIMARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_SECONDARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_S, CANDLE_SECONDARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_TERTIARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_S, CANDLE_TERTIARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_PRIMARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_W, CANDLE_PRIMARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_SECONDARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_W, CANDLE_SECONDARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_TERTIARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_PRIMARY_W, CANDLE_TERTIARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_NULL_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_PRIMARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_N, CANDLE_PRIMARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_SECONDARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_N, CANDLE_SECONDARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_TERTIARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_N, CANDLE_TERTIARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_NULL_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_PRIMARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_E, CANDLE_PRIMARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_SECONDARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_E, CANDLE_SECONDARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_TERTIARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_E, CANDLE_TERTIARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_NULL_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_PRIMARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_S, CANDLE_PRIMARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_SECONDARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_S, CANDLE_SECONDARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_TERTIARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_S, CANDLE_TERTIARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_NULL_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_PRIMARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_W, CANDLE_PRIMARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_SECONDARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_W, CANDLE_SECONDARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_TERTIARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_SECONDARY_W, CANDLE_TERTIARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_PRIMARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_N, CANDLE_PRIMARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_SECONDARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_N, CANDLE_SECONDARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_TERTIARY_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_N, CANDLE_TERTIARY_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_PRIMARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_E, CANDLE_PRIMARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_SECONDARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_E, CANDLE_SECONDARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_TERTIARY_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_E, CANDLE_TERTIARY_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_PRIMARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_S, CANDLE_PRIMARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_SECONDARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_S, CANDLE_SECONDARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_TERTIARY_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_S, CANDLE_TERTIARY_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_PRIMARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_W, CANDLE_PRIMARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_SECONDARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_W, CANDLE_SECONDARY_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_TERTIARY_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOKS_TERTIARY_W, CANDLE_TERTIARY_W);


    public AlchemyTableBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)
                .with(BOOKS, 0).with(CANDLES, 0).with(LIT, false).with(FLAME_TYPE, FlameType.NONE));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.BOOK)) {
            if (state.get(BOOKS) < 3) {
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                world.playSound(null, pos, SoundEvents.ITEM_BOOK_PUT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, state.with(BOOKS, Math.min(3, state.get(BOOKS) + 1)), Block.NOTIFY_ALL);
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            } else if (state.get(BOOKS) >= 3) {
                if (state.get(LIT)) {
                    NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                    if (screenHandlerFactory != null) {
                        player.openHandledScreen(screenHandlerFactory);
                    }
                } else {
                    return ActionResult.FAIL;
                }
            }
        } else if (itemStack.isOf(Items.CANDLE)) {
            if (state.get(CANDLES) < 3) {
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, state.with(CANDLES, Math.min(3, state.get(CANDLES) + 1)), Block.NOTIFY_ALL);
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            } else if (state.get(CANDLES) >= 3) {
                if (state.get(LIT)) {
                    NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                    if (screenHandlerFactory != null) {
                        player.openHandledScreen(screenHandlerFactory);
                    }
                } else {
                    return ActionResult.FAIL;
                }
            }
        } else if (itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.FIRE_CHARGE)) {
            if (!state.get(LIT)) {
                if (state.get(CANDLES) >= 1) {
                    if ((itemStack.isOf(Items.FLINT_AND_STEEL))) {
                        world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        if (!world.isClient) {
                            if (!player.isCreative()) {
                                itemStack.damage(1, world.getRandom(), null);
                            }
                        }
                    }
                    if ((itemStack.isOf(Items.FIRE_CHARGE))) {
                        world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        if (!player.isCreative()) {
                            itemStack.decrement(1);
                        }
                    }
                    world.setBlockState(pos, state.with(LIT, true).with(FLAME_TYPE, FlameType.REGULAR_FLAME), Block.NOTIFY_ALL);
                    world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                } else {
                    return ActionResult.FAIL;
                }
            } else if (state.get(LIT)) {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
        } else if ((itemStack.isOf(Items.SOUL_SAND) || itemStack.isOf(Items.SOUL_SOIL))) {
            if (state.get(FLAME_TYPE) != FlameType.SOUL_FLAME) {
                if (state.get(LIT)) {
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    world.playSound(null, pos, SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f); // Used as jingle for noe
                    world.setBlockState(pos, state.with(FLAME_TYPE, FlameType.SOUL_FLAME), Block.NOTIFY_ALL);
                    world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    if (state.get(CANDLES) == 1) {
                        if (state.get(FACING) == Direction.NORTH) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                        }
                        if (state.get(FACING) == Direction.EAST) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.1875, pos.getY() + 1.25, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                        }
                        if (state.get(FACING) == Direction.SOUTH) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.1875, pos.getY() + 1.25, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                        }
                        if (state.get(FACING) == Direction.WEST) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                        }
                    }
                    if (state.get(CANDLES) == 2) {
                        if (state.get(FACING) == Direction.NORTH) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.75, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.625, pos.getY() + 1.1875, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                        }
                        if (state.get(FACING) == Direction.EAST) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.25, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.1875, pos.getY() + 1.1875, pos.getZ() + 0.625, 0.0f, 0.0f, 0.0f);
                        }
                        if (state.get(FACING) == Direction.SOUTH) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.25, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.375, pos.getY() + 1.1875, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                        }
                        if (state.get(FACING) == Direction.WEST) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.75, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.375, 0.0f, 0.0f, 0.0f);
                        }
                    }
                    if (state.get(CANDLES) == 3) {
                        if (state.get(FACING) == Direction.NORTH) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.6875, pos.getY() + 1.0625, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                        }
                        if (state.get(FACING) == Direction.EAST) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.1875, pos.getY() + 1.0625, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.3125, pos.getY() + 1.1875, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                        }
                        if (state.get(FACING) == Direction.SOUTH) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.3125, pos.getY() + 1.0625, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.1875, pos.getY() + 1.1875, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                        }
                        if (state.get(FACING) == Direction.WEST) {
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.8125, pos.getY() + 1.0625, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.6875, pos.getY() + 1.1875, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                            world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                        }
                    }
                } else {
                    return ActionResult.FAIL;
                }
            } else if (state.get(FLAME_TYPE) == FlameType.SOUL_FLAME) {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
        } else if (itemStack.isOf(Items.WOODEN_SHOVEL) || itemStack.isOf(Items.STONE_SHOVEL)
                || itemStack.isOf(Items.IRON_SHOVEL) || itemStack.isOf(Items.GOLDEN_SHOVEL)
                || itemStack.isOf(Items.DIAMOND_SHOVEL) || itemStack.isOf(Items.NETHERITE_SHOVEL)) {
            if (state.get(LIT)) {
                if (!world.isClient) {
                    if (!player.isCreative()) {
                        itemStack.damage(1, world.getRandom(), null);
                    }
                }
                world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, state.with(LIT, false).with(FLAME_TYPE, FlameType.NONE), Block.NOTIFY_ALL);
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            } else {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
        } else {
            if (state.get(LIT)) {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            } else {
                return ActionResult.FAIL;
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.get(LIT).booleanValue()) {
            if (state.get(CANDLES) == 1) {
                if (state.get(FACING) == Direction.NORTH) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.8125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                }
                if (state.get(FACING) == Direction.EAST) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.8125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                }
                if (state.get(FACING) == Direction.WEST) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                }
            }
            if (state.get(CANDLES) == 2) {
                if (state.get(FACING) == Direction.NORTH) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.75, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.75, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float b = random.nextFloat();
                    if (b < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.625, pos.getY() + 1.1875, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                        if (b < 0.17f) {
                            world.playSound(pos.getX() + 0.625, pos.getY() + 1.1875, pos.getZ() + 0.8125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }

                    }
                }
                if (state.get(FACING) == Direction.EAST) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.1875, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.1875, pos.getY() + 1.25, pos.getZ() + 0.875, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float b = random.nextFloat();
                    if (b < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.125, pos.getY() + 1.1875, pos.getZ() + 0.625, 0.0f, 0.0f, 0.0f);
                        if (b < 0.17f) {
                            world.playSound(pos.getX() + 0.125, pos.getY() + 1.1875, pos.getZ() + 0.625, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }

                    }
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.0625, pos.getY() + 1.25, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.0625, pos.getY() + 1.25, pos.getZ() + 0.1875, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float b = random.nextFloat();
                    if (b < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.3125, pos.getY() + 1.1875, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                        if (b < 0.17f) {
                            world.playSound(pos.getX() + 0.3125, pos.getY() + 1.1875, pos.getZ() + 0.125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }

                    }
                }
                if (state.get(FACING) == Direction.WEST) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.75, pos.getY() + 1.25, pos.getZ() + 0.0625, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.75, pos.getY() + 1.25, pos.getZ() + 0.0625, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float b = random.nextFloat();
                    if (b < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                        if (b < 0.17f) {
                            world.playSound(pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.3125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }

                    }
                }
            }
            if (state.get(CANDLES) == 3) {
                if (state.get(FACING) == Direction.NORTH) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.6875, pos.getY() + 1.0625, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.6875, pos.getY() + 1.0625, pos.getZ() + 0.8125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float b = random.nextFloat();
                    if (b < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                        if (b < 0.17f) {
                            world.playSound(pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.6875, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }

                    }
                    float c = random.nextFloat();
                    if (c < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                        if (c < 0.17f) {
                            world.playSound(pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.875, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                }
                if (state.get(FACING) == Direction.EAST) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.1875, pos.getY() + 1.0625, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.1875, pos.getY() + 1.0625, pos.getZ() + 0.6875, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float b = random.nextFloat();
                    if (b < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.3125, pos.getY() + 1.1875, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                        if (b < 0.17f) {
                            world.playSound(pos.getX() + 0.3125, pos.getY() + 1.1875, pos.getZ() + 0.8125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float c = random.nextFloat();
                    if (c < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                        if (c < 0.17f) {
                            world.playSound(pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.875, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.3125, pos.getY() + 1.0625, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.3125, pos.getY() + 1.0625, pos.getZ() + 0.1875, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float b = random.nextFloat();
                    if (b < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.1875, pos.getY() + 1.1875, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                        if (b < 0.17f) {
                            world.playSound(pos.getX() + 0.1875, pos.getY() + 1.1875, pos.getZ() + 0.3125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float c = random.nextFloat();
                    if (c < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                        if (c < 0.17f) {
                            world.playSound(pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                }
                if (state.get(FACING) == Direction.WEST) {
                    float a = random.nextFloat();
                    if (a < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.8125, pos.getY() + 1.0625, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                        if (a < 0.17f) {
                            world.playSound(pos.getX() + 0.8125, pos.getY() + 1.0625, pos.getZ() + 0.3125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float b = random.nextFloat();
                    if (b < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.6875, pos.getY() + 1.1875, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                        if (b < 0.17f) {
                            world.playSound(pos.getX() + 0.6875, pos.getY() + 1.1875, pos.getZ() + 0.1875, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                    float c = random.nextFloat();
                    if (c < 0.3f) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                        if (c < 0.17f) {
                            world.playSound(pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.125, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
                        }
                    }
                }
            }
        }

        // regular fire particles
        if ((state.get(LIT).booleanValue()) && (state.get(FLAME_TYPE) == FlameType.REGULAR_FLAME)) {
            if (state.get(CANDLES) == 1) {
                if (state.get(FACING) == Direction.NORTH) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.EAST) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.1875, pos.getY() + 1.25, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.1875, pos.getY() + 1.25, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.WEST) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                }
            }
            if (state.get(CANDLES) == 2) {
                if (state.get(FACING) == Direction.NORTH) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.75, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.625, pos.getY() + 1.1875, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.EAST) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.25, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.1875, pos.getY() + 1.1875, pos.getZ() + 0.625, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.25, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.375, pos.getY() + 1.1875, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.WEST) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.75, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.375, 0.0f, 0.0f, 0.0f);
                }
            }
            if (state.get(CANDLES) == 3) {
                if (state.get(FACING) == Direction.NORTH) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.6875, pos.getY() + 1.0625, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.EAST) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.1875, pos.getY() + 1.0625, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.3125, pos.getY() + 1.1875, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.3125, pos.getY() + 1.0625, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.1875, pos.getY() + 1.1875, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.WEST) {
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.8125, pos.getY() + 1.0625, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.6875, pos.getY() + 1.1875, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                }
            }
        }

        // soul fire particles
        if ((state.get(LIT).booleanValue()) && (state.get(FLAME_TYPE) == FlameType.SOUL_FLAME)) {
            if (state.get(CANDLES) == 1) {
                if (state.get(FACING) == Direction.NORTH) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.EAST) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.1875, pos.getY() + 1.25, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.1875, pos.getY() + 1.25, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.WEST) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.8125, pos.getY() + 1.25, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                }
            }
            if (state.get(CANDLES) == 2) {
                if (state.get(FACING) == Direction.NORTH) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.75, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.625, pos.getY() + 1.1875, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.EAST) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.25, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.1875, pos.getY() + 1.1875, pos.getZ() + 0.625, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.25, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.375, pos.getY() + 1.1875, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.WEST) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.75, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.375, 0.0f, 0.0f, 0.0f);
                }
            }
            if (state.get(CANDLES) == 3) {
                if (state.get(FACING) == Direction.NORTH) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.6875, pos.getY() + 1.0625, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.8125, pos.getY() + 1.1875, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.EAST) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.1875, pos.getY() + 1.0625, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.3125, pos.getY() + 1.1875, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.3125, pos.getY() + 1.0625, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.1875, pos.getY() + 1.1875, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.WEST) {
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.8125, pos.getY() + 1.0625, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.6875, pos.getY() + 1.1875, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, pos.getX() + 0.875, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                }
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(BOOKS)) {
            case 0:
                switch (state.get(CANDLES)) {
                    case 0:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                        }
                    case 1:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_N;
                        }
                    case 2:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_N;
                        }
                    case 3:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_N;
                        }
                    default:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                        }
                }
            case 1:
                switch (state.get(CANDLES)) {
                    case 0:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_N;
                        }
                    case 1:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_PRIMARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_PRIMARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_PRIMARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_PRIMARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_PRIMARY_N;
                        }
                    case 2:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_SECONDARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_SECONDARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_SECONDARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_SECONDARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_SECONDARY_N;
                        }
                    case 3:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_TERTIARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_TERTIARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_TERTIARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_TERTIARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_TERTIARY_N;
                        }
                    default:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_PRIMARY_CANDLES_NULL_N;
                        }
                }
            case 2:
                switch (state.get(CANDLES)) {
                    case 0:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_NULL_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_NULL_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_NULL_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_NULL_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_NULL_N;
                        }
                    case 1:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_PRIMARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_PRIMARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_PRIMARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_PRIMARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_PRIMARY_N;
                        }
                    case 2:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_SECONDARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_SECONDARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_SECONDARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_SECONDARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_SECONDARY_N;
                        }
                    case 3:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_TERTIARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_TERTIARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_TERTIARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_TERTIARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_SECONDARY_CANDLES_TERTIARY_N;
                        }
                    default:
                }
            case 3:
                switch (state.get(CANDLES)) {
                    case 0:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_N;
                        }
                    case 1:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_PRIMARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_PRIMARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_PRIMARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_PRIMARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_PRIMARY_N;
                        }
                    case 2:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_SECONDARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_SECONDARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_SECONDARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_SECONDARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_SECONDARY_N;
                        }
                    case 3:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_TERTIARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_TERTIARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_TERTIARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_TERTIARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_TERTIARY_N;
                        }
                    default:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_TERTIARY_CANDLES_NULL_N;
                        }
                }
            default:
                switch (state.get(CANDLES)) {
                    case 0:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                        }
                    case 1:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_PRIMARY_N;
                        }
                    case 2:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_SECONDARY_N;
                        }
                    case 3:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_E;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_S;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_W;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_TERTIARY_N;
                        }
                    default:
                        switch (state.get(FACING)) {
                            case NORTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            case EAST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            case SOUTH:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            case WEST:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                            default:
                                return ALCHEMY_TABLE_BOOKS_NULL_CANDLES_NULL_N;
                        }
                }
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()))
                .with(BOOKS, 0).with(CANDLES, 0).with(LIT, false).with(FLAME_TYPE, FlameType.NONE);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BOOKS, FACING, CANDLES, LIT, FLAME_TYPE);
    }

    // Block Entity
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AlchemyTableBlockEntity) {
                ItemScatterer.spawn(world, pos, (AlchemyTableBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AlchemyTableBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, RhodiumBlockEntityRegistry.ALCHEMY_TABLE, AlchemyTableBlockEntity::tick);
    }

}