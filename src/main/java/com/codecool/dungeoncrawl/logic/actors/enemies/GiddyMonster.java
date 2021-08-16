package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

import java.util.Map;
import java.util.Random;

public class GiddyMonster extends Monster { // moves randomly
    public GiddyMonster(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "giddy";
    }

    public void monsterMove(GameMap map) {
        Random rand = new Random();
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int randInt = rand.nextInt(4);
        while(true) {
            Cell cell = map.getCell(this.getX() + directions[randInt][0], this.getY() + directions[randInt][1]);
            if (cell.getType() != CellType.WALL && cell.getActor() == null) {
                this.move(directions[randInt][0], directions[randInt][1]);
                break;
            }
            randInt = rand.nextInt(3);
        }
    }
}
