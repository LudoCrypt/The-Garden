package net.ludocrypt.the_garden.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class OSBBlock extends Block {

	public OSBBlock(Settings settings) {
		super(settings);
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		OSBBlock.displayParticles(state, world, pos, random, GardenParticles.SAWDUST, 2.5, 6.5, 0.25, 0.25);
	}

	@Environment(EnvType.CLIENT)
	public static void displayParticles(BlockState state, World world, BlockPos pos, Random random, ParticleEffect effect, double scale1, double scale2, double commonality, double extraCommonality) {
		if (random.nextDouble() < commonality) {
			int i = pos.getX();
			int j = pos.getY();
			int k = pos.getZ();
			double d = i + MathHelper.nextDouble(random, -scale1, scale1);
			double e = j + MathHelper.nextDouble(random, -scale1, scale1);
			double f = k + MathHelper.nextDouble(random, -scale1, scale1);
			world.addParticle(effect, d, e, f, 0.0D, 0.0D, 0.0D);
			BlockPos.Mutable mutable = new BlockPos.Mutable();

			if (random.nextDouble() < commonality) {
				mutable.set(i + MathHelper.nextDouble(random, -6.5, 6.5), j + MathHelper.nextDouble(random, -scale2, scale2), k + MathHelper.nextDouble(random, -scale2, scale2));
				BlockState blockState = world.getBlockState(mutable);
				if (!blockState.isFullCube(world, mutable)) {
					world.addParticle(effect, mutable.getX() + random.nextDouble(), mutable.getY() + random.nextDouble(), mutable.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
				}
			}

		}
	}

}
