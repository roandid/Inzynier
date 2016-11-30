/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;

/**
 *
 * @author M.Koszowski
 */
public class BasicBullet implements InterfaceBullet {

    private TextureRegion[] spritesRight;
    private TextureRegion[] spritesLeft;

    private float startPosX = 0;
    private float startPosY = 0;

    private float width = 0;
    private float height = 0;

    private float forceX = 0;
    private float forceY = 0;

    private Body body = null;
    
    public BasicBullet() {
    }

    public BasicBullet(float startPosX, float startPosY, byte dir, float force) {
        this.setSize(5, 5);
        this.setStartPosition(startPosX, startPosY);
        this.setForceDir(dir, force);
    }
    
    @Override
    public void setStartPosition(float x, float y) {
        this.startPosX = x;
        this.startPosY = y;
    }

    @Override
    public void setSize(float height, float width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public InterfaceBullet generateBullet(World world) {
        //Prawdopodbnie do zmiany. Nie wiem za bardzo co sie tu dzieje, ale sie generuje :)
        //Można to przenieś jako funkcje dziedziczącą - do ustalenia
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.position.set(this.startPosX, this.startPosY);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 3.5f;

        this.body = world.createBody(bodyDef);

        shape.setAsBox(this.width, this.height);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Constants.BIT_PLAYER;
        fixtureDef.filter.maskBits = Constants.BIT_WALL;
        this.body.createFixture(fixtureDef);
        
        return this;
    }

    @Override
    public void update() {
        this.body.applyForceToCenter(this.forceX, this.forceY, true);
    }

    @Override
    public void setForceDir(byte dir, float force) {
        //ustawianie kierunku - zrobić, żeby dziedziczyła po jakieś głownej klasie pocisku. Do dyskusji
        switch (dir) {
            case InterfaceBullet.UP: {
                this.forceX = 0;
                this.forceY = force;
            }
            break;
            case InterfaceBullet.DOWN: {
                this.forceX = 0;
                this.forceY = -force;
            }
            break;
            case InterfaceBullet.LEFT: {
                this.forceX = -force;
                this.forceY = 0;
            }
            break;
            case InterfaceBullet.RIGHT: {
                this.forceX = force;
                this.forceY = 0;
            }
            break;
            default: {

            }
            break;
        }
    }
}
