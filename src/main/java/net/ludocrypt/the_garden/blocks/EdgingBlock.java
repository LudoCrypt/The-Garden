package net.ludocrypt.the_garden.blocks;

import net.ludocrypt.the_garden.util.TripplePair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EdgingBlock extends Block implements Waterloggable {

	public static final BooleanProperty NORTH = BooleanProperty.of("north");
	public static final BooleanProperty EAST = BooleanProperty.of("east");
	public static final BooleanProperty SOUTH = BooleanProperty.of("south");
	public static final BooleanProperty WEST = BooleanProperty.of("west");
	public static final VoxelShape NORTH_SMALL_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 5, 4);
	public static final VoxelShape EAST_SMALL_SHAPE = Block.createCuboidShape(12, 0, 0, 16, 5, 16);
	public static final VoxelShape SOUTH_SMALL_SHAPE = Block.createCuboidShape(0, 0, 12, 16, 5, 16);
	public static final VoxelShape WEST_SMALL_SHAPE = Block.createCuboidShape(0, 0, 0, 4, 5, 16);

	public static final VoxelShape NORTH_BIG_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 4);
	public static final VoxelShape EAST_BIG_SHAPE = Block.createCuboidShape(12, 0, 0, 16, 16, 16);
	public static final VoxelShape SOUTH_BIG_SHAPE = Block.createCuboidShape(0, 0, 12, 16, 16, 16);
	public static final VoxelShape WEST_BIG_SHAPE = Block.createCuboidShape(0, 0, 0, 4, 16, 16);
	public static final TripplePair<BooleanProperty, Direction, Pair<VoxelShape, VoxelShape>> DIRECTION_PROPERTIES = TripplePair.newTripplePair();

	public EdgingBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(Properties.WATERLOGGED, false));
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		if (context.getStack().getItem() == this.asItem()) {
			if (state.get(DIRECTION_PROPERTIES.getAFromB(context.getPlayer().isSneaking() ? context.getPlayerFacing().getOpposite() : context.getPlayerFacing()))) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape shape = VoxelShapes.empty();

		for (Direction dir : Direction.values()) {
			if (!dir.equals(Direction.UP) && !dir.equals(Direction.DOWN)) {
				if (state.get(DIRECTION_PROPERTIES.getAFromB(dir))) {
					shape = VoxelShapes.union(shape, DIRECTION_PROPERTIES.getCFromB(dir).getLeft());
				}
			}
		}

		return shape;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape shape = VoxelShapes.empty();

		BlockState below = world.getBlockState(pos.down());

		for (Direction dir : Direction.values()) {
			if (!dir.equals(Direction.UP) && !dir.equals(Direction.DOWN)) {
				if (state.get(DIRECTION_PROPERTIES.getAFromB(dir)) && !(below.getBlock() instanceof EdgingFaceBlock && below.get(DIRECTION_PROPERTIES.getAFromB(dir)))) {
					shape = VoxelShapes.union(shape, DIRECTION_PROPERTIES.getCFromB(dir).getLeft());
				}
			}
		}

		return shape;
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, Properties.WATERLOGGED);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = getDefaultState();
		if (ctx.getWorld().getBlockState(ctx.getBlockPos()).isOf(this)) {
			state = ctx.getWorld().getBlockState(ctx.getBlockPos());
		}

		state = state.with(DIRECTION_PROPERTIES.getAFromB(ctx.getPlayer().isSneaking() ? ctx.getPlayerFacing().getOpposite() : ctx.getPlayerFacing()), true);

		if (ctx.getWorld().isWater(ctx.getBlockPos())) {
			state = state.with(Properties.WATERLOGGED, true);
		}
		return state;
	}

	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
		player.incrementStat(Stats.MINED.getOrCreateStat(this));
		player.addExhaustion(0.005F);
		DIRECTION_PROPERTIES.getA_LIST().forEach((property) -> {
			if (state.get(property)) {
				dropStacks(state, world, pos, blockEntity, player, stack);
			}
		});
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	static {
		DIRECTION_PROPERTIES.put(NORTH, Direction.NORTH, new Pair<VoxelShape, VoxelShape>(NORTH_SMALL_SHAPE, NORTH_BIG_SHAPE));
		DIRECTION_PROPERTIES.put(EAST, Direction.EAST, new Pair<VoxelShape, VoxelShape>(EAST_SMALL_SHAPE, EAST_BIG_SHAPE));
		DIRECTION_PROPERTIES.put(SOUTH, Direction.SOUTH, new Pair<VoxelShape, VoxelShape>(SOUTH_SMALL_SHAPE, SOUTH_BIG_SHAPE));
		DIRECTION_PROPERTIES.put(WEST, Direction.WEST, new Pair<VoxelShape, VoxelShape>(WEST_SMALL_SHAPE, WEST_BIG_SHAPE));
	}

}
