package com.apricot.zhihudaily.bean;

import java.util.List;

/**
 * Created by Apricot on 2016/6/6.
 */
public class DailyNews {
    private String date;
    private List<Story> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
