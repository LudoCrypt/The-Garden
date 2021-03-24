package net.ludocrypt.the_garden.init;

import net.ludocrypt.the_garden.PreInitialize;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.MineshaftFeature;

public class GardenEnum {

	public static final Biome.Category POINT_ONE_BIOME = Biome.Category.valueOf(PreInitialize.pointOneKey);
	public static final Biome.Category POINT_TWO_BIOME = Biome.Category.valueOf(PreInitialize.pointTwoKey);

	public static final MineshaftFeature.Type DEAD_TREE_MINESHAFT = MineshaftFeature.Type.valueOf(PreInitialize.deadTreeMineshaftType);

}
