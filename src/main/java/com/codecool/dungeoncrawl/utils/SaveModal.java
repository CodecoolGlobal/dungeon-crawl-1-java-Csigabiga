package com.codecool.dungeoncrawl.utils;

import com.codecool.dungeoncrawl.actors.Player;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SaveModal {
    public static String showModal(Button button, Player player, GameDatabaseManager dbmanager) {
        button.setOnAction(actionEvent -> {
            String result="";
            final Stage dialog = new Stage();
            dialog.setTitle("Save");
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Label("Name:"));
            TextField textField = new TextField();
            dialogVbox.getChildren().add(textField);
            Button saveButton =  new Button("Save");
            dialogVbox.getChildren().add(saveButton);
            Button cancelButton =  new Button("Cancel");
            dialogVbox.getChildren().add(cancelButton);
            dialogVbox.setPadding(new Insets(10));
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.showAndWait();
            cancelButton.setOnAction(actionEvent1 -> dialog.close());
                
            saveButton.setOnAction(actionEvent1 -> {
                //result = "valami";
//                if (dbmanager.checkPlayerNameInDb(textField.getText())) {
//                    ConfirmModal.showModal(saveButton, dbmanager, dialog);
//                } else {
//                    player.setPlayerName(textField.getText());
//                    dbmanager.savePlayer(player);
//                    dialog.close();
//                }
            });
        });
        return null;
    }
}
