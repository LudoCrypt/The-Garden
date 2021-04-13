package net.ludocrypt.the_garden.items;

import net.ludocrypt.the_garden.access.ItemStackCopy;
import net.ludocrypt.the_garden.config.GardenConfigurations;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WormRodItem extends FishingRodItem {

	public WormRodItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (user.fishHook != null) {
			if (!world.isClient) {
				int i = user.fishHook.use(itemStack);
				if (i > 0) {
					itemStack = ItemStackCopy.copy(itemStack, Items.FISHING_ROD);
				}
				itemStack.damage(i, user, ((p) -> {
					p.sendToolBreakStatus(hand);
				}));
			}
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
		} else {
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
			if (!world.isClient) {
				int multiplier = GardenConfigurations.getInstance().baitMultiplier;
				int i = EnchantmentHelper.getLure(itemStack) + multiplier;
				int k = EnchantmentHelper.getLuckOfTheSea(itemStack) + multiplier;
				world.spawnEntity(new FishingBobberEntity(user, world, k, i));
			}

			user.incrementStat(Stats.USED.getOrCreateStat(this));
		}

		return TypedActionResult.success(itemStack, world.isClient());
	}

}
