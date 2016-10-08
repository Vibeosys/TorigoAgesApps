package com.vibeosys.lawyerdiary.data;

/**
 * Created by akshay on 08-10-2016.
 */
public class NewsFeedData {
    private long _id;
    private String title;
    private String desc;

    public NewsFeedData(long _id, String title, String desc) {
        this._id = _id;
        this.title = title;
        this.desc = desc;
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
}
