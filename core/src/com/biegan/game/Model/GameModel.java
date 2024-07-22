package com.biegan.game.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Controller.AssetsMan;
import com.biegan.game.Controller.GameController;
import com.biegan.game.View.sprites.EnemyCar;
import com.biegan.game.View.sprites.Explosion;

import java.util.Random;

public class GameModel {
    private final float screenHeight;
    private boolean scored;
    private int score = 0;
    private float gameSpeed = 300;
    private float enemySpeed = gameSpeed - 50;
    private final float[] trackPositions;

    private final GameController controller;
    private final AssetsMan assetsMan;
    private final Array<EnemyCar> enemyCars;
    private final float enemyHeight;
    private final Random random;
    private boolean isMoving;
    private float lastYPosition;
    private int lastSpeedIncreaseScore = 0;

    public GameModel(GameController controller) {
        this.screenHeight = Gdx.graphics.getHeight();
        this.trackPositions = new float[] {120, 230, 330, 430};
        this.controller = controller;
        this.isMoving = false;
        this.assetsMan = controller.getAssetsMan();
        this.random = new Random();
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

    private void checkCollisions(float dt) {
        for (EnemyCar car : getEnemyCars()) {
            if (car.getBoundingRectangle().overlaps(controller.getPlayer().getBoundingRectangle())) {
                //collision
                handleCollision(car);
                break;
            }
        }
        for (EnemyCar car : getEnemyCars()) {
            if (car.getY() / BieganBrickRaceGame.sc < 0 && !hasScored()) {
                handleScore();
            }
        }

        if (getScore() % 500 == 0 && getScore() != 0 && getScore() != lastSpeedIncreaseScore) {
            controller.increaseGameSpeed();
            lastSpeedIncreaseScore = getScore();
        }
    }

    private void handleCollision(EnemyCar car) {
        assetsMan.playSound("explosion.mp3");
        controller.getExplosions().add(new Explosion(car.getX(), car.getY(), 0));
        controller.getExplosions().add(new Explosion(controller.getPlayer().getX(), controller.getPlayer().getY(), 0));
        assetsMan.stopMusic("engine.mp3");
        controller.setState(GameController.GAME_STATE.OVER);
    }

    private void handleScore() {
        assetsMan.playSound("high-speed-2-192899.mp3");
        setScore(getScore() + 50);
        setScored(true);
    }

    public void increaseGameSpeed() {
        setGameSpeed(getGameSpeed() + 20); // increase gamespeed
        setEnemySpeed(getGameSpeed() - 50);
    }

    public float getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(float gameSpeed) {
        this.gameSpeed = gameSpeed;
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
        this.isMoving = false;
        resetEnemyCars();
    }

    public void update(float dt) {
        if (isMoving) {
            checkCollisions(dt);
            for (EnemyCar enemyCar : enemyCars) {
                enemyCarUpdate(enemyCar, dt);
            }
        }
    }

    private void enemyCarUpdate(EnemyCar enemyCar, float dt) {
        enemyCar.setY(enemyCar.getY() - getEnemySpeed() * dt);
        if (enemyCar.getY() + enemyCar.getEnemyRegion().getRegionHeight() * BieganBrickRaceGame.sc < 0) {
            resetEnemyCarPosition(enemyCar);
            setScored(false);
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
            randomY = getScreenHeight() + random.nextFloat(3) * spriteHieght * BieganBrickRaceGame.sc;
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

    public float[] getPositionsX() {
        return trackPositions;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void startGameModel() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setMoving(true);
            }
        }, 1.6f);
    }
}
