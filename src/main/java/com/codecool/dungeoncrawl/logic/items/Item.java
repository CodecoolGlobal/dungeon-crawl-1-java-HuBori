package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.List;

public abstract class Item implements Drawable {
    private ItemType type;
	private String detail;
	protected Cell cell;

    public Item(Cell cell, ItemType type, String detail) { // TODO: show them on scene
        if (cell != null) {
            if (cell.getType() == CellType.FLOOR) {
                this.cell = cell;
                this.cell.setItem(this);
                this.type = type;
                this.detail = detail;
            }
        } else {
            throw new ArrayStoreException("Items should only be placed on the floor!");
        }
    }

    public ItemType getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }

    public void pickUp(List<Item> inventory, GameMap map){
        inventory.add(this);
        map.getCell(cell.getX(), cell.getY()).setItem(null);
    }

//    public abstract String getTileName();
}
