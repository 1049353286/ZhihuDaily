package com.apricot.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.apricot.zhihudaily.Constants;
import com.apricot.zhihudaily.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Apricot on 2016/8/17.
 */
public class PickDateActivity extends BaseActivity{
    @Bind(R.id.calendar_view)
    CalendarPickerView pickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResId=R.layout.activity_pick_date;
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar nextday=Calendar.getInstance();
        nextday.add(Calendar.DAY_OF_YEAR,1);
        pickerView.init(Constants.Dates.birthday,nextday.getTime())
                .withSelectedDate(Calendar.getInstance().getTime());
        pickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR,1);
                Intent intent=new Intent(PickDateActivity.this,SingleDayNewsActivity.class);
                intent.putExtra(Constants.BundleKeys.DATE,Constants.Dates.simpleDateFormat.format(calendar.getTime()));
                startActivity(intent);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        pickerView.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
            @Override
            public void onInvalidDateSelected(Date date) {
                if(date.after(new Date())){
                    showSnackBar(R.string.not_coming);
                }else {
                    showSnackBar(R.string.not_born);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
