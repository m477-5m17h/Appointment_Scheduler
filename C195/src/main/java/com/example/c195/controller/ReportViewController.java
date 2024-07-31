package com.example.c195.controller;

import com.example.c195.util.JDBC;
import com.example.c195.util.ScreenLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controller class for generating and displaying various reports.
 */
public class ReportViewController {

    @FXML
    private ComboBox<String> reportComboBox;

    @FXML
    private TableView<ObservableList<String>> reportTableView;

    @FXML
    private TableColumn<ObservableList<String>, String> col1;

    @FXML
    private TableColumn<ObservableList<String>, String> col2;

    @FXML
    private TableColumn<ObservableList<String>, String> col3;

    @FXML
    private TableColumn<ObservableList<String>, String> col4;

    @FXML
    private TableColumn<ObservableList<String>, String> col5;

    @FXML
    private TableColumn<ObservableList<String>, String> col6;

    @FXML
    private TableColumn<ObservableList<String>, String> col7;

    private ObservableList<String> reportOptions = FXCollections.observableArrayList(
            "Appointments by Type and Month",
            "Schedule for Each Contact",
            "Total Appointments per Customer"
    );

    /**
     * Initializes the controller class.
     * Sets up the combo box with report options and configures the table columns.
     */
    @FXML
    public void initialize() {
        reportComboBox.setItems(reportOptions);

        // Lambda expressions used to set cell value factories for table columns
        col1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        col2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        col3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().size() > 2 ? data.getValue().get(2) : ""));
        col4.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().size() > 3 ? data.getValue().get(3) : ""));
        col5.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().size() > 4 ? data.getValue().get(4) : ""));
        col6.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().size() > 5 ? data.getValue().get(5) : ""));
        col7.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().size() > 6 ? data.getValue().get(6) : ""));
    }

    /**
     * Handles the action of generating a report based on the selected report option.
     *
     * @param event the action event
     */
    @FXML
    void onGenerateReport(ActionEvent event) {
        String selectedReport = reportComboBox.getSelectionModel().getSelectedItem();

        if (selectedReport != null) {
            switch (selectedReport) {
                case "Appointments by Type and Month":
                    generateAppointmentsByTypeAndMonthReport();
                    break;
                case "Schedule for Each Contact":
                    generateScheduleForEachContactReport();
                    break;
                case "Total Appointments per Customer":
                    generateTotalAppointmentsPerCustomerReport();
                    break;
            }
        }
    }

    /**
     * Generates the "Appointments by Type and Month" report.
     */
    private void generateAppointmentsByTypeAndMonthReport() {
        showColumns(3); // Show 3 columns for this report
        col1.setText("Type");
        col2.setText("Month");
        col3.setText("Total Appointments");

        String sql = "SELECT Type, MONTH(Start) AS Month, COUNT(*) AS Total FROM appointments GROUP BY Type, Month";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("Type"));
                row.add(String.valueOf(rs.getInt("Month")));
                row.add(String.valueOf(rs.getInt("Total")));
                data.add(row);
            }

            reportTableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the "Schedule for Each Contact" report.
     */
    private void generateScheduleForEachContactReport() {
        showColumns(7); // Show 7 columns for this report
        col1.setText("Appointment ID");
        col2.setText("Title");
        col3.setText("Type");
        col4.setText("Description");
        col5.setText("Start");
        col6.setText("End");
        col7.setText("Customer ID");

        String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments ORDER BY Contact_ID";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(String.valueOf(rs.getInt("Appointment_ID")));
                row.add(rs.getString("Title"));
                row.add(rs.getString("Type"));
                row.add(rs.getString("Description"));
                row.add(rs.getTimestamp("Start").toString());
                row.add(rs.getTimestamp("End").toString());
                row.add(String.valueOf(rs.getInt("Customer_ID")));
                data.add(row);
            }

            reportTableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the "Total Appointments per Customer" report.
     */
    private void generateTotalAppointmentsPerCustomerReport() {
        showColumns(2); // Show 2 columns for this report
        col1.setText("Customer ID");
        col2.setText("Total Appointments");

        String sql = "SELECT Customer_ID, COUNT(*) AS Total_Appointments FROM appointments GROUP BY Customer_ID";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(String.valueOf(rs.getInt("Customer_ID")));
                row.add(String.valueOf(rs.getInt("Total_Appointments")));
                data.add(row);
            }

            reportTableView.setItems(data);
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
     * Shows or hides columns based on the number of columns needed for the selected report.
     *
     * @param numberOfColumns the number of columns to show
     */
    private void showColumns(int numberOfColumns) {
        col1.setVisible(numberOfColumns > 0);
        col2.setVisible(numberOfColumns > 1);
        col3.setVisible(numberOfColumns > 2);
        col4.setVisible(numberOfColumns > 3);
        col5.setVisible(numberOfColumns > 4);
        col6.setVisible(numberOfColumns > 5);
        col7.setVisible(numberOfColumns > 6);
    }
}
