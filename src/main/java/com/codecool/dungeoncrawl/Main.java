package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.gamecycle.GameCycle;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.utils.*;
import com.codecool.dungeoncrawl.actors.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
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
    public static Button saveButton;
    public static Button loadButton;
    public static Button importButton;
    public static Button exportButton;
    GameDatabaseManager dbManager;
    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        setupDbManager();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.setVgap(10.0);
        ui.setHgap(5.0);
        ui.setAlignment(Pos.BASELINE_CENTER);
        ui.add(pickUpButton = new Button("Pick up"), 0, 1);
        ui.add(saveButton = new Button("Save"), 0, 3);
        ui.add(loadButton = new Button("Load game"), 1, 3);
        ui.add(importButton = new Button("Import"), 1, 5);
        ui.add(exportButton = new Button("Export"), 0, 5);
        Style.setGrey(pickUpButton);
        Style.setGrey(loadButton);
        Style.setGrey(importButton);
        Style.setGrey(exportButton);
        Style.setReddish(saveButton);
        setButtonDisabledStatus(true);
        setActionListener(pickUpButton);
        setActionListener(saveButton);
        setActionListener(loadButton);
        setActionListener(importButton);
        setActionListener(exportButton);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        pickUpButton.setFocusTraversable(false);
        saveButton.setFocusTraversable(false);
        loadButton.setFocusTraversable(false);
        importButton.setFocusTraversable(false);
        exportButton.setFocusTraversable(false);
    }

    public static void setButtonDisabledStatus(boolean status) {
        pickUpButton.setDisable(status);
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        KeyCombination saveGame = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
        KeyCombination loadGame = new KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        } else if (saveGame.match(keyEvent)) {
            saveGame();
        } else if (loadGame.match(keyEvent)){
            loadGame();
        }
    }
    private void loadGame() {
        int player_Id = Modals.loadDialog(dbManager.getAllPlayers());
        if( player_Id != 0){
            currentMap = SerializationDeserialization.deSerializeMap(dbManager.getGameState(player_Id).getCurrentMap());
            gameCycle = new GameCycle(currentMap, this::refresh);
            start = false;
        };
    }

    private void saveGame() {
        boolean didWeGetAnswer = true;
        while(didWeGetAnswer){
            String playerName = Modals.inputDialog();
            if (dbManager.checkPlayerNameInDb(playerName)){
                if(Modals.confirmDialog()){
                    Player currentPlayer = currentMap.getPlayer();
                    currentPlayer.setPlayerName(playerName);
                    dbManager.updatePlayer(currentPlayer);
                    dbManager.updateGameState(SerializationDeserialization.serializeMap(currentMap),playerName);
                    didWeGetAnswer = false;
                }
            }else if(playerName == null){
                didWeGetAnswer = false;
            }
            else {
                Player currentPlayer = currentMap.getPlayer();
                currentPlayer.setPlayerName(playerName);
                dbManager.savePlayer(currentPlayer);
                dbManager.saveGameState(SerializationDeserialization.serializeMap(currentMap), dbManager.getPlayerModel(playerName));
                didWeGetAnswer = false;
            }
        }
    }

    private void importGameState() {
        FileChooserModal fileChooserModal = new FileChooserModal();
        String selectedFilePath = fileChooserModal.importMethod(primaryStage);
        if (selectedFilePath != null) {
            File file = new File(selectedFilePath);
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(file);
                byte[] fileContent = new byte[(int)file.length()];
                fin.read(fileContent);
                currentMap = SerializationDeserialization.deSerializeMap(fileContent);
                gameCycle = new GameCycle(currentMap, this::refresh);
                start = false;
            }
            catch (FileNotFoundException e) {
                System.out.println("File not found" + e);
            }
            catch (IOException ioe) {
                System.out.println("Exception while reading file " + ioe);
            }
            finally {
                try {
                    if (fin != null) {
                        fin.close();
                    }
                }
                catch (IOException ioe) {
                    System.out.println("Error while closing stream: " + ioe);
                }
            }
        }
    }

    private void exportGameState() {
        FileSaverModal fileSaverModal = new FileSaverModal();
        String selectedFilePath = fileSaverModal.exportMethod(primaryStage);
        byte[] fileContent = SerializationDeserialization.serializeMap(currentMap);
        if (selectedFilePath != null) {
            try {
                FileOutputStream fos = new FileOutputStream(selectedFilePath);
                fos.write(fileContent);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setActionListener(Button btn) {
        if (pickUpButton.equals(btn)) {
            pickUp(pickUpButton);
        } else if (saveButton.equals(btn)) {
            btn.setOnAction(actionEvent -> saveGame());
        } else if (loadButton.equals(btn)) {
            btn.setOnAction(actionEvent -> loadGame());
        } else if (importButton.equals(btn)) {
            btn.setOnAction(actionEvent -> importGameState());
        }else if (exportButton.equals(btn)){
            btn.setOnAction(actionEvent -> exportGameState());
        }
    }

    public void pickUp(Button button) {
        button.setOnAction(value -> {
            setButtonDisabledStatus(true);
            currentMap.getPlayer().addToInventory();
            currentMap.getPlayer().checkBonuses();
            currentMap.removeItem(currentMap.getPlayer().getCell().getItem());
        });
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        boolean ctrl = false;
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
                currentMap.getPlayer().move(1, 0);
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
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else if (cell.getTileName().matches("heart|infoBarShield|infoBarSword|infoBarCoins|infoBarBag"))
                    updateStatusBar(x, y, cell);
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + currentMap.getPlayer().getHealth());
        inventoryLabel.setText(currentMap.getPlayer().displayItems());
    }

    public void playerInteraction(int dx, int dy) {
        String levelCheck = currentMap.getPlayer().interact(dx, dy);
        refresh();
        if (Objects.equals(levelCheck, "nextLevel")) {
            if (map01.equals(currentMap)) {
                changeLevel(map01, map02, 20, 18);
            } else if (map02.equals(currentMap)) {
                changeLevel(map02, finish, 0, 0);
            } else if (map03.equals(currentMap)) {
                //TODO implement something map04 or something
//               changeLevel(map03, map01, 20, 18);
            }
        } else if (Objects.equals(levelCheck, "blueSwitch")) {
            Cell switchStance = currentMap.getPlayer().getCell().getNeighbor(dx, dy);
            if (switchStance.getTileName().equals("blueSwitchLeft")) {
                //switch change
                currentMap.getCell(19, 12).setType(CellType.BLUESWITCHRIGHT);
                currentMap.getCell(19, 7).setType(CellType.BLUESWITCHRIGHT);
                // floor change
                currentMap.getCell(20, 10).setType(CellType.FLOOR1);
                currentMap.getCell(21, 10).setType(CellType.FLOOR1);
                currentMap.getCell(20, 9).setType(CellType.FLOOR1);
                currentMap.getCell(21, 9).setType(CellType.FLOOR1);
                refresh();
            } else if (switchStance.getTileName().equals("blueSwitchRight")) {
                //switch change
                currentMap.getCell(19, 12).setType(CellType.BLUESWITCHLEFT);
                currentMap.getCell(19, 7).setType(CellType.BLUESWITCHLEFT);
                // floor change
                currentMap.getCell(20, 10).setType(CellType.BLUESWITCHLOCK);
                currentMap.getCell(21, 10).setType(CellType.BLUESWITCHLOCK);
                currentMap.getCell(20, 9).setType(CellType.BLUESWITCHLOCK);
                currentMap.getCell(21, 9).setType(CellType.BLUESWITCHLOCK);
                refresh();
            }
        }
    }

    public void changeLevel(GameMap previousMap, GameMap nextMap, int x, int y) {
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
        if (cell.getTileName().equals("heart")) {
            int playerHealth = currentMap.getPlayer().getHealth();
            if (playerHealth < 10) {
                currentMap.getCell(x + 1, y).setType(CellType.NUMBER0);
                currentMap.getCell(x + 2, y).setType(CellType.values()[playerHealth % 10]);
            } else {
                currentMap.getCell(x + 1, y).setType(CellType.NUMBER1);
                currentMap.getCell(x + 2, y).setType(CellType.NUMBER0);
            }
        } else if (cell.getTileName().equals("infoBarShield")) {
            int playerDefensePower = currentMap.getPlayer().getDefensePower() + currentMap.getPlayer().getBonusShield();
            currentMap.getCell(x + 1, y).setType(CellType.NUMBER0);
            currentMap.getCell(x + 2, y).setType(CellType.values()[playerDefensePower % 10]);
        } else if (cell.getTileName().equals("infoBarSword")) {
            int playerAttackPower = currentMap.getPlayer().getAttackPower() + currentMap.getPlayer().getBonusAttack();
            currentMap.getCell(x + 1, y).setType(CellType.NUMBER0);
            currentMap.getCell(x + 2, y).setType(CellType.values()[playerAttackPower % 10]);
        } else if (cell.getTileName().equals("infoBarCoins")) {
            currentMap.getCell(x + 1, y).setType(CellType.NUMBER0);
            currentMap.getCell(x + 2, y).setType(CellType.NUMBER0);
        } else if (cell.getTileName().equals("infoBarBag")) {
            int inventorySize = 6;
            ArrayList<Item> items = currentMap.getPlayer().getInventory();
            for (int i = 0; i < items.size(); i++) {
                currentMap.getCell(x + i + 2, y).setItem(items.get(i));
            }
            for (int i = items.size(); i < inventorySize; i++) {
                currentMap.getCell(x + i + 2, y).setItem(null);
            }
        }
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }


}
