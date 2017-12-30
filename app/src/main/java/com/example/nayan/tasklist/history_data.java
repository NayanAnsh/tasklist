package com.example.nayan.tasklist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class history_data extends AppCompatActivity {
    int i;


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_data);
        Intent intent = getIntent();
        String dateCalender = intent.getStringExtra("dateCalender");
        TextView textViewDate = (TextView) findViewById(R.id.textHistoryDate);
        textViewDate.setText((dateCalender) + "\n");
        ArrayList<String> arrayList = readDateHistory();
        TextView textViewData = (TextView) findViewById(R.id.textHistoryData);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String todayDate = dateFormat.format(date);
        if (todayDate.equals(dateCalender)) {
            textViewData.setText("your targets will be added tomorrow into history");
        } else {


            for (i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).equals(dateCalender)) {
                    readHistory(textViewData, arrayList.get(i));
                    break;
                } else if (i == (arrayList.size() - 1)) {
                    textViewData.append("\n" + "oops!nothing found");
                }
            }
        }
    }

    public ArrayList<String> readDateHistory() {
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        SQLiteDatabase db = sqliteDataBase.getReadableDatabase();
        String[] projection = {KeyWordsdatabase.feedEntryEXTRA.date};
        Cursor cursor = db.query(KeyWordsdatabase.feedEntryEXTRA.TableNameExtra, projection, null, null, null, null, null);
        cursor.moveToFirst();
        int columnDate = cursor.getColumnIndex(KeyWordsdatabase.feedEntryEXTRA.date);
        ArrayList<String> arrayList = new ArrayList<>();
        do {
            arrayList.add(cursor.getString(columnDate));
        } while (cursor.moveToNext());
        cursor.moveToLast();
        return arrayList;
    }

    public void readHistory(TextView textView, String date) {
        sqliteDataBase sqliteDataBase = new sqliteDataBase(this);
        SQLiteDatabase db = sqliteDataBase.getReadableDatabase();
        String[] projection = {KeyWordsdatabase.feedEntryEXTRA.idExtra, KeyWordsdatabase.feedEntryEXTRA.historyData, KeyWordsdatabase.feedEntryEXTRA.date};
        String selection = KeyWordsdatabase.feedEntryEXTRA.date + "=?";
        String[] selectingArgs = {date};
        Cursor cursor = db.query(KeyWordsdatabase.feedEntryEXTRA.TableNameExtra, projection, selection, selectingArgs, null, null, null);
        int columnHistory = cursor.getColumnIndex(KeyWordsdatabase.feedEntryEXTRA.historyData);
        int colunDate = cursor.getColumnIndex(KeyWordsdatabase.feedEntryEXTRA.date);
        cursor.moveToLast();
        String historyText = cursor.getString(columnHistory);
        if (historyText == null) {
            historyText = "it looks like you had a holiday";
        }
        textView.append(
                historyText);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.history,menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId =  item.getItemId();
//        if (R.id.delete_history == itemId ){
//            deleteHistory(i+1);
//        }
//        return true;
//    }
//public  void deleteHistory(int id ){
//        TextView textViewData =  (TextView)findViewById(R.id.textHistoryData);
//    sqliteDataBase sqliteDataBase =  new sqliteDataBase(this);
//    SQLiteDatabase db =  sqliteDataBase.getWritableDatabase();
//    String selection =  KeyWordsdatabase.feedEntryEXTRA.idExtra +"=?";
//    String[] selectionArgs =  {String.valueOf(id)};
//    ContentValues values =  new ContentValues();
//    values.put(KeyWordsdatabase.feedEntryEXTRA.historyData,"it looks like you had a holiday");
//    db.update(KeyWordsdatabase.feedEntry.TableName, values,selection,selectionArgs);
//    readHistory(textViewData,String.valueOf(id));
//}
}
