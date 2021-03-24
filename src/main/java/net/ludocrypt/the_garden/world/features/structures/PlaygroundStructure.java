package net.ludocrypt.the_garden.world.features.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import net.ludocrypt.the_garden.TheGarden;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class PlaygroundStructure extends JigsawFeature {

	public PlaygroundStructure(Codec<StructurePoolFeatureConfig> codec, int structureStartY, boolean bl, boolean surface) {
		super(codec, structureStartY, bl, surface);
	}

	public static final StructurePool STAIRCASES = StructurePools.register(
			new StructurePool(TheGarden.id("playground/staircases"),
					new Identifier("empty"), ImmutableList.of(
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/staircase_down_wide"), 1),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/staircase_down"), 2)
							), StructurePool.Projection.RIGID));
	
	public static final StructurePool PLAYWALKS = StructurePools.register(
			new StructurePool(TheGarden.id("playground/playwalks"),
					new Identifier("empty"), ImmutableList.of(
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/playwalks/playwalk_one"), 1),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/playwalks/playwalk_two"), 1),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/playwalks/playwalk_three"), 1)
							), StructurePool.Projection.RIGID));
	
	public static final StructurePool PLAYWALKS_TALL = StructurePools.register(
			new StructurePool(TheGarden.id("playground/playwalks_tall"),
					new Identifier("empty"), ImmutableList.of(
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/playwalks_tall/playwalk_one_tall"), 1),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/playwalks_tall/playwalk_two_tall"), 1),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/playwalks_tall/playwalk_three_tall"), 1)
							), StructurePool.Projection.RIGID));
	
	public static final StructurePool ADDITION = StructurePools.register(
			new StructurePool(TheGarden.id("playground/additions"),
					new Identifier("empty"), ImmutableList.of(
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/air_line"), 12),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/walkway_one_short"), 6),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/walkway_two_short"), 6),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/slide_one_short"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/slide_two_short"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/slide_three_short"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/slide_four_short"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/staircase_down"), 3),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/staircase_down_wide"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/staircase_up"), 3),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/staircase_up_wide"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/monkey_bars"), 10)
							), StructurePool.Projection.RIGID));
	
	public static final StructurePool ADDITION_TALL = StructurePools.register(
			new StructurePool(TheGarden.id("playground/additions_tall"),
					new Identifier("empty"), ImmutableList.of(
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/air_line_tall"), 12),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/walkway_one_tall"), 6),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/walkway_two_tall"), 6),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/slide_one_tall"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/slide_two_tall"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/slide_three_tall"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/slide_four_tall"), 2),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/staircase_up"), 3),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/addition/staircase_up_wide"), 2)
							), StructurePool.Projection.RIGID));
	
	public static final StructurePool FIREPOLES = StructurePools.register(
			new StructurePool(TheGarden.id("playground/firepoles"),
					new Identifier("empty"), ImmutableList.of(
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/firepoles/fireman_pole_short"), 1),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/firepoles/fireman_pole_short_air"), 3)
							), StructurePool.Projection.RIGID));
	
	public static final StructurePool FIREPOLES_TALL = StructurePools.register(
			new StructurePool(TheGarden.id("playground/firepoles_tall"),
					new Identifier("empty"), ImmutableList.of(
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/firepoles/fireman_pole_tall"), 1),
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/firepoles/fireman_pole_tall_air"), 3)
							), StructurePool.Projection.RIGID));
	
	public static final StructurePool PATHWAYS = StructurePools.register(
			new StructurePool(TheGarden.id("playground/pathways"),
					new Identifier("empty"), ImmutableList.of(
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/additions/walkway_two_short"), 1)
							), StructurePool.Projection.RIGID));
	
	public static final StructurePool PATHWAYS_TALL = StructurePools.register(
			new StructurePool(TheGarden.id("playground/pathways_tall"),
					new Identifier("empty"), ImmutableList.of(
								Pair.of(StructurePoolElement.method_30434("the_garden:playground/additions/walkway_two_tall"), 1)
							), StructurePool.Projection.RIGID));

	public static StructureProcessorList register(StructureProcessorList li, String name) {
		BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, TheGarden.id(name), li);
		return li;
	}

}
