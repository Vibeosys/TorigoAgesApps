package com.vibeosys.fitnessapp.data;

/**
 * Created by akshay on 11-03-2017.
 */
public class NoOfSetsData {

    private long repId;
    private long workId;
    private int noOfRep;
    private long dateTime;

    public NoOfSetsData(long workId, long dateTime) {
        this.repId = 0;
        this.workId = workId;
        this.noOfRep = 0;
        this.dateTime = dateTime;
    }

    public NoOfSetsData(long repId, long workId, int noOfRep, long dateTime) {
        this.repId = repId;
        this.workId = workId;
        this.noOfRep = noOfRep;
        this.dateTime = dateTime;
    }

    public long getRepId() {
        return repId;
    }

    public void setRepId(long repId) {
        this.repId = repId;
    }

    public long getWorkId() {
        return workId;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }

    public int getNoOfRep() {
        return noOfRep;
    }

    public void setNoOfRep(int noOfRep) {
        this.noOfRep = noOfRep;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
