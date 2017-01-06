package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public interface FighterInterface {

    public float getRangedPower();

    public Texture getRangedWeaponTexture();

    public Vector2 getPosition();
}
