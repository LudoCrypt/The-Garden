package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.init.GardenSounds;
import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.MusicSound;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Shadow
	public ClientPlayerEntity player;

	@Shadow
	public ClientWorld world;

	@Environment(EnvType.CLIENT)
	@Inject(method = "getMusicType", at = @At("HEAD"), cancellable = true)
	private void theGarden_getMusicType(CallbackInfoReturnable<MusicSound> ci) {
		if (this.player != null) {
			if (this.world.getRegistryKey() == PointOne.WORLD) {
				ci.setReturnValue(this.world.getBiomeAccess().getBiome(this.player.getBlockPos()).getMusic().orElse(GardenSounds.POINT_ONE));
			} else if (this.world.getRegistryKey() == PointTwo.WORLD) {
				ci.setReturnValue(this.world.getBiomeAccess().getBiome(this.player.getBlockPos()).getMusic().orElse(GardenSounds.POINT_TWO));
			}
		}
	}
}
