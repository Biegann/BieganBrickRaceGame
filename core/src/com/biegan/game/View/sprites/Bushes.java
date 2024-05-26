package com.biegan.game.View.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Controller.GameController;

import java.util.Random;

public class Bushes extends Sprite {
    private Texture bushesTexture;
    private TextureRegion bushesRegion;
    private float bushesSpeed;
    private float yPosition;
    private float xPosition;
    private Random random = new Random();
    private float delayTime;
    public float elapsedTime;
    private float screenHeight;
    private GameController controller;

    public Bushes(Texture bushesTexture, GameController controller) {
        this.bushesTexture = bushesTexture;
        this.bushesRegion = new TextureRegion(bushesTexture);
        this.controller = controller;
        bushesSpeed = controller.getGameSpeed();

        screenHeight = Gdx.graphics.getHeight();
        resetPosition(true);

        float stripeWidth = bushesRegion.getRegionWidth() * BieganBrickRaceGame.sc;
        float stripeHeight = bushesRegion.getRegionHeight() * BieganBrickRaceGame.sc;
        setBounds(xPosition, yPosition, stripeWidth, stripeHeight); // set initial dimensions and positions
    }
    public void bushesUpdate(float dt) {
        if (elapsedTime >= delayTime) {
            yPosition -= bushesSpeed * dt;
            //reset position
            if (yPosition + getHeight() < 0) {
                resetPosition(false);
                delayTime = random.nextFloat(2);
            }
        } else {
            elapsedTime += dt; // Increases the time if there is a delay
        }
        setPosition(xPosition, yPosition); // actualization of sprite position
    }

    public void resetPosition(boolean initial) {
        float stripeHeight = bushesRegion.getRegionHeight() * BieganBrickRaceGame.sc;
        if (initial) {
            yPosition = screenHeight - 50; // position Y at the beginning
            xPosition = random.nextFloat(500, 580);
        } else {
            yPosition = screenHeight + stripeHeight + 20;
            xPosition = random.nextFloat(530, 580);// above screen
        }
        elapsedTime = 0; // Reset of elapsed time
    }

    public void draw(SpriteBatch batch) {
        batch.draw(bushesRegion, getX(), getY(),
                bushesRegion.getRegionWidth() * BieganBrickRaceGame.sc,
                bushesRegion.getRegionHeight() * BieganBrickRaceGame.sc);
    }
    public void setSpeed(float bushesSpeed) {
        this.bushesSpeed = bushesSpeed;
    }
}
