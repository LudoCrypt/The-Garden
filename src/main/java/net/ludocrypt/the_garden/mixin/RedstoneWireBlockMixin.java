package net.ludocrypt.the_garden.mixin;

import java.util.Collections;
import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.the_garden.blocks.InsulationBlock;
import net.ludocrypt.the_garden.config.GardenConfigurations;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireBlockMixin {

	@Inject(method = "getReceivedRedstonePower", at = @At("RETURN"), cancellable = true)
	private void theGarden_insulateRedstone(World world, BlockPos pos, CallbackInfoReturnable<Integer> ci) {
		int power = ci.getReturnValueI();

		if (isInsulated(world, pos)) {
			power = Math.max(0, power - GardenConfigurations.getInstance().redstoneDampening);
		}

		ci.setReturnValue(power);
	}

	@Unique
	private static boolean isInsulated(WorldView world, BlockPos pos) {
		boolean insulated = false;
		int search = Collections.max(InsulationBlock.INSULATED_BLOCKS.getB_LIST());
		Iterator<BlockPos> i = BlockPos.iterate(pos.add(-search, -search, -search), pos.add(search, search, search)).iterator();

		while (i.hasNext()) {
			BlockPos blockPos = i.next();
			BlockState state = world.getBlockState(blockPos);

			if (InsulationBlock.INSULATED_BLOCKS.getA_LIST().contains(state.getBlock())) {
				insulated = blockPos.isWithinDistance(pos, InsulationBlock.INSULATED_BLOCKS.getAB_SIDE().get(state.getBlock()));
				if (insulated) {
					break;
				}
			}
		}

		return insulated;
	}

}
