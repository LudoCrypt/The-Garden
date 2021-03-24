package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.init.GardenEnum;
import net.minecraft.block.BlockState;
import net.minecraft.structure.MineshaftGenerator.MineshaftPart;
import net.minecraft.world.gen.feature.MineshaftFeature;

@Mixin(MineshaftPart.class)
public class MineshaftPartMixin {

	@Shadow
	protected MineshaftFeature.Type mineshaftType;

	@Inject(method = "getPlanksType", at = @At("HEAD"), cancellable = true)
	private void garden_deadTreePlanksType(CallbackInfoReturnable<BlockState> ci) {
		if (this.mineshaftType.equals(GardenEnum.DEAD_TREE_MINESHAFT)) {
			ci.setReturnValue(GardenBlocks.DEAD_TREE.planks.getDefaultState());
		}
	}

	@Inject(method = "getFenceType", at = @At("HEAD"), cancellable = true)
	private void garden_deadTreeFenceType(CallbackInfoReturnable<BlockState> ci) {
		if (this.mineshaftType.equals(GardenEnum.DEAD_TREE_MINESHAFT)) {
			ci.setReturnValue(GardenBlocks.DEAD_TREE.fence.getDefaultState());
		}
	}

}
