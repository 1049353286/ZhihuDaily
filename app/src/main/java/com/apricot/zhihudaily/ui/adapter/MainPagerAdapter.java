package com.apricot.zhihudaily.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.apricot.zhihudaily.Constants;
import com.apricot.zhihudaily.R;
import com.apricot.zhihudaily.ui.activity.MainActivity;
import com.apricot.zhihudaily.ui.fragment.NewsListFragment;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Apricot on 2016/6/6.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter{
    private Context mContext;
    public MainPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment mFragment=new NewsListFragment();
        Bundle bundle=new Bundle();
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1 - position);
        String date= Constants.Dates.simpleDateFormat.format(calendar.getTime());
        bundle.putString(Constants.BundleKeys.DATE,date);
        bundle.putBoolean(Constants.BundleKeys.IS_FIRST_PAGE,position==0);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int getCount() {
        return MainActivity.PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Calendar displayDate=Calendar.getInstance();
        displayDate.add(Calendar.DAY_OF_YEAR,-position);
        return (position == 0 ? mContext.getString(R.string.zhihu_daily_today) + " " : "")
                + DateFormat.getDateInstance().format(displayDate.getTime());
    }
}
