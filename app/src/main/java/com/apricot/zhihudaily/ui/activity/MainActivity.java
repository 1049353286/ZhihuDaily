package com.apricot.zhihudaily.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.apricot.zhihudaily.R;
import com.apricot.zhihudaily.ui.adapter.MainPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    public static final int PAGE_COUNT = 5;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.fab_pick_date)
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResId=R.layout.activity_main;
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.app_name);
        mViewPager.setOffscreenPageLimit(PAGE_COUNT);
        MainPagerAdapter mainPagerAdapter=new MainPagerAdapter(getSupportFragmentManager(),MainActivity.this);
        mViewPager.setAdapter(mainPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PickDateActivity.class);
                startActivity(intent);
            }
        });
    }
}
