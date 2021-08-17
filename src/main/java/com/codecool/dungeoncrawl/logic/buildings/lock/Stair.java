package com.codecool.dungeoncrawl.logic.buildings.lock;

import com.codecool.dungeoncrawl.logic.Cell;

public class Stair extends Lock {
    public Stair(Cell cell, int level, String detail) {
        super(cell, level, detail, LockType.STAIR);
    }

    @Override
    public String getTileName() {
        return "stair";
        /*if (passable) { return "open-stair"; }
        else { return "closed-stair"; }*/
    }
}
