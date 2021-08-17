package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.buildings.lock.Lock;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    private Label invLabel = new Label("Inventory:" + "\n");
    private Label invItems = new Label("");
    public static List<String> logging = new ArrayList<>();
    private Label logs = new Label("");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        btn.setText("Pick up");
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                Item item = map.getPlayer().getCell().getItem();
                if (item != null) {
                    item.pickUp(inventory, map);
                    System.out.println("You picked up a(n) " + item.getDetail() + "!");
                    refresh();
                }
                canvas.requestFocus();
            }
        };

        GridPane stats = new GridPane();
        Label health = new Label("Health: ");
        Label slash = new Label("/");
        Label attack = new Label("Attack: ");
        Label defense = new Label("Defense: ");
        health.setTextFill(Color.web("#9F9F9F"));
        slash.setTextFill(Color.web("#9F9F9F"));
        attack.setTextFill(Color.web("#9F9F9F"));
        defense.setTextFill(Color.web("#9F9F9F"));
        healthLabel.setTextFill(Color.web("#9F9F9F"));
        maxHPLabel.setTextFill(Color.web("#9F9F9F"));
        attackLabel.setTextFill(Color.web("#9F9F9F"));
        defenseLabel.setTextFill(Color.web("#9F9F9F"));

        btn.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                btn.setStyle("-fx-background-color:#5f5f5f;" +
                        "-fx-background-radius: 0;");
            }
        });
        btn.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                btn.setStyle("-fx-background-color:#808080;" +
                        "-fx-background-radius: 0;");
            }
        });
        btn.setStyle("-fx-border-style: none;" +
                "-fx-background-color: #808080;" +
                "-fx-text-fill: #e1e1e1;" +
                "-fx-background-radius: 0;" +
                "-fx-border-radius: 0");

        stats.setPrefWidth(150);
        stats.setPadding(new Insets(15));

        stats.add(btn, 0, 0);
        stats.add(health, 0, 2);
        stats.add(healthLabel, 1, 2);
        stats.add(slash, 2, 2);
        stats.add(maxHPLabel, 3, 2);
        stats.add(attack, 0, 3);
        stats.add(attackLabel, 1, 3);
        stats.add(defense, 0, 4);
        stats.add(defenseLabel, 1, 4);

        stats.setStyle("-fx-border-color: #9f9f9f;" +
                "-fx-border-width: 2px");

        GridPane inv = new GridPane();
        inv.setPrefWidth(150);
        inv.setPadding(new Insets(15));
        invLabel.setStyle("-fx-text-fill: #e1e1e1");
        invItems.setStyle("-fx-text-fill: #e1e1e1");
        inv.add(invLabel, 0, 0);
        inv.add(invItems, 0, 1);

        inv.setStyle("-fx-border-color: #9f9f9f;");

        GridPane log = new GridPane();
        log.setHgap(200);
        log.setMinHeight(115);
        logs.setStyle("-fx-text-fill: #e1e1e1");
        log.add(logs, 0, 0);


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setStyle("-fx-background-color: #64465a");
        borderPane.setBottom(log);
        borderPane.setLeft(inv);
        borderPane.setRight(stats);

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
        logging.add("turn+1");
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

        String tmp = "";
        for (int i = 0; i < inventory.size(); i++) {
            tmp += inventory.get(i) + "\n";
        }
        invItems.setText(tmp);
        tmp = "";
        if(logging.size() > 5){
            logging.remove(0);
        }
        for (int i = 0; i < logging.size(); i++) {
            tmp += logging.get(i) + "\n";
        }
        logs.setText(tmp);

        if (map.getPlayer().getCell().getItem() == null) { // TODO: add items to cells
            // TODO: fix bug
            canvas.requestFocus();
        } else {
            logging.add("I'm standing on an item! Let's pick it up!");
            btn.requestFocus();
        }
    }

    private void enemyMovement() {
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
