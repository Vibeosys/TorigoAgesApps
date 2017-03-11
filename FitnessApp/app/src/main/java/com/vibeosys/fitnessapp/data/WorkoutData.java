package com.vibeosys.fitnessapp.data;

/**
 * Created by akshay on 09-03-2017.
 */
public class WorkoutData {

    private long wkId;
    private String wkName;
    private String wkDesc;

    public WorkoutData(long wkId, String wkName, String wkDesc) {
        this.wkId = wkId;
        this.wkName = wkName;
        this.wkDesc = wkDesc;
    }

    public long getWkId() {
        return wkId;
    }

    public void setWkId(long wkId) {
        this.wkId = wkId;
    }

    public String getWkName() {
        return wkName;
    }

    public void setWkName(String wkName) {
        this.wkName = wkName;
    }

    public String getWkDesc() {
        return wkDesc;
    }

    public void setWkDesc(String wkDesc) {
        this.wkDesc = wkDesc;
    }
}
