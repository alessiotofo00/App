package com.mygdx.game;

import static com.mygdx.game.MyGdxGame.*;

public class Sound {
    public static boolean musicEnabled = true;
    public static boolean soundEnabled = true;
    public static float musicVolume = 1.0f;
    public static float soundVolume = 1.5f;

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
        soundDoubleJump.setVolume(volume);
        soundCoin.setVolume(volume);
        soundDeath.setVolume(volume);
        soundButton.setVolume(volume);
    }
    public static void playButtonSound(){
        if(soundEnabled){
            soundButton.stop();
            soundButton.play();
        }
    }
public static void playDeathSound(){
        if(soundEnabled){
            soundDeath.stop();
            soundDeath.play();
        }
}
    public static void playCoinSound(){
        if(soundEnabled){
            soundCoin.stop();
            soundCoin.play();
        }
    }
    public static void playJumpSound() {
        if (soundEnabled) {
            soundJump.stop();
            soundJump.play();
        }
    }
   public static void playDoubleJumpS(){
        if(soundEnabled){
            soundDoubleJump.stop();
            soundDoubleJump.play();
        }
    }


}
