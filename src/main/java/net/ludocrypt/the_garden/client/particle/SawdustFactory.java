package net.ludocrypt.the_garden.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.mixin.WaterSuspendParticleAccessor;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class SawdustFactory implements ParticleFactory<DefaultParticleType> {
	private final SpriteProvider spriteProvider;

	public SawdustFactory(SpriteProvider spriteProvider) {
		this.spriteProvider = spriteProvider;
	}

	public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double g, double h, double i) {
		WaterSuspendParticle sawdust = WaterSuspendParticleAccessor.createWaterSuspendParticle(world, x, y, z, g, -3.0D, i);
		sawdust.setSprite(this.spriteProvider);
		sawdust.setColor(0.921568627F, 0.717647059F, 0.470588235F);
		return sawdust;
	}
}
