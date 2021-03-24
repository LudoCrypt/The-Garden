package net.ludocrypt.the_garden.client.particle;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.mixin.WaterSuspendParticleAccessor;
import net.ludocrypt.the_garden.util.Color;
import net.ludocrypt.the_garden.util.GardenMulchEffects;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class CorkSporeFactory implements ParticleFactory<DefaultParticleType> {
	private final SpriteProvider spriteProvider;

	public CorkSporeFactory(SpriteProvider spriteProvider) {
		this.spriteProvider = spriteProvider;
	}

	public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double g, double h, double i) {
		if (y <= 255) {
			Random random = world.random;
			double j = random.nextGaussian() * 2.999999974752427E-7D;
			double k = random.nextGaussian() * 7.999999747378752E-5D;
			double l = random.nextGaussian() * 2.999999974752427E-7D;
			WaterSuspendParticle waterSuspendParticle = WaterSuspendParticleAccessor.createWaterSuspendParticle(world, x, y, z, j, k, l);
			waterSuspendParticle.setSprite(this.spriteProvider);
			waterSuspendParticle.setColor((float) Color.colorOf(GardenMulchEffects.getMulchColor(world, new BlockPos(x, y, z))).getRed() / 255, (float) Color.colorOf(GardenMulchEffects.getMulchColor(world, new BlockPos(x, y, z))).getGreen() / 255, (float) Color.colorOf(GardenMulchEffects.getMulchColor(world, new BlockPos(x, y, z))).getBlue() / 255);
			return waterSuspendParticle;
		} else {
			return new KillInstantParticle(world, x, y, z, g, h, i);
		}
	}
}
