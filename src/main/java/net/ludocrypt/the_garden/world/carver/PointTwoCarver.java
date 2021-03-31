package net.ludocrypt.the_garden.world.carver;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CaveCarver;

public class PointTwoCarver extends CaveCarver {

	public PointTwoCarver(Codec<ProbabilityConfig> codec, int y) {
		super(codec, y);
	}

	@Override
	protected float getTunnelSystemWidth(Random random) {
		float f = random.nextFloat() * 1.5F + random.nextFloat();
		if (random.nextInt(10) == 0) {
			f *= random.nextFloat() * random.nextFloat() * 1.0F + 0.25F;
		}

		return f;
	}

	@Override
	protected double getTunnelSystemHeightWidthRatio() {
		return 1.75D;
	}

	@Override
	protected int getCaveY(Random random) {
		return random.nextInt(200) + 60;
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
