package net.ludocrypt.the_garden.util;

import java.util.Optional;

import net.ludocrypt.the_garden.access.BiomeEffectsMulchColors;
import net.minecraft.world.biome.BiomeEffects;

public class GardenBiomeEffects extends BiomeEffects.Builder {

	private Optional<Integer> mulchColor = Optional.empty();

	public GardenBiomeEffects mulchColor(int color) {
		mulchColor = Optional.of(color);
		return this;
	}

	@Override
	public BiomeEffects build() {
		BiomeEffects effects = super.build();
		BiomeEffectsMulchColors.setMulchColor(effects, mulchColor.isPresent() ? mulchColor.get() : GardenMulchEffects.defaultMulchColor.getRGB());
		return effects;
	}
}
