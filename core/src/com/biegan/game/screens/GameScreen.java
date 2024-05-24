package com.biegan.game.screens;

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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.scenes.Hud;
import com.biegan.game.sprites.Bushes;
import com.biegan.game.sprites.EnemyCar;
import com.biegan.game.sprites.Player;
import com.biegan.game.sprites.RoadStripes;
import com.biegan.game.tools.EnemyCarManager;

public class GameScreen implements Screen {

    // main variables
    private BieganBrickRaceGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

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

    private boolean gameOver = false;
    public static int score = 0;
    private Music music;

    public GameScreen(BieganBrickRaceGame game) {
        score = 0;
        this.game = game;
        playerTexture = new Texture("C64_Style_Racing_Game/2D/car-player.png");
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(worldWidth, worldHeight , gameCam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("bbg.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 * BieganBrickRaceGame.sc);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        hud = new Hud(game.batch, this);
        enemyCarManager = new EnemyCarManager();
        bushes = new Bushes(new Texture("C64_Style_Racing_Game/2D/tree.png"), 550f);
        roadStripes = new RoadStripes(new Texture("C64_Style_Racing_Game/2D/lane.png"));
        player = new Player(playerTexture);
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

        if (gameOver) {
            game.setScreen(new GameOverScreen(game));
        }

        renderer.render(new int[] {map.getLayers().getIndex("background")});
        roadStripes.draw(game.batch);
        renderer.render(new int[] {map.getLayers().getIndex("ui")});

        for (EnemyCar car: enemyCarManager.getEnemyCars()) {
            car.draw(game.batch);
        }

        player.draw(game.batch);
        bushes.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.act(delta);
        hud.stage.draw();
    }

    public void update(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            BieganBrickRaceGame.manager.get("enginestart.mp3", Sound.class).play();
            start = false;

            // Ustaw timer na 1 sekundę
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    start = true;
                }
            }, 1.6f);
        }
        if (start) {
            music = BieganBrickRaceGame.manager.get("engine.mp3", Music.class);
            music.setLooping(true);
            music.play();
            roadStripes.updateRoadStripes(dt);
            bushes.bushesUpdate(dt);
            enemyCarManager.update(dt);
            for (EnemyCar car : enemyCarManager.getEnemyCars()) {
                // Use getBoundingRectangle() to get actual Y position
                float carY = car.getBoundingRectangle().y;
                float carHeight = car.getBoundingRectangle().height * BieganBrickRaceGame.sc;
                if (car.getY() / BieganBrickRaceGame.sc  < 0 && !car.hasScored()) {
                    BieganBrickRaceGame.manager.get("high-speed-2-192899.mp3", Sound.class).play();
                    score += 50;
                    car.setScored(true);
                }
            }

            //collision check
            for (EnemyCar car : enemyCarManager.getEnemyCars()) {
                if (car.getBoundingRectangle().overlaps(player.getBoundingRectangle()))
                    //collision
                    gameOver = true;
            }
            if (gameOver) music.stop();
            hud.update(dt);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) player.updatePlayerPosLeft();
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) player.updatePlayerPosRight();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) increaseGameSpeed();

        //update our gamecam with correct coordinates after changes
        gameCam.update();
        //tell our renderer to draw only what our camera can see in aur game world
        renderer.setView(gameCam);
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

    }

    public static int getScore() {
        return score;
    }

    public void increaseGameSpeed() {
        BieganBrickRaceGame.gameSpeed += 20; // increase gamespeed
        roadStripes.setSpeed(BieganBrickRaceGame.gameSpeed);
        bushes.setSpeed(BieganBrickRaceGame.gameSpeed);
        for (EnemyCar car : enemyCarManager.getEnemyCars()) {
            car.setSpeed(BieganBrickRaceGame.gameSpeed - 50);
        }
        hud.setGameSpeed();
    }
}
