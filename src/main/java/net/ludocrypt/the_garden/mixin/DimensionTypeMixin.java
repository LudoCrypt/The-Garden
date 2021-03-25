package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.serialization.Lifecycle;

import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {

	@Inject(method = "addRegistryDefaults", at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void theGarden_addRegistryDefaults(DynamicRegistryManager.Impl registryManager, CallbackInfoReturnable<DynamicRegistryManager.Impl> ci, MutableRegistry<DimensionType> mutableRegistry) {
		mutableRegistry.add(PointOne.DIMENSION_KEY, PointOne.DIMENSION_TYPE, Lifecycle.stable());
		mutableRegistry.add(PointTwo.DIMENSION_KEY, PointTwo.DIMENSION_TYPE, Lifecycle.stable());
	}

	@Inject(method = "createDefaultDimensionOptions", at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void theGarden_createDefaultDimensionOptions(Registry<DimensionType> dimensionRegistry, Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed, CallbackInfoReturnable<SimpleRegistry<DimensionOptions>> ci, SimpleRegistry<DimensionOptions> simpleRegistry) {
		simpleRegistry.add(PointOne.DIMENSION_OPTIONS, new DimensionOptions(() -> {
			return (DimensionType) dimensionRegistry.getOrThrow(PointOne.DIMENSION_KEY);
		}, PointOne.createGenerator(biomeRegistry, chunkGeneratorSettingsRegistry, seed)), Lifecycle.stable());
		simpleRegistry.add(PointTwo.DIMENSION_OPTIONS, new DimensionOptions(() -> {
			return (DimensionType) dimensionRegistry.getOrThrow(PointTwo.DIMENSION_KEY);
		}, PointTwo.createGenerator(biomeRegistry, chunkGeneratorSettingsRegistry, seed)), Lifecycle.stable());
	}

}
