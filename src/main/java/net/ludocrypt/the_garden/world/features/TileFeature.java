package net.ludocrypt.the_garden.world.features;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.ludocrypt.the_garden.blocks.TileBlock;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class TileFeature extends Feature<DefaultFeatureConfig> {

	public TileFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				if (x % 2 == 0) {
					world.setBlockState(pos.add(x, 0, z), GardenBlocks.TILE.getDefaultState().with(TileBlock.FACING, Direction.WEST), 2);
				} else {
					world.setBlockState(pos.add(x, 0, z), GardenBlocks.TILE.getDefaultState().with(TileBlock.FACING, Direction.EAST), 2);
				}
			}
		}
		return true;
	}

}
