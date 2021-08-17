package com.codecool.dungeoncrawl.logic.buildings.lock;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.utility.Key;

import static java.lang.Integer.parseInt;

public class Lock implements Drawable {
    protected Cell cell;
    protected int level;
    protected String detail; // stair -> the level it leads to // other -> detailed explanation
    protected boolean open;
    protected LockType type;

    public Lock(Cell cell, int level, String detail, LockType type) {
        this.cell = cell;
        this.cell.setLock(this);
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

    public void attemptOpen(Key key) { // TODO: for chest, something needs to be dropped when opened (later feature)
        if (key.getLevel() == level && key.getSubType() == type.getKey() && key.getDetail() == detail) {
            this.open = true;
        }
    }

    public String getTileName() {
        String name = (open) ? "open-" : "closed-";
        switch (type){
            case DOOR: return name + "door";
            case CHEST: return name + "chest";
            case STAIR: return "stair-" + ((parseInt(detail) > level) ? "up" : "down");
            default: throw new ClassCastException("No such lock handled");
        }
    }
}
