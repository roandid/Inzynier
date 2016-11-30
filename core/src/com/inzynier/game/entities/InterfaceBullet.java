/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inzynier.game.entities;

import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author M.Koszowski
 */
public interface InterfaceBullet {
    //Interface pocisków
    
    public static final byte LEFT = 0;
    public static final byte RIGHT = 1;
    public static final byte UP = 2;
    public static final byte DOWN = 3;
    
    public void setStartPosition(float x, float y);

    public void setSize(float height, float width);

    public void setForceDir(byte dir, float force);
    
    public InterfaceBullet generateBullet(World world);
    
    public void update();
}
