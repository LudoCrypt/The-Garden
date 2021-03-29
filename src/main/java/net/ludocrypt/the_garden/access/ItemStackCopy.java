package net.ludocrypt.the_garden.access;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ItemStackCopy {

	public ItemStack copy(Item item);

	public static ItemStack copy(ItemStack stack, Item item) {
		return ((ItemStackCopy) (Object) stack).copy(item);
	}

}
