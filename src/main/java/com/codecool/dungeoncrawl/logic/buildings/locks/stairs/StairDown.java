package com.codecool.dungeoncrawl.logic.buildings.locks.stairs;

import com.codecool.dungeoncrawl.logic.Cell;

import static com.codecool.dungeoncrawl.logic.buildings.locks.LockType.STAIR;
import static java.lang.Integer.parseInt;

public class StairDown extends Stair{ // TODO: put it on map after climbing to next level (where the player stays)
    public StairDown(Cell cell, int level, String leadsTo) {
        super(cell, level, leadsTo);
    }

    @Override
    public String getTileName() {
        return "stair-down";
        /*if (passable) { return "open-stair"; }
        else { return "closed-stair"; }*/
    }
}
