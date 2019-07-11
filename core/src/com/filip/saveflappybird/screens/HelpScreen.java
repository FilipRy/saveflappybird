package com.filip.saveflappybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.filip.saveflappybird.SFBGame;
import com.filip.saveflappybird.ui.SimpleButton;
import com.filip.saveflappybird.zbhelpers.AssetLoader;
import com.filip.saveflappybird.zbhelpers.HelpScreenInputHandler;

import static com.filip.saveflappybird.screens.GameScreen.DEFAULT_GAME_WIDTH;

public class HelpScreen implements Screen {

    private OrthographicCamera cam;
    private SpriteBatch batcher;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private SFBGame game;

    private int gameHeight;

    private SimpleButton backButton;

    private TextureRegion bg, grass;

    public HelpScreen(SFBGame game) {

        this.game = game;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = DEFAULT_GAME_WIDTH;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        this.gameHeight = (int) gameHeight;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, DEFAULT_GAME_WIDTH, gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        font = new BitmapFont(true);
        font.setColor(Color.WHITE);
        font.getData().setScale(0.55f);


        this.bg = AssetLoader.bg;
        this.grass = AssetLoader.grass;

        Gdx.input.setInputProcessor(new HelpScreenInputHandler(this, screenWidth / gameWidth, screenHeight / gameHeight));
        this.backButton = ((HelpScreenInputHandler) Gdx.input.getInputProcessor()).getBackButton();
    }


    public void goBackToGameScreen() {
        this.game.setScreen(new GameScreen(this.game));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(19 / 255.0f, 145 / 255.0f, 229 / 255.0f, 1f);
        shapeRenderer.rect(0, 0, DEFAULT_GAME_WIDTH, 1000);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, this.gameHeight / 2 + 66, DEFAULT_GAME_WIDTH, 11);

        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();

        batcher.draw(bg, 0, this.gameHeight / 2 + 23, DEFAULT_GAME_WIDTH, 43);

        batcher.enableBlending();


        shapeRenderer.end();


        drawButtons();

        // Draw the grass
        batcher.draw(grass, 0, this.gameHeight / 2 + 66, 143, 11);
        batcher.draw(grass, 143, this.gameHeight / 2 + 66, 143, 11);

        batcher.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, this.gameHeight / 2 + 77, DEFAULT_GAME_WIDTH, 52);

        shapeRenderer.end();

        batcher.begin();

        font.draw(batcher, "Your task is to move the pipes", 2, 40);
        font.draw(batcher, "such that the bird can bounce ", 2, 50);
        font.draw(batcher, "the bottom pipe to jump and fly", 2, 60);
        font.draw(batcher, "further. There are other birds", 2, 70);
        font.draw(batcher, "in the game. You should avoid", 2, 80);
        font.draw(batcher, "larger birds, because they will", 2, 90);
        font.draw(batcher, "eat you. You should sometimes eat", 2, 100);
        font.draw(batcher, "a smaller bird to slow the games", 2, 110);
        font.draw(batcher, "speed down.", 2, 120);

        batcher.end();

    }

    private void drawButtons() {
        backButton.draw(batcher);
    }

    @Override
    public void resize(int width, int height) {

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
