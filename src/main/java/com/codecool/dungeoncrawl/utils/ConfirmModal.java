package com.codecool.dungeoncrawl.utils;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmModal {
    public static String showModal(Button button, GameDatabaseManager dbmanager, Stage saveDialog) {
        button.setOnAction(actionEvent -> {
            final Stage dialog = new Stage();
            dialog.setTitle("Confirm?");
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Label("Would you like to overwrite the already existing state?"));
            Button acceptBtn = new Button("Yes");
            dialogVbox.getChildren().add(acceptBtn);
            Button cancelButton = new Button("No");
            dialogVbox.getChildren().add(cancelButton);
            dialogVbox.setPadding(new Insets(10));
            Scene dialogScene = new Scene(dialogVbox, 500, 150);
            dialog.setScene(dialogScene);
            dialog.showAndWait();
            cancelButton.setOnAction(actionEvent1 -> dialog.close());
            acceptBtn.setOnAction(actionEvent1 -> {
                //TODO update
                saveDialog.close();
                dialog.close();
            });
        });
        return null;
    }
}
