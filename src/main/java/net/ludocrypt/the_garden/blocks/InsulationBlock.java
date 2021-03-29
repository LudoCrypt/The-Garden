package net.ludocrypt.the_garden.blocks;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;
import com.ibm.icu.impl.Pair;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class InsulationBlock extends Block {

	public final static Map<Block, Pair<Integer, ParticleEffect>> INSULATED_BLOCKS = Maps.newHashMap();

	public InsulationBlock(Settings settings, int range, ParticleEffect effect) {
		super(settings);
		INSULATED_BLOCKS.put(this, Pair.of(range, effect));
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		insulate(world, pos, state);
	}

	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		insulate(world, pos, state);
		super.onBroken(world, pos, state);
	}

	public void insulate(WorldAccess world, BlockPos pos, BlockState state) {
		int range = INSULATED_BLOCKS.get(state.getBlock()).first;
		BlockPos.iterate(pos.add(-range, -range, -range), pos.add(range, range, range)).forEach((blockPos) -> {
			world.updateNeighbors(blockPos, world.getBlockState(blockPos).getBlock());
		});
	}

	@Override
	public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
		if (world.getBlockState(pos.down()).getBlock() instanceof InsulationBlock) {
			entity.handleFallDamage(distance, 0.2F);
		} else {
			entity.handleFallDamage(distance, 0.7F);
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		OSBBlock.displayParticles(state, world, pos, random, INSULATED_BLOCKS.get(state.getBlock()).second, 3.5, 8.5, 0.3, 0.15);
	}

}
