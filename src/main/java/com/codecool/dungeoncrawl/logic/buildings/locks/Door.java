package com.codecool.dungeoncrawl.logic.buildings.locks;

import com.codecool.dungeoncrawl.logic.Cell;

public class Door extends Lock {
    public Door(Cell cell, int level, String detail) {
        super(cell, level, detail, LockType.DOOR);
    }

    @Override
    public String getTileName() {
        return "door";
        /*if (passable) { return "open-door"; }
        else { return "closed-door"; }*/
    }
}
