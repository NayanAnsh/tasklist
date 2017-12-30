package com.example.nayan.tasklist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nayan on 14-11-2017.
 */

public class sqliteDataBase extends SQLiteOpenHelper {
    public   static   final String  TableName =  "taskList";
    public sqliteDataBase(Context context) {
        super(context,TableName , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(KeyWordsdatabase.feedEntry.DatabaseRawCode);
        sqLiteDatabase.execSQL(KeyWordsdatabase.feedEntryEXTRA.databaseExtra);
        Log.v("database creater", "onCreate: created datbase!!!!!!!!" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
