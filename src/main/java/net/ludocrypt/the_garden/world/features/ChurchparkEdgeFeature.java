package net.ludocrypt.the_garden.world.features;

import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import net.ludocrypt.the_garden.blocks.EdgingBlock;
import net.ludocrypt.the_garden.blocks.EdgingFaceBlock;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class ChurchparkEdgeFeature extends Feature<DefaultFeatureConfig> {

	public ChurchparkEdgeFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {

				BlockPos blockPos = pos.add(x, chunkGenerator.getSeaLevel(), z);

				while ((world.isAir(blockPos.down()) || (world.getBlockState(blockPos.down()).getBlock() instanceof EdgingBlock || world.getBlockState(blockPos.down()).getBlock() instanceof EdgingFaceBlock)) && blockPos.getY() >= 0) {
					blockPos = blockPos.down();
				}

				while (world.getBlockState(blockPos.down()).getBlock() == GardenBlocks.PEA_GRAVEL) {

					ImmutableList<Direction> list = ImmutableList.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

					BlockPos finalBlockPos = blockPos;

					list.forEach((dir) -> {

						BlockPos offset = finalBlockPos.offset(dir).down();

						if ((world.isAir(offset) || (world.getBlockState(offset).getBlock() instanceof EdgingBlock || world.getBlockState(offset).getBlock() instanceof EdgingFaceBlock))) {

							boolean north = false;
							boolean east = false;
							boolean south = false;
							boolean west = false;

							if (world.getBlockState(offset.north()).getBlock() == GardenBlocks.PEA_GRAVEL) {
								north = true;
							}
							if (world.getBlockState(offset.east()).getBlock() == GardenBlocks.PEA_GRAVEL) {
								east = true;
							}
							if (world.getBlockState(offset.south()).getBlock() == GardenBlocks.PEA_GRAVEL) {
								south = true;
							}
							if (world.getBlockState(offset.west()).getBlock() == GardenBlocks.PEA_GRAVEL) {
								west = true;
							}

							if (north || east || south || west) {
								world.setBlockState(offset, GardenBlocks.EDGING_FACE.BLACK.getDefaultState().with(EdgingFaceBlock.NORTH, north).with(EdgingFaceBlock.EAST, east).with(EdgingFaceBlock.SOUTH, south).with(EdgingFaceBlock.WEST, west), 2);
								if (world.isAir(offset.up())) {
									world.setBlockState(offset.up(), GardenBlocks.EDGING.BLACK.getDefaultState().with(EdgingFaceBlock.NORTH, north).with(EdgingFaceBlock.EAST, east).with(EdgingFaceBlock.SOUTH, south).with(EdgingFaceBlock.WEST, west), 2);
								}
							}
						}

					});

					blockPos = blockPos.down();
				}

			}
		}
		return true;
	}

}
