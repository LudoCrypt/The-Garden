package net.ludocrypt.the_garden.init;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.world.decorator.TileFeatureDecorator;
import net.ludocrypt.the_garden.world.features.BuriedBoxFeature;
import net.ludocrypt.the_garden.world.features.ChurchparkEdgeFeature;
import net.ludocrypt.the_garden.world.features.CorkSpikeFeature;
import net.ludocrypt.the_garden.world.features.CorkStumpFeature;
import net.ludocrypt.the_garden.world.features.DeadTreeFeature;
import net.ludocrypt.the_garden.world.features.EllipsoidFeatureConfig;
import net.ludocrypt.the_garden.world.features.PuddleFeature;
import net.ludocrypt.the_garden.world.features.TileFeature;
import net.ludocrypt.the_garden.world.features.structures.ExtendedStructurePoolFeatureConfig;
import net.ludocrypt.the_garden.world.features.structures.PlaygroundStructure;
import net.ludocrypt.the_garden.world.features.structures.PointOneMineshaftStructure;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

// TODO: Check
public class GardenFeatures {

	private static final Map<Identifier, ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>> CONFIGURED_FEATURES = new HashMap<>();
	private static final Map<Identifier, Pair<StructureFeature<? extends FeatureConfig>, Function<FabricStructureBuilder<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>>, ? extends StructureFeature<? extends FeatureConfig>>>> STRUCTURE_FEATURES = new HashMap<>();
	private static final Map<Identifier, ConfiguredStructureFeature<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>>> CONFIGURED_STRUCTURE_FEATURES = new HashMap<>();
	private static final Map<Identifier, Decorator<? extends DecoratorConfig>> DECORATORS = new HashMap<>();
	private static final Map<Identifier, Feature<? extends FeatureConfig>> FEATURES = new HashMap<>();

	public static final Decorator<NopeDecoratorConfig> TILE_FEATURE_DECORATOR = add("tile_feature_decorator", new TileFeatureDecorator(NopeDecoratorConfig.CODEC));

	public static final StructureFeature<StructurePoolFeatureConfig> PLAYGROUND_STRUCTURE = add("playground", new PlaygroundStructure(StructurePoolFeatureConfig.CODEC, 0, true, true), (structure) -> {
		return structure.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(15, 5, 9624).adjustsSurface().register();
	});
	public static final StructureFeature<StructurePoolFeatureConfig> BIG_PLAYGROUND_STRUCTURE = add("big_playground", new PlaygroundStructure(ExtendedStructurePoolFeatureConfig.CODEC, 0, true, true), (structure) -> {
		return structure.step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(20, 10, 2489).adjustsSurface().register();
	});
	public static final StructureFeature<MineshaftFeatureConfig> DEAD_TREE_MINESHAFT_STRUCTURE = add("dead_tree_mineshaft", new PointOneMineshaftStructure(MineshaftFeatureConfig.CODEC), (structure) -> {
		return structure.step(GenerationStep.Feature.UNDERGROUND_STRUCTURES).defaultConfig(5, 2, 4658).adjustsSurface().register();
	});

	public static final ConfiguredStructureFeature<?, ?> PLAYGROUND = add("playground", PLAYGROUND_STRUCTURE.configure(new StructurePoolFeatureConfig(() -> PlaygroundStructure.STAIRCASES, 7)));
	public static final ConfiguredStructureFeature<?, ?> BIG_PLAYGROUND = add("big_playground", BIG_PLAYGROUND_STRUCTURE.configure(new StructurePoolFeatureConfig(() -> PlaygroundStructure.PLAYWALKS_TALL, 20)));
	public static final ConfiguredStructureFeature<?, ?> DEAD_TREE_MINESHAFT = add("dead_tree_mineshaft", DEAD_TREE_MINESHAFT_STRUCTURE.configure(new MineshaftFeatureConfig(0.001F, GardenEnum.DEAD_TREE_MINESHAFT)));

	public static final Feature<DefaultFeatureConfig> TILE_FEATURE = add("tile_feature", new TileFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<EllipsoidFeatureConfig> CORK_STUMP_FEATURE = add("cork_stump_feature", new CorkStumpFeature(EllipsoidFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> DEAD_TREE_FEATURE = add("dead_tree_feature", new DeadTreeFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> CHURCHPARK_EDGE_FEATURE = add("churchpark_edge_feature", new ChurchparkEdgeFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> PUDDLE_FEATURE = add("puddle_feature", new PuddleFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> BURIED_BOX_FEATURE = add("buried_box_feature", new BuriedBoxFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> CORK_SPIKE_FEATURE = add("cork_spike_feature", new CorkSpikeFeature(DefaultFeatureConfig.CODEC));

	public static final ConfiguredFeature<?, ?> POINT_ONE_TILES = add("point_one_tiles", TILE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(TILE_FEATURE_DECORATOR.configure(NopeDecoratorConfig.DEFAULT)));
	public static final ConfiguredFeature<?, ?> CORK_STUMP = add("cork_stump", CORK_STUMP_FEATURE.configure(new EllipsoidFeatureConfig(6, 2, 5, GardenBlocks.CORK.getDefaultState(), Lists.newArrayList(GardenBlocks.MULCH_BLOCK.getDefaultState(), GardenBlocks.MULCH_LAYER_BLOCK.getDefaultState()))).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.5F, 5))));
	public static final ConfiguredFeature<?, ?> POINT_TWO_TILES = add("point_two_tiles", TILE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(TILE_FEATURE_DECORATOR.configure(NopeDecoratorConfig.DEFAULT)));
	public static final ConfiguredFeature<?, ?> DEAD_TREE = add("dead_tree", DEAD_TREE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.3F, 3))));
	public static final ConfiguredFeature<?, ?> CHURCHPARK_EDGE = add("churchpark_edge", CHURCHPARK_EDGE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(TILE_FEATURE_DECORATOR.configure(NopeDecoratorConfig.DEFAULT)));
	public static final ConfiguredFeature<?, ?> PUDDLE = add("puddle", PUDDLE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.5F, 1))));
	public static final ConfiguredFeature<?, ?> BURIED_BOX = add("buried_box", BURIED_BOX_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.05F, 1))));
	public static final ConfiguredFeature<?, ?> CORK_SPIKE_SPREAD = add("cork_spike_spread", CORK_SPIKE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_MULTILAYER.configure(new CountConfig(1))));
	public static final ConfiguredFeature<?, ?> CORK_SPIKE_EXTRA = add("cork_spike_extra", CORK_SPIKE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.3F, 2))));

	public static final ConfiguredFeature<?, ?> COAL_ORE_POINT_ONE = add("coal_ore_point_one", Feature.ORE.configure(new OreFeatureConfig(new BlockMatchRuleTest(GardenBlocks.PLAYDIRT), GardenBlocks.PLAYDIRT_COAL_ORE.getDefaultState(), 10)).decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(20, 0, 40))).spreadHorizontally().repeat(20));

	private static <FC extends FeatureConfig, F extends Feature<FC>> ConfiguredFeature<FC, F> add(String name, ConfiguredFeature<FC, F> cf) {
		CONFIGURED_FEATURES.put(TheGarden.id(name), cf);
		return cf;
	}

	private static <FC extends FeatureConfig> StructureFeature<FC> add(String name, StructureFeature<FC> sf, Function<FabricStructureBuilder<? extends FeatureConfig, ? extends StructureFeature<? extends FeatureConfig>>, ? extends StructureFeature<? extends FeatureConfig>> fabricStructure) {
		STRUCTURE_FEATURES.put(TheGarden.id(name), Pair.of(sf, fabricStructure));
		return sf;
	}

	private static <FC extends FeatureConfig, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> add(String name, ConfiguredStructureFeature<FC, F> sf) {
		CONFIGURED_STRUCTURE_FEATURES.put(TheGarden.id(name), sf);
		return sf;
	}

	private static <DC extends DecoratorConfig> Decorator<DC> add(String name, Decorator<DC> d) {
		DECORATORS.put(TheGarden.id(name), d);
		return d;
	}

	private static <FC extends FeatureConfig> Feature<FC> add(String name, Feature<FC> f) {
		FEATURES.put(TheGarden.id(name), f);
		return f;
	}

	public static void init() {
		for (Identifier id : CONFIGURED_FEATURES.keySet()) {
			Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, CONFIGURED_FEATURES.get(id));
		}
		for (Identifier id : STRUCTURE_FEATURES.keySet()) {
			StructureFeature<? extends FeatureConfig> sf = STRUCTURE_FEATURES.get(id).getFirst();
			FabricStructureBuilder<? extends FeatureConfig, ?> builder = FabricStructureBuilder.create(id, sf);
			STRUCTURE_FEATURES.get(id).getSecond().apply(builder);
		}
		for (Identifier id : CONFIGURED_STRUCTURE_FEATURES.keySet()) {
			Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, id, CONFIGURED_STRUCTURE_FEATURES.get(id));
		}
		for (Identifier id : DECORATORS.keySet()) {
			Registry.register(Registry.DECORATOR, id, DECORATORS.get(id));
		}
		for (Identifier id : FEATURES.keySet()) {
			Registry.register(Registry.FEATURE, id, FEATURES.get(id));
		}
	}

}
