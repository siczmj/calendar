package com.nirigo.mobile.calendar;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * Created by Sicz-Mesziár János on 2015.04.06..
 * Just an adapter class for selection Adapter.
 */
public class AdapterSelectorAdapter extends BaseAdapter implements SpinnerAdapter {

    String[] adapters;

    public AdapterSelectorAdapter(String[] adapters) {
        this.adapters = adapters;
    }
    public int getCount() { return adapters.length; }
    public Object getItem(int i) { return adapters[i]; }
    public long getItemId(int i) { return i; }
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup);
    }
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv = (TextView) view;
        if(tv == null) {
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, viewGroup.getContext().getResources().getDisplayMetrics());

            tv = new TextView(viewGroup.getContext());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setAllCaps(true);

            tv.setPadding(padding, padding, padding, padding);
        }
        tv.setText(adapters[i]);
        return tv;
    }

}
