package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.world.ClientWorld;

@Mixin(WaterSuspendParticle.class)
public interface WaterSuspendParticleAccessor {

	@Invoker("<init>")
	public static WaterSuspendParticle createWaterSuspendParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		throw new UnsupportedOperationException();
	}
}
