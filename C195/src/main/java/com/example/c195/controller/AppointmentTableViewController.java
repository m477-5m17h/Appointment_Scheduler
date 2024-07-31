package com.example.c195.controller;

import com.example.c195.model.Appointment;
import com.example.c195.util.JDBC;
import com.example.c195.util.ScreenLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Controller class for managing the appointment table view.
 */
public class AppointmentTableViewController {

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;

    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, String> contactColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;

    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;

    @FXML
    private TableColumn<Appointment, Integer> userIdColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> filterComboBox;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * Sets up the table columns, filter options, and loads initial data.
     *
     * The lambda expression in the `filterComboBox.setItems` method is used to
     * populate the combo box with filter options, making the code more readable and concise.
     */
    @FXML
    public void initialize() {
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        filterComboBox.setItems(FXCollections.observableArrayList("All", "Month", "Week"));
        filterComboBox.getSelectionModel().select("All");

        loadAppointmentData("All");
    }

    /**
     * Loads appointment data from the database based on the selected filter.
     *
     * @param filter the filter for the appointment data (e.g., All, Month, Week)
     */
    private void loadAppointmentData(String filter) {
        appointmentList.clear();

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
        if (filter.equals("Month")) {
            sql += " WHERE MONTH(Start) = MONTH(CURRENT_DATE()) AND YEAR(Start) = YEAR(CURRENT_DATE())";
        } else if (filter.equals("Week")) {
            sql += " WHERE WEEK(Start, 1) = WEEK(CURRENT_DATE(), 1) AND YEAR(Start) = YEAR(CURRENT_DATE())";
        }

        try (Connection conn = JDBC.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appointmentId, title, description, location, contactId, type, startDateTime, endDateTime, customerId, userId);
                appointmentList.add(appointment);
            }

            appointmentTableView.setItems(appointmentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of adding a new appointment.
     *
     * @param event the action event
     */
    @FXML
    void onAdd(ActionEvent event) {
        ScreenLoader.loadScreen("/com/example/c195/addAppointment.fxml");
    }

    /**
     * Handles the action of updating an existing appointment.
     *
     * @param event the action event
     */
    @FXML
    void onUpdate(ActionEvent event) {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an appointment to update.");
            return;
        }

        ScreenLoader.loadScreen("/com/example/c195/updateAppointment.fxml");
        UpdateAppointmentController controller = ScreenLoader.getController();
        controller.setAppointmentData(selectedAppointment);
    }

    /**
     * Handles the action of deleting an existing appointment.
     *
     * @param event the action event
     */
    @FXML
    void onDelete(ActionEvent event) {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an appointment to delete.");
            return;
        }

        try (Connection conn = JDBC.openConnection()) {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, selectedAppointment.getAppointmentId());
            ps.executeUpdate();

            appointmentList.remove(selectedAppointment);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment ID " + selectedAppointment.getAppointmentId() + " of type " + selectedAppointment.getType() + " deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of filtering the appointment data.
     *
     * @param event the action event
     */
    @FXML
    void onFilter(ActionEvent event) {
        String filter = filterComboBox.getSelectionModel().getSelectedItem();
        loadAppointmentData(filter);
    }

    /**
     * Handles the action of navigating back to the main menu.
     *
     * @param event the action event
     */
    @FXML
    void onBackToMainMenu(ActionEvent event) {
        ScreenLoader.loadScreen("/com/example/c195/mainMenu.fxml");
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
