package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.MobTimer;
import com.codecool.dungeoncrawl.util.Style;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.util.Random;

public class Main extends Application {
    Random random = new Random();
    GameMap map01 = MapLoader.loadMap("/maps/map01.txt");
    GameMap map02 = MapLoader.loadMap("/maps/map02.txt");
    GameMap map03 = MapLoader.loadMap("/maps/map03.txt");
    GameMap currentMap = map03;
    Canvas canvas = new Canvas(
            currentMap.getWidth() * Tiles.TILE_WIDTH,
            currentMap.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    boolean start = false;
    Label inventoryLabel = new Label();
    public static Button pickUpButton;


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
        ui.add(pickUpButton = new Button("Pick up"), 0, 1);
        Style.setGrey(pickUpButton);
        ui.add(new Label("Inventory: "), 0, 3);
        ui.add(inventoryLabel, 0, 4);
        setButtonDisabledStatus(true);
        toDoOnAction();

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

    public static void setButtonDisabledStatus(boolean status) {
        pickUpButton.setDisable(status);
    }

    public void toDoOnAction() {
        pickUpButton.setOnAction(value -> {
            setButtonDisabledStatus(true);
            currentMap.getPlayer().addToInventory();
        });
    }


    public void mobMovement() {
        for (Skeleton skeleton:
             currentMap.getSkeletons()) {
            int randInt = random.nextInt(4);
            switch (randInt) {
                case 0:
                    skeleton.move(0, -1);
                    refresh();
                    break;
                case 1:
                    skeleton.move(0, 1);
                    refresh();
                    break;
                case 2:
                    skeleton.move(-1, 0);
                    refresh();
                    break;
                case 3:
                    skeleton.move(1, 0);
                    refresh();
                    break;
            }
        }
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        if (!start) {
            MobTimer mobtimer = new MobTimer(this::mobMovement);
            start = true;
            mobtimer.start();
        }
        switch (keyEvent.getCode()) {
            case UP:
                currentMap.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                currentMap.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                currentMap.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                currentMap.getPlayer().move(1,0);
                refresh();
                break;
            case W:
                currentMap.getPlayer().attack(0,-1);
                refresh();
                break;
            case S:
                currentMap.getPlayer().attack(0,1);
                refresh();
                break;
            case A:
                currentMap.getPlayer().attack(-1,0);

                refresh();
                break;
            case D:
                currentMap.getPlayer().attack(1,0);
                refresh();
                break;
            case L:
                if (map01.equals(currentMap)) {
                    currentMap = map02;
                    refresh();
                } else if (map02.equals(currentMap)) {
                    currentMap = map03;
                    refresh();
                } else if (map03.equals(currentMap)) {
                    currentMap = map01;
                    refresh();
                }
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < currentMap.getWidth(); x++) {
            for (int y = 0; y < currentMap.getHeight(); y++) {
                Cell cell = currentMap.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                }
                else if(cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + currentMap.getPlayer().getHealth());
        inventoryLabel.setText(currentMap.getPlayer().display());
    }
}
