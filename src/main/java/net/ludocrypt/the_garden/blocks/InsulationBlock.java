package net.ludocrypt.the_garden.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.util.TripplePair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class InsulationBlock extends Block {

	public final static TripplePair<Block, Integer, ParticleEffect> INSULATED_BLOCKS = TripplePair.newTripplePair();

	public InsulationBlock(Settings settings, int range, ParticleEffect effect) {
		super(settings);
		INSULATED_BLOCKS.put(this, range, effect);
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		insulate(world, pos, state, INSULATED_BLOCKS.getBFromA(state.getBlock()));
	}

	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		insulate(world, pos, state, INSULATED_BLOCKS.getBFromA(state.getBlock()));
		super.onBroken(world, pos, state);
	}

	public void insulate(WorldAccess world, BlockPos pos, BlockState state, int range) {
		BlockPos.iterate(pos.add(-range, -range, -range), pos.add(range, range, range)).forEach((blockPos) -> {
			if (world.getBlockState(blockPos).getBlock() instanceof RedstoneWireBlock) {
				world.updateNeighbors(blockPos, world.getBlockState(blockPos).getBlock());
			}
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
		OSBBlock.displayParticles(state, world, pos, random, INSULATED_BLOCKS.getCFromA(state.getBlock()), 3.5, 8.5, 0.3, 0.15);
	}

}
