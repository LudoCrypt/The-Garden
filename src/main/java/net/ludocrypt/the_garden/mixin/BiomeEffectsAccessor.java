package net.ludocrypt.the_garden.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.biome.BiomeEffects;

@Mixin(BiomeEffects.class)
public interface BiomeEffectsAccessor {

	@Accessor("grassColor")
	public Optional<Integer> getOptionalGrassColor();

}
