package com.filip.saveflappybird.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {
	
	
	// Protected is similar to private, but allows inheritance by subclasses.
	protected Vector2 position;
	protected Vector2 velocity;
	protected int width;
	protected int height;
	protected boolean isScrolledOut;
	
	private float scrollSpeed;

	public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
		position = new Vector2(x, y);
		velocity = new Vector2(scrollSpeed, 0);
		this.width = width;
		this.height = height;
		this.scrollSpeed = scrollSpeed;
		isScrolledOut = false;
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));

		// If the Scrollable object is no longer visible:
		if (position.x + width < 0) {
			isScrolledOut = true;
		}
	}

	// Reset: Should Override in subclass for more specific behavior.
	public void reset(float newX) {
		position.x = newX;
		isScrolledOut = false;
	}

	public void stop() {
		velocity.x = 0;
	}
	
	public void incrementSpeed(){
		scrollSpeed = scrollSpeed - ScrollHandler.SCROLL_SPEED_DIFF;
		velocity.x = scrollSpeed;
	}
	
	public void decreaseSpeed(int diff){
		scrollSpeed = scrollSpeed + diff;
		velocity.x = scrollSpeed;
	}
	
	
	// Getters for instance variables
	public boolean isScrolledOut() {
		return isScrolledOut;
	}

	public float getTailX() {
		return position.x + width;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getScrollSpeed() {
		return scrollSpeed;
	}

}
