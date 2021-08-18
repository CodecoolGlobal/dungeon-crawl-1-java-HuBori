package com.codecool.dungeoncrawl.logic.buildings.locks.stairs;

import com.codecool.dungeoncrawl.logic.Cell;

public class StairUp extends Stair{
    public StairUp(Cell cell, int level, String leadsTo) {
        super(cell, level, leadsTo);
    }

    @Override
    public String getTileName() {
        return "stair-up";
        /*if (passable) { return "open-stair"; }
        else { return "closed-stair"; }*/
    }
}
