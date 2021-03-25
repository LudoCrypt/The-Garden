package net.ludocrypt.the_garden.mixin;

import java.util.LinkedHashSet;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;

@Mixin(DimensionOptions.class)
public class DimensionOptionsMixin {

	@Shadow
	@Final
	private static LinkedHashSet<RegistryKey<DimensionOptions>> BASE_DIMENSIONS;

	static {
		BASE_DIMENSIONS.add(PointOne.DIMENSION_OPTIONS);
		BASE_DIMENSIONS.add(PointTwo.DIMENSION_OPTIONS);
	}

}
