package net.ludocrypt.the_garden.compat.impl;

import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.ModMain;
import com.qouteall.immersive_portals.api.PortalAPI;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.global_portals.VerticalConnectingPortal;
import com.qouteall.immersive_portals.render.PortalEntityRenderer;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.compat.impl.entity.KillWhenDisabledPortal;
import net.ludocrypt.the_garden.compat.impl.entity.MulchPortalEntity;
import net.ludocrypt.the_garden.config.GardenConfig;
import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class GardenImmersivePortalsCompat {

	public static boolean isInstalled = FabricLoader.getInstance().isModLoaded("immersive_portals");

	public static void init() {
		if (!GardenConfig.getInstance().enableImmersivePortals) {
			isInstalled = false;
		}
		if (isInstalled) {
			serverInit();
		}
	}

	private static void serverInit() {
		MulchPortalEntity.entityType = FabricEntityTypeBuilder.create(SpawnGroup.MISC, MulchPortalEntity::new).dimensions(new EntityDimensions(1, 1, true)).fireImmune().trackRangeBlocks(96).trackedUpdateRate(20).forceTrackedVelocityUpdates(true).build();
		Registry.register(Registry.ENTITY_TYPE, TheGarden.id("mulch_portal"), MulchPortalEntity.entityType);
		KillWhenDisabledPortal.entityType = FabricEntityTypeBuilder.create(SpawnGroup.MISC, KillWhenDisabledPortal::new).dimensions(new EntityDimensions(1, 1, true)).fireImmune().trackRangeBlocks(96).trackedUpdateRate(20).forceTrackedVelocityUpdates(true).build();
		Registry.register(Registry.ENTITY_TYPE, TheGarden.id("kill_when_disabled_portal"), KillWhenDisabledPortal.entityType);
		if (GardenConfig.getInstance().enableImmersivePortals) {
			ModMain.postServerTickSignal.connect(GardenImmersivePortalsCompat::serverTick);
		}
	}

	private static void serverTick() {
		if (GardenConfig.getInstance().enableImmersivePortals) {
			if (McHelper.getGlobalPortals(McHelper.getServerWorld(PointOne.WORLD)).isEmpty() && McHelper.getGlobalPortals(McHelper.getServerWorld(PointTwo.WORLD)).isEmpty()) {
				if (GardenConfig.getInstance().enabledBiomes.hasPointTwo) {
					createConnectionBetween(PointOne.WORLD, PointTwo.WORLD);
				}
			}
		}
	}

	private static void createConnectionBetween(RegistryKey<World> from, RegistryKey<World> to) {
		ServerWorld fromWorld = McHelper.getServerWorld(from);
		ServerWorld toWorld = McHelper.getServerWorld(to);

		KillWhenDisabledPortal connectingPortal = KillWhenDisabledPortal.createConnectingPortal(fromWorld, VerticalConnectingPortal.ConnectorType.ceil, toWorld, 1, false, 0);
		KillWhenDisabledPortal reverse = PortalAPI.createReversePortal(connectingPortal);

		initializeFuseViewProperty(connectingPortal);
		initializeFuseViewProperty(reverse);

		PortalAPI.addGlobalPortal(fromWorld, connectingPortal);
		PortalAPI.addGlobalPortal(toWorld, reverse);
	}

	private static void initializeFuseViewProperty(Portal portal) {
		if (portal.world.getDimension().hasSkyLight()) {
			if (portal.getNormal().y < 0) {
				portal.fuseView = true;
			}
		}
	}

	public static void clientInit() {
		EntityRendererRegistry.INSTANCE.register(MulchPortalEntity.entityType, (entityRenderDispatcher, context) -> new PortalEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(KillWhenDisabledPortal.entityType, (entityRenderDispatcher, context) -> new PortalEntityRenderer(entityRenderDispatcher));

	}

}
