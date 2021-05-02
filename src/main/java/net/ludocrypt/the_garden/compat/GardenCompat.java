package net.ludocrypt.the_garden.compat;

import net.fabricmc.loader.api.FabricLoader;
import net.ludocrypt.the_garden.compat.impl.GardenImmersivePortalsCompat;
import net.ludocrypt.the_garden.compat.impl.entity.MulchPortalEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GardenCompat {

	public static boolean isImmersivePortalsModInstalled = FabricLoader.getInstance().isModLoaded("immersive_portals");

	public static void init() {
		if (isImmersivePortalsModInstalled) {
			GardenImmersivePortalsCompat.init();
		}
	}

	public static void attemptGenerateToPointOne(World current, BlockPos pos, MinecraftServer server) {
		if (isImmersivePortalsModInstalled) {
			MulchPortalEntity.generateToPointOne(current, pos, server);
		}
	}

	public static void attemptGenerateFromPointOne(World current, BlockPos pos, BlockPos targetInfo, MinecraftServer server) {
		if (isImmersivePortalsModInstalled) {
			MulchPortalEntity.generateFromPointOne(current, pos, targetInfo, server);
		}
	}

	public static void attemptGenerateToOverworld(World current, BlockPos pos, MinecraftServer server) {
		if (isImmersivePortalsModInstalled) {
			MulchPortalEntity.generateToOverworld(current, pos, server);
		}
	}

	public static void attemptGenerateFromOverworld(World current, BlockPos pos, BlockPos targetInfo, MinecraftServer server) {
		if (isImmersivePortalsModInstalled) {
			MulchPortalEntity.generateFromOverworld(current, pos, targetInfo, server);
		}
	}

	public static void attemptGenerateMulchPortals(ServerWorld world, ServerWorld destinationWorld, Vec3d destination, Vec3d portalCenter) {
		if (isImmersivePortalsModInstalled) {
			MulchPortalEntity.generateMulchPortals(world, destinationWorld, destination, portalCenter);
		}
	}

}
