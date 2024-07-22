package com.biegan.game.View.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.biegan.game.BieganBrickRaceGame;

public class EnemyCar extends Sprite {

    private Texture enemyTexture = new Texture("C64_Style_Racing_Game/2D/car-enemy.png");
    private final TextureRegion enemyRegion;
    private final float enemyHeight;

    public EnemyCar(float xPosition, float yPosition) {
        this.enemyRegion = new TextureRegion(enemyTexture);

        float stripeWidth = enemyRegion.getRegionWidth() * BieganBrickRaceGame.sc;
        float stripeHeight = enemyRegion.getRegionHeight() * BieganBrickRaceGame.sc;
        enemyHeight = stripeHeight;
        setBounds(xPosition, yPosition, stripeWidth, stripeHeight); // Set initial bounds and positions
    }

    public void draw(SpriteBatch batch) {
        batch.draw(enemyRegion, getX(), getY(),
                enemyRegion.getRegionWidth() * BieganBrickRaceGame.sc,
                enemyRegion.getRegionHeight() * BieganBrickRaceGame.sc);
    }

    public TextureRegion getEnemyRegion() {
        return enemyRegion;
    }

    public float getEnemyHeight() {
        return enemyHeight;
    }
}
