package com.codecool.dungeoncrawl.logic.buildings.locks.stairs;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.buildings.locks.Lock;
import com.codecool.dungeoncrawl.logic.buildings.locks.LockType;

public abstract class Stair extends Lock {
    public Stair(Cell cell, int level, String leadsTo) {
        super(cell, level, leadsTo, LockType.STAIR);
    }
}
