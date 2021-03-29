package net.ludocrypt.the_garden.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.mixin.WaterSuspendParticleAccessor;
import net.ludocrypt.the_garden.util.Color;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class InsulationFactory implements ParticleFactory<DefaultParticleType> {
	private final SpriteProvider spriteProvider;
	private final Color color;

	public InsulationFactory(SpriteProvider spriteProvider, Color color) {
		this.spriteProvider = spriteProvider;
		this.color = color;
	}

	public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double g, double h, double i) {
		WaterSuspendParticle insulation = WaterSuspendParticleAccessor.createWaterSuspendParticle(world, x, y, z, g, -4.0D, i);
		insulation.setSprite(this.spriteProvider);
		float red = (float) (((double) color.getRed()) / 255.0D);
		float green = (float) (((double) color.getGreen()) / 255.0D);
		float blue = (float) (((double) color.getBlue()) / 255.0D);
		insulation.setColor(red, green, blue);
		return insulation;
	}
}
