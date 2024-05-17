package com.biegan.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.controller.Controller;
import com.biegan.game.sprites.Player;
import com.biegan.game.sprites.RoadStripes;

public class GameScreen implements Screen {

    // main variables
    private BieganBrickRaceGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private final float worldWidth = 800; // Szerokość świata gry
    private final float worldHeight = 500; // Wysokość świata gry

    //tiledMap variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Texture playerTexture;

    private Controller controller;
    private RoadStripes roadStripes;
    private Player player;

    public GameScreen(BieganBrickRaceGame game) {
        this.game = game;
        playerTexture = new Texture("C64_Style_Racing_Game/2D/car-player.png");
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(worldWidth, worldHeight , gameCam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("bbg.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 * BieganBrickRaceGame.sc);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        controller = new Controller();
        Gdx.input.setInputProcessor(controller);

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

        // Wyczyść ekran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        renderer.render(new int[] {map.getLayers().getIndex("background")});
        roadStripes.draw(game.batch);
        renderer.render(new int[] {map.getLayers().getIndex("ui")});
        player.draw(game.batch);
        game.batch.end();

    }

    public void update(float dt) {

        if (controller.upPressed) roadStripes.updateRoadStripes(dt);
        if ((Gdx.input.isKeyJustPressed(Input.Keys.LEFT))) player.updatePlayerPosLeft();
        if ((Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))) player.updatePlayerPosRight();

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
}
