package com.nirigo.mobile.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nirigo.mobile.calendar.view.models.CalendarDate;
import com.nirigo.mobile.calendar.view.models.CalendarDateHeader;

import java.util.Calendar;


/**
 * Created by Sicz-Mesziár János on 2015.03.29..
 */
public class CalendarAdapter extends CalendarBaseAdapter {

    private int backgroundColorDefault  = Color.parseColor("#F5F7F9"),
                backgroundColorInactive = Color.parseColor("#D7D9DB"),
                backgroundColorToday    = Color.parseColor("#778088");

    private int textColorDefault        = Color.parseColor("#57626C"),
                textColorInactive       = Color.parseColor("#9FA6AB"),
                textColorToday          = Color.parseColor("#EFEFEF");

    private int paddingVertical         = 10,
                paddingHorizontal       = 3;

    public CalendarAdapter(Context context) {
        super(context);
        paddingVertical = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, context.getResources().getDisplayMetrics());
    }

    @Override
    public View getDateHeaderView(int position, View convertView, ViewGroup parent, CalendarDateHeader calendarDate) {
        if(convertView == null)
            convertView = new TextView(parent.getContext());

        TextView headerDateTextView = (TextView) convertView;
        headerDateTextView.setGravity(Gravity.CENTER);
        headerDateTextView.setText(calendarDate.getName());
        headerDateTextView.setTextColor(textColorDefault);
        headerDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        headerDateTextView.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal ,paddingVertical);

        return convertView;
    }

    @Override
    public View getDateView(int position, View convertView, ViewGroup parent, CalendarDate calendarDate, int monthType) {
        if(convertView == null)
            convertView = new TextView(parent.getContext());

        Calendar cal = Calendar.getInstance();
        cal.setTime(calendarDate.getDate());

        TextView dateTextView = (TextView) convertView;
        dateTextView.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        dateTextView.setGravity(Gravity.CENTER);
        dateTextView.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);

        if(monthType == MONTH_TYPE_PREVIOUSLY){

            dateTextView.setTextColor(textColorInactive);
            dateTextView.setBackgroundColor(backgroundColorInactive);

        }else if(monthType == MONTH_TYPE_CURRENT){

            if(isToday(cal)){
                dateTextView.setTextColor(textColorToday);
                dateTextView.setBackgroundColor(backgroundColorToday);
            }else {
                dateTextView.setTextColor(textColorDefault);
                dateTextView.setBackgroundColor(backgroundColorDefault);
            }

        }else if(monthType == MONTH_TYPE_NEXT){

            dateTextView.setTextColor(textColorInactive);
            dateTextView.setBackgroundColor(backgroundColorInactive);

        }


        return convertView;
    }


}
