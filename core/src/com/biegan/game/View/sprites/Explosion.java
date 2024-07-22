package com.biegan.game.View.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Explosion {
    private final Animation<TextureRegion> explosionAnimation;
    private  float stateTime;
    private final float x;
    private final float y;

    public Explosion(float x, float y, float stateTime) {
        this.x = x;
        this.y = y;
        this.stateTime = 0f;

        Array<TextureRegion> frames = new Array<>();
        for (int i =1; i < 5; i++) {
            frames.add(new TextureRegion(new Texture("exp" + i + ".png")));
        }

        explosionAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);
    }
    public void update(float delta) {
        stateTime += delta;
    }
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = explosionAnimation.getKeyFrame(stateTime);
        batch.draw(currentFrame, x, y);
    }

    public boolean isFinished() {
        return explosionAnimation.isAnimationFinished(stateTime);
    }

    public void dispose() {
        // Freeing resources
        for (TextureRegion region : explosionAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
    }
}
