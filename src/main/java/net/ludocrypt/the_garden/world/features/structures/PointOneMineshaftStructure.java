package net.ludocrypt.the_garden.world.features.structures;

import com.mojang.serialization.Codec;

import net.minecraft.structure.MineshaftGenerator;
import net.minecraft.structure.MineshaftGenerator.MineshaftRoom;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class PointOneMineshaftStructure extends MineshaftFeature {

	public PointOneMineshaftStructure(Codec<MineshaftFeatureConfig> codec) {
		super(codec);
	}

	public static class Start extends net.minecraft.world.gen.feature.MineshaftFeature.Start {

		public Start(StructureFeature<MineshaftFeatureConfig> structureFeature, int i, int j, BlockBox blockBox, int k, long l) {
			super(structureFeature, i, j, blockBox, k, l);
		}

		@Override
		public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int i, int j, Biome biome, MineshaftFeatureConfig mineshaftFeatureConfig) {
			MineshaftGenerator.MineshaftRoom mineshaftRoom = new MineshaftRoom(15, this.random, (i << 4) + 2, (j << 4) + 2, mineshaftFeatureConfig.type);
			this.children.add(mineshaftRoom);
			mineshaftRoom.fillOpenings(mineshaftRoom, this.children, this.random);
			this.setBoundingBoxFromChildren();
			this.randomUpwardTranslation(this.random, 35, 75);
		}
	}

}
