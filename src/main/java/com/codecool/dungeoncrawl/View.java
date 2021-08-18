package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.HashMap;

public class View {
    private final int width = 30;
    private final int height = 20;
    private HashMap<Integer, GameMap> dungeon;
    private int level;
    private GameMap scenery = new GameMap(level, width, height, CellType.EMPTY);

    public GameMap getScenery() {
        return scenery;
    }

    public View(HashMap<Integer, GameMap> dungeon, int level) {
        this.dungeon = dungeon;
        this.level = level;
        setScenery(level);
    }

    public void setScenery(int level) {
        GameMap map = dungeon.get(level);
        int x = (map.getPlayer().getX() - (width/2) < 0) ? 0 : map.getPlayer().getX() - (width/2);
        int y = (map.getPlayer().getY() - (height/2)) < 0 ? 0 : map.getPlayer().getY() - (height/2);
        x = (x < map.getWidth()) ? x : map.getWidth() - 1;
        y = (y < map.getHeight()) ? y : map.getHeight() - 1;
        for (int i = 0; i + x < map.getPlayer().getX() + (width/2) && i + x < map.getWidth(); i++) {
            for (int j = 0; j + y < map.getPlayer().getY() + (height/2) && j + y < map.getHeight(); j++) {
                scenery.getCell(i, j).setCell(map.getCell(x+i, y+j));
            }
        }
        this.scenery = scenery;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
