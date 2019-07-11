package com.filip.saveflappybird.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.filip.saveflappybird.zbhelpers.AssetLoader;


public class Bird {

	// TODO nerozjeb si hlavu ked sa odrazis - OK
	// TODO ked narazi s pipe, pri posunuti pipe a kontakte s skull nemoze
	// vyskocit - OK
	// TODO ked prechadza pipe, pri posunuti pipe zdola hore, nemoze zhebnut ale zaregistrovat len prvy dotyk

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private float rotation;
	
	private float height;
	
	private float circleRadius;

	private float originalY;

	private int lives;

	private Circle boundingCircle;

	public static final int NO_JUMP_BEFORE_PIPE_RANGE = 7;

	public static final int Y_SPEED = -100;
	public static final int Y_ACCELERATION = 100;

	private final int DONT_FLAPP_RANGE = 0;
	private final int FREE_ROTATION_RANGE = 60;
	private final int FALLING_RANGE = 110;
	
	private final float TOO_FAT_RADIUS_RANGE = 7.5f;
	public static final float MIN_BIRD_RADIUS = 2.5f;
	public static final float MAX_BIRD_RADIUS = 12f;

	public Bird(float x, float y) {

		this.originalY = y;
		position = new Vector2(x, y);
		velocity = new Vector2(0, Y_SPEED);
		acceleration = new Vector2(0, Y_ACCELERATION);
		boundingCircle = new Circle();
		lives = 1;
		circleRadius = 6f;
		height = 2 * circleRadius;

	}

	public void update(float delta) {

		if (getCircleRadius() >= TOO_FAT_RADIUS_RANGE) {

			float scale = getCircleRadius() - TOO_FAT_RADIUS_RANGE;
			scale = scale * (0.8f / 2) + 1.2f;

			velocity.add(acceleration.cpy().scl(delta * scale));
		}

		else {
			velocity.add(acceleration.cpy().scl(delta));
		}
		

		if (velocity.y > 200) {
			velocity.y = 200;
		}

		// CEILING CHECK
		if (position.y < -13) {
			position.y = -13;
			velocity.y = 0;
		}
		
		position.add(velocity.cpy().scl(delta));

		// Set the circle's center to be (9, 6) with respect to the bird.
		// Set the circle's radius to be 6.5f;
		boundingCircle.set(position.x + getWidth()/2f, position.y + getHeight()/2f, circleRadius);

		// Rotate counterclockwise
		if (velocity.y < 0) {
			rotation -= 600 * delta;

			if (rotation < -20) {
				rotation = -20;
			}
		}

		if (velocity.y > 0 && velocity.y <= FREE_ROTATION_RANGE) {
			rotation += 600 * delta;

			if (rotation > 20) {
				rotation = 20;
			}
		}

		if (velocity.y > FREE_ROTATION_RANGE) {
			rotation += 600 * delta;
		}

		// Rotate clockwise
		if (isFalling() || !isAlive()) {
			rotation += 480 * delta;
			if (rotation > 90) {
				rotation = 90;
			}

		}

	}

	public void updateReady(float runTime) {
		position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
	}

	public boolean isFalling() {
		return velocity.y > FALLING_RANGE;
	}

	public boolean shouldntFlap() {
		return velocity.y > DONT_FLAPP_RANGE || !isAlive();
	}

	public void onClick() {
		if (isAlive()) {
			AssetLoader.flap.play();
			velocity.y = -140;
		}
	}

	public void decreaseLives() {
		lives--;
		if (lives <= 0) {
			die();
		}
	}

	private void die() {
		lives = 0;
		velocity.y = 0;
	}

	public void decelerate() {
		acceleration.y = 0;
	}

	public void onRestart(int y) {
		rotation = 0;
		position.y = y;
		velocity.x = 0;
		velocity.y = Y_SPEED;
		acceleration.x = 0;
		acceleration.y = Y_ACCELERATION;
		circleRadius = 6.5f;
		height = 2 * circleRadius;
		lives = 1;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		float width = height * 35/29;
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public boolean isAlive() {
		return lives > 0;
	}

	public void setYSpeed(float ySpeed) {
		velocity.y = ySpeed;
	}
	
	public void largerRadius() {
		circleRadius = circleRadius + 0.5f;
		height = 2 * circleRadius;
	}
	
	public void smallerRadius() {
		circleRadius = circleRadius - 2f;
		height = 2 * circleRadius;
		
		if(circleRadius <= MIN_BIRD_RADIUS) {
			die();
		}
	}
	
	public float getCircleRadius() {
		return circleRadius;
	}

	public void setCircleRadius(float circleRadius) {
		this.circleRadius = circleRadius;
	}

}
