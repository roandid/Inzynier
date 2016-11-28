package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


//wspólna klasa dla animowanych obiektów
public class B2DSprite {
	protected Body body;
	//protected Animation animation;
	protected float width;
	protected float height;
	
	public B2DSprite(Body body) {
		this.body = body;
		//animation = new Animation();
	}

	public void setAnimation(TextureRegion[] region, float delay) {
		//animation.setFrames(region, delay);
		width = region[0].getRegionWidth();
		height = region[0].getRegionHeight();
	}

	public void update(float dt) {
		//animation.update(dt);

	}

	public void render(SpriteBatch sb) {

		sb.begin();
	//	sb.draw(animation.getFrame(), body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		sb.end();
	}

	public Body getBody() {
		return body;
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

}
