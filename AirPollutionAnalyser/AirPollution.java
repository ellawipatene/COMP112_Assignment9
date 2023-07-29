// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 9
 * Name: Ella Wipatene
 * Username: wipateella 
 * ID: 300558005
 */

import ecs100.*;
import java.util.*;
import java.text.*;

/**
 * AirPollution contains information about the pollution level and weather in a
 * city at a particular hour and date.
 */

public class AirPollution {

    private String city;
    private String date;
    private int hour;
    private double pm;             // Particulate matter
    private double humidity;
    private double temperature;

    /** Construct a new AirPollution object */
    public AirPollution(String city, String date, int hour, double pm, double humidity, double temperature) {
        this.city = city;
        this.date = date;
        this.hour = hour;
        this.pm = pm;
        this.humidity = humidity;
        this.temperature = temperature;
    }

    /**
     * PM concentration difference between two pollution records
     */
    public double diffPM(AirPollution other) {
        return (Math.abs(other.getPM() - this.getPM()));
    }

    /** Get the city */
    public String getCity() {return this.city;}

    /** Get the date */
    public String getDate() {return this.date;}

    /** Get the hour */
    public int getHour() {return this.hour;}

    /** Get the PM concentration */
    public double getPM() {return this.pm;}

    /** Get the humidity */
    public double getHumidity() {return this.humidity;}

    /** Get the temperature */
    public double getTemperature() {return this.temperature;}

    /**
     * Returns a nicely formatted String describing the pollution info.
     */
    public String toString() {
        return this.city + " at " + this.hour + " on " + this.date
        + " Humidity: " + this.humidity + " temperature: " + this.temperature;
    }

}
