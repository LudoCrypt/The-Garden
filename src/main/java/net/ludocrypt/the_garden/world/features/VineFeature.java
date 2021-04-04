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
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import com.terraformersmc.terraform.shapes.impl.validator.SafelistValidator;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.util.filler.WhitelistedFiller;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class VineFeature extends Feature<DefaultFeatureConfig> {

	public VineFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

		if (random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {
			List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.DEAD_TREE.leaves.getDefaultState().with(LeavesBlock.PERSISTENT, true), Blocks.AIR.getDefaultState());
			List<BlockState> VALIDATE_WHITELIST = Lists.newArrayList(GardenBlocks.PLAYDIRT.getDefaultState(), GardenBlocks.DEAD_TREE.leaves.getDefaultState().with(LeavesBlock.PERSISTENT, true), Blocks.AIR.getDefaultState());
			VALIDATE_WHITELIST.addAll(GardenBlocks.TILE.getStateManager().getStates());

			pos = new BlockPos(pos.getX(), 0, pos.getZ());
			BlockPos.Mutable mut = pos.mutableCopy();

			int height = 8;

			while (world.isAir(mut.up()) && mut.getY() < 255) {
				height++;
				mut.move(Direction.UP);
			}

			double x = MathHelper.lerp(random.nextDouble(), -15, 15);
			double y = random.nextDouble() * 360;
			double z = MathHelper.lerp(random.nextDouble(), -15, 15);

			double width = random.nextDouble() + 2;
			double length = random.nextDouble() + 2;

			double movementUp = (height / 2) - 3;

			Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

			/* Shape */
			shape.applyLayer(new AddLayer(
					/* Shape */
					Shapes.ellipticalPrism(width, length, height)))
					/* Rotation */
					.applyLayer(new RotateLayer(Quaternion.of(x, y, z, true)))
					/* Movement */
					.applyLayer(new TranslateLayer(Position.of(0, movementUp, 0)))
					/* Movement */
					.applyLayer(new TranslateLayer(Position.of(pos)))
					/* Placement */
					.validate(new SafelistValidator(world, VALIDATE_WHITELIST), (validShape) -> {
						validShape.fill(new WhitelistedFiller(world, new RandomSimpleFiller(world, GardenBlocks.DEAD_TREE.leaves.getDefaultState().with(LeavesBlock.PERSISTENT, true), world.getRandom(), 0.3F), WHITELIST));
					});

			return true;
		}

		return false;
	}

}
