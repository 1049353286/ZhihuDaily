package com.apricot.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apricot.zhihudaily.Constants;
import com.apricot.zhihudaily.R;
import com.apricot.zhihudaily.bean.DailyNews;
import com.apricot.zhihudaily.bean.Story;
import com.apricot.zhihudaily.task.SaveNewsListTask;
import com.apricot.zhihudaily.ui.activity.BaseActivity;
import com.apricot.zhihudaily.ui.adapter.NewsAdapter;
import com.apricot.zhihudaily.util.MyRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Apricot on 2016/6/6.
 */
public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Observer<List<Story>>{
    private List<Story> storyList=new ArrayList<>();
    private String date;
    private boolean isToday;
//    private boolean isRefreshed=false;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.news_list)
    RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private MyRetrofit myRetrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            Bundle bundle=getArguments();
            date=bundle.getString(Constants.BundleKeys.DATE);
            isToday=bundle.getBoolean(Constants.BundleKeys.IS_FIRST_PAGE);
            setRetainInstance(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_news_list,container,false);
        ButterKnife.bind(this,view);
        myRetrofit=new MyRetrofit();
        mRecyclerView.setHasFixedSize(isToday);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNewsAdapter=new NewsAdapter(storyList);
        mRecyclerView.setAdapter(mNewsAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(storyList.size()==0){
            myRetrofit.getZhihuApi().getDailyNews(date)
                    .map(new Func1<DailyNews, List<Story>>() {
                        @Override
                        public List<Story> call(DailyNews dailyNews) {
                            Log.d("Observable Test", "get story list");
                            return dailyNews.getStories();
                        }
                    })
                    .doOnNext(new Action1<List<Story>>() {
                        @Override
                        public void call(List<Story> storyList) {
                            for (Story story:storyList){
                                story.setDate(date);
                            }
                            Log.d("Observable Test", "set date");
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        }
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        myRetrofit.getZhihuApi().getDailyNews(date)
                .map(new Func1<DailyNews, List<Story>>() {
                    @Override
                    public List<Story> call(DailyNews dailyNews) {
                        return dailyNews.getStories();
                    }
                })
                .doOnNext(new Action1<List<Story>>() {
                    @Override
                    public void call(List<Story> storyList) {
                        for (Story story:storyList){
                            story.setDate(date);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onCompleted() {
        Log.d("Observable Test", "Completed");
        mSwipeRefreshLayout.setRefreshing(false);
        mNewsAdapter.updateNewsList(storyList);
        new SaveNewsListTask(storyList).execute();
    }

    @Override
    public void onError(Throwable e) {
        Log.d("Observable Test", "on error");
        mSwipeRefreshLayout.setRefreshing(false);
        if(isAdded()){
            ((BaseActivity)getActivity()).showSnackBar(R.string.network_error);
        }
    }



    @Override
    public void onNext(List<Story> storyList) {
        Log.d("Observable Test", "on next");
        this.storyList=storyList;
    }
}
