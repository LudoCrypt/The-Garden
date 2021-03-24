package net.ludocrypt.the_garden.init;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.util.GardenMulchEffects;
import net.ludocrypt.the_garden.world.biome.ChurchparkBiome;
import net.ludocrypt.the_garden.world.biome.CorkwoodPlainsBiome;
import net.ludocrypt.the_garden.world.biome.PlaypenBiome;
import net.ludocrypt.the_garden.world.biome.PointOneBiome;
import net.ludocrypt.the_garden.world.biome.PointTwoBiome;
import net.ludocrypt.the_garden.world.biome.PseudoCorkwoodPlains;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class GardenBiomes {

	private static final Map<Identifier, RegistryKey<Biome>> BIOMES = new LinkedHashMap<>();

	public static final RegistryKey<Biome> POINT_ONE = add(PointOneBiome.class);
	public static final RegistryKey<Biome> PLAYPEN = add(PlaypenBiome.class);
	public static final RegistryKey<Biome> CORKWOOD_PLAINS = add(CorkwoodPlainsBiome.class);
	public static final RegistryKey<Biome> PSEUDO_CORKWOOD_PLAINS = add(PseudoCorkwoodPlains.class);
	public static final RegistryKey<Biome> CHURCHPARK = add(ChurchparkBiome.class);

	public static final RegistryKey<Biome> POINT_TWO = add(PointTwoBiome.class);

	public static void init() {

	}

	private static RegistryKey<Biome> add(Class<?> cl) {
		try {
			String biomeName = "";
			Biome biome = null;
			Integer mulchColor = null;

			String className = cl.getName().replace(cl.getPackageName() + ".", "");

			className = className.replace("Biome", "");

			char[] charactersOfClassName = new char[className.length()];
			className.getChars(0, className.length(), charactersOfClassName, 0);
			int place = 0;

			for (char c : charactersOfClassName) {
				if (Character.isUpperCase(c)) {
					biomeName = String.join("", biomeName, place != 0 ? "_" : "", String.valueOf(Character.toLowerCase(c)));
				} else {
					biomeName = String.join("", biomeName, String.valueOf(c));
				}
				++place;
			}

			Method createBiome = cl.getDeclaredMethod("create");
			biome = (Biome) createBiome.invoke(cl, new Object[0]);

			try {
				Field mulchField = cl.getDeclaredField("mulchColor");
				mulchColor = (int) mulchField.get(cl);
			} catch (NoSuchFieldException nsfe) {
				mulchColor = GardenMulchEffects.defaultMulchColor.getRGB();
			}

			return add(biomeName, biome, mulchColor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static RegistryKey<Biome> add(String s, Biome b, int rgb) {
		Identifier id = TheGarden.id(s);
		Registry.register(BuiltinRegistries.BIOME, id, b);
		RegistryKey<Biome> key = RegistryKey.of(Registry.BIOME_KEY, id);
		BIOMES.put(id, key);
		GardenMulchEffects.REGISTERED_MULCH_COLORS.put(id, rgb);
		return key;
	}

}
