package com.nirigo.mobile.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nirigo.mobile.calendar.view.CalendarBaseAdapter;
import com.nirigo.mobile.calendar.view.models.CalendarDate;
import com.nirigo.mobile.calendar.view.models.CalendarDateHeader;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sicz-Mesziár János on 2015.04.06..
 * Example for custom adapter.
 */
public class CustomCalendarAdapter extends CalendarBaseAdapter {

    private SparseArray<Integer> weather = new SparseArray<>();
    private ViewHolder holder;
    private int defaultBackground, prevnextBackground, todayBackground, activeText, inactiveText;

    public CustomCalendarAdapter(Context context) {
        super(context);
        defaultBackground = Color.parseColor("#E4F5E5");
        prevnextBackground = Color.parseColor("#C4D1B8");
        todayBackground = Color.parseColor("#F1E9B1");
        activeText = Color.parseColor("#338C38");
        inactiveText = Color.parseColor("#aaaaaa");
    }

    // Weather logic ---------------------
    public void addWeather(Date date, int value){
        weather.put(hash(date), value);
    }

    public int getWeather(Date date){
        int key = hash(date);
        return weather.indexOfKey(key) >= 0 ? weather.get(key) : Integer.MIN_VALUE;
    }

    public static int hash(Date date){
        if(date == null) return 0;
        Calendar c = Calendar.getInstance();
                 c.setTime(date);
        return   c.get(Calendar.YEAR) * 365 +
                 c.get(Calendar.MONTH)*  31 +
                 c.get(Calendar.DAY_OF_MONTH);
    }



    // Design pattern implementation ------------
    @Override
    public View getDateHeaderView(int position, View convertView, ViewGroup parent, CalendarDateHeader calendarDate) {

        if(convertView == null)
            convertView = View.inflate(parent.getContext(), R.layout.listheader_calendar, null);

        TextView headerTextView = (TextView) convertView;
                 headerTextView.setText(calendarDate.getName()); // Set the day of week

        return convertView;

    }

    @Override
    public View getDateView(int position, View convertView, ViewGroup parent, CalendarDate calendarDate, int monthType) {

        // Just the usual...
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.listitem_calendar, null);
            holder = new ViewHolder();
            holder.dayTextView = (TextView) convertView.findViewById(R.id.day);
            holder.valueTextView = (TextView) convertView.findViewById(R.id.value);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        // How should be handling the day appearance
        Date currentDay = calendarDate.getDate();
        Calendar currentDayCalendar = Calendar.getInstance();
        currentDayCalendar.setTime(currentDay);

        int weatherValue = getWeather(currentDay);

        holder.dayTextView.setText(String.valueOf(currentDayCalendar.get(Calendar.DAY_OF_MONTH)));
        holder.valueTextView.setText(weatherValue == Integer.MIN_VALUE ? "-" : String.valueOf(weatherValue) + "°");

        // Coloring by month
        if(monthType == MONTH_TYPE_CURRENT) {

            // Coloring today using isToday() method
            if(isToday(currentDayCalendar)){
                convertView.setBackgroundColor(todayBackground);
            }else{
                convertView.setBackgroundColor(defaultBackground);
            }
            holder.dayTextView.setTextColor(activeText);

        }else if(monthType == MONTH_TYPE_PREVIOUSLY || monthType == MONTH_TYPE_NEXT) {
            convertView.setBackgroundColor(prevnextBackground);
            holder.dayTextView.setTextColor(inactiveText);
        }

        return convertView;
    }

    public class ViewHolder{
        TextView dayTextView, valueTextView;
    }

}
