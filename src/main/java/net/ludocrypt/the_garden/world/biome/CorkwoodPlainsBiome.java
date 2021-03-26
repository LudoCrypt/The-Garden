package net.ludocrypt.the_garden.world.biome;

import net.ludocrypt.the_garden.init.GardenBiomes;
import net.ludocrypt.the_garden.init.GardenCarvers;
import net.ludocrypt.the_garden.init.GardenFeatures;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.ludocrypt.the_garden.init.GardenSounds;
import net.ludocrypt.the_garden.init.GardenSurfaces;
import net.ludocrypt.the_garden.util.Color;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.GenerationStep.Feature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;

public class CorkwoodPlainsBiome {

	public static int mulchColor = Color.of(216, 155, 69);

	public static Biome create() {
		Biome.Builder biome = new Biome.Builder();

		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		generationSettings.carver(GenerationStep.Carver.AIR, GardenCarvers.POINT_ONE_CONFIGURED_CARVER);
		generationSettings.surfaceBuilder(GardenSurfaces.CORKWOOD_PLAINS);
		generationSettings.feature(Feature.RAW_GENERATION, GardenFeatures.POINT_ONE_TILES);
		generationSettings.feature(Feature.LAKES, ConfiguredFeatures.LAKE_WATER);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.CORK_STUMP);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.CORK_STUMP);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.CORK_STUMP);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.CORK_STUMP);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.CORK_SPIKE_SPREAD);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.CORK_SPIKE_EXTRA);
		generationSettings.feature(Feature.UNDERGROUND_ORES, GardenFeatures.COAL_ORE_POINT_ONE);
		generationSettings.structureFeature(GardenFeatures.PLAYGROUND);
		generationSettings.structureFeature(GardenFeatures.DEAD_TREE_MINESHAFT);

		BiomeEffects.Builder biomeEffects = new BiomeEffects.Builder();
		biomeEffects.skyColor(Color.of(255, 255, 255));
		biomeEffects.waterColor(Color.of(216, 155, 69));
		biomeEffects.waterFogColor(Color.of(216, 155, 69));
		biomeEffects.fogColor(Color.of(255, 255, 255));
		biomeEffects.grassColor(Color.of(226, 202, 132));
		biomeEffects.moodSound(BiomeMoodSound.CAVE);
		biomeEffects.music(GardenSounds.POINT_ONE);
		biomeEffects.particleConfig(new BiomeParticleConfig(GardenParticles.CORK_SPORE, 0.00539756F));
		BiomeEffects effects = biomeEffects.build();

		biome.spawnSettings(spawnSettings.build());
		biome.generationSettings(generationSettings.build());
		biome.effects(effects);

		biome.precipitation(Biome.Precipitation.NONE);
		biome.category(GardenBiomes.POINT_ONE_BIOME_CATEGORY);

		biome.depth(-0.3F);
		biome.scale(0.1F);

		biome.temperature(-0.2F);
		biome.downfall(0.0F);

		return biome.build();
	}

}
