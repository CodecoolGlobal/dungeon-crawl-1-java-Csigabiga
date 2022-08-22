package com.codecool.dungeoncrawl;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.gamecycle.GameCycle;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.utils.Style;
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
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {

    GameMap map01 = MapLoader.loadMap("/maps/map01.txt");
    GameMap map02 = MapLoader.loadMap("/maps/map02.txt");
    GameMap map03 = MapLoader.loadMap("/maps/map03.txt");
    GameMap finish = MapLoader.loadMap("/maps/finish.txt");
    GameMap currentMap = map01;
    GameCycle gameCycle = new GameCycle(currentMap, this::refresh);
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
        setActionListener();

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        pickUpButton.setFocusTraversable(false);
    }

    public static void setButtonDisabledStatus(boolean status) {
        pickUpButton.setDisable(status);
    }

    public void setActionListener() {
        pickUpButton.setOnAction(value -> {
            setButtonDisabledStatus(true);
            currentMap.getPlayer().addToInventory();
            currentMap.getPlayer().checkBonuses();
            currentMap.removeItem(currentMap.getPlayer().getCell().getItem());
        });
    }



    private void onKeyPressed(KeyEvent keyEvent) {
        if (!start) {
            start = true;
            gameCycle.start();
        }
        setButtonDisabledStatus(true);
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
                playerInteraction(0, -1);
                break;
            case S:
                playerInteraction(0, 1);

                break;
            case A:
                playerInteraction(-1, 0);

                break;
            case D:
                playerInteraction(1, 0);
                break;
            case L:
                if (map01.equals(currentMap)) {
                    gameCycle.stop();
                    currentMap = map02;
                    gameCycle = new GameCycle(currentMap, this::refresh);
                    refresh();
                } else if (map02.equals(currentMap)) {
                    currentMap = finish;
                    gameCycle = new GameCycle(currentMap, this::refresh);
                    refresh();
                } else if (finish.equals(currentMap)) {
                    currentMap = map01;
                    gameCycle = new GameCycle(currentMap, this::refresh);
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
                }
                else if (cell.getTileName().matches("heart|infoBarShield|infoBarSword|infoBarCoins|infoBarBag")) updateStatusBar(x, y, cell);
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + currentMap.getPlayer().getHealth());
        inventoryLabel.setText(currentMap.getPlayer().displayItems());
    }

    public void playerInteraction(int dx, int dy){
       String levelCheck =  currentMap.getPlayer().interact(dx, dy);
       refresh();
       if (Objects.equals(levelCheck, "nextLevel")){
           if (map01.equals(currentMap)) {
               changeLevel(map01, map02, 20, 18);
           } else if (map02.equals(currentMap)) {
               changeLevel(map02, finish, 0,0);
           } else if (map03.equals(currentMap)) {
               //TODO implement something map04 or something
//               changeLevel(map03, map01, 20, 18);
           }
        }else if (Objects.equals(levelCheck, "blueSwitch")){
           Cell switchStance = currentMap.getPlayer().getCell().getNeighbor(dx, dy);
           if (switchStance.getTileName().equals("blueSwitchLeft")){
               //switch change
               currentMap.getCell(19,12).setType(CellType.BLUESWITCHRIGHT);
               currentMap.getCell(19,7).setType(CellType.BLUESWITCHRIGHT);
               // floor change
               currentMap.getCell(20,10).setType(CellType.FLOOR1);
               currentMap.getCell(21,10).setType(CellType.FLOOR1);
               currentMap.getCell(20,9).setType(CellType.FLOOR1);
               currentMap.getCell(21,9).setType(CellType.FLOOR1);
               refresh();
           }else if(switchStance.getTileName().equals("blueSwitchRight")){
               //switch change
               currentMap.getCell(19,12).setType(CellType.BLUESWITCHLEFT);
               currentMap.getCell(19,7).setType(CellType.BLUESWITCHLEFT);
               // floor change
               currentMap.getCell(20,10).setType(CellType.BLUESWITCHLOCK);
               currentMap.getCell(21,10).setType(CellType.BLUESWITCHLOCK);
               currentMap.getCell(20,9).setType(CellType.BLUESWITCHLOCK);
               currentMap.getCell(21,9).setType(CellType.BLUESWITCHLOCK);
               refresh();
           }
       }
    }
    public void changeLevel(GameMap previousMap, GameMap nextMap, int x, int y){
        gameCycle.stop();
        currentMap = nextMap;
        start = false;
        gameCycle = new GameCycle(currentMap, this::refresh);
        nextMap.getCell(x, y).setActor(previousMap.getPlayer());
        nextMap.setPlayer(previousMap.getPlayer());
        nextMap.getPlayer().setCell(nextMap.getCell(x, y));
        refresh();
    }


    private void updateStatusBar(int x, int y, Cell cell) {
        Tiles.drawTile(context, cell, x, y);
        if(cell.getTileName().equals("heart")) {
            int playerHealth = currentMap.getPlayer().getHealth();
            if (playerHealth < 10) {
                currentMap.getCell(x + 1, y).setType(CellType.NUMBER0);
                currentMap.getCell(x + 2, y).setType(CellType.values()[playerHealth % 10]);
            } else {
                currentMap.getCell(x + 1, y).setType(CellType.NUMBER1);
                currentMap.getCell(x + 2, y).setType(CellType.NUMBER0);
            }
        }
        else if(cell.getTileName().equals("infoBarShield")){
            int playerDefensePower = currentMap.getPlayer().getDefensePower() + currentMap.getPlayer().getBonusShield();
            currentMap.getCell(x + 1, y).setType(CellType.NUMBER0);
            currentMap.getCell(x + 2, y).setType(CellType.values()[playerDefensePower % 10]);
        }
        else if (cell.getTileName().equals("infoBarSword")){
            int playerAttackPower = currentMap.getPlayer().getAttackPower() + currentMap.getPlayer().getBonusAttack();
            currentMap.getCell(x + 1, y).setType(CellType.NUMBER0);
            currentMap.getCell(x + 2, y).setType(CellType.values()[playerAttackPower % 10]);
        }
        else if(cell.getTileName().equals("infoBarCoins")){
            currentMap.getCell(x + 1, y).setType(CellType.NUMBER0);
            currentMap.getCell(x + 2, y).setType(CellType.NUMBER0);
        }
        else if (cell.getTileName().equals("infoBarBag")){
            int inventorySize = 6;
            ArrayList<Item> items = currentMap.getPlayer().getInventory();
            for (int i = 0 ; i < items.size(); i++){
                currentMap.getCell(x + i + 2, y).setItem(items.get(i));
            }
            for (int i = items.size(); i < inventorySize; i++){
                currentMap.getCell(x + i + 2, y).setItem(null);
            }
        }
    }
}
