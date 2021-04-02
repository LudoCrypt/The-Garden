package net.ludocrypt.the_garden.compat.impl.entity;

import com.qouteall.immersive_portals.portal.Portal;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.util.PortalUtil;
import net.ludocrypt.the_garden.world.PointOne;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class MulchPortalEntity extends Portal {
	public static EntityType<MulchPortalEntity> entityType;

	public MulchPortalEntity(EntityType<?> entityType, World world) {
		super(entityType, world);

		renderingMergable = true;
		hasCrossPortalCollision = false;
	}

	public static void generateToPointOne(World current, BlockPos pos, MinecraftServer server) {
		ServerWorld currentWorld = (ServerWorld) current;
		ServerWorld pointOne = server.getWorld(PointOne.WORLD);
		TeleportTarget target = PortalUtil.getTeleportTarget(pos, currentWorld, null, pointOne, true);

		generateMulchPortals(currentWorld, pointOne, target.position.add(-0.5D, 1.5D, -0.5D), Vec3d.ofCenter(pos).add(0.5D, 0.375D, 0.5D));
	}

	public static void generateFromPointOne(World current, BlockPos pos, BlockPos targetInfo, MinecraftServer server) {
		ServerWorld currentWorld = (ServerWorld) current;
		ServerWorld pointOne = server.getWorld(PointOne.WORLD);

		generateMulchPortals(pointOne, currentWorld, Vec3d.ofCenter(pos).add(-1.5D, 2.5D, 0.5D), Vec3d.ofCenter(targetInfo).add(0.5D, -0.625D, -0.5D));
	}

	public static void generateToOverworld(World current, BlockPos pos, MinecraftServer server) {
		ServerWorld overworld = server.getOverworld();
		ServerWorld pointOne = server.getWorld(PointOne.WORLD);
		TeleportTarget target = PortalUtil.getTeleportTarget(pos, pointOne, null, overworld, true);

		generateMulchPortals(pointOne, overworld, target.position.add(-0.5D, 1.5D, -0.5D), Vec3d.ofCenter(pos).add(0.5D, 0.375D, 0.5D));
	}

	public static void generateFromOverworld(World current, BlockPos pos, BlockPos targetInfo, MinecraftServer server) {
		ServerWorld overworld = server.getOverworld();
		ServerWorld pointOne = server.getWorld(PointOne.WORLD);

		generateMulchPortals(overworld, pointOne, Vec3d.ofCenter(pos).add(-1.5D, 2.5D, 0.5D), Vec3d.ofCenter(targetInfo).add(0.5D, -0.625D, -0.5D));
	}

	public static void generateMulchPortals(ServerWorld world, ServerWorld destinationWorld, Vec3d destination, Vec3d portalCenter) {
		Portal portal = new MulchPortalEntity(entityType, world);

		portal.updatePosition(portalCenter.x, portalCenter.y, portalCenter.z);

		portal.setDestination(destination);

		portal.dimensionTo = destinationWorld.getRegistryKey();

		portal.axisW = new Vec3d(0, 0, 1);
		portal.axisH = new Vec3d(1, 0, 0);

		portal.width = 2;
		portal.height = 2;

		world.spawnEntity(portal);
	}

	@Override
	public void tick() {
		super.tick();
		BlockPos pos = this.getBlockPos();
		if (!world.getBlockState(pos).isOf(GardenBlocks.MULCH_PORTAL)) {
			this.kill();
		}
	}

	@Override
	public void transformVelocity(Entity entity) {
	}

	@Override
	public boolean shouldLimitBoundingBox() {
		return false;
	}

}
