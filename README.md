# CalendarView
Full customizable calendar widget for Android 4 and above. The main goal is to create a customized 
calendar widget that is easy to use while it can be adapted for unique design. I follow the 
AdapterView concept because I am using the GridView with smart populating.

### Motivation
In 2012, I made a Calendar widget to and Android application which could be used several times. I decided to
clean the code and published. I hope to it will be useful.

### Screenshots
<img src="https://github.com/siczmj/calendar/blob/master/screenshots/calendar_example_1.jpg" width="230" />
<img src="https://github.com/siczmj/calendar/blob/master/screenshots/calendar_example_2.jpg" width="230" />
<img src="https://github.com/siczmj/calendar/blob/master/screenshots/calendar_example_3.jpg" width="230" />

------

## Usage

### Download
There is enough to download only the calendarview-library which contains all neccessary files.


### Add CalendarView to your XML layout
```xml
    <com.nirigo.mobile.calendar.view.CalendarView
            android:id="@+id/calendar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            />
```

### Implement CalendarBaseAdapter ...
```java
    public class CustomCalendarAdapter extends CalendarBaseAdapter {

        public CustomCalendarAdapter(Context context) {
            super(context);
        }
        
        @Override
        public View getDateHeaderView(int position, View convertView, ViewGroup parent, CalendarDateHeader calendarDate) {

            // Return the days of the weeks view

        }

        @Override
        public View getDateView(int position, View convertView, ViewGroup parent, CalendarDate calendarDate, int monthType) {

            // Just an usual recycling implementation...
            if(convertView == null){
                // .... inflating custim view for show days
            }

            // Coloring by date
            Date currentDay = calendarDate.getDate();
            if(monthType == MONTH_TYPE_CURRENT) {   // Actual month
                
                if(isToday(currentDay)){
                    // Today
                }else{                    
                    
                }
                
            }else if(monthType == MONTH_TYPE_PREVIOUSLY || monthType == MONTH_TYPE_NEXT) {
                
                // Previously or next month
                
            }

            return convertView;
        }
        
        ...
    }

```

#### ... then make an instance and set it!
```java

    CustomCalendarAdapter calendarAdapter = new CustomCalendarAdapter(MainActivity.this);
    CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
                 calendarView.setAdapter(calendarAdapter);

```


### Settings

#### First day is monday
If you want to start the week on CalendarView, just set it using by setFirstDayMonday().

```java
    calendarView.setFirstDayMonday(true);
```

#### Text formatting for date label (at top of CalendarView)
The CalendarView using the _yyyy. MMMM_ formula to date formatting. If you do not like it, then you 
can change with the new SimpleDateFormat. [Here is the symbol meaning.](http://developer.android.com/reference/java/text/SimpleDateFormat.html)
```java
    calendarView.setDateTextFormat(new SimpleDateFormat("yyyy-MM", Locale.getDefault()));
```

#### Change the appearance of components of CalendarView
It is possible to access the all UI components of CalendarView because of available via getters.

```java
    calendarView.getPrevButton();       // Jump to previously month button
    calendarView.getNextButton();       // Jump to next month button
    calendarView.getDateTextView();     // Actual date textview
    calendarView.getDaysGridView();     // GridView with days of the week and other days
```
Here is the structure to better understanding.
```text
    -------------------------------------
    |       |                   |       |       DaysGridView explain
    | Prev  |       Date        | Next  |
    |       |                   |       |       --------------------------------------
    -------------------------------------       | Mo | Tu | We | Th | Fr | Sat | Sun |
    |                                   |       --------------------------------------
    |                                   |       |  Days of prev. month |             |
    |                                   |       |-----------------------             |
    |                                   | >>>>  |                                    |
    |               Days                |       |                                    |
    |                                   |       |       Days of current month        |
    |                                   |       |                                    |
    |                                   |       |                                    |
    |                                   |       |             -----------------------|
    |                                   |       |             |  Days of next month  |
    -------------------------------------       --------------------------------------
````

### Controls

#### Paging month
```java
    calendarView.showPrevMonth();
    calendarView.showNextMonth();
    calendarView.showDate(date);    // jump to date
```


### Events

#### Calendar changed (month changed)
```java
    calendarView.setOnCalendarChangeListener(new CalendarView.OnCalendarChangeListener() {
        public void onMonthChanged(int newYear, int newMonth) {

        }
    });
```

#### Choose a date (date clicked)
```java
    calendarView.setOnDateClickListener(new CalendarView.OnDateClickListener() {
        public void onDateClick(Date date, View view) {

        }
    });
```

------

## Tips & Tricks

### How to hide days of the week?
It's really easy, just you need to add an empty View in your CalendarAdapter.

```java
    @Override
    public View getDateHeaderView(int position, View convertView, ViewGroup parent, CalendarDateHeader calendarDate) {

        return new View();

    }
```


## License
See the LICENSE file in the project root.