package net.ludocrypt.the_garden.world.surfaces;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.ibm.icu.impl.Pair;
import com.mojang.serialization.Codec;

import net.ludocrypt.the_garden.blocks.InsulationPaddingBlock;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class SkinnedHousepartsSurfaceBuilder extends SurfaceBuilder<PatternSurfaceConfig> {

	private long seed;
	private ImmutableList<Pair<BlockState, OctavePerlinNoiseSampler>> noisemapOne = ImmutableList.of();
	private OctavePerlinNoiseSampler shoreNoise;

	public SkinnedHousepartsSurfaceBuilder(Codec<PatternSurfaceConfig> codec) {
		super(codec);
	}

	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, PatternSurfaceConfig config) {
		List<BlockState> pattern = config.getPattern();
		int m = Math.abs(z % pattern.size());
		BlockState state = pattern.get(m);
		SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, new TernarySurfaceConfig(state, state, state));

		BlockState topLayer = this.noisemapOne.stream().max(Comparator.comparing((entry) -> {
			return ((Pair<BlockState, OctavePerlinNoiseSampler>) entry).second.sample(x, z, height);
		})).get().first;

		chunk.setBlockState(new BlockPos(x, height, z), topLayer, false);
	}

	public void initSeed(long seed) {
		if (this.seed != seed || this.shoreNoise == null || this.noisemapOne.isEmpty()) {
			this.noisemapOne = createNoisesForStates(this.getNoisemapOne(), seed);
			this.shoreNoise = new OctavePerlinNoiseSampler(new ChunkRandom(seed + this.noisemapOne.size()), ImmutableList.of(-3, 10, 15));
		}

		this.seed = seed;
	}

	private static ImmutableList<Pair<BlockState, OctavePerlinNoiseSampler>> createNoisesForStates(ImmutableList<BlockState> states, long seed) {
		ArrayList<Pair<BlockState, OctavePerlinNoiseSampler>> builder = Lists.newArrayList();

		for (UnmodifiableIterator<BlockState> var4 = states.iterator(); var4.hasNext(); ++seed) {
			BlockState layer = var4.next();
			builder.add(Pair.of(layer, new OctavePerlinNoiseSampler(new ChunkRandom(seed), ImmutableList.of(-6, -3))));
		}

		return ImmutableList.copyOf(builder);
	}

	protected ImmutableList<BlockState> getNoisemapOne() {
		return ImmutableList.of(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), GardenBlocks.PINK_INSULATION_PADDING.getDefaultState().with(InsulationPaddingBlock.DOWN, true), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), GardenBlocks.WHITE_INSULATION_PADDING.getDefaultState().with(InsulationPaddingBlock.DOWN, true), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), GardenBlocks.GREEN_INSULATION_PADDING.getDefaultState().with(InsulationPaddingBlock.DOWN, true), Blocks.AIR.getDefaultState());
	}

}
