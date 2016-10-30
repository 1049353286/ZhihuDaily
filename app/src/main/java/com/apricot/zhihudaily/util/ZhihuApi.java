package com.apricot.zhihudaily.util;

import com.apricot.zhihudaily.bean.DailyNews;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Apricot on 2016/6/12.
 */
public interface ZhihuApi {
    @GET("api/4/news/before/{date}")
    Observable<DailyNews> getDailyNews(@Path("date") String date);

}
