package com.biegan.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.biegan.game.screens.GameScreen;
import com.biegan.game.utilities.AssetsMan;

public class BieganBrickRaceGame extends Game {

	public static final float sc = 2.5f;
	public SpriteBatch batch;
	public AssetsMan assetsMan;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetsMan = new AssetsMan();
		assetsMan.load();
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
		assetsMan.dispose();
	}
}
