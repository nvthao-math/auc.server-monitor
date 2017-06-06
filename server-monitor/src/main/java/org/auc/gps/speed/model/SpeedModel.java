/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.speed.model;

import java.io.Serializable;

/**
 *
 * @author thaonguyen
 */
public class SpeedModel implements Serializable {

    // data attributes: ID,Arc_id,SubClass,Length,Speed_Max,Speed,Year,Month,Day,Hour,DayOfWeek,DayNameOfWeek
    private long id; // id of user
    private long arcId; // id id of road's segment
    private String subClass; // road category
    private double length;
    private double speedMax; // max speed in each road
    private double speed; // current speed of user go through this road
    private String time;
    private String dayOfWeek;
    private String dayNameOfWeek;

    // constructor
    public SpeedModel() {
        super();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = Long.parseLong(id);
    }

    /**
     * @return the arcId
     */
    public long getArcId() {
        return arcId;
    }

    /**
     * @param arcId the arcId to set
     */
    public void setArcId(String arcId) {
        this.arcId = Long.parseLong(arcId);
    }

    /**
     * @return the subClass
     */
    public String getSubClass() {
        return subClass;
    }

    /**
     * @param subClass the subClass to set
     */
    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    /**
     * @return the length
     */
    public double getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(String length) {
        this.length = Double.parseDouble(length);
    }

    /**
     * @return the speedMax
     */
    public double getSpeedMax() {
        return speedMax;
    }

    /**
     * @param speedMax the speedMax to set
     */
    public void setSpeedMax(String speedMax) {
        this.speedMax = Double.parseDouble(speedMax);
    }

    /**
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(String speed) {
        this.speed = Double.parseDouble(speed);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return the dayOfWeek
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @param dayOfWeek the dayOfWeek to set
     */
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * @return the dayNameOfWeek
     */
    public String getDayNameOfWeek() {
        return dayNameOfWeek;
    }

    /**
     * @param dayNameOfWeek the dayNameOfWeek to set
     */
    public void setDayNameOfWeek(String dayNameOfWeek) {
        this.dayNameOfWeek = dayNameOfWeek;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

}
