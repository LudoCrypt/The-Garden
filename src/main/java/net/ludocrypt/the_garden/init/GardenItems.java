package net.ludocrypt.the_garden.init;

import java.util.LinkedHashMap;
import java.util.Map;

import com.oroarmor.multi_item_lib.UniqueItemRegistry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.items.ChargedObsidianShardItem;
import net.ludocrypt.the_garden.items.WormRodItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GardenItems {

	private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

	public static final Item OBSIDIAN_SHARD = add("obsidian_shard", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
	public static final Item CHARGED_OBSIDIAN_SHARD = add("charged_obsidian_shard", new ChargedObsidianShardItem(new FabricItemSettings().group(ItemGroup.MISC)));
	public static final Item WORM = add("worm", new Item(new FabricItemSettings().food(new FoodComponent.Builder().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 0, false, false), 0.1F).hunger(2).saturationModifier(6.35F).snack().build()).group(ItemGroup.FOOD)));
	public static final Item WORMED_FISHING_ROD = add("wormed_fishing_rod", new WormRodItem(new FabricItemSettings().maxDamage(64).group(ItemGroup.TOOLS)));

	public static final Item IVORY_SHARD = add("ivory_shard", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
	public static final Item IVORY_MARROW = add("ivory_marrow", new Item(new FabricItemSettings().food(new FoodComponent.Builder().alwaysEdible().hunger(5).saturationModifier(8.5F).snack().build()).group(ItemGroup.FOOD)));

	private static <I extends Item> I add(String name, I item) {
		ITEMS.put(TheGarden.id(name), item);
		return item;
	}

	public static void init() {
		for (Identifier id : ITEMS.keySet()) {
			Registry.register(Registry.ITEM, id, ITEMS.get(id));
		}
		UniqueItemRegistry.FISHING_ROD.addItemToRegistry(WORMED_FISHING_ROD);
	}
}
