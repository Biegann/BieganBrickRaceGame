package com.biegan.game.Model;

import com.badlogic.gdx.Gdx;
import com.biegan.game.Controller.GameController;

public class GameModel {
    private GameController controller;
    private EnemyCarManager manager;
    private float screenHeight;
    private boolean scored;
    private int score = 0;
    private float gameSpeed = 300;
    private float enemySpeed = gameSpeed - 50;

    public GameModel(GameController controller) {
        this.controller = controller;
        this.manager = new EnemyCarManager(this, controller);
        this.screenHeight = Gdx.graphics.getHeight();

    }

    public GameController getController() {
        return controller;
    }

    public float getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(float gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public void update(float dt) {
        manager.update(dt);
    }

    public int getScore() {
        return score;
    }

    public boolean hasScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getEnemySpeed() {
        return enemySpeed;
    }

    public void setEnemySpeed(float enemySpeed) {
        this.enemySpeed = enemySpeed;
    }
    public void reset() {
        this.gameSpeed = 300;
        this.enemySpeed = gameSpeed - 50;
        this.score = 0;
        this.scored = false;
    }
}
