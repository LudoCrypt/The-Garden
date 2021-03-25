package net.ludocrypt.the_garden.world.decorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;

public class TileFeatureDecorator extends Decorator<ChanceDecoratorConfig> {

	public TileFeatureDecorator(Codec<ChanceDecoratorConfig> codec) {
		super(codec);
	}

	@Override
	public Stream<BlockPos> getPositions(DecoratorContext context, Random random, ChanceDecoratorConfig config, BlockPos pos) {
		BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(pos.getX(), config.chance, pos.getZ());
		List<BlockPos> blockPosList = new ArrayList<BlockPos>();
		blockPosList.add(mutableBlockPos);
		return blockPosList.stream();
	}
}
