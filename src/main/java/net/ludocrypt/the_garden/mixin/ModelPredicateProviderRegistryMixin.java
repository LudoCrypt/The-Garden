package net.ludocrypt.the_garden.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.init.GardenItems;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(ModelPredicateProviderRegistry.class)
public class ModelPredicateProviderRegistryMixin {

	@Shadow
	@Final
	private static Map<Item, Map<Identifier, ModelPredicateProvider>> ITEM_SPECIFIC;

	static {
		ITEM_SPECIFIC.put(GardenItems.WORMED_FISHING_ROD, ITEM_SPECIFIC.get(Items.FISHING_ROD));
	}

}
