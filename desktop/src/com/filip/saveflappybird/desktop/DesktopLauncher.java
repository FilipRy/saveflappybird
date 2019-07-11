package com.filip.saveflappybird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.filip.saveflappybird.SFBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Save Flappy Bird";
		config.width = 1080 / 3;
		config.height = 1920 / 3;

		new LwjglApplication(new SFBGame(), config);
	}
}
