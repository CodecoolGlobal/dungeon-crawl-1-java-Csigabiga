package com.codecool.dungeoncrawl.utils;

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

    public static void setReddish(Button button) {
        button.setStyle("-fx-background-color:\n" +
                "        linear-gradient(#de9999, #f60101),\n" +
                "                linear-gradient(#e3c8c8 0%, #ffe5e5 20%, #6f4c4c 100%),\n" +
                "                linear-gradient(#791b1b 0%, #e18c8c 50%);\n" +
                "        -fx-background-radius: 8,7,6;\n" +
                "        -fx-background-insets: 0,1,2;\n" +
                "        -fx-text-fill: #000000;\n" +
                "        -fx-effect: dropshadow( two-pass-box , rgba(0,2,0,0.6) , 5, 0.0 , 0 , 1 );");
    }
}
