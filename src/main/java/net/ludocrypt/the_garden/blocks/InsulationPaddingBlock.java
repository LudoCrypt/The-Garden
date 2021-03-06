package net.ludocrypt.the_garden.blocks;

import net.ludocrypt.the_garden.util.TripplePair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class InsulationPaddingBlock extends InsulationBlock implements Waterloggable {

	public static final BooleanProperty NORTH = Properties.NORTH;
	public static final BooleanProperty EAST = Properties.EAST;
	public static final BooleanProperty SOUTH = Properties.SOUTH;
	public static final BooleanProperty WEST = Properties.WEST;
	public static final BooleanProperty UP = Properties.UP;
	public static final BooleanProperty DOWN = Properties.DOWN;
	public static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 2);
	public static final VoxelShape EAST_SHAPE = Block.createCuboidShape(14, 0, 0, 16, 16, 16);
	public static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0, 0, 14, 16, 16, 16);
	public static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0, 0, 0, 2, 16, 16);
	public static final VoxelShape UP_SHAPE = Block.createCuboidShape(0, 14, 0, 16, 16, 16);
	public static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 2, 16);
	public static final TripplePair<BooleanProperty, Direction, VoxelShape> DIRECTION_PROPERTIES = TripplePair.newTripplePair();

	public InsulationPaddingBlock(Settings settings, int range, ParticleEffect effect) {
		super(settings, range, effect);
		setDefaultState(getStateManager().getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false).with(Properties.WATERLOGGED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, Properties.WATERLOGGED);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {

		World world = ctx.getWorld();
		BlockPos pos = ctx.getBlockPos();
		Direction side = ctx.getSide().getOpposite();
		BlockState state = world.getBlockState(pos);
		PlayerEntity player = ctx.getPlayer();

		if (!state.isOf(this)) {
			state = this.getDefaultState();
		}

		if (player.isSneaking()) {
			if (state.get(DIRECTION_PROPERTIES.getAFromB(ctx.getPlayerLookDirection()))) {
				state = state.with(DIRECTION_PROPERTIES.getAFromB(ctx.getPlayerLookDirection().getOpposite()), true);
			} else {
				state = state.with(DIRECTION_PROPERTIES.getAFromB(ctx.getPlayerLookDirection()), true);
			}
		} else {
			state = state.with(DIRECTION_PROPERTIES.getAFromB(side), true);
		}

		if (ctx.getWorld().isWater(ctx.getBlockPos())) {
			state = state.with(Properties.WATERLOGGED, true);
		}

		return state;
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext ctx) {
		ItemStack stack = ctx.getStack();
		boolean canReplace = false;

		if (stack.getItem().equals(Item.fromBlock(this))) {
			canReplace = true;
			if (state.isOf(this)) {
				if (state.get(DIRECTION_PROPERTIES.getAFromB(ctx.getPlayerLookDirection().getOpposite())) || (state.get(DIRECTION_PROPERTIES.getAFromB(ctx.getSide().getOpposite())) && state.get(DIRECTION_PROPERTIES.getAFromB(ctx.getSide())))) {
					canReplace = false;
				}
			}
		}

		return canReplace;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape shape = VoxelShapes.empty();
		for (Direction dir : Direction.values()) {
			if (state.get(DIRECTION_PROPERTIES.getAFromB(dir))) {
				shape = VoxelShapes.union(shape, DIRECTION_PROPERTIES.getCFromB(dir));
			}
		}
		return shape;
	}

	@Override
	public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
		entity.handleFallDamage(distance, 0.9F);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
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

	static {
		DIRECTION_PROPERTIES.put(NORTH, Direction.NORTH, NORTH_SHAPE);
		DIRECTION_PROPERTIES.put(EAST, Direction.EAST, EAST_SHAPE);
		DIRECTION_PROPERTIES.put(SOUTH, Direction.SOUTH, SOUTH_SHAPE);
		DIRECTION_PROPERTIES.put(WEST, Direction.WEST, WEST_SHAPE);
		DIRECTION_PROPERTIES.put(UP, Direction.UP, UP_SHAPE);
		DIRECTION_PROPERTIES.put(DOWN, Direction.DOWN, DOWN_SHAPE);
	}

}
