package net.ludocrypt.the_garden.blocks;

import java.util.Random;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.server.network.ServerPlayerEntity;
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
		Random random = world.getRandom();
		if (world.isAir(pos.up())) {
			if ((entity.getVelocity().getX() >= 0.2 || entity.getVelocity().getZ() >= 0.2) && random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {
				world.addParticle(GardenParticles.MULCH_PARTICLES.get(random.nextInt(GardenParticles.MULCH_PARTICLES.size())), entity.getX(), pos.getY() + (random.nextDouble() / world.getBlockState(pos).get(LAYERS)) + 0.5, entity.getZ(), -entity.getVelocity().getX(), random.nextDouble() * 3, -entity.getVelocity().getZ());
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

		if (player.getStackInHand(hand).getItem() instanceof ShovelItem && world.isAir(pos.up())) {
			if (state.get(LAYERS) > 1) {
				world.setBlockState(pos, state.with(LAYERS, state.get(LAYERS) - 1), 2);
			} else {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
			}
			if (!world.isClient) {
				player.getStackInHand(hand).damage(1, world.getRandom(), (ServerPlayerEntity) player);
			}
			return ActionResult.SUCCESS;
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}

}
