package com.example.c195.controller;

import com.example.c195.util.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller class for handling the main menu actions.
 */
public class MainMenuController {

    /**
     * Handles the action of navigating to the manage appointments screen.
     *
     * @param event the action event
     */
    @FXML
    void onManageAppointments(ActionEvent event) {
        ScreenLoader.loadScreen("/com/example/c195/appointmentTableView.fxml");
    }

    /**
     * Handles the action of navigating to the manage customers screen.
     *
     * @param event the action event
     */
    @FXML
    void onManageCustomers(ActionEvent event) {
        ScreenLoader.loadScreen("/com/example/c195/customerTableView.fxml");
    }

    /**
     * Handles the action of navigating to the view reports screen.
     *
     * @param event the action event
     */
    @FXML
    void onViewReports(ActionEvent event) {
        ScreenLoader.loadScreen("/com/example/c195/reportView.fxml");
    }

    /**
     * Handles the action of exiting the application.
     *
     * @param event the action event
     */
    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);
    }
}
