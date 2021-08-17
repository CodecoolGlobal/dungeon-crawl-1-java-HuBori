package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;
import com.codecool.dungeoncrawl.logic.buildings.Wall;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private GameMap map = MapLoader.loadMap();
    private Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    private GraphicsContext context = canvas.getGraphicsContext2D();
    private Label healthLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Cell cell;
        switch (keyEvent.getCode()) {
            case UP:
                cell = map.getCell(map.getPlayer().getX(), map.getPlayer().getY() - 1);
                if (canGoThere(cell)) {
                    map.getPlayer().move(0, -1);
                }
                break;
            case DOWN:
                cell = map.getCell(map.getPlayer().getX(), map.getPlayer().getY() + 1);
                if (canGoThere(cell)) {
                    map.getPlayer().move(0, 1);
                }
                break;
            case LEFT:
                cell = map.getCell(map.getPlayer().getX() - 1, map.getPlayer().getY());
                if (canGoThere(cell)) {
                    map.getPlayer().move(-1, 0);
                }
                break;
            case RIGHT:
                cell = map.getCell(map.getPlayer().getX() + 1, map.getPlayer().getY());
                if (canGoThere(cell)) {
                    map.getPlayer().move(1, 0);
                }
                break;
        }
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                cell = map.getCell(x, y);
                if (cell.getActor() != null && !cell.getActor().getHasMoved()) {
                    cell.getActor().setHasMoved(true);
                    cell.getActor().monsterMove(map);
                }
            }
        }
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
    }

    private boolean canGoThere(Cell cell) { // TODO: fix it so player can move on items
        return cell.getType() != CellType.WALL && cell.getActor() == null;
    }
}
