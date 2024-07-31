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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controller class for managing the customer table view.
 */
public class CustomerTableViewController {

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> postalCodeColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TableColumn<Customer, String> divisionColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * Sets up the table columns and loads initial customer data.
     */
    @FXML
    public void initialize() {
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));

        loadCustomerData();
    }

    /**
     * Loads customer data from the database.
     */
    private void loadCustomerData() {
        try (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");

                // Fetch division name based on divisionId
                String division = getDivisionName(divisionId);

                Customer customer = new Customer(customerId, customerName, address, postalCode, phone, division, null, null, null, null, divisionId, "USA");
                customerList.add(customer);
            }

            customerTableView.setItems(customerList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the division name based on the division ID.
     * This method should return the division name from the database based on the division ID.
     *
     * @param divisionId the division ID
     * @return the division name
     */
    private String getDivisionName(int divisionId) {
        // Placeholder method for getting division name from the database
        return "Division Name";
    }

    /**
     * Handles the action of adding a new customer.
     *
     * @param event the action event
     */
    @FXML
    void onAdd(ActionEvent event) {
        ScreenLoader.loadScreen("/com/example/c195/addCustomer.fxml");
    }

    /**
     * Handles the action of updating an existing customer.
     *
     * @param event the action event
     */
    @FXML
    void onUpdate(ActionEvent event) {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a customer to update.");
            return;
        }

        ScreenLoader.loadScreen("/com/example/c195/updateCustomer.fxml");
        UpdateCustomerController controller = ScreenLoader.getController();
        controller.setCustomerData(selectedCustomer);
    }

    /**
     * Handles the action of deleting an existing customer and their associated appointments.
     *
     * @param event the action event
     */
    @FXML
    void onDelete(ActionEvent event) {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a customer to delete.");
            return;
        }

        try (Connection conn = JDBC.openConnection()) {
            String deleteAppointmentsSql = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement deleteAppointmentsPs = conn.prepareStatement(deleteAppointmentsSql);
            deleteAppointmentsPs.setInt(1, selectedCustomer.getCustomerId());
            deleteAppointmentsPs.executeUpdate();

            String deleteCustomerSql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement deleteCustomerPs = conn.prepareStatement(deleteCustomerSql);
            deleteCustomerPs.setInt(1, selectedCustomer.getCustomerId());
            deleteCustomerPs.executeUpdate();

            customerList.remove(selectedCustomer);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of navigating back to the main menu.
     *
     * @param event the action event
     */
    @FXML
    void onBack(ActionEvent event) {
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

    /**
     * Filters customers by country.
     *
     * @param country the country to filter customers by
     * @return the filtered list of customers
     */
    private ObservableList<Customer> filterCustomersByCountry(String country) {
        // Lambda expression used to filter customers by country
        // Justification: The lambda expression makes the code more concise and readable by directly applying the filter condition.
        return customerList.filtered(customer -> customer.getCountry().equals(country));
    }
}
