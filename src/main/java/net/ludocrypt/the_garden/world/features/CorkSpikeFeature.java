package net.ludocrypt.the_garden.world.features;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Quaternion;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.impl.Shapes;
import com.terraformersmc.terraform.shapes.impl.filler.SimpleFiller;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import com.terraformersmc.terraform.shapes.impl.validator.SafelistValidator;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.util.layer.BendLayer;
import net.ludocrypt.the_garden.util.layer.HelixBendLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class CorkSpikeFeature extends Feature<DefaultFeatureConfig> {

	public CorkSpikeFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

		if (random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {

			List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.CORK.getDefaultState());
			BlockPos.Mutable mut = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos).mutableCopy();

			while (!WHITELIST.contains(world.getBlockState(mut.down())) && mut.getY() > 0) {
				mut.move(Direction.DOWN);
			}

			BlockPos blockPos = mut.toImmutable();

			WHITELIST.add(Blocks.AIR.getDefaultState());
			WHITELIST.addAll(GardenBlocks.MULCH_LAYER_BLOCK.getStateManager().getStates());

			BlockState state = world.getBlockState(blockPos.down());

			if (WHITELIST.contains(state)) {

				double x = MathHelper.lerp(random.nextDouble(), -10, 10);
				double y = random.nextDouble() * 360;
				double z = MathHelper.lerp(random.nextDouble(), -10, 10);

				double y2 = random.nextDouble() * 360;

				double width = random.nextDouble() * 2.5 + 2;
				double height = random.nextDouble() * 5 + 10 + (width * 1.5);

				double arc = 70 + MathHelper.lerp(random.nextDouble(), -10, 10);

				double helixHeight = MathHelper.lerp(random.nextDouble(), 0.5, 2);
				double helixWidth = MathHelper.lerp(random.nextDouble(), 2, 3);

				double downY = -(height / 6);

				Direction.Axis axis = Direction.Axis.pickRandomAxis(random);

				Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

				/* Shape */
				shape.applyLayer(new AddLayer(
						/* Shape */
						Shapes.ellipticalPyramid(width, width, height)))
						/* Bend */
						.applyLayer(new BendLayer(arc, height, axis))
						/* Rotation */
						.applyLayer(new RotateLayer(Quaternion.of(x, y, z, true)))
						/* Bend */
						.applyLayer(new HelixBendLayer(height * helixHeight, helixWidth, Direction.Axis.Y))
						/* Rotation */
						.applyLayer(new RotateLayer(Quaternion.of(0, y2, 0, true)))
						/* Movement */
						.applyLayer(new TranslateLayer(Position.of(0, downY, 0)))
						/* Movement */
						.applyLayer(new TranslateLayer(Position.of(blockPos)))
						/* Placement */
						.validate(new SafelistValidator(world, WHITELIST), (validShape) -> {
							validShape.fill(new SimpleFiller(world, GardenBlocks.CORK.getDefaultState()));
						});

				return true;
			}

		}

		return false;
	}

}
