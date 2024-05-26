package com.biegan.game.View.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.biegan.game.BieganBrickRaceGame;
import com.biegan.game.Controller.GameController;

public class GameOverScreen implements Screen {

    private BieganBrickRaceGame game;
    private BitmapFont font;
    private GameController controller;

    private Stage stage;
    private Label gameOverLabel;
    private Label restartLabel;
    private Label exitLabel;
    private Label scoreLabel;
    private int score;

    public GameOverScreen(BieganBrickRaceGame game, Viewport gamePort, int score) {
        this.game = game;
        this.score = score;
        font = new BitmapFont();
        controller = game.getController();

        stage = new Stage(gamePort, game.batch);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        gameOverLabel = new Label("GAME OVER!", new Label.LabelStyle(font, Color.WHITE));
        restartLabel = new Label("RESTART - PUSH ENTER", new Label.LabelStyle(font, Color.WHITE));
        exitLabel = new Label("EXIT - PUSH ESC", new Label.LabelStyle(font, Color.WHITE));
        scoreLabel = new Label("SCORE: " + score, new Label.LabelStyle(font, Color.WHITE));

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(scoreLabel).expandX().padTop(10f);
        table.row();
        table.add(restartLabel).expandX().padTop(10f);
        table.row();
        table.add(exitLabel).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() {
       Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(dt);
        stage.draw();

        // Buttons operations
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            controller.setGameSpeed(300);
            game.setScreen(new GameScreen(game)); // Restart
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); // Exit
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
