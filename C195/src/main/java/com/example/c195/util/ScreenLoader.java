package com.example.c195.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenLoader {
    private static Stage primaryStage;
    private static FXMLLoader loader;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void loadScreen(String fxmlFile) {
        try {
            loader = new FXMLLoader(ScreenLoader.class.getResource(fxmlFile));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getController() {
        return loader.getController();
    }
}
