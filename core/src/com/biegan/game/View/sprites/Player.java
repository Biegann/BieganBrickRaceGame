package com.biegan.game.View.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.biegan.game.BieganBrickRaceGame;

public class Player extends Sprite {

    private Texture playerTexture;
    private TextureRegion playerSprite;
    private float[] positions = {120, 230, 330, 430}; // Possible positions on the X axis
    private int currentPositionIndex = 1;

    public Player(Texture playerTexture) {
        this.playerTexture = playerTexture;
        this.playerSprite = new TextureRegion(playerTexture);

        float stripeWidth = playerSprite.getRegionWidth() * BieganBrickRaceGame.sc;
        float stripeHeight =playerSprite.getRegionHeight() * BieganBrickRaceGame.sc;

        setBounds(positions[currentPositionIndex], 0, stripeWidth, stripeHeight);
    }

    public void updatePlayerPosLeft() {
        if (currentPositionIndex > 0) {
            currentPositionIndex--;
            setX(positions[currentPositionIndex]);
        }
    }
    public void updatePlayerPosRight() {
        if (currentPositionIndex < positions.length - 1) {
            currentPositionIndex++;
            setX(positions[currentPositionIndex]);
        }
    }
    public void draw(SpriteBatch batch) {
        batch.draw(playerSprite, getX(), getY(),
                playerSprite.getRegionWidth() * BieganBrickRaceGame.sc,
                playerSprite.getRegionHeight() * BieganBrickRaceGame.sc);
    }
}
