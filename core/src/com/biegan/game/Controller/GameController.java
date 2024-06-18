package com.biegan.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Model.EnemyCarManager;
import com.biegan.game.Model.GameModel;
import com.biegan.game.View.Hud;
import com.biegan.game.View.screens.GameOverScreen;
import com.biegan.game.View.screens.GameScreen;
import com.biegan.game.View.sprites.*;

public class GameController {

    public enum GAME_STATE
    {
        START,
        NOT_STARTED,
        STARTED,
        OVER
    };

    private GameModel model;
    private GAME_STATE state;
    private BieganBrickRaceGame game;
    private Screen currScreen;
    private GameScreen gameScreen;
    private GameInput gameInput;
    private OverInput overInput;
    private int score;
    private int lastSpeedIncreaseScore = 0;
    private boolean isGameStarted;
    private float gameSpeed;

    private RoadStripes roadStripes;
    private Bushes bushes;
    private EnemyCarManager enemyCarManager;
    private Player player;
    private Array<Explosion> explosions;
    private Hud hud;
    private PositionsX positionsX;

    private final float worldWidth = 800; // Width of game world
    private final float worldHeight = 500; // Height of game world

    public GameController(BieganBrickRaceGame game)
    {
        this.game = game;
        init();
        game.setScreen(currScreen);
    }

    private void init() {
        this.positionsX = new PositionsX();
        this.model = new GameModel(this);
        this.gameSpeed = model.getGameSpeed();
        this.state = GAME_STATE.START;
        this.enemyCarManager = new EnemyCarManager(model, this);
        this.roadStripes = new RoadStripes(this);
        this.bushes = new Bushes(this);
        this.player = new Player(this);
        this.hud = new Hud(game.batch, this);
        this.gameScreen = new GameScreen(game, this, model, hud);
        this.explosions = new Array<>();
        this.currScreen = gameScreen;
        // Create inputProcessor
        this.gameInput = new GameInput(this, player);
        this.overInput = new OverInput(this);
    }

    public void update(float dt) {

        switch (state)
        {
            case START:
                startState();
                break;
            case NOT_STARTED:
                notStartedState();
                break;
            case STARTED:
                startedState(dt);
                break;
            case OVER:
                overState(dt);
                break;
            default:
                break;
        }
    }

    private void startState() {
        currScreen = new GameScreen(game, this, model, hud);
        game.setScreen(currScreen);
        state = GAME_STATE.NOT_STARTED;
    }

    private void notStartedState() {
        Gdx.input.setInputProcessor(gameInput);
        gameInput.handleInput();
        // inputProcessor changes this to STARTED
    }

    private void startedState(float dt) {
        gameInput.handleInput();
        updateScreen(dt);
        if (!isGameStarted) {
            startGame();
            isGameStarted = true;
        }
    }

    private void overState(float dt) {
        updateExplosions(dt);
        // Check all explosions finished
        if (explosions.size == 0) {
            game.setScreen(new GameOverScreen(game));
        }
    }

    private void startGame() {
        game.assetsMan.playSound("enginestart.mp3");
        // Set timer to start main game after engine start sound
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.assetsMan.playMusic("engine.mp3", true);
                state = GAME_STATE.STARTED;
                roadStripes.setMoving(true);
                bushes.setMoving(true);
                enemyCarManager.setMoving(true);
            }
        }, 1.6f);
    }

    private void checkCollisions() {
        for (EnemyCar car : enemyCarManager.getEnemyCars()) {
            if (car.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                //collision
                handleCollision(car);
                break;
            }
        }
        for (EnemyCar car : enemyCarManager.getEnemyCars()) {
            if (car.getY() / BieganBrickRaceGame.sc < 0 && !model.hasScored()) {
                handleScore();
            }
        }

        if (score % 500 == 0 && score != 0 && score != lastSpeedIncreaseScore) {
            increaseGameSpeed();
            lastSpeedIncreaseScore = score;
        }
    }

    private void handleCollision(EnemyCar car) {
        game.assetsMan.playSound("explosion.mp3");
        explosions.add(new Explosion(car.getX(), car.getY(), 0));
        explosions.add(new Explosion(player.getX(), player.getY(), 0));
        game.assetsMan.stopMusic("engine.mp3");
        setState(GAME_STATE.OVER);
    }

    private void handleScore() {
        game.assetsMan.playSound("high-speed-2-192899.mp3");
        score += 50;
        model.setScored(true);
    }

    private void updateExplosions(float dt) {
        for (int i = 0; i < explosions.size; i++) {
            explosions.get(i).update(dt);
            if (explosions.get(i).isFinished()) {
                explosions.removeIndex(i);
            }
        }
    }

    public void setState(GAME_STATE state) {
        this.state = state;
    }

    public GAME_STATE getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    public void increaseGameSpeed() {
        game.assetsMan.playSound("speedUp.mp3");
        model.setGameSpeed(model.getGameSpeed() + 20); // increase gamespeed
        roadStripes.setSpeed(model.getGameSpeed());
        bushes.setSpeed(model.getGameSpeed());
        gameSpeed = model.getGameSpeed();
        model.setEnemySpeed(gameSpeed - 50);
        hud.incSpeedDisplayValue();
    }

    public PositionsX getPositionX() {
        return positionsX;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public void playerSound () {
        game.assetsMan.playSound("playersound.mp3");
    }

    public class PositionsX {
        private final float[] positionsX = {120, 230, 330, 430};

        public float[] getPositionsX() {
            return positionsX;
        }
    }

    public void updateScreen (float dt) {
        model.update(dt);
        roadStripes.updateRoadStripes(dt);
        bushes.bushesUpdate(dt);
        enemyCarManager.update(dt);
        hud.update(dt);
        checkCollisions();
        updateExplosions(dt);
    }

    public RoadStripes getRoadStripes() {
        return roadStripes;
    }

    public Bushes getBushes() {
        return bushes;
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyCarManager getEnemyCarManager() {
        return enemyCarManager;
    }

    public Array<Explosion> getExplosions() {
        return explosions;
    }

    public OverInput getOverInput() {
        return overInput;
    }

    public float getGameSpeed() {
        return gameSpeed;
    }

    public void restart() {
        setState(GAME_STATE.START);
        model.reset();
        gameSpeed = model.getGameSpeed();
        score = 0;
        isGameStarted = false;
        enemyCarManager.setMoving(false);
        bushes.setMoving(false);
        roadStripes.setMoving(false);
        hud = new Hud(game.batch, this);
        explosions.clear();
        enemyCarManager.resetEnemyCars();
        roadStripes.setSpeed(gameSpeed);
        bushes.setSpeed(gameSpeed);
        game.setScreen(new GameScreen(game, this, model, hud));
        Gdx.input.setInputProcessor(gameInput);
    }
}
