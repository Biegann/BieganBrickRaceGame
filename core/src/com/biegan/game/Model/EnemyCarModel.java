package com.biegan.game.Model;

import java.util.Random;

class EnemyCarModel {
    private float enemySpeed;
    private float yPosition;
    private float xPosition;
    private Random random = new Random();
    private float delayTime;
    public float elapsedTime;
    private boolean scored = false; // Flag to track scores
    private float screenHeight;
    private GameModel model;


}
