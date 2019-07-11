package com.filip.saveflappybird.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.filip.saveflappybird.screens.GameScreen;
import com.filip.saveflappybird.zbhelpers.MyIntersector;

public class ScoreObject extends Scrollable {

	private int radius;
	private boolean isScored;
	private boolean isAlive;

	private int enemyType;
	private ScoreObjectType enemyTypeEnum;

	private float rotation;

	private Circle boundingCircle;
	public final static int DEFAULT_RADIUS = 5;

	public ScoreObject(float x, float y, int radius, float scrollSpeed,
			int enemyType) {

		super(x, y, radius, radius, scrollSpeed);

		if (enemyType == 1) {
			setEnemyTypeEnum(ScoreObjectType.WeightBird);
			super.width = radius * 2;
			super.height = radius * 2;
		}

		if (enemyType == 2) {
			setEnemyTypeEnum(ScoreObjectType.WeightBird);
			super.width = radius * 3;
			super.height = radius * 2;
		}
		
		if (enemyType == 3) {
			setEnemyTypeEnum(ScoreObjectType.SpeedBird);
			super.width = radius * 3;
			super.height = radius * 2;
		}

		isAlive = true;

		this.enemyType = enemyType;
		this.setRadius(radius);
		boundingCircle = new Circle();
	}

	public void update(float delta) {
		super.update(delta);

		if (!isAlive) {
			rotation += 480 * delta;
		}

		if (isScored && this.position.x + radius > 150) { // TODO replace 150
															// with screen width
			isScrolledOut = true;
		}

		// System.out.println("food position: "+position);
		boundingCircle.setPosition(position);
	}

	public boolean collidesBird(Bird bird) {
		return MyIntersector.collidesCircle(getBoundingCircle(),
				bird.getBoundingCircle());
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public void setBoundingCircle(Circle boundingCircle) {
		this.boundingCircle = boundingCircle;
	}

	public void reset(float newX) {
		super.reset(newX);
		isScored = false;
		isAlive = true;
	}

	public void die() {
		isAlive = false;
		isScored = true;
		super.velocity.y = 100;
	}

	public void runOut() {
		super.velocity.x = -super.getScrollSpeed() * 3.5f;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public int getEnemyType() {
		return enemyType;
	}

	public static int generateNewXPosition() {
		Random rand = new Random();
		return GameScreen.DEFAULT_GAME_WIDTH + DEFAULT_RADIUS
				+ rand.nextInt(50);
	}

	public static int generateFoodSpeed() {
		Random rand = new Random();
		return -(rand.nextInt(15) + 20);// <-34,-20>
	}

	public static int generateFoodRadius() {
		Random rand = new Random();

		return (int) (rand
				.nextInt((int) (Bird.MAX_BIRD_RADIUS - Bird.MIN_BIRD_RADIUS)) + Bird.MIN_BIRD_RADIUS);
	}

	public static int generateNewYPosition(float maxY, int radius) {
		Random rand = new Random();
		return rand.nextInt((int) (maxY - radius));
	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean isScored) {
		this.isScored = isScored;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public ScoreObjectType getEnemyTypeEnum() {
		return enemyTypeEnum;
	}

	public void setEnemyTypeEnum(ScoreObjectType enemyTypeEnum) {
		this.enemyTypeEnum = enemyTypeEnum;
	}

}
