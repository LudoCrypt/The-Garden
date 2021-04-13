package net.ludocrypt.the_garden.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.access.BiomeEffectsMulchColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.level.ColorResolver;

public class GardenMulchEffects {

	public static final Color defaultMulchColor = Color.colorOf(169, 114, 91);

	@Environment(EnvType.CLIENT)
	public static final ColorResolver MULCH_COLOR = (biome, d, e) -> {
		return BiomeEffectsMulchColors.getOrDefaultMulchColor(biome.getEffects());
	};

	@Environment(EnvType.CLIENT)
	public static int getMulchColor(BlockRenderView world, BlockPos pos) {
		return world.getColor(pos, MULCH_COLOR);
	}

}
