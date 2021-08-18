package com.codecool.dungeoncrawl.logic.buildings.locks;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.buildings.locks.Lock;
import com.codecool.dungeoncrawl.logic.buildings.locks.LockType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.offence.Weapon;
import com.codecool.dungeoncrawl.logic.items.offence.WeaponType;

public class Chest extends Lock {
    private Item content;

    public Chest(Cell cell, int level, String detail) {
        super(cell, level, detail, LockType.CHEST);
        content = getRandomItem();
    }

    @Override
    public String getTileName() {
        return "chest";
        /*if (passable) { return "open-chest"; }
        else { return "closed-chest"; }*/
    }

    private Item getRandomItem() {
        return new Weapon(cell, WeaponType.LONGSWORD);
    }
}
