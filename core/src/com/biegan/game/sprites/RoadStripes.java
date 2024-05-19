package com.biegan.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.biegan.game.BieganBrickRaceGame;

public class RoadStripes {

    private float roadSpeed = BieganBrickRaceGame.gameSpeed;
    private Texture roadTexture;
    private TextureRegion roadStripe; // Pojedynczy region tekstury pasa

    private float[][] yPositions; // Tablica pozycji y dla każdej kolumny i pasa
    private float[] xPositions; // Tablica pozycji x dla każdej kolumny
    private int numStripesPerColumn = 4;
    private int numColumns = 2;

    public RoadStripes(Texture roadTexture) {
        this.roadTexture = roadTexture;
        this.roadStripe = new TextureRegion(roadTexture);

        yPositions = new float[numColumns][numStripesPerColumn];
        xPositions = new float[numColumns];

        // Oblicz szerokość i wysokość pojedynczego pasa
        float stripeWidth = roadStripe.getRegionWidth() * BieganBrickRaceGame.sc + 70;
        float stripeHeight = roadStripe.getRegionHeight() * BieganBrickRaceGame.sc + 70;

        // Ustaw początkowe pozycje x i y pasów
        for (int col = 0; col < numColumns; col++) {
            xPositions[col] = 145 + col * (stripeWidth + 35); //  odstęp między kolumnami
            for (int row = 0; row < numStripesPerColumn; row++) {
                yPositions[col][row] = row * 1 * stripeHeight; // Początkowa pozycja z odstępem między pasami
            }
        }
    }

    public void updateRoadStripes(float dt) {
        float stripeHeight = roadStripe.getRegionHeight() * BieganBrickRaceGame.sc + 70; // Wysokość pasa
        //    float screenHeight = Gdx.graphics.getHeight(); // Wysokość ekranu

        for (int col = 0; col < numColumns; col++) {
            for (int row = 0; row < numStripesPerColumn; row++) {
                yPositions[col][row] -= roadSpeed * dt;
                if (yPositions[col][row] < -stripeHeight) { // Sprawdź, czy pas wyszedł poza ekran
                    float highestYInColumn = yPositions[col][0]; // Znajdź najwyższy pas w kolumnie
                    for (int j = 1; j < numStripesPerColumn; j++) {
                        highestYInColumn = Math.max(highestYInColumn, yPositions[col][j]);
                    }
                    yPositions[col][row] = highestYInColumn + 1 * stripeHeight; // Ustaw pozycję nowego pasa nad najwyższym
                }
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (int col = 0; col < numColumns; col++) {
            for (int row = 0; row < numStripesPerColumn; row++) {
                batch.draw(roadStripe, xPositions[col], yPositions[col][row],
                        roadStripe.getRegionWidth() * BieganBrickRaceGame.sc,
                        roadStripe.getRegionHeight() * BieganBrickRaceGame.sc);
            }
        }
    }
}