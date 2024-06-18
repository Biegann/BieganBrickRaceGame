package com.biegan.game.View;

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
import com.biegan.game.Controller.GameController;

public class Hud implements Disposable {

    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera gameCam;
    private GameController controller;

    private Label scoreLabel;
    private Label gameSpeedLabel;

    public Hud(SpriteBatch sb, GameController controller) {
        this.controller = controller;

        gameCam = new OrthographicCamera();
        viewport = new FitViewport(controller.getWorldWidth(), controller.getWorldHeight(), gameCam);
        stage = new Stage(viewport, sb);

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
    public void dispose() {
        stage.dispose();
    }

    public void incSpeedDisplayValue() {
        int gameSpeed =  (int) controller.getGameSpeed();
        gameSpeedLabel.setText(String.format("Speed : %04d", gameSpeed));
    }

    public void update(float delta) {
        int score = controller.getScore();
        scoreLabel.setText(String.format("Score : %06d", score));

        stage.act(delta);
    }

    public Stage getStage() {
        return stage;
    }
}
