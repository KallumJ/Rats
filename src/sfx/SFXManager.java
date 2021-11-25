package sfx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import objects.Bomb;

import java.io.File;

/**
 * A class to manage the playing of sound effects
 */
public class SFXManager {

    private static final String BOMB_SFX_FILE = "resources/sounds/bomb_sfx.mp3";

    /**
     * A method to play the sound effect for a bomb
     */
    public static void playBombSFX() {
        playSfx(BOMB_SFX_FILE);
    }

    /**
     * A method to play the provided sound file
     * @param filePath the path to the file to play
     */
    private static void playSfx(String filePath) {
        Media sfx = new Media(getSoundURI(filePath));
        MediaPlayer mediaPlayer = new MediaPlayer(sfx);
        mediaPlayer.play();
    }

    /**
     * Convert file path into resource file identifier for media player to use
     * @param filePath the path to the file to convert
     * @return the URI of the file provided
     */
    private static String getSoundURI(String filePath) {
        return new File(filePath).toURI().toString();
    }

}
