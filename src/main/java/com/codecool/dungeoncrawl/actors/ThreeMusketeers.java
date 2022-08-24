package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class ThreeMusketeers extends Mob implements Move{


    public ThreeMusketeers(Cell cell, int health, int attackPower, int defensePower) {
        super(cell, health, attackPower, defensePower, "musketeer");
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
        Actor player = lookingForPlayer();
        boolean found = false;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Actor playerNear = isPlayerNear(7 - i, 7 - j);
                if (playerNear != null) {
                    if (playerNear.getTileName().equals("player")) {
                        int xDiff = playerNear.getX() - getX();
                        int yDiff = playerNear.getY() - getY();
                        move(Integer.signum(xDiff), 0);
                        move(0, Integer.signum(yDiff));
                        found = true;
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }
        if (player != null) {
            dealDamage(attackPower, player);
        }
    }


    private Actor isPlayerNear(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    Actor actor = getCell().getNeighbor(x + i, y + j).getActor();
                    if (actor != null) {
                        if (actor.getTileName().equals("player")) {
                            return actor;
                        }
                    }
                } catch (Exception exception) {return  null;}
            }
        }
        return null;
    }
}
