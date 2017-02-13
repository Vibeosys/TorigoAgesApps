package com.vibeosys.lawyerdiary.data;

/**
 * Created by Vibeosys software on 12-09-2016.
 */

/**
 * Case data module which contain the case information like id,name,etc
 */
public class CaseData {

    private long _Id;
    private String mCaseName;
    private String mClientName;
    private String mOppositionName;
    private String mCaseDate;
    private String mCourtLocation;
    private String mDescription;
    private String mStatus;
    private String mKeyPoints;

    public CaseData(long _Id, String mCaseName, String mDescription) {
        this._Id = _Id;
        this.mCaseName = mCaseName;
        this.mDescription = mDescription;
    }

    public CaseData(long _Id, String mCaseName, String mClientName, String mOppositionName,
                    String mCaseDate, String mCourtLocation, String mDescription,
                    String mStatus, String mKeyPoints) {
        this._Id = _Id;
        this.mCaseName = mCaseName;
        this.mClientName = mClientName;
        this.mOppositionName = mOppositionName;
        this.mCaseDate = mCaseDate;
        this.mCourtLocation = mCourtLocation;
        this.mDescription = mDescription;
        this.mStatus = mStatus;
        this.mKeyPoints = mKeyPoints;
    }

    public long get_Id() {
        return _Id;
    }

    public void set_Id(long _Id) {
        this._Id = _Id;
    }

    public String getCaseName() {
        return mCaseName;
    }

    public void setCaseName(String mCaseName) {
        this.mCaseName = mCaseName;
    }

    public String getClientName() {
        return mClientName;
    }

    public void setClientName(String mClientName) {
        this.mClientName = mClientName;
    }

    public String getOppositionName() {
        return mOppositionName;
    }

    public void setOppositionName(String mOppositionName) {
        this.mOppositionName = mOppositionName;
    }

    public String getCaseDate() {
        return mCaseDate;
    }

    public void setCaseDate(String mCaseDate) {
        this.mCaseDate = mCaseDate;
    }

    public String getCourtLocation() {
        return mCourtLocation;
    }

    public void setCourtLocation(String mCourtLocation) {
        this.mCourtLocation = mCourtLocation;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getKeyPoints() {
        return mKeyPoints;
    }

    public void setKeyPoints(String mKeyPoints) {
        this.mKeyPoints = mKeyPoints;
    }
}
