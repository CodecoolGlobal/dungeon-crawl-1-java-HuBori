package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public abstract class Character implements Drawable {
    private Cell cell;
    private int health = 10;

    public Character(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
        attackIfCan();
    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    private void attackIfCan() {
        int[][] next = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int i = 0; i < 4; i++) {
            if (cell.getNeighbor(next[i][0], next[i][1]).getActor() instanceof Monster) {
                attack(cell.getNeighbor(next[i][0], next[i][1]).getActor());
                if (cell.getNeighbor(next[i][0], next[i][1]).getActor() instanceof Monster) {
                    cell.getNeighbor(next[i][0], next[i][1]).getActor().attack(this);
                }
            }
        }
    }

    public void attack(Character victim) {
        // TODO: implement it
    }
}
