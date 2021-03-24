package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.client.sky.GardenSkies;
import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(SkyProperties.class)
public class SkyPropertiesMixin {

	@Inject(method = "method_29092(Lit/unimi/dsi/fastutil/objects/Object2ObjectArrayMap;)V", at = @At("HEAD"))
	private static void theGarden_addSkyProperty(Object2ObjectArrayMap<Identifier, SkyProperties> map, CallbackInfo ci) {
		map.put(PointOne.SKY, GardenSkies.pointOneSky);
		map.put(PointTwo.SKY, GardenSkies.pointTwoSky);
	}

}
