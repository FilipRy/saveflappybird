package com.filip.saveflappybird.zbhelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.filip.saveflappybird.gameobjects.Bird;
import com.filip.saveflappybird.gameobjects.Pipe;
import com.filip.saveflappybird.gameworld.GameWorld;
import com.filip.saveflappybird.screens.GameScreen;
import com.filip.saveflappybird.ui.SimpleButton;

public class GameScreenInputHandler implements InputProcessor {
	
	private Bird myBird;
	private GameWorld myWorld;
	
	private Pipe pipe1, pipe2, pipe3;

	private List<SimpleButton> menuButtons;

	private SimpleButton playButton;
	private SimpleButton helpButton;

	private float scaleFactorX;
	private float scaleFactorY;

	public GameScreenInputHandler(GameWorld myWorld, float scaleFactorX,
								  float scaleFactorY) {
		this.myWorld = myWorld;
		myBird = myWorld.getBird();
		
		pipe1 = myWorld.getScroller().getPipe1();
		pipe2 = myWorld.getScroller().getPipe2();
		pipe3 = myWorld.getScroller().getPipe3();

		int midPointY = myWorld.getMidPointY();

		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;

		menuButtons = new ArrayList<SimpleButton>();
		playButton = new SimpleButton(
				GameScreen.DEFAULT_GAME_WIDTH / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
				midPointY + 20, 29, 16, AssetLoader.playButtonUp,
				AssetLoader.playButtonDown);

		helpButton = new SimpleButton(
				GameScreen.DEFAULT_GAME_WIDTH / 2 - (AssetLoader.helpButtonUp.getRegionWidth() / 2),
				midPointY + 45, 29, 16, AssetLoader.helpButtonUp,
				AssetLoader.helpButtonDown);

		menuButtons.add(playButton);
		menuButtons.add(helpButton);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		
		pipe1.onTouchEvent(screenX, screenY);
		pipe2.onTouchEvent(screenX, screenY);
		pipe3.onTouchEvent(screenX, screenY);
		
		if (myWorld.isMenu()) {
			playButton.isTouchDown(screenX, screenY);
			helpButton.isTouchDown(screenX, screenY);
		} else if (myWorld.isReady()) {
			myWorld.start();
		}

		if (myWorld.isGameOver() || myWorld.isHighScore()) {
			myWorld.restart();
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		pipe1.onTouchUpEvent(screenX, screenY);
		pipe2.onTouchUpEvent(screenX, screenY);
		pipe3.onTouchUpEvent(screenX, screenY);
		
		
		if (myWorld.isMenu()) {
			if (playButton.isTouchUp(screenX, screenY)) {
				myWorld.ready();
				return true;
			}
			if (helpButton.isTouchUp(screenX, screenY)) {
				myWorld.goToHelpScreen();
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {

		// Can now use Space Bar to play the game
		if (keycode == Keys.SPACE) {

			if (myWorld.isMenu()) {
				myWorld.ready();
			} else if (myWorld.isReady()) {
				myWorld.start();
			}

			//myBird.onClick();

			if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.restart();
			}

		}

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
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		
		//System.out.println("drag: ["+screenX+", "+screenY+"]"+ "pointer "+pointer);
		
		pipe1.onDragEvent(screenX, screenY);
		pipe2.onDragEvent(screenX, screenY);
		pipe3.onDragEvent(screenX, screenY);
		
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

	public List<SimpleButton> getMenuButtons() {
		return menuButtons;
	}
}
