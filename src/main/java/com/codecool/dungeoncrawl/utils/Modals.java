package com.codecool.dungeoncrawl.utils;

import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Modals {
//    public static String showModal(Button button, Player player, GameDatabaseManager dbmanager) {
//        button.setOnAction(actionEvent -> {
//            String result="";
//            final Stage dialog = new Stage();
//            dialog.setTitle("Save");
//            dialog.initModality(Modality.APPLICATION_MODAL);
//            VBox dialogVbox = new VBox(20);
//            dialogVbox.getChildren().add(new Label("Name:"));
//            TextField textField = new TextField();
//            dialogVbox.getChildren().add(textField);
//            Button saveButton =  new Button("Save");
//            dialogVbox.getChildren().add(saveButton);
//            Button cancelButton =  new Button("Cancel");
//            dialogVbox.getChildren().add(cancelButton);
//            dialogVbox.setPadding(new Insets(10));
//            Scene dialogScene = new Scene(dialogVbox, 300, 200);
//            dialog.setScene(dialogScene);
//            dialog.showAndWait();
//            cancelButton.setOnAction(actionEvent1 -> dialog.close());
//
//            saveButton.setOnAction(actionEvent1 -> {
//                //result = "valami";
////                if (dbmanager.checkPlayerNameInDb(textField.getText())) {
////                    ConfirmModal.showModal(saveButton, dbmanager, dialog);
////                } else {
////                    player.setPlayerName(textField.getText());
////                    dbmanager.savePlayer(player);
////                    dialog.close();
////                }
//            });
//        });
//        return null;
//    }

    public static String inputDialog() {
        TextInputDialog dialog = new TextInputDialog("player");
        dialog.setTitle("Save Game");
        dialog.setHeaderText("To save the game enter a 'NAME' for your player!");
        dialog.setContentText("Please enter your name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println(result.get());
            return result.get();
        }
        return null;
    }

    public static boolean confirmDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public static int loadDialog(List<PlayerModel> playerModels) {
        List<String> choices = new ArrayList<>();
        for (PlayerModel playerModel :
                playerModels) {
            choices.add(playerModel.getPlayerName());

        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Load Game");
        dialog.setHeaderText("Choose a Player Name to load the saved state");
        dialog.setContentText("Choose a name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            //TODO ide rakd csabi mert okos vagy :)
        }
        return 0;
    }
}
