package com.vibeosys.fitnessapp.data;

import java.util.ArrayList;

/**
 * Created by akshay on 14-03-2017.
 */
public class HistorySetsData {
    private long setId;
    private long dailySetId;
    private String setName;
    private String setDesc;
    private int repetition;
    private ArrayList<NoOfSetsData> repetitionData;
    private WorkoutCategory category;

    public HistorySetsData(long dailySetId, String setName, int repetition, long setId) {
        this.dailySetId = dailySetId;
        this.setName = setName;
        this.repetition = repetition;
        this.setId = setId;
    }

    public long getDailySetId() {
        return dailySetId;
    }

    public void setDailySetId(long dailySetId) {
        this.dailySetId = dailySetId;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSetDesc() {
        return setDesc;
    }

    public void setSetDesc(String setDesc) {
        this.setDesc = setDesc;
    }

    public ArrayList<NoOfSetsData> getRepetitionData() {
        return repetitionData;
    }

    public void setRepetitionData(ArrayList<NoOfSetsData> repetitionData) {
        this.repetitionData = repetitionData;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public WorkoutCategory getCategory() {
        return category;
    }

    public void setCategory(WorkoutCategory category) {
        this.category = category;
    }

    public long getSetId() {
        return setId;
    }

    public void setSetId(long setId) {
        this.setId = setId;
    }
}

