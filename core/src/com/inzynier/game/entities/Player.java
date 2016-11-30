package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.inzynier.game.MyGame;

public class Player extends B2DSprite {
	
	
	private Texture player;
	private Texture left;
	private TextureRegion[] spritesRight;
	private TextureRegion[] spritesLeft;
	
	public Player(Body body) {
		super(body);
		
		player = new Texture("issac.png");
		spritesRight = TextureRegion.split(player, 48, 64)[0];
		setAnimation(spritesRight, 1 / 12f);
		
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
