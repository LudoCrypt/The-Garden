package net.ludocrypt.the_garden.blocks;

import java.util.Random;

import com.terraformersmc.terraform.wood.block.TerraformStairsBlock;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OSBStairBlock extends TerraformStairsBlock {

	public OSBStairBlock(Block base, Settings settings) {
		super(base, settings);
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		OSBBlock.displayParticles(state, world, pos, random, GardenParticles.SAWDUST, 2.5, 6.5, 0.25, 0.25);
	}
}
