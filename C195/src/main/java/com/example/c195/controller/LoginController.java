package com.example.c195.controller;

import com.example.c195.util.JDBC;
import com.example.c195.util.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for handling the login functionality.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label locationLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     * Sets the location label, loads the resource bundle for localization, and sets the text for UI elements.
     */
    @FXML
    public void initialize() {
        ZoneId zoneId = ZoneId.systemDefault();
        locationLabel.setText(zoneId.toString());

        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("com/example/c195/Bundle", locale);

        // Set text based on locale
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        usernameField.setPromptText(rb.getString("username"));
        passwordField.setPromptText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
    }

    /**
     * Handles the action of logging in.
     * Validates the input fields, checks the credentials against the database, logs the login attempt, and navigates to the main menu upon success.
     *
     * @param event the action event
     */
    @FXML
    void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, rb.getString("error"), rb.getString("emptyFields"));
            logLoginAttempt(username, false);
            return;
        }

        try (Connection conn = JDBC.openConnection()) {
            String sql = "SELECT * FROM users WHERE user_name = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Successful login
                showAlert(Alert.AlertType.INFORMATION, rb.getString("success"), rb.getString("loginSuccess"));
                logLoginAttempt(username, true);
                checkForUpcomingAppointments();
                ScreenLoader.loadScreen("/com/example/c195/mainMenu.fxml");
            } else {
                // Failed login
                showAlert(Alert.AlertType.ERROR, rb.getString("error"), rb.getString("loginFailed"));
                logLoginAttempt(username, false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks for any upcoming appointments within the next 15 minutes and displays an alert.
     */
    private void checkForUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in15Minutes = now.plusMinutes(15);

        String sql = "SELECT Appointment_ID, Start FROM appointments WHERE Start BETWEEN ? AND ?";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, java.sql.Timestamp.valueOf(now));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(in15Minutes));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                LocalDateTime appointmentTime = rs.getTimestamp("Start").toLocalDateTime();
                String message = MessageFormat.format(rb.getString("appointmentDetails"), appointmentId, appointmentTime);
                showAlert(Alert.AlertType.INFORMATION, rb.getString("upcomingAppointment"), message);
            } else {
                showAlert(Alert.AlertType.INFORMATION, rb.getString("noUpcomingAppointments"),
                        rb.getString("noAppointmentsWithin15Minutes"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs the login attempt to a file.
     *
     * @param username the username
     * @param success  true if the login was successful, false otherwise
     */
    private void logLoginAttempt(String username, boolean success) {
        try (FileWriter writer = new FileWriter("login_activity.txt", true)) {
            writer.write(LocalDateTime.now() + " - Username: " + username + " - Success: " + success + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert with the specified type, title, and message.
     *
     * @param alertType the type of the alert
     * @param title     the title of the alert
     * @param message   the message of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
