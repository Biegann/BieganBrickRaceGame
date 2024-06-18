package com.biegan.game.Model;

import com.badlogic.gdx.utils.Array;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Controller.GameController;
import com.biegan.game.View.sprites.EnemyCar;

import java.util.Random;

public class EnemyCarManager {
    private GameModel model;
    private GameController controller;
    private Array<EnemyCar> enemyCars;
    private final float enemyHeight;
    private float[] trackPositions;
    private Random random;
    private boolean isMoving;
    private float lastYPosition;

    public EnemyCarManager(GameModel model, GameController controller) {
        isMoving = false;
        this.random = new Random();
        this.model = model;
        this.controller = controller;
        this.trackPositions = controller.getPositionX().getPositionsX();
        this.enemyCars = new Array<>();
        this.lastYPosition = - 9999;
        // Initialize enemy height
        this.enemyHeight = new EnemyCar(trackPositions[0], 0).getEnemyHeight();

        // Initialize enemy cars for each track position
        for (float xPosition : trackPositions) {
            float yPosition = getRandomYPosition();
            EnemyCar enemyCar = new EnemyCar(xPosition, yPosition);
            enemyCars.add(enemyCar);
        }
    }

    public void update(float dt) {
        if (isMoving) {
            for (EnemyCar enemyCar : enemyCars) {
                enemyCarUpdate(enemyCar, dt);
            }
        }
    }

    private void enemyCarUpdate(EnemyCar enemyCar, float dt) {
        enemyCar.setY(enemyCar.getY() - model.getEnemySpeed() * dt);
        if (enemyCar.getY() + enemyCar.getEnemyRegion().getRegionHeight() * BieganBrickRaceGame.sc < 0) {
            resetEnemyCarPosition(enemyCar);
            model.setScored(false);
        }
    }


    public void resetEnemyCars() {
        for (EnemyCar car : enemyCars) {
            resetEnemyCarPosition(car);
        }
    }

    private void resetEnemyCarPosition(EnemyCar car) {
        float yPosition = getRandomYPosition();
        car.setY(yPosition);
    }

    private float getRandomYPosition() {
        float spriteHieght = enemyHeight * BieganBrickRaceGame.sc;
        float randomY;

        do {
            randomY = model.getScreenHeight() + random.nextFloat(3) * spriteHieght * BieganBrickRaceGame.sc;
        } while (Math.abs(randomY - lastYPosition) < 500);

        lastYPosition = randomY;

        return randomY;
    }

    public Array<EnemyCar> getEnemyCars() {
        return enemyCars;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}
