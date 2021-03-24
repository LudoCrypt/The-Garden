package net.ludocrypt.the_garden.world;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.util.DimensionUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class PointOne {

	public static final Identifier IDENTIFIER = TheGarden.id("point_one");
	public static final Identifier SKY = IDENTIFIER;
	public static final RegistryKey<World> WORLD = DimensionUtil.getWorld(IDENTIFIER);
	public static final RegistryKey<DimensionType> DIMENSION_KEY = DimensionUtil.getDimensionType(IDENTIFIER);
	public static final RegistryKey<DimensionOptions> DIMENSION_OPTIONS = DimensionUtil.getDimensionOptions(IDENTIFIER);
	public static final RegistryKey<ChunkGeneratorSettings> CHUNK_GENERATOR_SETTINGS = RegistryKey.of(Registry.NOISE_SETTINGS_WORLDGEN, IDENTIFIER);

}
