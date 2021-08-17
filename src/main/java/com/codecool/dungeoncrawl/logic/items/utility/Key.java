package com.codecool.dungeoncrawl.logic.items.utility;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

public class Key extends Item {
    private int level;
	private KeyType subType;
	private String detail;

	public Key(Cell cell, KeyType type, int level, String detail) {
		super(cell, ItemType.UTILITY, type.toString().toLowerCase() + " key");
		this.subType = type;
		this.level = level;
		this.detail = detail;
	}

	@Override
	public String getTileName() {
		return "key";
	}

	public KeyType getSubType() {
		return subType;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public String getDetail() {
		return detail;
	}
}
