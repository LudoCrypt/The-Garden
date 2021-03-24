package net.ludocrypt.the_garden;

import net.fabricmc.api.ModInitializer;
import net.ludocrypt.the_garden.init.GardenBiomes;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.init.GardenBoats;
import net.ludocrypt.the_garden.init.GardenCarvers;
import net.ludocrypt.the_garden.init.GardenFeatures;
import net.ludocrypt.the_garden.init.GardenGroups;
import net.ludocrypt.the_garden.init.GardenItems;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.ludocrypt.the_garden.init.GardenSurfaces;
import net.minecraft.util.Identifier;

public class TheGarden implements ModInitializer {

	@Override
	public void onInitialize() {
		GardenParticles.init();
		GardenFeatures.init();
		GardenSurfaces.init();
		GardenCarvers.init();
		GardenGroups.init();
		GardenBiomes.init();
		GardenBlocks.init();
		GardenItems.init();
		GardenBoats.init();
	}

	public static Identifier id(String id) {
		return new Identifier("the_garden", id);
	}

}