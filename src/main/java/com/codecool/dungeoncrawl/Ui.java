package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.items.key.Key;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;

public class Ui {
    private BorderPane borderPane;
    private Main main;
    private Label healthLabel = new Label();
    private Label maxHPLabel = new Label();
    private Label attackLabel = new Label();
    private Label defenseLabel = new Label();

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public Ui(Main main) {
        this.main = main;
        Button inGameMenu = makeMenuButton();
        Label characterName = formatCharacterName();
        HashMap<String, Label> statLabels = makeStatLabels();
        statLabels.put("characterName", characterName);

        FlowPane stats = formatStats(statLabels);
        addStatLabels(stats, statLabels);
        GridPane inv = formatInventory();
        GridPane log = formatLog();
        borderPane = placePanes(log, inv, stats);
    }

    public void formatPickUpButton(Button pickUp, GameMap map) {
        buttonFormatter(pickUp);
        pickUp.setOnAction(e -> { Item item = map.getPlayer().getCell().getItem();
            if (item != null) {
                item.pickUp(main.inventory.get(ItemType.KEY), map);
                main.logging.add("You picked up the legendary " + item.getDetail() + "!");
                main.refresh();
            } else {
                main.logging.add("Master, you are not strong enough to pick up the planet itself just yet.");
            }
            main.canvas.requestFocus();
        });
    }

    private Button makeMenuButton() {
        Button inGameMenu = new Button(" Menu ");
        buttonFormatter(inGameMenu);
        inGameMenu.setOnAction(e -> {
            //TODO popup window
        });
        return inGameMenu;
    }

    private Label formatCharacterName() {
        Label characterName = new Label(main.map.get(main.level).getPlayer().getName());
        characterName.setTextFill(Color.web("#FFB10A"));
        characterName.setStyle("-fx-font-weight: bold;" + "-fx-font: 30 ani");
        return characterName;
    }

    private HashMap<String, Label> makeStatLabels() {
        HashMap<String, Label> labels = new HashMap<>() {{
            put("health", new Label("Health: "));
            put("slash", new Label("/"));
            put("attack", new Label("Attack: "));
            put("defense", new Label("Defense: "));
        }};
        return labels;
    }

    private FlowPane formatStats(HashMap<String, Label> labels) {
        FlowPane stats = new FlowPane();
        textFormatter(labels.get("health"));
        textFormatter(labels.get("slash"));
        textFormatter(labels.get("attack"));
        textFormatter(labels.get("defense"));
        textFormatter(healthLabel);
        textFormatter(maxHPLabel);
        textFormatter(attackLabel);
        textFormatter(defenseLabel);

        stats.setMaxWidth(160);
        stats.setMinWidth(160);
        stats.setPadding(new Insets(15));

        return stats;
    }

    private void addStatLabels(FlowPane stats, HashMap<String, Label> labels) {
        stats.getChildren().add(main.pickUp);
        //stats.getChildren().add(new Label("                 "));
        //stats.getChildren().add(inGameMenu); more complicated than thought, remains for next sprint
        stats.getChildren().add(new Label("           "));
        stats.getChildren().add(labels.get("characterName"));
        stats.getChildren().add(main.healthBar);
        stats.getChildren().add(labels.get("health"));
        stats.getChildren().add(healthLabel);
        stats.getChildren().add(labels.get("slash"));
        stats.getChildren().add(maxHPLabel);
        stats.getChildren().add(labels.get("attack"));
        stats.getChildren().add(attackLabel);
        stats.getChildren().add(labels.get("defense"));
        stats.getChildren().add(defenseLabel);

        stats.setStyle("-fx-border-color: #9f9f9f;" +
                "-fx-border-width: 2px");
    }

    private GridPane formatInventory() {
        GridPane inv = new GridPane();
        inv.setPrefWidth(150);
        inv.setPadding(new Insets(15));
        textFormatter(main.invLabel);
        textFormatter(main.invItems);
        inv.add(main.invLabel, 0, 0);
        inv.add(main.invItems, 0, 1);

        inv.setStyle("-fx-border-color: #9f9f9f;");
        return inv;
    }

    private GridPane formatLog() {
        GridPane log = new GridPane();
        log.setMinHeight(110);
        log.setMaxHeight(110);
        textFormatter(main.logs);
        main.logs.setPadding(new Insets(0,0,0,150));
        log.add(main.logs, 0, 0);

        return log;
    }

    private BorderPane placePanes(GridPane log, GridPane inv, FlowPane stats) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(main.canvas);
        borderPane.setStyle("-fx-background-color: #4b3241");
        borderPane.setBottom(log);
        borderPane.setLeft(inv);
        borderPane.setRight(stats);
        return borderPane;
    }

    public void buttonFormatter(Button btn){
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

    private void textFormatter(Label txt){
        txt.setTextFill(Color.web("#9F9F9F"));
        txt.setStyle("-fx-font: 16 ani");
    }


    protected void updateStats() {
        Player player = main.map.get(main.level).getPlayer();
        main.healthBar.setProgress((double) player.getHealth() / (double) player.getMaxHealth());
        healthLabel.setText("" + player.getHealth());
        maxHPLabel.setText("" + player.getMaxHealth());
        attackLabel.setText("" + player.getAttack());
        defenseLabel.setText("" + player.getDefense());
    }
}
