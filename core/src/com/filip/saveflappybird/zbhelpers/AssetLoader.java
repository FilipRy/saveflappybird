package com.filip.saveflappybird.zbhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture, logoTexture, birdTexture, birdEnemy1, birdEnemy2, birdEnemy3, gameNameTexture;
	
	public static TextureRegion logo, zbLogo, bg, grass, bird, birdDown,
			birdUp, bEnemy1, bEnemy1Up, bEnemy1Down, bEnemy2, bEnemy2Up, bEnemy2Down, bEnemy3, bEnemy3Up, bEnemy3Down, skullUp, skullDown, bar, playButtonUp, playButtonDown,
			helpButtonUp, helpButtonDown, backButtonUp, backButtonDown, ready, gameOver, highScore, scoreboard, star, noStar, retry;
	public static Animation birdAnimation, enemy1Animation, enemy2Animation, enemy3Animation;
	public static Sound dead, flap, coin, fall;
	public static BitmapFont font, shadow, whiteFont;
	private static Preferences prefs;

	public static void load() {
		
		AssetManager manager = new AssetManager();

		manager.load("data/mytexture.png", Texture.class);

		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new TextureRegion(logoTexture, 0, 0, 512, 114);

		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		birdTexture = new Texture(Gdx.files.internal("data/birdTexture.png"));
		birdTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		birdEnemy1 = new Texture(Gdx.files.internal("data/birdEnemy1.png"));
		birdEnemy1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		birdEnemy2 = new Texture(Gdx.files.internal("data/birdEnemy2.png"));
		birdEnemy2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		birdEnemy3 = new Texture(Gdx.files.internal("data/birdEnemy3.png"));
		birdEnemy3.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		gameNameTexture = new Texture(Gdx.files.internal("data/save-flappy-logo.png"));
		gameNameTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
		playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);

		helpButtonUp = new TextureRegion(texture, 0, 99, 29, 16);
		helpButtonDown = new TextureRegion(texture, 29, 99, 29, 16);
		helpButtonUp.flip(false, true);
		helpButtonDown.flip(false, true);

		backButtonUp = new TextureRegion(texture, 136, 21, 29, 16);
		backButtonDown = new TextureRegion(texture, 165, 21, 29, 16);
		backButtonUp.flip(false, true);
		backButtonDown.flip(false, true);

		ready = new TextureRegion(texture, 59, 83, 34, 7);
		ready.flip(false, true);

		retry = new TextureRegion(texture, 59, 110, 33, 7);
		retry.flip(false, true);
		
		gameOver = new TextureRegion(texture, 59, 92, 46, 7);
		gameOver.flip(false, true);

		scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
		scoreboard.flip(false, true);

		star = new TextureRegion(texture, 152, 70, 10, 10);
		noStar = new TextureRegion(texture, 165, 70, 10, 10);

		star.flip(false, true);
		noStar.flip(false, true);

		highScore = new TextureRegion(texture, 59, 101, 48, 7);
		highScore.flip(false, true);

		zbLogo = new TextureRegion(gameNameTexture, 5, 5, 150, 50);
		zbLogo.flip(false, true);

		bg = new TextureRegion(texture, 0, 0, 136, 43);
		bg.flip(false, true);

		grass = new TextureRegion(texture, 0, 43, 143, 11);
		grass.flip(false, true);

		birdDown = new TextureRegion(birdTexture, 90, 0, 36, 32);
		birdDown.flip(false, true);

		bird = new TextureRegion(birdTexture, 40, 0, 36, 32);
		bird.flip(false, true);

		birdUp = new TextureRegion(birdTexture, 0, 0, 36, 32);
		birdUp.flip(false, true);
		
		bEnemy1Up = new TextureRegion(birdEnemy1, 0, 0, 38, 32);
		bEnemy1Up.flip(false, true);
		
		bEnemy1 = new TextureRegion(birdEnemy1, 43, 0, 38, 32);
		bEnemy1.flip(false, true);
		
		bEnemy1Down = new TextureRegion(birdEnemy1, 90, 0, 38, 32);
		bEnemy1Down.flip(false, true);
		
		bEnemy2Up = new TextureRegion(birdEnemy2, 0, 0, 47, 32);
		bEnemy2Up.flip(false, true);
		
		bEnemy2 = new TextureRegion(birdEnemy2, 132, 0, 47, 32);
		bEnemy2.flip(false, true);
		
		bEnemy2Down = new TextureRegion(birdEnemy2, 210, 0, 47, 32);
		bEnemy2Down.flip(false, true);
		
		bEnemy3Up = new TextureRegion(birdEnemy3, 0, 0, 37, 32);
		bEnemy3Up.flip(false, true);
		
		bEnemy3 = new TextureRegion(birdEnemy3, 158, 0, 37, 32);
		bEnemy3.flip(false, true);
		
		bEnemy3Down = new TextureRegion(birdEnemy3, 220, 0, 37, 32);
		bEnemy3Down.flip(false, true);
		
		TextureRegion[] birds = {birdDown, bird, birdUp };
		birdAnimation = new Animation(0.09f, birds);
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		TextureRegion[] enemies1 = {bEnemy1Down, bEnemy1, bEnemy1Up };
		enemy1Animation = new Animation(0.1f, enemies1);
		enemy1Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		TextureRegion[] enemies2 = {bEnemy2Down, bEnemy2, bEnemy2Up };
		enemy2Animation = new Animation(0.16f, enemies2);
		enemy2Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		TextureRegion[] enemies3 = {bEnemy3Down, bEnemy3, bEnemy3Up };
		enemy3Animation = new Animation(0.11f, enemies3);
		enemy3Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		skullUp = new TextureRegion(texture, 192, 0, 24, 11);
		// Create by flipping existing skullUp
		skullDown = new TextureRegion(skullUp);
		skullDown.flip(false, true);

		bar = new TextureRegion(texture, 136, 16, 22, 3);
		bar.flip(false, true);

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.getData().scaleX = .25f;
		font.getData().scaleY = -.25f;

		whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
		whiteFont.getData().scaleX = .1f;
		whiteFont.getData().scaleY = -.1f;

		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.getData().scaleX = .25f;
		shadow.getData().scaleY = -.25f;

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("SaveFlappyBird");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void dispose() {
		// We must dispose of the texture when we are finished.
		texture.dispose();

		// Dispose sounds
		dead.dispose();
		flap.dispose();
		coin.dispose();

		font.dispose();
		shadow.dispose();
	}

}