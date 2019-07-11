package com.filip.saveflappybird;

import com.badlogic.gdx.Game;
import com.filip.saveflappybird.screens.SplashScreen;
import com.filip.saveflappybird.zbhelpers.AssetLoader;

public class SFBGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
