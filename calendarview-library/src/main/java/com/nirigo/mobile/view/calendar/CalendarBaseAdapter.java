package com.nirigo.mobile.view.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.nirigo.mobile.view.calendar.models.CalendarDate;
import com.nirigo.mobile.view.calendar.models.CalendarDateHeader;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by Sicz-Mesziár János on 2015.03.29..
 */
public abstract class CalendarBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<CalendarDate> mItems;

    // Context defaults
    private GregorianCalendar mCalendar, mCalendarToday;
    private DateFormatSymbols mFormatSymbols;
    private List<String>      mWeekDays;
    private boolean           mFirstDayMonday;

    // Logic, populate, ...
    private int mDaysShown;
    private int mDaysLastMonth;
    private int mDaysNextMonth;


    // Custom values
    private List<String> customWeekDays;


    public final static int MONTH_TYPE_PREVIOUSLY   = -1,
                            MONTH_TYPE_CURRENT      =  0,
                            MONTH_TYPE_NEXT         = +1;

    private final static String VIEW_TYPE_HEADER    = "header",
                                VIEW_TYPE_DAY       = "day";

    protected final static AbsListView.LayoutParams dayLayoutParams   = new AbsListView.LayoutParams(
            AbsListView.LayoutParams.MATCH_PARENT,
            AbsListView.LayoutParams.WRAP_CONTENT
    );

    // Constructs ----------------------------------------------------------------------------------
    public CalendarBaseAdapter(Context context) {
        this(context, null);
    }

    public CalendarBaseAdapter(Context context, List<String> weekDays){
        this.mContext = context;
        this.mItems = new ArrayList<CalendarDate>();
        this.mCalendar = new GregorianCalendar();
        this.mCalendarToday = new GregorianCalendar();
        this.mFormatSymbols = new DateFormatSymbols();

        this.customWeekDays = weekDays;

        populate();
    }

    public void populate(){

        // init
        mItems.clear();
        mDaysShown = 0;
        mDaysLastMonth = 0;
        mDaysNextMonth = 0;

        GregorianCalendar tempCalendar = new GregorianCalendar();


        // Init weekdays
        if(customWeekDays != null) mWeekDays = customWeekDays;
        else populateDefaultWeekDays();
        for(String day : mWeekDays){
            if(day != null && !day.isEmpty()) {
                mItems.add(new CalendarDateHeader(day));
                mDaysShown++;
            }
        }


        int year      = getYear();
        int month     = getMonth();
        int firstDay  = getFirstDay();
        int countDays = getCountDay();
        int prevMonth = getPrevMonth();
        int nextMonth = getNextMonth();

        // Previously month's days
        int prevDay  = getCountDay(prevMonth) - firstDay + 1;
        for (int i = 0; i < firstDay; i++) {
            tempCalendar.set(month == 0 ? year-1 : year, prevMonth, prevDay + i);
            mItems.add(new CalendarDate(tempCalendar.getTime()));
            mDaysLastMonth++;
            mDaysShown++;
        }

        // Current month
        for (int i = 1; i <= countDays; i++) {
            tempCalendar.set(year, month, i);
            mItems.add(new CalendarDate(tempCalendar.getTime()));
            mDaysShown++;
        }

        // Next month's days
        mDaysNextMonth = 1;
        while (mDaysShown % 7 != 0) {
            tempCalendar.set(month == 11 ? year+1 : year, nextMonth, mDaysNextMonth);
            mItems.add(new CalendarDate(tempCalendar.getTime()));
            mDaysShown++;
            mDaysNextMonth++;
        }

        // When it's neccessary show the next month days
        if(mItems.size() < 49){ // 7 * 7 => 49
            int to = 49 - mItems.size();
            for(int i = 0; i < to; i++) {
                tempCalendar.set(year, nextMonth, mDaysNextMonth);
                mItems.add(new CalendarDate(tempCalendar.getTime()));
                mDaysShown++;
                mDaysNextMonth++;
            }
        }

    }

    // Day of weeks: original --> "", "Sun", "Mon", ...
    public void populateDefaultWeekDays(){
        this.mWeekDays = new ArrayList<String>();
        Collections.addAll(mWeekDays, mFormatSymbols.getShortWeekdays());
        mWeekDays.remove(0);    // remove empty string
        if(mFirstDayMonday){
            mWeekDays.add(mWeekDays.get(0));    // add "Sun" to end
            mWeekDays.remove(0);                // remove "Sun"
        }
    }

    /**
     * For week starting Monday instead SUNDAY and count start with zero.
     * @return day week starting Monday
     */
    private int getFirstDay(){
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int day = mCalendar.get(Calendar.DAY_OF_WEEK);
        if(mFirstDayMonday)
            return day == Calendar.SUNDAY ? 6 : day - 2;
        else
            return day - 1;
    }

    private int getCountDay(){
        return mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    private int getCountDay(int month){
        return new GregorianCalendar(
                mCalendar.get(Calendar.YEAR),
                month,
                mCalendar.get(Calendar.DAY_OF_MONTH)
        ).getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    // Getter --------------------------------------------------------------------------------------
    public List<String> getWeekDays() {
        return mWeekDays;
    }

    public int getYear(){
        return mCalendar.get(Calendar.YEAR);
    }

    public int getMonth(){
        return mCalendar.get(Calendar.MONTH);
    }

    public int getPrevMonth(){
        return getMonth() == 0 ? 11 : getMonth() - 1;
    }

    public int getNextMonth(){
        return getMonth() == 11 ? 0 : getMonth() + 1;
    }

    public Date getDate(){
        return mCalendar.getTime();
    }

    public boolean isToday(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
                          calendar.setTime(date);
        return isToday(calendar);
    }

    public boolean isToday(Calendar calendar){
        return mCalendarToday.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
               mCalendarToday.get(Calendar.YEAR)  == calendar.get(Calendar.YEAR)  &&
               mCalendarToday.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Setters -------------------------------------------------------------------------------------
    public void setFirstDayMonday(boolean firstDayMonday) {
        this.mFirstDayMonday = firstDayMonday;
        this.mCalendar.setFirstDayOfWeek(firstDayMonday ? Calendar.MONDAY : Calendar.SUNDAY);
    }

    public void setDate(Date date){
        this.mCalendar.setTime(date);
        this.populate();
        this.notifyDataSetChanged();
    }

    public void setDate(int year, int month){
        this.mCalendar.set(year, month, mCalendar.get(Calendar.DAY_OF_MONTH));
        this.populate();
        this.notifyDataSetChanged();
    }

    // Lifecycle -----------------------------------------------------------------------------------
    @Override
    public int getCount() { return mItems.size(); }
    @Override
    public Object getItem(int position) { return mItems.get(position); }
    @Override
    public long getItemId(int position) { return position; }
    @Override
    public boolean areAllItemsEnabled() { return false; }
    @Override
    public boolean isEnabled(int position) { return !(getItem(position) instanceof CalendarDateHeader); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CalendarDate calendarDate = (CalendarDate) getItem(position);

        if(position <= mWeekDays.size() && calendarDate instanceof CalendarDateHeader){                                   // name of day
            convertView = convertView != null && !VIEW_TYPE_HEADER.equals(convertView.getTag(convertView.getId())) ? null : convertView;
            convertView = getDateHeaderView(position, convertView, parent, (CalendarDateHeader) calendarDate);
            convertView.setTag(convertView.getId(), VIEW_TYPE_HEADER);
        } else {
            convertView = convertView != null && !VIEW_TYPE_DAY.equals(convertView.getTag(convertView.getId())) ? null : convertView;
            if(position <= mDaysLastMonth + mWeekDays.size() - 1){       // prev month
                convertView = getDateView(position, convertView, parent, calendarDate, MONTH_TYPE_PREVIOUSLY);
            } else if (position <= mDaysShown - mDaysNextMonth  ) {         // current month
                convertView = getDateView(position, convertView, parent, calendarDate, MONTH_TYPE_CURRENT);
            } else {                                                        // next month
                convertView = getDateView(position, convertView, parent, calendarDate, MONTH_TYPE_NEXT);
            }
            convertView.setTag(convertView.getId(), VIEW_TYPE_DAY);
        }

        convertView.setLayoutParams(dayLayoutParams);

        return convertView;
    }


    public abstract View getDateHeaderView(int position, View convertView, ViewGroup parent, CalendarDateHeader calendarDate);
    public abstract View getDateView(int position, View convertView, ViewGroup parent, CalendarDate calendarDate, int monthType);

}
