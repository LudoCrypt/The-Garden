package net.ludocrypt.the_garden.world.features;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Quaternion;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.impl.Shapes;
import com.terraformersmc.terraform.shapes.impl.filler.RandomSimpleFiller;
import com.terraformersmc.terraform.shapes.impl.filler.SimpleFiller;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import com.terraformersmc.terraform.shapes.impl.validator.SafelistValidator;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.util.filler.WhitelistedFiller;
import net.ludocrypt.the_garden.util.layer.HelixBendLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class DirtPillarFeature extends Feature<BooleanFeatureConfig> {

	public DirtPillarFeature(Codec<BooleanFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, BooleanFeatureConfig config) {

		if (random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {

			List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.PLAYDIRT.getDefaultState(), GardenBlocks.DEAD_TREE.leaves.getDefaultState().with(LeavesBlock.PERSISTENT, true), Blocks.AIR.getDefaultState());
			List<BlockState> VALIDATION = WHITELIST;
			VALIDATION.addAll(GardenBlocks.TILE.getStateManager().getStates());

			BlockPos blockPos = new BlockPos(pos.getX(), 0, pos.getZ());
			BlockPos.Mutable mut = blockPos.mutableCopy();

			int height = 8;

			while (world.isAir(mut.up()) && mut.getY() < 255) {
				height++;
				mut.move(Direction.UP);
			}

			double finalHeight = height;

			double x = MathHelper.lerp(random.nextDouble(), -15, 15);
			double y = random.nextDouble() * 360;
			double z = MathHelper.lerp(random.nextDouble(), -15, 15);

			double width = random.nextDouble() * 2 + 3;
			double length = random.nextDouble() * 2 + 3;

			double pillarHeight = (finalHeight / 2) + 8;
			double pillarMovement = finalHeight - 4;

			double topY = random.nextDouble() * 360;

			Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

			/* Shape */
			shape.applyLayer(new AddLayer(
					/* Shape */
					Shapes.ellipticalPyramid(width, length, pillarHeight)))
					/* Shape */
					.applyLayer(new AddLayer(
							/* Shape */
							Shapes.ellipticalPyramid(width, length, pillarHeight)
									/* Rotation */
									.applyLayer(new RotateLayer(Quaternion.of(180, topY, 0, true)))
									/* Movement */
									.applyLayer(new TranslateLayer(Position.of(0, pillarMovement, 0)))))
					/* Rotation */
					.applyLayer(new RotateLayer(Quaternion.of(x, y, z, true)))
					/* Movement */
					.applyLayer(new TranslateLayer(Position.of(blockPos)))
					/* Placement */
					.validate(new SafelistValidator(world, VALIDATION), (validShape) -> {
						validShape.fill(new WhitelistedFiller(world, new SimpleFiller(world, GardenBlocks.PLAYDIRT.getDefaultState()), WHITELIST));

						if (random.nextBoolean() && config.get()) {
							double VineWidth = random.nextDouble() * 2 + 1;

							double helixHeight = finalHeight / (random.nextDouble() * 2 + 1);
							double radius = width + (VineWidth / 3) - 1.5;

							double movementUp = (finalHeight / 2) - 3;

							double vineY = random.nextDouble() * 360;

							Shape VineShape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

							/* Shape */
							VineShape.applyLayer(new AddLayer(
									/* Shape */
									Shapes.ellipticalPrism(VineWidth, VineWidth, finalHeight)
											/* Rotation */
											.applyLayer(new RotateLayer(Quaternion.of(0, vineY, 0, true)))))
									/* Rotation */
									.applyLayer(new HelixBendLayer(helixHeight, radius, Direction.Axis.Y))
									/* Rotation */
									.applyLayer(new RotateLayer(Quaternion.of(x, y, z, true)))
									/* Movement */
									.applyLayer(new TranslateLayer(Position.of(0, movementUp, 0)))
									/* Movement */
									.applyLayer(new TranslateLayer(Position.of(blockPos)))
									/* Placement */
									.validate(new SafelistValidator(world, VALIDATION), (vineShape) -> {
										vineShape.fill(new WhitelistedFiller(world, new RandomSimpleFiller(world, GardenBlocks.DEAD_TREE.leaves.getDefaultState().with(LeavesBlock.PERSISTENT, true), world.getRandom(), 0.6F), WHITELIST));
									});
						}

					});

			return true;
		}

		return false;
	}

}
