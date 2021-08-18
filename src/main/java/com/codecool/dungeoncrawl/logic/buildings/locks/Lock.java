package com.codecool.dungeoncrawl.logic.buildings.locks;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.buildings.Obstacle;
import com.codecool.dungeoncrawl.logic.buildings.ObstacleType;
import com.codecool.dungeoncrawl.logic.items.key.Key;

public abstract class Lock extends Obstacle {
    private int level;
    private String detail; // stair -> the level it leads to // other -> detailed explanation
    private LockType type;
    protected boolean passable;

    public Lock(Cell cell, int level, String detail, LockType type) {
        super(cell, ObstacleType.LOCK);
        cell.setLock(this);
        this.level = level;
        this.detail = detail;
        this.type = type;
        passable = false;
    }

    public boolean isOpen() {
        return passable;
    }

    public LockType getType() {
        return type;
    }

    public void attemptOpen(Key key) { // TODO: fix -> gets stuck when unable to open
        System.out.println("There is a lock '" + detail + "' here. Let's open it!");
        System.out.println("Let's try key '" + key.getDetail() + "' into it!");
        if (key.getLevel() == level && key.getSubType() == type.getKey() && key.getDetail() == detail) {
            this.passable = true;
            System.out.println("The lock is opened");
        } else {
            System.out.println("This key doesn't fit");
        }
    }

    @Override
    public String getTileName() {
        return "door";
    }
}
