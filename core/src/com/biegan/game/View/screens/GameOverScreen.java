package com.biegan.game.View.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.biegan.game.Controller.GameController;
import com.biegan.game.View.inputProcessors.OverInput;

public class GameOverScreen implements Screen {

    private final BitmapFont font;
    private final GameController controller;
    private final OverInput overInput;

    private final Stage stage;
    private Label gameOverLabel;
    private Label restartLabel;
    private Label exitLabel;
    private final Label scoreLabel;

    public GameOverScreen(GameController controller) {
        this.controller = controller;
        this.font = new BitmapFont();
        this.overInput = new OverInput(controller);
        this.stage = new Stage();

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        gameOverLabel = new Label("GAME OVER!", new Label.LabelStyle(font, Color.WHITE));
        restartLabel = new Label("RESTART - PUSH ENTER", new Label.LabelStyle(font, Color.WHITE));
        exitLabel = new Label("EXIT - PUSH ESC", new Label.LabelStyle(font, Color.WHITE));
        scoreLabel = new Label("SCORE: " + controller.getScore(), new Label.LabelStyle(font, Color.WHITE));

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
       Gdx.input.setInputProcessor(overInput);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        scoreLabel.setText("SCORE: " + controller.getScore());

        stage.act(dt);
        stage.draw();
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
        font.dispose();
    }
}
