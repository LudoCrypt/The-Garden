package net.ludocrypt.the_garden.init;

import java.util.LinkedHashMap;
import java.util.Map;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.recipe.WormedFishingRodRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GardenRecipes {

	private static final Map<Identifier, RecipeSerializer<?>> RECIPES = new LinkedHashMap<>();

	public static final SpecialRecipeSerializer<WormedFishingRodRecipe> WORMED_FISHING_ROD = add("crafting_special_wormed_fishing_rod", new SpecialRecipeSerializer<WormedFishingRodRecipe>(WormedFishingRodRecipe::new));

	public static void init() {
		for (Identifier id : RECIPES.keySet()) {
			Registry.register(Registry.RECIPE_SERIALIZER, id, RECIPES.get(id));
		}
	}

	private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S add(String id, S r) {
		Identifier realId = TheGarden.id(id);
		RECIPES.put(realId, r);
		return r;
	}

}
