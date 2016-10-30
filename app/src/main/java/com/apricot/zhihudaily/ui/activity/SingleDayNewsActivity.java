package com.apricot.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.apricot.zhihudaily.Constants;
import com.apricot.zhihudaily.R;
import com.apricot.zhihudaily.ui.fragment.NewsListFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Apricot on 2016/8/25.
 */
public class SingleDayNewsActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String stringDate=getIntent().getStringExtra(Constants.BundleKeys.DATE);
        Calendar calendar=Calendar.getInstance();
        try {
            Date date=Constants.Dates.simpleDateFormat.parse(stringDate);
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR,-1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(DateFormat.getDateInstance().format(calendar.getTime()));

        Bundle bundle=new Bundle();
        bundle.putString(Constants.BundleKeys.DATE,stringDate);
        bundle.putBoolean(Constants.BundleKeys.IS_FIRST_PAGE,isSameDay(calendar,Calendar.getInstance()));

        Fragment singleFragment=new NewsListFragment();
        singleFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_frame,singleFragment)
                .commit();

    }
    private boolean isSameDay(Calendar first,Calendar second){
        return (first.get(Calendar.YEAR)==second.get(Calendar.YEAR)
                && first.get(Calendar.DAY_OF_YEAR)==second.get(Calendar.DAY_OF_YEAR));
    }
}
