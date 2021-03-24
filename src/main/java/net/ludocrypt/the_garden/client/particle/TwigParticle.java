package net.ludocrypt.the_garden.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.util.Color;
import net.ludocrypt.the_garden.util.GardenMulchEffects;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class TwigParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;
	private int totalAge = 0;

	public TwigParticle(ClientWorld world, double x, double y, double z, double vX, double vY, double vZ, SpriteProvider spriteProvider) {
		super(world, x, y, z, vX, vY, vZ);
		this.maxAge = 128;
		this.setColor((float) Color.colorOf(GardenMulchEffects.getMulchColor(world, new BlockPos(x, y, z))).getRed() / 255, (float) Color.colorOf(GardenMulchEffects.getMulchColor(world, new BlockPos(x, y, z))).getGreen() / 255, (float) Color.colorOf(GardenMulchEffects.getMulchColor(world, new BlockPos(x, y, z))).getBlue() / 255);
		this.gravityStrength = 0.1F;
		this.spriteProvider = spriteProvider;
		this.setSprite(spriteProvider.getSprite(this.age % 4, 4));
	}

	public int getColorMultiplier(float tint) {
		return 15728880;
	}

	public void tick() {
		this.velocityY -= 0.04D * (double) this.gravityStrength;
		this.move(this.velocityX, this.velocityY, this.velocityZ);
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ >= this.maxAge || this.onGround) {
			this.markDead();
		} else {
			if (this.age % 8 == 0) {
				totalAge++;
				this.setSprite(spriteProvider.getSprite((this.totalAge % 4) + 1, 4));
			}
		}
	}

	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_LIT;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double Xv, double Yv, double Zv) {
			if (y <= 255) {
				return new TwigParticle(clientWorld, x, y, z, Xv, Yv, Zv, this.spriteProvider);
			} else {
				return new KillInstantParticle(clientWorld, x, y, z, Xv, Yv, Zv);
			}
		}
	}
}
