package com.example.nayan.tasklist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

public   class CursorAdapterClass extends CursorAdapter {


    public CursorAdapterClass(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.simple_text1,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CheckBox checkBox  =  (CheckBox)view.findViewById(R.id.checkbox);
        TextView textView  =  (TextView)view.findViewById(R.id.text1Custome);

        int check = 0 ;
             check   =  cursor.getInt(cursor.getColumnIndex(KeyWordsdatabase.feedEntry.checkBox));
        String text =   cursor.getString(cursor.getColumnIndex(KeyWordsdatabase.feedEntry.targetText));
        if(check == 1 ){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
        textView.setText(text);


    }
}
