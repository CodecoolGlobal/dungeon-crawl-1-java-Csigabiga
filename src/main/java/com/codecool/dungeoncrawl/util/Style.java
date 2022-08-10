package com.codecool.dungeoncrawl.util;

import javafx.scene.control.Button;

public class Style {
    public static void setGrey(Button button) {
        button.setStyle("-fx-background-color:\n" +
                "        linear-gradient(#f2f2f2, #d6d6d6),\n" +
                "                linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\n" +
                "                linear-gradient(#dddddd 0%, #f6f6f6 50%);\n" +
                "        -fx-background-radius: 8,7,6;\n" +
                "        -fx-background-insets: 0,1,2;\n" +
                "        -fx-text-fill: #651717;\n" +
                "        -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
    }
}
