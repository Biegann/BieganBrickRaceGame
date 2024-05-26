package com.biegan.game.Controller;

import com.badlogic.gdx.Screen;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Model.GameModel;
import com.biegan.game.View.screens.GameOverScreen;
import com.biegan.game.View.screens.GameScreen;

public class GameController {
    public enum GAME_STATE
    {
        START,
        NOT_STARTED,
        STARTED,
        OVER
    };

    private GameModel model;
    private GAME_STATE state;
    private BieganBrickRaceGame game;
    private Screen currScreen;

    public GameController(BieganBrickRaceGame game)
    {
        this.game = game;
        model = new GameModel(this);
        currScreen = new GameScreen(game, this);
        // create inputProcessor
        game.setScreen(currScreen);
    }

    public void update(float delta)
    {
        switch (state)
        {
            case START:
                currScreen = new GameScreen(game, this);
                game.setScreen(currScreen);
                state = GAME_STATE.NOT_STARTED;
                break;
            case NOT_STARTED:
                // inputProcessor changes this to STARTED
                break;
            case STARTED:
                model.update(delta);
                updateGameScreen();
                break;
            case OVER:
                currScreen = new GameOverScreen(game, model.getScore());
                game.setScreen(currScreen);
                // switch to gameOverScreen inputProcessor
                break;
            default:
                break;
        }

    }

    void updateGameScreen()
    {
        // Get enemy cars positions
        List<Position> enemyCarPos = model.getEnemyCarPos();

        currScreen.updatePositions(delta);

        // update HUD
        int score = model.getScore();
        currScreen.setScore(score);
    }

    public void changeState(GAME_STATE state)
    {
        this.state = state;
    }

    public float getGameSpeed() {return model.getGameSpeed();}
}
