package net.ludocrypt.the_garden.init;

import com.chocohead.mm.api.ClassTinkerers;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class GardenEarlyRisers implements Runnable {

	public static final String POINT_ONE_KEY = "point1";
	public static final String POINT_TWO_KEY = "point2";
	public static final String DEAD_TREE_KEY = "dead_tree";

	@Override
	public void run() {
		MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

		// Biome Types
		ClassTinkerers.enumBuilder(remapper.mapClassName("intermediary", "net.minecraft.class_1959$class_1961"), String.class)
				// Point One
				.addEnum(POINT_ONE_KEY, () -> new Object[] { POINT_ONE_KEY })
				// Point Two
				.addEnum(POINT_TWO_KEY, () -> new Object[] { POINT_TWO_KEY })
				// Build
				.build();

		// Mineshaft Types
		ClassTinkerers.enumBuilder(remapper.mapClassName("intermediary", "net.minecraft.class_3098$class_3100"), String.class)
				// Dead Tree
				.addEnum(DEAD_TREE_KEY, () -> new Object[] { DEAD_TREE_KEY })
				// Build
				.build();
	}
}
