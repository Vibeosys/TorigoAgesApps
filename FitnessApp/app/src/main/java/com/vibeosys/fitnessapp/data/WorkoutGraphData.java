package com.vibeosys.fitnessapp.data;

/**
 * Created by akshay on 18-03-2017.
 */
public class WorkoutGraphData {

    private long wkId;
    private String name;
    private int repetition;

    public WorkoutGraphData(long wkId, String name) {
        this.wkId = wkId;
        this.name = name;
    }

    public long getWkId() {
        return wkId;
    }

    public void setWkId(long wkId) {
        this.wkId = wkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }
}
