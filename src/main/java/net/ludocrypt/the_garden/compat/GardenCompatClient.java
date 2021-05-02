package net.ludocrypt.the_garden.compat;

import net.ludocrypt.the_garden.compat.impl.GardenImmersivePortalsClientCompat;

public class GardenCompatClient {

	public static void attemptIMPLClientInit() {
		if (GardenCompat.isImmersivePortalsModInstalled) {
			GardenImmersivePortalsClientCompat.clientInit();
		}
	}

}
