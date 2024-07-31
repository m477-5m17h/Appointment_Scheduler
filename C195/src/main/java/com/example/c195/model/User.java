package com.example.c195.model;

import java.time.LocalDateTime;

/**
 * Represents a user in the scheduling system.
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructs a new User with the specified details.
     *
     * @param userId        the ID of the user
     * @param userName      the name of the user
     * @param password      the password of the user
     * @param createDate    the creation date of the user record
     * @param createdBy     the user who created the user record
     * @param lastUpdate    the last update date of the user record
     * @param lastUpdatedBy the user who last updated the user record
     */
    public User(int userId, String userName, String password, LocalDateTime createDate,
                String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the name of the user.
     *
     * @return the name of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the name of the user.
     *
     * @param userName the name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the creation date of the user record.
     *
     * @return the creation date of the user record
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the user record.
     *
     * @param createDate the creation date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the user who created the user record.
     *
     * @return the user who created the user record
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the user record.
     *
     * @param createdBy the user to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the last update date of the user record.
     *
     * @return the last update date of the user record
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last update date of the user record.
     *
     * @param lastUpdate the last update date to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Returns the user who last updated the user record.
     *
     * @return the user who last updated the user record
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the user record.
     *
     * @param lastUpdatedBy the user to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
