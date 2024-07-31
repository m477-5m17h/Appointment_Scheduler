package com.example.c195;

import com.example.c195.util.JDBC;
import com.example.c195.util.ScreenLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Set the primary stage in ScreenLoader
        ScreenLoader.setPrimaryStage(primaryStage);

        // Load the login form screen
        ScreenLoader.loadScreen("/com/example/c195/loginForm.fxml");

        primaryStage.setTitle("Scheduling Desktop Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Open the database connection
        JDBC.openConnection();

        // Launch the JavaFX application
        launch(args);

        // Close the database connection
        JDBC.closeConnection();
    }
}
