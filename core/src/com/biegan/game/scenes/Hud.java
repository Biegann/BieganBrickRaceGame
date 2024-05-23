package com.biegan.game.scenes;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.biegan.game.screens.GameScreen;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private OrthographicCamera gameCam;

    private int gameSpeed = 1;
    private int lastSpeedIncreaseScore = 0;

    public Label scoreLabel;
    Label gameSpeedLabel;
    private GameScreen gameScreen;

    private final float worldWidth = 800; // witdh of game world
    private final float worldHeight = 500; // height of game world

    public Hud(SpriteBatch sb, GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        gameCam = new OrthographicCamera();
        viewport = new FitViewport(worldWidth, worldHeight , gameCam);
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.setPosition(650, 0); // set table positions

        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.2f); // Scale up font
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        scoreLabel = new Label(String.format("Score : %06d", GameScreen.getScore()),
                labelStyle);
        gameSpeedLabel = new Label(String.format("Speed: %03d", gameSpeed),
                labelStyle);

        table.add(scoreLabel).expandX().fillX().pad(10);
        table.row();
        table.add(gameSpeedLabel).expandX().fillX().pad(10);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setGameSpeed() {
        gameSpeed ++;
        gameSpeedLabel.setText(String.format("Speed: %03d", gameSpeed));
    }

    public void update(float delta) {
        int score = GameScreen.getScore();
        scoreLabel.setText(String.format("Score : %06d", gameScreen.getScore()));
        if (score % 500 == 0 && score != 0 && score != lastSpeedIncreaseScore) {
            gameScreen.increaseGameSpeed();
            lastSpeedIncreaseScore = score;
        }
        stage.act(delta); // Call act() on the stage object
    }
}