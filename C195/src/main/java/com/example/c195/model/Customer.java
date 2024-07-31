package com.example.c195.model;

import java.time.LocalDateTime;

/**
 * Represents a customer in the scheduling system.
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private String country;

    /**
     * Default constructor.
     */
    public Customer() {
    }

    /**
     * Constructs a new Customer with all fields.
     *
     * @param customerId     the customer ID
     * @param customerName   the name of the customer
     * @param address        the address of the customer
     * @param postalCode     the postal code of the customer
     * @param phone          the phone number of the customer
     * @param division       the division of the customer
     * @param createDate     the creation date of the customer record
     * @param createdBy      the user who created the customer record
     * @param lastUpdate     the last update date of the customer record
     * @param lastUpdatedBy  the user who last updated the customer record
     * @param divisionId     the division ID of the customer
     * @param country        the country of the customer
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.country = country;
    }

    /**
     * Constructs a new Customer without createDate, createdBy, lastUpdate, and lastUpdatedBy.
     *
     * @param customerId    the customer ID
     * @param customerName  the name of the customer
     * @param address       the address of the customer
     * @param postalCode    the postal code of the customer
     * @param phone         the phone number of the customer
     * @param division      the division of the customer
     * @param divisionId    the division ID of the customer
     * @param country       the country of the customer
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, String division, int divisionId, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.divisionId = divisionId;
        this.country = country;
    }

    /**
     * Returns the customer ID.
     *
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID.
     *
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the name of the customer.
     *
     * @return the name of the customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer.
     *
     * @param customerName the name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the address of the customer.
     *
     * @return the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the postal code of the customer.
     *
     * @return the postal code of the customer
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the customer.
     *
     * @param postalCode the postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Returns the phone number of the customer.
     *
     * @return the phone number of the customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the division of the customer.
     *
     * @return the division of the customer
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the division of the customer.
     *
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Returns the creation date of the customer record.
     *
     * @return the creation date of the customer record
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the customer record.
     *
     * @param createDate the creation date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the user who created the customer record.
     *
     * @return the user who created the customer record
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the customer record.
     *
     * @param createdBy the user to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the last update date of the customer record.
     *
     * @return the last update date of the customer record
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last update date of the customer record.
     *
     * @param lastUpdate the last update date to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Returns the user who last updated the customer record.
     *
     * @return the user who last updated the customer record
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the customer record.
     *
     * @param lastUpdatedBy the user to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Returns the division ID of the customer.
     *
     * @return the division ID of the customer
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division ID of the customer.
     *
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Returns the country of the customer.
     *
     * @return the country of the customer
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the customer.
     *
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
