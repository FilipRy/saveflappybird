package com.filip.saveflappybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.filip.saveflappybird.SFBGame;
import com.filip.saveflappybird.gameworld.GameRenderer;
import com.filip.saveflappybird.gameworld.GameWorld;
import com.filip.saveflappybird.zbhelpers.GameScreenInputHandler;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    private SFBGame game;

    public static int DEFAULT_GAME_WIDTH = 136;

    public GameScreen(SFBGame game) {
        this.game = game;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = DEFAULT_GAME_WIDTH;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(midPointY, (int) gameHeight, game);
        Gdx.input.setInputProcessor(new GameScreenInputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);
        world.setRenderer(renderer);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
