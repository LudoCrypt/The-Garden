package net.ludocrypt.the_garden.world.features;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.shapes.api.Filler;
import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Quaternion;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.impl.Shapes;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import com.terraformersmc.terraform.shapes.impl.validator.SafelistValidator;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class PuddleFeature extends Feature<DefaultFeatureConfig> {

	public PuddleFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

		List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.MULCH_BLOCK.getDefaultState(), GardenBlocks.CORK.getDefaultState(), GardenBlocks.PEA_GRAVEL.getDefaultState(), Blocks.WATER.getDefaultState(), GardenBlocks.PLAYDIRT.getDefaultState());

		pos = world.getTopPosition(Type.WORLD_SURFACE_WG, pos);

		if (world.getBlockState(pos).isOf(GardenBlocks.MULCH_LAYER_BLOCK)) {
			pos = pos.down();
		}

		int count = random.nextInt(6);

		for (int i = 0; i < count; i++) {

			BlockPos puddle = pos;

			puddle = i >= 1 ? puddle.add(random.nextDouble() * 20 - 10, random.nextDouble() * puddle.getY() - (puddle.getY() / 2), random.nextDouble() * 20 - 10) : pos;

			double x = MathHelper.lerp(random.nextDouble(), -10, 10);
			double y = random.nextDouble() * 360;
			double z = MathHelper.lerp(random.nextDouble(), -10, 10);

			double width = random.nextDouble() * 4 + 3;
			double length = random.nextDouble() * 4 + 3;
			double height = random.nextDouble() * 3 + 1;

			double downY = -(height / 2);

			Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

			/* Shape */
			shape.applyLayer(new AddLayer(
					/* Shape */
					Shapes.ellipsoid(width, length, height)))
					/* Rotation */
					.applyLayer(new RotateLayer(Quaternion.of(x, y, z, true)))
					/* Movement */
					.applyLayer(new TranslateLayer(Position.of(0, downY, 0)))
					/* Movement */
					.applyLayer(new TranslateLayer(Position.of(puddle)))
					/* Placement */
					.validate(new SafelistValidator(world, WHITELIST), (validShape) -> {
						validShape.fill(new PuddleFiller(world, Blocks.WATER.getDefaultState()));
					});
		}

		return true;
	}

	private static class PuddleFiller implements Filler {

		private final StructureWorldAccess world;
		private final BlockState state;
		private final int flags;

		private PuddleFiller(StructureWorldAccess world, BlockState state, int flags) {
			this.world = world;
			this.state = state;
			this.flags = flags;
		}

		private PuddleFiller(StructureWorldAccess world, BlockState state) {
			this(world, state, 3);
		}

		@Override
		public void accept(Position position) {
			if (!world.isAir(position.toBlockPos()) && !world.isAir(position.toBlockPos().north()) && !world.isAir(position.toBlockPos().east()) && !world.isAir(position.toBlockPos().south()) && !world.isAir(position.toBlockPos().west())) {
				world.setBlockState(position.toBlockPos(), this.state, this.flags);
			}
		}
	}

}
