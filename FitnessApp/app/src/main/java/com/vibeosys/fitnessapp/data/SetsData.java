package com.vibeosys.fitnessapp.data;

import java.io.Serializable;

/**
 * Created by akshay on 09-03-2017.
 */
public class SetsData implements Serializable {

    private long setId;
    private String setName;
    private String setDesc;

    public SetsData() {
    }

    public SetsData(long setId, String setName, String setDesc) {
        this.setId = setId;
        this.setName = setName;
        this.setDesc = setDesc;
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
}
