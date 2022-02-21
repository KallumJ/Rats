package sfx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * A class to manage the playing of sound effects
 *
 * @author Kallum Jones 2005855
 * @date 2022/02/18
 *
 */
public class SFXManager {

	private static final String BOMB_SFX_FILE = "resources/sounds/bomb_sfx" +
			".mp3";
	private static final String PUT_SFX_FILE = "resources/sounds/put_sfx.mp3";
	private static final String DEATHRAT_SFX_FILE = "resources/sounds/deathRat"
			+ ".mp3";
	private static final String ENTRYSIGN_SFX_FILE = "resources/sounds" +
			"/noEntrySign.mp3";
	private static final String THUNDER_SFX_FILE = "resources/sounds" +
			"/thunder.mp3";
	private static final String AIRSTRIKE_SFX_FILE = "resources/sounds/AirstrikeCall.mp3";

	/**
	 * Empty private constructor method, preventing SFXManager from being
	 * instantiated as an object.
	 */
	private SFXManager() {
	}

	/**
	 * A method to play the sound effect for a bomb
	 */
	public static void playBombSFX() {
		playSfx(BOMB_SFX_FILE);
	}

	/**
	 * A method to play the call sound of an airstrike
	 */
	public static void playAirstrikeSFX() {
		playSfx(AIRSTRIKE_SFX_FILE);
	}

	/**
	 * A method to play the sound effect for a death rat
	 */
	public static void playDeathratSFX() {
		playSfx(DEATHRAT_SFX_FILE);
	}

	/**
	 * A method to play the sound effect for rat bumping into a no entry sign
	 */
	public static void playEntrysignSFX() {
		playSfx(ENTRYSIGN_SFX_FILE);
	}

	/**
	 * A method to play the sound when item be placed
	 */
	public static void bePlaced() {
		playSfx(PUT_SFX_FILE);
	}
        
        /**
	 * A method to play the sound effect for a thundery weather.
	 */
	public static void playThunder() {
		playSfx(THUNDER_SFX_FILE);
	}

	/**
	 * A method to play the provided sound file
	 *
	 * @param filePath the path to the file to play
	 */
	private static void playSfx(String filePath) {
		Media sfx = new Media(getSoundURI(filePath));
		MediaPlayer mediaPlayer = new MediaPlayer(sfx);
		mediaPlayer.play();
	}

	/**
	 * Convert file path into resource file identifier for media player to use
	 *
	 * @param filePath the path to the file to convert
	 * @return the URI of the file provided
	 */
	private static String getSoundURI(String filePath) {
		return new File(filePath).toURI().toString();
	}

}
