package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.advancements.GardenAdvancement;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(HoeItem.class)
public class HoeItemMixin {

	@Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void theGarden_giveAdvancement(ItemUsageContext context, CallbackInfoReturnable<ActionResult> ci, World world, BlockPos blockPos, BlockState blockState) {
		if (blockState.isOf(GardenBlocks.PLAYDIRT_FARMLAND)) {
			GardenAdvancement.grantAdvancement(context.getPlayer(), TheGarden.id("point_one/cultivate_land"));
		}
	}

}
