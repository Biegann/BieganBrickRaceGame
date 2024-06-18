package com.biegan.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.biegan.game.View.sprites.Player;

public class GameInput implements InputProcessor {

  private final GameController controller;
  private final Player player;

    public GameInput(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;

    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && controller.getState() != GameController.GAME_STATE.STARTED) {
            startGame();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.updatePlayerPosLeft();
            controller.playerSound();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.updatePlayerPosRight();
            controller.playerSound();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            controller.increaseGameSpeed();
        }

    }

    public void startGame() {
        controller.setState(GameController.GAME_STATE.STARTED);
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
