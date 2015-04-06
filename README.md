# CalendarView
A full customizable calendar widget for Android 4 and above. The main goal is to create a customized 
calendar widget that is easy to use while it can be adapted for unique design. I follow the 
AdapterView concept because I am using the GridView with smart populating.

### Motivation
2 years ago I made a Calendar widget on Android which could be used several times. I decided to 
clean the code and published. I hope to it will be useful.


## Usage

### Prepare build.gradle

There is enough to download only the calendarview-library which contains all neccessary files. If 
your project already uses Gradle just insert the follow row at the dependencies:
<pre><code>
 ... maven in progress, sorry ...
</code></pre>


### Add CalendarView to your layout XML
<pre><code>
    <com.nirigo.mobile.calendar.view.CalendarView
            android:id="@+id/calendar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            />
</code></pre>

### Implement CalendarBaseAdapter

...



## Tips & Tricks

### How to hide the days of the week?
It's really easy, just you need to add an empty View in your CalendarAdapter.

