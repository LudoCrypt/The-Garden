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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class DirtSpikeFeature extends Feature<DefaultFeatureConfig> {

	public DirtSpikeFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

		List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.PLAYDIRT.getDefaultState(), Blocks.AIR.getDefaultState());
		List<BlockState> VALIDATION = WHITELIST;
		VALIDATION.addAll(GardenBlocks.TILE.getStateManager().getStates());

		pos = new BlockPos(pos.getX(), 0, pos.getZ());

		double x = MathHelper.lerp(random.nextDouble(), -30, 30);
		double y = random.nextDouble() * 360;
		double z = MathHelper.lerp(random.nextDouble(), -30, 30);

		double width = random.nextDouble() * 6 + 3;
		double length = random.nextDouble() * 6 + 3;
		double height = random.nextDouble() * 10 + width;

		Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

		/* Shape */
		shape.applyLayer(new AddLayer(
				/* Shape */
				Shapes.ellipticalPyramid(width, length, height)))
				/* Rotation */
				.applyLayer(new RotateLayer(Quaternion.of(x, y, z, true)))
				/* Movement */
				.applyLayer(new TranslateLayer(Position.of(0, -2, 0)))
				/* Movement */
				.applyLayer(new TranslateLayer(Position.of(pos)))
				/* Placement */
				.validate(new SafelistValidator(world, VALIDATION), (validShape) -> {
					validShape.fill(new WhitelistedSimpleFiller(world, GardenBlocks.PLAYDIRT.getDefaultState(), WHITELIST));
				});

		return true;
	}

}
