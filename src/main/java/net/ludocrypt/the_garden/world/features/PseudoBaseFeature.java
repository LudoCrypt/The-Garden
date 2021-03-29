package net.ludocrypt.the_garden.world.features;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.impl.Shapes;
import com.terraformersmc.terraform.shapes.impl.filler.SimpleFiller;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.SubtractLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import com.terraformersmc.terraform.shapes.impl.validator.SafelistValidator;

import net.ludocrypt.the_garden.blocks.InsulationPaddingBlock;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class PseudoBaseFeature extends Feature<DefaultFeatureConfig> {

	public PseudoBaseFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

		if (random.nextBoolean() && random.nextBoolean()) {

			List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.PINK_INSULATION.getDefaultState(), GardenBlocks.OSB_BOARD.getDefaultState(), Blocks.AIR.getDefaultState(), GardenBlocks.PLAYDIRT.getDefaultState());
			WHITELIST.addAll(GardenBlocks.PINK_INSULATION_PADDING.getStateManager().getStates());
			WHITELIST.addAll(GardenBlocks.WHITE_INSULATION_PADDING.getStateManager().getStates());
			WHITELIST.addAll(GardenBlocks.BROWN_INSULATION_PADDING.getStateManager().getStates());
			WHITELIST.addAll(GardenBlocks.GREEN_INSULATION_PADDING.getStateManager().getStates());

			pos = world.getTopPosition(Type.WORLD_SURFACE_WG, pos);

			if (world.getBlockState(pos).isOf(GardenBlocks.MULCH_LAYER_BLOCK) || world.getBlockState(pos).getBlock() instanceof InsulationPaddingBlock) {
				pos = pos.down();
			}

			int x = random.nextInt(6) + 5;
			int y = random.nextInt(8) + 8;
			int z = random.nextInt(6) + 5;

			int interiorX = x - 2;
			int interiorY = y - 3;
			int interiorZ = z - 2;

			Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

			/* Shape */
			shape = shape.applyLayer(new AddLayer(
					/* Shape */
					Shapes.rectanglarPrism(x, y, z)))
					/* Interior */
					.applyLayer(new SubtractLayer(Shapes.rectanglarPrism(interiorX, interiorY, interiorZ)))
					/* Movement */
					.applyLayer(new TranslateLayer(Position.of(pos)))
					/* Placement */
					.validate(new SafelistValidator(world, WHITELIST), (validShape) -> {
						validShape.fill(new SimpleFiller(world, GardenBlocks.OSB_BOARD.getDefaultState()));
					});

			return true;

		}

		return false;

	}

}
