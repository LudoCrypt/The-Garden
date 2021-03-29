package net.ludocrypt.the_garden.blocks;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MulchLayerBlock extends SnowBlock {

	public MulchLayerBlock(Settings settings) {
		super(settings);

	}

	public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
		entity.handleFallDamage(distance, (float) (world.getBlockState(pos).get(LAYERS) * 0.1));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return LAYERS_TO_SHAPE[(Integer) state.get(LAYERS)];
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if (super.getPlacementState(ctx).get(LAYERS).equals(8)) {
			return GardenBlocks.MULCH_BLOCK.getDefaultState();
		} else {
			return super.getPlacementState(ctx);
		}
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		return context.getStack().getItem().equals(GardenBlocks.MULCH_LAYER_BLOCK.asItem()) || context.getStack().getItem().equals(GardenBlocks.MULCH_BLOCK.asItem());
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, Entity entity) {
		MulchBlock.spawnSteppingParticles(world, pos, entity);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		return MulchBlock.use(state, world, pos, player, hand, hit);
	}

}
