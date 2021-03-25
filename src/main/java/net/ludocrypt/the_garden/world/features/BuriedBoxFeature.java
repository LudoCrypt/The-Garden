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

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class BuriedBoxFeature extends Feature<DefaultFeatureConfig> {

	public BuriedBoxFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

		if (random.nextBoolean()) {

			List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.MULCH_BLOCK.getDefaultState(), GardenBlocks.CORK.getDefaultState(), GardenBlocks.PEA_GRAVEL.getDefaultState(), Blocks.AIR.getDefaultState());

			BlockPos.Mutable mut = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos).mutableCopy();

			while (!WHITELIST.contains(world.getBlockState(mut.down())) && mut.getY() > 0) {
				mut.move(Direction.DOWN);
			}

			if (world.getBlockState(mut).isOf(GardenBlocks.MULCH_LAYER_BLOCK)) {
				mut.move(Direction.DOWN);
			}

			BlockPos blockPos = mut.toImmutable();

			WHITELIST.addAll(GardenBlocks.MULCH_LAYER_BLOCK.getStateManager().getStates());

			BlockState state = world.getBlockState(blockPos.down());

			if (WHITELIST.contains(state)) {

				double x = MathHelper.lerp(random.nextDouble(), -5, 5);
				double y = random.nextDouble() * 360;
				double z = MathHelper.lerp(random.nextDouble(), -5, 5);

				double width = random.nextDouble() * 4.5 + 5.5;
				double length = random.nextDouble() * 3.5 + 7.5;
				double height = random.nextDouble() + 2.5;

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
						.applyLayer(new TranslateLayer(Position.of(blockPos)))
						/* Placement */
						.validate(new SafelistValidator(world, WHITELIST), (validShape) -> {
							validShape.fill(new SimpleFiller(world, state));

							if (random.nextBoolean() && random.nextBoolean()) {
								world.setBlockState(blockPos.down(), Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.byId(random.nextInt(4) + 2)), 2);
								LootableContainerBlockEntity.setLootTable(world, random, blockPos.down(), TheGarden.id("chests/buried_box"));
							}

						});

				return true;
			}

		}

		return false;
	}

}
