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

		System.out.println(context.getStack().hasTag() ? context.getStack().getTag().toString() : "");

		if (state.getBlock() == GardenBlocks.MULCH_BLOCK) {
			BlockPattern pattern = BlockPatternBuilder.start().aisle("?mm?", "mmmm", "mmmm", "?mm?").where('m', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(GardenBlocks.MULCH_BLOCK))).where('?', CachedBlockPosition.matchesBlockState((blockState) -> {
				if (blockState.isOf(GardenBlocks.MULCH_BLOCK)) {
					return false;
				}
				return true;
			})).build();
			BlockPattern.Result result = pattern.searchAround(world, pos);
			if (result != null) {
				world.addParticle(ParticleTypes.SWEEP_ATTACK, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0, 0, 0);
				context.getStack().decrement(1);
				if (world.isClient) {
					return ActionResult.SUCCESS;
				} else {
					BlockPos patternPos = result.getFrontTopLeft();

					if (world.getBlockState(patternPos.north()).getBlock() == GardenBlocks.MULCH_BLOCK && world.getBlockState(patternPos.west()).getBlock() == GardenBlocks.MULCH_BLOCK) {

						patternPos = patternPos.add(-2, 0, -2);

						for (int i = 0; i < 2; ++i) {
							for (int j = 0; j < 2; ++j) {
								world.setBlockState(patternPos.add(i, 0, j), GardenBlocks.MULCH_PORTAL.getDefaultState(), 2);
							}
						}

						world.syncGlobalEvent(648572, patternPos, 0);
					}
					return ActionResult.CONSUME;
				}
			}
		}

		return ActionResult.PASS;
	}

}
