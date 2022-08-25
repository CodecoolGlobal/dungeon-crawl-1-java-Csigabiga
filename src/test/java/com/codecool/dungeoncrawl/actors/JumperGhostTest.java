package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JumperGhostTest {

    GameMap gameMap = new GameMap(5, 5, CellType.FLOOR);


    @Test
    void jumperGhostCanGoIntoWall() {
        JumperGhost jumperGhost = new JumperGhost(gameMap.getCell(2, 2), 1, 1, 1);
        gameMap.getCell(2, 3).setType(CellType.WALL);
        assertTrue(jumperGhost.move(0, 1));
    }


    @Test
    void jumperGhostCanNotMoveIntoActors() {
        JumperGhost jumperGhost = new JumperGhost(gameMap.getCell(2, 2), 1, 1, 1);
        new Player(gameMap.getCell(1,2), 1, 1, 1);
        new Bomber(gameMap.getCell(3, 2), 1, 1, 1);
        assertFalse(jumperGhost.move(1, 0));
        assertFalse(jumperGhost.move(-1, 0));
    }


    @Test
    void jumperGhostJumpToTheCornerAndBecomeInvisible() {
        JumperGhost jumperGhost = new JumperGhost(gameMap.getCell(2, 2), 1, 1, 1);
        jumperGhost.mobRound();
        assertEquals(jumperGhost, gameMap.getCell(0, 0).getActor());
        assertEquals("orangeWall", jumperGhost.tileName);
    }


    @Test
    void jumperGhostJumpToTheNExtHexIfTheCornerOccupied() {
        JumperGhost jumperGhost = new JumperGhost(gameMap.getCell(2, 2), 1, 1, 1);
        JumperGhost jumperGhost2 = new JumperGhost(gameMap.getCell(2, 3), 1, 1, 1);
        jumperGhost.mobRound();
        jumperGhost2.mobRound();
        assertEquals(jumperGhost2, gameMap.getCell(1, 0).getActor());
    }


    @Test
    void jumperGhostJumpBackAndVisibleAgain() {
        GameMap gameMap1 = new GameMap(7, 7, CellType.FLOOR);
        JumperGhost jumperGhost = new JumperGhost(gameMap1.getCell(3, 3), 1, 1, 1);
        jumperGhost.mobRound();
        jumperGhost.mobRound();
        assertEquals("jumperGhost", jumperGhost.tileName);
        assertNotEquals(jumperGhost, gameMap1.getCell(0, 0).getActor());
    }


    @Test
    void jumperGhostStayInvisibleAndOnTheCornerHexIfNoFreePlaceToMove() {
        GameMap gameMap1 = new GameMap(2, 2, CellType.FLOOR);
        JumperGhost jumperGhost = new JumperGhost(gameMap1.getCell(1, 1), 1, 1, 1);
        jumperGhost.mobRound();
        new Bomber(gameMap1.getCell(0, 1), 1, 1, 1);
        new Bomber(gameMap1.getCell(1, 1), 1, 1, 1);
        new Bomber(gameMap1.getCell(1, 0), 1, 1, 1);
        jumperGhost.mobRound();
        assertEquals(jumperGhost, gameMap1.getCell(0, 0).getActor());
        assertEquals("orangeWall", jumperGhost.tileName);
    }


    @Test
    void jumperGhostDealDamage() {
        boolean assertion = false;
        JumperGhost jumperGhost = new JumperGhost(gameMap.getCell(2, 2), 1, 5, 1);
        Player player = new Player(gameMap.getCell(1, 3), 10, 1, 1);
        Player player2 = new Player(gameMap.getCell(3, 1), 10, 1, 1);
        Player player3 = new Player(gameMap.getCell(3, 3), 10, 1, 1);
        Player player4 = new Player(gameMap.getCell(1, 1), 10, 1, 1);
        boolean move = false;
        while (!move) {
            jumperGhost.mobRound();
            if (gameMap.getCell(0, 0).getActor() == null) {
                move = true;
            }
        }
        jumperGhost.mobRound();
        jumperGhost.mobRound();
        jumperGhost.mobRound();
        if (6 == player.getHealth() || 6 == player2.getHealth() || 6 == player3.getHealth() || 6 == player4.getHealth()) {
            assertion = true;
        }
        assertTrue(assertion);
    }


    @Test
    void jumperGhostTakeDamage() {
        JumperGhost jumperGhost = new JumperGhost(gameMap.getCell(2, 2), 10, 1, 1);
        Player player = new Player(gameMap.getCell(2, 3), 1, 5, 1);
        player.calculateAttack(jumperGhost);
        assertEquals(6, jumperGhost.getHealth());
    }


    @Test
    void jumperGhostDie() {
        JumperGhost jumperGhost = new JumperGhost(gameMap.getCell(2, 2), 1, 1, 1);
        Player player = new Player(gameMap.getCell(2, 3), 1, 5, 1);
        assertTrue(jumperGhost.isAlive());
        player.calculateAttack(jumperGhost);
        assertFalse(jumperGhost.isAlive());
    }


    @Test
    void jumperGhostChangeStages() {
        JumperGhost jumperGhost = new JumperGhost(gameMap.getCell(2, 2), 1, 1, 1);
        jumperGhost.mobRound();
        assertEquals("orangeWall", jumperGhost.tileName);
        jumperGhost.mobRound();
        assertEquals("jumperGhost", jumperGhost.tileName);
        jumperGhost.mobRound();
        assertEquals("jumperGhost1", jumperGhost.tileName);
        jumperGhost.mobRound();
        assertEquals("jumperGhost2", jumperGhost.tileName);
    }
}