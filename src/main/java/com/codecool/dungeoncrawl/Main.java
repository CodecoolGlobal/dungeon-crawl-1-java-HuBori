package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Main extends Application {
    String[] mapFiles = new String[] {"level-1.txt", "level-2.txt", "level-3.txt"};
    private HashMap<Integer, GameMap> map = new HashMap<>() {{
        put(1, MapLoader.loadMap(mapFiles[0]));
        put(2, MapLoader.loadMap(mapFiles[1]));
        put(3, MapLoader.loadMap(mapFiles[2]));
    }};
    private Canvas canvas;
    private Button btn = new Button();
    private GraphicsContext context;
    private Label healthLabel = new Label();
    private Label maxHPLabel = new Label();
    private Label attackLabel = new Label();
    private Label defenseLabel = new Label();
    private Map<ItemType, ArrayList<Item>> inventory = new HashMap<>() {
        {
            put(ItemType.ARMOR, new ArrayList<>());
            put(ItemType.WEAPON, new ArrayList<>());
            put(ItemType.KEY, new ArrayList<>());
            put(ItemType.UTILITY, new ArrayList<>());
        }};
    private Label invLabel = new Label("Inventory");
    private Label invItems = new Label("");
    public static List<String> logging = new ArrayList<>();
    private Label logs = new Label("");
    private int level = 1;

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(map.get(level).getWidth() * Tiles.TILE_WIDTH, map.get(level).getHeight() * Tiles.TILE_WIDTH);
        context = canvas.getGraphicsContext2D();

        btn.setText("Pick up");
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                Item item = map.get(level).getPlayer().getCell().getItem();
                if (item != null) {
                    item.pickUp(inventory.get(item.getType()), map.get(level));
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
        btn.setOnAction(handler);

        primaryStage.setTitle("Dungeon Crawl");
        canvas.requestFocus();
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Cell cell;
        switch (keyEvent.getCode()) {
            case UP:
                cell = map.get(level).getCell(map.get(level).getPlayer().getX(), map.get(level).getPlayer().getY() - 1);
                if (cell.getType() != CellType.WALL) {
                    map.get(level).getPlayer().tryMove(0, -1, inventory.get(ItemType.KEY));
                }
                break;
            case DOWN:
                cell = map.get(level).getCell(map.get(level).getPlayer().getX(), map.get(level).getPlayer().getY() + 1);
                if (cell.getType() != CellType.WALL) {
                    map.get(level).getPlayer().tryMove(0, 1, inventory.get(ItemType.KEY));
                }
                break;
            case LEFT:
                cell = map.get(level).getCell(map.get(level).getPlayer().getX() - 1, map.get(level).getPlayer().getY());
                if (cell.getType() != CellType.WALL) {
                    map.get(level).getPlayer().tryMove(-1, 0, inventory.get(ItemType.KEY));
                }
                break;
            case RIGHT:
                cell = map.get(level).getCell(map.get(level).getPlayer().getX() + 1, map.get(level).getPlayer().getY());
                if (cell.getType() != CellType.WALL) {
                    map.get(level).getPlayer().tryMove(1, 0, inventory.get(ItemType.KEY));
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
        for (int x = 0; x < map.get(level).getWidth(); x++) {
            for (int y = 0; y < map.get(level).getHeight(); y++) {
                Cell cell = map.get(level).getCell(x, y);
                if (cell.getActor() != null) {
                    cell.getActor().setHasMoved(false);
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                }else{
                    System.out.println("\ncontent: " + cell.getType());
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }

        healthLabel.setText("" + map.get(level).getPlayer().getHealth());
        maxHPLabel.setText("" + map.get(level).getPlayer().getMaxHealth());
        attackLabel.setText("" + map.get(level).getPlayer().getAttack());
        defenseLabel.setText("" + map.get(level).getPlayer().getDefense());

        int inventorySize = inventory.get(ItemType.ARMOR).size();
        inventorySize += inventory.get(ItemType.WEAPON).size();
        inventorySize += inventory.get(ItemType.KEY).size();
        inventorySize += inventory.get(ItemType.UTILITY).size();

        String invTitle = String.format("Inventory (%d):", inventorySize);
        String tmp = "";
        Set<ItemType> setOfKeySet = inventory.keySet();
        for (ItemType key: setOfKeySet) {
            ArrayList value = inventory.get(key);
            tmp += String.format("\n\t%s (%d):\n", key.toString().toLowerCase(), value.size());
            for (int i = 0; i < value.size(); i++) {
                tmp += String.format("\t\t%s\n", ((ArrayList<Item>) value).get(i).getDetail());
            }
        }
        invLabel.setText(invTitle);
        invItems.setText(tmp);
        tmp = "";
        if(logging.size() > 5){
            logging.remove(0);
        }
        for (int i = 0; i < logging.size(); i++) {
            tmp += logging.get(i) + "\n";
        }
        logs.setText(tmp);

        if (map.get(level).getPlayer().getCell().getItem() == null) {
            canvas.requestFocus();
        } else {
            logging.add("I'm standing on an item! Let's pick it up!");
            btn.requestFocus();
        }
    }

    private void enemyMovement() {
        for (int x = 0; x < map.get(level).getWidth(); x++) {
            for (int y = 0; y < map.get(level).getHeight(); y++) {
                Cell cell = map.get(level).getCell(x, y);
                if (cell.getActor() != null && !cell.getActor().getHasMoved()) {
                    cell.getActor().setHasMoved(true);
                    cell.getActor().monsterMove(map.get(level));
                }
            }
        }
    }
}
