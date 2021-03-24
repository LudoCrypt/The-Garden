package net.ludocrypt.the_garden.world.features.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.structure.pool.StructurePool;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ExtendedStructurePoolFeatureConfig {

	public static final Codec<StructurePoolFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(StructurePoolFeatureConfig::getStartPool), Codec.INT.fieldOf("size").forGetter(StructurePoolFeatureConfig::getSize)).apply(instance, StructurePoolFeatureConfig::new);
	});

}
