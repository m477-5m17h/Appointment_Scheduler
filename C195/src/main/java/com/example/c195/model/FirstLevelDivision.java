package com.example.c195.model;

import java.time.LocalDateTime;

/**
 * Represents a first-level division (e.g., state, province) in the scheduling system.
 */
public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryId;

    /**
     * Constructs a new FirstLevelDivision with the specified details.
     *
     * @param divisionId    the ID of the division
     * @param division      the name of the division
     * @param createDate    the creation date of the division record
     * @param createdBy     the user who created the division record
     * @param lastUpdate    the last update date of the division record
     * @param lastUpdatedBy the user who last updated the division record
     * @param countryId     the ID of the country to which the division belongs
     */
    public FirstLevelDivision(int divisionId, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /**
     * Default constructor.
     */
    public FirstLevelDivision() {}

    /**
     * Returns the division ID.
     *
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division ID.
     *
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Returns the name of the division.
     *
     * @return the name of the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the division.
     *
     * @param division the name to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Returns the creation date of the division record.
     *
     * @return the creation date of the division record
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the division record.
     *
     * @param createDate the creation date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the user who created the division record.
     *
     * @return the user who created the division record
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the division record.
     *
     * @param createdBy the user to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the last update date of the division record.
     *
     * @return the last update date of the division record
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last update date of the division record.
     *
     * @param lastUpdate the last update date to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Returns the user who last updated the division record.
     *
     * @return the user who last updated the division record
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the division record.
     *
     * @param lastUpdatedBy the user to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Returns the ID of the country to which the division belongs.
     *
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the ID of the country to which the division belongs.
     *
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
