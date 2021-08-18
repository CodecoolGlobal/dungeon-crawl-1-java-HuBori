package com.codecool.dungeoncrawl.logic.buildings.locks;

import com.codecool.dungeoncrawl.logic.items.key.KeyType;

public enum LockType {
    DOOR(KeyType.BRONZE),
    CHEST(KeyType.SILVER),
    STAIR(KeyType.GOLD);

    private KeyType key;

    LockType(KeyType key) {
        this.key = key;
    }

    public KeyType getKey() {
        return key;
    }
}
