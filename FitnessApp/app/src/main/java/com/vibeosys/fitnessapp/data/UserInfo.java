package com.vibeosys.fitnessapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shrinivas on 14-03-2017.
 */
public class UserInfo implements Parcelable {
    private String mUserName, mUserEmailId, mUserPassword;
    private int mUserAge;
    private double mUserHeight, mUserWeight;

    public UserInfo(String mUserName, String mUserEmailId, String mUserPassword,
                    int mUserAge, double mUserHeight, double mUserWeight) {
        this.mUserName = mUserName;
        this.mUserEmailId = mUserEmailId;
        this.mUserPassword = mUserPassword;
        this.mUserAge = mUserAge;
        this.mUserHeight = mUserHeight;
        this.mUserWeight = mUserWeight;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getUserEmailId() {
        return mUserEmailId;
    }

    public void setUserEmailId(String mUserEmailId) {
        this.mUserEmailId = mUserEmailId;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String mUserPassword) {
        this.mUserPassword = mUserPassword;
    }

    public int getUserAge() {
        return mUserAge;
    }

    public void setUserAge(int mUserAge) {
        this.mUserAge = mUserAge;
    }

    public double getUserHeight() {
        return mUserHeight;
    }

    public void setUserHeight(double mUserHeight) {
        this.mUserHeight = mUserHeight;
    }

    public double getUserWeight() {
        return mUserWeight;
    }

    public void setUserWeight(double mUserWeight) {
        this.mUserWeight = mUserWeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUserName);
        dest.writeString(this.mUserEmailId);
        dest.writeString(this.mUserPassword);
        dest.writeInt(this.mUserAge);
        dest.writeDouble(this.mUserWeight);
        dest.writeDouble(this.mUserHeight);
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public UserInfo(Parcel parcel) {
        mUserName = parcel.readString();
        mUserEmailId = parcel.readString();
        mUserPassword = parcel.readString();
        mUserAge = parcel.readInt();
        mUserWeight = parcel.readDouble();
        mUserHeight = parcel.readDouble();

    }
}
