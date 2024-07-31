package com.example.c195.model;

import java.time.LocalDateTime;

/**
 * Represents a country in the scheduling system.
 */
public class Country {
    private int countryId;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructs a new Country with the specified details.
     *
     * @param countryId     the ID of the country
     * @param country       the name of the country
     * @param createDate    the creation date of the country record
     * @param createdBy     the user who created the country record
     * @param lastUpdate    the last update date of the country record
     * @param lastUpdatedBy the user who last updated the country record
     */
    public Country(int countryId, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Default constructor.
     */
    public Country() {}

    /**
     * Returns the country ID.
     *
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the country ID.
     *
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Returns the name of the country.
     *
     * @return the name of the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     *
     * @param country the name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns the creation date of the country record.
     *
     * @return the creation date of the country record
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the country record.
     *
     * @param createDate the creation date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the user who created the country record.
     *
     * @return the user who created the country record
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the country record.
     *
     * @param createdBy the user to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the last update date of the country record.
     *
     * @return the last update date of the country record
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last update date of the country record.
     *
     * @param lastUpdate the last update date to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Returns the user who last updated the country record.
     *
     * @return the user who last updated the country record
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the country record.
     *
     * @param lastUpdatedBy the user to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
