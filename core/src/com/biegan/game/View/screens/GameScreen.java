package com.biegan.game.View.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Controller.GameController;
import com.biegan.game.View.Hud;
import com.biegan.game.View.sprites.*;
import com.biegan.game.Model.EnemyCarManager;

public class GameScreen implements Screen {

    // main variables
    private BieganBrickRaceGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private GameController controller;

    private final float worldWidth = 800; // Width of game world
    private final float worldHeight = 500; // Height of game world

    //tiledMap variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private boolean start;

    private Hud hud;
    private Texture playerTexture;
    private Player player;
    private RoadStripes roadStripes;
    private EnemyCarManager enemyCarManager;
    private Bushes bushes;
    private Array<Explosion> explosions;

    private boolean gameOver = false;
    private boolean gamePaused = false;
    private boolean isGameStarted = false;
    public int score = 0;
    private Music music;

    public GameScreen(BieganBrickRaceGame game, GameController controller) {
        score = 0;
        this.game = game;
        playerTexture = new Texture("C64_Style_Racing_Game/2D/car-player.png");
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(worldWidth, worldHeight , gameCam);
        this.controller = controller;

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("bbg.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 * BieganBrickRaceGame.sc);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        hud = new Hud(game.batch, this);
        enemyCarManager = new EnemyCarManager();
        bushes = new Bushes(new Texture("C64_Style_Racing_Game/2D/tree.png"));
        roadStripes = new RoadStripes(new Texture("C64_Style_Racing_Game/2D/lane.png"));
        player = new Player(playerTexture);
        explosions = new Array<>();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // separate our update logic from render
        update(delta);

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        renderer.render();
        roadStripes.draw(game.batch);

        for (EnemyCar car: enemyCarManager.getEnemyCars()) {
            car.draw(game.batch);
        }

        player.draw(game.batch);
        bushes.draw(game.batch);

        for (Explosion explosion : explosions) {
            explosion.render(game.batch);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.act(delta);
        hud.stage.draw();
        //check all explosions finish
        if (gameOver && explosions.size == 0) {
            game.setScreen(new GameOverScreen(game, gamePort, score));
        }
    }

    public void update(float dt) {
        if (!gamePaused) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !isGameStarted) {
                game.assetsMan.get("enginestart.mp3", Sound.class).play();
                start = false;
                isGameStarted = true;

                // Set timer
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        start = true;
                    }
                }, 1.6f);
            }
            if (start) {
                music = game.assetsMan.get("engine.mp3", Music.class);
                music.setLooping(true);
                music.play();
                roadStripes.updateRoadStripes(dt);
                bushes.bushesUpdate(dt);
                enemyCarManager.update(dt);
                for (EnemyCar car : enemyCarManager.getEnemyCars()) {
                    // Use getBoundingRectangle() to get actual Y position
                    float carY = car.getBoundingRectangle().y;
                    float carHeight = car.getBoundingRectangle().height * BieganBrickRaceGame.sc;
                    if (car.getY() / BieganBrickRaceGame.sc < 0 && !car.hasScored()) {
                        game.assetsMan.get("high-speed-2-192899.mp3", Sound.class).play();
                        score += 50;
                        car.setScored(true);
                    }
                }

                //collision check
                for (EnemyCar car : enemyCarManager.getEnemyCars()) {
                    if (car.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                        //collision
                        game.assetsMan.get("explosion.mp3", Sound.class).play();
                        gameOver = true;
                        gamePaused = true;
                        explosions.add(new Explosion(car.getX(), car.getY(), 0));
                        explosions.add(new Explosion(player.getX(), player.getY(), 0));
                        music.stop();
                        break;
                    }
                }

                hud.update(dt);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                game.assetsMan.get("playersound.mp3", Sound.class).play();
                player.updatePlayerPosLeft();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                game.assetsMan.get("playersound.mp3", Sound.class).play();
                player.updatePlayerPosRight();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) increaseGameSpeed();

            //update our gamecam with correct coordinates after changes
            gameCam.update();
            //tell our renderer to draw only what our camera can see in aur game world
            renderer.setView(gameCam);
        }
        else {
            // Actualization of only explosions when game is paused
            for (int i = 0; i < explosions.size; i++) {
                explosions.get(i).update(dt);
                if (explosions.get(i).isFinished()) {
                    explosions.removeIndex(i);
                }
            }
        }
    }
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        playerTexture.dispose();
        map.dispose();
        renderer.dispose();
        for (Explosion explosion : explosions) {
            explosion.dispose();
        }
    }

    public int getScore() {
        return score;
    }

    public void increaseGameSpeed() {
        game.assetsMan.get("speedUp.mp3", Sound.class).play();
        controller.setGameSpeed(controller.getGameSpeed() + 20); // increase gamespeed
        roadStripes.setSpeed(controller.getGameSpeed());
        bushes.setSpeed(controller.getGameSpeed());
        for (EnemyCar car : enemyCarManager.getEnemyCars()) {
            car.setSpeed(controller.getGameSpeed() - 50);
        }
        hud.incSpeedDisplayValue();
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }
}
