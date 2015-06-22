package com.nirigo.mobile.view.calendar.models;

/**
 * Created by Sicz-Mesziár János on 2015.03.29..
 */
public class CalendarDateHeader extends CalendarDate {

    protected String name;

    public CalendarDateHeader(String name) {
        super(null);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
