package net.ludocrypt.the_garden.util.filler;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;

import net.minecraft.block.BlockState;
import net.minecraft.world.StructureWorldAccess;

public class WhitelistedRandomSimpleFiller implements Filler {

	private final StructureWorldAccess world;
	private final BlockState state;
	private final int flags;
	private final Random random;
	private final float probability;
	private final List<BlockState> blockstates;

	public WhitelistedRandomSimpleFiller(StructureWorldAccess world, BlockState state, int flags, Random random, float probability, List<BlockState> blockStates) {
		this.world = world;
		this.state = state;
		this.flags = flags;
		this.random = random;
		this.probability = probability;
		this.blockstates = blockStates;
	}

	public WhitelistedRandomSimpleFiller(StructureWorldAccess world, BlockState state, Random random, float probability, List<BlockState> blockStates) {
		this(world, state, 3, random, probability, blockStates);
	}

	public WhitelistedRandomSimpleFiller(StructureWorldAccess world, BlockState state, Random random, float probability, BlockState... blockStates) {
		this(world, state, 3, random, probability, Lists.newArrayList(blockStates));
	}

	public static WhitelistedRandomSimpleFiller of(StructureWorldAccess world, BlockState state, int flags, Random random, float probability, BlockState... blockStates) {
		return new WhitelistedRandomSimpleFiller(world, state, flags, random, probability, Lists.newArrayList(blockStates));
	}

	public static WhitelistedRandomSimpleFiller of(StructureWorldAccess world, BlockState state, Random random, float probability, BlockState... blockStates) {
		return new WhitelistedRandomSimpleFiller(world, state, random, probability, Lists.newArrayList(blockStates));
	}

	public static WhitelistedRandomSimpleFiller of(StructureWorldAccess world, BlockState state, int flags, Random random, float probability, List<BlockState> blockStates) {
		return new WhitelistedRandomSimpleFiller(world, state, flags, random, probability, blockStates);
	}

	public static WhitelistedRandomSimpleFiller of(StructureWorldAccess world, BlockState state, Random random, float probability, List<BlockState> blockStates) {
		return new WhitelistedRandomSimpleFiller(world, state, random, probability, blockStates);
	}

	@Override
	public void accept(Position position) {
		if (this.blockstates.contains(this.world.getBlockState(position.toBlockPos()))) {
			if (this.random.nextFloat() < this.probability) {
				world.setBlockState(position.toBlockPos(), this.state, this.flags);
			}
		}
	}

}
