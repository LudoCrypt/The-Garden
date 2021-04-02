package net.ludocrypt.the_garden.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.access.ItemStackCopy;
import net.ludocrypt.the_garden.init.GardenItems;
import net.ludocrypt.the_garden.init.GardenRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class WormedFishingRodRecipe extends SpecialCraftingRecipe {

	public WormedFishingRodRecipe(Identifier identifier) {
		super(identifier);
	}

	public boolean matches(CraftingInventory craftingInventory, World world) {
		List<ItemStack> list = Lists.newArrayList();

		for (int i = 0; i < craftingInventory.size(); ++i) {
			if (!craftingInventory.getStack(i).isEmpty()) {
				list.add(craftingInventory.getStack(i));
			}
		}

		if (list.size() == 2) {
			ItemStack pseudoWorm = list.get(0);
			ItemStack pseudoFishingRod = list.get(1);
			if ((pseudoWorm.getItem().equals(GardenItems.WORM) && pseudoFishingRod.getItem().equals(Items.FISHING_ROD)) || (pseudoFishingRod.getItem().equals(GardenItems.WORM) && pseudoWorm.getItem().equals(Items.FISHING_ROD))) {
				return true;
			}
		}

		return false;
	}

	public ItemStack craft(CraftingInventory craftingInventory) {
		List<ItemStack> list = Lists.newArrayList();

		for (int i = 0; i < craftingInventory.size(); ++i) {
			if (!craftingInventory.getStack(i).isEmpty()) {
				list.add(craftingInventory.getStack(i));
			}
		}

		if (list.size() == 2) {
			ItemStack pseudoWorm = list.get(0);
			ItemStack pseudoFishingRod = list.get(1);
			if (pseudoFishingRod.getItem().equals(Items.FISHING_ROD)) {
				return ItemStackCopy.copy(pseudoFishingRod, GardenItems.WORMED_FISHING_ROD);
			} else if (pseudoWorm.getItem().equals(Items.FISHING_ROD)) {
				return ItemStackCopy.copy(pseudoWorm, GardenItems.WORMED_FISHING_ROD);
			}
		}

		return ItemStack.EMPTY;
	}

	@Environment(EnvType.CLIENT)
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}

	public RecipeSerializer<?> getSerializer() {
		return GardenRecipes.WORMED_FISHING_ROD;
	}
}
