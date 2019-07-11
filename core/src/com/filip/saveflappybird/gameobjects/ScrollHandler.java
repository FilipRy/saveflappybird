package com.filip.saveflappybird.gameobjects;

import com.filip.saveflappybird.gameworld.GameWorld;
import com.filip.saveflappybird.zbhelpers.AssetLoader;

public class ScrollHandler {

	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	private ScoreObjects scoreObjects;
	public static final int SCROLL_SPEED = -100;
	public static final int SCROLL_SPEED_DIFF = 50; 
	
	public static final int PIPE_GAP = 70;

	private GameWorld gameWorld;

	private float groundY;
	
	
	private float timeToDecreaseSpeed = 0.5f;

	public ScrollHandler(GameWorld gameWorld, float yPos) {
		this.gameWorld = gameWorld;
		frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
		backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11,
				SCROLL_SPEED);

		pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
		pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, Pipe.PIPE_WIDTH, 70,
				SCROLL_SPEED, yPos);
		pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, Pipe.PIPE_WIDTH, 60,
				SCROLL_SPEED, yPos);

		groundY = yPos;
		
		scoreObjects = new ScoreObjects(gameWorld.getBird(), groundY);


	}

	public void updateReady(float delta) {

		frontGrass.update(delta);
		backGrass.update(delta);

		// Same with grass
		if (frontGrass.isScrolledOut()) {
			frontGrass.reset(backGrass.getTailX());

		} else if (backGrass.isScrolledOut()) {
			backGrass.reset(frontGrass.getTailX());

		}

	}

	public void update(float delta) {
		
		
		if(frontGrass.getScrollSpeed() < ScrollHandler.SCROLL_SPEED){
			System.out.println("time to decrease speed "+this.timeToDecreaseSpeed);
			
			timeToDecreaseSpeed = timeToDecreaseSpeed - Math.abs(delta);
		}
		
		
		if(timeToDecreaseSpeed <= 0f){
			if(frontGrass.getScrollSpeed() < ScrollHandler.SCROLL_SPEED) {
				decreaseSpeed(10);
				timeToDecreaseSpeed = 0.5f;
			}
		}
		
		// Update our objects
		frontGrass.update(delta);
		backGrass.update(delta);
		pipe1.update(delta);
		pipe2.update(delta);
		pipe3.update(delta);
		scoreObjects.update(delta);

		// Check if any of the pipes are scrolled left,
		// and reset accordingly
		if (pipe1.isScrolledOut()) {
			pipe1.reset(pipe3.getTailX() + PIPE_GAP);
		} else if (pipe2.isScrolledOut()) {
			pipe2.reset(pipe1.getTailX() + PIPE_GAP);

		} else if (pipe3.isScrolledOut()) {
			pipe3.reset(pipe2.getTailX() + PIPE_GAP);
		}

		// Same with grass
		if (frontGrass.isScrolledOut()) {
			frontGrass.reset(backGrass.getTailX());

		} else if (backGrass.isScrolledOut()) {
			backGrass.reset(frontGrass.getTailX());

		}

	}

	public void stop() {
		frontGrass.stop();
		backGrass.stop();
		pipe1.stop();
		pipe2.stop();
		pipe3.stop();
		scoreObjects.stop();
	}

	public boolean collides(Bird bird) {

		boolean collidesPipe1, collidesPipe2, collidesPipe3;

		collidesPipe1 = pipe1.collides(bird);
		collidesPipe2 = pipe2.collides(bird);
		collidesPipe3 = pipe3.collides(bird);

		if (!pipe1.isScored()
				&& pipe1.getX() + (pipe1.getWidth() / 2) < bird.getX()
						+ bird.getWidth()) {
			addScore(1);
			pipe1.setScored(true);
			AssetLoader.coin.play();
		} else if (!pipe2.isScored()
				&& pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX()
						+ bird.getWidth()) {
			addScore(1);
			pipe2.setScored(true);
			AssetLoader.coin.play();

		} else if (!pipe3.isScored()
				&& pipe3.getX() + (pipe3.getWidth() / 2) < bird.getX()
						+ bird.getWidth()) {
			addScore(1);
			pipe3.setScored(true);
			AssetLoader.coin.play();

		}
		
		ScoreObject food = scoreObjects.getFoodCollidingBird();
		

		if (food != null) {
			
			if(!food.isScored() && bird.isAlive()) {
				
				if (food.getEnemyTypeEnum() == ScoreObjectType.SpeedBird) {
					this.timeToDecreaseSpeed = 0.5f;
					incrementSpeed();
				}
				
				else if(food.getEnemyTypeEnum() == ScoreObjectType.WeightBird) {
					if (food.getRadius() > bird.getCircleRadius()) {
						bird.smallerRadius();
						food.runOut();
					}
					if (food.getRadius() <= bird.getCircleRadius()) {
						bird.largerRadius();
						food.die();
					}
				}
				
				
				
				food.setScored(true);
				
				
				if(!bird.isAlive()) {
					return true;
				}
			}
			
		}
		
		return (collidesPipe1 || collidesPipe2 || collidesPipe3);
	}

	public boolean birdCollidesPipeSkull(Bird bird) {

		boolean collides = pipe1.birdJumpAfterSkullCollicion(bird)
				|| pipe2.birdJumpAfterSkullCollicion(bird)
				|| pipe3.birdJumpAfterSkullCollicion(bird);

		return collides;
	}

	public Pipe getNearestPipeToBird(Bird bird) {
		float p1 = pipe1.getDistanceToBird(bird);
		float p2 = pipe2.getDistanceToBird(bird);
		float p3 = pipe3.getDistanceToBird(bird);

		if (p1 < p2 && p1 < p3) {
			return pipe1;
		}
		if (p2 < p1 && p2 < p3) {
			return pipe2;
		}
		if (p3 < p1 && p3 < p2) {
			return pipe3;
		}
		return null;
	}

	private void addScore(int increment) {
		scoreObjects.birdGetScored();
		gameWorld.addScore(increment);
	}

	public void incrementSpeed() {
		 pipe1.incrementSpeed();
		 pipe2.incrementSpeed();
		 pipe3.incrementSpeed();
		 frontGrass.incrementSpeed();
		 backGrass.incrementSpeed();
	}
	
	public void decreaseSpeed(int diff) {  
		pipe1.decreaseSpeed(diff);
		pipe2.decreaseSpeed(diff);
		pipe3.decreaseSpeed(diff);
		frontGrass.decreaseSpeed(diff);
		backGrass.decreaseSpeed(diff);
	}

	public Grass getFrontGrass() {
		return frontGrass;
	}

	public Grass getBackGrass() {
		return backGrass;
	}

	public Pipe getPipe1() {
		return pipe1;
	}

	public Pipe getPipe2() {
		return pipe2;
	}

	public Pipe getPipe3() {
		return pipe3;
	}

	public void onRestart() {
		frontGrass.onRestart(0, SCROLL_SPEED);
		backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
		pipe1.onRestart(210, SCROLL_SPEED);
		pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
		pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);

		scoreObjects.onRestart();
	}

	public ScoreObjects getScoreObjects() {
		return scoreObjects;
	}

	public void setScoreObjects(ScoreObjects scoreObjects) {
		this.scoreObjects = scoreObjects;
	}


}
