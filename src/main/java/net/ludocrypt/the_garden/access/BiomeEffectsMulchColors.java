package net.ludocrypt.the_garden.access;

import java.util.Optional;

import net.ludocrypt.the_garden.util.GardenMulchEffects;
import net.minecraft.world.biome.BiomeEffects;

public interface BiomeEffectsMulchColors {

	public Optional<Integer> getMulchColor();

	public void setMulchColor(int color);

	public static Optional<Integer> getMulchColor(BiomeEffects effects) {
		return ((BiomeEffectsMulchColors) effects).getMulchColor();
	}

	public static int getOrDefaultMulchColor(BiomeEffects effects) {
		Optional<Integer> optional = ((BiomeEffectsMulchColors) effects).getMulchColor();
		return optional.isPresent() ? optional.get() : GardenMulchEffects.defaultMulchColor.getRGB();
	}

	public static void setMulchColor(BiomeEffects effects, int color) {
		((BiomeEffectsMulchColors) effects).setMulchColor(color);
	}

}
