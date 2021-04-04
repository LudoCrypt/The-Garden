package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

@Mixin(Block.class)
public class BlockMixin {

	@Inject(method = "hasRandomTicks", at = @At("HEAD"), cancellable = true)
	private void theGarden_hasTicks(BlockState state, CallbackInfoReturnable<Boolean> ci) {
		if (state.isOf(Blocks.CRYING_OBSIDIAN)) {
			ci.setReturnValue(true);
		}
	}

}
