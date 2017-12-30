package com.example.nayan.tasklist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class scheduleMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_main);
        Intent intentget = getIntent();
        String date =  intentget.getStringExtra("dateSchedule");
        if(date == null){
            date = intentget.getStringExtra("dateAdd");
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateRaw =  new Date();
        String dateToday =  dateFormat.format(dateRaw);
        TextView textView =  (TextView)findViewById(R.id.dateSchedule);
        textView.setText(date);
        Button  button = (Button)findViewById(R.id.schecduleAddMain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent =  new Intent(scheduleMain.this,scheduleAddTask.class);
                startActivity(intent);
            }
        });
        Intent intent =  getIntent();
        String mainTarget = intent.getStringExtra("mainScheduleText");

        if (mainTarget !=  null) {
            addTargetSchedule(mainTarget, date);
        }
       readDataSchedule(date);
    }
    public  void addTargetSchedule(String TEXT , String date){
        sqliteDatabaseSchedule sqliteData =  new sqliteDatabaseSchedule(this);
        SQLiteDatabase db  =  sqliteData.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(KeyWordsdatabase.feedEntrySchedule.dateSchedule,date);
        values.put(KeyWordsdatabase.feedEntrySchedule.schedule,TEXT);
        try{

            db.insertOrThrow(KeyWordsdatabase.feedEntrySchedule.TableNameSchedule,null,values);
            Log.v("added in schedule ","added - "+values);
        }catch (SQLException e ){
            Log.v("added catch schedule ","added nope I am catch- "+values);
            Toast.makeText(scheduleMain.this,"error:- duplicate target.please try again",Toast.LENGTH_SHORT).show();
        }
    }
        public   void readDataSchedule(String date){
        sqliteDatabaseSchedule sqliteDatabaseSchedule =  new sqliteDatabaseSchedule(this);
        SQLiteDatabase db =  sqliteDatabaseSchedule.getReadableDatabase();
        String [] projection =  {KeyWordsdatabase.feedEntrySchedule.schedule};
        String seletion =  KeyWordsdatabase.feedEntrySchedule.dateSchedule +"=?";
        String[] seletionArgs  =  {date};
        Cursor cursor = db.query(KeyWordsdatabase.feedEntrySchedule.TableNameSchedule,projection,seletion,seletionArgs,null,null,null) ;
            ArrayList<String> arrayList =  new ArrayList<String>();
            int coumnTextID  =  cursor.getColumnIndex(KeyWordsdatabase.feedEntrySchedule.schedule);

            if(cursor.moveToFirst())    {
            do{
                arrayList.add(cursor.getString(coumnTextID));
            }while (cursor.moveToNext());
                if(arrayList.get(0) !=  null) {
                    ListView listView = findViewById(R.id.list_view_schedule);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
                    listView.setAdapter(arrayAdapter);
                }
        }
    }
}












