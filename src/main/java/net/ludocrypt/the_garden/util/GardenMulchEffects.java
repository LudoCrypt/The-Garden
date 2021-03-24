package net.ludocrypt.the_garden.util;

import java.util.HashMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.client.TheClientGarden;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.level.ColorResolver;

public class GardenMulchEffects {

	public static final HashMap<Identifier, Integer> REGISTERED_MULCH_COLORS = new HashMap<Identifier, Integer>();

	public static final Color defaultMulchColor = Color.colorOf(169, 114, 91);

	@Environment(EnvType.CLIENT)
	public static final ColorResolver MULCH_COLOR = (biome, d, e) -> {
		return REGISTERED_MULCH_COLORS.getOrDefault(TheClientGarden.client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome), Color.colorOf(biome.getFoliageColor()).sepia().getRGB());
	};

	@Environment(EnvType.CLIENT)
	public static int getMulchColor(BlockRenderView world, BlockPos pos) {
		return world.getColor(pos, MULCH_COLOR);
	}

}
