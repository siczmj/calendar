package com.nirigo.mobile.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nirigo.mobile.calendar.view.CalendarAdapter;
import com.nirigo.mobile.calendar.view.CalendarBaseAdapter;
import com.nirigo.mobile.calendar.view.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private CalendarView calendarView;
    private Button controlBack, controlForward;
    private EditText controlJump;

    private Spinner adapterSelectorSpinner;
    private AdapterSelectorAdapter adapterSelectorAdapter;
    private final static String ADAPTER_DEFAULT = "default",
                                ADAPTER_GREEN   = "custom green";

    private CalendarBaseAdapter calendarAdapter;

    private SimpleDateFormat controlJumpFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views -------------------
        controlBack = (Button) findViewById(R.id.back);
        controlForward = (Button) findViewById(R.id.forward);
        controlJump = (EditText) findViewById(R.id.jump);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        adapterSelectorSpinner = (Spinner) findViewById(R.id.adapter_selector);

        // CalendarView access View and change --------
        calendarView.getPrevButton();       // Jump to previously month button
        calendarView.getNextButton();       // Jump to next month button
        calendarView.getDateTextView();     // Actual date textview
        calendarView.getDaysGridView();     // GridView with days of the week and other days

        // CalendarView behavior changin
        calendarView.setDateTextFormat(new SimpleDateFormat("yyyy.MM", Locale.getDefault()));
        calendarView.setFirstDayMonday(true);
        calendarView.getDaysGridView().setDrawSelectorOnTop(true);


        // CalendarView custom adapter ----------------
        calendarAdapter = calendarView.getAdapter();
        adapterSelectorAdapter = new AdapterSelectorAdapter(new String[]{
            ADAPTER_DEFAULT,
            ADAPTER_GREEN
        });
        adapterSelectorSpinner.setAdapter(adapterSelectorAdapter);
        adapterSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {}
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedAdapter = (String) adapterSelectorSpinner.getSelectedItem();
                if(selectedAdapter.equals(ADAPTER_DEFAULT)){

                    // Built-In adapter
                    calendarAdapter = new CalendarAdapter(MainActivity.this);
                    calendarView.getDaysGridView().setBackgroundResource(0);
                    calendarView.setAdapter(calendarAdapter);

                }else if (selectedAdapter.equals(ADAPTER_GREEN)) {

                    // Custom adapter with random temperature as weather service
                    calendarAdapter = new CustomCalendarAdapter(MainActivity.this);
                    GregorianCalendar cal = new GregorianCalendar();
                    Random rnd = new Random();
                    for(int d=1; d<20; d++) {
                        cal.set(Calendar.DAY_OF_MONTH, d);
                        ((CustomCalendarAdapter)calendarAdapter).addWeather(cal.getTime(), rnd.nextInt(40));
                    }
                    calendarView.getDaysGridView().setBackgroundColor(Color.GRAY);
                    calendarView.setAdapter(calendarAdapter);

                }

            }
        });

        // CalendarView custom controls ---------------
        controlBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                calendarView.showPrevMonth();

            }
        });

        controlForward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                calendarView.showNextMonth();

            }
        });

        controlJump.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                String newText = editable.toString().trim();
                if (newText.length() >= 6) {
                    try {

                        Date date = controlJumpFormat.parse(newText);
                        calendarView.showDate(date);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        // CalendarView events ----------
        calendarView.setOnCalendarChangeListener(new CalendarView.OnCalendarChangeListener() {
            public void onMonthChanged(int newYear, int newMonth) {

                Toast.makeText(
                        MainActivity.this,
                        "Year : " + newYear + "\n" + "Month : " + newMonth,
                        Toast.LENGTH_SHORT
                ).show();

                calendarView.getDateTextView().setTextColor(
                        newMonth % 2 == 0 ? Color.BLUE : Color.BLACK
                );

            }
        });
        calendarView.setOnDateClickListener(new CalendarView.OnDateClickListener() {
            public void onDateClick(Date date, View view) {

                Toast.makeText(
                        MainActivity.this,
                        date.toString(),
                        Toast.LENGTH_SHORT
                ).show();

            }
        });






    }


}
