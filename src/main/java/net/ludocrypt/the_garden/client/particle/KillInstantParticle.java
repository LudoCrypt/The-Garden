package net.ludocrypt.the_garden.client.particle;

import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.world.ClientWorld;

public class KillInstantParticle extends NoRenderParticle {

	public KillInstantParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
		super(clientWorld, d, e, f, g, h, i);
		this.markDead();
	}

	public KillInstantParticle(ClientWorld clientWorld, double d, double e, double f) {
		super(clientWorld, d, e, f);
		this.markDead();
	}

}
