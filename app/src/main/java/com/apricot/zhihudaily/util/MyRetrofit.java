package com.apricot.zhihudaily.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Apricot on 2016/6/12.
 */
public class MyRetrofit {
    private Retrofit retrofit;
    private ZhihuApi zhihuApi;

    public MyRetrofit(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(12, TimeUnit.SECONDS)
                .build();


        retrofit=new Retrofit.Builder()
                .baseUrl("http://news.at.zhihu.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        zhihuApi=retrofit.create(ZhihuApi.class);
    }

    public ZhihuApi getZhihuApi(){
        return zhihuApi;
    }
}
