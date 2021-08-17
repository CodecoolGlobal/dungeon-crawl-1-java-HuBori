package com.codecool.dungeoncrawl.logic.buildings.lock;

import com.codecool.dungeoncrawl.logic.items.utility.Key;

public class Lock {
    private int level;
    private String detail;
    private boolean open;
    private LockType type;

    public Lock(int level, String detail, LockType type) {
        this.level = level;
        this.detail = detail;
        this.type = type;
        this.open = false;
    }

    public int getLevel() {
        return level;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isOpen() {
        return open;
    }

    public LockType getType() {
        return type;
    }

    public void attemptOpen(Key key) {
        if (key.getLevel() == level && key.getSubType() == type.getKey() && key.getDetail() == detail) {
            this.open = true;
        }
    }
}
