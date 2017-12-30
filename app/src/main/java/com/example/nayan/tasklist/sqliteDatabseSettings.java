package com.example.nayan.tasklist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nayan on 09-12-2017.
 */

public class sqliteDatabseSettings extends SQLiteOpenHelper {
    private static String DataBaseName =  "settings";
    public sqliteDatabseSettings(Context context) {

        super(context,DataBaseName, null, 2);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(KeyWordsdatabase.feedEntrySettings.Tableexe);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
