package net.ludocrypt.the_garden.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class EdgingFaceBlock extends EdgingBlock {

	public EdgingFaceBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape shape = VoxelShapes.empty();

		if (state.get(EAST)) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(12, 0, 0, 16, 16, 16));
		}
		if (state.get(NORTH)) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 0, 16, 16, 4));
		}
		if (state.get(SOUTH)) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 12, 16, 16, 16));
		}
		if (state.get(WEST)) {
			shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 0, 4, 16, 16));
		}

		return shape;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return getOutlineShape(state, world, pos, context);
	}

}
