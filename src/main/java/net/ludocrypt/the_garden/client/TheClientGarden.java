package net.ludocrypt.the_garden.client;

import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.ludocrypt.the_garden.client.entity.block.MulchPortalBlockEntityRenderer;
import net.ludocrypt.the_garden.client.particle.CorkSporeFactory;
import net.ludocrypt.the_garden.client.particle.LeafParticle;
import net.ludocrypt.the_garden.client.particle.ThrownTwigParticle;
import net.ludocrypt.the_garden.client.particle.TwigParticle;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.init.GardenBoats;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.ludocrypt.the_garden.util.GardenMulchEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;

@Environment(EnvType.CLIENT)
public class TheClientGarden implements ClientModInitializer {

	public static MinecraftClient client = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
		ColorProviderRegistryImpl.BLOCK.register((state, view, pos, tintIndex) -> {
			if (client.world != null && pos != null) {
				return GardenMulchEffects.getMulchColor(view, pos);
			}
			return GardenMulchEffects.defaultMulchColor.getRGB();
		}, GardenBlocks.MULCH_BLOCK, GardenBlocks.MULCH_LAYER_BLOCK);

		ColorProviderRegistryImpl.ITEM.register((stack, tintIndex) -> {
			if (client.world != null && client.player != null) {
				return GardenMulchEffects.getMulchColor(client.world, client.player.getBlockPos());
			}
			return GardenMulchEffects.defaultMulchColor.getRGB();
		}, GardenBlocks.MULCH_BLOCK, GardenBlocks.MULCH_LAYER_BLOCK);

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), GardenBlocks.DEAD_TREE.trapdoor, GardenBlocks.DEAD_TREE.door);

		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((TerraformSignBlock) GardenBlocks.DEAD_TREE.sign).getTexture()));
		EntityRendererRegistry.INSTANCE.register(GardenBoats.DEAD_TREE_BOAT, (dispatcher, context) -> new BoatEntityRenderer(dispatcher));

		ParticleFactoryRegistry.getInstance().register(GardenParticles.TWIG, TwigParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(GardenParticles.LEAF, LeafParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(GardenParticles.THROWN_TWIG, ThrownTwigParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(GardenParticles.CORK_SPORE, CorkSporeFactory::new);

		BlockEntityRendererRegistry.INSTANCE.register(GardenBlocks.MULCH_PORTAL_BLOCK_ENTITY, MulchPortalBlockEntityRenderer::new);
	}

}
