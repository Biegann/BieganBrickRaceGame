package com.biegan.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.biegan.game.BieganBrickRaceGame;

public class GameOverScreen implements Screen {

    private BieganBrickRaceGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private final float worldWidth = 800;
    private final float worldHeight = 500;

    public GameOverScreen(BieganBrickRaceGame game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(worldWidth, worldHeight , gameCam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(gameCam.combined);

        batch.begin();
        float centerX = gamePort.getWorldWidth() / 2f;
        float centerY = gamePort.getWorldHeight() / 2f;

        font.draw(batch, "GAME OVER!", centerX - 50, centerY + 20);
        font.draw(batch, "RESTART - PUSH ENTER", centerX - 90, centerY - 30);
        font.draw(batch, "EXIT - PUSH ESC", centerX - 60, centerY - 60);
        batch.end();

        // Buttons operations
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            BieganBrickRaceGame.gameSpeed = 300;
            game.setScreen(new GameScreen(game)); // Restart
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); // Exit
        }
    }

    @Override
    public void resize(int with, int height) {
        gamePort.update(with, height, true);
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
