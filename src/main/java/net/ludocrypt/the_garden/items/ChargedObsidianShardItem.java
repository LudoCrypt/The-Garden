package net.ludocrypt.the_garden.items;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChargedObsidianShardItem extends Item {

	public ChargedObsidianShardItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {

		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);

		if (state.getBlock() == GardenBlocks.MULCH_BLOCK) {
			BlockPattern pattern = BlockPatternBuilder.start().aisle("?mm?", "mmmm", "mmmm", "?mm?").where('m', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(GardenBlocks.MULCH_BLOCK))).where('?', CachedBlockPosition.matchesBlockState((blockState) -> {
				if (blockState.isOf(GardenBlocks.MULCH_BLOCK)) {
					return false;
				}
				return true;
			})).build();
			BlockPattern.Result result = pattern.searchAround(world, pos);
			if (result != null) {
				world.addParticle(ParticleTypes.SWEEP_ATTACK, result.getFrontTopLeft().getX() - 1, pos.getY() + 1.5, result.getFrontTopLeft().getZ() - 1, 0, 0, 0);
				context.getStack().decrement(1);
				if (world.isClient) {
					return ActionResult.SUCCESS;
				} else {
					BlockPos patternPos = result.getFrontTopLeft();
					if (world.getBlockState(patternPos.north()).getBlock() == GardenBlocks.MULCH_BLOCK && world.getBlockState(patternPos.west()).getBlock() == GardenBlocks.MULCH_BLOCK) {
						patternPos = patternPos.add(-2, 0, -2);
						createPortal(patternPos, world);
					}
					return ActionResult.CONSUME;
				}
			}
		}
		return ActionResult.PASS;
	}

	private void createPortal(BlockPos pos, World world) {
		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 2; ++j) {
				world.setBlockState(pos.add(i, 0, j), GardenBlocks.MULCH_PORTAL.getDefaultState(), 2);
			}
		}
		world.playSound(null, pos.add(1, 0, 1), SoundEvents.ENTITY_BLAZE_HURT, SoundCategory.BLOCKS, 1.0F, 0.5F);
		world.playSound(null, pos.add(1, 0, 1), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 0.875F);
	}

}
