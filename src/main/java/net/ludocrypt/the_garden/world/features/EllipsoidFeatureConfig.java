package net.ludocrypt.the_garden.world.features;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;

public class EllipsoidFeatureConfig implements FeatureConfig {

	public static final Codec<EllipsoidFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Codec.DOUBLE.fieldOf("width").forGetter((config) -> {
			return config.width;
		}), Codec.DOUBLE.fieldOf("length").forGetter((config) -> {
			return config.length;
		}), Codec.DOUBLE.fieldOf("height").forGetter((config) -> {
			return config.height;
		}), BlockState.CODEC.fieldOf("state").forGetter((config) -> {
			return config.state;
		}), Codec.list(BlockState.CODEC).fieldOf("safelist").forGetter((config) -> {
			return config.safelist;
		})).apply(instance, EllipsoidFeatureConfig::new);
	});

	private final double width;
	private final double length;
	private final double height;
	private final BlockState state;
	private final List<BlockState> safelist;

	public EllipsoidFeatureConfig(double width, double length, double height, BlockState state, List<BlockState> safelist) {
		this.width = width;
		this.length = length;
		this.height = height;
		this.state = state;
		this.safelist = safelist;
	}

	public EllipsoidFeatureConfig(double width, BlockState state, List<BlockState> safelist) {
		this(width, width, width, state, safelist);
	}

	public double getWidth() {
		return width;
	}

	public double getLength() {
		return length;
	}

	public double getHeight() {
		return height;
	}

	public BlockState getState() {
		return state;
	}

	public List<BlockState> getSafelist() {
		return safelist;
	}

}
