package com.codecool.dungeoncrawl.logic.buildings;

public class Wall implements Obstacle{
    public Wall() {
    }

    @Override
    public String getTileName() {
        return "wall";
    }
}
