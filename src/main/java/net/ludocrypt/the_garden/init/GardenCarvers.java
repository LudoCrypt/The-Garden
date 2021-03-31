package net.ludocrypt.the_garden.init;

import java.util.HashMap;
import java.util.Map;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.world.carver.PointOneCarver;
import net.ludocrypt.the_garden.world.carver.PointTwoCarver;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;

public class GardenCarvers {

	private static final Map<Identifier, Carver<? extends CarverConfig>> CARVERS = new HashMap<>();
	private static final Map<Identifier, ConfiguredCarver<? extends CarverConfig>> CONFIGURED_CARVERS = new HashMap<>();

	public static final Carver<ProbabilityConfig> POINT_ONE_CARVER = add("point_one_carver", new PointOneCarver(ProbabilityConfig.CODEC, 256));
	public static final ConfiguredCarver<ProbabilityConfig> POINT_ONE_CONFIGURED_CARVER = add("point_one", POINT_ONE_CARVER.configure(new ProbabilityConfig(0.364896F)));

	public static final Carver<ProbabilityConfig> POINT_TWO_CARVER = add("point_two_carver", new PointTwoCarver(ProbabilityConfig.CODEC, 256));
	public static final ConfiguredCarver<ProbabilityConfig> POINT_TWO_CONFIGURED_CARVER = add("point_two", POINT_TWO_CARVER.configure(new ProbabilityConfig(0.046875F)));

	private static <C extends CarverConfig> Carver<C> add(String name, Carver<C> c) {
		CARVERS.put(TheGarden.id(name), c);
		return c;
	}

	private static <WC extends CarverConfig> ConfiguredCarver<WC> add(String name, ConfiguredCarver<WC> cc) {
		CONFIGURED_CARVERS.put(TheGarden.id(name), cc);
		return cc;
	}

	public static void init() {
		for (Identifier id : CARVERS.keySet()) {
			Registry.register(Registry.CARVER, id, CARVERS.get(id));
		}
		for (Identifier id : CONFIGURED_CARVERS.keySet()) {
			Registry.register(BuiltinRegistries.CONFIGURED_CARVER, id, CONFIGURED_CARVERS.get(id));
		}
	}

}
