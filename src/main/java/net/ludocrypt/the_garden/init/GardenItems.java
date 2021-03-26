package net.ludocrypt.the_garden.init;

import java.util.LinkedHashMap;
import java.util.Map;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.items.ChargedObsidianShardItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GardenItems {

	private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

	public static final Item OBSIDIAN_SHARD = add("obsidian_shard", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
	public static final Item CHARGED_OBSIDIAN_SHARD = add("charged_obsidian_shard", new ChargedObsidianShardItem(new FabricItemSettings().group(ItemGroup.MISC)));

	private static <I extends Item> I add(String name, I item) {
		ITEMS.put(TheGarden.id(name), item);
		return item;
	}

	public static void init() {
		for (Identifier id : ITEMS.keySet()) {
			Registry.register(Registry.ITEM, id, ITEMS.get(id));
		}
	}
}
