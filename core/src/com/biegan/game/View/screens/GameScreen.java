package com.biegan.game.View.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Controller.AssetsMan;
import com.biegan.game.Controller.GameController;
import com.biegan.game.View.inputProcessors.GameInput;
import com.biegan.game.View.sprites.*;

public class GameScreen implements Screen {

    // main variables
    private OrthographicCamera gameCam;
    private final Viewport gamePort;
    private final GameController controller;
    public SpriteBatch batch;
    private final float worldWidth = 800; // Width of game world
    private final float worldHeight = 500; // Height of game world

    //tiledMap variables
    private TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    private final Player player;
    private final RoadStripes roadStripes;
    private final Bushes bushes;
    private final Array<Explosion> explosions;
    private final AssetsMan assetsMan;
    private final GameInput gameInput;

    private final Stage stage;
    private Viewport viewport;

    private final Label scoreLabel;
    private final Label gameSpeedLabel;

    public GameScreen(GameController controller) {

        Gdx.app.log("GameScreen", "Inicjalizacja GameScreen.");
        batch = new SpriteBatch();
        this.controller = controller;
        this.assetsMan = controller.getAssetsMan();
        this.gameCam = new OrthographicCamera();
        this.gamePort = new FitViewport(worldWidth, worldHeight , gameCam);

        this.player = controller.getPlayer();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("bbg.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 * BieganBrickRaceGame.sc);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        this.roadStripes = new RoadStripes(controller);
        this.bushes = new Bushes(controller);
        this.gameInput = new GameInput(controller);
        this.explosions = new Array<>();

        gameCam = new OrthographicCamera();
        viewport = new FitViewport(worldWidth, worldHeight, gameCam);
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setPosition(720, 500);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.2f); // Scale up font
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        scoreLabel = new Label(String.format("Score : %06d", controller.getScore()), labelStyle);
        gameSpeedLabel = new Label(String.format("Speed : %04d",(int) controller.getGameSpeed()), labelStyle);

        table.add(scoreLabel).expandX().fillX().pad(10);
        table.row();
        table.add(gameSpeedLabel).expandX().fillX().pad(10);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(gameInput);
    }

    @Override
    public void render(float delta) {
        // separate our update logic from render
        controller.update(delta);
        gameInput.handleInput();
        updateExplosions(delta);

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameCam.update();
        renderer.setView(gameCam);
        renderer.render();

        batch.begin();
        roadStripes.draw(batch);


        for (EnemyCar car : controller.getEnemyCars()) {
            car.draw(batch);
        }

        player.draw(batch);
        bushes.draw(batch);

        for (Explosion explosion : explosions) {
            explosion.render(batch);
        }

        batch.end();

        batch.setProjectionMatrix(getStage().getCamera().combined);
        getStage().act(delta);
        getStage().draw();
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

    public void incSpeedDisplayValue() {
        int gameSpeed =  (int) controller.getGameSpeed();
        gameSpeedLabel.setText(String.format("Speed : %04d", gameSpeed));
    }

    public void update(float delta) {
        int score = controller.getScore();
        bushes.bushesUpdate(delta);
        roadStripes.updateRoadStripes(delta);
        scoreLabel.setText(String.format("Score : %06d", score));

        stage.act(delta);
    }

    public Stage getStage() {
        return stage;
    }

    public Array<Explosion> getExplosions() {
        return explosions;
    }

    public void resetUI() {
        scoreLabel.setText(String.format("Score : %06d", controller.getScore()));
        gameSpeedLabel.setText(String.format("Speed : %04d", (int) controller.getGameSpeed()));
    }

    public void increaseGameSpeed() {
        assetsMan.playSound("speedUp.mp3");
        roadStripes.setSpeed(controller.getGameSpeed());
        bushes.setSpeed(controller.getGameSpeed());
        incSpeedDisplayValue();
    }

    public void updateExplosions(float dt) {
        for (int i = 0; i < explosions.size; i++) {
            explosions.get(i).update(dt);
            if (explosions.get(i).isFinished()) {
                explosions.removeIndex(i);
            }
        }
    }

    public boolean isExplosionsFinished() {
        return getExplosions().size == 0;
    }

    public void startGameView() {
        assetsMan.playSound("enginestart.mp3");
        // Set timer to start main game after engine start sound
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                assetsMan.playMusic("engine.mp3", true);
                controller.setState(GameController.GAME_STATE.STARTED);
                roadStripes.setMoving(true);
                bushes.setMoving(true);
            }
        }, 1.6f);
    }

    public void resetView() {
        bushes.setMoving(false);
        roadStripes.setMoving(false);
        getExplosions().clear();
        roadStripes.setSpeed(controller.getGameSpeed());
        bushes.setSpeed(controller.getGameSpeed());
        resetUI();
        Gdx.input.setInputProcessor(gameInput);
    }

    @Override
    public void dispose() {
        batch.dispose();
        bushes.dispose();
        map.dispose();
        renderer.dispose();
        stage.dispose();
        for (Explosion explosion : explosions) {
            explosion.dispose();
        }
    }
}
