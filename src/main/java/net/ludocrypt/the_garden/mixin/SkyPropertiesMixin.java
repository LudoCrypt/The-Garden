package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
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

	@Shadow
	@Final
	private static Object2ObjectMap<Identifier, SkyProperties> BY_IDENTIFIER;

	static {
		BY_IDENTIFIER.put(PointOne.SKY, GardenSkies.pointOneSky);
		BY_IDENTIFIER.put(PointTwo.SKY, GardenSkies.pointTwoSky);
	}

}
