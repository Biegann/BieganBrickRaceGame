package com.biegan.game.View.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Controller.GameController;

public class RoadStripes {

    private float roadSpeed;
    private Texture roadTexture = new Texture("C64_Style_Racing_Game/2D/lane.png");
    private TextureRegion roadStripe; // Single region of lane texture

    private float[][] yPositions; // An array of y positions far each column and lane
    private float[] xPositions; // An array of x positions for each column
    private int numStripesPerColumn = 4;
    private int numColumns = 2;
    private GameController controller;
    private boolean isMoving;

    public RoadStripes(GameController controller) {
        isMoving = false;
        this.roadStripe = new TextureRegion(roadTexture);
        this.controller = controller;
        roadSpeed = controller.getGameSpeed();

        yPositions = new float[numColumns][numStripesPerColumn];
        xPositions = new float[numColumns];

        // Calculate the width and height of a single lane
        float stripeWidth = roadStripe.getRegionWidth() * BieganBrickRaceGame.sc + 70;
        float stripeHeight = roadStripe.getRegionHeight() * BieganBrickRaceGame.sc + 70;

        // Set the starting x and y positions of the lanes
        for (int col = 0; col < numColumns; col++) {
            xPositions[col] = 145 + col * (stripeWidth + 35); //  spacing between columns
            for (int row = 0; row < numStripesPerColumn; row++) {
                yPositions[col][row] = row * 1 * stripeHeight; // Starting position with space between lanes
            }
        }
    }

    public void updateRoadStripes(float dt) {
        float stripeHeight = roadStripe.getRegionHeight() * BieganBrickRaceGame.sc + 70; // Height of lane
        if (isMoving()) {
            for (int col = 0; col < numColumns; col++) {
                for (int row = 0; row < numStripesPerColumn; row++) {
                    yPositions[col][row] -= roadSpeed * dt;
                    if (yPositions[col][row] < -stripeHeight) { // Check if the belt has gone beyond the screen
                        float highestYInColumn = yPositions[col][0]; // Find the highest lane in the column
                        for (int j = 1; j < numStripesPerColumn; j++) {
                            highestYInColumn = Math.max(highestYInColumn, yPositions[col][j]);
                        }
                        yPositions[col][row] = highestYInColumn + 1 * stripeHeight; // Position the new lane above the highest one
                    }
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

    public void setSpeed(float roadSpeed) {
        this.roadSpeed = roadSpeed;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isMoving() {
        return isMoving;
    }
}
