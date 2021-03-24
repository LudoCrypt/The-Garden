package net.ludocrypt.the_garden.world.surfaces;

import java.util.Comparator;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;

import net.ludocrypt.the_garden.blocks.MulchLayerBlock;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class PointOneSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

	private long seed;
	private ImmutableMap<Integer, OctavePerlinNoiseSampler> noisemapOne = ImmutableMap.of();
	private OctavePerlinNoiseSampler shoreNoise;

	public PointOneSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
		super(codec);
	}

	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
		SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
		BlockPos pos = new BlockPos(x, height, z);

		while (chunk.getBlockState(pos.up()).getBlock() != Blocks.AIR) {
			pos = pos.up();
		}

		if (chunk.getBlockState(pos.down()).getBlock() == GardenBlocks.MULCH_BLOCK) {
			int layer = 0;

			Integer addedLayerHeight = this.noisemapOne.entrySet().stream().max(Comparator.comparing((entry) -> {
				return entry.getValue().sample((double) x, (double) z, (double) height);
			})).get().getKey();

			addedLayerHeight %= 8;

			layer += addedLayerHeight;

			for (int xCheck = -1; xCheck <= 1; xCheck++) {
				for (int zCheck = -1; zCheck <= 1; zCheck++) {
					if (chunk.getPos().getStartPos() == new ChunkPos(pos.add(xCheck, 1, zCheck)).getStartPos()) {
						if (chunk.getBlockState(pos.add(xCheck, 1, zCheck)).getBlock() == GardenBlocks.MULCH_BLOCK) {
							layer += random.nextInt(3) + 16;
							break;
						}
					}
				}
			}

			if (layer >= 1 && layer <= 7) {
				chunk.setBlockState(pos, GardenBlocks.MULCH_LAYER_BLOCK.getDefaultState().with(MulchLayerBlock.LAYERS, layer), false);
			} else if (layer >= 8) {
				BlockPos.Mutable mut = pos.mutableCopy();
				for (int i = 0; i < layer / 8; i++) {
					chunk.setBlockState(mut, GardenBlocks.MULCH_BLOCK.getDefaultState(), false);
					mut.move(Direction.UP);
				}
				if (layer % 8 != 0) {
					chunk.setBlockState(pos, GardenBlocks.MULCH_LAYER_BLOCK.getDefaultState().with(MulchLayerBlock.LAYERS, layer % 8), false);
				}
			}
		}
	}

	public void initSeed(long seed) {
		if (this.seed != seed || this.shoreNoise == null || this.noisemapOne.isEmpty()) {
			this.noisemapOne = createNoisesForStates(this.getNoisemapOne(), seed);
			this.shoreNoise = new OctavePerlinNoiseSampler(new ChunkRandom(seed + this.noisemapOne.size()), ImmutableList.of(0));
		}

		this.seed = seed;
	}

	private static ImmutableMap<Integer, OctavePerlinNoiseSampler> createNoisesForStates(ImmutableList<Integer> states, long seed) {
		Builder<Integer, OctavePerlinNoiseSampler> builder = new Builder<Integer, OctavePerlinNoiseSampler>();

		for (UnmodifiableIterator<Integer> var4 = states.iterator(); var4.hasNext(); ++seed) {
			Integer layer = (Integer) var4.next();
			builder.put(layer, new OctavePerlinNoiseSampler(new ChunkRandom(seed), ImmutableList.of(-4)));
		}

		return builder.build();
	}

	protected ImmutableList<Integer> getNoisemapOne() {
		return ImmutableList.of(0, 1, 2, 8, 9);
	}

}
