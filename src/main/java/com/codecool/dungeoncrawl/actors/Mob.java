package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.io.Serializable;

public abstract class Mob extends Actor implements Serializable {

    protected String tileName;


    public Mob(Cell cell, int health, int attackPower, int defensePower, String tileName) {
        super(cell, health, attackPower, defensePower);
        this.tileName = tileName;
    }


    @Override
    public String getTileName() {return tileName;}


    public abstract void mobRound();


    public void dealDamage (int damage, Actor actor) {actor.setHealth(damage);}


    public Actor lookingForPlayer() {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    Actor player = getCell().getNeighbor(i, j).getActor();
                    if (player != null) {
                        if (player.getTileName().equals("player")) {
                            return player;
                        }
                    }
                }
                catch (Exception exception) {
                    return null;
                }
            }
        }
        return null;
    }


    public boolean isAlive () {
        if (getHealth() <= 0) {
            tileName = "corpse";
            getCell().setType(CellType.CORPSE);
            getCell().setActor(null);
            return false;
        }
        return true;
    }
}
