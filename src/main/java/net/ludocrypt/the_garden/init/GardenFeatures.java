package net.ludocrypt.the_garden.init;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.chocohead.mm.api.ClassTinkerers;
import com.mojang.datafixers.util.Pair;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.world.decorator.TileFeatureDecorator;
import net.ludocrypt.the_garden.world.features.BooleanFeatureConfig;
import net.ludocrypt.the_garden.world.features.BuriedBoxFeature;
import net.ludocrypt.the_garden.world.features.ChurchparkEdgeFeature;
import net.ludocrypt.the_garden.world.features.CorkSpikeFeature;
import net.ludocrypt.the_garden.world.features.CorkStumpFeature;
import net.ludocrypt.the_garden.world.features.CrackedObsidianPoolFeature;
import net.ludocrypt.the_garden.world.features.DeadTreeFeature;
import net.ludocrypt.the_garden.world.features.DirtPillarFeature;
import net.ludocrypt.the_garden.world.features.DirtSpikeFeature;
import net.ludocrypt.the_garden.world.features.EllipsoidFeatureConfig;
import net.ludocrypt.the_garden.world.features.IvoryMarrowFeature;
import net.ludocrypt.the_garden.world.features.IvoryTuskFeature;
import net.ludocrypt.the_garden.world.features.PseudoBaseFeature;
import net.ludocrypt.the_garden.world.features.PuddleFeature;
import net.ludocrypt.the_garden.world.features.TileFeature;
import net.ludocrypt.the_garden.world.features.TwistedVineFeature;
import net.ludocrypt.the_garden.world.features.VineFeature;
import net.ludocrypt.the_garden.world.features.structures.ExtendedStructurePoolFeatureConfig;
import net.ludocrypt.the_garden.world.features.structures.PlaygroundStructure;
import net.ludocrypt.the_garden.world.features.structures.PointOneMineshaftStructure;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeature;
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

	public static final MineshaftFeature.Type DEAD_TREE_MINESHAFT_TYPE = ClassTinkerers.getEnum(MineshaftFeature.Type.class, GardenEarlyRisers.DEAD_TREE_KEY);

	public static final Decorator<ChanceDecoratorConfig> TILE_FEATURE_DECORATOR = add("tile_feature_decorator", new TileFeatureDecorator(ChanceDecoratorConfig.CODEC));

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
	public static final ConfiguredStructureFeature<?, ?> DEAD_TREE_MINESHAFT = add("dead_tree_mineshaft", DEAD_TREE_MINESHAFT_STRUCTURE.configure(new MineshaftFeatureConfig(0.001F, DEAD_TREE_MINESHAFT_TYPE)));

	// Point One
	public static final Feature<DefaultFeatureConfig> TILE_FEATURE = add("tile_feature", new TileFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<EllipsoidFeatureConfig> CORK_STUMP_FEATURE = add("cork_stump_feature", new CorkStumpFeature(EllipsoidFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> DEAD_TREE_FEATURE = add("dead_tree_feature", new DeadTreeFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> CHURCHPARK_EDGE_FEATURE = add("churchpark_edge_feature", new ChurchparkEdgeFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> PUDDLE_FEATURE = add("puddle_feature", new PuddleFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> BURIED_BOX_FEATURE = add("buried_box_feature", new BuriedBoxFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> CORK_SPIKE_FEATURE = add("cork_spike_feature", new CorkSpikeFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> PSEUDO_BASE_FEATURE = add("pseudo_base_feature", new PseudoBaseFeature(DefaultFeatureConfig.CODEC));

	// Point Two
	public static final Feature<DefaultFeatureConfig> DIRT_SPIKE_FEATURE = add("dirt_spike_feature", new DirtSpikeFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<BooleanFeatureConfig> DIRT_PILLAR_FEATURE = add("dirt_pillar_feature", new DirtPillarFeature(BooleanFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> VINE_FEATURE = add("vine_feature", new VineFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> TWISTED_VINE_FEATURE = add("twisted_vine_feature", new TwistedVineFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> IVORY_TUSK_FEATURE = add("ivory_tusk_feature", new IvoryTuskFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> IVORY_MARROW_FEATURE = add("ivory_marrow_feature", new IvoryMarrowFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> CRACKED_OBSIDIAN_POOL_FEATURE = add("cracked_obsidian_pool_feature", new CrackedObsidianPoolFeature(DefaultFeatureConfig.CODEC));

	// Point One
	public static final ConfiguredFeature<?, ?> POINT_ONE_TILES = add("point_one_tiles", TILE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(TILE_FEATURE_DECORATOR.configure(new ChanceDecoratorConfig(255))));
	public static final ConfiguredFeature<?, ?> CORK_STUMP = add("cork_stump", CORK_STUMP_FEATURE.configure(new EllipsoidFeatureConfig(6, 2, 5, GardenBlocks.CORK.getDefaultState())).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.5F, 1))));
	public static final ConfiguredFeature<?, ?> POINT_TWO_TILES = add("point_two_tiles", TILE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(TILE_FEATURE_DECORATOR.configure(new ChanceDecoratorConfig(0))));
	public static final ConfiguredFeature<?, ?> DEAD_TREE = add("dead_tree", DEAD_TREE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.3F, 3))));
	public static final ConfiguredFeature<?, ?> CHURCHPARK_EDGE = add("churchpark_edge", CHURCHPARK_EDGE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(TILE_FEATURE_DECORATOR.configure(new ChanceDecoratorConfig(255))));
	public static final ConfiguredFeature<?, ?> PUDDLE = add("puddle", PUDDLE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.5F, 1))));
	public static final ConfiguredFeature<?, ?> BURIED_BOX = add("buried_box", BURIED_BOX_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.05F, 1))));
	public static final ConfiguredFeature<?, ?> CORK_SPIKE_SPREAD = add("cork_spike_spread", CORK_SPIKE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_MULTILAYER.configure(new CountConfig(3))));
	public static final ConfiguredFeature<?, ?> CORK_SPIKE_EXTRA = add("cork_spike_extra", CORK_SPIKE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.7F, 2))));
	public static final ConfiguredFeature<?, ?> GREEN_INSULATION_BALL = add("green_insulation_ball", CORK_STUMP_FEATURE.configure(new EllipsoidFeatureConfig(3, 2, 6, GardenBlocks.GREEN_INSULATION.getDefaultState())).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.8F, 1))));
	public static final ConfiguredFeature<?, ?> PINK_INSULATION_BALL = add("pink_insulation_ball", CORK_STUMP_FEATURE.configure(new EllipsoidFeatureConfig(3, 2, 6, GardenBlocks.PINK_INSULATION.getDefaultState())).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.3F, 1))));
	public static final ConfiguredFeature<?, ?> BROWN_INSULATION_BALL = add("brown_insulation_ball", CORK_STUMP_FEATURE.configure(new EllipsoidFeatureConfig(3, 2, 6, GardenBlocks.BROWN_INSULATION.getDefaultState())).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.5F, 1))));
	public static final ConfiguredFeature<?, ?> PSEUDO_BASE = add("pseudo_base", PSEUDO_BASE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.5F, 1))));

	public static final ConfiguredFeature<?, ?> COAL_ORE_POINT_ONE = add("coal_ore_point_one", Feature.ORE.configure(new OreFeatureConfig(new BlockMatchRuleTest(GardenBlocks.PLAYDIRT), GardenBlocks.PLAYDIRT_COAL_ORE.getDefaultState(), 10)).decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(20, 0, 40))).spreadHorizontally().repeat(20));

	// Point Two
	public static final ConfiguredFeature<?, ?> DIRT_SPIKE = add("dirt_spike", DIRT_SPIKE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.3F, 3))));
	public static final ConfiguredFeature<?, ?> VINED_DIRT_PILLAR = add("vined_dirt_pillar", DIRT_PILLAR_FEATURE.configure(BooleanFeatureConfig.TRUE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.3F, 2))));
	public static final ConfiguredFeature<?, ?> DIRT_PILLAR = add("dirt_pillar", DIRT_PILLAR_FEATURE.configure(BooleanFeatureConfig.FALSE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.3F, 2))));
	public static final ConfiguredFeature<?, ?> VINE = add("vine", VINE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.8F, 2))));
	public static final ConfiguredFeature<?, ?> TWISTED_VINE = add("twisted_vine", TWISTED_VINE_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.5F, 2))));
	public static final ConfiguredFeature<?, ?> IVORY_TUSK = add("ivory_tusk", IVORY_TUSK_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.3F, 1))));
	public static final ConfiguredFeature<?, ?> IVORY_MARROW = add("ivory_marrow", IVORY_MARROW_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.5F, 2))));
	public static final ConfiguredFeature<?, ?> CRACKED_OBSIDIAN_POOL = add("cracked_obsidian_point_two", CRACKED_OBSIDIAN_POOL_FEATURE.configure(DefaultFeatureConfig.INSTANCE).decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(215, 15, 230))).spreadHorizontally().repeat(6));
	public static final ConfiguredFeature<?, ?> IRON_ORE_POINT_TWO = add("iron_ore_point_two", Feature.ORE.configure(new OreFeatureConfig(new BlockMatchRuleTest(GardenBlocks.PLAYDIRT), GardenBlocks.PLAYDIRT_IRON_ORE.getDefaultState(), 8)).decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(215, 10, 250))).spreadHorizontally().repeat(15));

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
