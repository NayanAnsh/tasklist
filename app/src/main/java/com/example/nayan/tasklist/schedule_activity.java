package com.example.nayan.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

public class schedule_activity extends AppCompatActivity {
    public static String dateSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_activity);
        CalendarView calendarView = findViewById(R.id.scheduleCalender);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Intent intent =  new Intent(schedule_activity.this,scheduleMain.class);
                //going to @scheduleMain with date selected information will use these data there .

                String dayDate;
                if (day < 10){
                    dayDate =  String.valueOf(0)+String.valueOf(day);
                }else{
                    dayDate =  String.valueOf(day);
                }
                 dateSchedule =  dayDate+"/"+String.valueOf(++month)+"/"+String.valueOf(year);

                intent.putExtra("dateSchedule",dateSchedule);
                startActivity(intent);

            }
        });
    }

}
