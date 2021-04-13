package net.ludocrypt.the_garden.mixin;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

@Mixin(BuiltinRegistries.class)
public interface BuiltinRegistriesAccessor {

	@Invoker("addRegistry")
	public static <T> Registry<T> invokeAddRegistry(RegistryKey<? extends Registry<T>> registryRef, Supplier<T> defaultValueSupplier) {
		throw new UnsupportedOperationException();
	}

}
