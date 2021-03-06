package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Character;
import com.codecool.dungeoncrawl.logic.buildings.locks.Lock;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Cell implements Drawable {
    private CellType type;
    private Item item;
    private Character actor;
    private Lock lock;
    private GameMap gameMap;
    private int x, y;

    public Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Cell(CellType type) {
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Character actor) {
        this.actor = actor;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Character getActor() {
        return actor;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Item getItem() {
        return item;
    }

    public Lock getLock() {
        return lock;
    }

    public boolean isLock() {
        switch (type) {
            case DOOR: return true;
            case CHEST: return true;
            case STAIRDOWN: return true;
            case STAIRUP: return true;
            default: return false;
        }
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public void setCell(Cell cell) {
        type = cell.getType();
        item = cell.getItem();
        actor = cell.getActor();
        lock = cell.getLock();
        x = cell.getX();
        y = cell.getY();
    }
}
