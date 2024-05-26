package com.biegan.game.Controller;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AssetsMan {
    private AssetManager assetManager;

    public AssetsMan() {
        assetManager = new AssetManager();
    }

    public void load() {
        assetManager.load("engine.mp3", Music.class);
        assetManager.load("enginestart.mp3", Sound.class);
        assetManager.load("high-speed-2-192899.mp3", Sound.class);
        assetManager.load("explosion.mp3", Sound.class);
        assetManager.load("speedUp.mp3", Sound.class);
        assetManager.load("playersound.mp3", Sound.class);
        assetManager.finishLoading();
    }
    public boolean update() {
        return assetManager.update();
    }

    public float getProgress() {
        return assetManager.getProgress();
    }

    public <T> T get(String fileName, Class<T> type) {
        return assetManager.get(fileName, type);
    }

    public void dispose() {
        assetManager.dispose();
    }
}
