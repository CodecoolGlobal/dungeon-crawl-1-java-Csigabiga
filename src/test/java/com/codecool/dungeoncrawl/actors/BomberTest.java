package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BomberTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);


    @Test
    void BomberDoNotExplodeWithoutSensingPlayer() {
        Bomber bomber = new Bomber(gameMap.getCell(1,1), 4, 5, 0);
        bomber.mobRound();
        assertEquals("sleep", bomber.getTileName());
    }


    @Test
    void bomberSensePlayer() {
        Bomber bomber = new Bomber(gameMap.getCell(1,1), 4, 5, 0);
        new Player(gameMap.getCell(0,1), 10, 5, 2);
        bomber.mobRound();
        assertEquals("ready", bomber.getTileName());
    }


    @Test
    void bomberExplode() {
        Bomber bomber = new Bomber(gameMap.getCell(1,1), 4, 5, 0);
        new Player(gameMap.getCell(0,1), 10, 5, 2);
        bomber.mobRound();
        bomber.mobRound();
        assertEquals("explode", bomber.getTileName());
    }


    @Test
    void bomberStillExplodeIfPlayerMoveAway() {
        Bomber bomber = new Bomber(gameMap.getCell(1,1), 4, 5, 0);
        Player player = new Player(gameMap.getCell(0,1), 10, 5, 2);
        bomber.mobRound();
        player.getCell().setActor(null);
        bomber.mobRound();
        bomber.mobRound();
        assertFalse(bomber.isAlive());
    }


    @Test
    void bomberDealDamage() {
        Bomber bomber = new Bomber(gameMap.getCell(1,1), 4, 5, 0);
        Player player = new Player(gameMap.getCell(0,1), 10, 5, 2);
        bomber.mobRound();
        bomber.mobRound();
        assertEquals(7, player.getHealth());
    }


    @Test
    void bomberDisappearIfExploded() {
        Bomber bomber = new Bomber(gameMap.getCell(1,1), 4, 5, 0);
        new Player(gameMap.getCell(0,1), 10, 5, 2);
        bomber.mobRound();
        bomber.mobRound();
        bomber.mobRound();
        assertFalse(bomber.isAlive());
        assertNull(gameMap.getCell(1,1).getActor());
    }


    @Test
    void bomberDoNotExplodeCauseOfOtherMonsters() {
        Bomber bomber = new Bomber(gameMap.getCell(1,1), 4, 5, 0);
        new Skeleton(gameMap.getCell(0, 0), 1, 1, 1);
        new ThreeMusketeers(gameMap.getCell(2, 2), 1, 1, 1);
        bomber.mobRound();
        assertEquals("sleep", bomber.getTileName());
    }


    @Test
    void bomberTakesDamage() {
        Bomber bomber = new Bomber(gameMap.getCell(1,1), 6, 5, 0);
        Player player = new Player(gameMap.getCell(0,1), 10, 5, 2);
        player.calculateAttack(bomber);
        assertEquals(1, bomber.getHealth());
    }


    @Test
    void bomberDoNotExplodeIfDead() {
        Bomber bomber = new Bomber(gameMap.getCell(1,1), 4, 5, 0);
        Player player = new Player(gameMap.getCell(0,1), 10, 5, 2);
        player.calculateAttack(bomber);
        assertFalse(bomber.isAlive());
        bomber.mobRound();
        bomber.mobRound();
        bomber.mobRound();
        assertEquals(10, player.getHealth());
    }
}
