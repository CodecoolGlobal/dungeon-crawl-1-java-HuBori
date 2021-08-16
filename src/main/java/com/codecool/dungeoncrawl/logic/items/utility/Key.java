package com.codecool.dungeoncrawl.logic.items.utility;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.ItemType;

public class Key {
    private int level;
	private KeyType type;
	private String detail;

	public Key(Cell cell, KeyType type, int level, String detail) {
		super();
		this.level = level;
		this.detail = detail;
	}
}
