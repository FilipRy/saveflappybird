package com.filip.saveflappybird.zbhelpers;

import com.badlogic.gdx.InputProcessor;
import com.filip.saveflappybird.screens.GameScreen;
import com.filip.saveflappybird.screens.HelpScreen;
import com.filip.saveflappybird.ui.SimpleButton;

public class HelpScreenInputHandler implements InputProcessor {


    private SimpleButton backButton;
    private HelpScreen helpScreen;

    private float scaleFactorX;
    private float scaleFactorY;

    public HelpScreenInputHandler(HelpScreen helpScreen, float scaleFactorX, float scaleFactorY) {

        this.helpScreen = helpScreen;

        this.backButton = new SimpleButton(GameScreen.DEFAULT_GAME_WIDTH / 2 - (AssetLoader.backButtonUp.getRegionWidth() / 2),
                5, 29, 16, AssetLoader.backButtonUp, AssetLoader.backButtonDown);

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

    }

    public SimpleButton getBackButton() {
        return backButton;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        this.backButton.isTouchDown(screenX, screenY);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (backButton.isTouchUp(screenX, screenY)) {
            this.helpScreen.goBackToGameScreen();
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

}
