package com.vibeosys.fitnessapp.data;

/**
 * Created by akshay on 11-03-2017.
 */
public class AppWorkoutData {

    private long workoutDate = 0;
    private long workoutId = 0;
    private long setId = 0;
    private long dailyWorkId = 0;

    public AppWorkoutData(long workoutDate, long workoutId, long setId, long dailyWorkId) {
        this.workoutDate = workoutDate;
        this.workoutId = workoutId;
        this.setId = setId;
        this.dailyWorkId = dailyWorkId;
    }

    public long getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(long workoutDate) {
        this.workoutDate = workoutDate;
    }

    public long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }

    public long getSetId() {
        return setId;
    }

    public void setSetId(long setId) {
        this.setId = setId;
    }

    public long getDailyWorkId() {
        return dailyWorkId;
    }

    public void setDailyWorkId(long dailyWorkId) {
        this.dailyWorkId = dailyWorkId;
    }
}
