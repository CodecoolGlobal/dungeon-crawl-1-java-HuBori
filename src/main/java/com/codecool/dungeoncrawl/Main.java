package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.buildings.lock.Lock;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.items.defence.ArmorType;
import com.codecool.dungeoncrawl.logic.items.offence.WeaponType;

import com.codecool.dungeoncrawl.logic.items.utility.Key;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private GameMap map = MapLoader.loadMap();
    private Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    private Button btn = new Button();
    private GraphicsContext context = canvas.getGraphicsContext2D();
    private Label healthLabel = new Label();
    private Label maxHPLabel = new Label();
    private Label attackLabel = new Label();
    private Label defenseLabel = new Label();
    private List<Item> inventory = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        btn.setText("Pick up");
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                Item item = map.getPlayer().getCell().getItem();
                if (item != null) {
                    item.pickUp(inventory, map);
                    System.out.println("You picked up a " + item.getDetail() + "!");
                    refresh();
                }
                canvas.requestFocus();
            }
        };

        ui.add(btn, 0, 0);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(healthLabel, 1, 1);
        ui.add(new Label("/"), 2, 1);
        ui.add(maxHPLabel, 3, 1);
        ui.add(new Label("Attack: "), 0, 2);
        ui.add(attackLabel, 1, 2);
        ui.add(new Label("Defense: "), 0, 3);
        ui.add(defenseLabel, 1, 3);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);
        refresh();
        //scene.setOnKeyPressed(this::onKeyPressed);
        btn.setOnAction(handler);

        primaryStage.setTitle("Dungeon Crawl");
        canvas.requestFocus();
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Cell cell;
        switch (keyEvent.getCode()) {
            case UP:
                cell = map.getCell(map.getPlayer().getX(), map.getPlayer().getY() - 1);
                if (cell.getType() != CellType.WALL && canGoThroughDoor(cell)) {
                    map.getPlayer().tryMove(0, -1);
                }
                break;
            case DOWN:
                cell = map.getCell(map.getPlayer().getX(), map.getPlayer().getY() + 1);
                if (cell.getType() != CellType.WALL && canGoThroughDoor(cell)) {
                    map.getPlayer().tryMove(0, 1);
                }
                break;
            case LEFT:
                cell = map.getCell(map.getPlayer().getX() - 1, map.getPlayer().getY());
                if (cell.getType() != CellType.WALL && canGoThroughDoor(cell)) {
                    map.getPlayer().tryMove(-1, 0);
                }
                break;
            case RIGHT:
                cell = map.getCell(map.getPlayer().getX() + 1, map.getPlayer().getY());
                if (cell.getType() != CellType.WALL && canGoThroughDoor(cell)) {
                    map.getPlayer().tryMove(1, 0);
                }
                break;
        }
        enemyMovement();
        refresh();
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    cell.getActor().setHasMoved(false);
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }

        healthLabel.setText("" + map.getPlayer().getHealth());
        maxHPLabel.setText("" + map.getPlayer().getMaxHealth());
        attackLabel.setText("" + map.getPlayer().getAttack());
        defenseLabel.setText("" + map.getPlayer().getDefense());

        if (map.getPlayer().getCell().getItem() == null) { // TODO: add items to cells
            // TODO: fix bug
            canvas.requestFocus();
        } else {
            System.out.println("I'm standing on an item! Let's pick it up!");
            btn.requestFocus();
        }
    }

    private void enemyMovement(){
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null && !cell.getActor().getHasMoved()) {
                    cell.getActor().setHasMoved(true);
                    cell.getActor().monsterMove(map);
                }
            }
        }
    }

    private boolean canGoThroughDoor(Cell cell){
        if (cell.getType() == CellType.LOCK) {
            Lock lock = cell.getLock();
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).getType() == ItemType.UTILITY && inventory.get(i).getDetail().matches("[a-z]+ key")) {
                    lock.attemptOpen((Key) inventory.get(i));
                    if (lock.isOpen()) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }
}
