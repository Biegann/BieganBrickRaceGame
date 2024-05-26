package com.biegan.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.biegan.game.Controller.GameController;
import com.biegan.game.View.screens.GameScreen;
import com.biegan.game.Controller.AssetsMan;

public class BieganBrickRaceGame extends Game {

	public static final float sc = 2.5f;
	public SpriteBatch batch;
	public AssetsMan assetsMan;
	private GameController controller;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetsMan = new AssetsMan();
		assetsMan.load();
		controller = new GameController();
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

	public GameController getController() {return controller;}
}
