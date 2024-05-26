package com.biegan.game.Model;

import com.biegan.game.Controller.GameController;

public class GameModel {
    private GameController controller;

    private float gameSpeed = 300;

    public GameModel(GameController controller)
    {
        this.controller = controller;
    }

    public GameController getController() {return controller;}

    public float getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(float gameSpeed) {
        this.gameSpeed = gameSpeed;
    }
}
