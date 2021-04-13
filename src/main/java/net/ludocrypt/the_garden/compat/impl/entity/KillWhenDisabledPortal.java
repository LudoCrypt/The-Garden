package net.ludocrypt.the_garden.compat.impl.entity;

import com.qouteall.immersive_portals.Helper;
import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.my_util.DQuaternion;
import com.qouteall.immersive_portals.portal.PortalExtension;
import com.qouteall.immersive_portals.portal.global_portals.VerticalConnectingPortal;

import net.ludocrypt.the_garden.config.GardenConfigurations;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class KillWhenDisabledPortal extends VerticalConnectingPortal {

	public static EntityType<KillWhenDisabledPortal> entityType;

	public KillWhenDisabledPortal(EntityType<?> entityType_1, World world_1) {
		super(entityType_1, world_1);
	}

	@Override
	public void tick() {
		super.tick();
		if (!GardenConfigurations.getInstance().immersivePortals.connectDimensions) {
			this.kill();
		}
	}

	public static KillWhenDisabledPortal createConnectingPortal(ServerWorld fromWorld, ConnectorType connectorType, ServerWorld toWorld, double scaling, boolean inverted, double rotationAlongYDegrees) {
		KillWhenDisabledPortal verticalConnectingPortal = new KillWhenDisabledPortal(entityType, fromWorld);

		verticalConnectingPortal.dimensionTo = toWorld.getRegistryKey();
		verticalConnectingPortal.width = 23333333333.0d;
		verticalConnectingPortal.height = 23333333333.0d;

		switch (connectorType) {
		case floor:
			verticalConnectingPortal.updatePosition(0, McHelper.getMinY(fromWorld), 0);
			verticalConnectingPortal.axisW = new Vec3d(0, 0, 1);
			verticalConnectingPortal.axisH = new Vec3d(1, 0, 0);
			break;
		case ceil:
			verticalConnectingPortal.updatePosition(0, McHelper.getMaxContentYExclusive(fromWorld), 0);
			verticalConnectingPortal.axisW = new Vec3d(1, 0, 0);
			verticalConnectingPortal.axisH = new Vec3d(0, 0, 1);
			break;
		}

		if (!inverted) {
			switch (connectorType) {
			case floor:
				verticalConnectingPortal.setDestination(new Vec3d(0, McHelper.getMaxContentYExclusive(toWorld), 0));
				break;
			case ceil:
				verticalConnectingPortal.setDestination(new Vec3d(0, McHelper.getMinY(toWorld), 0));
				break;
			}
		} else {
			switch (connectorType) {
			case floor:
				verticalConnectingPortal.setDestination(new Vec3d(0, McHelper.getMinY(toWorld), 0));
				break;
			case ceil:
				verticalConnectingPortal.setDestination(new Vec3d(0, McHelper.getMaxContentYExclusive(toWorld), 0));
				break;
			}
		}

		DQuaternion inversionRotation = inverted ? DQuaternion.rotationByDegrees(new Vec3d(1, 0, 0), 180) : null;
		DQuaternion additionalRotation = rotationAlongYDegrees != 0 ? DQuaternion.rotationByDegrees(new Vec3d(0, 1, 0), rotationAlongYDegrees) : null;
		DQuaternion rotation = Helper.combineNullable(inversionRotation, additionalRotation, DQuaternion::hamiltonProduct);
		verticalConnectingPortal.rotation = rotation != null ? rotation.toMcQuaternion() : null;

		if (scaling != 1.0) {
			verticalConnectingPortal.scaling = scaling;
			verticalConnectingPortal.teleportChangesScale = false;
			PortalExtension.get(verticalConnectingPortal).adjustPositionAfterTeleport = false;
		}

		return verticalConnectingPortal;
	}

}
