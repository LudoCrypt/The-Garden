package net.ludocrypt.the_garden.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.mixin.ChunkGeneratorSettingsAccessor;
import net.ludocrypt.the_garden.mixin.DimensionTypeAccessor;
import net.ludocrypt.the_garden.mixin.MultiNoiseBiomeSourceAccessor;
import net.ludocrypt.the_garden.util.DimensionUtil;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.VoronoiBiomeAccessType;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseSamplingConfig;
import net.minecraft.world.gen.chunk.SlideConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;

public class PointOne {

	public static final Identifier IDENTIFIER = TheGarden.id("point_one");
	public static final Identifier SKY = IDENTIFIER;
	public static final RegistryKey<World> WORLD = DimensionUtil.getWorld(IDENTIFIER);
	public static final RegistryKey<DimensionType> DIMENSION_KEY = DimensionUtil.getDimensionType(IDENTIFIER);
	public static final RegistryKey<DimensionOptions> DIMENSION_OPTIONS = DimensionUtil.getDimensionOptions(IDENTIFIER);
	public static final RegistryKey<ChunkGeneratorSettings> CHUNK_GENERATOR_SETTINGS = RegistryKey.of(Registry.NOISE_SETTINGS_WORLDGEN, IDENTIFIER);
	public static final DimensionType DIMENSION_TYPE = DimensionTypeAccessor.createDimensionType(OptionalLong.of(1200), true, false, false, false, 7.0D, false, false, false, true, false, 256, VoronoiBiomeAccessType.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getId(), SKY, 0.25F);

	private static final Map<RegistryKey<Biome>, Biome.MixedNoisePoint> NOISE_POINTS = new HashMap<>();

	public static void addBiome(RegistryKey<Biome> biome, Biome.MixedNoisePoint noise) {
		NOISE_POINTS.put(biome, noise);
	}

	public static Map<RegistryKey<Biome>, Biome.MixedNoisePoint> getNoisePoints() {
		return NOISE_POINTS;
	}

	public static final MultiNoiseBiomeSource.Preset BIOME_SOURCE_PRESET = new MultiNoiseBiomeSource.Preset(IDENTIFIER, (preset, registry, seed) -> {
		List<Pair<Biome.MixedNoisePoint, Supplier<Biome>>> biomes = new ArrayList<>();
		getNoisePoints().forEach((biomeKey, noisePoint) -> biomes.add(Pair.of(noisePoint, () -> registry.getOrThrow(biomeKey))));
		return MultiNoiseBiomeSourceAccessor.createMultiNoiseBiomeSource(seed, biomes, new MultiNoiseBiomeSource.NoiseParameters(-8, ImmutableList.of(1.0D, -1.0D)), new MultiNoiseBiomeSource.NoiseParameters(-7, ImmutableList.of(1.0D, 1.0D)), new MultiNoiseBiomeSource.NoiseParameters(-6, ImmutableList.of(0.75D, 1.0D)), new MultiNoiseBiomeSource.NoiseParameters(-7, ImmutableList.of(1.0D, 1.0D)), Optional.of(Pair.of(registry, preset)));
	});

	private static ChunkGeneratorSettings createSettings() {
		return ChunkGeneratorSettingsAccessor.createChunkGeneratorSettings(new StructuresConfig(false), new GenerationShapeConfig(256, new NoiseSamplingConfig(0.514507745D, 0.693358845D, 50.928453884D, 128.954865687D), new SlideConfig(0, 0, 0), new SlideConfig(0, 0, 0), 1, 2, 2.1399846D, 1.8662893D, true, true, false, false), GardenBlocks.PLAYDIRT.getDefaultState(), GardenBlocks.PLAYDIRT.getDefaultState(), -10, 0, -1, false);
	}

	public static ChunkGenerator createGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
		return new NoiseChunkGenerator(BIOME_SOURCE_PRESET.getBiomeSource(biomeRegistry, seed), seed, () -> {
			return chunkGeneratorSettingsRegistry.getOrThrow(CHUNK_GENERATOR_SETTINGS);
		});
	}

	public static void init() {
		BuiltinRegistries.add(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, IDENTIFIER, createSettings());
	}

}
