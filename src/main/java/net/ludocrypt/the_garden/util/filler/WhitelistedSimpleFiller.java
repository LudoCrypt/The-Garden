package net.ludocrypt.the_garden.util.filler;

import java.util.List;

import com.google.common.collect.Lists;
import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;

import net.minecraft.block.BlockState;
import net.minecraft.world.StructureWorldAccess;

public class WhitelistedSimpleFiller implements Filler {

	private final StructureWorldAccess world;
	private final BlockState state;
	private final int flags;
	private final List<BlockState> blockstates;

	public WhitelistedSimpleFiller(StructureWorldAccess world, BlockState state, int flags, List<BlockState> blockStates) {
		this.world = world;
		this.state = state;
		this.flags = flags;
		this.blockstates = blockStates;
	}

	public WhitelistedSimpleFiller(StructureWorldAccess world, BlockState state, List<BlockState> blockStates) {
		this(world, state, 3, blockStates);
	}

	public WhitelistedSimpleFiller(StructureWorldAccess world, BlockState state, BlockState... blockStates) {
		this(world, state, 3, Lists.newArrayList(blockStates));
	}

	public static WhitelistedSimpleFiller of(StructureWorldAccess world, BlockState state, int flags, BlockState... blockStates) {
		return new WhitelistedSimpleFiller(world, state, flags, Lists.newArrayList(blockStates));
	}

	public static WhitelistedSimpleFiller of(StructureWorldAccess world, BlockState state, BlockState... blockStates) {
		return new WhitelistedSimpleFiller(world, state, Lists.newArrayList(blockStates));
	}

	public static WhitelistedSimpleFiller of(StructureWorldAccess world, BlockState state, int flags, List<BlockState> blockStates) {
		return new WhitelistedSimpleFiller(world, state, flags, blockStates);
	}

	public static WhitelistedSimpleFiller of(StructureWorldAccess world, BlockState state, List<BlockState> blockStates) {
		return new WhitelistedSimpleFiller(world, state, blockStates);
	}

	@Override
	public void accept(Position position) {
		if (this.blockstates.contains(this.world.getBlockState(position.toBlockPos()))) {
			world.setBlockState(position.toBlockPos(), this.state, this.flags);
		}
	}

}
