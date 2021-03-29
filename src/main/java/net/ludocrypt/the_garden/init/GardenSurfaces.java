package net.ludocrypt.the_garden.init;

import java.util.HashMap;
import java.util.Map;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.world.surfaces.PatternSurfaceConfig;
import net.ludocrypt.the_garden.world.surfaces.PointOneSurfaceBuilder;
import net.ludocrypt.the_garden.world.surfaces.SkinnedHousepartsSurfaceBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class GardenSurfaces {

	private static final Map<Identifier, SurfaceBuilder<? extends SurfaceConfig>> SURFACE_BUILDERS = new HashMap<>();
	private static final Map<Identifier, ConfiguredSurfaceBuilder<? extends SurfaceConfig>> CONFIGURED_SURFACE_BUILDERS = new HashMap<>();

	public static final SurfaceBuilder<TernarySurfaceConfig> POINT_ONE_SURFACE_BUILDER = add("point_one_surface_builder", new PointOneSurfaceBuilder(TernarySurfaceConfig.CODEC));
	public static final SurfaceBuilder<PatternSurfaceConfig> SKINNED_HOUSEPARTS_SURFACE_BUILDER = add("skinned_houseparts_surface_builder", new SkinnedHousepartsSurfaceBuilder(PatternSurfaceConfig.CODEC));

	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> POINT_ONE = add("point_one", POINT_ONE_SURFACE_BUILDER.withConfig(new TernarySurfaceConfig(GardenBlocks.MULCH_BLOCK.getDefaultState(), GardenBlocks.MULCH_BLOCK.getDefaultState(), GardenBlocks.MULCH_BLOCK.getDefaultState())));
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CORKWOOD_PLAINS = add("corkwood_plains", POINT_ONE_SURFACE_BUILDER.withConfig(new TernarySurfaceConfig(GardenBlocks.CORK.getDefaultState(), GardenBlocks.CORK.getDefaultState(), GardenBlocks.CORK.getDefaultState())));
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CHURCHPARK = add("churchpark", POINT_ONE_SURFACE_BUILDER.withConfig(new TernarySurfaceConfig(GardenBlocks.PEA_GRAVEL.getDefaultState(), GardenBlocks.PEA_GRAVEL.getDefaultState(), GardenBlocks.PEA_GRAVEL.getDefaultState())));
	public static final ConfiguredSurfaceBuilder<PatternSurfaceConfig> SKINNED_HOUSEPARTS = add("skinned_houseparts", SKINNED_HOUSEPARTS_SURFACE_BUILDER.withConfig(PatternSurfaceConfig.of(GardenBlocks.OSB_BOARD.getDefaultState(), GardenBlocks.PINK_INSULATION.getDefaultState(), GardenBlocks.PINK_INSULATION.getDefaultState(), GardenBlocks.OSB_BOARD.getDefaultState(), GardenBlocks.PINK_INSULATION.getDefaultState(), GardenBlocks.PINK_INSULATION.getDefaultState(), GardenBlocks.PINK_INSULATION.getDefaultState(), GardenBlocks.OSB_BOARD.getDefaultState(), GardenBlocks.PINK_INSULATION.getDefaultState(), GardenBlocks.PINK_INSULATION.getDefaultState(), GardenBlocks.OSB_BOARD.getDefaultState(), GardenBlocks.PINK_INSULATION.getDefaultState())));

	private static <S extends SurfaceBuilder<? extends SurfaceConfig>> S add(String name, S s) {
		SURFACE_BUILDERS.put(TheGarden.id(name), s);
		return s;
	}

	private static <SC extends SurfaceConfig> ConfiguredSurfaceBuilder<SC> add(String name, ConfiguredSurfaceBuilder<SC> s) {
		CONFIGURED_SURFACE_BUILDERS.put(TheGarden.id(name), s);
		return s;
	}

	public static void init() {
		for (Identifier id : SURFACE_BUILDERS.keySet()) {
			Registry.register(Registry.SURFACE_BUILDER, id, SURFACE_BUILDERS.get(id));
		}
		for (Identifier id : CONFIGURED_SURFACE_BUILDERS.keySet()) {
			Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, id, CONFIGURED_SURFACE_BUILDERS.get(id));
		}
	}
}
