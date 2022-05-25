package net.ibadgerlord.rhodium.blocks;

import net.ibadgerlord.rhodium.blocks.entity.AlchemyTableEntity;
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
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.stream.Stream;

public class AlchemyTable extends BlockWithEntity implements BlockEntityProvider {

    protected static final VoxelShape ALCHEMY_TABLE_BASE = Block.createCuboidShape(0, 0, 0, 16, 12, 16);

    protected static final VoxelShape BOOK_N = Block.createCuboidShape(3, 12, 2, 10, 14.003, 12);
    protected static final VoxelShape BOOK_E = Block.createCuboidShape(4, 12, 3, 14, 14.003, 10);
    protected static final VoxelShape BOOK_S = Block.createCuboidShape(6, 12, 4, 13, 14.003, 14);
    protected static final VoxelShape BOOK_W = Block.createCuboidShape(2, 12, 6, 12, 14.003, 13);

    protected static final VoxelShape CANDLE_1_N = Block.createCuboidShape(12, 12, 12, 14, 18, 14);
    protected static final VoxelShape CANDLE_2_N = Stream.of(
            Block.createCuboidShape(9, 14, 11, 10, 18, 12),
            Block.createCuboidShape(10, 12, 11, 15, 18, 14),
            Block.createCuboidShape(9, 12, 12, 10, 18, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    protected static final VoxelShape CANDLE_3_N = Block.createCuboidShape(10, 12, 10, 15, 18, 15);

    protected static final VoxelShape CANDLE_1_E = Block.createCuboidShape(2, 12, 12, 4, 18, 14);
    protected static final VoxelShape CANDLE_2_E = Stream.of(
            Block.createCuboidShape(4, 14, 9, 5, 18, 10),
            Block.createCuboidShape(2, 12, 10, 5, 18, 15),
            Block.createCuboidShape(2, 12, 9, 4, 18, 10)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    protected static final VoxelShape CANDLE_3_E = Block.createCuboidShape(1, 12, 10, 6, 18, 15);

    protected static final VoxelShape CANDLE_1_S = Block.createCuboidShape(2, 12, 2, 4, 18, 4);
    protected static final VoxelShape CANDLE_2_S = Stream.of(
            Block.createCuboidShape(6, 14, 4, 7, 18, 5),
            Block.createCuboidShape(1, 12, 2, 6, 18, 5),
            Block.createCuboidShape(6, 12, 2, 7, 18, 4)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    protected static final VoxelShape CANDLE_3_S = Block.createCuboidShape(1, 12, 1, 6, 18, 6);

    protected static final VoxelShape CANDLE_1_W = Block.createCuboidShape(12, 12, 2, 14, 18, 4);
    protected static final VoxelShape CANDLE_2_W = Stream.of(
            Block.createCuboidShape(11, 14, 6, 12, 18, 7),
            Block.createCuboidShape(11, 12, 1, 14, 18, 6),
            Block.createCuboidShape(12, 12, 6, 14, 18, 7)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    protected static final VoxelShape CANDLE_3_W = Block.createCuboidShape(10, 12, 1, 15, 18, 6);

    protected static final VoxelShape ALCHEMY_TABLE_BOOK_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_1_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_N, CANDLE_1_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_2_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_N, CANDLE_2_N);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_3_N = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_N, CANDLE_3_N);

    protected static final VoxelShape ALCHEMY_TABLE_BOOK_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_1_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_E, CANDLE_1_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_2_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_E, CANDLE_2_E);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_3_E = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_E, CANDLE_3_E);

    protected static final VoxelShape ALCHEMY_TABLE_BOOK_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_1_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_S, CANDLE_1_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_2_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_S, CANDLE_2_S);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_3_S = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_S, CANDLE_3_S);

    protected static final VoxelShape ALCHEMY_TABLE_BOOK_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_1_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_W, CANDLE_1_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_2_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_W, CANDLE_2_W);
    protected static final VoxelShape ALCHEMY_TABLE_BOOK_CANDLE_3_W = VoxelShapes.union(ALCHEMY_TABLE_BASE, BOOK_W, CANDLE_3_W);

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_BOOK = BooleanProperty.of("has_book");
    public static final IntProperty CANDLES = IntProperty.of("candles", 0, 3);
    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    public static final EnumProperty<FlameType> FLAME_TYPE = RhodiumProperties.FLAME_TYPE;

    public AlchemyTable(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)
                .with(HAS_BOOK, false).with(CANDLES, 0).with(LIT, false).with(FLAME_TYPE, FlameType.NONE));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT).booleanValue()) {
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
        if (state.get(FLAME_TYPE) == FlameType.REGULAR_FLAME) {
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
        if (state.get(FLAME_TYPE) == FlameType.SOUL_FLAME) {
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

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        ItemStack itemStack = player.getStackInHand(hand);
        boolean has_book = state.get(HAS_BOOK);
        boolean lit = state.get(LIT);

        if (!lit) {
            if (itemStack.isOf(Items.BOOK)) {
                if (!has_book) {
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    world.setBlockState(pos, state.with(HAS_BOOK, true), Block.NOTIFY_ALL);
                } else if (has_book) {
                    world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f); // Used as jingle for noe
                }

            } else if (itemStack.isOf(Items.CANDLE)) {
                if (has_book) {
                    if (state.get(CANDLES) < 3) {
                        if (!player.isCreative()) {
                            itemStack.decrement(1);
                        }
                        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        world.setBlockState(pos, state.with(CANDLES, Math.min(3, state.get(CANDLES) + 1)), Block.NOTIFY_ALL);
                    } else if (state.get(CANDLES) >= 3) {
                        world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f); // Used as jingle for noe
                    }
                } else if (!has_book) {
                    world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f); // Used as jingle for noe
                }

            } else if (itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.FIRE_CHARGE)) {
                if (!lit && (state.get(CANDLES) == 3)) {
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
                } else if (lit || (state.get(CANDLES) != 3)) {
                    world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f); // Used as jingle for noe
                }

            } else {
                world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f); // Used as jingle for noe
            }

        } else if ((itemStack.isOf(Items.SOUL_SAND) || (itemStack.isOf(Items.SOUL_SOIL))) && (state.get(FLAME_TYPE) != FlameType.SOUL_FLAME)) {
            if (lit) {
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                world.playSound(null, pos, SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f); // Used as jingle for noe
                world.setBlockState(pos, state.with(FLAME_TYPE, FlameType.SOUL_FLAME), Block.NOTIFY_ALL);

                if (state.get(FACING) == Direction.NORTH) {
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.6875, pos.getY() + 1.0625 + 0.0625, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.8125, pos.getY() + 1.1875 + 0.0625, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.875, pos.getY() + 1.25 + 0.0625, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.EAST) {
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.1875, pos.getY() + 1.0625 + 0.0625, pos.getZ() + 0.6875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.3125, pos.getY() + 1.1875 + 0.0625, pos.getZ() + 0.8125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.125, pos.getY() + 1.25 + 0.0625, pos.getZ() + 0.875, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.3125, pos.getY() + 1.0625 + 0.0625, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.1875, pos.getY() + 1.1875 + 0.0625, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.125, pos.getY() + 1.25 + 0.0625, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                }
                if (state.get(FACING) == Direction.WEST) {
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.8125, pos.getY() + 1.0625 + 0.0625, pos.getZ() + 0.3125, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.6875, pos.getY() + 1.1875 + 0.0625, pos.getZ() + 0.1875, 0.0f, 0.0f, 0.0f);
                    world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.875, pos.getY() + 1.25 + 0.0625, pos.getZ() + 0.125, 0.0f, 0.0f, 0.0f);
                }

            } else if (!lit) {
                world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 2.0f, 0.5f + world.random.nextFloat() * 1.2f); // Used as jingle for noe
            }

        } else if (lit && has_book && (state.get(CANDLES) == 3)) {
            if (!world.isClient) {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }

        }
        return ActionResult.SUCCESS;

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!state.get(HAS_BOOK)) {
            return ALCHEMY_TABLE_BASE;
        } else {
            switch (state.get(FACING)) {
                case EAST: {
                    switch (state.get(CANDLES)) {
                        case 1: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_1_E;
                        }
                        case 2: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_2_E;
                        }
                        case 3: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_3_E;
                        }
                        default: {
                            return ALCHEMY_TABLE_BOOK_E;
                        }
                    }

                }
                case SOUTH: {
                    switch (state.get(CANDLES)) {
                        case 1: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_1_S;
                        }
                        case 2: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_2_S;
                        }
                        case 3: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_3_S;
                        }
                        default: {
                            return ALCHEMY_TABLE_BOOK_S;
                        }
                    }

                }
                case WEST: {
                    switch (state.get(CANDLES)) {
                        case 1: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_1_W;
                        }
                        case 2: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_2_W;
                        }
                        case 3: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_3_W;
                        }
                        default: {
                            return ALCHEMY_TABLE_BOOK_W;
                        }
                    }

                }
                default: {
                    switch (state.get(CANDLES)) {
                        case 1: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_1_N;
                        }
                        case 2: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_2_N;
                        }
                        case 3: {
                            return ALCHEMY_TABLE_BOOK_CANDLE_3_N;
                        }
                        default: {
                            return ALCHEMY_TABLE_BOOK_N;
                        }
                    }

                }
            }
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()))
                .with(HAS_BOOK, false).with(CANDLES, 0).with(LIT, false).with(FLAME_TYPE, FlameType.NONE);
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
        builder.add(HAS_BOOK, FACING, CANDLES, LIT, FLAME_TYPE);
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
            if (blockEntity instanceof AlchemyTableEntity) {
                ItemScatterer.spawn(world, pos, (AlchemyTableEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AlchemyTableEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, RhodiumBlockEntityRegistry.ALCHEMY_TABLE, AlchemyTableEntity::tick);
    }

}