package net.ludocrypt.the_garden.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "the_garden")
public class GardenConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public BiomeOptions enabledBiomes = new BiomeOptions();

	@ConfigEntry.Gui.Tooltip()
	public int baitMultiplier = 4;

	@ConfigEntry.Gui.Tooltip()
	@ConfigEntry.Gui.RequiresRestart
	public boolean enableImmersivePortals = true;

	public static void init() {
		AutoConfig.register(GardenConfig.class, GsonConfigSerializer::new);
	}

	public static GardenConfig getInstance() {
		return AutoConfig.getConfigHolder(GardenConfig.class).getConfig();
	}

	public static class BiomeOptions {

		@ConfigEntry.Gui.Tooltip()
		@ConfigEntry.Gui.RequiresRestart
		public boolean hasPointTwo = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enablePointOne = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enablePseudoCorkwoodPlains = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enableCorkwoodPlains = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enablePlaypen = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enableChurchpark = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enableSkinnedHouseparts = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enablePointTwo = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enableGravityFalls = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enableIvoryShallows = true;

		@ConfigEntry.Gui.RequiresRestart
		public boolean enableHolicIsles = true;
	}

}
