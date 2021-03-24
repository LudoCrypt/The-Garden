package net.ludocrypt.the_garden.blocks.entity;

import net.ludocrypt.the_garden.init.GardenBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EndPortalBlockEntity;

public class MulchPortalBlockEntity extends EndPortalBlockEntity {

	public MulchPortalBlockEntity(BlockEntityType<?> blockEntityType) {
		super(blockEntityType);
	}

	public MulchPortalBlockEntity() {
		super(GardenBlocks.MULCH_PORTAL_BLOCK_ENTITY);
	}

}
