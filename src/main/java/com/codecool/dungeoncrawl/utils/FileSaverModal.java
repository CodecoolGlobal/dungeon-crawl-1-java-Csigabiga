package com.codecool.dungeoncrawl.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class FileSaverModal {
    public String exportMethod(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export file");

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        fileChooser.setInitialFileName("saveGameState");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON file", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        if (selectedFile != null) {
            return selectedFile.getPath();
        }
        return null;
    }
}
