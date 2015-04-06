package com.nirigo.mobile.calendar.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nirigo.mobile.calendar.view.models.CalendarDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Sicz-Mesziár János on 2015.03.29..
 * An new CalendarView using a flexible and customizable UI.
 */
public class CalendarView extends RelativeLayout {

    // Views ---------------------------------
    private Button mPrevButton, mNextButton;
    private TextView mDateTextView;
    private GridView mDaysGridView;

    private LayoutParams prevButtonLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT),
                         nextButtonLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT),
                         dateTextViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT),
                         daysGridViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

    private CalendarBaseAdapter mAdapter;


    // Defaults ------------------------------
    private int mTextSize;
    private SimpleDateFormat mDateTextFormat;

    // Events --------------------------------
    private OnDateClickListener onDateClickListener;
    private OnCalendarChangeListener onCalendarChangeListener;


    // Construct -----------------------------------------------------------------------------------
    public CalendarView(Context context) {
        super(context);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // Init's --------------------------------------------------------------------------------------
    private void init(){
        initAttributes();
        initViews();
        initCalendar();
        initDateText();
    }

    private void initAttributes(){
        // TODO
        mTextSize = 12;
        mDateTextFormat = new SimpleDateFormat("yyyy. MMMM", Locale.getDefault());
    }

    private void initViews(){

        // Buttons
        mPrevButton = createButton();
        mPrevButton.setText("◀");
        mPrevButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showPrevMonth();
            }
        });

        mNextButton = createButton();
        mNextButton.setText("▶");
        mNextButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showNextMonth();
            }
        });

        // Current month
        mDateTextView = new TextView(getContext());
        mDateTextView.setId(ViewUtils.generateViewIdCompat());
        mDateTextView.setTextSize(mTextSize);
        mDateTextView.setGravity(Gravity.CENTER);
        mDateTextView.setTypeface(Typeface.DEFAULT_BOLD);

        // Days
        mDaysGridView = new CalendarGridView(getContext());
        mDaysGridView.setId(ViewUtils.generateViewIdCompat());
        mDaysGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CalendarDate calendarDate = (CalendarDate) mDaysGridView.getItemAtPosition(i);
                if(calendarDate != null && onDateClickListener != null){
                    onDateClickListener.onDateClick(calendarDate.getDate(), view);
                }
            }
        });

        // layouting
        prevButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        nextButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        dateTextViewLayoutParams.addRule(RelativeLayout.RIGHT_OF, mPrevButton.getId());
        dateTextViewLayoutParams.addRule(RelativeLayout.LEFT_OF, mNextButton.getId());
        dateTextViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        dateTextViewLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, mPrevButton.getId());
        daysGridViewLayoutParams.addRule(RelativeLayout.BELOW, mDateTextView.getId());

        // add views ...
        this.addView(mPrevButton,   prevButtonLayoutParams);
        this.addView(mNextButton,   nextButtonLayoutParams);
        this.addView(mDateTextView, dateTextViewLayoutParams);
        this.addView(mDaysGridView, daysGridViewLayoutParams);
    }

    private void initCalendar(){
        if(mAdapter == null) {
            mAdapter = new CalendarAdapter(getContext());
            mAdapter.setFirstDayMonday(true);
            mAdapter.populate();
        }
        setAdapter(mAdapter);
    }

    private void initDateText(){
        if(mAdapter != null && mAdapter.getDate() != null) {
            mDateTextView.setText(mDateTextFormat.format(mAdapter.getDate()));
        }
    }


    @SuppressWarnings("deprecation")
    private Button createButton(){
        Button b = new Button(getContext(), null, android.R.attr.borderlessButtonStyle);
        b.setId(ViewUtils.generateViewIdCompat());
        b.setTextSize(mTextSize);
        b.setGravity(Gravity.CENTER);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) b.setBackground(null);
        else b.setBackgroundDrawable(null);
        return b;
    }

    // Navigation ----------------------------------------------------------------------------------
    public void showPrevMonth(){
        if(mAdapter != null) {
            if(mAdapter.getMonth() == 0)
                showDate(mAdapter.getYear() - 1, 11);
            else
                showDate(mAdapter.getYear(), mAdapter.getMonth() - 1);
        }
    }

    public void showNextMonth(){
        if(mAdapter != null) {
            if(mAdapter.getMonth() == 11)
                showDate(mAdapter.getYear() + 1, 0);
            else
                showDate(mAdapter.getYear(), mAdapter.getMonth() + 1);
        }
    }

    public void showDate(int year, int month){
        if(mAdapter != null) {
            mAdapter.setDate(year, month);
            initDateText();
            if(onCalendarChangeListener != null)
                onCalendarChangeListener.onMonthChanged(year, month+1);
        }
    }

    public void showDate(Date date){
        if(mAdapter != null) {
            mAdapter.setDate(date);
            initDateText();
        }
    }


    // Getters -------------------------------------------------------------------------------------
    public Button getPrevButton() {
        return mPrevButton;
    }

    public Button getNextButton() {
        return mNextButton;
    }

    public TextView getDateTextView() {
        return mDateTextView;
    }

    public GridView getDaysGridView() {
        return mDaysGridView;
    }

    public CalendarBaseAdapter getAdapter() {
        return mAdapter;
    }

    // Setters -------------------------------------------------------------------------------------
    public void setAdapter(CalendarBaseAdapter adapter) {
        this.mAdapter = adapter;
        this.mDaysGridView.setAdapter(mAdapter);
    }

    public void setDateTextFormat(SimpleDateFormat dateTextFormat) {
        this.mDateTextFormat = dateTextFormat;
    }

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
    }

    public void setOnCalendarChangeListener(OnCalendarChangeListener onCalendarChangeListener) {
        this.onCalendarChangeListener = onCalendarChangeListener;
    }

    public void setFirstDayMonday(boolean monday){
        if(mAdapter != null) {
            mAdapter.setFirstDayMonday(monday);
            mAdapter.notifyDataSetChanged();
        }
    }

    // Interfaces ----------------------------------------------------------------------------------
    public interface OnDateClickListener{
        void onDateClick(Date date, View view);
        // TODO: object store
    }

    public interface OnCalendarChangeListener {
        void onMonthChanged(int newYear, int newMonth);
    }


}
