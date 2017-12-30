package com.example.nayan.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

public class history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        CalendarView calendarView =  (CalendarView)findViewById(R.id.calenderHistory);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                //"dd/MM/yyyy"
                String dayDate;
                if (day < 10){
                    dayDate =  String.valueOf(0)+String.valueOf(day);
                }else{
                    dayDate =  String.valueOf(day);
                }
                String dateCalender =  dayDate+"/"+String.valueOf(++month)+"/"+String.valueOf(year);
                Intent intent =  new Intent(history.this,history_data.class);
                intent.putExtra("dateCalender",dateCalender);
                startActivity(intent);

            }
        });


    }




}
