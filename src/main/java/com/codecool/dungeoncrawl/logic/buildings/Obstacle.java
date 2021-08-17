package com.codecool.dungeoncrawl.logic.buildings;

import com.codecool.dungeoncrawl.logic.Drawable;

public interface Obstacle extends Drawable {
    @Override
    String getTileName();
}
