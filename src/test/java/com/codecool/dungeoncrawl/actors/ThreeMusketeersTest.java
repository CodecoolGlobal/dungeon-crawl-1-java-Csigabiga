package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreeMusketeersTest {

    GameMap gameMap = new GameMap(11, 11, CellType.FLOOR);


    @Test
    void musketeerCanNotMoveIntoWall() {
        gameMap.getCell(9, 10).setType(CellType.WALL);
        ThreeMusketeers threeMusketeers = new ThreeMusketeers(gameMap.getCell(9, 9), 1,1,1);
        assertFalse(threeMusketeers.move(0, 1));
    }


    @Test
    void musketeerCanNotMoveIntoActors() {
        ThreeMusketeers threeMusketeers = new ThreeMusketeers(gameMap.getCell(9, 9), 1,1,1);
        new Player(gameMap.getCell(9, 10), 1, 1, 1);
        new Bomber(gameMap.getCell(9, 8), 1, 1, 1);
        assertFalse(threeMusketeers.move(0, 1));
        assertFalse(threeMusketeers.move(0, -1));
    }


    @Test
    void musketeersCanMoveOneHexPerRound() {
        ThreeMusketeers threeMusketeers = new ThreeMusketeers(gameMap.getCell(9, 9), 1,1,1);
        new Player(gameMap.getCell(9, 5), 1, 1, 1);
        threeMusketeers.mobRound();
        assertEquals(threeMusketeers, gameMap.getCell(9, 8).getActor());
    }


    @Test
    void musketeerMoveTowardsPlayer() {
        ThreeMusketeers threeMusketeers = new ThreeMusketeers(gameMap.getCell(9, 9), 1,1,1);
        Player player = new Player(gameMap.getCell(9, 5), 1, 1, 1);
        threeMusketeers.mobRound();
        assertEquals(threeMusketeers, gameMap.getCell(9, 8).getActor());
        player.move(-1, 0);
        threeMusketeers.mobRound();
        assertEquals(threeMusketeers, gameMap.getCell(8, 7).getActor());
        player.move(2, -1);
        threeMusketeers.mobRound();
        assertEquals(threeMusketeers, gameMap.getCell(9, 6).getActor());
    }


    @Test
    void musketeerDoesNotMoveIfPlayerNotInRange() {
        ThreeMusketeers threeMusketeers = new ThreeMusketeers(gameMap.getCell(10, 10), 1,1,1);
        new Player(gameMap.getCell(0, 0), 1, 1, 1);
        threeMusketeers.mobRound();
        assertEquals(threeMusketeers, gameMap.getCell(10, 10).getActor());
    }


    @Test
    void musketeerMoveWhenPlayerInRange() {
        ThreeMusketeers threeMusketeers = new ThreeMusketeers(gameMap.getCell(10, 10), 1,1,1);
        Player player = new Player(gameMap.getCell(0, 0), 1, 1, 1);
        threeMusketeers.mobRound();
        assertEquals(threeMusketeers, gameMap.getCell(10, 10).getActor());
        player.move(1, 1);
        threeMusketeers.mobRound();
        assertEquals(threeMusketeers, gameMap.getCell(10, 10).getActor());
        player.move(1, 1);
        threeMusketeers.mobRound();
        assertNotEquals(threeMusketeers, gameMap.getCell(10, 10).getActor());
    }


    @Test
    void musketeerDealDamage() {
        ThreeMusketeers threeMusketeers = new ThreeMusketeers(gameMap.getCell(10, 10), 1,5,1);
        Player player = new Player(gameMap.getCell(9, 10), 10, 1, 1);
        threeMusketeers.mobRound();
        assertEquals(6, player.getHealth());
    }


    @Test
    void musketeerTakeDamage() {
        ThreeMusketeers threeMusketeers = new ThreeMusketeers(gameMap.getCell(10, 10), 10,1,1);
        Player player = new Player(gameMap.getCell(9, 10), 1, 5, 1);
        player.calculateAttack(threeMusketeers);
        assertEquals(6, threeMusketeers.getHealth());
    }


    @Test
    void musketeerDie() {
        ThreeMusketeers threeMusketeers = new ThreeMusketeers(gameMap.getCell(10, 10), 1,1,1);
        Player player = new Player(gameMap.getCell(9, 10), 1, 5, 1);
        player.calculateAttack(threeMusketeers);
        assertFalse(threeMusketeers.isAlive());
    }
}
