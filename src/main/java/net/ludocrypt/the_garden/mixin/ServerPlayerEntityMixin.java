package net.ludocrypt.the_garden.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.the_garden.world.PointOne;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

	@Unique
	private int portalId = 1032;

	@Inject(method = "moveToWorld", at = @At("HEAD"))
	private void theGarden_setPortalId(ServerWorld destination, CallbackInfoReturnable<Entity> ci) {
		ServerPlayerEntity player = ((ServerPlayerEntity) (Object) this);
		if (destination.getRegistryKey().equals(PointOne.WORLD) || player.getServerWorld().getRegistryKey().equals(PointOne.WORLD)) {
			portalId = 5485720;
		} else {
			portalId = 1032;
		}
	}

	@ModifyArg(method = "moveToWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/WorldEventS2CPacket;<init>(ILnet/minecraft/util/math/BlockPos;IZ)V", ordinal = 0), index = 0)
	private int theGarden_changePortalId(int in) {
		int oldPortalId = portalId;
		portalId = 1032;
		return oldPortalId;
	}

}
