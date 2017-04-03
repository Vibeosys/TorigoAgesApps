package com.vibeosys.fitnessapp.utils;

/**
 * Created by akshay on 16-03-2017.
 */
public class BmiCalculation {

    public static double calculateBMI(double height, double weight) {
        return weight / (height * height);
    }

}
