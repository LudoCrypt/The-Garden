package net.ludocrypt.the_garden.init;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.ludocrypt.the_garden.TheGarden;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class GardenGroups {

	public static void init() {
		FabricItemGroupBuilder.create(TheGarden.id("items")).icon(() -> GardenBlocks.MULCH_BLOCK.asItem().getDefaultStack()).appendItems((stacks) -> {
			Registry.ITEM.stream().filter((item) -> {
				return Registry.ITEM.getId(item).getNamespace().equals("the_garden");
			}).forEach((item) -> stacks.add(new ItemStack(item)));
		}).build();
	}
}
