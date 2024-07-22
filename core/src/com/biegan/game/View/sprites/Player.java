package com.biegan.game.View.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Controller.GameController;

public class Player extends Sprite {

    private Texture playerTexture;
    private final TextureRegion playerSprite;
    private GameController controller;
    private final float[] positionsX; ; // Possible positions on the X axis
    private int currentPositionIndex = 1;

    public Player(GameController controller) {
        this.controller = controller;
        this.positionsX = controller.getPositionX();
        playerTexture = new Texture("C64_Style_Racing_Game/2D/car-player.png");
        this.playerSprite = new TextureRegion(playerTexture);

        float stripeWidth = playerSprite.getRegionWidth() * BieganBrickRaceGame.sc;
        float stripeHeight =playerSprite.getRegionHeight() * BieganBrickRaceGame.sc;

        setBounds(positionsX[currentPositionIndex], 0, stripeWidth, stripeHeight);
    }

    public void updatePlayerPosLeft() {
        if (currentPositionIndex > 0) {
            currentPositionIndex--;
            setX(positionsX[currentPositionIndex]);
        }
    }
    public void updatePlayerPosRight() {
        if (currentPositionIndex < positionsX.length - 1) {
            currentPositionIndex++;
            setX(positionsX[currentPositionIndex]);
        }
    }
    public void draw(SpriteBatch batch) {
        batch.draw(playerSprite, getX(), getY(),
                playerSprite.getRegionWidth() * BieganBrickRaceGame.sc,
                playerSprite.getRegionHeight() * BieganBrickRaceGame.sc);
    }
}
