package com.biegan.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.biegan.game.BieganBrickRaceGame;
import java.util.Random;

public class EnemyCar extends Sprite {

    private Texture enemyTexture;
    private TextureRegion enemyRegion;
    private float enemySpeed = BieganBrickRaceGame.gameSpeed - 50;
    private float yPosition;
    private float xPosition;
    private Random random = new Random();
    private float delayTime;
    public float elapsedTime;
    private boolean scored = false; // Flag to track scores
    private float screenHeight;

    public EnemyCar(Texture enemyTexture, float xPosition) {
        this.enemyTexture = enemyTexture;
        this.enemyRegion = new TextureRegion(enemyTexture);
        this.xPosition = xPosition;

        screenHeight = Gdx.graphics.getHeight();
        resetPosition(true);

        float stripeWidth = enemyRegion.getRegionWidth() * BieganBrickRaceGame.sc;
        float stripeHeight = enemyRegion.getRegionHeight() * BieganBrickRaceGame.sc;
        setBounds(xPosition, yPosition, stripeWidth, stripeHeight); // Set initial bounds and positions
    }

    public void enemyCarUpdate(float dt) {
        if (elapsedTime >= delayTime) {
            yPosition -= enemySpeed * dt;
            //counting points and resetting position
              if (yPosition + getHeight() < 0) {
                resetPosition(false);
                scored = false; //reset of score flag
            }
        } else {
            elapsedTime += dt; // Increase time when it is delay
        }
        setPosition(xPosition, yPosition); // Actualization of sprite position
    }

    public void resetPosition(boolean initial) {
        float stripeHeight = enemyRegion.getRegionHeight() * BieganBrickRaceGame.sc;
        if (initial) {
            yPosition = screenHeight + random.nextFloat(screenHeight) + 20; // Random Y position at the beginning of game
        } else {
            yPosition = screenHeight + stripeHeight + 20; // above screen
        }
        elapsedTime = 0;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(enemyRegion, getX(), getY(),
                enemyRegion.getRegionWidth() * BieganBrickRaceGame.sc,
                enemyRegion.getRegionHeight() * BieganBrickRaceGame.sc);
    }
    public void setSpeed(float enemySpeed) {
        this.enemySpeed = enemySpeed;
    }
    public boolean hasScored() {
        return  scored;
    }
    public void setScored(boolean scored) {
        this.scored = scored;
    }

    public float getDelayTime() {
        return delayTime;
    }
    public void setDelayTime(float delayTime) {
        this.delayTime = delayTime;
    }
}