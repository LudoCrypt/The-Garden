package net.ludocrypt.the_garden.world.decorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;

public class TileFeatureDecorator extends Decorator<NopeDecoratorConfig> {

	public TileFeatureDecorator(Codec<NopeDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public Stream<BlockPos> getPositions(DecoratorContext context, Random random, NopeDecoratorConfig config, BlockPos pos) {
		BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(pos.getX(), 0, pos.getZ());
		List<BlockPos> blockPosList = new ArrayList<BlockPos>();
		blockPosList.add(mutableBlockPos);
		return blockPosList.stream();
	}
}
