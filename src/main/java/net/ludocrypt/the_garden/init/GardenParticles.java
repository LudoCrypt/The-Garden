package net.ludocrypt.the_garden.init;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.ludocrypt.the_garden.TheGarden;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// TODO: Check
public class GardenParticles {

	private static final Map<Identifier, ParticleType<?>> PARTICLES = new LinkedHashMap<>();

	public static final DefaultParticleType TWIG = add("twig", FabricParticleTypes.simple(true));
	public static final DefaultParticleType LEAF = add("leaf", FabricParticleTypes.simple(true));
	public static final DefaultParticleType THROWN_TWIG = add("thrown_twig", FabricParticleTypes.simple(true));
	public static final DefaultParticleType CORK_SPORE = add("cork_spore", FabricParticleTypes.simple(true));
	public static final DefaultParticleType SAWDUST = add("sawdust", FabricParticleTypes.simple(true));
	public static final DefaultParticleType GREEN_INSULATION = add("green_insulation", FabricParticleTypes.simple(true));
	public static final DefaultParticleType BROWN_INSULATION = add("brown_insulation", FabricParticleTypes.simple(true));
	public static final DefaultParticleType PINK_INSULATION = add("pink_insulation", FabricParticleTypes.simple(true));
	public static final DefaultParticleType WHITE_INSULATION = add("white_insulation", FabricParticleTypes.simple(true));

	public static final ArrayList<ParticleEffect> MULCH_PARTICLES = Lists.newArrayList(TWIG, LEAF);

	private static <T extends ParticleEffect, P extends ParticleType<T>> P add(String name, P particle) {
		PARTICLES.put(TheGarden.id(name), particle);
		return particle;
	}

	public static void init() {
		for (Identifier id : PARTICLES.keySet()) {
			Registry.register(Registry.PARTICLE_TYPE, id, PARTICLES.get(id));
		}

	}

}
