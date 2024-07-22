package com.biegan.game.Controller;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AssetsMan {
    private AssetManager assetManager;

    public AssetsMan() {
        assetManager = new AssetManager();
    }

    public void loadGameScreenAssets() {
        assetManager.load("engine.mp3", Music.class);
        assetManager.load("playersound.mp3", Sound.class);
        assetManager.load("enginestart.mp3", Sound.class);
        assetManager.load("high-speed-2-192899.mp3", Sound.class);
        assetManager.load("explosion.mp3", Sound.class);
        assetManager.load("speedUp.mp3", Sound.class);

        assetManager.finishLoading();
    }

    public <T> T get(String fileName, Class<T> type) {
        if (assetManager.isLoaded(fileName)) {
            return assetManager.get(fileName, type);
        } else {
            throw new RuntimeException("Asset not loaded: " + fileName);
        }
    }

    public void playSound(String fileName) {
        Sound sound = get(fileName, Sound.class);
        sound.play();
    }

    public void playMusic(String fileName, boolean looping) {
        Music music = get(fileName, Music.class);
        music.setLooping(looping);
        music.play();
    }

    public void stopMusic(String fileName) {
        Music music = get(fileName, Music.class);
        music.stop();
    }

    public void dispose() {
        assetManager.dispose();
    }
}
