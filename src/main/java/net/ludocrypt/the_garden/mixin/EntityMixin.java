package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
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

	@Unique
	private void toPointOne() {
		Entity entity = ((Entity) (Object) this);
		if (!entity.world.isClient) {
			ServerWorld serverWorld = (ServerWorld) entity.world;
			MinecraftServer server = serverWorld.getServer();
			ServerWorld dimension = server.getWorld(PointOne.WORLD);
			FabricDimensions.teleport(entity, dimension, findSuitableTarget(entity, dimension));
		}
	}

	@Unique
	private void toPointTwo() {
		Entity entity = ((Entity) (Object) this);
		if (!entity.world.isClient) {
			ServerWorld serverWorld = (ServerWorld) entity.world;
			MinecraftServer server = serverWorld.getServer();
			ServerWorld dimension = server.getWorld(PointTwo.WORLD);
			FabricDimensions.teleport(entity, dimension, findSuitableTarget(entity, dimension));
		}
	}

	@Unique
	private TeleportTarget findSuitableTarget(Entity entity, ServerWorld world) {
		BlockPos pos = new BlockPos(entity.getBlockPos().getX(), 1, entity.getBlockPos().getZ());

		boolean suitable = false;

		while (!suitable && pos.getY() <= 255) {
			if (world.getBlockState(pos.north().down()).isSideSolidFullSquare(world, pos.north().down(), Direction.UP)) {
				pos = pos.north();
				suitable = true;
			} else if (world.getBlockState(pos.west().down().down()).isSideSolidFullSquare(world, pos.west().down(), Direction.UP)) {
				pos = pos.west();
				suitable = true;
			} else if (world.getBlockState(pos.north().east().down()).isSideSolidFullSquare(world, pos.north().east().down(), Direction.UP)) {
				pos = pos.north().east();
				suitable = true;
			} else if (world.getBlockState(pos.south().west().down()).isSideSolidFullSquare(world, pos.south().west().down(), Direction.UP)) {
				pos = pos.south().west();
				suitable = true;
			} else if (world.getBlockState(pos.south(2).down()).isSideSolidFullSquare(world, pos.south(2).down(), Direction.UP)) {
				pos = pos.south(2);
				suitable = true;
			} else if (world.getBlockState(pos.east(2).down()).isSideSolidFullSquare(world, pos.east(2).down(), Direction.UP)) {
				pos = pos.east(2);
				suitable = true;
			} else if (world.getBlockState(pos.south(2).east().down()).isSideSolidFullSquare(world, pos.south(2).east().down(), Direction.UP)) {
				pos = pos.south(2).east();
				suitable = true;
			} else if (world.getBlockState(pos.east(2).south().down()).isSideSolidFullSquare(world, pos.east(2).south().down(), Direction.UP)) {
				pos = pos.east(2).south();
				suitable = true;
			}

			pos = suitable ? pos : pos.up();
		}

		return new TeleportTarget(Vec3d.ofCenter(pos), Vec3d.ZERO, entity.yaw, entity.pitch);
	}
}
