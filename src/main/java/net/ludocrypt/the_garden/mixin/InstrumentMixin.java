package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.Instrument;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(Instrument.class)
public class InstrumentMixin {

	@Inject(method = "fromBlockState", at = @At("HEAD"), cancellable = true)
	private static void theGarden_myNoteBlockSounds(BlockState state, CallbackInfoReturnable<Instrument> ci) {
		Identifier id = Registry.BLOCK.getId(state.getBlock());
		if (state.isOf(GardenBlocks.IVORY_MARROW_BLOCK)) {
			ci.setReturnValue(Instrument.FLUTE);
		} else if (id.toString().contains("ivory") && id.getNamespace().equals("the_garden")) {
			ci.setReturnValue(Instrument.XYLOPHONE);
		}
	}

}
