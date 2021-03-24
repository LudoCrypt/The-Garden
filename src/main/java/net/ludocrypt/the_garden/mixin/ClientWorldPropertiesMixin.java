package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.Properties.class)
public class ClientWorldPropertiesMixin {

	@Inject(method = "getSkyDarknessHeight", at = @At("HEAD"), cancellable = true)
	private void theGarden_getSkyDarknessHeight(CallbackInfoReturnable<Double> ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.world.getRegistryKey().equals(PointTwo.WORLD)) {
			ci.setReturnValue(-32.0D);
		}
	}

	@Inject(method = "getHorizonShadingRatio", at = @At("HEAD"), cancellable = true)
	private void theGarden_getHorizonShadingRatio(CallbackInfoReturnable<Double> ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.world.getRegistryKey().equals(PointTwo.WORLD)) {
			ci.setReturnValue(16.0D);
		}
	}

}
