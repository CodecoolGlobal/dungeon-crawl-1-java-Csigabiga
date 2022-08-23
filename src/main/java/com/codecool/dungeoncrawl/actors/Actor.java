package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {

    protected Cell cell;
    private int health;
    private final int defensePower;
    protected int attackPower;

    public Actor(Cell cell, int health, int attackPower, int defensePower) {
        this.cell = cell;
        this.health = health;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.cell.setActor(this);
    }

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

    public int getHealth() {
        return health;
    }

    public void setHealth(int damage) {
        if(health - damage + defensePower <=0){
            this.health=0;
        }
        else{
            this.health = health - damage + defensePower;
        }
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell){
        this.cell = cell;
    }

    public void heal(int hp) {
        if (this.health < 10) {
            this.health += hp;
        }
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public int getDefensePower() {return defensePower;}

    public int getAttackPower() {
        return attackPower;
    }
}
