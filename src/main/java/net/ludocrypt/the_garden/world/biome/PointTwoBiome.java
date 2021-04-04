package net.ludocrypt.the_garden.world.biome;

import net.ludocrypt.the_garden.init.GardenBiomes;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.init.GardenCarvers;
import net.ludocrypt.the_garden.init.GardenFeatures;
import net.ludocrypt.the_garden.init.GardenSounds;
import net.ludocrypt.the_garden.util.Color;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.GenerationStep.Feature;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class PointTwoBiome {

	public static Biome create() {
		Biome.Builder biome = new Biome.Builder();

		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		generationSettings.carver(GenerationStep.Carver.AIR, GardenCarvers.POINT_TWO_CONFIGURED_CARVER);
		generationSettings.surfaceBuilder(SurfaceBuilder.NOPE.withConfig(new TernarySurfaceConfig(GardenBlocks.PLAYDIRT.getDefaultState(), GardenBlocks.PLAYDIRT.getDefaultState(), GardenBlocks.PLAYDIRT.getDefaultState())));
		generationSettings.feature(Feature.RAW_GENERATION, GardenFeatures.POINT_TWO_TILES);
		generationSettings.feature(Feature.LOCAL_MODIFICATIONS, GardenFeatures.CRACKED_OBSIDIAN_POOL);

		BiomeEffects.Builder biomeEffects = new BiomeEffects.Builder();
		biomeEffects.skyColor(Color.of(255, 255, 255));
		biomeEffects.waterColor(Color.of(169, 114, 91));
		biomeEffects.waterFogColor(Color.of(169, 114, 91));
		biomeEffects.fogColor(Color.of(82, 75, 55));
		biomeEffects.grassColor(Color.of(226, 202, 132));
		biomeEffects.moodSound(BiomeMoodSound.CAVE);
		biomeEffects.music(GardenSounds.POINT_TWO);
		BiomeEffects effects = biomeEffects.build();

		biome.spawnSettings(spawnSettings.build());
		biome.generationSettings(generationSettings.build());
		biome.effects(effects);

		biome.precipitation(Biome.Precipitation.NONE);
		biome.category(GardenBiomes.POINT_TWO_BIOME_CATEGORY);

		biome.depth(0.1F);
		biome.scale(0.1F);

		biome.temperature(0.2F);
		biome.downfall(0.0F);

		return biome.build();
	}

}
