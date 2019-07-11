package com.filip.saveflappybird.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends Scrollable {

	private Random r;

	private Rectangle skullUp, skullDown, barUp, barDown;
	public Rectangle tmpRect;

	public static final int VERTICAL_GAP = 60;
	public static final int PIPE_WIDTH = 22;
	public static final int SKULL_WIDTH = 32;
	public static final int SKULL_HEIGHT = 11;

	public static final int BAR_HEIGHT_OUT_SCREEN = 400;
	private final int CLICK_PIPE_RANGE = 15;

	private float groundY;

	private boolean isScored = false;
	//private boolean birdCameOver = false; // bird came over the pipe with
											// colliding it(lose on live)
	//private boolean birdJumped = false; // bird jumped after skull collision

	private boolean isSelectedForMove = false;
	private float oldY = 0f;

	// When Pipe's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Pipe(float x, float y, int width, int height, float scrollSpeed,
			float groundY) {
		super(x, y - BAR_HEIGHT_OUT_SCREEN, width, height
				+ BAR_HEIGHT_OUT_SCREEN, scrollSpeed);
		// Initialize a Random object for Random number generation
		r = new Random();
		skullUp = new Rectangle();
		skullUp.x = -SKULL_WIDTH;
		skullDown = new Rectangle();
		skullDown.x = -SKULL_WIDTH;
		barUp = new Rectangle();
		barDown = new Rectangle();

		this.groundY = groundY;
	}

	@Override
	public void update(float delta) {
		// Call the update method in the superclass (Scrollable)
		super.update(delta);

		// The set() method allows you to set the top left corner's x, y
		// coordinates,
		// along with the width and height of the rectangle

		barUp.set(position.x, position.y, width, height);
		barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
				groundY - (position.y + height + VERTICAL_GAP));

		// Our skull width is 24. The bar is only 22 pixels wide. So the skull
		// must be shifted by 1 pixel to the left (so that the skull is centered
		// with respect to its bar).

		// This shift is equivalent to: (SKULL_WIDTH - width) / 2
		skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height
				- SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
		skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y,
				SKULL_WIDTH, SKULL_HEIGHT);
	}

	@Override
	public void reset(float newX) {
		// Call the reset method in the superclass (Scrollable)
		super.reset(newX);
		// Change the height to a random number
		height = r.nextInt(90) + 15 + BAR_HEIGHT_OUT_SCREEN;
		super.position.y = -BAR_HEIGHT_OUT_SCREEN;
		isScored = false;
//		birdCameOver = false;
//		birdJumped = false;
	}

	public void onTouchEvent(int screenX, int screenY) {

		boolean moveOnThisPipe = isClickOnPipe(screenX, screenY);

		if (!moveOnThisPipe) {
			return;
		}

		oldY = screenY;
		isSelectedForMove = true;

	}

	public void onDragEvent(int screenX, int screenY) {

		if (!isSelectedForMove) {
			return;
		}
		float diffY = (screenY - oldY);

		if (position.y + height + VERTICAL_GAP + diffY > groundY - 5) {
			oldY = screenY;
			return;
		}

		super.position.y = super.position.y + diffY;

		oldY = screenY;

	}

	public void onTouchUpEvent(int screenX, int screenY) {
		isSelectedForMove = false;

	}

	public boolean isClickOnPipe(int clickX, int clickY) {

		return clickX + CLICK_PIPE_RANGE >= position.x
				&& clickX <= position.x + width + CLICK_PIPE_RANGE
				&& clickY <= groundY;

	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x);
	}

	public Rectangle getSkullUp() {
		return skullUp;
	}

	public Rectangle getSkullDown() {
		return skullDown;
	}

	public Rectangle getBarUp() {
		return barUp;
	}

	public Rectangle getBarDown() {
		return barDown;
	}

	public boolean collides(Bird bird) {

		if (position.x < bird.getX() + bird.getWidth()) {

			tmpRect = new Rectangle(barDown);
			tmpRect.setY(barDown.getY() + 5);

			boolean intersects = (Intersector.overlaps(
					bird.getBoundingCircle(), tmpRect)
					|| Intersector.overlaps(bird.getBoundingCircle(), barUp) || Intersector
					.overlaps(bird.getBoundingCircle(), skullUp));

//			if (birdJumped) {
//				return false;
//			}

			return intersects;

		}
		return false;
	}

	public boolean collidesDownPipe(Bird bird) {
		
		if (position.x < bird.getX() + bird.getWidth()) {

			tmpRect = new Rectangle(barDown);
			tmpRect.setY(barDown.getY() + 5);

			boolean intersects = Intersector.overlaps(bird.getBoundingCircle(),
					tmpRect);

			return intersects;

		}
		return false;
	}

	public boolean birdJumpAfterSkullCollicion(Bird bird) {

//		boolean jump = !birdCameOver && birdCollidesSkull(bird);
//		if (jump) {
//			birdJumped = true;
//		}
//
//		return jump;
		return birdCollidesSkull(bird);
	}

	private boolean birdCollidesSkull(Bird bird) {

		boolean collidesHeight = ((bird.getY() + bird.getHeight() - skullDown
				.getY()) < 3 && (bird.getY() + bird.getHeight() - skullDown
				.getY()) > 0);
		boolean collidesWidth = (skullDown.getX() - bird.getX() < 14)
				&& ((bird.getX() + bird.getWidth())
						- (skullDown.getX() + skullDown.getWidth()) < 14);

		return collidesHeight && collidesWidth;

	}

	public boolean isBirdInPipeRange(Bird bird) {
		return position.x <= bird.getX() + bird.getWidth()
				+ Bird.NO_JUMP_BEFORE_PIPE_RANGE
				&& position.x + width >= bird.getX() + bird.getWidth();
	}

	public float getDistanceToBird(Bird bird) {
		float s = position.x - (bird.getX());
		if (s < 0) {
			return Float.MAX_VALUE;
		}
		return s;
	}

	public float getUpdateCountBirdToPipe(Bird bird, float deltaTime) {
		float s = getDistanceToBird(bird);

		float updateCount = s / (-ScrollHandler.SCROLL_SPEED * deltaTime);
		return updateCount;
	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean b) {
		isScored = b;
	}
}
