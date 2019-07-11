package com.filip.saveflappybird.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.filip.saveflappybird.SFBGame;
import com.filip.saveflappybird.gameobjects.Bird;
import com.filip.saveflappybird.gameobjects.ScrollHandler;
import com.filip.saveflappybird.screens.HelpScreen;
import com.filip.saveflappybird.zbhelpers.AssetLoader;

public class GameWorld {

    private Bird bird;
    private ScrollHandler scroller;
    private Rectangle ground;
    private int score = 0;
    private float runTime = 0;
    private int midPointY;
    private GameRenderer renderer;

    private GameState currentState;

    private SFBGame game;

    private int gameHeight;

    private boolean collidesWithPipe = false;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE, HELP
    }

    public GameWorld(int midPointY, int gameHeight, SFBGame game) {
        this.game = game;
        currentState = GameState.MENU;
        this.midPointY = midPointY;
        bird = new Bird(33, midPointY - 5);
        // The grass should start 66 pixels below the midPointY
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 137, 11);
        this.gameHeight = gameHeight;
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }

    }

    private void updateReady(float delta) {
        bird.updateReady(runTime);
        scroller.updateReady(delta);
    }

    public void updateRunning(float delta) {

        if (delta > .15f) {
            delta = .15f;
        }

        scroller.update(delta);

        if (scroller.birdCollidesPipeSkull(bird) && bird.isAlive()) {
            //System.out.println("bird collides skull");
            bird.setYSpeed(Bird.Y_SPEED);
        }

        bird.update(delta);

        if (scroller.collides(bird) && bird.isAlive()) {

            if (!collidesWithPipe) {
                bird.decreaseLives();

                if (!bird.isAlive()) {
                    scroller.stop();
                    AssetLoader.dead.play();
                    renderer.prepareTransition(255, 255, 255, .3f);
                    AssetLoader.fall.play();
                }
            }

            collidesWithPipe = true;
        } else {
            collidesWithPipe = false;
        }

        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {

            bird.decreaseLives();

            if (!bird.isAlive()) {
                AssetLoader.dead.play();
                renderer.prepareTransition(255, 255, 255, .3f);

                scroller.stop();
                bird.decelerate();
                currentState = GameState.GAMEOVER;


                if (score > AssetLoader.getHighScore()) {
                    AssetLoader.setHighScore(score);
                    currentState = GameState.HIGHSCORE;
                }
            }

        }

    }

    public Bird getBird() {
        return bird;

    }

    public int getMidPointY() {
        return midPointY;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void goToHelpScreen() {
        currentState = GameState.HELP;
        this.game.setScreen(new HelpScreen(this.game));
    }

    public void ready() {
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
    }

    public void restart() {
        score = 0;
        bird.onRestart(midPointY - 5);
        scroller.onRestart();
        ready();
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isAbout() {
        return currentState == GameState.HELP;
    }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

    public GameState getCurrentState() {
        return currentState;
    }


}
