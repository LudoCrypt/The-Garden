package net.ludocrypt.the_garden.client.entity.block;

import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.blocks.entity.MulchPortalBlockEntity;
import net.ludocrypt.the_garden.mixin.RenderPhaseAccessor;
import net.ludocrypt.the_garden.util.Color;
import net.ludocrypt.the_garden.util.GardenMulchEffects;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class MulchPortalBlockEntityRenderer extends BlockEntityRenderer<MulchPortalBlockEntity> {
	private static final Identifier GROUND_TEXTURE = TheGarden.id("textures/sky/mulch_portal_ground.png");
	private static final Identifier DEBRIS_TEXTIRE = TheGarden.id("textures/sky/mulch_portal_debris.png");
	private static final List<RenderLayer> RENDER_LAYERS = IntStream.range(0, 16).mapToObj((i) -> {
		return getMulchPortal(i + 1);
	}).collect(ImmutableList.toImmutableList());

	public MulchPortalBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	public void render(MulchPortalBlockEntity entity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		double distance = entity.getPos().getSquaredDistance(this.dispatcher.camera.getPos(), true);
		int layerCount = this.getLayerCount(distance);
		float yHeight = this.getYHeight();
		Matrix4f matrix = matrixStack.peek().getModel();
		this.render(entity, yHeight, matrix, vertexConsumerProvider.getBuffer(RENDER_LAYERS.get(0)));

		for (int l = 1; l < layerCount; ++l) {
			this.render(entity, yHeight, matrix, vertexConsumerProvider.getBuffer(RENDER_LAYERS.get(l)));
		}

	}

	private void render(MulchPortalBlockEntity entity, float yHeight, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
		Color color = Color.colorOf(GardenMulchEffects.getMulchColor(entity.getWorld(), entity.getPos()));
		float r = (float) ((color.getRed() / 255.0D) * 0.5F);
		float g = (float) ((color.getGreen() / 255.0D) * 0.5F);
		float b = (float) ((color.getBlue() / 255.0D) * 0.5F);
		this.render(entity, matrix4f, vertexConsumer, 0.0F, 1.0F, yHeight, yHeight, 1.0F, 1.0F, 0.0F, 0.0F, r, g, b, Direction.UP);
	}

	private void render(MulchPortalBlockEntity entity, Matrix4f matrix4f, VertexConsumer vertexConsumer, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, float r, float g, float b, Direction direction) {
		vertexConsumer.vertex(matrix4f, x1, y1, z1).color(r, g, b, 1.0F).next();
		vertexConsumer.vertex(matrix4f, x2, y1, z2).color(r, g, b, 1.0F).next();
		vertexConsumer.vertex(matrix4f, x2, y2, z3).color(r, g, b, 1.0F).next();
		vertexConsumer.vertex(matrix4f, x1, y2, z4).color(r, g, b, 1.0F).next();
	}

	protected int getLayerCount(double d) {
		if (d > 36864.0D) {
			return 1;
		} else if (d > 25600.0D) {
			return 3;
		} else if (d > 16384.0D) {
			return 5;
		} else if (d > 9216.0D) {
			return 7;
		} else if (d > 4096.0D) {
			return 9;
		} else if (d > 1024.0D) {
			return 11;
		} else if (d > 576.0D) {
			return 13;
		} else {
			return d > 256.0D ? 14 : 15;
		}
	}

	protected float getYHeight() {
		return 0.875F;
	}

	public static RenderLayer getMulchPortal(int layer) {
		RenderPhase.Transparency transparency2;
		RenderPhase.Texture texture2;
		if (layer <= 1) {
			transparency2 = RenderPhaseAccessor.getTranslucentTransparency();
			texture2 = new RenderPhase.Texture(GROUND_TEXTURE, false, false);
		} else {
			transparency2 = RenderPhaseAccessor.getAdditiveTransparency();
			texture2 = new RenderPhase.Texture(DEBRIS_TEXTIRE, false, false);
		}

		return RenderLayer.of("mulch_portal", VertexFormats.POSITION_COLOR, 7, 256, false, true, RenderLayer.MultiPhaseParameters.builder().transparency(transparency2).texture(texture2).texturing(new RenderPhase.PortalTexturing(layer)).fog(RenderPhaseAccessor.getBlackFog()).build(false));
	}
}
