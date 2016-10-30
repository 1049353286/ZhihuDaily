package com.apricot.zhihudaily;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.apricot.zhihudaily.db.DailyNewsDataSource;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Apricot on 2016/6/6.
 */
public class MyApplication extends Application{
    private static DailyNewsDataSource dataSource;
    private static MyApplication applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=this;
        initImageLoader(getApplicationContext());
        dataSource=new DailyNewsDataSource(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static MyApplication getInstance(){
        return applicationContext;
    }

    public static DailyNewsDataSource getDataSource(){
        return dataSource;
    }

    public static SharedPreferences getSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

}
