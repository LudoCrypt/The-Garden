package net.ludocrypt.the_garden.world.surfaces;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

public class PatternSurfaceConfig implements SurfaceConfig {

	public static final Codec<PatternSurfaceConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(new ListCodec<PatternPair>(PatternPair.CODEC).fieldOf("pattern").forGetter((config) -> config.pattern)).apply(instance, PatternSurfaceConfig::new);
	});

	private final List<PatternPair> pattern;

	public PatternSurfaceConfig(List<PatternPair> pattern) {
		this.pattern = pattern;
	}

	public List<PatternPair> getPattern() {
		return pattern;
	}

	@Override
	public BlockState getTopMaterial() {
		return pattern.get(0).getState();
	}

	@Override
	public BlockState getUnderMaterial() {
		return pattern.get(0).getState();
	}

	public static PatternSurfaceConfig of(PatternPair... pattern) {
		return new PatternSurfaceConfig(List.of(pattern));
	}

	public static class PatternPair {

		public static final Codec<PatternPair> CODEC = RecordCodecBuilder.create((instance) -> {
			return instance.group(BlockState.CODEC.fieldOf("state").forGetter((config) -> config.state), Codec.INT.fieldOf("height").forGetter((config) -> config.h)).apply(instance, PatternPair::new);
		});

		private final BlockState state;
		private final int h;

		PatternPair(BlockState state, int h) {
			this.state = state;
			this.h = h;
		}

		public BlockState getState() {
			return state;
		}

		public int getH() {
			return h;
		}

		public static PatternPair of(BlockState state) {
			return new PatternPair(state, 0);
		}

		public static PatternPair of(BlockState state, int h) {
			return new PatternPair(state, h);
		}

	}
}
