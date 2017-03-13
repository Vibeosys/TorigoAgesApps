package com.vibeosys.fitnessapp.data;

/**
 * Created by akshay on 13-03-2017.
 */
public class WorkoutCategory {

    private long categoryId;
    private String categoryName;
    private String categoryUnit;
    private String categoryMeasure;

    public WorkoutCategory(long categoryId, String categoryName, String categoryUnit, String categoryMeasure) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryUnit = categoryUnit;
        this.categoryMeasure = categoryMeasure;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryUnit() {
        return categoryUnit;
    }

    public void setCategoryUnit(String categoryUnit) {
        this.categoryUnit = categoryUnit;
    }

    public String getCategoryMeasure() {
        return categoryMeasure;
    }

    public void setCategoryMeasure(String categoryMeasure) {
        this.categoryMeasure = categoryMeasure;
    }
}
