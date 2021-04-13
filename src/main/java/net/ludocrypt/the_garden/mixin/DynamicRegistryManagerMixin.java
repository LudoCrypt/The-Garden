package net.ludocrypt.the_garden.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;

import net.ludocrypt.the_garden.init.GardenGeneration;
import net.ludocrypt.the_garden.util.BiomeNoisePairRegistry;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

@Mixin(DynamicRegistryManager.class)
public abstract class DynamicRegistryManagerMixin {

	@Inject(method = "method_30531()Lcom/google/common/collect/ImmutableMap;", at = @At(value = "RETURN", shift = At.Shift.BEFORE, ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private static void theGarden_dynamicRegistries(CallbackInfoReturnable<Map<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>>> ci, Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> builder) {
		register(builder, GardenGeneration.POINT_ONE_BIOMES_KEY, BiomeNoisePairRegistry.CODEC);
		register(builder, GardenGeneration.POINT_TWO_BIOMES_KEY, BiomeNoisePairRegistry.CODEC);
	}

	@Shadow
	private static <E> void register(Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> infosBuilder, RegistryKey<? extends Registry<E>> registryRef, Codec<E> entryCodec) {
		throw new UnsupportedOperationException();
	}

}
