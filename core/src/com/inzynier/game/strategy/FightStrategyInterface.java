package com.inzynier.game.strategy;

import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.FighterInterface;

public interface FightStrategyInterface {

    public void fight(FighterInterface fighter, World world);
}
