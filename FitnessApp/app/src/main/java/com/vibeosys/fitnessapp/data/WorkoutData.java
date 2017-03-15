package com.vibeosys.fitnessapp.data;

/**
 * Created by akshay on 09-03-2017.
 */
public class WorkoutData {

    private long wkId;
    private long dwId;
    private String wkName;
    private String wkDesc;

    public WorkoutData(long wkId, String wkName, String wkDesc) {
        this.wkId = wkId;
        this.wkName = wkName;
        this.wkDesc = wkDesc;
    }

    public WorkoutData(long wkId, long dwId, String wkName) {
        this.wkId = wkId;
        this.dwId = dwId;
        this.wkName = wkName;
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

    public long getDwId() {
        return dwId;
    }

    public void setDwId(long dwId) {
        this.dwId = dwId;
    }
}
