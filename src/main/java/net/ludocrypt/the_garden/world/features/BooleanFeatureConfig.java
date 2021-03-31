package net.ludocrypt.the_garden.world.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.feature.FeatureConfig;

public class BooleanFeatureConfig implements FeatureConfig {

	public static final Codec<BooleanFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Codec.BOOL.fieldOf("boolean").forGetter((config) -> {
			return config.bool;
		})).apply(instance, BooleanFeatureConfig::new);
	});

	public static final BooleanFeatureConfig TRUE = new BooleanFeatureConfig(true);
	public static final BooleanFeatureConfig FALSE = new BooleanFeatureConfig(false);

	private final boolean bool;

	public BooleanFeatureConfig(boolean bool) {
		this.bool = bool;
	}

	public boolean get() {
		return bool;
	}

}
