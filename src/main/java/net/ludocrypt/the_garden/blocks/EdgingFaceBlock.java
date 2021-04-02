package net.ludocrypt.the_garden.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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

		for (Direction dir : Direction.values()) {
			if (!dir.equals(Direction.UP) && !dir.equals(Direction.DOWN)) {
				if (state.get(DIRECTION_PROPERTIES.getAFromB(dir))) {
					shape = VoxelShapes.union(shape, DIRECTION_PROPERTIES.getCFromB(dir).second);
				}
			}
		}

		return shape;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return getOutlineShape(state, world, pos, context);
	}

}
