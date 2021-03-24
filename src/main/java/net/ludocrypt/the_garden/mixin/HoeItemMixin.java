package net.ludocrypt.the_garden.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;

@Mixin(HoeItem.class)
public class HoeItemMixin {

	@Shadow
	@Final
	protected static Map<Block, BlockState> TILLED_BLOCKS;

	static {
		TILLED_BLOCKS.put(GardenBlocks.PLAYDIRT, Blocks.FARMLAND.getDefaultState());
	}

}
