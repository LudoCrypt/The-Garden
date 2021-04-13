package net.ludocrypt.the_garden.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.ludocrypt.the_garden.blocks.entity.MulchPortalBlockEntity;
import net.ludocrypt.the_garden.compat.GardenCompat;
import net.ludocrypt.the_garden.config.GardenConfigurations;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.ludocrypt.the_garden.util.PortalUtil;
import net.ludocrypt.the_garden.world.PointOne;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class MulchPortalBlock extends Block implements BlockEntityProvider {

	public MulchPortalBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (GardenCompat.isImmersivePortalsModInstalled && GardenConfigurations.getInstance().immersivePortals.mulchPortal) {
			return VoxelShapes.empty();
		}
		return Block.createCuboidShape(0, 0, 0, 16, 14, 16);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if ((GardenCompat.isImmersivePortalsModInstalled && !GardenConfigurations.getInstance().immersivePortals.mulchPortal) || !GardenCompat.isImmersivePortalsModInstalled) {
			if (!world.isClient) {
				ServerWorld serverWorld = (ServerWorld) world;
				teleport(serverWorld, entity, pos, PointOne.WORLD);
			}
		}
	}

	private void teleport(ServerWorld world, Entity entity, BlockPos pos, RegistryKey<World> substitute) {
		MinecraftServer server = world.getServer();
		ServerWorld dimension = world.getRegistryKey() == substitute ? server.getWorld(World.OVERWORLD) : server.getWorld(substitute);
		FabricDimensions.teleport(entity, dimension, PortalUtil.getTeleportTarget(pos, world, entity, dimension, true));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {

		List<Direction> dirs = ImmutableList.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

		BlockState returnState = state;

		for (Direction dir : dirs) {
			if (returnState == state) {
				if (!(world.getBlockState(pos.offset(dir)).isOf(this) || world.getBlockState(pos.offset(dir)).isOf(GardenBlocks.MULCH_BLOCK))) {
					returnState = Blocks.AIR.getDefaultState();
					break;
				}
			}
		}

		return returnState;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (random.nextInt(3) == 0) {
			double d = pos.getX() + random.nextDouble();
			double e = pos.getY() + 0.8D;
			double f = pos.getZ() + random.nextDouble();
			world.addParticle(GardenParticles.MULCH_PARTICLES.get(random.nextInt(GardenParticles.MULCH_PARTICLES.size())), d, e, f, 0.0D, 0.3D, 0.0D);
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new MulchPortalBlockEntity();
	}

}
