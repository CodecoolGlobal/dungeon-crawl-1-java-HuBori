package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.items.defence.Armor;
import com.codecool.dungeoncrawl.logic.items.defence.ArmorType;
import com.codecool.dungeoncrawl.logic.items.offence.Weapon;
import com.codecool.dungeoncrawl.logic.items.offence.WeaponType;
import com.codecool.dungeoncrawl.logic.items.utility.Key;
import com.codecool.dungeoncrawl.logic.items.utility.KeyType;
import com.codecool.dungeoncrawl.logic.actors.enemies.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
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
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'g': // giddy
                            cell.setType(CellType.FLOOR);
                            new GiddyMonster(cell);
                            break;
                        case 'm': // marching
                            cell.setType(CellType.FLOOR);
                            new MarchingMonster(cell);
                            break;
                        case 's': // stationary
                            cell.setType(CellType.FLOOR);
                            new MuzzyMonster(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            Key key = new Key(cell, KeyType.BRONSE, 1, "only key");
                            cell.setItem(key);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            Weapon weapon = new Weapon(cell, WeaponType.LONGSWORD);
                            cell.setItem(weapon);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            Armor armor = new Armor(cell, ArmorType.SHIELD);
                            cell.setItem(armor);
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
