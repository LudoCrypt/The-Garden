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
import net.minecraft.world.gen.feature.Feature;

public class CorkStumpFeature extends Feature<EllipsoidFeatureConfig> {

	public CorkStumpFeature(Codec<EllipsoidFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, EllipsoidFeatureConfig config) {

		List<BlockState> WHITELIST = Lists.newArrayList(config.getState(), Blocks.AIR.getDefaultState());
		WHITELIST.addAll(config.getSafelist());

		pos = world.getTopPosition(Type.WORLD_SURFACE_WG, pos);

		if (world.getBlockState(pos).isOf(GardenBlocks.MULCH_LAYER_BLOCK)) {
			pos = pos.down();
		}

		double x = MathHelper.lerp(random.nextDouble(), -30, 30);
		double y = random.nextDouble() * 360;
		double z = MathHelper.lerp(random.nextDouble(), -30, 30);

		double width = config.getWidth();
		double length = config.getLength();
		double height = config.getHeight();

		double downY = -(height / 3);

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
				.applyLayer(new TranslateLayer(Position.of(pos)))
				/* Placement */
				.validate(new SafelistValidator(world, WHITELIST), (validShape) -> {
					validShape.fill(new SimpleFiller(world, config.getState()));
				});

		return true;
	}

}
