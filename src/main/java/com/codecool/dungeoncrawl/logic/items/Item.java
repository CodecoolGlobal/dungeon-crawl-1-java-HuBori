package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Item {
    private ItemType type;
	private String detail;
	private Cell cell;

    public Item(Cell cell, ItemType type/*, String detail*/) {
        this.cell = cell;
        this.type = type;
        //this.detail = detail;
    }
}
