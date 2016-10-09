package com.system.caseandroid.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import com.system.caseandroid.R;

/**
 * 三种日期控件
 */
public class CalendarViewActivity extends AppCompatActivity
{
    private CalendarView calendarView;
    private DatePicker datePicker;
    private TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        calendarView = (CalendarView) findViewById(R.id.cdv);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth)
            {
                Toast.makeText(CalendarViewActivity.this, year+"年"+month+"月"+dayOfMonth+"日", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
