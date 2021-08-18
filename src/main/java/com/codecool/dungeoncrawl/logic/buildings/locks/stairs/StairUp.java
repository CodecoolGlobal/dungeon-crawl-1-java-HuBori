package com.codecool.dungeoncrawl.logic.buildings.locks.stairs;

import com.codecool.dungeoncrawl.logic.Cell;

public class StairUp extends Stair{
    private final int maxReachableLevel = 3;

    public StairUp(Cell cell, int level, String leadsTo) {
        super(cell, level, leadsTo);
        if (level + 1 > maxReachableLevel || String.valueOf(level + 1) != leadsTo) {
            throw new IndexOutOfBoundsException("You can't go to maps that do not exist!");
        }
    }

    @Override
    public String getTileName() {
        return "stair-up";
        /*if (passable) { return "open-stair"; }
        else { return "closed-stair"; }*/
    }
}
