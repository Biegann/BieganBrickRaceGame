package com.biegan.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.biegan.game.screens.GameScreen;

public class BieganBrickRaceGame extends Game {

	public static final float sc = 2.5f;
	public static int gameSpeed = 300;
	public SpriteBatch batch;
	// WARNING using AssetManager in a static way can cause issues
	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("engine.mp3", Music.class);
		manager.load("enginestart.mp3", Sound.class);
		manager.load("high-speed-2-192899.mp3", Sound.class);
		manager.finishLoading();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}