package com.mygdx.game;

import static com.mygdx.game.MyGdxGame.musicMenu;
import static com.mygdx.game.MyGdxGame.soundJump;

public class Sound {
    public static boolean musicEnabled = true;
    public static boolean soundEnabled = true;
    public static float musicVolume = 1.0f;
    public static float soundVolume = 1.0f;

    //cambia stato musica
    public static void changeMusicState() {
        if (musicEnabled) {
            if (!musicMenu.isPlaying()) {
                musicMenu.play();
                musicMenu.setLooping(true);
                musicMenu.setVolume(musicVolume);

            }
        } else {
            if (musicMenu.isPlaying()) {
                musicMenu.stop();
                musicMenu.setLooping(false);
            }
        }
    }
//attivo musica
    public static void setMusicEnabled(boolean enabled) {
        musicEnabled = enabled;
        changeMusicState();

    }
//cambio volume musica
    public static void changeMusicVolume(float volume) {
        musicVolume = volume;
        musicMenu.setVolume(musicVolume);

    }
//mi dice se la musica è attiva
    public boolean getMusicEnabled() {
        return musicEnabled;
    }
//mi attiva i suoni
    public static void setSoundEnabled(boolean enabled) {
        soundEnabled = enabled;

    }
//mi cambia il valore del volume dei suoni
    public static void changeSoundVolume(float volume) {
        soundVolume = volume;
        soundJump.setVolume(volume); //suono salto

    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;

    }

    public static void playJumpSound() {
        if (soundEnabled) {
            soundJump.stop();
            soundJump.play();
        }
    }
}
