package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.world.PointOne;
import net.ludocrypt.the_garden.world.PointTwo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
@Mixin(value = WorldRenderer.class, priority = 486)
public class WorldRendererMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	@Final
	private TextureManager textureManager;

	@Shadow
	private ClientWorld world;

	@Unique
	private static final Identifier POINT_ONE_SKY = new Identifier("textures/environment/end_sky.png");

	@Inject(method = "renderSky", at = @At("HEAD"))
	private void theGarden_renderSky(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		if (this.client.world.getRegistryKey().equals(PointOne.WORLD)) {
			this.renderPointOneSky(matrices);
		} else if (this.client.world.getRegistryKey().equals(PointTwo.WORLD)) {
			this.renderPointTwoSky(matrices);
		}
	}

	@Unique
	private void renderPointOneSky(MatrixStack matrices) {
		RenderSystem.disableAlphaTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		textureManager.bindTexture(TheGarden.id("textures/sky/dirt_sky.png"));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		matrices.push();

		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));

		Matrix4f matrix4f = matrices.peek().getModel();
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(matrix4f, -100, -20, -100).texture(0.0F, 0.0F).next();
		bufferBuilder.vertex(matrix4f, -100, -20, 100).texture(0.0F, 1.0F).next();
		bufferBuilder.vertex(matrix4f, 100, -20, 100).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(matrix4f, 100, -20, -100).texture(1.0F, 0.0F).next();
		tessellator.draw();
		matrices.pop();

		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
	}

	@Unique
	private void renderPointTwoSky(MatrixStack matrices) {
		RenderSystem.disableAlphaTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		textureManager.bindTexture(TheGarden.id("textures/sky/mulch_sky.png"));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		matrices.push();

		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(0.0F));

		Matrix4f matrix4f = matrices.peek().getModel();
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(matrix4f, -100, -20, -100).texture(0.0F, 0.0F).next();
		bufferBuilder.vertex(matrix4f, -100, -20, 100).texture(0.0F, 1.0F).next();
		bufferBuilder.vertex(matrix4f, 100, -20, 100).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(matrix4f, 100, -20, -100).texture(1.0F, 0.0F).next();
		tessellator.draw();
		matrices.pop();

		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
	}

	@Inject(method = "processGlobalEvent", at = @At("HEAD"))
	private void theGarden_processGlobalEvent(int eventId, BlockPos pos, int i, CallbackInfo ci) {
		Camera camera = this.client.gameRenderer.getCamera();
		if (camera.isReady()) {
			if (eventId == 648572) {
				this.world.playSound(camera.getPos().x, camera.getPos().y, camera.getPos().z, SoundEvents.ENTITY_BLAZE_HURT, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
				this.world.playSound(camera.getPos().x, camera.getPos().y, camera.getPos().z, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
			}
		}
	}

}
