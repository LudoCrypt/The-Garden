package net.ludocrypt.the_garden.util.filler;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;

import net.minecraft.block.BlockState;
import net.minecraft.world.ModifiableWorld;

public class ListFiller implements Filler {

	private final ModifiableWorld world;
	private final int flags;
	private final Random random;
	private final List<BlockState> list;

	public ListFiller(ModifiableWorld world, int flags, Random random, List<BlockState> list) {
		this.world = world;
		this.flags = flags;
		this.random = random;
		this.list = list;
	}

	public ListFiller(ModifiableWorld world, Random random, List<BlockState> list) {
		this(world, 3, random, list);
	}

	public ListFiller(ModifiableWorld world, Random random, BlockState... list) {
		this(world, 3, random, Lists.newArrayList(list));
	}

	public static ListFiller of(ModifiableWorld world, int flags, Random random, List<BlockState> list) {
		return new ListFiller(world, flags, random, list);
	}

	public static ListFiller of(ModifiableWorld world, Random random, List<BlockState> list) {
		return new ListFiller(world, 3, random, list);
	}

	@Override
	public void accept(Position position) {
		if (!this.list.isEmpty()) {
			world.setBlockState(position.toBlockPos(), list.get(random.nextInt(list.size())), this.flags);
		}
	}
}
