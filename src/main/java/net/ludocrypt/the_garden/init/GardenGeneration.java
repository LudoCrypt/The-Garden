package net.ludocrypt.the_garden.init;

import java.util.LinkedHashMap;
import java.util.Map;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.config.GardenConfigurations;
import net.ludocrypt.the_garden.mixin.BuiltinRegistriesAccessor;
import net.ludocrypt.the_garden.util.BiomeNoisePairRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.MixedNoisePoint;
import net.minecraft.world.biome.BiomeKeys;

public class GardenGeneration {

	private static final Map<Identifier, BiomeNoisePairRegistry> POINT_ONE_GENERATION = new LinkedHashMap<>();
	private static final Map<Identifier, BiomeNoisePairRegistry> POINT_TWO_GENERATION = new LinkedHashMap<>();

	public static final RegistryKey<Registry<BiomeNoisePairRegistry>> POINT_ONE_BIOMES_KEY = RegistryKey.ofRegistry(TheGarden.id("custom/generation/point_one"));
	public static final Registry<BiomeNoisePairRegistry> POINT_ONE_BIOMES = BuiltinRegistriesAccessor.invokeAddRegistry(POINT_ONE_BIOMES_KEY, () -> {
		return GardenGeneration.DEFAULT_POINT_ONE;
	});

	public static final RegistryKey<Registry<BiomeNoisePairRegistry>> POINT_TWO_BIOMES_KEY = RegistryKey.ofRegistry(TheGarden.id("custom/generation/point_two"));
	public static final Registry<BiomeNoisePairRegistry> POINT_TWO_BIOMES = BuiltinRegistriesAccessor.invokeAddRegistry(POINT_TWO_BIOMES_KEY, () -> {
		return GardenGeneration.DEFAULT_POINT_TWO;
	});

	public static final BiomeNoisePairRegistry DEFAULT_POINT_ONE = Registry.register(POINT_ONE_BIOMES, TheGarden.id("default_failed_registry"), new BiomeNoisePairRegistry(BiomeKeys.OCEAN, new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F)));
	public static final BiomeNoisePairRegistry DEFAULT_POINT_TWO = Registry.register(POINT_TWO_BIOMES, TheGarden.id("default_failed_registry"), new BiomeNoisePairRegistry(BiomeKeys.OCEAN, new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F)));

	public static final RegistryKey<BiomeNoisePairRegistry> POINT_ONE = addPointOneGeneration("point_one", GardenBiomes.POINT_ONE, new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
	public static final RegistryKey<BiomeNoisePairRegistry> PLAYPEN = addPointOneGeneration("playpen", GardenBiomes.PLAYPEN, new MixedNoisePoint(0.0F, -0.2F, 0.0F, 0.0F, 0.0F));
	public static final RegistryKey<BiomeNoisePairRegistry> CORKWOOD_PLAINS = addPointOneGeneration("corkwood_plains", GardenBiomes.CORKWOOD_PLAINS, new MixedNoisePoint(0.0F, -0.375F, 0.0F, 0.0F, 0.0F));
	public static final RegistryKey<BiomeNoisePairRegistry> PSEUDO_CORKWOOD_PLAINS = addPointOneGeneration("pseudo_corkwood_plains", GardenBiomes.PSEUDO_CORKWOOD_PLAINS, new MixedNoisePoint(0.0F, 0.3F, 0.0F, 0.0F, 0.275F));
	public static final RegistryKey<BiomeNoisePairRegistry> CHURCHPARK = addPointOneGeneration("churchpark", GardenBiomes.CHURCHPARK, new MixedNoisePoint(0.0F, 0.0F, 0.4F, 0.0F, 0.325F));
	public static final RegistryKey<BiomeNoisePairRegistry> SKINNED_HOUSEPARTS = addPointOneGeneration("skinned_houseparts", GardenBiomes.SKINNED_HOUSEPARTS, new MixedNoisePoint(0.0F, 0.25F, 0.1F, 0.0F, 0.25F));

	public static final RegistryKey<BiomeNoisePairRegistry> POINT_TWO = addPointTwoGeneration("point_two", GardenBiomes.POINT_TWO, new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
	public static final RegistryKey<BiomeNoisePairRegistry> GRAVITY_FALLS = addPointTwoGeneration("gravity_falls", GardenBiomes.GRAVITY_FALLS, new MixedNoisePoint(0.0F, 0.2F, 0.0F, 0.0F, 0.0F));
	public static final RegistryKey<BiomeNoisePairRegistry> IVORY_SHALLOWS = addPointTwoGeneration("ivory_shallows", GardenBiomes.IVORY_SHALLOWS, new MixedNoisePoint(0.0F, 0.0F, 0.2F, 0.2F, 0.0F));
	public static final RegistryKey<BiomeNoisePairRegistry> HOLIC_ISLES = addPointTwoGeneration("holic_isles", GardenBiomes.HOLIC_ISLES, new MixedNoisePoint(0.2F, 0.0F, 0.2F, -0.25F, 0.0F));

	public static void init() {
		GardenConfigurations config = GardenConfigurations.getInstance();
		if (config.enabledBiomes.enablePointOne) {
			Registry.register(POINT_ONE_BIOMES, TheGarden.id("point_one"), POINT_ONE_GENERATION.get(TheGarden.id("point_one")));
		}
		if (config.enabledBiomes.enablePseudoCorkwoodPlains) {
			Registry.register(POINT_ONE_BIOMES, TheGarden.id("playpen"), POINT_ONE_GENERATION.get(TheGarden.id("playpen")));
		}
		if (config.enabledBiomes.enableCorkwoodPlains) {
			Registry.register(POINT_ONE_BIOMES, TheGarden.id("corkwood_plains"), POINT_ONE_GENERATION.get(TheGarden.id("corkwood_plains")));
		}
		if (config.enabledBiomes.enablePlaypen) {
			Registry.register(POINT_ONE_BIOMES, TheGarden.id("pseudo_corkwood_plains"), POINT_ONE_GENERATION.get(TheGarden.id("pseudo_corkwood_plains")));
		}
		if (config.enabledBiomes.enableChurchpark) {
			Registry.register(POINT_ONE_BIOMES, TheGarden.id("churchpark"), POINT_ONE_GENERATION.get(TheGarden.id("churchpark")));
		}
		if (config.enabledBiomes.enableSkinnedHouseparts) {
			Registry.register(POINT_ONE_BIOMES, TheGarden.id("skinned_houseparts"), POINT_ONE_GENERATION.get(TheGarden.id("skinned_houseparts")));
		}

		if (config.enabledBiomes.enablePointTwo) {
			Registry.register(POINT_TWO_BIOMES, TheGarden.id("point_two"), POINT_TWO_GENERATION.get(TheGarden.id("point_two")));
		}
		if (config.enabledBiomes.enableGravityFalls) {
			Registry.register(POINT_TWO_BIOMES, TheGarden.id("gravity_falls"), POINT_TWO_GENERATION.get(TheGarden.id("gravity_falls")));
		}
		if (config.enabledBiomes.enableIvoryShallows) {
			Registry.register(POINT_TWO_BIOMES, TheGarden.id("ivory_shallows"), POINT_TWO_GENERATION.get(TheGarden.id("ivory_shallows")));
		}
		if (config.enabledBiomes.enableHolicIsles) {
			Registry.register(POINT_TWO_BIOMES, TheGarden.id("holic_isles"), POINT_TWO_GENERATION.get(TheGarden.id("holic_isles")));
		}
	}

	private static RegistryKey<BiomeNoisePairRegistry> addPointOneGeneration(String s, RegistryKey<Biome> biome, MixedNoisePoint noise) {
		Identifier id = TheGarden.id(s);
		BiomeNoisePairRegistry bnpr = new BiomeNoisePairRegistry(biome, noise);
		POINT_ONE_GENERATION.put(id, bnpr);
		return RegistryKey.of(POINT_ONE_BIOMES_KEY, id);
	}

	private static RegistryKey<BiomeNoisePairRegistry> addPointTwoGeneration(String s, RegistryKey<Biome> biome, MixedNoisePoint noise) {
		Identifier id = TheGarden.id(s);
		BiomeNoisePairRegistry bnpr = new BiomeNoisePairRegistry(biome, noise);
		POINT_TWO_GENERATION.put(id, bnpr);
		return RegistryKey.of(POINT_TWO_BIOMES_KEY, id);
	}

}
