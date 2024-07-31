package com.example.c195.controller;

import com.example.c195.util.JDBC;
import com.example.c195.util.ScreenLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controller class for adding an appointment.
 */
public class AddAppointmentController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<Integer> contactComboBox;

    @FXML
    private TextField typeField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<Integer> startHourComboBox;

    @FXML
    private ComboBox<Integer> startMinuteComboBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<Integer> endHourComboBox;

    @FXML
    private ComboBox<Integer> endMinuteComboBox;

    @FXML
    private ComboBox<Integer> customerIdComboBox;

    @FXML
    private ComboBox<Integer> userIdComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private ObservableList<Integer> contacts = FXCollections.observableArrayList(1, 2, 3); // Fetch from DB
    private ObservableList<Integer> customerIds = FXCollections.observableArrayList(1, 2, 3); // Fetch from DB
    private ObservableList<Integer> userIds = FXCollections.observableArrayList(1, 2, 3); // Fetch from DB

    /**
     * Initializes the controller class.
     * Sets up the combo boxes and populates them with initial values.
     *
     * The lambda expressions in the `startHourComboBox.setItems`, `startMinuteComboBox.setItems`, `endHourComboBox.setItems`,
     * and `endMinuteComboBox.setItems` methods are used to streamline the conversion of a range of integers into a list,
     * enhancing readability and reducing boilerplate code.
     */
    @FXML
    public void initialize() {
        contactComboBox.setItems(contacts);
        customerIdComboBox.setItems(customerIds);
        userIdComboBox.setItems(userIds);

        // Initialize hour and minute combo boxes
        // Lambda expression used to convert a range of integers to a list
        startHourComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList())));
        startMinuteComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList())));
        endHourComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList())));
        endMinuteComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList())));
    }

    /**
     * Handles the action of saving an appointment.
     * Validates input fields, checks for overlapping appointments and business hours, and saves the appointment to the database.
     *
     * @param event the action event
     */
    @FXML
    void onSave(ActionEvent event) {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        int contactId = contactComboBox.getSelectionModel().getSelectedItem();
        String type = typeField.getText();
        LocalDate startDate = startDatePicker.getValue();
        int startHour = startHourComboBox.getSelectionModel().getSelectedItem();
        int startMinute = startMinuteComboBox.getSelectionModel().getSelectedItem();
        LocalDate endDate = endDatePicker.getValue();
        int endHour = endHourComboBox.getSelectionModel().getSelectedItem();
        int endMinute = endMinuteComboBox.getSelectionModel().getSelectedItem();
        int customerId = customerIdComboBox.getSelectionModel().getSelectedItem();
        int userId = userIdComboBox.getSelectionModel().getSelectedItem();

        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() ||
                startDate == null || endDate == null || customerId == 0 || userId == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(startHour, startMinute));
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(endHour, endMinute));

        if (startDateTime.isAfter(endDateTime)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Start time must be before end time.");
            return;
        }

        // Validate business hours (8:00 a.m. to 10:00 p.m. ET)
        ZoneId easternTimeZone = ZoneId.of("America/New_York");
        ZonedDateTime startET = startDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(easternTimeZone);
        ZonedDateTime endET = endDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(easternTimeZone);

        if (startET.toLocalTime().isBefore(LocalTime.of(8, 0)) || endET.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            showAlert(Alert.AlertType.ERROR, "Error", "Appointments must be between 8:00 a.m. and 10:00 p.m. ET.");
            return;
        }

        if (isOverlappingAppointment(startDateTime, endDateTime, customerId)) {
            showAlert(Alert.AlertType.ERROR, "Error", "This appointment overlaps with another appointment for the same customer.");
            return;
        }

        try (Connection conn = JDBC.openConnection()) {
            String sql = "INSERT INTO appointments (Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setInt(4, contactId);
            ps.setString(5, type);
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(startDateTime));
            ps.setTimestamp(7, java.sql.Timestamp.valueOf(endDateTime));
            ps.setInt(8, customerId);
            ps.setInt(9, userId);

            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment added successfully!");
            ScreenLoader.loadScreen("/com/example/c195/appointmentTableView.fxml");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if an appointment overlaps with existing appointments for the same customer.
     *
     * @param startDateTime the start date and time of the appointment
     * @param endDateTime the end date and time of the appointment
     * @param customerId the customer ID
     * @return true if there is an overlap, false otherwise
     */
    private boolean isOverlappingAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId) {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND ((? >= Start AND ? <= End) OR (? >= Start AND ? <= End))";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(startDateTime));
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(startDateTime));
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(endDateTime));
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(endDateTime));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Handles the action of cancelling the operation and navigating back to the appointment table view.
     *
     * @param event the action event
     */
    @FXML
    void onCancel(ActionEvent event) {
        ScreenLoader.loadScreen("/com/example/c195/appointmentTableView.fxml");
    }

    /**
     * Displays an alert with the specified type, title, and message.
     *
     * @param alertType the type of the alert
     * @param title the title of the alert
     * @param message the message of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
