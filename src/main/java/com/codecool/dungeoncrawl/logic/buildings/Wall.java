package com.codecool.dungeoncrawl.logic.buildings;

import com.codecool.dungeoncrawl.logic.Cell;

public class Wall extends Obstacle{
    public Wall(Cell cell) {
        super(cell, ObstacleType.WALL);
        tileName = "wall";
    }

    @Override
    public String getTileName() {
        return tileName;
    }
}
