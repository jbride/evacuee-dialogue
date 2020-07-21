package com.redhat.cajun.navy.evacuee;

import java.math.BigDecimal;

public class Incident {

    private String id;
    private BigDecimal lat;
    private BigDecimal lon;
    private int numberOfPeople;
    private boolean medicalNeeded;
    private long timestamp;
    private String victimName;
    private String victimPhoneNumber;
    private String status;
    private String sentimentData;

    public String getId() {
        return id;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Boolean isMedicalNeeded() {
        return medicalNeeded;
    }

    public void setMedicalNeeded(boolean medicalNeeded) {
        this.medicalNeeded = medicalNeeded;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public String getVictimPhoneNumber() {
        return victimPhoneNumber;
    }

    public void setVictimPhoneNumber(String victimPhoneNumber) {
        this.victimPhoneNumber = victimPhoneNumber;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long x) {
        this.timestamp = x;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSentimentData() {
        return sentimentData;
    }
    
    public void setSentimentData(String sentimentData) {
        this.sentimentData = sentimentData;
    }

}
