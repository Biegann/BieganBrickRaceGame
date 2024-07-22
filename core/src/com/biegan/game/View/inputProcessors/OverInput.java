package com.biegan.game.View.inputProcessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.biegan.game.Controller.GameController;

public class OverInput implements InputProcessor {

    private final GameController controller;

    public OverInput(GameController controller) {
        this.controller = controller;
    }

    @Override
    public boolean keyDown(int i) {
        if (i == Input.Keys.ENTER) {
            controller.restart();
            return true;
        }

        if (i == Input.Keys.ESCAPE) {
            Gdx.app.exit(); // Exit
            return true;
        }
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
