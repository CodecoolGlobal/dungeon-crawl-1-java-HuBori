package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.ArrayList;
import java.util.Random;

public class GiddyMonster extends Monster { // moves randomly
    public GiddyMonster(Cell cell) {
        super(cell);
        maxHealth = 5;
        defense = 0;
        attack = 5;
    }

    @Override
    public String getTileName() {
        return "giddy";
    }

    @Override
    public void monsterMove(GameMap map) {
        Random rand = new Random();
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int randInt = rand.nextInt(4);
        while(true) {
            Cell cell = map.getCell(this.getX() + directions[randInt][0], this.getY() + directions[randInt][1]);
            if (cell.getType() != CellType.WALL && cell.getActor() == null) {
                this.tryMove(directions[randInt][0], directions[randInt][1], null);
                break;
            }
            randInt = rand.nextInt(3);
        }
    }
}
