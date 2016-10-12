package com.vibeosys.lawyerdiary.data;

import java.util.Comparator;

/**
 * Created by akshay on 08-10-2016.
 */
public class NewsFeedData implements Comparable<NewsFeedData> {
    private long _id;
    private String heading;
    private String title;
    private String desc;
    private long date;

    public NewsFeedData(long _id, String heading, String title, String desc, long date) {
        this._id = _id;
        this.heading = heading;
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int compareTo(NewsFeedData another) {
        int result = (int) (another.date - this.date);
        if (result != 0) {
            return result;
        }
        return (int) (another.date - this.date);
    }

}
