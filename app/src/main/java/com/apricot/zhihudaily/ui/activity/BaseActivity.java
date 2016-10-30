package com.apricot.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.apricot.zhihudaily.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Apricot on 2016/6/6.
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    @Nullable
    @Bind(R.id.coordinator_layout)
    protected CoordinatorLayout mCoordinatorLayout;

    protected int layoutResId=R.layout.activity_base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSnackBar(int resId){
        Snackbar.make(mCoordinatorLayout,resId,Snackbar.LENGTH_SHORT).show();
    }
}
