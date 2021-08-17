package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Item {
    private ItemType type;
	private String detail;
	private Cell cell;

    public Item(Cell cell, ItemType type, String detail) {
        if (cell != null) {
            if (cell.getType() == CellType.FLOOR || cell.getType() == null) {
                this.cell = cell;
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
}
