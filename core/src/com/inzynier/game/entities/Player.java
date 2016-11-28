package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.inzynier.game.MyGame;

public class Player extends B2DSprite {
	
	
	private Texture right;
	private Texture left;
	private TextureRegion[] spritesRight;
	private TextureRegion[] spritesLeft;
	
	public Player(Body body) {
		super(body);
		
		/*right = MyGame.res.getTexture("megaman");
		spritesRight = TextureRegion.split(right, 32, 32)[0];
		left = MyGame.res.getTexture("left");
		spritesLeft = TextureRegion.split(left, 32, 32)[0];
		setAnimation(spritesRight, 1 / 12f);*/
	}
	
	public void setTexture(boolean isLeft){
		if(isLeft){
			setAnimation(spritesLeft, 1/12f);
		} else
		{
			setAnimation(spritesRight, 1/12f);
		}
	}

}
