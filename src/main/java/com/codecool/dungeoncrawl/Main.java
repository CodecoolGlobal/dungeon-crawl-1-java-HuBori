package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.defence.ArmorType;
import com.codecool.dungeoncrawl.logic.items.offence.WeaponType;

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
                    inventory.add(item);
                    switch (item.getType()) {
                        case ARMOR:
                            switch (item.getDetail()) {
                                case "shield": map.getPlayer().setDefense(ArmorType.SHIELD.getProtection()); break;
                                case "helmet": map.getPlayer().setDefense(ArmorType.HELMET.getProtection()); break;
                                default: throw new ClassCastException("No such type of armor handled");
                            }
                        case WEAPON:
                            switch (item.getDetail()) {
                                case "longsword": map.getPlayer().setDefense(WeaponType.LONGSWORD.getAttack()); break;
                                case "mace": map.getPlayer().setDefense(WeaponType.MACE.getAttack()); break;
                                case "dagger": map.getPlayer().setDefense(WeaponType.DAGGER.getAttack()); break;
                                case "shortsword": map.getPlayer().setDefense(WeaponType.SHORTSWORD.getAttack()); break;
                                case "lance": map.getPlayer().setDefense(WeaponType.LANCE.getAttack()); break;
                                default: throw new ClassCastException("No such type of weapon handled");
                            }
                        default: throw new ClassCastException("No such item type handles");
                    }
                }
                canvas.requestFocus();
            }
        };

        ui.add(btn, 0, 0);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(healthLabel, 1, 1);

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
                if (cell.getType() != CellType.WALL) {
                    map.getPlayer().tryMove(0, -1);
                }
                break;
            case DOWN:
                cell = map.getCell(map.getPlayer().getX(), map.getPlayer().getY() + 1);
                if (cell.getType() != CellType.WALL) {
                    map.getPlayer().tryMove(0, 1);
                }
                break;
            case LEFT:
                cell = map.getCell(map.getPlayer().getX() - 1, map.getPlayer().getY());
                if (cell.getType() != CellType.WALL) {
                    map.getPlayer().tryMove(-1, 0);
                }
                break;
            case RIGHT:
                cell = map.getCell(map.getPlayer().getX() + 1, map.getPlayer().getY());
                if (cell.getType() != CellType.WALL) {
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
}
