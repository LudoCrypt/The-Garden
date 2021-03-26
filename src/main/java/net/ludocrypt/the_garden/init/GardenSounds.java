package net.ludocrypt.the_garden.init;

import java.util.LinkedHashMap;
import java.util.Map;

import net.ludocrypt.the_garden.TheGarden;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GardenSounds {

	private static final Map<Identifier, SoundEvent> SOUND_EVENTS = new LinkedHashMap<>();

	// Music
	public static final SoundEvent POINT_ONE_MUSIC = add("music.point_one");
	public static final SoundEvent POINT_TWO_MUSIC = add("music.point_two");
	public static final SoundEvent THE_GARDEN_MENU_MUSIC = add("music.menu");
	public static final MusicSound POINT_ONE = MusicType.createIngameMusic(POINT_ONE_MUSIC);
	public static final MusicSound POINT_TWO = MusicType.createIngameMusic(POINT_TWO_MUSIC);

	private static SoundEvent add(String id) {
		Identifier realId = TheGarden.id(id);
		SoundEvent S = new SoundEvent(realId);
		SOUND_EVENTS.put(realId, S);
		return S;
	}

	public static void init() {
		for (Identifier id : SOUND_EVENTS.keySet()) {
			Registry.register(Registry.SOUND_EVENT, id, SOUND_EVENTS.get(id));
		}

	}

}
