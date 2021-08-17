package com.codecool.dungeoncrawl.logic.buildings.lock;

import com.codecool.dungeoncrawl.logic.items.key.KeyType;

public enum LockType {
    DOOR(KeyType.BRONSE),
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
