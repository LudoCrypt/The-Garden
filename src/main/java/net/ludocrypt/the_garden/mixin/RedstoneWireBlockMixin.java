package net.ludocrypt.the_garden.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.the_garden.blocks.InsulationBlock;
import net.minecraft.block.Block;
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
			power = Math.max(0, power - 4);
		}

		ci.setReturnValue(power);
	}

	@Unique
	private static boolean isInsulated(WorldView world, BlockPos pos, int search, Block block) {
		Iterator<BlockPos> i = BlockPos.iterate(pos.add(-search, -search, -search), pos.add(search, search, search)).iterator();

		BlockPos blockPos;
		do {
			if (!i.hasNext()) {
				return false;
			}

			blockPos = i.next();
		} while (!(world.getBlockState(blockPos).isOf(block)));

		return true;
	}

	@Unique
	private static boolean isInsulated(WorldView world, BlockPos pos) {
		boolean insulated = false;

		for (Block block : InsulationBlock.INSULATED_BLOCKS.keySet()) {
			if (!insulated) {
				insulated = isInsulated(world, pos, InsulationBlock.INSULATED_BLOCKS.get(block).first, block);
			}
			if (insulated) {
				break;
			}
		}

		return insulated;
	}

}