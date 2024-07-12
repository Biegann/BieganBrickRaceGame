package com.biegan.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Model.GameModel;
import com.biegan.game.View.screens.GameOverScreen;
import com.biegan.game.View.screens.GameScreen;
import com.biegan.game.View.sprites.*;

public class GameController {

    public enum GAME_STATE
    {
        NOT_STARTED,
        STARTED,
        OVER
    };

    private final GameModel model;
    private final GameScreen gameScreen;
    private final GameOverScreen gameOverScreen;
    private final BieganBrickRaceGame game;
    private final Player player;
    private boolean isGameStarted;
    private GAME_STATE state;

    public GameController(BieganBrickRaceGame game)
    {
        this.game = game;
        this.model = new GameModel(this);
        this.player = new Player(this);

        this.gameScreen = new GameScreen(this);
        this.gameOverScreen = new GameOverScreen(this);

        game.setScreen(gameScreen);
        setState(GAME_STATE.NOT_STARTED);
    }

    public void update(float dt) {

        switch (state)
        {
            case STARTED:
                startedState(dt);
                break;
            case OVER:
                overState(dt);
                break;
            default:
                break;
        }
    }

    private void startedState(float dt) {
        updateAll(dt);
        if (!isGameStarted) {
            startGame();
            isGameStarted = true;
        }
    }

    private void overState(float dt) {
        // Check all explosions finished
        if (gameScreen.isExplosionsFinished()) {
            game.setScreen(gameOverScreen);
        }
    }

    private void startGame() {
        gameScreen.startGameView();
        model.startGameModel();
    }

    public void setState(GAME_STATE state) {
        this.state = state;
    }

    public GAME_STATE getState() {
        return state;
    }

    public void increaseGameSpeed() {
        model.increaseGameSpeed();
        gameScreen.increaseGameSpeed();
    }

    public float[] getPositionX() {
        return model.getPositionsX();
    }

    public void updateAll(float dt) {
        model.update(dt);
        gameScreen.update(dt);
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Explosion> getExplosions() {
        return gameScreen.getExplosions();
    }

    public float getGameSpeed() {
        return model.getGameSpeed();
    }

    public void restart() {
        model.reset();
        gameScreen.resetView();
        isGameStarted = false;
        game.setScreen(gameScreen);
        setState(GAME_STATE.NOT_STARTED);
    }

    public Integer getScore() {
        return model.getScore();
    }

    public Array<EnemyCar> getEnemyCars() {
        return model.getEnemyCars();
    }

    public AssetsMan getAssetsMan() {
        return game.getAssetsMan();
    }
}
