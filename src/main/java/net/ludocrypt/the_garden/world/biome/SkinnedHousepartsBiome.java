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

public class SkinnedHousepartsBiome {

	public static int mulchColor = Color.of(137, 97, 105);

	public static Biome create() {
		Biome.Builder biome = new Biome.Builder();

		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		generationSettings.carver(GenerationStep.Carver.AIR, GardenCarvers.POINT_ONE_CONFIGURED_CARVER);
		generationSettings.surfaceBuilder(GardenSurfaces.SKINNED_HOUSEPARTS);
		generationSettings.feature(Feature.RAW_GENERATION, GardenFeatures.POINT_ONE_TILES);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.PUDDLE);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.GREEN_INSULATION_BALL);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.GREEN_INSULATION_BALL);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.GREEN_INSULATION_BALL);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.PINK_INSULATION_BALL);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.BROWN_INSULATION_BALL);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.BROWN_INSULATION_BALL);
		generationSettings.feature(Feature.TOP_LAYER_MODIFICATION, GardenFeatures.PSEUDO_BASE);

		BiomeEffects.Builder biomeEffects = new BiomeEffects.Builder();
		biomeEffects.skyColor(Color.of(255, 255, 255));
		biomeEffects.waterColor(Color.of(153, 111, 142));
		biomeEffects.waterFogColor(Color.of(153, 111, 142));
		biomeEffects.fogColor(Color.of(255, 255, 255));
		biomeEffects.grassColor(Color.of(226, 202, 132));
		biomeEffects.moodSound(BiomeMoodSound.CAVE);
		biomeEffects.music(GardenSounds.POINT_ONE);
		biomeEffects.particleConfig(new BiomeParticleConfig(GardenParticles.SAWDUST, 0.053846894F));
		BiomeEffects effects = biomeEffects.build();

		biome.spawnSettings(spawnSettings.build());
		biome.generationSettings(generationSettings.build());
		biome.effects(effects);

		biome.precipitation(Biome.Precipitation.NONE);
		biome.category(GardenBiomes.POINT_ONE_BIOME_CATEGORY);

		biome.depth(-0.6F);
		biome.scale(0.1F);

		biome.temperature(0.5F);
		biome.downfall(0.0F);

		return biome.build();
	}

}
