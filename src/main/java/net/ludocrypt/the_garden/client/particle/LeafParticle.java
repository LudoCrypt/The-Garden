package net.ludocrypt.the_garden.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class LeafParticle extends TwigParticle {

	public LeafParticle(ClientWorld world, double x, double y, double z, double vX, double vY, double vZ, SpriteProvider spriteProvider) {
		super(world, x, y, z, vX, vY, vZ, spriteProvider);
		this.gravityStrength = 0.05F;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double Xv, double Yv, double Zv) {
			if (y <= 255) {
				return new LeafParticle(clientWorld, x, y, z, Xv, Yv, Zv, this.spriteProvider);
			} else {
				return new KillInstantParticle(clientWorld, x, y, z, Xv, Yv, Zv);
			}
		}
	}
}
