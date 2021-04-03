package net.ludocrypt.the_garden.advancements;

import java.util.Iterator;
import java.util.function.Consumer;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.LocationArrivalCriterion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.Tag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class GardenAdvancement {

	private final Advancement.Task advancement;

	public GardenAdvancement(Identifier parent, AdvancementDisplay display, AdvancementRewards rewards, Consumer<Advancement.Task> add) {
		advancement = Advancement.Task.create().parent(parent).display(display).rewards(rewards);
		add.accept(advancement);
	}

	public static GardenAdvancement simple(Identifier parent, ItemStack item, String name, AdvancementFrame frame) {
		return simpleWithEXP(parent, item, name, 0, frame);
	}

	public static GardenAdvancement simpleWithEXP(Identifier parent, ItemStack item, String name, int exp, AdvancementFrame frame) {
		AdvancementDisplay display = new AdvancementDisplay(item, new TranslatableText(name + ".title"), new TranslatableText(name + ".description"), null, frame, true, true, false);
		return new GardenAdvancement(parent, display, AdvancementRewards.Builder.experience(exp).build(), GardenAdvancement::nothing);
	}

	public static GardenAdvancement complex(Identifier parent, ItemStack item, String name, Consumer<Advancement.Task> add, AdvancementFrame frame) {
		return complexWithEXP(parent, item, name, 0, add, frame);
	}

	public static GardenAdvancement complexWithEXP(Identifier parent, ItemStack item, String name, int exp, Consumer<Advancement.Task> add, AdvancementFrame frame) {
		AdvancementDisplay display = new AdvancementDisplay(item, new TranslatableText(name + ".title"), new TranslatableText(name + ".description"), null, frame, true, true, false);
		return new GardenAdvancement(parent, display, AdvancementRewards.Builder.experience(exp).build(), add);
	}

	public static void addBiome(Advancement.Task advancement, RegistryKey<Biome> biome) {
		advancement.criterion("find_" + biome.getValue().getNamespace() + "_" + biome.getValue().getPath(), new AdvancementCriterion(LocationArrivalCriterion.Conditions.create(LocationPredicate.biome(biome))));
	}

	public static void addItem(Advancement.Task advancement, Item item) {
		advancement.criterion("get_" + Registry.ITEM.getId(item).getNamespace() + "_" + Registry.ITEM.getId(item).getPath(), new AdvancementCriterion(InventoryChangedCriterion.Conditions.items(item)));
	}

	public static void addItem(Advancement.Task advancement, Tag<Item> tag, String id) {
		advancement.criterion("get_" + id, new AdvancementCriterion(addItemFromTag(tag)));
	}

	public static void addItem(Advancement.Task advancement, ItemPredicate item, String id) {
		advancement.criterion("get_" + id, new AdvancementCriterion(InventoryChangedCriterion.Conditions.items(item)));
	}

	private static InventoryChangedCriterion.Conditions addItemFromTag(Tag<Item> tag) {
		ItemPredicate itemPredicate = new ItemPredicate(tag, null, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY);
		return InventoryChangedCriterion.Conditions.items(itemPredicate);
	}

	public Advancement.Task getAdvancement() {
		return advancement;
	}

	private static void nothing(Advancement.Task advancement) {

	}

	public static void grantAdvancement(PlayerEntity player, Identifier id) {
		if (player instanceof ServerPlayerEntity) {
			Advancement adv = ((ServerPlayerEntity) player).server.getAdvancementLoader().get(id);
			AdvancementProgress advp = ((ServerPlayerEntity) player).getAdvancementTracker().getProgress(adv);
			if (!advp.isDone()) {
				Iterator<String> itr = advp.getUnobtainedCriteria().iterator();
				while (itr.hasNext()) {
					String crit = itr.next();
					((ServerPlayerEntity) player).getAdvancementTracker().grantCriterion(adv, crit);
				}
			}
		}
	}
}
