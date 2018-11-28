package com.bignerdranch.android.weatherprogrammer;

import java.io.Serializable;

public class Weather implements Serializable {

    private String date;
    private String week;
    private String status;
    private int icon;
    private String highersTemperature;
    private String lowestTemperature;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getHighersTemperature() {
        return highersTemperature;
    }

    public void setHighersTemperature(String highersTemperature) {
        this.highersTemperature = highersTemperature;
    }

    public String getLowestTemperature() {
        return lowestTemperature;
    }

    public void setLowestTemperature(String lowestTemperature) {
        this.lowestTemperature = lowestTemperature;
    }

    @Override
    public String toString() {
        return getDate() +", "+ getWeek() +", "+  getStatus();
    }
}
