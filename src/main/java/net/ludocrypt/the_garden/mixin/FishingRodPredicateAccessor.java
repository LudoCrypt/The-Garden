package net.ludocrypt.the_garden.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@Mixin(ModelPredicateProviderRegistry.class)
public interface FishingRodPredicateAccessor {
	@Invoker("method_27883(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/entity/LivingEntity;)F")
	public static float invokeMethod_27883(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
		throw new AssertionError();
	}
}
