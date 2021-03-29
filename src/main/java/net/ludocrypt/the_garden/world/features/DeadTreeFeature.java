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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class DeadTreeFeature extends Feature<DefaultFeatureConfig> {

	public DeadTreeFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

		List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.MULCH_BLOCK.getDefaultState(), GardenBlocks.DEAD_TREE.log.getDefaultState(), Blocks.AIR.getDefaultState());

		pos = world.getTopPosition(Type.WORLD_SURFACE_WG, pos);

		if (world.getBlockState(pos).isOf(GardenBlocks.MULCH_LAYER_BLOCK)) {
			pos = pos.down();
		}

		double x = MathHelper.lerp(random.nextDouble(), -15, 15);
		double y = random.nextDouble() * 360;
		double z = MathHelper.lerp(random.nextDouble(), -15, 15);

		double width = random.nextDouble() * 2 + 1;
		double length = random.nextDouble() * 2 + 1;
		double height = random.nextDouble() * 4 + 2;

		Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

		/* Shape */
		shape.applyLayer(new AddLayer(
				/* Shape */
				Shapes.ellipticalPyramid(width, length, height)))
				/* Rotation */
				.applyLayer(new RotateLayer(Quaternion.of(x, y, z, true)))
				/* Movement */
				.applyLayer(new TranslateLayer(Position.of(0, -1.5, 0)))
				/* Movement */
				.applyLayer(new TranslateLayer(Position.of(pos)))
				/* Placement */
				.validate(new SafelistValidator(world, WHITELIST), (validShape) -> {
					validShape.fill(new SimpleFiller(world, GardenBlocks.DEAD_TREE.log.getDefaultState()));
				});

		return true;
	}

}
