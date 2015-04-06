# CalendarView
A full customizable calendar widget for Android 4 and above. The main goal is to create a customized 
calendar widget that is easy to use while it can be adapted for unique design. I follow the 
AdapterView concept because I am using the GridView with smart populating.

### Motivation
2 years ago I made a Calendar widget on Android which could be used several times. I decided to 
clean the code and published. I hope to it will be useful.

### Screenshots

<img src="https://github.com/siczmj/calendar/blob/master/screenshots/calendar_example_1.jpg" width="200" />
<img src="https://github.com/siczmj/calendar/blob/master/screenshots/calendar_example_2.jpg" width="200" />
<img src="https://github.com/siczmj/calendar/blob/master/screenshots/calendar_example_3.jpg" width="200" />

## Usage

### Prepare build.gradle

There is enough to download only the calendarview-library which contains all neccessary files. 




### Add CalendarView to your XML layout
```xml
    <com.nirigo.mobile.calendar.view.CalendarView
            android:id="@+id/calendar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            />
```

### Implement CalendarBaseAdapter

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

            // Just the usual...
            if(convertView == null){
                // ....
            }

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

### Controls and settings




## Tips & Tricks

### How to hide days of the week?
It's really easy, just you need to add an empty View in your CalendarAdapter.

