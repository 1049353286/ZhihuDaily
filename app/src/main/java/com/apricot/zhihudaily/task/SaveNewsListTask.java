package com.apricot.zhihudaily.task;

import android.os.AsyncTask;
import android.util.Log;

import com.apricot.zhihudaily.MyApplication;
import com.apricot.zhihudaily.bean.Story;
import com.apricot.zhihudaily.db.DailyNewsDataSource;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by Apricot on 2016/6/12.
 */
public class SaveNewsListTask extends AsyncTask<Void,Void,Void>{
    private List<Story> storyList;

    public SaveNewsListTask(List<Story> storyList){
        this.storyList=storyList;
    }

    @Override
    protected Void doInBackground(Void... params) {
        saveStoryList(storyList);
        return null;
    }

    private void saveStoryList(List<Story> storyList){
        DailyNewsDataSource dataSource= MyApplication.getDataSource();
        String date=storyList.get(0).getDate();
        dataSource.insertOrUpdateNewsList(date,new GsonBuilder().create().toJson(storyList));
        Log.d("Observable Test", "save successful");
    }
}
