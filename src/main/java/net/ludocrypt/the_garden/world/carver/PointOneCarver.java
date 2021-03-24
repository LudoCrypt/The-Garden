package net.ludocrypt.the_garden.world.carver;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CaveCarver;

public class PointOneCarver extends CaveCarver {

	public PointOneCarver(Codec<ProbabilityConfig> codec, int y) {
		super(codec, y);
	}

	@Override
	protected float getTunnelSystemWidth(Random random) {
		float f = random.nextFloat() * 3.5F + random.nextFloat();
		if (random.nextInt(5) == 0) {
			f *= random.nextFloat() * random.nextFloat() * 2.5F + 1.0F;
		}

		return f;
	}

	@Override
	protected double getTunnelSystemHeightWidthRatio() {
		return 2.3D;
	}

	@Override
	protected int getCaveY(Random random) {
		return (random.nextInt(50) * 2) + 40;
	}

	@Override
	protected boolean canAlwaysCarveBlock(BlockState state) {
		return true;
	}

	@Override
	protected boolean canCarveBlock(BlockState state, BlockState stateAbove) {
		return true;
	}
}
