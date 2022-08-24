package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkeletonTest {


    GameMap gameMap = new GameMap(5, 5, CellType.FLOOR);

    @Test
    void skeletonCanMove() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 2), 1, 1, 1);
        skeleton.move(0, 1);
        assertEquals(gameMap.getCell(2, 3).getActor(), skeleton);
    }


    @Test
    void skeletonCanMoveOneHexPerRound() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 2), 1, 1, 1);
        skeleton.mobRound();
        assertNull(gameMap.getCell(2, 2).getActor());
        assertNull(gameMap.getCell(1, 1).getActor());
        assertNull(gameMap.getCell(1, 3).getActor());
        assertNull(gameMap.getCell(3, 1).getActor());
        assertNull(gameMap.getCell(3, 3).getActor());
        assertNull(gameMap.getCell(0, 0).getActor());
        assertNull(gameMap.getCell(0, 1).getActor());
        assertNull(gameMap.getCell(0, 2).getActor());
        assertNull(gameMap.getCell(0, 3).getActor());
        assertNull(gameMap.getCell(0, 4).getActor());
        assertNull(gameMap.getCell(1, 0).getActor());
        assertNull(gameMap.getCell(2, 0).getActor());
        assertNull(gameMap.getCell(3, 0).getActor());
        assertNull(gameMap.getCell(4, 0).getActor());
        assertNull(gameMap.getCell(4, 1).getActor());
        assertNull(gameMap.getCell(4, 2).getActor());
        assertNull(gameMap.getCell(4, 3).getActor());
        assertNull(gameMap.getCell(4, 4).getActor());
        assertNull(gameMap.getCell(3, 4).getActor());
        assertNull(gameMap.getCell(2, 4).getActor());
        assertNull(gameMap.getCell(1, 4).getActor());
    }


    @Test
    void skeletonCanNotMoveIntoWall() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 2), 1, 1, 1);
        gameMap.getCell(2, 1).setType(CellType.WALL);
        assertFalse(skeleton.move(0, -1));
    }


    @Test
    void skeletonCanNotMoveIntoActors() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 2), 1, 1, 1);
        new Player(gameMap.getCell(2, 3), 1, 1, 1);
        new ThreeMusketeers(gameMap.getCell(2, 1), 2, 2, 2);
        assertFalse(skeleton.move(0, -1));
        assertFalse(skeleton.move(0, 1));
    }


    @Test
    void skeletonDealDamage() {
        gameMap.getCell(0, 1).setType(CellType.WALL);
        gameMap.getCell(1, 0).setType(CellType.WALL);
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 1), 1, 5, 1);
        Player player = new Player(gameMap.getCell(2, 2), 10, 1, 1);
        skeleton.mobRound();
        assertEquals(6, player.getHealth());
    }


    @Test
    void skeletonTakeDamage() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 2), 10, 1, 1);
        Player player = new Player(gameMap.getCell(2, 2), 1, 5, 1);
        player.calculateAttack(skeleton);
        assertEquals(6, skeleton.getHealth());
    }

    @Test
    void skeletonChangeAppearance() {
        gameMap.getCell(0, 2).setType(CellType.WALL);
        gameMap.getCell(2, 0).setType(CellType.WALL);
        gameMap.getCell(2, 4).setType(CellType.WALL);
        gameMap.getCell(4, 2).setType(CellType.WALL);
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 2), 1, 1, 1);
        assertEquals(skeleton.tileName, "skeleton0");
        skeleton.mobRound();
        assertEquals(skeleton.tileName, "skeleton2");
        skeleton.mobRound();
        assertEquals(skeleton.tileName, "skeleton1");
        skeleton.mobRound();
    }


    @Test
    void skeletonDie() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 2), 1, 1, 1);
        Player player = new Player(gameMap.getCell(2, 2), 1, 5, 1);
        player.calculateAttack(skeleton);
        assertFalse(skeleton.isAlive());
    }
}