package com.biegan.game;

import com.badlogic.gdx.Game;
import com.biegan.game.Controller.GameController;
import com.biegan.game.Controller.AssetsMan;

public class BieganBrickRaceGame extends Game {

	public static final float sc = 2.5f;
	private AssetsMan assetsMan;

    @Override
	public void create () {
		assetsMan = new AssetsMan();
		assetsMan.loadGameScreenAssets();
        GameController controller = new GameController(this);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		assetsMan.dispose();
	}

	public AssetsMan getAssetsMan() {
		return assetsMan;
	}
}
