package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.items.WormRodItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.Identifier;

@Mixin(value = FishingBobberEntityRenderer.class, priority = 245)
public class FishingBobberEntityRendererMixin {

	@Unique
	private static final Identifier WORM_HOOK = TheGarden.id("textures/entity/wormed_fishing_hook.png");

	@Unique
	private static final RenderLayer WORM_LAYER = RenderLayer.getEntityCutout(WORM_HOOK);

	@ModifyVariable(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;", ordinal = 0), ordinal = 0)
	private VertexConsumer theGarden_changeToWorm(VertexConsumer in, FishingBobberEntity fishingBobberEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		PlayerEntity player = fishingBobberEntity.getPlayerOwner();
		if (player.getMainHandStack().getItem() instanceof WormRodItem || player.getOffHandStack().getItem() instanceof WormRodItem) {
			return vertexConsumerProvider.getBuffer(WORM_LAYER);
		}
		return in;
	}

	@Inject(method = "getTexture", at = @At("TAIL"))
	private void theGarden_changeTextureToWorm(FishingBobberEntity fishingBobberEntity, CallbackInfoReturnable<Identifier> ci) {
		PlayerEntity player = fishingBobberEntity.getPlayerOwner();
		if (player != null) {
			if (player.getMainHandStack().getItem() instanceof WormRodItem || player.getOffHandStack().getItem() instanceof WormRodItem) {
				ci.setReturnValue(WORM_HOOK);
			}
		}
	}

}
