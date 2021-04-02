package net.ludocrypt.the_garden.world.features;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Quaternion;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.impl.Shapes;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import com.terraformersmc.terraform.shapes.impl.validator.SafelistValidator;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.util.filler.WhitelistedSimpleFiller;
import net.ludocrypt.the_garden.util.layer.BendLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class IvoryMarrowFeature extends Feature<DefaultFeatureConfig> {

	public IvoryMarrowFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
		if (random.nextBoolean() && random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {

			int count = random.nextInt(5);
			for (int i = 0; i < count; i++) {
				BlockPos blockPos = pos.add(MathHelper.nextDouble(random, -15, 15), 0, MathHelper.nextDouble(random, -15, 15));
				generateTusk(world, chunkGenerator, random, blockPos, config);
			}

			return generateTusk(world, chunkGenerator, random, pos, config);
		}

		return false;
	}

	public boolean generateTusk(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
		List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.IVORY_MARROW_BLOCK.getDefaultState().with(PillarBlock.AXIS, Axis.Y), GardenBlocks.PLAYDIRT.getDefaultState(), Blocks.AIR.getDefaultState());
		List<BlockState> VALIDATE_WHITELIST = WHITELIST;
		VALIDATE_WHITELIST.addAll(GardenBlocks.TILE.getStateManager().getStates());

		pos = new BlockPos(pos.getX(), 0, pos.getZ());
		BlockPos.Mutable mut = pos.mutableCopy();

		int heightToTop = 3;

		while (!world.getBlockState(mut.up()).isOf(GardenBlocks.PLAYDIRT) && mut.getY() < 255) {
			heightToTop++;
			mut.move(Direction.UP);
		}

		double x = MathHelper.lerp(random.nextDouble(), -10, 10);
		double y = random.nextDouble() * 360;
		double z = MathHelper.lerp(random.nextDouble(), -10, 10);

		double width = random.nextDouble() * 3.5 + 5;
		double length = random.nextDouble() * 3.5 + 5;
		double height = random.nextDouble() * 12 + 15;

		double arc = MathHelper.nextDouble(random, 0, 30);

		double movementUp = heightToTop + (height / 4);

		Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

		/* Shape */
		shape.applyLayer(new AddLayer(
				/* Shape */
				Shapes.ellipticalPyramid(width, length, height)))
				/* Bend */
				.applyLayer(new BendLayer(arc, height, Direction.Axis.Z))
				/* Rotation */
				.applyLayer(new RotateLayer(Quaternion.of(0, 0, 180, true)))
				/* Rotation */
				.applyLayer(new RotateLayer(Quaternion.of(x, y, z, true)))
				/* Movement */
				.applyLayer(new TranslateLayer(Position.of(0, movementUp, 0)))
				/* Movement */
				.applyLayer(new TranslateLayer(Position.of(pos)))
				/* Placement */
				.validate(new SafelistValidator(world, VALIDATE_WHITELIST), (validShape) -> {
					validShape.fill(new WhitelistedSimpleFiller(world, GardenBlocks.IVORY_MARROW_BLOCK.getDefaultState().with(PillarBlock.AXIS, Axis.Y), WHITELIST));
				});

		return true;
	}

}
