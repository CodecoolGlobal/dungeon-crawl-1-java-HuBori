package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.enemies.MarchingMonster;
import com.codecool.dungeoncrawl.logic.actors.enemies.MuzzyMonster;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(1, 3, 3, CellType.FLOOR);

    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(gameMap.getCell(1, 0));

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(gameMap.getCell(1, 0));

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1));
        player.move(gameMap.getCell(1, 0));

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Player player = new Player(gameMap.getCell(1, 1));
        MuzzyMonster golem = new MuzzyMonster(gameMap.getCell(2, 1));
        player.move(gameMap.getCell(1, 0));

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, golem.getX());
        assertEquals(1, golem.getY());
        assertEquals(golem, gameMap.getCell(2, 1).getActor());
    }
}