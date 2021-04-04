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
import com.terraformersmc.terraform.shapes.impl.filler.SimpleFiller;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.AddLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import com.terraformersmc.terraform.shapes.impl.validator.SafelistValidator;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.util.PositionUtil;
import net.ludocrypt.the_garden.util.filler.ListFiller;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class CrackedObsidianPoolFeature extends Feature<DefaultFeatureConfig> {

	public CrackedObsidianPoolFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {

		if (random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {

			List<BlockState> WHITELIST = Lists.newArrayList(GardenBlocks.CRACKED_OBSIDIAN.getDefaultState(), Blocks.WATER.getDefaultState(), GardenBlocks.PLAYDIRT.getDefaultState(), Blocks.AIR.getDefaultState());

			double x = MathHelper.lerp(random.nextDouble(), -10, 10);
			double y = random.nextDouble() * 360;
			double z = MathHelper.lerp(random.nextDouble(), -10, 10);

			double width = random.nextDouble() * 4 + 7;
			double length = random.nextDouble() * 4 + 7;
			double height = random.nextDouble() * 2 + 2;

			double scaleX = width / 3;
			double scaleZ = length / 3;

			Shape shape = Shape.of((point) -> false, Position.of(0, 0, 0), Position.of(0, 0, 0));

			/* Shape */
			shape.applyLayer(new AddLayer(
					/* Shape */
					Shapes.ellipsoid(width, length, height)))
					/* Rotation */
					.applyLayer(new RotateLayer(Quaternion.of(x, y, z, true)))
					/* Movement */
					.applyLayer(new TranslateLayer(Position.of(pos)))
					/* Placement */
					.validate(new SafelistValidator(world, WHITELIST), (validShape) -> {
						validShape.fill(new ModuloFiller(PositionUtil.getCenter(validShape), new ListFiller(world, random, GardenBlocks.CRACKED_OBSIDIAN.getDefaultState(), GardenBlocks.PLAYDIRT.getDefaultState(), GardenBlocks.PLAYDIRT.getDefaultState(), GardenBlocks.PLAYDIRT.getDefaultState()), new SimpleFiller(world, Blocks.WATER.getDefaultState()), 1, scaleX, 1, scaleZ));
					});

			return true;

		}

		return false;
	}

	public static class ModuloFiller implements Filler {

		private final Position center;
		private final Filler fillerA;
		private final Filler fillerB;
		private final double radius;
		private final double scaleX;
		private final double scaleY;
		private final double scaleZ;

		public ModuloFiller(Position center, Filler fillerA, Filler fillerB, double radius, double scaleX, double scaleY, double scaleZ) {
			this.center = center;
			this.fillerA = fillerA;
			this.fillerB = fillerB;
			this.radius = radius;
			this.scaleX = scaleX;
			this.scaleY = scaleY;
			this.scaleZ = scaleZ;
		}

		@Override
		public void accept(Position position) {
			Vec3d pos = PositionUtil.toVec3d(position);
			double dist = PositionUtil.distanceToStretched(pos, PositionUtil.toVec3d(center), scaleX, scaleY, scaleZ);
			if (dist % radius <= (radius / 2)) {
				fillerA.accept(position);
			} else {
				fillerB.accept(position);
			}
		}
	}

}
