package net.ludocrypt.the_garden;

import com.chocohead.mm.api.ClassTinkerers;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public interface PreInitialize {

	public static String pointOneKey = "point1";
	public static String pointTwoKey = "point2";

	public static String deadTreeMineshaftType = "dead_tree";

	static void initialize() {
		MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

		// Biome Types
		ClassTinkerers.enumBuilder(remapper.mapClassName("intermediary", "net.minecraft.class_1959$class_1961"), String.class)
				// Point One
				.addEnum(pointOneKey, () -> new Object[] { pointOneKey })
				// Point Two
				.addEnum(pointTwoKey, () -> new Object[] { pointTwoKey })
				// Build
				.build();

		// Mineshaft Types
		ClassTinkerers.enumBuilder(remapper.mapClassName("intermediary", "net.minecraft.class_3098$class_3100"), String.class)
				// Dead Tree
				.addEnum(deadTreeMineshaftType, () -> new Object[] { deadTreeMineshaftType })
				// Build
				.build();
	}
}
