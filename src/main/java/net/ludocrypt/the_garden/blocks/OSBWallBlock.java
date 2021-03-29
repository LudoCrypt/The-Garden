package net.ludocrypt.the_garden.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OSBWallBlock extends WallBlock {

	public OSBWallBlock(Settings settings) {
		super(settings);
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		OSBBlock.displayParticles(state, world, pos, random, GardenParticles.SAWDUST, 2.5, 6.5, 0.25, 0.25);
	}
}
