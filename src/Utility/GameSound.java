package Utility;

import org.jsfml.audio.SoundBuffer;
import org.jsfml.audio.Sound;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Class used for in-game sounds.
 */

public class GameSound {

    private SoundBuffer soundBuffer = new SoundBuffer();
    private Sound sound;
    private boolean isPlaying;

    /**
     * Plays a sound needed for set application.
     * @param toPlay sound to play.
     * @param loudness volume at which the sound is played.
     */

    public void Sound(String toPlay, float loudness) {
        
        try {
            switch (toPlay) {
                case "start":
                    soundBuffer.loadFromFile(Paths.get("Sounds/RetroDetune-ThatFunkyTune.wav"));
                    break;
                case "inGame":
                    soundBuffer.loadFromFile(Paths.get("Sounds/RetroDetune-16Bit.wav"));
                    break;
                case "shoot":
                    soundBuffer.loadFromFile(Paths.get("Sounds/LaserSound.wav"));
                    break;
                case "explosion":
                    soundBuffer.loadFromFile(Paths.get("Sounds/explosion.wav"));
                    break;
                case "powerUp":
                    soundBuffer.loadFromFile(Paths.get("Sounds/powerUP.wav"));
                    break;
                case "coinPickUp":
                    soundBuffer.loadFromFile(Paths.get("Sounds/coinPickUp.wav"));
                    break;
            }
        } catch (IOException ex) {
            System.err.println("Failed to load the sound:");
            ex.printStackTrace();
        }
        this.isPlaying = true;
        sound = new Sound(soundBuffer);
        sound.play();
        sound.setVolume(loudness);
    }

    /**
     * Enables or disables sound looping.
     * @param state turns loop on or off.
     */
    public void setLoop(boolean state){ sound.setLoop(state);}

    /**
     * Stops the sound.
     */
    public void stop() {
        sound.stop();
        this.isPlaying = false;
    }

    /**
     * Pauses the sound.
     */
    public void pause() {
        sound.pause();
        this.isPlaying = false;
    }

    /**
     * Plays the sound.
     */
    public void play(){
        sound.play();
        this.isPlaying = true;
    }

    /**
     * Returns a boolean whether sound is playing currently.
     * @return true or false if sound is playing.
     */
    public boolean isPlaying(){
        return isPlaying;
    }

}
