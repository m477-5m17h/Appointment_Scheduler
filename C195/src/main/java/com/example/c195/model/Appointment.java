package com.example.c195.model;

import java.time.LocalDateTime;

/**
 * Represents an appointment in the scheduling system.
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;

    /**
     * Constructs a new Appointment.
     *
     * @param appointmentId the appointment ID
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of the appointment
     * @param contactId the contact ID associated with the appointment
     * @param type the type of the appointment
     * @param startDateTime the start date and time of the appointment
     * @param endDateTime the end date and time of the appointment
     * @param customerId the customer ID associated with the appointment
     * @param userId the user ID who scheduled the appointment
     */
    public Appointment(int appointmentId, String title, String description, String location, int contactId, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    // Getters and setters

    /**
     * Returns the appointment ID.
     *
     * @return the appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment ID.
     *
     * @param appointmentId the appointment ID to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Returns the title of the appointment.
     *
     * @return the title of the appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the appointment.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the appointment.
     *
     * @return the description of the appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the appointment.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the location of the appointment.
     *
     * @return the location of the appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the appointment.
     *
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the contact ID associated with the appointment.
     *
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact ID associated with the appointment.
     *
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Returns the type of the appointment.
     *
     * @return the type of the appointment
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the appointment.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the start date and time of the appointment.
     *
     * @return the start date and time of the appointment
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets the start date and time of the appointment.
     *
     * @param startDateTime the start date and time to set
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Returns the end date and time of the appointment.
     *
     * @return the end date and time of the appointment
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets the end date and time of the appointment.
     *
     * @param endDateTime the end date and time to set
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Returns the customer ID associated with the appointment.
     *
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID associated with the appointment.
     *
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the user ID who scheduled the appointment.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID who scheduled the appointment.
     *
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the contact name associated with the contact ID.
     *
     * @return the contact name
     */
    public String getContact() {
        // Fetch contact name based on contactId, assuming you have a method to do this
        return fetchContactNameById(contactId);
    }

    /**
     * Fetches the contact name based on the contact ID.
     *
     * @param contactId the contact ID
     * @return the contact name
     */
    private String fetchContactNameById(int contactId) {
        // Implement logic to fetch contact name based on contactId
        // For now, return a dummy value
        return "Contact Name";
    }
}
