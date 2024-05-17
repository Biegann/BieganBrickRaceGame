package com.biegan.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.biegan.game.BieganBrickRaceGame;

public class Player extends Sprite {

    private Texture playerTexture;
    private TextureRegion playerSprite;
    private float[] positions = {120, 230, 330, 430}; // Możliwe pozycje na osi X
    private int currentPositionIndex = 1; // Początkowa pozycja (230)

    public Player(Texture playerTexture) {
        this.playerTexture = playerTexture;
        this.playerSprite = new TextureRegion(playerTexture);

        float stripeWidth = playerSprite.getRegionWidth() * BieganBrickRaceGame.sc + 70;
        float stripeHeight =playerSprite.getRegionHeight() * BieganBrickRaceGame.sc + 70;

        setBounds(positions[currentPositionIndex], 0, stripeWidth, stripeHeight); // Ustaw początkowe wymiary i pozycję
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
