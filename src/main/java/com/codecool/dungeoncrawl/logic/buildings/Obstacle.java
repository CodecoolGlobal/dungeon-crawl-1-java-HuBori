package com.codecool.dungeoncrawl.logic.buildings;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Obstacle implements Drawable {
    private ObstacleType type;
    protected Cell cell;
    protected boolean passable;
    protected static String tileName;

    public Obstacle(Cell cell, ObstacleType type) {
        this.cell = cell;
        this.type = type;
        this.passable = false;
    }
}
