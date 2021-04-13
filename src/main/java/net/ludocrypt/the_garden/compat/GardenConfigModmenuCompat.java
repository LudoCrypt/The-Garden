package net.ludocrypt.the_garden.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.the_garden.config.GardenConfigurations;

@Environment(EnvType.CLIENT)
public class GardenConfigModmenuCompat implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> AutoConfig.getConfigScreen(GardenConfigurations.class, parent).get();
	}

}
