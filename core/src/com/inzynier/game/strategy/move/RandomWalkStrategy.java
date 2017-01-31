package com.inzynier.game.strategy.move;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.strategy.StrategyInterface;
import java.util.Random;

public class RandomWalkStrategy implements StrategyInterface {

    protected enum State {
        WALK, WAIT
    }

    protected enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected float howLongWalk;
    protected float howLongWait;
    protected float counter;
    protected State state;
    protected Direction direction;
    protected Random generator;
    protected Vector2 force;

    public RandomWalkStrategy(float howLongWalk, float howLongWait) {
        this.howLongWait = howLongWait;
        this.howLongWalk = howLongWalk;
        this.state = State.WAIT;
        this.direction = Direction.DOWN;
        this.counter = 0;
        this.generator = new Random();
    }

    @Override
    public void action(Actor actor, float dt, World world) {
        if (this.state == State.WAIT) {
            this.waitAction(dt);

            return;
        }

        this.walkAction(actor, dt, world);
    }

    protected void waitAction(float dt) {
        this.counter += dt;

        if (this.counter < this.howLongWait) {
            return;
        }

        this.state = State.WALK;
        this.counter = 0;
        this.direction = this.getRandomDirection();
        this.force = this.getForceVector();
    }

    protected void walkAction(Actor actor, float dt, World world) {
        this.counter += dt;

        if (this.counter > this.howLongWalk) {
            this.state = State.WAIT;
            this.counter = 0;

            return;
        }

        actor.getBody().applyForceToCenter(200 * this.force.x, 200 * this.force.y, true);
    }

    protected Direction getRandomDirection() {
        int rand = this.generator.nextInt() % 4;

        switch (rand) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
                return Direction.DOWN;
        }
    }

    protected Vector2 getForceVector() {

        switch (this.direction) {
            case UP:
                return new Vector2(0, 1);
            case DOWN:
                return new Vector2(0, -1);
            case LEFT:
                return new Vector2(-1, 0);
            case RIGHT:
                return new Vector2(1, 0);
            default:
                return new Vector2(1, 1);
        }
    }
}
