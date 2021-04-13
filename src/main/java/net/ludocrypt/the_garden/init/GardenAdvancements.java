package net.ludocrypt.the_garden.init;

import java.util.HashMap;
import java.util.Map;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.advancements.GardenAdvancement;
import net.ludocrypt.the_garden.config.GardenConfigurations;
import net.ludocrypt.the_garden.config.GardenConfigurations.BiomeOptions;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class GardenAdvancements {

	public static final Map<Identifier, Advancement.Task> ADVANCEMENTS = new HashMap<Identifier, Advancement.Task>();

	public static final Advancement.Task EXPLORE_POINT_ONE = add("point_one/explore", GardenAdvancement.complexWithEXP(TheGarden.id("point_one/root"), new ItemStack(Items.COMPASS), "the_garden.advancements.point_one.explore", 500, (advancement) -> {
		BiomeOptions config = GardenConfigurations.getInstance().enabledBiomes;

		if (config.enablePointOne) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.POINT_ONE);
		}
		if (config.enablePseudoCorkwoodPlains) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.PSEUDO_CORKWOOD_PLAINS);
		}
		if (config.enableCorkwoodPlains) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.CORKWOOD_PLAINS);
		}
		if (config.enablePlaypen) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.PLAYPEN);
		}
		if (config.enableChurchpark) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.CHURCHPARK);
		}
		if (config.enableSkinnedHouseparts) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.SKINNED_HOUSEPARTS);
		}
	}, AdvancementFrame.CHALLENGE).getAdvancement());

	public static final Advancement.Task GET_INSULATION = add("point_one/get_insulation", GardenAdvancement.complex(TheGarden.id("point_one/get_dead_tree"), new ItemStack(GardenBlocks.PINK_INSULATION), "the_garden.advancements.point_one.get_insulation", (advancement) -> {
		GardenAdvancement.addItem(advancement, GardenBlocks.WHITE_INSULATION.asItem());
		GardenAdvancement.addItem(advancement, GardenBlocks.BROWN_INSULATION.asItem());
		GardenAdvancement.addItem(advancement, GardenBlocks.GREEN_INSULATION.asItem());
		GardenAdvancement.addItem(advancement, GardenBlocks.PINK_INSULATION.asItem());
		GardenAdvancement.addItem(advancement, GardenBlocks.OSB_BOARD.asItem());
	}, AdvancementFrame.GOAL).getAdvancement());

	public static final Advancement.Task EXPLORE_POINT_TWO = add("point_two/explore", GardenAdvancement.complexWithEXP(TheGarden.id("point_two/root"), new ItemStack(Items.COMPASS), "the_garden.advancements.point_two.explore", 500, (advancement) -> {
		BiomeOptions config = GardenConfigurations.getInstance().enabledBiomes;

		if (config.enablePointTwo) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.POINT_TWO);
		}
		if (config.enableGravityFalls) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.GRAVITY_FALLS);
		}
		if (config.enableIvoryShallows) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.IVORY_SHALLOWS);
		}
		if (config.enableHolicIsles) {
			GardenAdvancement.addBiome(advancement, GardenBiomes.HOLIC_ISLES);
		}
	}, AdvancementFrame.CHALLENGE).getAdvancement());

	private static Advancement.Task add(String id, Advancement.Task advancement) {
		ADVANCEMENTS.put(TheGarden.id(id), advancement);
		return advancement;
	}

}
