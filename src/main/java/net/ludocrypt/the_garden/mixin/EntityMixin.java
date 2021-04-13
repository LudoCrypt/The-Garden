package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.ludocrypt.the_garden.compat.GardenCompat;
import net.ludocrypt.the_garden.config.GardenConfigurations;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

@SuppressWarnings("deprecation")
@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "tick", at = @At(value = "TAIL"))
	private void theGarden_tick(CallbackInfo ci) {
		Entity entity = ((Entity) (Object) this);
		if ((GardenCompat.isImmersivePortalsModInstalled && !GardenConfigurations.getInstance().immersivePortals.connectDimensions) || !GardenCompat.isImmersivePortalsModInstalled) {
			if (entity.world.getRegistryKey() == PointOne.WORLD) {
				if (entity.getY() >= 256) {
					toPointTwo();
				}
			} else if (entity.world.getRegistryKey() == PointTwo.WORLD) {
				if (entity.getY() <= -1) {
					toPointOne();
				}
			}
		}
	}

	@Unique
	private void toPointOne() {
		Entity entity = ((Entity) (Object) this);
		if (!entity.world.isClient) {
			ServerWorld serverWorld = (ServerWorld) entity.world;
			MinecraftServer server = serverWorld.getServer();
			ServerWorld dimension = server.getWorld(PointOne.WORLD);
			FabricDimensions.teleport(entity, dimension, findSuitableTarget(entity, dimension, true));
		}
	}

	@Unique
	private void toPointTwo() {
		Entity entity = ((Entity) (Object) this);
		if (!entity.world.isClient) {
			ServerWorld serverWorld = (ServerWorld) entity.world;
			MinecraftServer server = serverWorld.getServer();
			ServerWorld dimension = server.getWorld(PointTwo.WORLD);
			FabricDimensions.teleport(entity, dimension, findSuitableTarget(entity, dimension, false));
		}
	}

	@Unique
	private TeleportTarget findSuitableTarget(Entity entity, ServerWorld world, boolean pointOne) {
		BlockPos old = new BlockPos(entity.getBlockPos().getX(), pointOne ? 255 : 2, entity.getBlockPos().getZ());
		BlockPos pos = old.add(-1, pointOne ? 0 : -1, -1);

		boolean suitable = false;

		int l = 3;
		Direction dir = Direction.EAST;

		spiral: for (int s = 0; s < 5; s++) {
			for (int i = 0; i < 4; i++) {
				for (int w = 0; w < l - 1; w++) {
					if ((world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP) || world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.DOWN)) && !world.getBlockState(pos).isFullCube(world, pos)) {
						suitable = true;
						break spiral;
					}
					pos = pos.offset(dir);
				}
				dir = dir.rotateYClockwise();
			}
			pos = pos.add(-1, 0, -1);
			dir = dir.rotateYClockwise();
			l += 2;
		}

		if (!suitable) {
			BlockPos.iterate(old.add(-1, pointOne ? -1 : -2, -1), old.add(1, pointOne ? -1 : -2, 1)).forEach((pPos) -> {
				if (world.isAir(pPos)) {
					world.setBlockState(pPos, pointOne ? GardenBlocks.MULCH_BLOCK.getDefaultState() : GardenBlocks.PLAYDIRT.getDefaultState(), 2);
				}
				if (pointOne && world.getBlockState(pPos.up()).isOf(GardenBlocks.TILE)) {
					world.setBlockState(pPos.up(), Blocks.AIR.getDefaultState(), 2);
				}
			});
			return new TeleportTarget(Vec3d.ofCenter(old), Vec3d.ZERO, entity.yaw, entity.pitch);
		}

		return new TeleportTarget(Vec3d.ofCenter(pos), Vec3d.ZERO, entity.yaw, entity.pitch);
	}
}
