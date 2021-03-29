package net.ludocrypt.the_garden.world.surfaces;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

public class PatternSurfaceConfig implements SurfaceConfig {

	public static final Codec<PatternSurfaceConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(new ListCodec<BlockState>(BlockState.CODEC).fieldOf("pattern").forGetter((config) -> config.pattern)).apply(instance, PatternSurfaceConfig::new);
	});

	private final List<BlockState> pattern;

	public PatternSurfaceConfig(List<BlockState> pattern) {
		this.pattern = pattern;
	}

	public List<BlockState> getPattern() {
		return pattern;
	}

	@Override
	public BlockState getTopMaterial() {
		return pattern.get(0);
	}

	@Override
	public BlockState getUnderMaterial() {
		return pattern.get(0);
	}

	public static PatternSurfaceConfig of(BlockState... pattern) {
		return new PatternSurfaceConfig(List.of(pattern));
	}

}
