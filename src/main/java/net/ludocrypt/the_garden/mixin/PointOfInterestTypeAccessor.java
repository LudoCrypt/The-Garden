package net.ludocrypt.the_garden.mixin;

import java.util.Set;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;

@Environment(EnvType.CLIENT)
@Mixin(PointOfInterestType.class)
public interface PointOfInterestTypeAccessor {

	@Invoker("register")
	public static PointOfInterestType invokeRegister(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance) {
		throw new AssertionError();
	}

	@Invoker("register")
	public static PointOfInterestType invokeRegister(String id, Set<BlockState> workStationStates, int ticketCount, Predicate<PointOfInterestType> completionCondition, int searchDistance) {
		throw new AssertionError();
	}

}
