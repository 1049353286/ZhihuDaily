package com.apricot.zhihudaily.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apricot.zhihudaily.Constants;
import com.apricot.zhihudaily.bean.DailyNews;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by Apricot on 2016/6/6.
 */
public class DailyNewsDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_DATE,
            DBHelper.COLUMN_CONTENT
    };

    public DailyNewsDataSource(Context context){
        dbHelper=new DBHelper(context);
        database=dbHelper.getWritableDatabase();
    }

    public List<DailyNews> insertDailyNewsList(String date,String content){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DATE, date);
        values.put(DBHelper.COLUMN_CONTENT, content);
        long insertId=database.insert(DBHelper.TABLE_NAME,null,values);
        Cursor cursor=database.query(DBHelper.TABLE_NAME,allColumns,DBHelper.COLUMN_ID+"="+insertId,null,null,null,null);
        cursor.moveToFirst();
        List<DailyNews> newsList=cursorToNewsList(cursor);
        cursor.close();
        return newsList;
    }

    public void updateNewsList(String date,String content){
        ContentValues values=new ContentValues();
        values.put(DBHelper.COLUMN_DATE,date);
        values.put(DBHelper.COLUMN_CONTENT, content);
        database.update(DBHelper.TABLE_NAME, values, DBHelper.COLUMN_DATE + "=" + date, null);
    }

    public void insertOrUpdateNewsList(String date, String content) {
        if (newsOfTheDay(date) != null) {
            updateNewsList(date, content);
        } else {
            insertDailyNewsList(date, content);
        }
    }

    public List<DailyNews> newsOfTheDay(String date) {
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, DBHelper.COLUMN_DATE + " = " + date, null, null, null, null);

        cursor.moveToFirst();
        List<DailyNews> newsList = cursorToNewsList(cursor);
        cursor.close();
        return newsList;
    }

    private List<DailyNews> cursorToNewsList(Cursor cursor){
        if(cursor!=null&&cursor.getCount()>0){
            return new GsonBuilder().create().fromJson(cursor.getString(2), Constants.Types.newsListType);
        }else{
            return null;
        }
    }
}
