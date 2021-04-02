package net.ludocrypt.the_garden;

import net.fabricmc.api.ModInitializer;
import net.ludocrypt.the_garden.compat.GardenCompat;
import net.ludocrypt.the_garden.config.GardenConfig;
import net.ludocrypt.the_garden.init.GardenBiomes;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.init.GardenBoats;
import net.ludocrypt.the_garden.init.GardenCarvers;
import net.ludocrypt.the_garden.init.GardenCommands;
import net.ludocrypt.the_garden.init.GardenFeatures;
import net.ludocrypt.the_garden.init.GardenGroups;
import net.ludocrypt.the_garden.init.GardenItems;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.ludocrypt.the_garden.init.GardenRecipes;
import net.ludocrypt.the_garden.init.GardenSurfaces;
import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.util.Identifier;

public class TheGarden implements ModInitializer {

	@Override
	public void onInitialize() {
		GardenParticles.init();
		GardenCommands.init();
		GardenFeatures.init();
		GardenSurfaces.init();
		GardenCarvers.init();
		GardenRecipes.init();
		GardenGroups.init();
		GardenConfig.init();
		GardenCompat.init();
		GardenBiomes.init();
		GardenBlocks.init();
		GardenItems.init();
		GardenBoats.init();
		PointOne.init();
		PointTwo.init();
	}

	public static Identifier id(String id) {
		return new Identifier("the_garden", id);
	}

}
