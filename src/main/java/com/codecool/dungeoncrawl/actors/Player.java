package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.StringJoiner;

public class Player extends Actor {

    private int bonusAttack = 0;
    private int bonusShield = 0;
    public Player(Cell cell, int health, int attackPower, int defensePower) {
        super(cell, health, attackPower, defensePower);
    }
    private final ArrayList<Item> items = new ArrayList<>();

    public String interact(int dx, int dy){
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (nextCell.getActor() != null){
                Actor enemy = nextCell.getActor();
                calculateAttack(enemy);
            }
        else if(nextCell.getTileName().equals("closedBlueDoor")){
            openDoor(dx, dy);
        }else if(nextCell.getTileName().equals("stairDown")){
            return "nextLevel";
        } else if (nextCell.getTileName().equals("stairUp")) {
            return "previousLevel";
        }else if (nextCell.getTileName().matches("blueSwitchLeft|blueSwitchRight")) {
            return "blueSwitch";
        }
        return null;
    }



    @Override
    public void setHealth(int damage) {
        super.setHealth(damage-bonusShield);
    }

    public void checkBonuses() {
        for (Item item:
             items) {
            if (item.getTileName().equals("sword")) {
                this.bonusAttack = item.getBonus();
            } else if (item.getTileName().equals("shield")) {
                this.bonusShield = item.getBonus();
            }
        }
    }


    public ArrayList<Item> getInventory () {return items;}

    public void addToInventory() {
        items.add(getCell().getItem());
        getCell().setItem(null);
    }

    public String getTileName() {
        return "player";
    }

    private void pickUpItems() {
        if (getCell().getItem().getClass().getSimpleName().equals("Sword") || getCell().getItem().getClass().getSimpleName().equals("Key")) {
            Main.setButtonDisabledStatus(false);
        }
    }

    private void openDoor(int dx, int dy) {
        for (Item item: items) {
            if (item.getClass().getSimpleName().equals("Key")) {
                getCell().getNeighbor(dx, dy).setType(CellType.OPENBLUEDOOR);
                items.remove(item);
            }
        }
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        if (getCell().getItem() != null) {
            if (getCell().getItem().getClass().getSimpleName().equals("Sword") || getCell().getItem().getClass().getSimpleName().equals("Key") || getCell().getItem().getTileName().equals("shield")) {
                Main.setButtonDisabledStatus(false);
                pickUpItems();
            }
        }
    }

    public int getBonusAttack() {
        return bonusAttack;
    }

    public int getBonusShield() {
        return bonusShield;
    }
    /*  public void moveBox(int dx, int dy, char key) {
        if (getCell().getNeighbor(dx, dy).getTileName().equals("box")) {
            int boxPositionX = getX();
            int boxPositionY = getY();
            int playerPositionX = getCell().getX();
            int playerPositionY = getCell().getY();

            if (key == 'S') {
                boxPositionY = getY() - 1;
            }
        }
    }*/

    public void calculateAttack(Actor enemy){
        enemy.setHealth(getAttackPower()+bonusAttack);
    }

    public String displayItems() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (Item item: items) {
            stringJoiner.add(item.getClass().getSimpleName());
        }
        return stringJoiner.toString();
    }
}
