package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int defensePower;
    private int attackPower;

    public Actor(Cell cell, int health, int attackPower, int defensePower) {
        this.cell = cell;
        this.health = health;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().getTileName().matches("floor|floor1|floor2|corpse|closed|openedBlueDoor") &&
             nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int damage) {this.health = health - damage + defensePower;}

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setDefensePower(int defensePower) {this.defensePower = defensePower;}

    public int getDefensePower() {return defensePower;}

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
