package net.ludocrypt.the_garden.util;

import java.util.Iterator;

import net.ludocrypt.the_garden.compat.impl.GardenImmersivePortalsCompat;
import net.ludocrypt.the_garden.compat.impl.entity.MulchPortalEntity;
import net.ludocrypt.the_garden.config.GardenConfig;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.world.PointOne;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;

public class PortalUtil {

	public static TeleportTarget getTeleportTarget(BlockPos portal, World world, Entity teleported, ServerWorld destination, boolean bl) {

		BlockPos pos = portal;

		if (world.getBlockState(pos.north()).isOf(GardenBlocks.MULCH_PORTAL) && world.getBlockState(pos.west()).isOf(GardenBlocks.MULCH_PORTAL)) {
			pos = pos.west();
		} else if (world.getBlockState(pos.south()).isOf(GardenBlocks.MULCH_PORTAL) && world.getBlockState(pos.west()).isOf(GardenBlocks.MULCH_PORTAL)) {
			pos = pos.west().south();
		} else if (world.getBlockState(pos.south()).isOf(GardenBlocks.MULCH_PORTAL) && world.getBlockState(pos.east()).isOf(GardenBlocks.MULCH_PORTAL)) {
			pos = pos.south().east();
		}

		WorldBorder worldBorder = destination.getWorldBorder();
		double d = Math.max(-2.9999872E7D, worldBorder.getBoundWest() + 16.0D);
		double e = Math.max(-2.9999872E7D, worldBorder.getBoundNorth() + 16.0D);
		double f = Math.min(2.9999872E7D, worldBorder.getBoundEast() - 16.0D);
		double g = Math.min(2.9999872E7D, worldBorder.getBoundSouth() - 16.0D);
		double h = DimensionType.method_31109(world.getDimension(), destination.getDimension());
		if (bl) {
			pos = new BlockPos(MathHelper.clamp(pos.getX() * h, d, f), 0, MathHelper.clamp(pos.getZ() * h, e, g));
		}

		boolean found = false;

		while (!destination.getBlockState(pos).isOf(GardenBlocks.MULCH_PORTAL) && pos.getY() <= 255) {
			pos = pos.up();
		}

		if (destination.getBlockState(pos).isOf(GardenBlocks.MULCH_PORTAL)) {
			pos = pos.up();
			found = true;
		}

		if (destination.getBlockState(pos.down()).isOf(GardenBlocks.MULCH_PORTAL) && destination.getBlockState(pos.down().north()).isOf(GardenBlocks.MULCH_PORTAL)) {
			pos = pos.north();
		}

		boolean suitable = false;

		while (!suitable && pos.getY() <= 255) {
			boolean success = false;
			if (!destination.getBlockState(pos.north()).isFullCube(destination, pos.north())) {
				pos = pos.north();
				success = true;
			} else if (!destination.getBlockState(pos.west()).isFullCube(destination, pos.west())) {
				pos = pos.west();
				success = true;
			} else if (!destination.getBlockState(pos.north().east()).isFullCube(destination, pos.north().east())) {
				pos = pos.north().east();
				success = true;
			} else if (!destination.getBlockState(pos.south().west()).isFullCube(destination, pos.south().west())) {
				pos = pos.south().west();
				success = true;
			} else if (!destination.getBlockState(pos.south(2)).isFullCube(destination, pos.south(2))) {
				pos = pos.south(2);
				success = true;
			} else if (!destination.getBlockState(pos.east(2)).isFullCube(destination, pos.east(2))) {
				pos = pos.east(2);
				success = true;
			} else if (!destination.getBlockState(pos.south(2).east()).isFullCube(destination, pos.south(2).east())) {
				pos = pos.south(2).east();
				success = true;
			} else if (!destination.getBlockState(pos.east(2).south()).isFullCube(destination, pos.east(2).south())) {
				pos = pos.east(2).south();
				success = true;
			}

			suitable = success;
			pos = suitable ? pos : pos.up();
		}

		if (!found) {
			if (bl) {
				boolean goAgain = false;
				BlockPos goAgainPos = new BlockPos(pos.getX(), 0, pos.getZ());

				double s = MathHelper.ceil(3 * h);
				Iterator<BlockPos> iterator = BlockPos.iterate(goAgainPos.add(-s, 0, -s), goAgainPos.add(s, 0, s)).iterator();

				searchForLoop: while (iterator.hasNext()) {
					BlockPos blockPos = iterator.next();
					while (!destination.getBlockState(blockPos).isOf(GardenBlocks.MULCH_PORTAL) && blockPos.getY() <= 255) {
						blockPos = blockPos.up();
					}
					if (destination.getBlockState(blockPos).isOf(GardenBlocks.MULCH_PORTAL)) {
						goAgain = true;
						goAgainPos = blockPos;
						break searchForLoop;
					}
				}

				if (goAgain) {
					return getTeleportTarget(goAgainPos, world, teleported, destination, false);
				}
			}

			pos = new BlockPos(pos.getX(), 255, pos.getZ());

			while (destination.isAir(pos.down()) && pos.getY() >= 0) {
				pos = pos.down();
			}

			BlockPos.iterate(pos.add(-1, -1, 1), pos.add(2, -1, -2)).forEach((blockPos) -> {
				destination.setBlockState(blockPos, GardenBlocks.MULCH_BLOCK.getDefaultState(), 2);
			});
			BlockPos.iterate(pos.add(0, -1, 0), pos.add(1, -1, -1)).forEach((blockPos) -> {
				destination.setBlockState(blockPos, GardenBlocks.MULCH_PORTAL.getDefaultState(), 2);
			});

			if (GardenImmersivePortalsCompat.isModInstalled && GardenConfig.getInstance().immersivePortals.mulchPortal) {
				if (!world.isClient) {
					if (destination.getRegistryKey().equals(PointOne.WORLD)) {
						MulchPortalEntity.generateFromPointOne(world, portal, pos, destination.getServer());
					}
				}
			}

			pos = pos.up().west();
		}

		return new TeleportTarget(Vec3d.ofCenter(pos), Vec3d.ZERO, teleported != null ? teleported.yaw : 0, teleported != null ? teleported.pitch : 0);
	}

}
