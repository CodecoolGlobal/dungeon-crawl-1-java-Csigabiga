package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Player extends Actor {
    public Player(Cell cell, int health) {
        super(cell, health);
        this.setAttackPower(5);
        this.setDefensePower(1);
    }

    public void attack(int dx, int dy){
        Cell attackCell = getCell().getNeighbor(dx, dy);
        if(attackCell.getActor() != null){
            Actor enemy = attackCell.getActor();
            calculateAttack(enemy);

        }
    }

    public String getTileName() {
        return "player";
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        if (getCell().getItem() != null) {
            if (getCell().getItem().getClass().getSimpleName().equals("Sword") || getCell().getItem().getClass().getSimpleName().equals("Key")) {
                Main.setButtonDisabledStatus(false);
                Item.addToInventory(getCell().getItem());
                getCell().setItem(null);
            }
        }
    }

    public void calculateAttack(Actor enemy){
        int playerHealth = getHealth();
        int playerAttackPower = getAttackPower();
        int playerDefensePower = getDefensePower();
        int enemyHealth =  enemy.getHealth();
        int enemyAttackPower = enemy.getAttackPower();
        int enemyDefensePower = enemy.getDefensePower();
        enemy.setHealth((enemyHealth + enemyDefensePower) - playerAttackPower);
        if(enemyHealth<= 0){
            enemy.getCell().setType(CellType.CORPSE);
            enemy.getCell().setActor(null);

        }
        else{
            this.setHealth((playerHealth + playerDefensePower) - enemyAttackPower);
        }
    }
}
