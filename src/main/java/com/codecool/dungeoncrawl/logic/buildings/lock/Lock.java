package com.codecool.dungeoncrawl.logic.buildings.lock;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.buildings.Obstacle;
import com.codecool.dungeoncrawl.logic.buildings.ObstacleType;
import com.codecool.dungeoncrawl.logic.items.utility.Key;

import static java.lang.Integer.parseInt;

public class Lock extends Obstacle {
    private int level;
    private String detail; // stair -> the level it leads to // other -> detailed explanation
    private LockType type;

    public Lock(Cell cell, int level, String detail, LockType type) {
        super(cell, ObstacleType.LOCK);
        cell.setLock(this);
        this.level = level;
        this.detail = detail;
        this.type = type;
        //setTileName();
    }

    public boolean isOpen() {
        return passable;
    }

    public LockType getType() {
        return type;
    }

    public void attemptOpen(Key key) { // TODO: for chest, something needs to be dropped when opened (later feature)
        if (key.getLevel() == level && key.getSubType() == type.getKey() && key.getDetail() == detail) {
            this.passable = true;
        }
    }

    /*private void setTileName() {
        String name = (passable) ? "open-" : "closed-";
        switch (type){
            case DOOR: tileName = name + "door";
            case CHEST: tileName = name + "chest";
            case STAIR: tileName = "stair-" + ((parseInt(detail) > level) ? "up" : "down");
            default: throw new ClassCastException("No such lock handled");
        }
    }*/

    @Override
    public String getTileName() {
        return "door";
    }
}
