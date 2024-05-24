package com.biegan.game.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.biegan.game.sprites.EnemyCar;
import java.util.*;

public class EnemyCarManager {

    private List<EnemyCar> enemyCars;
    private Texture enemyTexture;
    private float[] trackPositions; // X positions for each track
    private Random random;

    // Definition of local constants
    private static final float MIN_DELAY_TIME = 0.5f;
    private static final float MAX_DELAY_TIME = 6.0f;
    private static final float MIN_DIFFERENCE = 0.8f;

    public EnemyCarManager() {
        enemyCars = new ArrayList<>();
        enemyTexture = new Texture("C64_Style_Racing_Game/2D/car-enemy.png");
        random = new Random();
        trackPositions = new float[]{120, 230, 330, 430};

        for (float xPosition : trackPositions) {
            EnemyCar enemyCar = new EnemyCar(enemyTexture, xPosition);
            enemyCar.setDelayTime(generateUniqueDelayTime());
            enemyCars.add(enemyCar);
        }
    }

    public void update(float dt) {
        for (EnemyCar enemyCar : enemyCars) {
            enemyCar.enemyCarUpdate(dt);
        }
    }

    public float generateUniqueDelayTime() {
        float delayTime;
        boolean isUnique;
        do {
            isUnique = true;
            delayTime = MIN_DELAY_TIME + random.nextFloat() * (MAX_DELAY_TIME - MIN_DELAY_TIME);
            for (EnemyCar car : enemyCars) {
                if (Math.abs(car.getDelayTime() - delayTime) < MIN_DIFFERENCE) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);
        return delayTime;
    }

    public List<EnemyCar>  getEnemyCars() {
        return  enemyCars;
    }
}
