package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Main extends Application {
    private Stage menuStage = new Stage();
    String[] mapFiles = new String[] {"level-1.txt", "level-2.txt", "level-3.txt"};

    private HashMap<Integer, GameMap> map = new HashMap<>() {{
        put(1, MapLoader.loadMap(mapFiles[0]));
        put(2, MapLoader.loadMap(mapFiles[1]));
        put(3, MapLoader.loadMap(mapFiles[2]));
    }};
    private int level = 1;
    private Player player = map.get(level).getPlayer();
    private Inventory inventory = player.getInventory();

    private View view;
    private GameMap scenery;
    private Canvas canvas;
    private GraphicsContext context;
    private Button btn = new Button();
    private HashMap<String, Label> statLabels = new HashMap<> {{
        put("healthLabel", new Label());
        put("maxHPLabel", new Label());
        put("attackLabel", new Label());
        put("defenseLabel", new Label());
    }};
    private Button pickUp = new Button("Pick up");
    private ProgressBar healthBar = new ProgressBar(1);
    private Label invLabel = new Label("Inventory:\n");
    private Label invItems = new Label("");
    public static List<String> logging = new ArrayList<>();
    private Label logs = new Label("");

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        menuStage.setTitle("Java's lot");
        menu();

        view = new View(map.get(level), level);
        scenery = view.getScenery();
        canvas = new Canvas(scenery.getWidth() * Tiles.TILE_WIDTH, scenery.getHeight() * Tiles.TILE_WIDTH);
        context = canvas.getGraphicsContext2D();

        buttonFormatter(pickUp);
        pickUp.setOnAction(e -> {
            Item item = player.getCell().getItem();
            if (item != null) {
                item.pickUp(inventory.get(ItemType.KEY), map.get(level));
                logging.add("You picked up the legendary " + item.getDetail() + "!");
                refresh();
            } else {
                logging.add("Master, you are not strong enough to pick up the planet itself just yet.");
            }
            canvas.requestFocus();
        });


        Button inGameMenu = new Button(" Menu ");
        buttonFormatter(inGameMenu);
        inGameMenu.setOnAction(e -> {
            //TODO popup window
        });


/*
        btn.setText("Pick up");
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                Item item = player.getCell().getItem();
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

        Label characterName = new Label(player.getName());
        characterName.setTextFill(Color.web("#FFB10A"));
        characterName.setStyle("-fx-font-weight: bold;" +
                "-fx-font: 30 ani");

        FlowPane stats = new FlowPane();
        Label health = new Label("Health: ");
        Label slash = new Label("/");
        Label attack = new Label("Attack: ");
        Label defense = new Label("Defense: ");
        textFormatter(health);
        textFormatter(slash);
        textFormatter(attack);
        textFormatter(defense);
        statLabels.values().stream().map(s -> textFormatter(s)).collect(Collectors.toList()); // TODO: test it
        /* // refactored above
        textFormatter(healthLabel);
        textFormatter(maxHPLabel);
        textFormatter(attackLabel);
        textFormatter(defenseLabel);
         */


        stats.setMaxWidth(160);
        stats.setMinWidth(160);
        stats.setPadding(new Insets(15));

        stats.getChildren().add(pickUp);
        //stats.getChildren().add(new Label("                 "));
        //stats.getChildren().add(inGameMenu); more complicated than thought, remains for next sprint
        stats.getChildren().add(new Label("           "));
        stats.getChildren().add(characterName);
        stats.getChildren().add(healthBar);
        stats.getChildren().add(health);
        stats.getChildren().add(statLabels.get("healthLabel"));
        stats.getChildren().add(slash);
        stats.getChildren().add(statLabels.get("maxHPLabel"));
        stats.getChildren().add(attack);
        stats.getChildren().add(statLabels.get("attackLabel"));
        stats.getChildren().add(defense);
        stats.getChildren().add(statLabels.get("defenseLabel"));

        stats.setStyle("-fx-border-color: #9f9f9f;" +
                "-fx-border-width: 2px");

        GridPane inv = new GridPane();
        inv.setPrefWidth(150);
        inv.setPadding(new Insets(15));
        textFormatter(invLabel);
        textFormatter(invItems);
        inv.add(invLabel, 0, 0);
        inv.add(invItems, 0, 1);

        inv.setStyle("-fx-border-color: #9f9f9f;");

        GridPane log = new GridPane();
        log.setMinHeight(110);
        log.setMaxHeight(110);
        textFormatter(logs);
        logs.setPadding(new Insets(0,0,0,150));
        log.add(logs, 0, 0);


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setStyle("-fx-background-color: #4b3241");
        borderPane.setBottom(log);
        borderPane.setLeft(inv);
        borderPane.setRight(stats);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);
        refresh();

        primaryStage.setTitle("Java's lot");
        canvas.requestFocus();
        primaryStage.show();
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

    private void menu() {
        Button newGame = new Button("New Game");
        Button close = new Button("Exit");
        buttonFormatter(newGame);
        buttonFormatter(close);

        newGame.setOnAction(e -> {
            newGame();
        });

        close.setOnAction(e -> {
            menuStage.close();
            System.exit(0);
        });

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

        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(this::onKeyPressed);
        menuStage.setScene(scene);
        if (!menuStage.isShowing()) {
            menuStage.showAndWait();
        }
    }

    private void newGame() {
        Label menuText = new Label("Name your character");
        TextField input = new TextField();
        Button play = new Button("Start Game");
        Button mainMenu = new Button("Main Menu");
        buttonFormatter(play);
        buttonFormatter(mainMenu);

        play.setOnAction(e -> {
            player.setName(input.getText());
            menuStage.close();
        });

        mainMenu.setOnAction(e -> {
            menu();
        });
        menuText.setTextFill(Color.web("#AFAFAF"));
        menuText.setStyle("-fx-font-weight: bold");

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

        Scene scene = new Scene(pane);
        menuStage.setScene(scene);
    }

    private void textFormatter(Label txt){
        txt.setTextFill(Color.web("#9F9F9F"));
        txt.setStyle("-fx-font: 16 ani");
    }

    private void buttonFormatter(Button btn){
        //btn.setPadding(new Insets(0,10,0,10));
        btn.setStyle("-fx-border-style: none;" +
                "-fx-background-color: #808080;" +
                "-fx-text-fill: #e1e1e1;" +
                "-fx-background-radius: 0;" +
                "-fx-border-radius: 0;");

        btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                btn.setStyle("-fx-background-color:#5f5f5f;" +
                        "-fx-background-radius: 0;" +
                        "-fx-text-fill: #e1e1e1;");
            }
        });
        btn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                btn.setStyle("-fx-background-color:#808080;" +
                        "-fx-background-radius: 0;" +
                        "-fx-text-fill: #e1e1e1;");
            }
        });
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Cell cell;
        switch (keyEvent.getCode()) {
            case UP:
                cell = map.get(level).getCell(player.getX(), player.getY() - 1);
                if (cell.getType() != CellType.WALL) {
                    player.tryMove(0, -1, inventory.get(ItemType.KEY));
                }
                break;
            case DOWN:
                cell = map.get(level).getCell(player.getX(), player.getY() + 1);
                if (cell.getType() != CellType.WALL) {
                    player.tryMove(0, 1, inventory.get(ItemType.KEY));
                }
                break;
            case LEFT:
                cell = map.get(level).getCell(player.getX() - 1, player.getY());
                if (cell.getType() != CellType.WALL) {
                    player.tryMove(-1, 0, inventory.get(ItemType.KEY));
                }
                break;
            case RIGHT:
                cell = map.get(level).getCell(player.getX() + 1, player.getY());
                if (cell.getType() != CellType.WALL) {
                    player.tryMove(1, 0, inventory.get(ItemType.KEY));
                }
                break;
        }
        enemyMovement();
        refresh();
    }

    private void refresh() {
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

        healthBar.setProgress((double) player.getHealth() / (double) player.getMaxHealth());
        statLabels.put("healthLabel").setText("" + player.getHealth());
        statLabels.put("maxHPLabel").setText("" + player.getMaxHealth());
        statLabels.put("attackLabel").setText("" + player.getAttack());
        statLabels.put("defenseLabel").setText("" + player.getDefense());

        String invTitle = String.format("Inventory (%d):", inventory.getSize());
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

        if (player.getCell().getItem() == null) {
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
