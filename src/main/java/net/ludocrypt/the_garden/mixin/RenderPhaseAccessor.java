package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderPhase;

@Environment(EnvType.CLIENT)
@Mixin(RenderPhase.class)
public interface RenderPhaseAccessor {

	@Accessor("TRANSLUCENT_TRANSPARENCY")
	public static RenderPhase.Transparency getTranslucentTransparency() {
		throw new AssertionError();
	}

	@Accessor("ADDITIVE_TRANSPARENCY")
	public static RenderPhase.Transparency getAdditiveTransparency() {
		throw new AssertionError();
	}

	@Accessor("BLACK_FOG")
	public static RenderPhase.Fog getBlackFog() {
		throw new AssertionError();
	}

}
