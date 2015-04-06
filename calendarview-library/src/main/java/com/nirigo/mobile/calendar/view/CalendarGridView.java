package com.nirigo.mobile.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;

/**
 * Created by Sicz-Mesziár János on 2015.03.29..
 */
public class CalendarGridView extends GridView {

    private int mNumColumns = 7,
                mSpacing    = 1;

    // Construct -----------------------------------------------------------------------------------
    public CalendarGridView(Context context) {
        super(context);
        init();
    }

    public CalendarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        this.setNumColumns(mNumColumns);
        this.setVerticalSpacing(mSpacing);
        this.setHorizontalSpacing(mSpacing);
        this.setScrollContainer(false);
        this.setGravity(Gravity.CENTER);
        this.setVerticalScrollBarEnabled(false);
        this.setVerticalFadingEdgeEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        this.setHorizontalFadingEdgeEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // int widthSize = MeasureSpec.getSize(widthMeasureSpec);



        if(heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            if (getAdapter() != null) {
                int newHeight = 0;
                for(int i=0; i<getAdapter().getCount(); i+=mNumColumns){
                    View v = getAdapter().getView(i, getChildAt(i), this);
                    v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                    newHeight += v.getMeasuredHeight() + mSpacing;
                }
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY));
            }
        }

    }




}
