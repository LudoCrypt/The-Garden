package net.ludocrypt.the_garden.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class EdgingBlock extends Block implements Waterloggable {

	public static final BooleanProperty NORTH = BooleanProperty.of("north");
	public static final BooleanProperty EAST = BooleanProperty.of("east");
	public static final BooleanProperty SOUTH = BooleanProperty.of("south");
	public static final BooleanProperty WEST = BooleanProperty.of("west");

	public EdgingBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(Properties.WATERLOGGED, false));
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		if (context.getStack().getItem() == this.asItem()) {
			Direction facing = context.getPlayer().isSneaking() ? context.getPlayerFacing().getOpposite() : context.getPlayerFacing();
			if (facing.equals(Direction.NORTH) ? state.get(NORTH) : facing.equals(Direction.EAST) ? state.get(EAST) : facing.equals(Direction.SOUTH) ? state.get(SOUTH) : facing.equals(Direction.WEST) ? state.get(WEST) : true) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape shape = VoxelShapes.empty();

		if (state.get(EAST)) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(12, 0, 0, 16, 5, 16));
		}
		if (state.get(NORTH)) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 0, 16, 5, 4));
		}
		if (state.get(SOUTH)) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 12, 16, 5, 16));
		}
		if (state.get(WEST)) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 0, 4, 5, 16));
		}

		return shape;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape shape = VoxelShapes.empty();

		boolean north = true;
		boolean east = true;
		boolean south = true;
		boolean west = true;

		if (world.getBlockState(pos.down()).getBlock() instanceof EdgingFaceBlock) {
			BlockState block = world.getBlockState(pos.down());

			if (block.get(EAST)) {
				east = false;
			}
			if (block.get(NORTH)) {
				north = false;
			}
			if (block.get(SOUTH)) {
				south = false;
			}
			if (block.get(WEST)) {
				west = false;
			}

		}

		if (state.get(EAST) && east) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(12, 0, 0, 16, 5, 16));
		}
		if (state.get(NORTH) && north) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 0, 16, 5, 4));
		}
		if (state.get(SOUTH) && south) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 12, 16, 5, 16));
		}
		if (state.get(WEST) && west) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 0, 4, 5, 16));
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
		switch (ctx.getPlayer().isSneaking() ? ctx.getPlayerFacing().getOpposite() : ctx.getPlayerFacing()) {
		case EAST:
			state = state.with(EAST, true);
			break;
		case NORTH:
			state = state.with(NORTH, true);
			break;
		case SOUTH:
			state = state.with(SOUTH, true);
			break;
		case WEST:
			state = state.with(WEST, true);
			break;
		default:
			state = state.with(NORTH, true);
			break;
		}
		if (ctx.getWorld().isWater(ctx.getBlockPos())) {
			state = state.with(Properties.WATERLOGGED, true);
		}
		return state;
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

}
