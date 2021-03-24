package net.ludocrypt.the_garden.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CryingObsidianBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

@Mixin(value = CryingObsidianBlock.class, priority = 246)
public abstract class CryingObsidianBlockMixin extends Block {

	public CryingObsidianBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN) {
			world.setBlockState(pos.down(), GardenBlocks.CRACKED_OBSIDIAN.getDefaultState(), 2);
			world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
		super.randomTick(state, world, pos, random);
	}

}
