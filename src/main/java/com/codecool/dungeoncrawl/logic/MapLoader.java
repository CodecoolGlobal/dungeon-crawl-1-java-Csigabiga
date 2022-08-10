package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.actors.Player;
import com.codecool.dungeoncrawl.actors.Skeleton;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class MapLoader  {

    private static void randomFloorTile(Cell cell){
        Random random = new Random();
        int randomFloor = random.nextInt(3);
        switch (randomFloor){
            case 0:
                cell.setType(CellType.FLOOR);
                break;
            case 1:
                cell.setType(CellType.FLOOR1);
                break;
            case 2:
                cell.setType(CellType.FLOOR2);
                break;
        }
    }

    public static GameMap loadMap(String mapPath) {
        InputStream is = MapLoader.class.getResourceAsStream(mapPath);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            randomFloorTile(cell);
                            break;
                        case 's':
                            randomFloorTile(cell);
                            new Skeleton(cell);
                            break;
                        case '@':
                            randomFloorTile(cell);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            map.setKey(new Key(cell));
                            break;
                        case 'x':
                            cell.setType(CellType.FLOOR);
                            map.setSword(new Sword(cell));
                            break;
                        case 'l':
                            cell.setType(CellType.STAIRDOWN);

                            break;
                        case 'L':
                            cell.setType(CellType.STAIRUP);

                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
