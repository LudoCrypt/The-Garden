package net.ludocrypt.the_garden.util.filler;

import java.util.List;

import com.google.common.collect.Lists;
import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;

import net.minecraft.block.BlockState;
import net.minecraft.world.StructureWorldAccess;

public class WhitelistedFiller implements Filler {

	private final StructureWorldAccess world;
	private final List<BlockState> blockStates;
	private final Filler other;

	public WhitelistedFiller(StructureWorldAccess world, Filler other, List<BlockState> blockStates) {
		this.world = world;
		this.blockStates = blockStates;
		this.other = other;
	}

	public WhitelistedFiller(StructureWorldAccess world, Filler other, BlockState... blockStates) {
		this(world, other, Lists.newArrayList(blockStates));
	}

	@Override
	public void accept(Position t) {
		if (this.blockStates.contains(this.world.getBlockState(t.toBlockPos()))) {
			other.accept(t);
		}
	}

}
