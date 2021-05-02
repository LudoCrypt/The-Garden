package net.ludocrypt.the_garden.compat.impl;

import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.ModMain;
import com.qouteall.immersive_portals.api.PortalAPI;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.global_portals.VerticalConnectingPortal;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.compat.GardenCompat;
import net.ludocrypt.the_garden.compat.impl.entity.KillWhenDisabledPortal;
import net.ludocrypt.the_garden.compat.impl.entity.MulchPortalEntity;
import net.ludocrypt.the_garden.config.GardenConfigurations;
import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class GardenImmersivePortalsCompat {

	public static void init() {
		if (GardenCompat.isImmersivePortalsModInstalled) {
			serverInit();
		}
	}

	private static void serverInit() {
		MulchPortalEntity.entityType = FabricEntityTypeBuilder.create(SpawnGroup.MISC, MulchPortalEntity::new).dimensions(new EntityDimensions(1, 1, true)).fireImmune().trackRangeBlocks(96).trackedUpdateRate(20).forceTrackedVelocityUpdates(true).build();
		Registry.register(Registry.ENTITY_TYPE, TheGarden.id("mulch_portal"), MulchPortalEntity.entityType);
		KillWhenDisabledPortal.entityType = FabricEntityTypeBuilder.create(SpawnGroup.MISC, KillWhenDisabledPortal::new).dimensions(new EntityDimensions(1, 1, true)).fireImmune().trackRangeBlocks(96).trackedUpdateRate(20).forceTrackedVelocityUpdates(true).build();
		Registry.register(Registry.ENTITY_TYPE, TheGarden.id("kill_when_disabled_portal"), KillWhenDisabledPortal.entityType);
		if (GardenConfigurations.getInstance().immersivePortals.connectDimensions) {
			ModMain.postServerTickSignal.connect(GardenImmersivePortalsCompat::serverTick);
		}
	}

	private static void serverTick() {
		if (GardenConfigurations.getInstance().immersivePortals.connectDimensions) {
			if (McHelper.getGlobalPortals(McHelper.getServerWorld(PointOne.WORLD)).isEmpty() && McHelper.getGlobalPortals(McHelper.getServerWorld(PointTwo.WORLD)).isEmpty()) {
				createConnectionBetween(PointOne.WORLD, PointTwo.WORLD);
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

}
