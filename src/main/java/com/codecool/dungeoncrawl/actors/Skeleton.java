package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.io.Serializable;
import java.util.Random;

public class Skeleton extends Mob implements Move{

    Random random = new Random();


    public Skeleton(Cell cell, int health, int attackPower, int defensePower) {
        super(cell, health, attackPower, defensePower, "skeleton0");
    }


    @Override
    public boolean move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().getTileName().matches("floor|floor1|floor2|corpse|closed|openedBlueDoor|trapRouteTile") &&
                nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
            return true;
        }
        return false;
    }


    @Override
    public void mobRound() {
        if (tileName.equals("skeleton0")) {
            tileName = "skeleton2";
        } else if (tileName.equals("skeleton2")){
            tileName = "skeleton1";
        } else {tileName = "skeleton2";}
        switch (random.nextInt(4)) {
            case 0:
                move(0, -1);
                break;
            case 1:
                move(0, 1);
                break;
            case 2:
                move(-1, 0);
                break;
            case 3:
                move(1, 0);
                break;
        }
        Actor player = lookingForPlayer();
        if (player != null) {
            if (player.getTileName().equals("player")) {
                dealDamage(attackPower, player);
            }
        }
    }
}
