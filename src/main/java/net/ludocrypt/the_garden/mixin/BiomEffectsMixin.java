package net.ludocrypt.the_garden.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.ludocrypt.the_garden.access.BiomeEffectsMulchColors;
import net.ludocrypt.the_garden.util.GardenMulchEffects;
import net.minecraft.world.biome.BiomeEffects;

@Mixin(BiomeEffects.class)
public class BiomEffectsMixin implements BiomeEffectsMulchColors {

	@Unique
	private Optional<Integer> mulchColor = Optional.empty();

	@Inject(method = "method_28445(Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;)Lcom/mojang/datafixers/kinds/App;", at = @At("TAIL"), cancellable = true, remap = false)
	private static void theGarden_addMulchColorsToEffects(RecordCodecBuilder.Instance<BiomeEffects> codecBuilder, CallbackInfoReturnable<App<Mu<BiomeEffects>, BiomeEffects>> ci) {
		ci.setReturnValue(codecBuilder.group(ci.getReturnValue(), Codec.INT.optionalFieldOf("mulch_color").forGetter((biomeEffects) -> {
			return BiomeEffectsMulchColors.getMulchColor(biomeEffects);
		})).apply(codecBuilder, (biomeEffects, mulch) -> {
			BiomeEffectsMulchColors.setMulchColor(biomeEffects, mulch.isPresent() ? mulch.get() : GardenMulchEffects.defaultMulchColor.getRGB());
			return biomeEffects;
		}));
	}

	@Override
	public Optional<Integer> getMulchColor() {
		return mulchColor;
	}

	@Override
	public void setMulchColor(int color) {
		mulchColor = Optional.of(color);
	}

}
