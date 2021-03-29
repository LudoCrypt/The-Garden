package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.ludocrypt.the_garden.access.ItemStackCopy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackCopy {

	@Unique
	private Item copyItem = null;

	@ModifyArg(method = "copy", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/item/ItemConvertible;I)V"))
	private ItemConvertible theGarden_copyToDifferingItem(ItemConvertible in) {
		if (copyItem != null) {
			return copyItem;
		}
		return in;
	}

	@Override
	public ItemStack copy(Item item) {
		copyItem = item;
		return copy();
	}

	@Shadow
	abstract ItemStack copy();

}
