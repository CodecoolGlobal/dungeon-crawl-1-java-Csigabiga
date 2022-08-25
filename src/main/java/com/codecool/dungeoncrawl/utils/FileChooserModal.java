package com.codecool.dungeoncrawl.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class FileChooserModal {
    public String importMethod(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import file");

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            return selectedFile.getPath();
        }
        return null;
    }
}
