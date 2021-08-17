package com.codecool.dungeoncrawl.logic.buildings.lock;

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

    public void setOpen(boolean open) {
        this.open = open;
    }
}
