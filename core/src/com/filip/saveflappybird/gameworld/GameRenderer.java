package com.filip.saveflappybird.gameworld;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.filip.saveflappybird.gameobjects.*;
import com.filip.saveflappybird.screens.GameScreen;
import com.filip.saveflappybird.tweenaccessors.Value;
import com.filip.saveflappybird.tweenaccessors.ValueAccessor;
import com.filip.saveflappybird.ui.SimpleButton;
import com.filip.saveflappybird.zbhelpers.AssetLoader;
import com.filip.saveflappybird.zbhelpers.GameScreenInputHandler;

public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY;

    // Game Objects
    private Bird bird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;

    // Game Assets
    private TextureRegion bg, grass, birdMid, enemy1Mid, enemy2Mid, enemy3Mid, skullUp, skullDown, bar, ready,
            zbLogo, gameOver, highScore, scoreboard, star, noStar, retry;
    private Animation birdAnimation, enemy1Animation, enemy2Animation, enemy3Animation;

    // Tween stuff
    private TweenManager manager;
    private Value alpha = new Value();

    // Buttons
    private List<SimpleButton> menuButtons;
    private Color transitionColor;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;

        this.midPointY = midPointY;
        this.menuButtons = ((GameScreenInputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

        cam = new OrthographicCamera();
        cam.setToOrtho(true, GameScreen.DEFAULT_GAME_WIDTH, gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();

        transitionColor = new Color();
        prepareTransition(255, 255, 255, .5f);
    }

    private void initGameObjects() {
        bird = myWorld.getBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
    }

    private void initAssets() {
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        enemy1Animation = AssetLoader.enemy1Animation;
        enemy2Animation = AssetLoader.enemy2Animation;
        enemy3Animation = AssetLoader.enemy3Animation;
        birdMid = AssetLoader.bird;
        enemy1Mid = AssetLoader.bEnemy1;
        enemy2Mid = AssetLoader.bEnemy2;
        enemy3Mid = AssetLoader.bEnemy3;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
        ready = AssetLoader.ready;
        zbLogo = AssetLoader.zbLogo;
        gameOver = AssetLoader.gameOver;
        highScore = AssetLoader.highScore;
        scoreboard = AssetLoader.scoreboard;
        retry = AssetLoader.retry;
        star = AssetLoader.star;
        noStar = AssetLoader.noStar;
    }

    private void drawGrass() {
        // Draw the grass
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawSkulls() {

        batcher.draw(skullUp, pipe1.getSkullUp().x,
                pipe1.getY() + pipe1.getHeight() - Pipe.SKULL_HEIGHT, Pipe.SKULL_WIDTH, Pipe.SKULL_HEIGHT);
        batcher.draw(skullDown, pipe1.getSkullUp().x,
                pipe1.getY() + pipe1.getHeight() + Pipe.VERTICAL_GAP,
                Pipe.SKULL_WIDTH, Pipe.SKULL_HEIGHT);

        batcher.draw(skullUp, pipe2.getSkullUp().x,
                pipe2.getY() + pipe2.getHeight() - Pipe.SKULL_HEIGHT, Pipe.SKULL_WIDTH, Pipe.SKULL_HEIGHT);
        batcher.draw(skullDown, pipe2.getSkullUp().x,
                pipe2.getY() + pipe2.getHeight() + Pipe.VERTICAL_GAP,
                Pipe.SKULL_WIDTH, Pipe.SKULL_HEIGHT);

        batcher.draw(skullUp, pipe3.getSkullUp().x,
                pipe3.getY() + pipe3.getHeight() - Pipe.SKULL_HEIGHT, Pipe.SKULL_WIDTH, Pipe.SKULL_HEIGHT);
        batcher.draw(skullDown, pipe3.getSkullUp().x,
                pipe3.getY() + pipe3.getHeight() + Pipe.VERTICAL_GAP,
                Pipe.SKULL_WIDTH, Pipe.SKULL_HEIGHT);
    }

    private void drawPipes() {
        batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight()
                + Pipe.VERTICAL_GAP, pipe1.getWidth(), pipe1.getHeight());

        batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight()
                + Pipe.VERTICAL_GAP, pipe2.getWidth(), pipe2.getHeight());

        batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight()
                + Pipe.VERTICAL_GAP, pipe3.getWidth(), pipe3.getHeight());
    }

    private void drawBirdCentered(float runTime) {

        batcher.draw((TextureRegion) birdAnimation.getKeyFrame(runTime), 59, bird.getY() - 15,
                bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
    }

    private void drawBird(float runTime) {

//		shapeRenderer.begin(ShapeType.Filled);
//
//		// Draw Bird circle
//		shapeRenderer.setColor(Color.GREEN);
//		shapeRenderer.circle(bird.getBoundingCircle().x, bird.getBoundingCircle().y, bird.getBoundingCircle().radius);
//		
//		shapeRenderer.end();

        if (bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());


        } else {
            batcher.draw((TextureRegion) birdAnimation.getKeyFrame(runTime), bird.getX(),
                    bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }

    }

    private void drawMenuUI() {
        batcher.draw(zbLogo, GameScreen.DEFAULT_GAME_WIDTH / 2 - 60, midPointY - 65,
                zbLogo.getRegionWidth() / 1.2f, zbLogo.getRegionHeight() / 1.2f);

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

    }

    private void drawScoreboard() {
        batcher.draw(scoreboard, 22, midPointY - 30, 97, 37);

        batcher.draw(noStar, 25, midPointY - 15, 10, 10);
        batcher.draw(noStar, 37, midPointY - 15, 10, 10);
        batcher.draw(noStar, 49, midPointY - 15, 10, 10);
        batcher.draw(noStar, 61, midPointY - 15, 10, 10);
        batcher.draw(noStar, 73, midPointY - 15, 10, 10);

        if (myWorld.getScore() > 2) {
            batcher.draw(star, 73, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 17) {
            batcher.draw(star, 61, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 50) {
            batcher.draw(star, 49, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 80) {
            batcher.draw(star, 37, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 120) {
            batcher.draw(star, 25, midPointY - 15, 10, 10);
        }

        int length = ("" + myWorld.getScore()).length();

        AssetLoader.whiteFont.draw(batcher, "" + myWorld.getScore(),
                104 - (2 * length), midPointY - 20);

        int length2 = ("" + AssetLoader.getHighScore()).length();
        AssetLoader.whiteFont.draw(batcher, "" + AssetLoader.getHighScore(),
                104 - (2.5f * length2), midPointY - 3);

    }

    private void drawRetry() {
        batcher.draw(retry, 36, midPointY + 10, 66, 14);
    }

    private void drawReady() {
        batcher.draw(ready, 36, midPointY - 50, 68, 14);
    }

    private void drawGameOver() {
        batcher.draw(gameOver, 24, midPointY - 50, 92, 14);
    }

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 82);
        AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 83);
    }

    private void drawHighScore() {
        batcher.draw(highScore, 22, midPointY - 50, 96, 14);
    }

    private void drawScoreObjects(float runTime) {


        ScoreObjects foods = scroller.getScoreObjects();
        ScoreObject[] food = foods.getFood();

        for (int i = 0; i < food.length; i++) {
            if (food[i] != null) {

                float e1X = food[i].getX() - food[i].getWidth() / 2f;
                float e1Y = food[i].getY() - food[i].getHeight() / 2f;

                if (!food[i].isAlive() || !bird.isAlive()) { // !bird.isAlive to determine if game is in HIGH score state

                    TextureRegion texture = enemy1Mid;

                    if (food[i].getEnemyType() == 2) {
                        texture = enemy2Mid;
                    }

                    if (food[i].getEnemyType() == 3) {
                        texture = enemy3Mid;
                    }


                    batcher.draw(texture, e1X,
                            e1Y, food[i].getWidth() / 2.0f,
                            food[i].getHeight() / 2.0f, food[i].getWidth(), food[i].getHeight(),
                            1, 1, food[i].getRotation());
                } else {

                    Animation anim = enemy1Animation;

                    if (food[i].getEnemyType() == 2) {
                        anim = enemy2Animation;
                    }

                    if (food[i].getEnemyType() == 3) {
                        anim = enemy3Animation;
                    }

                    batcher.draw((TextureRegion) anim.getKeyFrame(runTime), e1X,
                            e1Y, food[i].getWidth() / 2.0f,
                            food[i].getHeight() / 2.0f, food[i].getWidth(), food[i].getHeight(),
                            1, 1, food[i].getRotation());
                }
            }
        }
    }

    public void render(float delta, float runTime) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(19 / 255.0f, 145 / 255.0f, 229 / 255.0f, 1f);
        shapeRenderer.rect(0, 0, GameScreen.DEFAULT_GAME_WIDTH, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, GameScreen.DEFAULT_GAME_WIDTH, 11);

        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();

        batcher.draw(bg, 0, midPointY + 23, GameScreen.DEFAULT_GAME_WIDTH, 43);

        batcher.enableBlending();

        if (myWorld.isRunning()) {
            drawPipes();
            drawSkulls();
            drawBird(runTime);
            drawScoreObjects(runTime);
            drawScore();
        } else if (myWorld.isReady()) {
            drawBird(runTime);
            drawReady();
        } else if (myWorld.isMenu()) {
            drawBirdCentered(runTime);
            drawMenuUI();
        } else if (myWorld.isGameOver()) {
            drawPipes();
            drawSkulls();
            drawScoreboard();
            drawBird(runTime);
            drawGameOver();
            drawRetry();
        } else if (myWorld.isHighScore()) {
            drawPipes();
            drawSkulls();
            drawScoreboard();
            drawBird(runTime);
            drawHighScore();
            drawRetry();
        }

        drawGrass();

        batcher.end();

        shapeRenderer.begin(ShapeType.Filled);
        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, GameScreen.DEFAULT_GAME_WIDTH, 52);

        shapeRenderer.end();

        drawTransition(delta);
    }

    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, GameScreen.DEFAULT_GAME_WIDTH, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }

}
