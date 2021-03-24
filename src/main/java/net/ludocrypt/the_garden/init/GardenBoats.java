package net.ludocrypt.the_garden.init;

import com.terraformersmc.terraform.boat.TerraformBoat;
import com.terraformersmc.terraform.boat.TerraformBoatEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.util.wood.WoodBlocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GardenBoats {

	public static EntityType<TerraformBoatEntity> DEAD_TREE_BOAT;

	public static void init() {
		DEAD_TREE_BOAT = registerBoat("dead_tree", GardenBlocks.DEAD_TREE, BoatEntity.Type.DARK_OAK);
	}

	private static EntityType<TerraformBoatEntity> registerBoat(String name, WoodBlocks wood, BoatEntity.Type vanilla) {
		Identifier skin = TheGarden.id("textures/entity/boat/" + name + ".png");
		TerraformBoat boat = new TerraformBoat(wood.boat, wood.planks.asItem(), skin, vanilla);

		EntityType<TerraformBoatEntity> type = FabricEntityTypeBuilder.<TerraformBoatEntity>create(SpawnGroup.MISC, (entity, world) -> new TerraformBoatEntity(entity, world, boat)).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).build();

		return Registry.register(Registry.ENTITY_TYPE, TheGarden.id(name + "_boat"), type);
	}

}
