package basic;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.init() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can use the static variable SoundEffect.volume to mute the sound.
 */

public enum SoundEffect {
	CLICK("res/sounds/beep1.wav"),		// increase /decrease something
	WRONGCODE("res/sounds/switch1.wav"),
	RIGHTCODE("res/sounds/win2.wav"),
	GAMEOVER("res/sounds/lose1.wav"),
	COLORCHANGE("res/sounds/beep1.wav");

	// Each sound effect has its own clip, loaded with its own sound file.
	private Sound clip;

	// Constructor to construct each element of the enum with its own sound file.
	SoundEffect(String soundFileName) {
		try {
			clip = new Sound(soundFileName);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.play();
	}


	// Optional static method to pre-load all the sound files.
	static void init() {
		values(); // calls the constructor for all the elements
	}
}