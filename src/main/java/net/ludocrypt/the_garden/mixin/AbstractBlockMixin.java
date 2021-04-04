package net.ludocrypt.the_garden.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

	@Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
	private void theGarden_tickCryingObsidian(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
		if (state.isOf(Blocks.CRYING_OBSIDIAN)) {
			if (world.getBlockState(pos.down()).isOf(Blocks.OBSIDIAN)) {
				world.setBlockState(pos.down(), GardenBlocks.CRACKED_OBSIDIAN.getDefaultState(), 2);
				world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

}
