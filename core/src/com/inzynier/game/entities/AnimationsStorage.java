package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.inzynier.game.Constants;

public class AnimationsStorage {

    public enum Action {
        WALK_LEFT,
        WALK_RIGHT,
        WALK_UP,
        WALK_DOWN,
        STAY_LEFT,
        STAY_RIGHT,
        STAY_FRONT,
        STAY_DOWN
    }

    protected Animation walkLeft;
    protected Animation walkRight;
    protected Animation walkUp;
    protected Animation walkDown;

    protected Animation stayLeft;
    protected Animation stayRight;
    protected Animation stayFront;
    protected Animation stayDown;

    protected Animation currentAnimation;
    protected Action currentAction;

    float frameRate;

    public AnimationsStorage(TextureAtlas atlas) {
        this.walkLeft = this.createAnimation("left", atlas);
        this.walkRight = this.createAnimation("right", atlas);
        this.walkUp = this.createAnimation("up", atlas);
        this.walkDown = this.createAnimation("down", atlas);

        this.stayLeft = this.createAnimation("stay_left", atlas);
        this.stayRight = this.createAnimation("stay_right", atlas);
        this.stayFront = this.createAnimation("stay_front", atlas);
        this.stayDown = this.createAnimation("stay_back", atlas);

        this.currentAction = Action.WALK_UP;
        this.currentAnimation = this.walkUp;

        this.frameRate = 0.0f;
    }

    public void play(float dt, SpriteBatch sb, Vector2 position) {
        this.frameRate += dt;

        TextureRegion texture = this.currentAnimation.getKeyFrame(this.frameRate);

        sb.draw(
            texture,
            Constants.fromBox2d(position.x) - texture.getRegionWidth() * 0.025f,
            Constants.fromBox2d(position.y) - texture.getRegionHeight() * 0.0055f
        );
    }

    public boolean stands() {
        return this.currentAction == Action.STAY_DOWN
            || this.currentAction == Action.STAY_FRONT
            || this.currentAction == Action.STAY_LEFT
            || this.currentAction == Action.STAY_RIGHT;
    }

    public void stop() {
        if (this.stands()) {
            return;
        }

        if (!this.hasStaysAnimations()) {
            return;
        }

        switch (this.currentAction) {
            case WALK_LEFT:
                this.currentAnimation = this.stayLeft;
                this.currentAction = Action.STAY_LEFT;
                break;
            case WALK_RIGHT:
                this.currentAnimation = this.stayRight;
                this.currentAction = Action.STAY_RIGHT;
                break;
            case WALK_DOWN:
                this.currentAnimation = this.stayFront;
                this.currentAction = Action.STAY_FRONT;
                break;
            case WALK_UP:
                this.currentAnimation = this.stayDown;
                this.currentAction = Action.STAY_DOWN;
                break;
            default:
                this.currentAnimation = this.stayFront;
                this.currentAction = Action.STAY_FRONT;
        }
    }

    public void changeAnimation(Action action) {

        if (action == this.currentAction) {
            return;
        }

        this.currentAction = action;

        switch (action) {
            case WALK_LEFT:
                this.currentAnimation = this.walkLeft;
                break;
            case WALK_RIGHT:
                this.currentAnimation = this.walkRight;
                break;
            case WALK_DOWN:
                this.currentAnimation = this.walkDown;
                break;
            case WALK_UP:
                this.currentAnimation = this.walkUp;
                break;
            default:
                this.currentAnimation = this.walkUp;
        }
    }

    private Animation createAnimation(String region, TextureAtlas atlas) {
        Array<AtlasRegion> frames = atlas.findRegions(region);

        if (frames.size == 0) {
            return null;
        }

        return new Animation(0.15f, frames, PlayMode.LOOP);
    }

    private boolean hasStaysAnimations() {
        return !(this.stayDown == null
            || this.stayFront == null
            || this.stayLeft == null
            || this.stayRight == null);
    }
}
