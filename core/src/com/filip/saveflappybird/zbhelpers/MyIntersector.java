package com.filip.saveflappybird.zbhelpers;

import com.badlogic.gdx.math.Circle;

public class MyIntersector {
	
	public static boolean collidesCircle(Circle c1, Circle c2) {
		
		float xDiff = c1.x - c2.x;
		float yDiff = c1.y - c2.y;
		
		float dist = (float) Math.sqrt(xDiff*xDiff + yDiff*yDiff);
		
		if(dist - (c1.radius + c2.radius) < 5f) {
			System.out.println(dist - (c1.radius + c2.radius));
		}
		
		return dist - (c1.radius + c2.radius) < 5f;
	}

}
