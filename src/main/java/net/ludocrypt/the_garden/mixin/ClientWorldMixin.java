package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.util.GardenMulchEffects;
import net.minecraft.client.world.BiomeColorCache;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.level.ColorResolver;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public class ClientWorldMixin {

	@Inject(method = "method_23778(Lit/unimi/dsi/fastutil/objects/Object2ObjectArrayMap;)V", at = @At("HEAD"))
	private static void theGarden_addColorProvider(Object2ObjectArrayMap<ColorResolver, BiomeColorCache> map, CallbackInfo ci) {
		map.put(GardenMulchEffects.MULCH_COLOR, new BiomeColorCache());
	}

}
