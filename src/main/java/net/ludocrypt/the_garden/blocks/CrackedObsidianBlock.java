package net.ludocrypt.the_garden.blocks;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.ludocrypt.the_garden.TheGarden;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class CrackedObsidianBlock extends Block {

	public CrackedObsidianBlock(Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getBlockState(pos.up()).getBlock() instanceof AbstractFireBlock) {
			world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
			world.setBlockState(pos, Blocks.AIR.getDefaultState());

			LootContext.Builder builder = (new LootContext.Builder(world));
			LootTable lootTable = world.getServer().getLootManager().getTable(TheGarden.id("gameplay/charged_obsidian"));
			List<ItemStack> list = lootTable.generateLoot(builder.build(LootContextTypes.EMPTY));
			Iterator<ItemStack> var7 = list.iterator();

			while (var7.hasNext()) {
				ItemStack itemStack = (ItemStack) var7.next();
				ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
				itemEntity.setVelocity(0.0D, 0.2D, 0.0D);
				world.spawnEntity(itemEntity);
			}

			world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 0.5F);
		}
	}

}
