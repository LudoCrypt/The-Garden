package net.ludocrypt.the_garden.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.MixedNoisePoint;

public class BiomeNoisePairRegistry {

	public static final Codec<BiomeNoisePairRegistry> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Identifier.CODEC.fieldOf("biome").forGetter((pointOneBiomes) -> {
			return pointOneBiomes.biomeToGenerate.getValue();
		}), Biome.MixedNoisePoint.CODEC.fieldOf("noise").forGetter((pointOneBiomes) -> {
			return pointOneBiomes.noiseToPair;
		})).apply(instance, BiomeNoisePairRegistry::new);
	});

	private final RegistryKey<Biome> biomeToGenerate;
	private final MixedNoisePoint noiseToPair;

	public BiomeNoisePairRegistry(RegistryKey<Biome> biome, MixedNoisePoint noise) {
		this.biomeToGenerate = biome;
		this.noiseToPair = noise;
	}

	public BiomeNoisePairRegistry(Identifier biome, MixedNoisePoint noise) {
		this(RegistryKey.of(Registry.BIOME_KEY, biome), noise);
	}

	public RegistryKey<Biome> getBiomeToGenerate() {
		return biomeToGenerate;
	}

	public MixedNoisePoint getNoiseToPair() {
		return noiseToPair;
	}

}
