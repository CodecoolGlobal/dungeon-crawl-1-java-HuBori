package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Main extends Application {
    private Stage menuStage = new Stage();
    String[] mapFiles = new String[] {"level-1.txt", "level-2.txt", "level-3.txt"};
    protected HashMap<Integer, GameMap> map = new HashMap<>() {{
        put(1, MapLoader.loadMap(mapFiles[0]));
        put(2, MapLoader.loadMap(mapFiles[1]));
        put(3, MapLoader.loadMap(mapFiles[2]));
    }};
    private View view;
    private GameMap scenery;
    protected Canvas canvas;
    private GraphicsContext context;
    private Button btn = new Button();
    protected Button pickUp = new Button("Pick up");
    protected ProgressBar healthBar = new ProgressBar(1);
    protected Label invLabel = new Label("Inventory:\n");
    protected Map<ItemType, ArrayList<Item>> inventory = new HashMap<>() {
        {
            put(ItemType.ARMOR, new ArrayList<>());
            put(ItemType.WEAPON, new ArrayList<>());
            put(ItemType.KEY, new ArrayList<>());
            put(ItemType.UTILITY, new ArrayList<>());
        }};
    protected Label invItems = new Label("");
    public static List<String> logging = new ArrayList<>();
    protected Label logs = new Label("");
    protected int level = 1;
    private Ui ui = new Ui(this);

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setMapDependentVariables() {
        view = new View(map.get(level), level);
        scenery = view.getScenery();
        canvas = new Canvas(scenery.getWidth() * Tiles.TILE_WIDTH, scenery.getHeight() * Tiles.TILE_WIDTH);
        context = canvas.getGraphicsContext2D();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        menuStage.setTitle("Java's lot");
        menu();
        setMapDependentVariables();
        ui.formatPickUpButton(pickUp, map.get(level));
        Scene scene = new Scene(ui.getBorderPane());
        primaryStage.setScene(scene);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);
        refresh();

        primaryStage.setTitle("Java's lot");
        canvas.requestFocus();
        primaryStage.show();
/*
        btn.setText("Pick up");
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                Item item = map.get(level).getPlayer().getCell().getItem();
                if (item != null) {
                    item.pickUp(inventory, map);
                    System.out.println("You picked up the legendary " + item.getDetail() + "!");
                    item.pickUp(inventory.get(item.getType()), map.get(level));
                    System.out.println("You picked up a(n) " + item.getDetail() + "!");
                    refresh();
                }
                canvas.requestFocus();
            }
        };
*/
    }

    private Canvas menuBg(){
        Canvas canvas = new Canvas(
                10 * Tiles.TILE_WIDTH,
                10 * Tiles.TILE_WIDTH);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Cell cell = new Cell(CellType.FLOOR);
                Tiles.drawTile(context, cell, x, y);
            }
        }
        return canvas;
    }

    private AnchorPane setMenuAnchor(Button newGame, Button close) {
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(menuBg());
        AnchorPane.setTopAnchor(newGame, 120.0);
        AnchorPane.setLeftAnchor(newGame, 0.0);
        AnchorPane.setRightAnchor(newGame, 0.0);
        AnchorPane.setTopAnchor(close, 170.0);
        AnchorPane.setLeftAnchor(close, 0.0);
        AnchorPane.setRightAnchor(close, 0.0);
        pane.getChildren().add(newGame);
        pane.getChildren().add(close);
        newGame.setAlignment(Pos.CENTER);
        close.setAlignment(Pos.CENTER);
        return pane;
    }

    private void setMenuScene(AnchorPane pane) {
        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(this::onKeyPressed);
        menuStage.setScene(scene);
        if (!menuStage.isShowing()) {
            menuStage.showAndWait();
        }
    }

    private void menu() {
        Button newGame = new Button("New Game");
        Button close = new Button("Exit");
        ui.buttonFormatter(newGame);
        ui.buttonFormatter(close);

        newGame.setOnAction(e -> {
            newGame();
        });

        close.setOnAction(e -> {
            menuStage.close();
            System.exit(0);
        });

        AnchorPane pane = setMenuAnchor(newGame, close);
        setMenuScene(pane);
    }

    private AnchorPane setGameAnchor(Label menuText, TextField input, Button play, Button mainMenu) {
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(menuBg());
        AnchorPane.setTopAnchor(menuText, 50.0);
        AnchorPane.setLeftAnchor(menuText, 0.0);
        AnchorPane.setRightAnchor(menuText, 0.0);
        AnchorPane.setTopAnchor(input, 100.0);
        AnchorPane.setLeftAnchor(input, 0.0);
        AnchorPane.setRightAnchor(input, 0.0);
        AnchorPane.setTopAnchor(play, 150.0);
        AnchorPane.setLeftAnchor(play, 0.0);
        AnchorPane.setRightAnchor(play, 0.0);
        AnchorPane.setTopAnchor(mainMenu, 200.0);
        AnchorPane.setLeftAnchor(mainMenu, 0.0);
        AnchorPane.setRightAnchor(mainMenu, 0.0);
        pane.getChildren().add(menuText);
        pane.getChildren().add(input);
        pane.getChildren().add(play);
        pane.getChildren().add(mainMenu);
        menuText.setAlignment(Pos.CENTER);
        input.setAlignment(Pos.CENTER);
        play.setAlignment(Pos.CENTER);
        mainMenu.setAlignment(Pos.CENTER);
        return pane;
    }

    private void newGame() {
        Label menuText = new Label("Name your character");
        TextField input = new TextField();
        Button play = new Button("Start Game");
        Button mainMenu = new Button("Main Menu");
        ui.buttonFormatter(play);
        ui.buttonFormatter(mainMenu);

        play.setOnAction(e -> {
            map.get(level).getPlayer().setName(input.getText());
            menuStage.close();
        });

        mainMenu.setOnAction(e -> {
            menu();
        });
        menuText.setTextFill(Color.web("#AFAFAF"));
        menuText.setStyle("-fx-font-weight: bold");

        AnchorPane pane = setGameAnchor(menuText, input, play, mainMenu);

        Scene scene = new Scene(pane);
        menuStage.setScene(scene);
    }

    private void handleKey(int modX, int modY) {
        Cell cell = map.get(level).getCell(map.get(level).getPlayer().getX() + modX, map.get(level).getPlayer().getY() + modY);
        if (cell.getType() != CellType.WALL) {
            map.get(level).getPlayer().tryMove(modX, modY, inventory.get(ItemType.KEY));
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                handleKey(0, -1);
                break;
            case DOWN:
                handleKey(0, 1);
                break;
            case LEFT:
                handleKey(-1, 0);
                break;
            case RIGHT:
                handleKey(1, 0);
                break;
        }
        enemyMovement();
        refresh();
    }

    private void updateScenery() {
        view.setScenery();
        scenery = view.getScenery();

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < scenery.getWidth(); x++) {
            for (int y = 0; y < scenery.getHeight(); y++) {
                Cell cell = scenery.getCell(x, y);
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
    }

    private void updateInventory() {
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
    }

    private void updateLog() {
        String tmp = "";
        if(logging.size() > 5){
            logging.remove(0);
        }
        for (String log : logging) {
            tmp += log + "\n";
        }
        logs.setText(tmp);

        if (map.get(level).getPlayer().getCell().getItem() == null) {
            canvas.requestFocus();
        } else {
            logging.add("I'm standing on an item! Let's pick it up!");
            btn.requestFocus();
        }
    }

    protected void refresh() {
        updateScenery();
        ui.updateStats();
        updateInventory();
        updateLog();

        /*if (map.get(level).getPlayer().getCell().getItem() == null) {
            canvas.requestFocus();
        } else {
            logging.add("Do you want to pick up this marvelous artifact master?");
            pickUp.requestFocus();
            //btn.requestFocus();
        }

        String tmp = ""; //making note for the commit, merge mostly done, fixing rest in ide then commit
        for (Item item : inventory) {
            tmp += item.getDetail() + "\n";*/
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
