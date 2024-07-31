package com.example.c195.controller;

import com.example.c195.model.Appointment;
import com.example.c195.util.JDBC;
import com.example.c195.util.ScreenLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;

/**
 * Controller class for updating an appointment.
 */
public class UpdateAppointmentController {

    @FXML
    private TextField appointmentIdField;

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
    private Button updateButton;

    @FXML
    private Button cancelButton;

    private ObservableList<Integer> contacts = FXCollections.observableArrayList(1, 2, 3); // Fetch from DB
    private ObservableList<Integer> customerIds = FXCollections.observableArrayList(1, 2, 3); // Fetch from DB
    private ObservableList<Integer> userIds = FXCollections.observableArrayList(1, 2, 3); // Fetch from DB

    /**
     * Initializes the controller class.
     * Sets up the combo boxes and populates them with initial values.
     */
    @FXML
    public void initialize() {
        contactComboBox.setItems(contacts);
        customerIdComboBox.setItems(customerIds);
        userIdComboBox.setItems(userIds);

        // Populate hours and minutes using lambda expressions
        // Justification: The lambda expressions make the code more concise and readable by directly defining the population logic for hours and minutes.
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        IntStream.range(0, 24).forEach(hours::add); // Using lambda expression to populate hours
        startHourComboBox.setItems(hours);
        endHourComboBox.setItems(hours);

        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        IntStream.iterate(0, i -> i + 5).limit(12).forEach(minutes::add); // Using lambda expression to populate minutes
        startMinuteComboBox.setItems(minutes);
        endMinuteComboBox.setItems(minutes);
    }

    /**
     * Sets the appointment data to be edited.
     *
     * @param appointment the appointment to be edited
     */
    public void setAppointmentData(Appointment appointment) {
        appointmentIdField.setText(String.valueOf(appointment.getAppointmentId()));
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        contactComboBox.setValue(appointment.getContactId());
        typeField.setText(appointment.getType());
        startDatePicker.setValue(appointment.getStartDateTime().toLocalDate());
        startHourComboBox.setValue(appointment.getStartDateTime().getHour());
        startMinuteComboBox.setValue(appointment.getStartDateTime().getMinute());
        endDatePicker.setValue(appointment.getEndDateTime().toLocalDate());
        endHourComboBox.setValue(appointment.getEndDateTime().getHour());
        endMinuteComboBox.setValue(appointment.getEndDateTime().getMinute());
        customerIdComboBox.setValue(appointment.getCustomerId());
        userIdComboBox.setValue(appointment.getUserId());
    }

    /**
     * Handles the action of updating the appointment.
     *
     * @param event the action event
     */
    @FXML
    void onUpdate(ActionEvent event) {
        int appointmentId = Integer.parseInt(appointmentIdField.getText());
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        int contactId = contactComboBox.getSelectionModel().getSelectedItem();
        String type = typeField.getText();
        LocalDate startDate = startDatePicker.getValue();
        Integer startHour = startHourComboBox.getSelectionModel().getSelectedItem();
        Integer startMinute = startMinuteComboBox.getSelectionModel().getSelectedItem();
        LocalDate endDate = endDatePicker.getValue();
        Integer endHour = endHourComboBox.getSelectionModel().getSelectedItem();
        Integer endMinute = endMinuteComboBox.getSelectionModel().getSelectedItem();
        int customerId = customerIdComboBox.getSelectionModel().getSelectedItem();
        int userId = userIdComboBox.getSelectionModel().getSelectedItem();

        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || contactId == 0 || type.isEmpty() ||
                startDate == null || startHour == null || startMinute == null || endDate == null || endHour == null || endMinute == null || customerId == 0 || userId == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(startHour, startMinute));
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(endHour, endMinute));

        if (startDateTime.isAfter(endDateTime)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Start time must be before end time.");
            return;
        }

        // Check if appointment is within business hours (8:00 a.m. to 10:00 p.m. ET)
        ZoneId easternZoneId = ZoneId.of("America/New_York");
        ZonedDateTime startDateTimeET = startDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(easternZoneId);
        ZonedDateTime endDateTimeET = endDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(easternZoneId);

        LocalTime businessStart = LocalTime.of(8, 0);
        LocalTime businessEnd = LocalTime.of(22, 0);

        if (startDateTimeET.toLocalTime().isBefore(businessStart) || endDateTimeET.toLocalTime().isAfter(businessEnd)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Appointments must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
            return;
        }

        if (isOverlappingAppointment(startDateTime, endDateTime, customerId, appointmentId)) {
            showAlert(Alert.AlertType.ERROR, "Error", "This customer has an overlapping appointment.");
            return;
        }

        try (Connection conn = JDBC.openConnection()) {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
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
            ps.setInt(10, appointmentId);

            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment updated successfully!");
            ScreenLoader.loadScreen("/com/example/c195/appointmentTableView.fxml");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the given appointment time overlaps with existing appointments for the same customer.
     *
     * @param startDateTime the start date and time of the appointment
     * @param endDateTime   the end date and time of the appointment
     * @param customerId    the customer ID
     * @param appointmentId the appointment ID
     * @return true if there is an overlapping appointment, false otherwise
     */
    private boolean isOverlappingAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int appointmentId) {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND Appointment_ID != ? AND ((? >= Start AND ? <= End) OR (? >= Start AND ? <= End))";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, appointmentId);
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(startDateTime));
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(startDateTime));
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(endDateTime));
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(endDateTime));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Handles the action of cancelling the update and navigating back to the appointment table view.
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
