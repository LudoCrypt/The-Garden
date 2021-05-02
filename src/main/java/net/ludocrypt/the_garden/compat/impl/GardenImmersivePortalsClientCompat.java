package net.ludocrypt.the_garden.compat.impl;

import com.qouteall.immersive_portals.render.PortalEntityRenderer;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.ludocrypt.the_garden.compat.impl.entity.KillWhenDisabledPortal;
import net.ludocrypt.the_garden.compat.impl.entity.MulchPortalEntity;

public class GardenImmersivePortalsClientCompat {

	public static void clientInit() {
		EntityRendererRegistry.INSTANCE.register(MulchPortalEntity.entityType, (entityRenderDispatcher, context) -> new PortalEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(KillWhenDisabledPortal.entityType, (entityRenderDispatcher, context) -> new PortalEntityRenderer(entityRenderDispatcher));
	}

}
