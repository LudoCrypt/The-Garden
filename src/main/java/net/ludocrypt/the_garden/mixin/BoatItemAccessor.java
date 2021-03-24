package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;

@Mixin(BoatItem.class)
public interface BoatItemAccessor {

	@Accessor("type")
	public BoatEntity.Type getType();
}
