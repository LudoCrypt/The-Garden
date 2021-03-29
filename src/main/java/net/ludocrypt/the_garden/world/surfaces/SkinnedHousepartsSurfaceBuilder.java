package net.ludocrypt.the_garden.world.surfaces;

import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;

import net.ludocrypt.the_garden.world.surfaces.PatternSurfaceConfig.PatternPair;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class SkinnedHousepartsSurfaceBuilder extends SurfaceBuilder<PatternSurfaceConfig> {

	public SkinnedHousepartsSurfaceBuilder(Codec<PatternSurfaceConfig> codec) {
		super(codec);
	}

	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, PatternSurfaceConfig config) {
		List<PatternPair> pattern = config.getPattern();
		int m = Math.abs(z % pattern.size());
		SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height + pattern.get(m).getH(), noise, defaultBlock, defaultFluid, seaLevel, seed, new TernarySurfaceConfig(pattern.get(m).getState(), pattern.get(m).getState(), pattern.get(m).getState()));
	}

}
