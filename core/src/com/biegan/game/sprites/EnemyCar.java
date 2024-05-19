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
    private float enemySpeed = BieganBrickRaceGame.gameSpeed;
    private float yPosition;
    private float xPosition;
    private Random random = new Random();
    private float delayTime; // Czas opóźnienia
    private float elapsedTime; // Upływający czas

    private float screenHeight;

    public EnemyCar(Texture enemyTexture, float xPosition) {
        this.enemyTexture = enemyTexture;
        this.enemyRegion = new TextureRegion(enemyTexture);
        this.xPosition = xPosition;

     //   this.yPosition = random.nextFloat(350) + 100; // Losowanie y z zakresu 100-450
        screenHeight = Gdx.graphics.getHeight(); // Inicjalizacja w konstruktorze
        resetPosition(true);

        float stripeWidth = enemyRegion.getRegionWidth() * BieganBrickRaceGame.sc;
        float stripeHeight = enemyRegion.getRegionHeight() * BieganBrickRaceGame.sc;
        setBounds(xPosition, yPosition, stripeWidth, stripeHeight); // Ustaw początkowe wymiary i pozycję
    }

    public void enemyCarUpdate(float dt) {
        if (elapsedTime >= delayTime) {
            yPosition -= enemySpeed * dt;
        //    if (yPosition < -getHeight()) {
              if (yPosition + getHeight() < 0) {
                resetPosition(false);
            }
        } else {
            elapsedTime += dt; // Zwiększa czas, jeśli jest opóźnienie
        }
        setPosition(xPosition, yPosition); // Aktualizacja pozycji sprite'a
    }

    private void resetPosition(boolean initial) {
        float stripeHeight = enemyRegion.getRegionHeight() * BieganBrickRaceGame.sc;
        if (initial) {
            yPosition = screenHeight + random.nextFloat(screenHeight) + 100; // Losowa pozycja Y na początku gry
        } else {
            yPosition = screenHeight + stripeHeight; // + random.nextFloat(150); // Losowanie y z zakresu 0-150 powyżej ekranu
        }
        delayTime = random.nextFloat() * 2 + 0.5f; // Losowe opóźnienie między 1 a 3 sekundami
        elapsedTime = 0; // Reset upływającego czasu
    }

    public void draw(SpriteBatch batch) {
        batch.draw(enemyRegion, getX(), getY(),
                enemyRegion.getRegionWidth() * BieganBrickRaceGame.sc,
                enemyRegion.getRegionHeight() * BieganBrickRaceGame.sc);
    }
}