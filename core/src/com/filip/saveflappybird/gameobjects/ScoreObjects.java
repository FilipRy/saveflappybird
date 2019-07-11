package com.filip.saveflappybird.gameobjects;

import java.util.Random;

public class ScoreObjects{
	
	private ScoreObject food[];
	private Bird birdRef;
	private float groundY;
	private int currentObjectsCount;
	
	private int scoreToAddObject;
	private int thresholdToAddobject;
	
	private final int MAX_OBJECTS_COUNT = 8;
	
	
	public ScoreObjects (Bird bird, float groundY) {
		this.food = new ScoreObject[MAX_OBJECTS_COUNT];
		this.birdRef = bird;
		this.groundY = groundY;
		currentObjectsCount = 1;
		
		scoreToAddObject = 2;
		thresholdToAddobject = 2;
		
		for(int i = 0; i < currentObjectsCount; i++) {
			generateScoreObj(i);
		}
		
	}

	private void generateScoreObj(int i) {
		float food1_x = ScoreObject.generateNewXPosition();
		float food1_y = ScoreObject.generateNewYPosition(groundY,
				ScoreObject.DEFAULT_RADIUS);
		int food1_radius = ScoreObject.generateFoodRadius();
		float food1_speed = ScoreObject.generateFoodSpeed();

		Random rand = new Random();
		int scoreObjectType = rand.nextInt(3) + 1;
		
		food[i] = new ScoreObject(food1_x, food1_y, food1_radius, food1_speed, scoreObjectType);
	}
	
	public void update(float delta) {
		for(int i = 0; i < food.length; i++){
			if(food[i] != null) {
				food[i].update(delta);
				
				if(food[i].isScrolledOut() ) {
					generateScoreObj(i);
				}
				
			}
		}
		
		
	}
	
	public void stop() {
		for(int i = 0; i < food.length; i++){
			if(food[i] != null) {
				food[i].stop();
			}
		}
	}
	
	public void onRestart(){
		generateScoreObj(0);
		
		for(int i = 1; i < food.length; i++){
			food[i] = null;
		}
		currentObjectsCount = 1;
		scoreToAddObject = 2;
		thresholdToAddobject = 2;
	}
	
	
	public ScoreObject getFoodCollidingBird() {
		for(int i = 0; i < food.length; i++) {
			if(food[i] != null) {
				if(food[i].collidesBird(birdRef)){
					return food[i];
				}
			}
		}
		return null;
	}
	
	public void birdGetScored(){
		scoreToAddObject--;
		if(this.scoreToAddObject == 0){
			incrementCurrentObjectsCount();
			thresholdToAddobject++;
			scoreToAddObject = thresholdToAddobject;
		}
	}
	

	public ScoreObject[] getFood() {
		return food;
	}
	
	public int getCurrentObjectsCount() {
		return currentObjectsCount;
	}

	public void setCurrentObjectsCount(int currentObjectsCount) {
		this.currentObjectsCount = currentObjectsCount;
	}
	
	private void incrementCurrentObjectsCount() {
		
		if(this.currentObjectsCount < MAX_OBJECTS_COUNT) {
			this.generateScoreObj(currentObjectsCount++);
		}
		
	}
	
	

}
