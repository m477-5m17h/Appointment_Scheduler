package com.example.c195.model;

/**
 * Represents a contact in the scheduling system.
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    /**
     * Constructs a new Contact with the specified details.
     *
     * @param contactId   the ID of the contact
     * @param contactName the name of the contact
     * @param email       the email of the contact
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Default constructor.
     */
    public Contact() {}

    /**
     * Returns the contact ID.
     *
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact ID.
     *
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Returns the contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact name.
     *
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Returns the email of the contact.
     *
     * @return the email of the contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the contact.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
