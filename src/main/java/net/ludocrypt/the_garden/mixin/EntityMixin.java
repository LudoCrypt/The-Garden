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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "tick", at = @At(value = "TAIL"))
	private void theGarden_tick(CallbackInfo ci) {
		Entity entity = ((Entity) (Object) this);
		if (entity.world.getRegistryKey() == PointOne.WORLD) {
			if (entity.getY() >= 256) {
				swapDimensions(PointTwo.WORLD, 1);
			}
		} else if (entity.world.getRegistryKey() == PointTwo.WORLD) {
			if (entity.getY() <= -1) {
				swapDimensions(PointOne.WORLD, 254);
			}
		}
	}

	@Unique
	private void swapDimensions(RegistryKey<World> world, int y) {
		Entity entity = ((Entity) (Object) this);
		if (!entity.world.isClient) {
			ServerWorld serverWorld = (ServerWorld) entity.world;
			MinecraftServer server = serverWorld.getServer();
			ServerWorld dimension = server.getWorld(world);
			FabricDimensions.teleport(entity, dimension, new TeleportTarget(new Vec3d(entity.getPos().x, y, entity.getPos().z), Vec3d.ZERO, entity.yaw, entity.pitch));
		}
	}
}
