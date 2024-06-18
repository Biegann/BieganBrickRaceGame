package com.biegan.game.View.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Controller.GameController;
import com.biegan.game.Model.GameModel;
import com.biegan.game.View.Hud;
import com.biegan.game.View.sprites.*;
import com.biegan.game.Model.EnemyCarManager;

public class GameScreen implements Screen {

    // main variables
    private BieganBrickRaceGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private GameController controller;
    private GameModel model;

    private float worldWidth;
    private  float worldHeight;
    //tiledMap variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Hud hud;
    private Texture playerTexture;
    private Player player;
    private RoadStripes roadStripes;
    private EnemyCarManager enemyCarManager;
    private Bushes bushes;
    private Array<Explosion> explosions;

    public int score = 0;

    public GameScreen(BieganBrickRaceGame game, GameController controller, GameModel model, Hud hud) {

        this.game = game;
        this.controller = controller;
        this.worldWidth = controller.getWorldWidth();
        this.worldHeight = controller.getWorldHeight();
        this.model = model;
        this.gameCam = new OrthographicCamera();
        this.gamePort = new FitViewport(worldWidth, worldHeight , gameCam);

        this.score = model.getScore();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("bbg.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 * BieganBrickRaceGame.sc);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        this.hud = hud;
        this.enemyCarManager = controller.getEnemyCarManager();
        this.roadStripes = controller.getRoadStripes();
        this.bushes = controller.getBushes();
        this.player = controller.getPlayer();
        this.explosions = controller.getExplosions();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // separate our update logic from render
        controller.update(delta);

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameCam.update();
        renderer.setView(gameCam);
        renderer.render();

        if (explosions != null) {
            game.batch.begin();

            roadStripes.draw(game.batch);

            for (EnemyCar car : enemyCarManager.getEnemyCars()) {
                car.draw(game.batch);
            }

            player.draw(game.batch);
            bushes.draw(game.batch);

            for (Explosion explosion : explosions) {
                explosion.render(game.batch);
            }

            game.batch.end();
        }
        game.batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().act(delta);
        hud.getStage().draw();
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
        bushes.dispose();
        map.dispose();
        renderer.dispose();
        for (Explosion explosion : explosions) {
            explosion.dispose();
        }
    }

    public int getScore() {
        return score;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
