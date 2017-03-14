package com.vibeosys.fitnessapp.data;

import java.util.ArrayList;

/**
 * Created by akshay on 14-03-2017.
 */
public class HistorySetsData {
    private long setId;
    private String setName;
    private String setDesc;
    private int repetition;
    ArrayList<NoOfSetsData> repetitionData;


    public HistorySetsData(long setId, String setName, int repetition) {
        this.setId = setId;
        this.setName = setName;
        this.repetition = repetition;
    }

    public long getSetId() {
        return setId;
    }

    public void setSetId(long setId) {
        this.setId = setId;
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
}

