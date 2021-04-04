package net.ludocrypt.the_garden.server;

import java.util.EnumSet;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

import net.ludocrypt.the_garden.mixin.TeleportCommandAccessor;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class TpBiomeCommand {
	public static final DynamicCommandExceptionType INVALID_EXCEPTION = new DynamicCommandExceptionType((object) -> {
		return new TranslatableText("commands.locatebiome.invalid", new Object[] { object });
	});
	private static final DynamicCommandExceptionType NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType((object) -> {
		return new TranslatableText("commands.locatebiome.notFound", new Object[] { object });
	});

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register((CommandManager.literal("tpbiome").requires((serverCommandSource) -> {
			return serverCommandSource.hasPermissionLevel(2);
		})).then(CommandManager.argument("biome", IdentifierArgumentType.identifier()).suggests(SuggestionProviders.ALL_BIOMES).executes((commandContext) -> {
			return execute(commandContext, commandContext.getArgument("biome", Identifier.class));
		})));
	}

	private static int execute(CommandContext<ServerCommandSource> context, Identifier identifier) throws CommandSyntaxException {
		ServerCommandSource source = context.getSource();
		Biome biome = source.getMinecraftServer().getRegistryManager().get(Registry.BIOME_KEY).getOrEmpty(identifier).orElseThrow(() -> {
			return INVALID_EXCEPTION.create(identifier);
		});
		ServerWorld world = source.getWorld();
		BlockPos blockPos = new BlockPos(source.getPosition());
		BlockPos blockPos2 = world.locateBiome(biome, blockPos, 6400, 8);

		String string = identifier.toString();
		if (blockPos2 == null) {
			throw NOT_FOUND_EXCEPTION.create(string);
		} else {

			blockPos2 = new BlockPos(blockPos2.getX(), 255, blockPos2.getZ());

			while (world.getBlockState(blockPos2.down()).isAir()) {
				blockPos2 = blockPos2.down();
			}

			if (blockPos2.getY() <= 0) {
				blockPos2 = new BlockPos(blockPos2.getX(), 256, blockPos2.getZ());
			}

			TeleportCommandAccessor.teleport(source, source.getPlayer(), world, blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), EnumSet.noneOf(PlayerPositionLookS2CPacket.Flag.class), source.getPlayer().yaw, source.getPlayer().pitch, null);
			return LocateCommand.sendCoordinates(source, string, blockPos, blockPos2, "commands.locatebiome.success");
		}
	}

}
