package com.example.nayan.tasklist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nayan on 16-12-2017.
 */

public class sqliteDatabaseSchedule extends SQLiteOpenHelper {
    private static String name = "schedule";

    public sqliteDatabaseSchedule(Context context) {
        super(context, name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         sqLiteDatabase.execSQL(KeyWordsdatabase.feedEntrySchedule.TableexeSchedule);
        Log.v("sqliteDatabaseschedule","here i AM .I JUST CREATED SQLITE DATABSE YOU ARE GOOD T GO!!!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
