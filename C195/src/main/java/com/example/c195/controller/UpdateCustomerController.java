package com.example.c195.controller;

import com.example.c195.model.Customer;
import com.example.c195.util.JDBC;
import com.example.c195.util.ScreenLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Controller class for updating a customer.
 */
public class UpdateCustomerController {

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> divisionComboBox;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    private ObservableList<String> countries = FXCollections.observableArrayList("U.S", "Canada", "UK");

    /**
     * Initializes the controller class.
     * Sets up the country and division combo boxes and populates them with initial values.
     */
    @FXML
    public void initialize() {
        countryComboBox.setItems(countries);

        // Lambda expression used to handle the selection of a country and populate the division combo box
        // Justification: The lambda expression simplifies the code and improves readability by directly associating an action with the event of selecting a country.
        countryComboBox.setOnAction(event -> {
            String selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            ObservableList<String> divisions = FXCollections.observableArrayList();

            switch (selectedCountry) {
                case "U.S":
                    divisions.addAll("New York", "California", "Texas");
                    break;
                case "Canada":
                    divisions.addAll("Ontario", "Quebec", "British Columbia");
                    break;
                case "UK":
                    divisions.addAll("England", "Scotland", "Wales");
                    break;
            }

            divisionComboBox.setItems(divisions);
        });
    }

    /**
     * Sets the customer data to be edited.
     *
     * @param customer the customer to be edited
     */
    public void setCustomerData(Customer customer) {
        customerIdField.setText(String.valueOf(customer.getCustomerId()));
        customerNameField.setText(customer.getCustomerName());
        addressField.setText(customer.getAddress());
        postalCodeField.setText(customer.getPostalCode());
        phoneField.setText(customer.getPhone());
        countryComboBox.setValue(customer.getCountry());
        divisionComboBox.setValue(customer.getDivision());
    }

    /**
     * Handles the action of updating the customer.
     *
     * @param event the action event
     */
    @FXML
    void onUpdate(ActionEvent event) {
        int customerId = Integer.parseInt(customerIdField.getText());
        String customerName = customerNameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();
        String country = countryComboBox.getSelectionModel().getSelectedItem();
        String division = divisionComboBox.getSelectionModel().getSelectedItem();

        if (customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || country == null || division == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        try (Connection conn = JDBC.openConnection()) {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, getDivisionId(division));
            ps.setInt(6, customerId);

            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer updated successfully!");
            ScreenLoader.loadScreen("/com/example/c195/customerTableView.fxml");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of cancelling the update and navigating back to the customer table view.
     *
     * @param event the action event
     */
    @FXML
    void onCancel(ActionEvent event) {
        ScreenLoader.loadScreen("/com/example/c195/customerTableView.fxml");
    }

    /**
     * Retrieves the division ID for the given division name.
     * This method should return the Division_ID from the database based on the division name.
     * For simplicity, returning a dummy value. Implement the actual database query to fetch Division_ID.
     *
     * @param division the division name
     * @return the division ID
     */
    private int getDivisionId(String division) {
        // Placeholder method for getting division ID from the database
        return 1;
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
