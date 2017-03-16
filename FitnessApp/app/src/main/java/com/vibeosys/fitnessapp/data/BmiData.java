package com.vibeosys.fitnessapp.data;

/**
 * Created by akshay on 16-03-2017.
 */
public class BmiData {

    private long bmiId;
    private double bmi;
    private double weight;
    private double height;
    private long dateTime;

    public BmiData(long bmiId, double bmi, double weight, double height, long dateTime) {
        this.bmiId = bmiId;
        this.bmi = bmi;
        this.weight = weight;
        this.height = height;
        this.dateTime = dateTime;
    }

    public long getBmiId() {
        return bmiId;
    }

    public void setBmiId(long bmiId) {
        this.bmiId = bmiId;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
