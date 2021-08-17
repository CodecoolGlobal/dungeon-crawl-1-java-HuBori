package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.buildings.lock.Lock;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    LOCK("door");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
