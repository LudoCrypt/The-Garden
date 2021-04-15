package net.ludocrypt.the_garden.init;

import java.util.LinkedHashMap;
import java.util.Map;

import com.chocohead.mm.api.ClassTinkerers;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.config.GardenConfigurations;
import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.ludocrypt.the_garden.world.biome.ChurchparkBiome;
import net.ludocrypt.the_garden.world.biome.CorkwoodPlainsBiome;
import net.ludocrypt.the_garden.world.biome.GravityFallsBiome;
import net.ludocrypt.the_garden.world.biome.HolicIslesBiome;
import net.ludocrypt.the_garden.world.biome.IvoryShallowsBiome;
import net.ludocrypt.the_garden.world.biome.PlaypenBiome;
import net.ludocrypt.the_garden.world.biome.PointOneBiome;
import net.ludocrypt.the_garden.world.biome.PointTwoBiome;
import net.ludocrypt.the_garden.world.biome.PseudoCorkwoodPlains;
import net.ludocrypt.the_garden.world.biome.SkinnedHousepartsBiome;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.MixedNoisePoint;

public class GardenBiomes {

	private static final Map<Identifier, Biome> BIOMES = new LinkedHashMap<>();

	public static final Biome.Category POINT_ONE_BIOME_CATEGORY = ClassTinkerers.getEnum(Biome.Category.class, GardenEarlyRisers.POINT_ONE_KEY);
	public static final Biome.Category POINT_TWO_BIOME_CATEGORY = ClassTinkerers.getEnum(Biome.Category.class, GardenEarlyRisers.POINT_TWO_KEY);

	public static final RegistryKey<Biome> POINT_ONE = add("point_one", PointOneBiome.create());
	public static final RegistryKey<Biome> PLAYPEN = add("playpen", PlaypenBiome.create());
	public static final RegistryKey<Biome> CORKWOOD_PLAINS = add("corkwood_plains", CorkwoodPlainsBiome.create());
	public static final RegistryKey<Biome> PSEUDO_CORKWOOD_PLAINS = add("pseudo_corkwood_plains", PseudoCorkwoodPlains.create());
	public static final RegistryKey<Biome> CHURCHPARK = add("churchpark", ChurchparkBiome.create());
	public static final RegistryKey<Biome> SKINNED_HOUSEPARTS = add("skinned_houseparts", SkinnedHousepartsBiome.create());

	public static final RegistryKey<Biome> POINT_TWO = add("point_two", PointTwoBiome.create());
	public static final RegistryKey<Biome> GRAVITY_FALLS = add("gravity_falls", GravityFallsBiome.create());
	public static final RegistryKey<Biome> IVORY_SHALLOWS = add("ivory_shallows", IvoryShallowsBiome.create());
	public static final RegistryKey<Biome> HOLIC_ISLES = add("holic_isles", HolicIslesBiome.create());

	public static void init() {
		for (Identifier id : BIOMES.keySet()) {
			Registry.register(BuiltinRegistries.BIOME, id, BIOMES.get(id));
		}

		GardenConfigurations config = GardenConfigurations.getInstance();
		if (config.enabledBiomes.enablePointOne) {
			PointOne.addBiome(GardenBiomes.POINT_ONE, new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		}
		if (config.enabledBiomes.enablePseudoCorkwoodPlains) {
			PointOne.addBiome(GardenBiomes.PLAYPEN, new MixedNoisePoint(0.0F, -0.2F, 0.0F, 0.0F, 0.0F));
		}
		if (config.enabledBiomes.enableCorkwoodPlains) {
			PointOne.addBiome(GardenBiomes.CORKWOOD_PLAINS, new MixedNoisePoint(0.0F, -0.375F, 0.0F, 0.0F, 0.0F));
		}
		if (config.enabledBiomes.enablePlaypen) {
			PointOne.addBiome(GardenBiomes.PSEUDO_CORKWOOD_PLAINS, new MixedNoisePoint(0.0F, 0.3F, 0.0F, 0.0F, 0.275F));
		}
		if (config.enabledBiomes.enableChurchpark) {
			PointOne.addBiome(GardenBiomes.CHURCHPARK, new MixedNoisePoint(0.0F, 0.0F, 0.4F, 0.0F, 0.325F));
		}
		if (config.enabledBiomes.enableSkinnedHouseparts) {
			PointOne.addBiome(GardenBiomes.SKINNED_HOUSEPARTS, new MixedNoisePoint(0.0F, 0.25F, 0.1F, 0.0F, 0.25F));
		}

		if (config.enabledBiomes.enablePointTwo) {
			PointTwo.addBiome(GardenBiomes.POINT_TWO, new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		}
		if (config.enabledBiomes.enableGravityFalls) {
			PointTwo.addBiome(GardenBiomes.GRAVITY_FALLS, new MixedNoisePoint(0.0F, 0.2F, 0.0F, 0.0F, 0.0F));
		}
		if (config.enabledBiomes.enableIvoryShallows) {
			PointTwo.addBiome(GardenBiomes.IVORY_SHALLOWS, new MixedNoisePoint(0.0F, 0.0F, 0.2F, 0.2F, 0.0F));
		}
		if (config.enabledBiomes.enableHolicIsles) {
			PointTwo.addBiome(GardenBiomes.HOLIC_ISLES, new MixedNoisePoint(0.2F, 0.0F, 0.2F, -0.25F, 0.0F));
		}

	}

	private static RegistryKey<Biome> add(String s, Biome b) {
		Identifier id = TheGarden.id(s);
		BIOMES.put(id, b);
		return RegistryKey.of(Registry.BIOME_KEY, id);
	}

}
