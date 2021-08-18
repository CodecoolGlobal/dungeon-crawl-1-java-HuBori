package com.codecool.dungeoncrawl.logic.buildings.locks.stairs;

import com.codecool.dungeoncrawl.logic.Cell;

import static com.codecool.dungeoncrawl.logic.buildings.locks.LockType.STAIR;
import static java.lang.Integer.parseInt;

public class StairDown extends Stair{
    public StairDown(Cell cell, int level, String leadsTo) {
        super(cell, level, leadsTo);
        if (level - 1 <= 0 || String.valueOf(level - 1) != leadsTo) {
            throw new IndexOutOfBoundsException("You can't go to maps that do not exist!");
        }
    }

    @Override
    public String getTileName() {
        return "stair-down";
        /*if (passable) { return "open-stair"; }
        else { return "closed-stair"; }*/
    }
}
