package net.ludocrypt.the_garden.init;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.ludocrypt.the_garden.server.TpBiomeCommand;

public class GardenCommands {

	public static void init() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			TpBiomeCommand.register(dispatcher);
		});
	}

}
